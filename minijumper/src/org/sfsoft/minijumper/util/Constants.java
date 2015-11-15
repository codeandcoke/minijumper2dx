package org.sfsoft.minijumper.util;

import java.io.File;

/**
 * Constantes del juego
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Constants {

	public static final String APP_NAME = "minijumper";

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;

	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;

	public static final float PLAYER_SPEED = 2f;
	public static final float ENEMY_SPEED = 1f;
	public static final float GRAVITY = 9f;
	public static final float JUMPING_SPEED = 5f;

	public static final int PLAYER_WIDTH = 20;
	public static final int PLAYER_HEIGHT = 28;

	public static final int STONE_WIDTH = 15;
	public static final int STONE_HEIGHT = 14;

	public static final int ENEMY_WIDTH = 26;
	public static final int ENEMY_HEIGHT = 20;

	public static final int TILES_IN_CAMERA = 16;
	public static final int CAMERA_WIDTH = TILES_IN_CAMERA * TILE_WIDTH;
	public static final int CAMERA_HEIGHT = TILES_IN_CAMERA * TILE_HEIGHT;

	public static final String SOUND = "sounds/";
	public static final String MUSIC = "musics/";
	public static final String TEXTURE_ATLAS = "characters/minijumper.pack";
}