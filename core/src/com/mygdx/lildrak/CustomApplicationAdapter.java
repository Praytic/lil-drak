package com.mygdx.lildrak;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.entity.BodyEntityListener;
import com.mygdx.lildrak.entity.systems.InputSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomApplicationAdapter extends ApplicationAdapter {

    public static SpriteBatch spriteBatch;

    @Autowired
    private Game customGame;
    @Autowired
    private AssetManager assetManager;
    @Autowired
    private StartScreenAdapter startScreen;
    @Autowired
    private GameScreen gameScreen;
    @Autowired
    private World world;
    @Autowired
    private InputAdapter inputAdapter;

    @Override
    public void create() {
        customGame.create();
        spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(inputAdapter);

        Map<String, Class> assetsToLoad = new HashMap<>();
        for (Asset assetType : Asset.Image.values()) {
            assetsToLoad.put(assetType.getFileName(), Texture.class);
        }
        assetsToLoad.put(Asset.Sound.PICKUP.getFileName(), Sound.class);
        assetsToLoad.put(Asset.Sound.HURT.getFileName(), Sound.class);
        assetsToLoad.put(Asset.Sound.MUSIC_GAME.getFileName(), Music.class);
        assetsToLoad.put(Asset.Font.DEFAULT.getFileName(), BitmapFont.class);
        assetsToLoad.entrySet().forEach(entry -> assetManager.load(entry.getKey(), entry.getValue()));
    }

    @Override
    public void render() {
        if (assetManager.update()) {
            customGame.render();
        }
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        assetManager.dispose();
        world.dispose();
        spriteBatch.dispose();
        startScreen.dispose();
    }

    @Bean
    public AssetManager assetManager() {
        return new AssetManager();
    }

    @Bean
    public Engine engine(InputSystem inputSystem, BodyEntityListener bodyEntityListener) {
        Engine engine = new Engine();
        engine.addEntityListener(Family.all(BodyComponent.class).get(), bodyEntityListener);
        engine.addSystem(inputSystem);
        return engine;
    }

    @Bean
    public World world(ContactListener contactListener) {
        World world = new World(new Vector2(0f, 0f), false);
        world.setGravity(new Vector2(0f, Constants.WORLD_GRAVITY));
        world.setContactListener(contactListener);
        return world;
    }
}
