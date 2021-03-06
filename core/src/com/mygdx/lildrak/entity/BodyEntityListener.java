package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lildrak.LoggerTag;
import com.mygdx.lildrak.component.BodyComponent;
import com.mygdx.lildrak.component.NameComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BodyEntityListener implements EntityListener {

    @Autowired
    private World world;

    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.debug(LoggerTag.NEW_ENTITY.toString(), entity.getComponent(NameComponent.class).getName());
    }

    @Override
    public void entityRemoved(Entity entity) {
        Gdx.app.debug(LoggerTag.DELETE_ENTITY.toString(), entity.getComponent(NameComponent.class).getName());
        BodyComponent component = entity.getComponent(BodyComponent.class);
        if (component != null) {
            world.destroyBody(component.getBody());
        }
    }
}
