package com.codeandcoke.minijumper.characters.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.codeandcoke.minijumper.managers.SpriteManager;
import com.codeandcoke.minijumper.characters.Character;

import static com.codeandcoke.minijumper.util.Constants.*;

/**
 * Clase base para todos los enemigos del juego
 * @author Santiago Faci
 * @curso 2014-2015
 */

public abstract class Enemy extends Character {

    public enum EnemyType {
        green, gray, yellow
    }
    protected EnemyType type;
    Animation<TextureRegion> rightAnimation, leftAnimation;
    public boolean faceLeft;

    public Enemy(float x, float y, int lives, Vector2 velocity) {
        super(x, y, lives, velocity);

        faceLeft = true;
        rect.width = ENEMY_WIDTH;
        rect.height = ENEMY_HEIGHT;
    }

    @Override
    public void update(float dt, SpriteManager spriteManager) {

        stateTime += dt;

        if (faceLeft)
            currentFrame = leftAnimation.getKeyFrame(stateTime, true);
        else
            currentFrame = rightAnimation.getKeyFrame(stateTime, true);

        velocity.y -= GRAVITY * dt;
        velocity.scl(dt);
        rect.set(position.x, position.y, rect.getWidth(), rect.getHeight());
        checkCollisions(spriteManager);
        velocity.scl(1 / dt);
        position.add(velocity);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void die() {
        dead = true;
    }

    @Override
    public void resurrect() {
        dead = false;
    }
}
