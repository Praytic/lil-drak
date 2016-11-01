package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.*;

public class BatAnimationSystem extends IntervalSystem
{
    private ImmutableArray<Entity> entities;

    public BatAnimationSystem()
    {
        super(0.1f);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        entities = engine.getEntitiesFor(Family.all(BatAnimation.class, Health.class, Invincibility.class, TextureComponent.class).get());
    }

    public void updateInterval()
    {
        for (int i = 0; i < entities.size(); ++i)
        {
            Entity entity = entities.get(i);
            TextureComponent tc = Mappers.textureComponent.get(entity);
            BatAnimation ac = Mappers.batAnimation.get(entity);
            Health hc = Mappers.health.get(entity);
            Invincibility ic = Mappers.invincibility.get(entity);

            if (hc.health <= 0) tc.region.setTexture(ac.deadFrame);
            else if (tc.region.getTexture() == ac.idleFrame1)
            {
                // alternate idle frames or idle + hurt frame
                if (ic.counter > 0) tc.region.setTexture(ac.hurtFrame);
                else tc.region.setTexture(ac.idleFrame2);
            } else tc.region.setTexture(ac.idleFrame1);
        }
    }
}
