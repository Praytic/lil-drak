package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.OriginalPositionComponent;

public class ResetSystem extends IteratingSystem
{
    private float verticalLimit = 8f;

    public ResetSystem()
    {
        super(Family.all(OriginalPositionComponent.class, BodyComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        OriginalPositionComponent oc = Mappers.originalPosition.get(entity);
        BodyComponent bc = Mappers.bodyComponent.get(entity);

        if (bc.getBody().getPosition().y > verticalLimit + oc.y)
        {
            bc.getBody().setTransform(oc.x, oc.y, bc.getBody().getAngle());
        }
    }
}
