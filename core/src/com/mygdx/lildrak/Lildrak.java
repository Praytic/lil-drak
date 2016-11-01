package com.mygdx.lildrak;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;

import java.util.Random;

public class Lildrak extends ApplicationAdapter
{
    public static final Game game = new Game()
    {
        @Override
        public void create()
        {
            game.setScreen(new LoadScreen());
        }
    };
    public static final Logger logger = new Logger("");
    public static final AssetManager assets = new AssetManager();
    public static final Random random = new Random();
    public static final Engine engine = new Engine();
    public static final World world  = new World(new Vector2(0f, 0f), false);
    public static SpriteBatch spriteBatch;
    public static StartScreen startScreen;
    public static GameScreen gameScreen;

    @Override
    public void create()
    {
        try
        {
            logger.setLevel(Logger.NONE); // set 'Logger.NONE' to turn off logging
            Gdx.app.setLogLevel(Application.LOG_DEBUG);

            Texture.setAssetManager(assets);

            spriteBatch = new SpriteBatch();

            game.create();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void render ()
    {
        try
        {
            game.render();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose()
    {
        gameScreen.dispose();
        assets.dispose();
        world.dispose();
        if (spriteBatch != null) spriteBatch.dispose();
        if (startScreen != null) startScreen.dispose();
        if (gameScreen != null) gameScreen.dispose();
    }
}
