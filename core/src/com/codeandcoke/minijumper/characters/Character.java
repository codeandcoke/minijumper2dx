package com.codeandcoke.minijumper.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.codeandcoke.minijumper.characters.enemies.Enemy;
import com.codeandcoke.minijumper.managers.SpriteManager;
import com.codeandcoke.minijumper.util.Constants;

/**
 * Clase base para todos los personajes del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public abstract class Character extends SpriteAnimation {

    public Vector2 velocity;
    public int lives;
    public boolean isJumping;

    public Character(float x, float y, int lives, Vector2 velocity) {
        super(x, y);
        this.lives = lives;
        this.velocity = velocity;
    }

    /**
     * Checks collisions between current character and other elements in the game
     * @param spriteManager
     */
    public void checkCollisions(SpriteManager spriteManager) {

        int startY, endY, startX, endX;
        // Comprueba las colisiones en el eje Y
        if (velocity.y > 0)
            startY = endY = (int) (position.y + rect.getHeight() + velocity.y);
        else
            startY = endY = (int) (position.y + velocity.y);

        startX = (int) position.x;
        endX = (int) (position.x + rect.getWidth());

        spriteManager.getCollisionTiles(this, startX, endX, startY, endY);
        rect.y += velocity.y;
        for (Rectangle tile : spriteManager.tiles) {
            if (rect.overlaps(tile)) {

                if (velocity.y > 0) {
                    position.y = tile.y - rect.getHeight();
                } else {
                    position.y = tile.y + Constants.TILE_HEIGHT;
                    isJumping = false;
                }
                velocity.y = 0;
                break;
            }
        }

        // Comprueba las colisiones en el eje X
        if (velocity.x > 0)
            startX = endX = (int) (position.x + rect.getWidth() + velocity.x);
        else
            startX = endX = (int) (position.x + velocity.x);

        startY = (int) position.y;
        endY = (int) (position.y + rect.getHeight());

        spriteManager.getCollisionTiles(this, startX, endX, startY, endY);
        rect.x += velocity.x;
        for (Rectangle tile : spriteManager.tiles) {
            if (rect.overlaps(tile)) {
                if (this instanceof Enemy) {
                    velocity.x = -velocity.x;
                    ((Enemy) this).faceLeft = !((Enemy) this).faceLeft;
                }
                else
                    velocity.x = 0;
                break;
            }
        }
        rect.x = position.x;
    }

    public abstract void update(float dt, SpriteManager spriteManager);
}
