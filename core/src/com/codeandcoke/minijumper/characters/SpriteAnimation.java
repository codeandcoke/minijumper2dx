package com.codeandcoke.minijumper.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase base para cualquier cosa animada
 * @author Santiago Faci
 * @curso 2014-2015
 */
public abstract class SpriteAnimation {

    public Vector2 position;
    public float stateTime;
    public TextureRegion currentFrame;
    protected boolean dead;
    public Rectangle rect;

    public SpriteAnimation(float x, float y) {

        position = new Vector2(x, y);
        rect = new Rectangle();
    }

    public void render(Batch batch) {
        if (currentFrame != null)
            batch.draw(currentFrame, position.x, position.y);
    }

    public abstract void update(float dt);

    public boolean isDead() {
        return dead;
    }

    public abstract void die();

    public abstract void resurrect();
}
