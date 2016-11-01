package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.entity.components.BodyComponent;

/**
 * Destroy bodies, when their entities are removed from the engine
 */
public class BodyEntityListener implements EntityListener
{
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    public void entityAdded(Entity entity) {}
    public void entityRemoved(Entity entity)
    {
        BodyComponent bc = bm.get(entity);
        GameScreen.world.destroyBody(bc.body);
        bc.body = null;
    }
}
