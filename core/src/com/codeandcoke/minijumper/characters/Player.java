package com.codeandcoke.minijumper.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.codeandcoke.minijumper.managers.ConfigurationManager;
import com.codeandcoke.minijumper.managers.ResourceManager;
import com.codeandcoke.minijumper.managers.SpriteManager;
import com.codeandcoke.minijumper.util.Constants;

/**
 * Player character
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Player extends Character {

    Animation<TextureRegion> rightAnimation, leftAnimation, upAnimation, friendAnimation;
    public enum State {
        IDLE_RIGHT, IDLE_LEFT, RIGHT, LEFT, CLIMBING, DEAD;
    }
    public State state;
    public int score;
    public int rainbows;

    // To manage friend actions
    private TextureRegion friendFrame;
    private Vector2 friendPosition;
    private boolean friendAttack;
    private long friendAttackTime;
    private static long FRIEND_ATTACK_DURATION = 1000;

    private boolean faceRight;
    public long firePressed, fireReleased;
    public boolean firing;

    private long timeBetweenFires;
    private static long TIME_BETWEEN_FIRES = 500;

    public Player(float x, float y, int lives, Vector2 velocity) {
        super(x, y, lives, velocity);

        rightAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("player_run_right"));
        leftAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("player_run_left"));
        upAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("player_up"));
        // Friend information
        friendAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("bat"));
        friendPosition = new Vector2(x - 10, y + Constants.PLAYER_HEIGHT + 5);

        state = State.IDLE_RIGHT;
        currentFrame = ResourceManager.getRegion("player_idle_right");

        rect.width = Constants.PLAYER_WIDTH;
        rect.height = Constants.PLAYER_HEIGHT;

        timeBetweenFires = 0;
        faceRight = true;
    }

    @Override
    public void update(float dt, SpriteManager spriteManager) {

        stateTime += dt;

        switch(state) {
            case IDLE_RIGHT:
                currentFrame = ResourceManager.getRegion("player_idle_right");
                faceRight = true;
                break;
            case IDLE_LEFT:
                currentFrame = ResourceManager.getRegion("player_idle_left");
                faceRight = false;
                break;
            case RIGHT:
                if (!isJumping)
                    currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                else
                    currentFrame = ResourceManager.getRegion("player_run_right");

                faceRight = true;
                break;
            case LEFT:
                if (!isJumping)
                    currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                else
                    currentFrame = ResourceManager.getRegion("player_run_left");

                faceRight = false;
                break;
            case CLIMBING:
                currentFrame = upAnimation.getKeyFrame(stateTime, true);
                break;
            case DEAD:
                currentFrame = ResourceManager.getRegion("player_idle_down");
                break;
            default:
                currentFrame = ResourceManager.getRegion("player_idle");
        }

        friendFrame = friendAnimation.getKeyFrame(stateTime, true);

        // Update player position and check for collisions with the terrain
        velocity.y -= Constants.GRAVITY * dt;
        velocity.scl(dt);
        rect.set(position.x, position.y, rect.getWidth(), rect.getHeight());
        if (!isDead())
            checkCollisions(spriteManager);
        velocity.scl(1 / dt);
        position.add(velocity);

        // Update friend position
        if (!friendAttack) {
            friendPosition.x = position.x - 10;
            friendPosition.y = position.y + Constants.PLAYER_HEIGHT + 5;
        }

        // Check screen borders
        if (position.x < 0)
            position.x = 0;

        if ((position.y < 0) && (!isDead()))
            die();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Batch batch) {
        super.render(batch);

        // Flip friend frame before draw it if player looks at left
        if ((state == State.LEFT) || (state == State.IDLE_LEFT))
            friendFrame.flip(true, false);

        batch.draw(friendFrame, friendPosition.x, friendPosition.y);

        // Flip again friend sprite to restore its state
        if ((state == State.LEFT) || (state == State.IDLE_LEFT))
            friendFrame.flip(true, false);
    }

    /**
     * Player jump
     */
    public void jump() {
        velocity.y = Constants.JUMPING_SPEED;
        isJumping = true;
        if (ConfigurationManager.isSoundEnabled())
            ResourceManager.getSound(Constants.SOUND + "jump.mp3").play();
    }

    /**
     * Superjump
     */
    public void superJump() {
        velocity.y = Constants.JUMPING_SPEED * 0.0234f;
        isJumping = true;
        if (ConfigurationManager.isSoundEnabled())
            ResourceManager.getSound(Constants.SOUND + "spring.mp3").play();
    }

    /**
     * Player death
     */
    @Override
    public void die() {
        if (!isDead()) {
            lives--;
            dead = true;
            state = State.DEAD;
            velocity.y = Constants.JUMPING_SPEED;
            velocity.x = 0;
            ResourceManager.getSound(Constants.SOUND + "player_die.mp3").play();
            score = 0;
        }
    }

    /**
     * player resurrection
     */
    @Override
    public void resurrect() {
        dead = false;
        state = State.IDLE_RIGHT;
        velocity.y = 0;
    }

    public void loadFire() {
        firing = true;
        firePressed = TimeUtils.millis();
    }

    /**
     * The player fires a stone
     */
    public com.codeandcoke.minijumper.characters.animations.Stone fire() {

        fireReleased = TimeUtils.millis();
        com.codeandcoke.minijumper.characters.animations.Stone stone = null;
        float power = MathUtils.clamp(fireReleased - firePressed, 0, 240);
        float speedX = power / 40;

        if (TimeUtils.millis() - timeBetweenFires >= TIME_BETWEEN_FIRES) {
            stone = new com.codeandcoke.minijumper.characters.animations.Stone(position.x + 15, position.y + 10, faceRight, speedX);
            timeBetweenFires = TimeUtils.millis();
            ResourceManager.getSound(Constants.SOUND + "throw.mp3").play();
            firing = false;
        }

        return stone;
    }

    /**
     * Player throws his bird to attack some enemy or pick some item
     */
    public void throwBird() {

        if (!friendAttack) {
            friendAttackTime = TimeUtils.millis();
            friendAttack = true;
        } else {
            //if (TimeUtils.millis() - friendAttackTime)
        }
    }

    @Override
    public void checkCollisions(SpriteManager spriteManager) {
        if (!isDead())
            super.checkCollisions(spriteManager);
    }
}
