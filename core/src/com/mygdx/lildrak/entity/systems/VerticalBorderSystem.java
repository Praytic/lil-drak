package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.VerticalLimitComponent;

public class VerticalBorderSystem extends IteratingSystem
{
    public VerticalBorderSystem()
    {
        super(Family.all(VerticalLimitComponent.class, BodyComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        VerticalLimitComponent lc = Mappers.verticalLimit.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        if (bc.getBody().getPosition().y > lc.limit)
        {
            GameScreen.engine.removeEntity(entity);
        }
    }
}
