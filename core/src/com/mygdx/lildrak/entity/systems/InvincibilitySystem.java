package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.component.InvincibilityComponent;

public class InvincibilitySystem extends IteratingSystem
{
    public InvincibilitySystem()
    {
        super(Family.all(InvincibilityComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        InvincibilityComponent ic = Mappers.invincibility.get(entity);
        if (ic.counter > 0)
        {
            ic.counter -= deltaTime;
            if (ic.counter < 0) ic.counter = 0;
        }
    }
}
