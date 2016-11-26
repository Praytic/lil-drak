package com.mygdx.lildrak;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.entity.BodyEntityListener;
import com.mygdx.lildrak.entity.EntityFactory;
import com.mygdx.lildrak.entity.MyInputAdapter;
import com.mygdx.lildrak.entity.systems.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameScreen extends ScreenAdapter {

    public static Engine engine;
    public static World world;
    public static int score;
    public static int playerHealth;
    EntityFactory entityFactory;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    MyInputAdapter inputAdapter;
    Hud hud;
    Entity player;
    Room room;
    Spawner spawner;
    Preferences prefs;
    @Autowired
    private StartScreenAdapter startScreen;
    @Autowired
    private Lildrak lildrak;

    public void initialize() {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        debugRenderer = new Box2DDebugRenderer();
        world = Lildrak.world;
        world.setGravity(new Vector2(0f, Constants.WORLD_GRAVITY));
        entityFactory = new EntityFactory();
        spawner = new Spawner(entityFactory);
        room = new Room(world);
        prefs = Gdx.app.getPreferences("My Preferences");

        engine = Lildrak.engine;
        engine.addSystem(new PhysicsSystem(0)); // transform must be updated before rendering
        engine.addSystem(new RenderSystem(1));
        engine.addSystem(new ResetSystem());
        engine.addSystem(new BatAnimationSystem());
        engine.addSystem(new VerticalBorderSystem());
        engine.addSystem(new HorizontalBorderSystem());
        engine.addSystem(new HealthSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new InvincibilitySystem());

        InputSystem inputSystem = new InputSystem();
        this.inputAdapter = new MyInputAdapter(inputSystem);
        engine.addSystem(inputSystem);
        Gdx.input.setInputProcessor(inputAdapter);

        BodyEntityListener bodyRemoval = new BodyEntityListener();
        engine.addEntityListener(Family.all(BodyComponent.class).get(), bodyRemoval);

        spawner.init();
        player = entityFactory.createBat(2f, 1f);
        hud = new Hud();
        score = 0;
        playerHealth = Constants.PLAYER_HEALTH;
        Gdx.input.setInputProcessor(inputAdapter);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);

        Lildrak.spriteBatch.begin();
        engine.update(deltaTime);
        Lildrak.spriteBatch.end();

        hud.draw();

        spawner.run(deltaTime);

        if (playerHealth <= 0 || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) endGame();
    }

    private void endGame() {
        Gdx.input.setInputProcessor(null);
        if (score > prefs.getInteger("score", 0)) {
            prefs.putInteger("score", score);
            prefs.flush();
        }
        prefs.putInteger("lastScore", score);

        pause();
        // Change the screen immediately, if player restarted the game
        // Add a delay, if player died
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) lildrak.game.setScreen(startScreen);
        else Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                lildrak.game.setScreen(startScreen);
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
