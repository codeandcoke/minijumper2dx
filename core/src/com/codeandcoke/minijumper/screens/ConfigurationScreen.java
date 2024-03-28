package com.codeandcoke.minijumper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.codeandcoke.minijumper.MiniJumper;
import com.codeandcoke.minijumper.util.Constants;

/**
 * Pantalla de configuración del juego
 * @author Santiago Faci
 * @version Curso 2014-2015
 */
public class ConfigurationScreen implements Screen {

    MiniJumper game;
    Stage stage;
    // Almacena las preferencias (en %UserProfile%/.prefs/PreferencesName)
    Preferences prefs;

    public ConfigurationScreen(MiniJumper game) {
        this.game = game;
    }

    /**
     * Carga el menú en pantalla
     */
    private void loadScreen() {

        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        Label title = new Label("MINIJUMPER\nSETTINGS", game.getSkin());
        title.setFontScale(2.5f);

        final CheckBox checkSound = new CheckBox(" SOUND", game.getSkin());
        checkSound.setChecked(prefs.getBoolean("sound"));
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prefs.putBoolean("sound", checkSound.isChecked());
            }
        });

        Label difficultyLabel = new Label("-- DIFFICULTY --", game.getSkin());

        String[] resolutionsArray = {"LOW", "MEDIUM", "HIGH"};
        final List difficultyList = new List(game.getSkin());
        difficultyList.setItems(resolutionsArray);

        difficultyList.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                switch (difficultyList.getSelectedIndex()) {
                    case 0:
                        prefs.putString("difficulty", "low");
                        break;
                    case 1:
                        prefs.putString("difficulty", "medium");
                        break;
                    case 2:
                        prefs.putString("difficulty", "high");
                        break;
                    default:
                }
            }
        });

        TextButton exitButton = new TextButton("MAIN MENU", game.getSkin());
        exitButton.setColor(Color.RED);
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prefs.flush();
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Label aboutLabel = new Label("minijumper v0.4\n(c) Santiago Faci\nhttp://bitbucket.org/sfaci/minijumper", game.getSkin());
        aboutLabel.setFontScale(1f);

        table.row().height(150);
        table.add(title).center().pad(35f);
        table.row().height(20);
        table.add(checkSound).center().pad(5f);
        table.row().height(20);
        table.add(difficultyLabel).center().pad(5f);
        table.row().height(70);
        table.add(difficultyList).center().pad(5f);
        table.row().height(70);
        table.add(exitButton).center().width(300).pad(5f);
        table.row().height(70);
        table.add(aboutLabel).center().pad(55f);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Carga las preferencias del juego
     */
    private void loadPreferences() {

        prefs = Gdx.app.getPreferences(Constants.APP_NAME);

        // Coloca los valores por defecto (para la primera ejecución)
        if (!prefs.contains("sound"))
            prefs.putBoolean("sound", true);
    }

    @Override
    public void show() {

        loadPreferences();
        loadScreen();
    }

    @Override
    public void render(float dt) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();
    }

    @Override
    public void dispose() {

        stage.dispose();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        //stage.setViewport(width, height);
    }

    @Override
    public void resume() {
    }
}
