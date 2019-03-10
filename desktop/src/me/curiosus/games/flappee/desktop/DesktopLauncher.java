package me.curiosus.games.flappee.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.curiosus.games.flappee.FlappeeGame;
import me.curiosus.games.flappee.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Avionics";
		config.width = (int) GameScreen.WORLD_WIDTH;
		config.height = (int) GameScreen.WORLD_HEIGHT;
		new LwjglApplication(new FlappeeGame(), config);
	}
}
