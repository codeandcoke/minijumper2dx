package com.codeandcoke.minijumper2dx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.codeandcoke.minijumper.MiniJumper;
import com.codeandcoke.minijumper.util.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "MiniJumper";
        config.width = Constants.SCREEN_WIDTH;
        config.height = Constants.SCREEN_HEIGHT;
		//configuration.fullscreen = true;

		/*TexturePacker2.Settings settings = new TexturePacker2.Settings();
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		//settings.filterMag = Texture.TextureFilter.Linear;
		//settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker2.process(settings, "minijumper-desktop/assets/characters", "minijumper-desktop/assets/characters", "minijumper.pack");*/

		new LwjglApplication(new MiniJumper(), config);
	}
}
