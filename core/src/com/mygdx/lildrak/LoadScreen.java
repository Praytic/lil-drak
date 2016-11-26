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
        intro = new Texture(Asset.Image.INTRO.getFileName());
        fadeBox = new Sprite(new Texture(Asset.Image.FADE.getFileName()));
        fadeBox.setPosition(400, 170);
        music = Gdx.audio.newMusic(Gdx.files.internal(Asset.Sound.MUSIC.getFileName()));
        music.setLooping(true);
        music.play();

        for (Asset assetType : Asset.Image.values()) {
            Lildrak.ASSETS.load(assetType.getFileName(), Texture.class);
        }
        Lildrak.ASSETS.load(Asset.Sound.PICKUP.getFileName(), Sound.class);
        Lildrak.ASSETS.load(Asset.Sound.HURT.getFileName(), Sound.class);
        Lildrak.ASSETS.load(Asset.Sound.MUSIC_GAME.getFileName(), Music.class);
        Lildrak.ASSETS.load(Asset.Font.DEFAULT.getFileName(), BitmapFont.class);
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