package com.codeandcoke.minijumper.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.codeandcoke.minijumper.util.Constants;

import java.io.File;

/**
 * Gestor de recursos del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class ResourceManager {

    public static AssetManager assets = new AssetManager();

    /**
     * Carga todos los recursos del juego
     */
    public static void loadAllResources() {
        assets.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);

        loadSounds();
        loadMusics();
    }

    public static boolean update() {
        return assets.update();
    }

    /**
     * Carga los sonidos
     */
    public static void loadSounds() {

        assets.load("sounds" + File.separator + "game_begin.wav", Sound.class);
        assets.load("sounds" + File.separator + "game_over.wav", Sound.class);
        assets.load("sounds" + File.separator + "jump.mp3", Sound.class);
        assets.load("sounds" + File.separator + "player_die.mp3", Sound.class);
        assets.load("sounds" + File.separator + "player_die2.wav", Sound.class);
        assets.load("sounds" + File.separator + "player_die3.wav", Sound.class);
        assets.load("sounds" + File.separator + "blop.mp3", Sound.class);
        assets.load("sounds" + File.separator + "throw.mp3", Sound.class);
        assets.load("sounds" + File.separator + "spring.mp3", Sound.class);
        assets.load("sounds" + File.separator + "shot.mp3", Sound.class);
    }

    /**
     * Carga las músicas
     */
    public static void loadMusics() {
        assets.load("musics" + File.separator + "bso.mp3", Music.class);

    }

    /**
     * Obtiene una región de textura o la primera de una animación
     * @param name
     * @return
     */
    public static TextureRegion getRegion(String name) {

        return assets.get(Constants.TEXTURE_ATLAS, TextureAtlas.class).findRegion(name);
    }

    /**
     * Obtiene una región de textura determinada de las que forman una animación
     * @param name
     * @param position
     * @return
     */
    public static TextureRegion getRegion(String name, int position) {

        return assets.get(Constants.TEXTURE_ATLAS, TextureAtlas.class).findRegion(name, position);
    }

    /**
     * Obtiene todas las regiones de textura que forman una misma animación
     * @param name
     * @return
     */
    public static Array<TextureAtlas.AtlasRegion> getRegions(String name) {

        return assets.get(Constants.TEXTURE_ATLAS, TextureAtlas.class).findRegions(name);
    }

    /**
     * Obtiene un sonido determinado
     * @param name
     * @return
     */
    public static Sound getSound(String name) {
        return assets.get(name, Sound.class);
    }

    /**
     * Obtiene una música determinada
     * @param name
     * @return
     */
    public static Music getMusic(String name) {
        return assets.get(name, Music.class);
    }
}
