package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.BodyComponent;
import com.mygdx.lildrak.entity.components.Transform;

public class PhysicsSystem extends IteratingSystem
{
    public PhysicsSystem(int priority)
    {
        super(Family.all(BodyComponent.class, Transform.class).get(), priority);
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        Transform tc = Mappers.transform.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        Vector2 position = bc.body.getPosition();
        tc.x = position.x / Constants.METER_TO_PIXEL;
        tc.y = position.y / Constants.METER_TO_PIXEL;
        // body angle is in rads, so it needs to be converted to degrees
        float angle = bc.body.getAngle() / Constants.DEGTORAD;
        tc.angle = angle;
    }
}
