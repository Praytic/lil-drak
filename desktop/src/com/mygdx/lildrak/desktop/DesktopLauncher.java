package com.mygdx.lildrak.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.Lildrak;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mygdx.lildrak")
public class DesktopLauncher {

	public static void main (String[] arg) {
		ApplicationContext context = new AnnotationConfigApplicationContext(DesktopLauncher.class);
		Lildrak lildrak = context.getBean(Lildrak.class);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width  = (int) (Constants.VIEWPORT_WIDTH / Constants.METER_TO_PIXEL);
		config.height = (int) (Constants.VIEWPORT_HEIGHT / Constants.METER_TO_PIXEL);
		config.title = "Lil' Drak";
		config.resizable = false;

		LwjglApplication application = new LwjglApplication(lildrak, config);
	}
}
