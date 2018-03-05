package com.codeandcoke.minijumper.characters.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.codeandcoke.minijumper.util.Constants;
import com.codeandcoke.minijumper.managers.ResourceManager;

/**
 * Green Enemy is the most basic enemy
 * @author Santiago Faci
 * @curso 2014-2015
 */
public class GreenEnemy extends Enemy {

    public GreenEnemy(float x, float y, int lives, Vector2 velocity) {
        super(x, y, lives, velocity);

        rightAnimation = new Animation(0.15f, ResourceManager.getRegions("green_bubble_right"));
        leftAnimation = new Animation(0.15f, ResourceManager.getRegions("green_bubble_left"));
        faceLeft = true;
        currentFrame = ResourceManager.getRegion("green_bubble_left", 1);

        rect.width = Constants.ENEMY_WIDTH;
        rect.height = Constants.ENEMY_HEIGHT;
    }
}
