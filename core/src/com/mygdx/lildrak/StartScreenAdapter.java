package com.mygdx.lildrak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartScreenAdapter extends ScreenAdapter {

    private Texture background;
    private Texture controlsHidingTexture;
    private Sprite fadeBox;
    private Color currentColor;
    private Color randomColor;
    private Color defaultColor;
    private Music music;
    private BitmapFont font;
    private int fadeDirection = -1;
    private int highScore;
    private int lastScore;
    private int difficulty;
    private float frameCounter = 0f;
    private boolean firstLaunch = true;
    @Autowired
    private GameScreen gameScreen;
    @Autowired
    private Lildrak lildrak;

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Lildrak.spriteBatch.begin();

        Lildrak.spriteBatch.draw(background, 0, 0);
        font.draw(Lildrak.spriteBatch, "Difficulty: " + difficulty, 1000, 900);
        updateFadeBoxColor();
        fadeBox.draw(Lildrak.spriteBatch);
        if (!firstLaunch) drawScore(deltaTime);

        Lildrak.spriteBatch.end();

        getInput();
    }

    private void updateFadeBoxColor() {
        currentColor = fadeBox.getColor();
        if ((fadeDirection == -1 && currentColor.a < 0.1f) || (fadeDirection == 1 && currentColor.a > 0.7f))
            fadeDirection = -fadeDirection;
        fadeBox.setColor(currentColor.r, currentColor.g, currentColor.b, currentColor.a + fadeDirection * 0.009f);
    }

    private void drawScore(float deltaTime) {
        Lildrak.spriteBatch.draw(controlsHidingTexture, 300, 400); // hide controls
        font.setColor(defaultColor);
        font.draw(Lildrak.spriteBatch, "High Score: ", 450, 500);
        if (lastScore == highScore) {
            if (frameCounter >= 0.5f) {
                randomColor = new Color(Lildrak.random.nextFloat(), Lildrak.random.nextFloat(), Lildrak.random.nextFloat(), 1f);
                font.setColor(randomColor);
            } else frameCounter += deltaTime;
        } else font.draw(Lildrak.spriteBatch, ("Score: " + lastScore), 450, 600);

        font.draw(Lildrak.spriteBatch, "" + highScore, 670, 500);
    }

    private void getInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            Gdx.app.getPreferences("My Preferences").putInteger("difficulty", 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            Gdx.app.getPreferences("My Preferences").putInteger("difficulty", 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.getPreferences("My Preferences").putInteger("difficulty", 2);
        }
        difficulty = Gdx.app.getPreferences("My Preferences").getInteger("difficulty", 0);

        // Skip screen by pressing space
         if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    music.play();
                }
            }, 2f);
            firstLaunch = false;
            lildrak.game.setScreen(gameScreen);
            font.setColor(defaultColor);
        }
      }

    @Override
    public void show() {
        background = Lildrak.ASSETS.get(Asset.Image.BACKGROUND.getFileName());
        controlsHidingTexture = Lildrak.ASSETS.get(Asset.Image.BLACK.getFileName());
        Texture fadeBoxTexture = Lildrak.ASSETS.get(Asset.Image.FADE_BLACK.getFileName());
        fadeBox = new Sprite(fadeBoxTexture);
        fadeBox.setPosition(380, 60);

        music = Lildrak.ASSETS.get(Asset.Sound.MUSIC_GAME.getFileName());
        music.setLooping(true);

        font = Lildrak.ASSETS.get(Asset.Font.DEFAULT.getFileName());
        defaultColor = new Color(1f, 1f, 1f, 1f);

        difficulty = Gdx.app.getPreferences("My Preferences").getInteger("difficulty", 0);

        highScore = Gdx.app.getPreferences("My Preferences").getInteger("score", 0);
        lastScore = Gdx.app.getPreferences("My Preferences").getInteger("lastScore", 0);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
