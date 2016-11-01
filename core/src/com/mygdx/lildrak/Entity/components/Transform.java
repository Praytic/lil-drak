package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class Transform implements Component
{
    public float x, y, angle;
    public int z;

    public Transform(float x, float y, int z, float angle)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }
}
