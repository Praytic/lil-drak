package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.LoggerTag;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.NameComponent;

/**
 * Destroy bodies, when their entities are removed from the engine
 */
public class BodyEntityListener implements EntityListener {

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.debug(LoggerTag.NEW_ENTITY.toString(), entity.getComponent(NameComponent.class).getName());
    }

    @Override
    public void entityRemoved(Entity entity) {
        Gdx.app.debug(LoggerTag.DELETE_ENTITY.toString(), entity.getComponent(NameComponent.class).getName());
        BodyComponent component = entity.getComponent(BodyComponent.class);
        if (component != null) {
            GameScreen.world.destroyBody(component.getBody());
        }
    }
}
