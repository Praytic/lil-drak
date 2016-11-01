package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.BodyComponent;
import com.mygdx.lildrak.entity.components.HorizontalLimit;

public class HorizontalBorderSystem extends IteratingSystem
{
    public HorizontalBorderSystem()
    {
        super(Family.all(HorizontalLimit.class, BodyComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        HorizontalLimit lc = Mappers.horizontalLimit.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        if (bc.body.getPosition().x < lc.limit)
        {
            GameScreen.engine.removeEntity(entity);
        }
    }
}
