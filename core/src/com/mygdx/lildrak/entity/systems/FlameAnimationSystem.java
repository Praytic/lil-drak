package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.lildrak.component.*;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlameAnimationSystem extends IntervalSystem {

    private ImmutableArray<Entity> entities;

    @Autowired
    public FlameAnimationSystem(Engine engine) {
        super(0.1f);
        engine.addSystem(this);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(FlameAnimationComponent.class).get());

    }

    @Override
    public void updateInterval() {
        for (Entity entity : entities) {
            TextureComponent tc = Mappers.textureComponent.get(entity);
            FlameAnimationComponent ac = Mappers.fireAnimation.get(entity);
            if (tc.region.getTexture() == ac.texture1) {
                tc.region.setTexture(ac.texture2);
            }
            else {
                tc.region.setTexture(ac.texture1);
            }
        }
    }
}
