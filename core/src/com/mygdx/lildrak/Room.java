package com.mygdx.lildrak;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Room {

    private Body groundBody;
    private Body ceilingBody;
    private Body leftWallBody;
    private Body rightWallBody;

    public Room(World box2dWorld) {
        groundBody = createHorizontalBorder(0, 0, box2dWorld);
        ceilingBody = createHorizontalBorder(0, Constants.VIEWPORT_HEIGHT, box2dWorld);
        leftWallBody = createVerticalBorder(0, 0, box2dWorld);
        rightWallBody = createVerticalBorder(Constants.VIEWPORT_WIDTH, 0, box2dWorld);
    }

    private Body createHorizontalBorder(float x, float y, World box2world) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Body body;

        shape.setAsBox(Constants.VIEWPORT_WIDTH, 0);
        bodyDef.position.set(x, y);
        body = box2world.createBody(bodyDef);
        body.createFixture(shape, 0.0f);

        shape.dispose();
        return body;
    }

    private Body createVerticalBorder(float x, float y, World box2world) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Body body;

        shape.setAsBox(0, Constants.VIEWPORT_HEIGHT);
        bodyDef.position.set(x, y);
        body = box2world.createBody(bodyDef);
        body.createFixture(shape, 0.0f);

        shape.dispose();
        return body;
    }
}
