package org.sfsoft.minijumper.characters.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.sfsoft.minijumper.characters.enemies.Enemy;
import org.sfsoft.minijumper.managers.ResourceManager;

/**
 * A big stone
 * @author Santiago Faci
 * @version August
 */
public class BigStone extends Enemy {

    public BigStone(float x, float y, Vector2 velocity) {
        super(x, y, -1, velocity);
        leftAnimation = new Animation(0.15f, ResourceManager.getRegions("big_stone"));
        rightAnimation = new Animation(0.15f, ResourceManager.getRegions("big_stone"));
        currentFrame = new TextureRegion(ResourceManager.getRegion("big_stone", 1));
    }

    @Override
    public void update(float dt) {

        stateTime += dt;
    }

    @Override
    public void die() {

    }

    @Override
    public void resurrect() {

    }
}
