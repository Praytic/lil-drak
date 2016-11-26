package com.mygdx.lildrak;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

public class Hud {
    BitmapFont font;
    Vector2 defaultFontScale;
    int lives;
    int realScore = 0;
    int currentScore = 0;
    int scoreUpdateSpeedLow = 22;
    int scoreUpdateSpeedMed = 88;
    int scoreUpdateSpeedHigh = 773;
    int scoreUpdateSpeed = scoreUpdateSpeedLow; // updates per frame

    Texture heartTexture;
    GlyphLayout layout;
    float width;
    float scale = 0;
    float yOffset = 0;
    int juiceJump = 50;
    int juiceScale = 10000;

    public Hud() {
        this.font = Lildrak.ASSETS.get(AssetPaths.FONT);
        this.defaultFontScale = new Vector2(font.getScaleX(), font.getScaleY());

        heartTexture = Lildrak.ASSETS.get(AssetPaths.HEART);
        layout = new GlyphLayout();
    }

    public void draw() {
        realScore = GameScreen.score;
        lives = GameScreen.playerHealth;
        updateScore();

        layout.setText(font, "" + currentScore);
        width = layout.width;

        // SCORE JUICE
        if (realScore != currentScore && currentScore != 0) {
            scale = (float) (realScore - currentScore) / juiceScale + 1;
            yOffset = (float) (realScore - currentScore) / realScore * juiceJump;

            font.getData().setScale(defaultFontScale.x * scale, defaultFontScale.y * scale);
        }

        Lildrak.spriteBatch.begin();

        font.draw(Lildrak.spriteBatch, ("" + currentScore), 1200 - width, 900 + yOffset);
        font.getData().setScale(defaultFontScale.x, defaultFontScale.y);
        for (int i = 0; i < lives; i++) {
            Lildrak.spriteBatch.draw(heartTexture, 10 + i * 70, 870);
        }
        Lildrak.spriteBatch.end();
    }

    private void updateScore() {
        // Increase speed, when currentScore is lagging behind too much
        if (realScore - currentScore > 4998) scoreUpdateSpeed = scoreUpdateSpeedHigh;
        else if (realScore - currentScore > 1498) scoreUpdateSpeed = scoreUpdateSpeedMed;

        // Increment digits to make the score change look nice
        if (currentScore < realScore) currentScore += scoreUpdateSpeed;

        // Adjust score just in case
        if (currentScore > realScore) currentScore = realScore;

        // speed reset
        if (currentScore >= realScore) scoreUpdateSpeed = scoreUpdateSpeedLow;
    }
}
