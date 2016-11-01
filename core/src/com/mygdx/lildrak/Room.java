package com.mygdx.lildrak;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Room
{
    World box2dWorld;
    Body groundBody;
    Body ceilingBody;
    Body leftWallBody;
    Body rightWallBody;

    public Room(World box2dWorld)
    {
        this.box2dWorld= box2dWorld;
        groundBody = createHorizontalBorder(0, 0);
        ceilingBody = createHorizontalBorder(0, Constants.VIEWPORT_HEIGHT);
        leftWallBody = createVerticalBorder(0, 0);
        rightWallBody = createVerticalBorder(Constants.VIEWPORT_WIDTH, 0);
    }

    private Body createHorizontalBorder(float x, float y)
    {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Body body;

        shape.setAsBox(Constants.VIEWPORT_WIDTH, 0);
        bodyDef.position.set(x, y);
        body = box2dWorld.createBody(bodyDef);
        body.createFixture(shape, 0.0f);

        shape.dispose();
        return body;
    }

    private Body createVerticalBorder(float x, float y)
    {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Body body;

        shape.setAsBox(0, Constants.VIEWPORT_HEIGHT);
        bodyDef.position.set(x, y);
        body = box2dWorld.createBody(bodyDef);
        body.createFixture(shape, 0.0f);

        shape.dispose();
        return body;
    }
}
