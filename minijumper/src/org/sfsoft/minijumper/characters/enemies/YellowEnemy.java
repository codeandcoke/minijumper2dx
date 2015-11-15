package org.sfsoft.minijumper.characters.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import org.sfsoft.minijumper.characters.enemies.Enemy;
import org.sfsoft.minijumper.managers.ResourceManager;
import org.sfsoft.minijumper.managers.SpriteManager;

import static org.sfsoft.minijumper.util.Constants.*;

/**
 * Yellow Enemy is a kind of enemy capable of jumping
 * @author Santiago Faci
 * @curso 2014-2015
 */
public class YellowEnemy extends Enemy {

    private boolean jumping;
    private long lastJump;
    private final static long JUMP_TIME = 2000;

    public YellowEnemy(float x, float y, int lives, Vector2 velocity) {
        super(x, y, lives, velocity);

        rightAnimation = new Animation(0.15f, ResourceManager.getRegions("yellow_bubble_right"));
        leftAnimation = new Animation(0.15f, ResourceManager.getRegions("yellow_bubble_left"));
        faceLeft = true;
        currentFrame = ResourceManager.getRegion("yellow_bubble_left", 1);
        lastJump = 0;
    }

    public void jump() {

    }

    @Override
    public void update(float dt, SpriteManager spriteManager) {
        super.update(dt, spriteManager);

        // he jumps randomly
        if (((TimeUtils.millis() - lastJump) >= JUMP_TIME) && (!jumping)) {

            int random = MathUtils.random(0, 10);
            System.out.println(random);
            if (random < 2) {
                velocity.y = JUMPING_SPEED;
                lastJump = TimeUtils.millis();
                jumping = true;
            }
            else {
                lastJump = TimeUtils.millis();
            }
        }

        if (velocity.y < 0)
            jumping = false;
    }
}
