package com.codeandcoke.minijumper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.codeandcoke.minijumper.MiniJumper;

/**
 * Pantalla de fin de partida
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GameOverScreen implements Screen {
    final MiniJumper game;

    private Stage stage;

    public GameOverScreen(MiniJumper game) {
        this.game = game;
    }

    @Override
    public void render(float dt) {

        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //stage.setViewport(width, height);
    }

    @Override
    public void show() {
        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        Label title = new Label("GAME OVER!!!!!!!!!!", game.getSkin());
        title.setColor(Color.RED);
        title.setFontScale(2.5f);

        TextButton playButton = new TextButton("PLAY AGAIN", game.getSkin());
        playButton.setColor(Color.RED);
        playButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });
        TextButton mainMenuButton = new TextButton("MAIN MENU", game.getSkin());
        mainMenuButton.setColor(Color.RED);
        mainMenuButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        TextButton exitButton = new TextButton("QUIT GAME", game.getSkin());
        exitButton.setColor(Color.RED);
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                System.exit(0);
            }
        });

        Label aboutLabel = new Label("MINIJUMPER v0.4\n(c) Santiago Faci\nhttp://bitbucket.org/sfaci/minijumper", game.getSkin());
        aboutLabel.setFontScale(1f);

        table.row().height(50);
        table.add(title).center().pad(35f);
        table.row().height(50);
        table.add(playButton).center().width(250).pad(5f);
        table.row().height(50);
        table.add(mainMenuButton).center().width(250).pad(5f);
        table.row().height(50);
        table.add(exitButton).center().width(250).pad(5f);
        table.row().height(50);
        table.add(aboutLabel).center().pad(55f);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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
