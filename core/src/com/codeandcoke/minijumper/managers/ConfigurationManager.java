package com.codeandcoke.minijumper.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.codeandcoke.minijumper.util.Constants;

/**
 * Gestor de la configuración de la partida
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class ConfigurationManager {

    private static Preferences prefs = Gdx.app.getPreferences(Constants.APP_NAME);;

    /**
     * Comprueba si el sonido está o no activado durante el juego
     * @return
     */
    public static boolean isSoundEnabled() {

        return prefs.getBoolean("sound");
    }
}
