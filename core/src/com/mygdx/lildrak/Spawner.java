package com.mygdx.lildrak;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.entity.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

@Component
public class Spawner {

    @Autowired
    private EntityFactory entityFactory;
    @Autowired
    private Engine engine;

    private Preferences prefs;
    private int difficultySetting; // from preferences
    private int currentLevel = 0;
    private float levelChangeDuration = Constants.VIEWPORT_HEIGHT / Constants.SCROLL_SPEED;
    private float currentSpawnRate;
    private float currentScrollSpeed = Constants.SCROLL_SPEED;
    private float colY = -0.4f; // y of collectible spawns(positioned above whip spawns)
    private float whipTimer;
    private float collectibleTimer;
    private float spawnTimer;
    private float levelChangeTimer;
    private float candySpawnRate;
    private float lollipopSpawnRate;
    private float moneySpawnRate;
    private float flameSpawnRate;

    private List<Float> gauntlet1a = new ArrayList<Float>(Arrays.asList(2.2f, 3.5f));
    private List<Float> gauntlet1b = new ArrayList<Float>(Arrays.asList(0.6f, 1.8f));
    private List<Float> gauntlet1c = new ArrayList<Float>(Arrays.asList(0.7f, 3.3f));
    private List<Float> gauntlet1d = new ArrayList<Float>(Arrays.asList(3.3f));
    private List<Float> gauntlet1e = new ArrayList<Float>(Arrays.asList(0.7f));
    private List<Float> gauntlet1f = new ArrayList<Float>(Arrays.asList(2.0f));
    private List<Float> gauntlet2a = new ArrayList<Float>(Arrays.asList(0.5f, 2.8f, 4.1f));
    private List<Float> gauntlet2b = new ArrayList<Float>(Arrays.asList(-0.1f, 2.0f, 3.3f));
    private List<Float> gauntlet2c = new ArrayList<Float>(Arrays.asList(-0.1f, 1.2f, 3.5f));
    private List<Float> gauntlet2d = new ArrayList<Float>(Arrays.asList(0.7f, 2.0f, 4.1f));
    private List<Float> gauntlet2e = new ArrayList<Float>(Arrays.asList(2.0f));
    private List<Float> gauntlet2f = new ArrayList<Float>(Arrays.asList(2.4f, 3.7f));
    private List<Float> gauntlet2g = new ArrayList<Float>(Arrays.asList(1.3f, 2.6f));
    private List<Float> gauntlet2i = new ArrayList<Float>(Arrays.asList(0f, 2.0f, 4.1f));
    private List<Float> gauntlet3a = new ArrayList<Float>(Arrays.asList(1.0f, 2.4f));
    private List<Float> gauntlet3b = new ArrayList<Float>(Arrays.asList(1.5f, 2.9f));
    private List<Float> gauntlet3c = new ArrayList<Float>(Arrays.asList(1.2f, 3.3f));
    private List<Float> gauntlet3d = new ArrayList<Float>(Arrays.asList(0.7f, 2.7f));
    private List<Float> gauntlet3e = new ArrayList<Float>(Arrays.asList(-0.5f, 1.6f, 2.9f));
    private List<Float> gauntlet3f = new ArrayList<Float>(Arrays.asList(1.0f, 2.4f, 4.5f));

    private List<Float> lastGauntlet;
    private List<Float> currentGauntlet;

    private List<List<Float>> gauntletList = new ArrayList<List<Float>>
            (Arrays.asList(gauntlet1a, gauntlet1a, gauntlet1b, gauntlet1b,
                    gauntlet1c, gauntlet1c, gauntlet1d, gauntlet1d,
                    gauntlet1e, gauntlet1e, gauntlet1f, gauntlet1f,
                    gauntlet2a, gauntlet2a, gauntlet2b, gauntlet2b,
                    gauntlet2c, gauntlet2c, gauntlet2d, gauntlet2d,
                    gauntlet2e, gauntlet2e, gauntlet2g, gauntlet2f,
                    gauntlet2i, gauntlet3a, gauntlet3b, gauntlet3c,
                    gauntlet3d, gauntlet3e, gauntlet3f));
    private int streakCounter;
    private int maxStreak = 2;

    private List<com.badlogic.gdx.physics.box2d.Body> windowBodies = new ArrayList<com.badlogic.gdx.physics.box2d.Body>();

    public float getCurrentSpawnRate() {
        return currentSpawnRate;
    }

    public float getCurrentScrollSpeed() {
        return currentScrollSpeed;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    private enum STATE {SPAWN, LEVEL_CHANGE}

    private STATE currentState;

    public void init() {
        prefs = Gdx.app.getPreferences("My Preferences");
        difficultySetting = prefs.getInteger("difficulty", 0);
        currentSpawnRate = Constants.SPAWN_RATE - Constants.SPAWN_RATE_DIFFICULTY_INCREASE * difficultySetting;
        currentScrollSpeed = Constants.SCROLL_SPEED;
        whipTimer = currentSpawnRate;
        collectibleTimer = getCollectibleSpawnRate();
        spawnTimer = Constants.LEVEL_TIME;
        levelChangeTimer = Constants.VIEWPORT_HEIGHT / Constants.SCROLL_SPEED;
        currentLevel = 0;

        windowBodies.clear(); // CLEAR THE GODDAMNED WINDOW BODIES ARRAY
        Array<Entity> windows = new Array<Entity>();
        windows.add(entityFactory.createWindow(0.45f, -1f, 0.45f, -1f, Constants.SCROLL_SPEED));
        windows.add(entityFactory.createWindow(2f, -3f, 2f, -3f, Constants.SCROLL_SPEED));
        windows.add(entityFactory.createWindow(3.55f, -1f, 3.55f, -1f, Constants.SCROLL_SPEED));
        // bottom windows need to spawn lower, but reset to the same y position as top windows
        // to make a scrolling effect
        windows.add(entityFactory.createWindow(0.45f, -5f, 0.45f, -1f, Constants.SCROLL_SPEED));
        windows.add(entityFactory.createWindow(2f, -7f, 2f, -3f, Constants.SCROLL_SPEED));
        windows.add(entityFactory.createWindow(3.55f, -5f, 3.55f, -1f, Constants.SCROLL_SPEED));
        for (Entity e : windows) {
            ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
            BodyComponent bc = bm.get(e);
            windowBodies.add(bc.getBody());
        }

        currentState = STATE.SPAWN;
    }

    public float getCollectibleSpawnRate() {
        return Constants.COLLECTIBLE_SPAWN_RATE / (currentLevel == 0 ? 1 : currentLevel);
    }

    public void run(float deltaTime) {
        if (currentState == STATE.SPAWN) {
            spawnTimer -= deltaTime;
            if (spawnTimer <= 0) {
                currentState = STATE.LEVEL_CHANGE;
                spawnTimer = Constants.LEVEL_TIME;
            }
            spawnWhips(deltaTime);
            spawnCollectibles(deltaTime);
        } else if (currentState == STATE.LEVEL_CHANGE) {
            levelChangeTimer -= deltaTime;
            if (levelChangeTimer <= 0) {
                currentState = STATE.SPAWN;
                if (currentLevel < Constants.MAX_LEVEL) changeLevel();
                levelChangeTimer = Constants.VIEWPORT_HEIGHT / Constants.SCROLL_SPEED;
            }
        }
    }

    private void changeLevel() {
        currentSpawnRate -= Constants.SPAWN_RATE_MODIFIER;
        currentScrollSpeed += Constants.SCROLL_SPEED_INCREASE;
        levelChangeDuration = Constants.VIEWPORT_HEIGHT / currentScrollSpeed + 2f;
        for (com.badlogic.gdx.physics.box2d.Body e : windowBodies) {
            e.setLinearVelocity(0, currentScrollSpeed);
        }
        spawnSkullMaybe();

        currentLevel++;
        Gdx.app.debug(LoggerTag.LEVEL_UP.toString(), String.valueOf(currentLevel));
        Gdx.app.debug(LoggerTag.COLLECTIBLE_SPAWN_RATE_UP.toString(), String.valueOf(getCollectibleSpawnRate()));
    }

    private void spawnWhips(float deltaTime) {
        if (whipTimer > 0) whipTimer -= deltaTime;
        else {
            spawnRandomGauntlet();
            whipTimer = currentSpawnRate;
        }
    }

    private void spawnCollectibles(float deltaTime) {
        if (collectibleTimer > 0) collectibleTimer -= deltaTime;
        else {
            spawnRandomCollectible(random.nextFloat() * (Constants.VIEWPORT_WIDTH - 0.5f) + 0.25f);
            collectibleTimer = Constants.COLLECTIBLE_SPAWN_RATE;
        }
    }

    private void spawnRandomGauntlet() {
        currentGauntlet = gauntletList.get(random.nextInt(gauntletList.size()));
        // Catch streaks greater than 2 and reroll
        if (currentGauntlet.equals(lastGauntlet)) streakCounter++;
        if (streakCounter >= maxStreak) {
            currentGauntlet = gauntletList.get(random.nextInt(gauntletList.size()));
            streakCounter = 0;
        }

        spawnGauntlet(currentGauntlet);
        lastGauntlet = currentGauntlet; // save last spawned gauntlet
    }

    private void spawnGauntlet(List<Float> gauntlet) {
        for (Float e : gauntlet) {
            engine.addEntity(entityFactory.createWhip(e, -1f, currentScrollSpeed));
        }
    }

    private void spawnRandomCollectible(float x) {
        float offset = random.nextInt(3) / 10.0f - 0.1f;
        float sum = Constants.COLLECTIBLE_CANDY_SPAWN_RATE + Constants.COLLECTIBLE_FLAME_SPAWN_RATE +
                Constants.COLLECTIBLE_LOLLIPOP_SPAWN_RATE + Constants.COLLECTIBLE_MONEY_SPAWN_RATE;
        candySpawnRate = Constants.COLLECTIBLE_CANDY_SPAWN_RATE * (100 / sum);
        flameSpawnRate = Constants.COLLECTIBLE_FLAME_SPAWN_RATE * (100 / sum);
        moneySpawnRate = Constants.COLLECTIBLE_MONEY_SPAWN_RATE * (100 / sum);
        lollipopSpawnRate = Constants.COLLECTIBLE_LOLLIPOP_SPAWN_RATE * (100 / sum);
        float randomNumber = (float) random.nextInt(100);
        if (0 <= randomNumber && randomNumber < moneySpawnRate && currentLevel > 1) {
            entityFactory.createLargeBonus(x, colY + offset, currentScrollSpeed);
        }
        else if (moneySpawnRate <= randomNumber && randomNumber < moneySpawnRate + flameSpawnRate && currentLevel > 1) {
            entityFactory.createSmallLoss(x, colY + offset, currentScrollSpeed);
        }
        else if (moneySpawnRate + flameSpawnRate <= randomNumber && randomNumber < moneySpawnRate + flameSpawnRate + lollipopSpawnRate) {
            entityFactory.createMediumBonus(x, colY + offset, currentScrollSpeed);
        }
        else if (moneySpawnRate + flameSpawnRate + lollipopSpawnRate <= randomNumber && randomNumber < 100) {
            entityFactory.createSmallBonus(x, colY + offset, currentScrollSpeed);
        }
    }

    private void spawnSkullMaybe() {
        int rand = random.nextInt(2);
        if (rand >= 1) entityFactory.createSkull(5f, 0.5f, -currentScrollSpeed);
    }

    public float getCandySpawnRate() {
        return candySpawnRate;
    }

    public float getLollipopSpawnRate() {
        return lollipopSpawnRate;
    }

    public float getMoneySpawnRate() {
        return moneySpawnRate;
    }

    public float getFlameSpawnRate() {
        return flameSpawnRate;
    }

}
