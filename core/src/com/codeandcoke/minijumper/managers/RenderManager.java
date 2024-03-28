package com.codeandcoke.minijumper.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.codeandcoke.minijumper.characters.enemies.Enemy;
import com.codeandcoke.minijumper.characters.items.Item;
import com.codeandcoke.minijumper.characters.SpriteAnimation;

import static com.codeandcoke.minijumper.util.Constants.*;
import static com.codeandcoke.minijumper.util.Constants.TILE_HEIGHT;

/**
 * Clase encargada del renderizado de la pantalla durante la partida
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class RenderManager {

    Batch batch;
    BitmapFont font;
    SpriteManager spriteManager;
    CameraManager cameraManager;

    public RenderManager(SpriteManager spriteManager, CameraManager cameraManager, Batch batch) {
        this.spriteManager = spriteManager;
        this.cameraManager = cameraManager;
        this.batch = batch;
        font = new BitmapFont();
    }

    /**
     * Renderizado de cada frame
     */
    public void drawFrame() {
        // Inicia renderizado del juego
        batch.begin();
        // Pinta el HUD
        drawHud();
        // Pinta al jugador
        spriteManager.player.render(batch);
        for (Enemy enemy : spriteManager.enemies)
            enemy.render(batch);
        for (Item item : spriteManager.items)
            item.render(batch);
        for (SpriteAnimation animation : spriteManager.animations)
            animation.render(batch);
        batch.end();
    }

    /**
     * Renderiza el HUD de la partida
     */
    private void drawHud() {
        batch.draw(ResourceManager.getRegion("player_idle_down"), cameraManager.camera.position.x - CAMERA_WIDTH / 2 + 10, CAMERA_HEIGHT - TILE_HEIGHT);
        font.draw(batch, " x " + spriteManager.player.lives, cameraManager.camera.position.x - CAMERA_WIDTH / 2 + PLAYER_WIDTH + 10, CAMERA_HEIGHT - TILE_HEIGHT / 2);

        batch.draw(ResourceManager.getRegion("balloon"), cameraManager.camera.position.x - CAMERA_WIDTH / 2 + 10 + TILE_WIDTH * 2, CAMERA_HEIGHT - PLAYER_HEIGHT);
        font.draw(batch, " x " + spriteManager.player.score, cameraManager.camera.position.x - CAMERA_WIDTH / 2 + PLAYER_WIDTH * 4 + 10, CAMERA_HEIGHT - TILE_HEIGHT / 2);

        batch.draw(ResourceManager.getRegion("rainbow", 6), cameraManager.camera.position.x - CAMERA_WIDTH / 2 + 10 + TILE_WIDTH * 4, CAMERA_HEIGHT - PLAYER_HEIGHT);
        font.draw(batch, " x " + spriteManager.player.rainbows, cameraManager.camera.position.x - CAMERA_WIDTH / 2 + PLAYER_WIDTH * 8 + 10, CAMERA_HEIGHT - TILE_HEIGHT / 2);
    }
}
