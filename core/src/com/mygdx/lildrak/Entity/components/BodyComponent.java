package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class BodyComponent implements Component
{
    public com.badlogic.gdx.physics.box2d.Body body;

    public BodyComponent(com.badlogic.gdx.physics.box2d.Body body)
    {
        this.body = body;
    }
}
