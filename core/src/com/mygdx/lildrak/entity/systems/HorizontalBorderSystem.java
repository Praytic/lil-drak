package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.HorizontalLimitComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HorizontalBorderSystem extends IteratingSystem {

    private final Engine engine;

    @Autowired
    public HorizontalBorderSystem(Engine engine) {
        super(Family.all(HorizontalLimitComponent.class, BodyComponent.class).get());
        engine.addSystem(this);
        this.engine = engine;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Mappers.bodyComponent.get(entity).getBody().getPosition().x < Mappers.horizontalLimit.get(entity).limit) {
            engine.removeEntity(entity);
        }
    }
}
