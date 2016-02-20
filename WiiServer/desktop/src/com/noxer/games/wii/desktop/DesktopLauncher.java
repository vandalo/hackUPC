package com.noxer.games.wii.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.noxer.games.wii.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Race";
	    config.width = 800;
	    config.height = 480;
		new LwjglApplication(new Start(), config);
	}
}
