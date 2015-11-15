package org.sfsoft.minijumper.characters.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import org.sfsoft.minijumper.characters.enemies.Enemy;
import org.sfsoft.minijumper.managers.ResourceManager;

import static org.sfsoft.minijumper.util.Constants.*;

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

        rect.width = ENEMY_WIDTH;
        rect.height = ENEMY_HEIGHT;
    }
}
