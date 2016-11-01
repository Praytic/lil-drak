package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class OriginalPosition implements Component
{
    public float x;
    public float y;

    public OriginalPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
