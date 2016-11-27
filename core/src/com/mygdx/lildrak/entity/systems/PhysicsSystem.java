package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.lildrak.Constants;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.TransformComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhysicsSystem extends IteratingSystem {

    @Autowired
    public PhysicsSystem(Engine engine) {
        super(Family.all(BodyComponent.class, TransformComponent.class).get(), 0);
        engine.addSystem(this);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = Mappers.transform.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        Vector2 position = bc.getBody().getPosition();
        tc.x = position.x / Constants.METER_TO_PIXEL;
        tc.y = position.y / Constants.METER_TO_PIXEL;
        // body angle is in rads, so it needs to be converted to degrees
        float angle = bc.getBody().getAngle() / Constants.DEG_TO_RAD;
        tc.angle = angle;
    }
}
