package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.component.InvincibilityComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvincibilitySystem extends IteratingSystem {

    @Autowired
    public InvincibilitySystem(Engine engine) {
        super(Family.all(InvincibilityComponent.class).get());
        engine.addSystem(this);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        InvincibilityComponent ic = Mappers.invincibility.get(entity);
        if (ic.counter > 0) {
            ic.counter -= deltaTime;
            if (ic.counter < 0) ic.counter = 0;
        }
    }
}
