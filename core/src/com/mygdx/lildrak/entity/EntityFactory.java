package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.lildrak.Asset;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.Lildrak;
import com.mygdx.lildrak.component.*;
import org.springframework.stereotype.Component;

import static com.mygdx.lildrak.Constants.WHIP_Z;

@Component
public class EntityFactory {

    public Entity createWhip(float x, float y, float ySpeed) {
        Texture texture = Lildrak.ASSETS.get(Asset.Image.WHIP.getFileName());
        TextureRegion region = new TextureRegion(texture);
        return new Whip(x, y, WHIP_Z, 0, region, Constants.VIEWPORT_HEIGHT + 1f, 1, ySpeed);
    }

    public Entity createSmallBonus(float x, float y, float ySpeed) {
        return createCollectible(x, y, ySpeed, 1, CollectibleType.CANDY);
    }

    public Entity createMediumBonus(float x, float y, float ySpeed) {
        return createCollectible(x, y, ySpeed, 3, CollectibleType.LOLLIPOP);
    }

    public Entity createLargeBonus(float x, float y, float ySpeed) {
        return createCollectible(x, y, ySpeed, 5, CollectibleType.MONEY);
    }

    public Entity createSmallLoss(float x, float y, float ySpeed) {
        return createCollectible(x, y, ySpeed, 0, CollectibleType.FLAME);
    }

    public Entity createCollectible(float x, float y, float ySpeed, int value, CollectibleType collectibleType) {
        Entity entity = new Entity();

        float width = collectibleType.getWidth() * Constants.METER_TO_PIXEL;
        float height = collectibleType.getHeight() * Constants.METER_TO_PIXEL;
        if (collectibleType == CollectibleType.FLAME) {
            entity.add(new FlameAnimationComponent(
                    collectibleType.getTextures().get(0), collectibleType.getTextures().get(1)))
                    .add(new DamageComponent(1));
        }
        Texture texture = collectibleType.getTextures().get(0);
        TextureRegion region = new TextureRegion(texture);

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

        Sound hurtSound = Lildrak.ASSETS.get(Asset.Sound.PICKUP.getFileName());


        entity.add(new TransformComponent(x, y, Constants.COLLECTIBLE_Z, 0))
                .add(new BodyComponent(body))
                .add(new VerticalLimitComponent(Constants.VIEWPORT_HEIGHT + 1f))
                .add(new HealthComponent(1))
                .add(new ScoreComponent(value))
                .add(new HurtSoundComponent(hurtSound))
                .add(new TextureComponent(region))
                .add(new NameComponent(collectibleType.getName()));
        GameScreen.engine.addEntity(entity);
        return entity;
    }

    public Entity createWindow(float x, float y, float originalX, float originalY, float ySpeed) {
        Entity entity = new Entity();

        Texture texture = Lildrak.ASSETS.get(Asset.Image.WINDOW.getFileName());
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

        Texture texture = Lildrak.ASSETS.get(Asset.Image.SKULL.getFileName());
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

        Array<Texture> frames = new Array<Texture>();
        frames.add(Lildrak.ASSETS.get(Asset.Image.BAT1.getFileName()));
        frames.add(Lildrak.ASSETS.get(Asset.Image.BAT2.getFileName()));
        frames.add(Lildrak.ASSETS.get(Asset.Image.BAT3.getFileName()));
        frames.add(Lildrak.ASSETS.get(Asset.Image.BAT4.getFileName()));
        Texture texture = frames.get(0);
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
        Sound hurtSound = Lildrak.ASSETS.get(Asset.Sound.HURT.getFileName());

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
