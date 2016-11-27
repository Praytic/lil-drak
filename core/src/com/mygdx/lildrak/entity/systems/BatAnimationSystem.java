package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.lildrak.component.BatAnimationComponent;
import com.mygdx.lildrak.component.HealthComponent;
import com.mygdx.lildrak.component.InvincibilityComponent;
import com.mygdx.lildrak.component.TextureComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatAnimationSystem extends IntervalSystem {

    private ImmutableArray<Entity> entities;

    @Autowired
    public BatAnimationSystem(Engine engine) {
        super(0.1f);
        engine.addSystem(this);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(BatAnimationComponent.class, HealthComponent.class,
                InvincibilityComponent.class, TextureComponent.class).get());
    }

    @Override
    public void updateInterval() {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            TextureComponent tc = Mappers.textureComponent.get(entity);
            BatAnimationComponent ac = Mappers.batAnimation.get(entity);
            HealthComponent hc = Mappers.health.get(entity);
            InvincibilityComponent ic = Mappers.invincibility.get(entity);

            if (hc.health <= 0) tc.region.setTexture(ac.deadFrame);
            else if (tc.region.getTexture() == ac.idleFrame1) {
                // alternate idle frames or idle + hurt frame
                if (ic.counter > 0) tc.region.setTexture(ac.hurtFrame);
                else tc.region.setTexture(ac.idleFrame2);
            } else tc.region.setTexture(ac.idleFrame1);
        }
    }
}
