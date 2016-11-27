package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.OriginalPositionComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResetSystem extends IteratingSystem {

    @Autowired
    public ResetSystem(Engine engine) {
        super(Family.all(OriginalPositionComponent.class, BodyComponent.class).get());
        engine.addSystem(this);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        OriginalPositionComponent oc = Mappers.originalPosition.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);
        if (bc.getBody().getPosition().y > 8f + oc.y) {
            bc.getBody().setTransform(oc.x, oc.y, bc.getBody().getAngle());
        }
    }
}
