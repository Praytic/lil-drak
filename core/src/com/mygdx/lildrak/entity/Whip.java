package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.component.*;

public class Whip extends Entity {

    public Whip(float x, float y, int z, float angle, TextureRegion texture, float limit, int damage, float speed) {

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox((float) texture.getRegionWidth() * Constants.METER_TO_PIXEL / 2,
                           (float) texture.getRegionHeight() * Constants.METER_TO_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        Body body = GameScreen.world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(this);
        body.setLinearVelocity(0, speed);

        rectangle.dispose();

        add(new TransformComponent(x, y, z, angle));
        add(new TextureComponent(texture));
        add(new BodyComponent(body));
        add(new VerticalLimitComponent(limit));
        add(new DamageComponent(damage));
        add(new NameComponent("whip"));
    }
}
