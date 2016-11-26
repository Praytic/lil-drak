package com.mygdx.lildrak;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Lildrak extends ApplicationAdapter {

    @Autowired
    private LoadScreen loadScreen;

    public Game game;
    public static final Logger logger = new Logger("new_entity", Logger.DEBUG);
    public static final AssetManager ASSETS = new AssetManager();
    public static final Random random = new Random();
    public static final Engine engine = new Engine();
    public static final World world = new World(new Vector2(0f, 0f), false);
    public static SpriteBatch spriteBatch;
    @Autowired
    public StartScreenAdapter startScreen;
    @Autowired
    public GameScreen gameScreen;

    @Override
    public void create() {
        game = new Game() {
            @Override
            public void create() {
                game.setScreen(loadScreen);
            }
        };
        try {
            logger.setLevel(Logger.NONE); // set 'Logger.NONE' to turn off logging
            Gdx.app.setLogLevel(Application.LOG_DEBUG);

            Texture.setAssetManager(ASSETS);

            spriteBatch = new SpriteBatch();

            game.create();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void render() {
        try {
            game.render();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        ASSETS.dispose();
        world.dispose();
        if (spriteBatch != null) spriteBatch.dispose();
        if (startScreen != null) startScreen.dispose();
        if (gameScreen != null) gameScreen.dispose();
    }
}
