package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.PlayerMovementComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.stereotype.Component;

@Component
public class InputSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(BodyComponent.class, PlayerMovementComponent.class).get());
    }

    public void moveLeft() {
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            BodyComponent bc = Mappers.bodyComponent.get(e);
            PlayerMovementComponent pc = Mappers.playerMovement.get(e);

            bc.getBody().applyForceToCenter(-pc.moveForce / 4, pc.moveForce / 2, true);
        }
    }

    public void moveUp() {
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            BodyComponent bc = Mappers.bodyComponent.get(e);
            PlayerMovementComponent pc = Mappers.playerMovement.get(e);

            bc.getBody().applyForceToCenter(0, pc.moveForce, true);
        }
    }

    public void moveRight() {
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            BodyComponent bc = Mappers.bodyComponent.get(e);
            PlayerMovementComponent pc = Mappers.playerMovement.get(e);

            bc.getBody().applyForceToCenter(pc.moveForce / 4, pc.moveForce / 2, true);
        }
    }
}
