package com.codeandcoke.minijumper.characters.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.codeandcoke.minijumper.managers.ResourceManager;
import com.codeandcoke.minijumper.managers.SpriteManager;

import static com.codeandcoke.minijumper.util.Constants.*;

/**
 * Gray Enemy is a kind of enemy capable of firing
 * @author Santiago Faci
 * @curso 2014-2015
 */
public class GrayEnemy extends Enemy {

    public GrayEnemy(float x, float y, int lives, Vector2 velocity) {
        super(x, y, lives, velocity);

        rightAnimation = new Animation(0.15f, ResourceManager.getRegions("gray_bubble_right"));
        leftAnimation = new Animation(0.15f, ResourceManager.getRegions("gray_bubble_left"));
        faceLeft = true;
        currentFrame = ResourceManager.getRegion("gray_bubble_left", 1);

        rect.width = ENEMY_WIDTH;
        rect.height = ENEMY_HEIGHT;
    }

    public void fire() {

    }

    @Override
    public void update(float dt, SpriteManager spriteManager) {
        super.update(dt, spriteManager);

        // he fires when he find the player
    }
}
