package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.BodyComponent;
import com.mygdx.lildrak.entity.components.OriginalPosition;

public class ResetSystem extends IteratingSystem
{
    private float verticalLimit = 8f;

    public ResetSystem()
    {
        super(Family.all(OriginalPosition.class, BodyComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        OriginalPosition oc = Mappers.originalPosition.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        if (bc.body.getPosition().y > verticalLimit + oc.y)
        {
            bc.body.setTransform(oc.x, oc.y, bc.body.getAngle());
        }
    }
}
