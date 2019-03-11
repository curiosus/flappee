package me.curiosus.games.flappee.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import me.curiosus.games.flappee.FlappeeGame;
import me.curiosus.games.flappee.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Avionics";
		config.width = (int) GameScreen.WORLD_WIDTH;
		config.height = (int) GameScreen.WORLD_HEIGHT;
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
		TexturePacker.process(settings, "core/assets", "core/assets", "flappee_assets");
		new LwjglApplication(new FlappeeGame(), config);
	}
}
