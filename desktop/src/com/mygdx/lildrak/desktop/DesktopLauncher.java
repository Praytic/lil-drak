package com.mygdx.lildrak.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.Lildrak;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width  = (int) (Constants.VIEWPORT_WIDTH / Constants.METER_TO_PIXEL);
		config.height = (int) (Constants.VIEWPORT_HEIGHT / Constants.METER_TO_PIXEL);
		config.title = "Lil' Drak";
		config.resizable = false;

		new LwjglApplication(new Lildrak(), config);
	}
}
