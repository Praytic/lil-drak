package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.lildrak.component.*;

public class Whip extends Entity {

    public Whip(float x, float y, int z, float angle, TextureRegion texture, float limit, int damage, Body body) {
        add(new TransformComponent(x, y, z, angle));
        add(new TextureComponent(texture));
        add(new BodyComponent(body));
        add(new VerticalLimitComponent(limit));
        add(new DamageComponent(damage));
        add(new NameComponent("whip"));
    }
}
