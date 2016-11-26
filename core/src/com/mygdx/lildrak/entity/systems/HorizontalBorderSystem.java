package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.HorizontalLimitComponent;

public class HorizontalBorderSystem extends IteratingSystem
{
    public HorizontalBorderSystem()
    {
        super(Family.all(HorizontalLimitComponent.class, BodyComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        HorizontalLimitComponent lc = Mappers.horizontalLimit.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        if (bc.getBody().getPosition().x < lc.limit)
        {
            GameScreen.engine.removeEntity(entity);
        }
    }
}
