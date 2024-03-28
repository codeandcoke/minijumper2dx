package com.codeandcoke.minijumper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.codeandcoke.minijumper.MiniJumper;
import com.codeandcoke.minijumper.managers.CameraManager;
import com.codeandcoke.minijumper.managers.LevelManager;
import com.codeandcoke.minijumper.managers.RenderManager;
import com.codeandcoke.minijumper.managers.SpriteManager;

/**
 * Pantalla de juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GameScreen implements Screen {

    MiniJumper game;
    SpriteManager spriteManager;
    LevelManager levelManager;
    CameraManager cameraManager;
    RenderManager renderManager;

    public enum Mode {
        ONE_PLAYER, TWO_PLAYER;
    }

    public GameScreen(MiniJumper game) {
        this.game = game;

        //ResourceManager.loadAllResources();
        spriteManager = new SpriteManager(game);
        levelManager = new LevelManager(spriteManager);
        levelManager.loadCurrentLevel();

        spriteManager.setLevelManager(levelManager);
        cameraManager = new CameraManager(spriteManager, levelManager);
        levelManager.setCameraManager(cameraManager);
        spriteManager.setCameraManager(cameraManager);
        renderManager = new RenderManager(spriteManager, cameraManager, levelManager.batch);
    }

    @Override
    public void render(float dt) {
        // Pinta el fondo de la pantalla de azul oscuro (RGB + alpha)
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        // Limpia la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraManager.handleCamera();
        spriteManager.update(dt);
        renderManager.drawFrame();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
