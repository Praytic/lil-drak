package com.mygdx.lildrak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadScreen extends ScreenAdapter {

    private Sprite fadeBox;
    private Color fadeBoxColor;
    private int fadeDirection = -1;
    private Music music;

    @Autowired
    private StartScreenAdapter startScreen;
    @Autowired
    private AssetManager assetManager;
    @Autowired
    private Game game;

    @Override
    public void render(float deltaTime) {
        getMusic();
        Gdx.gl.glClearColor(0.5f, 0.5f, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        updateFadeBoxColor();
        if (assetManager.update()) {
            game.setScreen(startScreen);
            getMusic().stop();
        }
    }

    private void updateFadeBoxColor() {
        fadeBoxColor = getFadeBox().getColor();
        if ((fadeDirection == -1 && fadeBoxColor.a < 0.1f) || (fadeDirection == 1 && fadeBoxColor.a > 0.7f))
            fadeDirection = -fadeDirection;
        getFadeBox().setColor(fadeBoxColor.r, fadeBoxColor.g, fadeBoxColor.b, fadeBoxColor.a + fadeDirection * 0.009f);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        fadeBox.getTexture().dispose();
        getMusic().dispose();
    }

    public Sprite getFadeBox() {
        if (fadeBox == null) {
            fadeBox = new Sprite(new Texture(Asset.Image.FADE.getFileName()));
            fadeBox.setPosition(400, 170);
            return fadeBox;
        }
        else {
            return fadeBox;
        }
    }

    public Music getMusic() {
        if (music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal(Asset.Sound.MUSIC.getFileName()));
            music.setLooping(true);
            music.play();
            return music;
        }
        else {
            return music;
        }
    }
}