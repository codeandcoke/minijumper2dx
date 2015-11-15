package org.sfsoft.minijumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.sfsoft.minijumper.util.Constants;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase principal de la versión de escritorio (PC) del juego
 * Esta clase hace de lanzadora (para la versión de PC en este caso)
 * del proyecto principal
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 */
public class DesktopMiniJumper {

	public static void main(String[] args) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "MiniJumper";
		configuration.width = Constants.SCREEN_WIDTH;
		configuration.height = Constants.SCREEN_HEIGHT;
		//configuration.fullscreen = true;

		/*TexturePacker2.Settings settings = new TexturePacker2.Settings();
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		//settings.filterMag = Texture.TextureFilter.Linear;
		//settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker2.process(settings, "minijumper-desktop/assets/characters", "minijumper-desktop/assets/characters", "minijumper.pack");*/
				
		new LwjglApplication(new MiniJumper(), configuration);
	}
}
