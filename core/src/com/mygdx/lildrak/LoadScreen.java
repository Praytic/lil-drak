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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadScreen extends ScreenAdapter
{
    SpriteBatch batch;
    Texture intro;
    Sprite fadeBox;
    Color fadeBoxColor;
    int fadeDirection = -1;
    Music music;
    float minimumShowTime = 100;

    @Override
    public void show()
    {
        batch = Lildrak.spriteBatch;
        intro = new Texture(AssetPaths.INTRO);
        fadeBox = new Sprite(new Texture(AssetPaths.FADE));
        fadeBox.setPosition(400, 170);
        music = Gdx.audio.newMusic(Gdx.files.internal(AssetPaths.MUSIC));
        music.setLooping(true);
        music.play();

        for (String e : AssetPaths.textureList)
        {
            Lildrak.assets.load(e, Texture.class);
        }
        Lildrak.assets.load(AssetPaths.PICKUP, Sound.class);
        Lildrak.assets.load(AssetPaths.HURT, Sound.class);
        Lildrak.assets.load(AssetPaths.MUSIC_GAME, Music.class);
        Lildrak.assets.load(AssetPaths.FONT, BitmapFont.class);
        Lildrak.assets.finishLoading();

        Lildrak.startScreen = new StartScreen();
        Lildrak.gameScreen = new GameScreen();
    }

    @Override
    public void render(float deltaTime)
    {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        batch.draw(intro, 0, 0);
        updateFadeBoxColor();
        fadeBox.draw(batch);
        batch.end();

        minimumShowTime -= deltaTime;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            minimumShowTime = 0;

        if (Lildrak.assets.update() && minimumShowTime <= 0)
        {
            Lildrak.game.setScreen(Lildrak.startScreen);
            music.stop();
        }
    }

    private void updateFadeBoxColor()
    {
        fadeBoxColor = fadeBox.getColor();
        if ( (fadeDirection == -1 && fadeBoxColor.a < 0.1f) || (fadeDirection == 1 && fadeBoxColor.a > 0.7f) )
            fadeDirection = -fadeDirection;
        fadeBox.setColor(fadeBoxColor.r, fadeBoxColor.g, fadeBoxColor.b, fadeBoxColor.a + fadeDirection * 0.009f);
    }

    @Override
    public void hide()
    {
        dispose();
    }
    @Override
    public void dispose()
    {
        intro.dispose();
        fadeBox.getTexture().dispose();
        music.dispose();
    }
}