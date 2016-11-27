package com.mygdx.lildrak;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Hud {
    int lives;
    int realScore = 0;
    int currentScore = 0;
    int scoreUpdateSpeedLow = 22;
    int scoreUpdateSpeedMed = 88;
    int scoreUpdateSpeedHigh = 773;
    int scoreUpdateSpeed = scoreUpdateSpeedLow; // updates per frame

    float scale = 0;
    float yOffset = 0;
    int juiceJump = 50;
    int juiceScale = 10000;
    @Autowired
    private Spawner spawner;
    @Autowired
    private StartScreenAdapter startScreenAdapter;

    public Hud() {
        System.out.println();
    }

    public void draw() {
        BitmapFont font = Lildrak.ASSETS.get(Asset.Font.DEFAULT.getFileName());
        font.getData().setScale(0.5f, 0.5f);
        Texture heartTexture = Lildrak.ASSETS.get(Asset.Image.HEART.getFileName());
        GlyphLayout layout = new GlyphLayout();

        realScore = GameScreen.score;
        lives = GameScreen.playerHealth;
        updateScore();

        layout.setText(font, new String(new char[12]).replace('\0', '*'));
        float width = layout.width;

        if (realScore != currentScore && currentScore != 0) {
            scale = (float) (realScore - currentScore) / juiceScale + 1;
            yOffset = (float) (realScore - currentScore) / realScore * juiceJump;
        }

        Lildrak.spriteBatch.begin();

        font.draw(Lildrak.spriteBatch, String.format("current_score: %d", currentScore), 1200 - width, 900 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("highest_score: %d", startScreenAdapter.getHighScore()), 1200 - width, 875 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("level: %d", spawner.currentLevel), 1200 - width, 850 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("speed: %.2f", spawner.currentScrollSpeed), 1200 - width, 825 + yOffset);
        font.draw(Lildrak.spriteBatch, "------ rate ------", 1200 - width, 775 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("candy_spawn: %.2f", 1 / (spawner.getCandySpawnRate() / 100f) * spawner.currentSpawnRate), 1200 - width, 750 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("lollipop_spawn: %.2f", 1 / (spawner.getLollipopSpawnRate() / 100f) * spawner.currentSpawnRate), 1200 - width, 725 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("money_spawn: %.2f", 1 / (spawner.getMoneySpawnRate() / 100f) * spawner.currentSpawnRate), 1200 - width, 700 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("flame_spawn: %.2f", 1 / (spawner.getFlameSpawnRate() / 100f) * spawner.currentSpawnRate), 1200 - width, 675 + yOffset);
        font.draw(Lildrak.spriteBatch, String.format("platform_spawn: %.2f", spawner.currentSpawnRate), 1200 - width, 650 + yOffset);
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
