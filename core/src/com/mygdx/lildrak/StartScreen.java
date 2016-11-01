package com.mygdx.lildrak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class StartScreen extends ScreenAdapter
{
    SpriteBatch batch;
    Texture background;
    Texture controlsHidingTexture;
    Sprite fadeBox;
    Color currentColor;
    Color randomColor;
    Color defaultColor;
    Music music;
    BitmapFont font;
    Preferences prefs;
    int fadeDirection = -1;
    int highScore;
    int lastScore;
    int difficulty;
    float frameCounter = 0f;
    boolean firstLaunch = true;

    public StartScreen()
    {
        batch = Lildrak.spriteBatch;

        background = Lildrak.assets.get(AssetPaths.BACKGROUND);
        controlsHidingTexture = Lildrak.assets.get(AssetPaths.BLACK);
        Texture fadeBoxTexture = Lildrak.assets.get(AssetPaths.FADE_BLACK);
        fadeBox = new Sprite(fadeBoxTexture);
        fadeBox.setPosition(380, 60);

        music = Lildrak.assets.get(AssetPaths.MUSIC_GAME);
        music.setLooping(true);

        font = Lildrak.assets.get(AssetPaths.FONT);
        defaultColor = new Color(1f, 1f, 1f, 1f);

        prefs = Gdx.app.getPreferences("My Preferences");
        difficulty = prefs.getInteger("difficulty", 0);
    }

    @Override
    public void render(float deltaTime)
    {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0);
        font.draw(batch, "Difficulty: " + difficulty, 1000, 900);
        updateFadeBoxColor();
        fadeBox.draw(batch);
        if (!firstLaunch) drawScore(deltaTime);

        batch.end();

        getInput();
    }

    private void updateFadeBoxColor()
    {
        currentColor = fadeBox.getColor();
        if ((fadeDirection == -1 && currentColor.a < 0.1f) || (fadeDirection == 1 && currentColor.a > 0.7f))
            fadeDirection = -fadeDirection;
        fadeBox.setColor(currentColor.r, currentColor.g, currentColor.b, currentColor.a + fadeDirection * 0.009f);
    }

    private void drawScore(float deltaTime)
    {
        batch.draw(controlsHidingTexture, 300, 400); // hide controls
        font.setColor(defaultColor);
        font.draw(batch, "High Score: ", 450, 500);
        if (lastScore == highScore)
        {
            if (frameCounter >= 0.5f)
            {
                randomColor = new Color(Lildrak.random.nextFloat(), Lildrak.random.nextFloat(), Lildrak.random.nextFloat(), 1f);
                font.setColor(randomColor);
            } else frameCounter += deltaTime;
        } else font.draw(batch, ("Score: " + lastScore), 450, 600);

        font.draw(batch, "" + highScore, 670, 500);
    }

    private void getInput()
    {
        // Change difficulty
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0))
        {
            prefs.putInteger("difficulty", 0);
            difficulty = prefs.getInteger("difficulty", 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
        {
            prefs.putInteger("difficulty", 1);
            difficulty = prefs.getInteger("difficulty", 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
        {
            prefs.putInteger("difficulty", 2);
            difficulty = prefs.getInteger("difficulty", 0);
        }

        // Skip screen by pressing space
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    music.play();
                }
            }, 2f);
            firstLaunch = false;
            Lildrak.game.setScreen(Lildrak.gameScreen);
            font.setColor(defaultColor);
        }
    }

    @Override
    public void show()
    {
        reloadScore();
    }

    private void reloadScore()
    {
        highScore = prefs.getInteger("score", 0);
        lastScore = prefs.getInteger("lastScore", 0);
    }
}
