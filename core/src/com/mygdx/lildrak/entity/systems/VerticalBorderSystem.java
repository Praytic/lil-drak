package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.VerticalLimitComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerticalBorderSystem extends IteratingSystem {

    private final Engine engine;

    @Autowired
    public VerticalBorderSystem(Engine engine) {
        super(Family.all(VerticalLimitComponent.class, BodyComponent.class).get());
        engine.addSystem(this);
        this.engine = engine;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        VerticalLimitComponent lc = Mappers.verticalLimit.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        if (bc.getBody().getPosition().y > lc.limit) {
            engine.removeEntity(entity);
        }
    }
}
