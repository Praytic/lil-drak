package com.mygdx.lildrak.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.Lildrak;

public class HtmlLauncher extends GwtApplication
{

    @Override
    public GwtApplicationConfiguration getConfig()
    {
        int width = (int) (Constants.VIEWPORT_WIDTH / Constants.METER_TO_PIXEL);
        int height = (int) (Constants.VIEWPORT_HEIGHT / Constants.METER_TO_PIXEL);
        GwtApplicationConfiguration configuration = new GwtApplicationConfiguration(width, height);
        return configuration;
    }

    @Override
    public ApplicationListener getApplicationListener()
    {
        return new Lildrak();
    }
}