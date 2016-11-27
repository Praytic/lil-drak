package com.mygdx.lildrak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mygdx.lildrak.CustomApplicationAdapter.spriteBatch;

@Component
public class StartScreenAdapter extends ScreenAdapter {

    private Texture background;
    private Sprite fadeBox;
    private Color currentColor;
    private Color defaultColor;
    private Music music;
    private BitmapFont font;
    private int fadeDirection = -1;
    private int highScore;
    private int difficulty;
    @Autowired
    private GameScreen gameScreen;
    @Autowired
    private AssetManager assetManager;
    @Autowired
    private Game game;

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);
        updateFadeBoxColor();
        fadeBox.draw(spriteBatch);
        spriteBatch.end();

        getInput();
    }

    private void updateFadeBoxColor() {
        currentColor = fadeBox.getColor();
        if ((fadeDirection == -1 && currentColor.a < 0.1f) || (fadeDirection == 1 && currentColor.a > 0.7f))
            fadeDirection = -fadeDirection;
        fadeBox.setColor(currentColor.r, currentColor.g, currentColor.b, currentColor.a + fadeDirection * 0.009f);
    }

    private void getInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    music.play();
                }
            }, 2f);
            game.setScreen(gameScreen);
            font.setColor(defaultColor);
        }
    }

    @Override
    public void show() {
        background = assetManager.get(Asset.Image.BACKGROUND.getFileName());
        Texture fadeBoxTexture = assetManager.get(Asset.Image.FADE_BLACK.getFileName());
        fadeBox = new Sprite(fadeBoxTexture);
        fadeBox.setPosition(380, 60);

        music = assetManager.get(Asset.Sound.MUSIC_GAME.getFileName());
        music.setLooping(true);

        font = assetManager.get(Asset.Font.DEFAULT.getFileName());
        defaultColor = new Color(1f, 1f, 1f, 1f);

        difficulty = Gdx.app.getPreferences("My Preferences").getInteger("difficulty", 0);
        highScore = Gdx.app.getPreferences("My Preferences").getInteger("score", 0);
    }

    public int getHighScore() {
        return highScore;
    }
}
