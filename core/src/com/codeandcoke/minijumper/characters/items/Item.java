package com.codeandcoke.minijumper.characters.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase base para todos los items del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public abstract class Item {

    public Vector2 position;
    TextureRegion currentFrame;
    public Rectangle rect;
    public int score;

    public Item(float x, float y) {

        position = new Vector2(x, y);
        rect = new Rectangle();
    }

    public void render(Batch batch) {
        batch.draw(currentFrame, position.x, position.y);
    }

    public abstract void update(float dt);
}
