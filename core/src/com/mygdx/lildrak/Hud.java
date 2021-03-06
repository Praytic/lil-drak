package com.mygdx.lildrak;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mygdx.lildrak.CustomApplicationAdapter.spriteBatch;

@Component
public class Hud {

    private int lives;
    private int realScore = 0;
    private int currentScore = 0;
    private int scoreUpdateSpeedLow = 22;
    private int scoreUpdateSpeedMed = 88;
    private int scoreUpdateSpeedHigh = 773;
    private int scoreUpdateSpeed = scoreUpdateSpeedLow; // updates per frame

    private float scale = 0;
    private float yOffset = 0;
    private int juiceJump = 50;
    private int juiceScale = 10000;

    @Autowired
    private Spawner spawner;
    @Autowired
    private StartScreenAdapter startScreenAdapter;
    @Autowired
    private AssetManager assetManager;

    public void draw() {
        BitmapFont font = assetManager.get(Asset.Font.DEFAULT.getFileName());
        font.getData().setScale(0.5f, 0.5f);
        Texture heartTexture = assetManager.get(Asset.Image.HEART.getFileName());
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

        spriteBatch.begin();

        font.draw(spriteBatch, String.format("current_score: %d", currentScore), 1200 - width, 900 + yOffset);
        font.draw(spriteBatch, String.format("highest_score: %d", startScreenAdapter.getHighScore()), 1200 - width, 875 + yOffset);
        font.draw(spriteBatch, String.format("level: %d", spawner.getCurrentLevel()), 1200 - width, 850 + yOffset);
        font.draw(spriteBatch, String.format("speed: %.2f", spawner.getCurrentScrollSpeed()), 1200 - width, 825 + yOffset);
        font.draw(spriteBatch, "------ rate ------", 1200 - width, 775 + yOffset);
        font.draw(spriteBatch, String.format("candy_spawn: %.2f", 1 / (spawner.getCandySpawnRate() / 100f) * spawner.getCurrentSpawnRate()), 1200 - width, 750 + yOffset);
        font.draw(spriteBatch, String.format("lollipop_spawn: %.2f", 1 / (spawner.getLollipopSpawnRate() / 100f) * spawner.getCurrentSpawnRate()), 1200 - width, 725 + yOffset);
        font.draw(spriteBatch, String.format("money_spawn: %.2f", 1 / (spawner.getMoneySpawnRate() / 100f) * spawner.getCurrentSpawnRate()), 1200 - width, 700 + yOffset);
        font.draw(spriteBatch, String.format("flame_spawn: %.2f", 1 / (spawner.getFlameSpawnRate() / 100f) * spawner.getCurrentSpawnRate()), 1200 - width, 675 + yOffset);
        font.draw(spriteBatch, String.format("platform_spawn: %.2f", spawner.getCurrentSpawnRate()), 1200 - width, 650 + yOffset);
        for (int i = 0; i < lives; i++) {
            spriteBatch.draw(heartTexture, 10 + i * 70, 870);
        }
        spriteBatch.end();
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
