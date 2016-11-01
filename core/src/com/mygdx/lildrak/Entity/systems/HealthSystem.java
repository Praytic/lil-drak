package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.Health;

public class HealthSystem extends IteratingSystem
{
    public HealthSystem()
    {
        super(Family.all(Health.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        Health hc = Mappers.health.get(entity);

        if (hc.health <= 0) GameScreen.engine.removeEntity(entity);
    }
}
