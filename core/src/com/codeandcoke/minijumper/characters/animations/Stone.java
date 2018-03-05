package com.codeandcoke.minijumper.characters.animations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.codeandcoke.minijumper.util.Constants;
import com.codeandcoke.minijumper.characters.SpriteAnimation;
import com.codeandcoke.minijumper.managers.ResourceManager;

/**
 * A Stone that player fires
 * August 2015
 * @author Santiago Faci
 * @version August 2015
 */
public class Stone extends SpriteAnimation {

    public final static Vector2 TOP_SPEED = new Vector2(6, 5);
    private Vector2 speed;

    public Stone(float x, float y, boolean faceRight) {
        super(x, y);
        currentFrame = new TextureRegion(ResourceManager.getRegion("stone"));
        rect.height = Constants.STONE_HEIGHT;
        rect.width = Constants.STONE_WIDTH;
        speed = new Vector2(TOP_SPEED.x, TOP_SPEED.y);

        if (!faceRight)
            speed.x *= -1;
    }

    public Stone(float x, float y, boolean faceRight, float speedX) {
        super(x, y);
        currentFrame = new TextureRegion(ResourceManager.getRegion("stone"));
        rect.height = Constants.STONE_HEIGHT;
        rect.width = Constants.STONE_WIDTH;
        speed = new Vector2(speedX, TOP_SPEED.y);

        if (!faceRight)
            speed.x *= -1;
    }

    @Override
    public void update(float dt) {

        stateTime += dt;
        position.x += speed.x;
        position.y += speed.y;
        rect.set(position.x, position.y, rect.getWidth(), rect.getHeight());

        speed.y -= 0.5f;
        if (speed.y < -10f)
            speed.y = -10f;
    }

    @Override
    public void die() {

    }

    @Override
    public void resurrect() {

    }
}
