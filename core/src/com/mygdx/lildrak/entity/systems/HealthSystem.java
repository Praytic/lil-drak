package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.component.HealthComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthSystem extends IteratingSystem {

    private final Engine engine;

    @Autowired
    public HealthSystem(Engine engine) {
        super(Family.all(HealthComponent.class).get());
        engine.addSystem(this);
        this.engine = engine;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Mappers.health.get(entity).health <= 0) {
            engine.removeEntity(entity);
        }
    }
}
