package com.mygdx.lildrak.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.CustomApplicationAdapter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mygdx.lildrak")
public class DesktopLauncher {

	@Bean
	public LwjglApplication application(CustomApplicationAdapter adapter, LwjglApplicationConfiguration configuration) {
		return new LwjglApplication(adapter, configuration);
	}

	@Bean
	public LwjglApplicationConfiguration configuration() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width  = (int) (Constants.VIEWPORT_WIDTH / Constants.METER_TO_PIXEL);
		config.height = (int) (Constants.VIEWPORT_HEIGHT / Constants.METER_TO_PIXEL);
		config.title = "Lil' Drak";
		config.resizable = false;
		return config;
	}

	public static void main (String[] arg) {
		new AnnotationConfigApplicationContext(DesktopLauncher.class);
	}
}
