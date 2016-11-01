package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.BodyComponent;
import com.mygdx.lildrak.entity.components.VerticalLimit;

public class VerticalBorderSystem extends IteratingSystem
{
    public VerticalBorderSystem()
    {
        super(Family.all(VerticalLimit.class, BodyComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        VerticalLimit lc = Mappers.verticalLimit.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        if (bc.body.getPosition().y > lc.limit)
        {
            GameScreen.engine.removeEntity(entity);
        }
    }
}
