package com.codeandcoke.minijumper;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.codeandcoke.minijumper.util.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
        config.setTitle("MiniJumper");
        config.setWindowSizeLimits(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		//configuration.fullscreen = true;

		/*TexturePacker2.Settings settings = new TexturePacker2.Settings();
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		//settings.filterMag = Texture.TextureFilter.Linear;
		//settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker2.process(settings, "minijumper-desktop/assets/characters", "minijumper-desktop/assets/characters", "minijumper.pack");*/

		new Lwjgl3Application(new MiniJumper(), config);
	}
}
