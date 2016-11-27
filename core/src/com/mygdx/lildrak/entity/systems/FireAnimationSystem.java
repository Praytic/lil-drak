package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.lildrak.component.*;
import com.mygdx.lildrak.entity.Mappers;

public class FireAnimationSystem extends IntervalSystem {

    private ImmutableArray<Entity> entities;

    public FireAnimationSystem() {
        super(0.1f);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(FireAnimationComponent.class).get());

    }

    @Override
    public void updateInterval() {
        for (Entity entity : entities) {
            TextureComponent tc = Mappers.textureComponent.get(entity);
            FireAnimationComponent ac = Mappers.fireAnimation.get(entity);
            if (tc.region.getTexture() == ac.texture1) {
                tc.region.setTexture(ac.texture2);
            }
            else {
                tc.region.setTexture(ac.texture1);
            }
        }
    }
}
