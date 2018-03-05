package com.codeandcoke.minijumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.codeandcoke.minijumper.screens.SplashScreen;

/**
 * Clase principal del proyecto
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MiniJumper extends Game {

	public SpriteBatch spriteBatch;
	public BitmapFont font;
	Skin skin;

	/*
	 * Método invocado en el momento de crearse la aplicación
	 */
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(new SplashScreen(this));
	}

	/* Método principal del juego para renderizado y lógica
	 * Es el método principal del juego, y es invocado automáticamente
	 * por el motor libGDX de forma continuada
	 * Desde aquí se harán las llamadas a todas las cosas que queremos que ocurran
	 */
	@Override
	public void render() {

		super.render();
	}
	
	/*
	 * Método invocado cuando se destruye la aplicación
	 * Siempre va precedido de una llamada a 'pause()'
	 */
	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
	}

	/**
	 * Devuelve el skin utilizado para los menú del juego
	 * @return
	 */
	public Skin getSkin() {
		if (skin == null)
			skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		return skin;
	}
}