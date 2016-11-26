package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class TransformComponent implements Component
{
    public float x, y, angle;
    public int z;

    public TransformComponent(float x, float y, int z, float angle)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }
}
