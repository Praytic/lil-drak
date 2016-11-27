package com.mygdx.lildrak;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.lildrak.entity.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mygdx.lildrak.CustomApplicationAdapter.spriteBatch;

@Component
public class GameScreen extends ScreenAdapter {

    public static int score;
    public static int playerHealth;
    EntityFactory entityFactory;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    @Autowired
    private Hud hud;
    @Autowired
    private Game game;
    @Autowired
    private World world;
    Entity player;
    Room room;
    @Autowired
    private Spawner spawner;
    @Autowired
    private StartScreenAdapter startScreen;
    @Autowired
    private Engine engine;
    private boolean isCreated = false;

    public void initialize() {
        if (isCreated) {
            spawner.init();
            player = entityFactory.createBat(2f, 1f);
            score = 0;
            playerHealth = Constants.PLAYER_HEALTH;
            return;
        }
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        debugRenderer = new Box2DDebugRenderer();
        entityFactory = new EntityFactory();
        room = new Room(world);

        spawner.init();
        player = entityFactory.createBat(2f, 1f);
        score = 0;
        playerHealth = Constants.PLAYER_HEALTH;

        isCreated = true;
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);

        spriteBatch.begin();
        engine.update(deltaTime);
        spriteBatch.end();

        hud.draw();

        spawner.run(deltaTime);

        if (playerHealth <= 0 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            endGame();
        }
    }

    private void endGame() {
        if (score > Gdx.app.getPreferences("My Preferences").getInteger("score", 0)) {
            Gdx.app.getPreferences("My Preferences").putInteger("score", score);
            Gdx.app.getPreferences("My Preferences").flush();
        }
        Gdx.app.getPreferences("My Preferences").putInteger("lastScore", score);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(startScreen);
            }
        }, 0.5f);
    }

    public void show() {
        initialize();
    }

    public void hide() {
        engine.removeAllEntities();
    }
}
