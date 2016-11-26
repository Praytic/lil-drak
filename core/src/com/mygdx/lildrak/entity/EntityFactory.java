package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.lildrak.AssetPaths;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.Lildrak;
import com.mygdx.lildrak.component.*;
import com.mygdx.lildrak.component.TransformComponent;

import static com.mygdx.lildrak.Constants.WHIP_Z;

public class EntityFactory {

    public Entity createWhip(float x, float y, float ySpeed) {
        Texture texture = Lildrak.ASSETS.get(AssetPaths.WHIP);
        TextureRegion region = new TextureRegion(texture);
        return new Whip(x, y, WHIP_Z, 0, region, Constants.VIEWPORT_HEIGHT + 1f, 1, ySpeed);
    }

    public Entity createCandy(float x, float y, float ySpeed) {
        return createCollectible(x, y, ySpeed, AssetPaths.CANDY, 1000);
    }

    public Entity createLollipop(float x, float y, float ySpeed) {
        return createCollectible(x, y, ySpeed, AssetPaths.LOLLIPOP, 2500);
    }

    public Entity createMoney(float x, float y, float ySpeed) {
        return createCollectible(x, y, ySpeed, AssetPaths.MONEY, 10000);
    }

    public Entity createCollectible(float x, float y, float ySpeed, String asset, int value) {
        Entity entity = new Entity();

        Texture texture = Lildrak.ASSETS.get(asset);
        TextureRegion region = new TextureRegion(texture);
        float width = texture.getWidth() * Constants.METER_TO_PIXEL;
        float height = texture.getHeight() * Constants.METER_TO_PIXEL;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width / 2, height / 2); //setAsBox() takes half-width and half-height
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        com.badlogic.gdx.physics.box2d.Body body = GameScreen.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);
        body.setLinearVelocity(0, ySpeed);
        fixture.setUserData(entity);
        rectangle.dispose();

        Sound hurtSound = Lildrak.ASSETS.get(AssetPaths.PICKUP);

        entity.add(new TransformComponent(x, y, Constants.COLLECTIBLE_Z, 0))
                .add(new TextureComponent(region))
                .add(new BodyComponent(body))
                .add(new VerticalLimitComponent(Constants.VIEWPORT_HEIGHT + 1f))
                .add(new HealthComponent(1))
                .add(new ScoreComponent(value))
                .add(new HurtSoundComponent(hurtSound))
                .add(new NameComponent(asset));
        GameScreen.engine.addEntity(entity);
        return entity;
    }

    public Entity createWindow(float x, float y, float originalX, float originalY, float ySpeed) {
        Entity entity = new Entity();

        com.badlogic.gdx.graphics.Texture texture = Lildrak.ASSETS.get(AssetPaths.WINDOW);
        TextureRegion region = new TextureRegion(texture);
        float width = texture.getWidth() * Constants.METER_TO_PIXEL;
        float height = texture.getHeight() * Constants.METER_TO_PIXEL;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width / 2, height / 2); //setAsBox() takes half-width and half-height
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        fixtureDef.filter.groupIndex = -2;
        com.badlogic.gdx.physics.box2d.Body body = GameScreen.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(entity);
        body.setLinearVelocity(0, ySpeed);
        rectangle.dispose();

        entity.add(new TransformComponent(x, y, Constants.WINDOW_Z, 0))
                .add(new OriginalPositionComponent(originalX, originalY))
                .add(new TextureComponent(region))
                .add(new BodyComponent(body))
                .add(new NameComponent("window"));
        GameScreen.engine.addEntity(entity);
        return entity;
    }

    public Entity createSkull(float x, float y, float xSpeed) {
        Entity entity = new Entity();

        com.badlogic.gdx.graphics.Texture texture = Lildrak.ASSETS.get(AssetPaths.SKULL);
        TextureRegion region = new TextureRegion(texture);
        Color color = new Color(1.0f, 1.0f, 1.0f, 0.6f);
        float width = texture.getWidth() * Constants.METER_TO_PIXEL;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        CircleShape circle = new CircleShape();
        circle.setRadius(width / 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 20f;
        fixtureDef.friction = 0f;
        com.badlogic.gdx.physics.box2d.Body body = GameScreen.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(entity);

        body.setLinearVelocity(xSpeed, 0);
        body.setAngularVelocity(0.2f);
        circle.dispose();

        entity.add(new TransformComponent(x, y, Constants.SKULL_Z, 0))
                .add(new TextureComponent(region))
                .add(new ColorComponent(color))
                .add(new BodyComponent(body))
                .add(new DamageComponent(1))
                .add(new HorizontalLimitComponent(-1f))
                .add(new NameComponent("skull"));
        GameScreen.engine.addEntity(entity);
        return entity;
    }

    public Entity createBat(float x, float y) {
        Entity entity = new Entity();

        Array<com.badlogic.gdx.graphics.Texture> frames = new Array<com.badlogic.gdx.graphics.Texture>();
        frames.add((com.badlogic.gdx.graphics.Texture) Lildrak.ASSETS.get(AssetPaths.BAT1));
        frames.add((com.badlogic.gdx.graphics.Texture) Lildrak.ASSETS.get(AssetPaths.BAT2));
        frames.add((com.badlogic.gdx.graphics.Texture) Lildrak.ASSETS.get(AssetPaths.BAT3));
        frames.add((com.badlogic.gdx.graphics.Texture) Lildrak.ASSETS.get(AssetPaths.BAT4));
        com.badlogic.gdx.graphics.Texture texture = frames.get(0);
        TextureRegion region = new TextureRegion(texture);

        float width = texture.getWidth() * Constants.METER_TO_PIXEL;
        float height = texture.getHeight() * Constants.METER_TO_PIXEL;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        CircleShape circle = new CircleShape();
        circle.setRadius(width / 4); //setAsBox() takes half-width and half-height
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        fixtureDef.filter.groupIndex = Constants.BACKGROUND_INDEX;
        com.badlogic.gdx.physics.box2d.Body body = GameScreen.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(entity);
        body.setLinearDamping(1f);
        circle.dispose();
        Sound hurtSound = Lildrak.ASSETS.get(AssetPaths.HURT);

        entity.add(new TransformComponent(x, y, Constants.BAT_Z, 0))
                .add(new TextureComponent(region))
                .add(new BatAnimationComponent(frames))
                .add(new BodyComponent(body))
                .add(new PlayerMovementComponent(Constants.MOVE_FORCE))
                .add(new HealthComponent(Constants.PLAYER_HEALTH))
                .add(new DamageComponent(1))
                .add(new HurtSoundComponent(hurtSound))
                .add(new VerticalLimitComponent(Constants.VIEWPORT_HEIGHT + 0.1f))
                .add(new VerticalLimitComponent(Constants.VIEWPORT_HEIGHT + 0.1f))
                .add(new InvincibilityComponent(1f))
                .add(new NameComponent("bat"));
        GameScreen.engine.addEntity(entity);
        return entity;
    }
}