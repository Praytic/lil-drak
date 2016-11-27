package com.mygdx.lildrak;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.entity.EntityFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Spawner {
    EntityFactory entityFactory;
    Preferences prefs;
    int difficultySetting; // from preferences
    int currentLevel = 0;
    float levelChangeDuration = Constants.VIEWPORT_HEIGHT / Constants.SCROLL_SPEED;
    float currentSpawnRate;
    float currentScrollSpeed = Constants.SCROLL_SPEED;
    float colY = -0.4f; // y of collectible spawns(positioned above whip spawns)
    float whipTimer;
    float collectibleTimer;
    float spawnTimer;
    float levelChangeTimer;

    List<Float> gauntlet1a = new ArrayList<Float>(Arrays.asList(2.2f, 3.5f));
    List<Float> gauntlet1b = new ArrayList<Float>(Arrays.asList(0.6f, 1.8f));
    List<Float> gauntlet1c = new ArrayList<Float>(Arrays.asList(0.7f, 3.3f));
    List<Float> gauntlet1d = new ArrayList<Float>(Arrays.asList(3.3f));
    List<Float> gauntlet1e = new ArrayList<Float>(Arrays.asList(0.7f));
    List<Float> gauntlet1f = new ArrayList<Float>(Arrays.asList(2.0f));
    List<Float> gauntlet2a = new ArrayList<Float>(Arrays.asList(0.5f, 2.8f, 4.1f));
    List<Float> gauntlet2b = new ArrayList<Float>(Arrays.asList(-0.1f, 2.0f, 3.3f));
    List<Float> gauntlet2c = new ArrayList<Float>(Arrays.asList(-0.1f, 1.2f, 3.5f));
    List<Float> gauntlet2d = new ArrayList<Float>(Arrays.asList(0.7f, 2.0f, 4.1f));
    List<Float> gauntlet2e = new ArrayList<Float>(Arrays.asList(2.0f));
    List<Float> gauntlet2f = new ArrayList<Float>(Arrays.asList(2.4f, 3.7f));
    List<Float> gauntlet2g = new ArrayList<Float>(Arrays.asList(1.3f, 2.6f));
    List<Float> gauntlet2i = new ArrayList<Float>(Arrays.asList(0f, 2.0f, 4.1f));
    List<Float> gauntlet3a = new ArrayList<Float>(Arrays.asList(1.0f, 2.4f));
    List<Float> gauntlet3b = new ArrayList<Float>(Arrays.asList(1.5f, 2.9f));
    List<Float> gauntlet3c = new ArrayList<Float>(Arrays.asList(1.2f, 3.3f));
    List<Float> gauntlet3d = new ArrayList<Float>(Arrays.asList(0.7f, 2.7f));
    List<Float> gauntlet3e = new ArrayList<Float>(Arrays.asList(-0.5f, 1.6f, 2.9f));
    List<Float> gauntlet3f = new ArrayList<Float>(Arrays.asList(1.0f, 2.4f, 4.5f));

    List<Float> lastGauntlet;
    List<Float> currentGauntlet;

    List<List<Float>> gauntletList = new ArrayList<List<Float>>
            (Arrays.asList(gauntlet1a, gauntlet1a, gauntlet1b, gauntlet1b,
                    gauntlet1c, gauntlet1c, gauntlet1d, gauntlet1d,
                    gauntlet1e, gauntlet1e, gauntlet1f, gauntlet1f,
                    gauntlet2a, gauntlet2a, gauntlet2b, gauntlet2b,
                    gauntlet2c, gauntlet2c, gauntlet2d, gauntlet2d,
                    gauntlet2e, gauntlet2e, gauntlet2g, gauntlet2f,
                    gauntlet2i, gauntlet3a, gauntlet3b, gauntlet3c,
                    gauntlet3d, gauntlet3e, gauntlet3f));
    int streakCounter;
    int maxStreak = 2;

    List<com.badlogic.gdx.physics.box2d.Body> windowBodies = new ArrayList<com.badlogic.gdx.physics.box2d.Body>();

    enum STATE {SPAWN, LEVEL_CHANGE}

    static STATE currentState;

    public Spawner(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
        prefs = Gdx.app.getPreferences("My Preferences");
    }

    public void init() {
        difficultySetting = prefs.getInteger("difficulty", 0);
        // EASY:2.5 MEDIUM:2.0 HARD:1.5
        currentSpawnRate = Constants.SPAWN_RATE - Constants.SPAWN_RATE_DIFFICULTY_INCREMENT * difficultySetting;
        currentScrollSpeed = Constants.SCROLL_SPEED;
        whipTimer = currentSpawnRate;
        collectibleTimer = Constants.COLLECTIBLE_SPAWN_RATE;
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
        currentScrollSpeed *= Constants.SCROLL_SPEED_MODIFIER;
        levelChangeDuration = Constants.VIEWPORT_HEIGHT / currentScrollSpeed + 2f;
        for (com.badlogic.gdx.physics.box2d.Body e : windowBodies) {
            e.setLinearVelocity(0, currentScrollSpeed);
        }
        spawnSkullMaybe();

        currentLevel++;
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
            // spawn collectibles with a collectibleChance and increase likelihood of spawn by difficulty
            // highest difficulty(3) has 100 % spawn chance
            float rand = Lildrak.random.nextInt((int) (1 / Constants.COLLECTIBLE_CHANCE));
            rand += currentLevel;
            if (rand >= 1) {
                float x = Lildrak.random.nextFloat() * (Constants.VIEWPORT_WIDTH - 0.5f) + 0.25f;
                spawnRandomCollectible(x);
            }
            rand = Lildrak.random.nextInt((int) (1 / Constants.COLLECTIBLE_CHANCE));
            rand += currentLevel;
            if (rand >= 1) {
                float x = Lildrak.random.nextFloat() * (Constants.VIEWPORT_WIDTH - 0.5f) + 0.25f;
                spawnRandomCollectible(x);
            }
            collectibleTimer = Constants.COLLECTIBLE_SPAWN_RATE;
        }
    }

    private void spawnRandomGauntlet() {
        currentGauntlet = gauntletList.get(Lildrak.random.nextInt(gauntletList.size()));
        // Catch streaks greater than 2 and reroll
        if (currentGauntlet.equals(lastGauntlet)) streakCounter++;
        if (streakCounter >= maxStreak) {
            currentGauntlet = gauntletList.get(Lildrak.random.nextInt(gauntletList.size()));
            streakCounter = 0;
        }

        spawnGauntlet(currentGauntlet);
        lastGauntlet = currentGauntlet; // save last spawned gauntlet
    }

    private void spawnGauntlet(List<Float> gauntlet) {
        for (Float e : gauntlet) {
            GameScreen.engine.addEntity(entityFactory.createWhip(e, -1f, currentScrollSpeed));
        }
    }

    private void spawnRandomCollectible(float x) {
        float offset = Lildrak.random.nextInt(3) / 10.0f - 0.1f;
        Random random = new Random();
        int randomInt = random.nextInt(100);
        if (randomInt < 10 && currentLevel > 1) {
            if (randomInt < 3) {
                entityFactory.createLargeBonus(x, colY + offset, currentScrollSpeed);
            }
            else {
                entityFactory.createSmallLoss(x, colY + offset, currentScrollSpeed);
            }
        }
        else if (randomInt < 20) {
            entityFactory.createMediumBonus(x, colY + offset, currentScrollSpeed);
        }
        else if (randomInt < 40) {
            entityFactory.createSmallBonus(x, colY + offset, currentScrollSpeed);
        }
    }

    private void spawnSkullMaybe() {
        int rand = Lildrak.random.nextInt(2);
        if (rand >= 1) entityFactory.createSkull(5f, 0.5f, -currentScrollSpeed);
    }
}
