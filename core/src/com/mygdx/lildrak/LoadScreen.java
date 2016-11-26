package com.mygdx.lildrak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadScreen extends ScreenAdapter {

    Texture intro;
    Sprite fadeBox;
    Color fadeBoxColor;
    int fadeDirection = -1;
    Music music;
    float minimumShowTime = 100;
    @Autowired
    private StartScreenAdapter startScreen;
    @Autowired
    private Lildrak lildrak;

    @Override
    public void show() {
        intro = new Texture(AssetPaths.INTRO);
        fadeBox = new Sprite(new Texture(AssetPaths.FADE));
        fadeBox.setPosition(400, 170);
        music = Gdx.audio.newMusic(Gdx.files.internal(AssetPaths.MUSIC));
        music.setLooping(true);
        music.play();

        for (String e : AssetPaths.textureList) {
            Lildrak.ASSETS.load(e, Texture.class);
        }
        Lildrak.ASSETS.load(AssetPaths.PICKUP, Sound.class);
        Lildrak.ASSETS.load(AssetPaths.HURT, Sound.class);
        Lildrak.ASSETS.load(AssetPaths.MUSIC_GAME, Music.class);
        Lildrak.ASSETS.load(AssetPaths.FONT, BitmapFont.class);
        Lildrak.ASSETS.finishLoading();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Lildrak.spriteBatch.begin();
        Lildrak.spriteBatch.draw(intro, 0, 0);
        updateFadeBoxColor();
        fadeBox.draw(Lildrak.spriteBatch);
        Lildrak.spriteBatch.end();

        minimumShowTime -= deltaTime;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            minimumShowTime = 0;

        if (Lildrak.ASSETS.update() && minimumShowTime <= 0) {
            lildrak.game.setScreen(startScreen);
            music.stop();
        }
    }

    private void updateFadeBoxColor() {
        fadeBoxColor = fadeBox.getColor();
        if ((fadeDirection == -1 && fadeBoxColor.a < 0.1f) || (fadeDirection == 1 && fadeBoxColor.a > 0.7f))
            fadeDirection = -fadeDirection;
        fadeBox.setColor(fadeBoxColor.r, fadeBoxColor.g, fadeBoxColor.b, fadeBoxColor.a + fadeDirection * 0.009f);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        intro.dispose();
        fadeBox.getTexture().dispose();
        music.dispose();
    }
}