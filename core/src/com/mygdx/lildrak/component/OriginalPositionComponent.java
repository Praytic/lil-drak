package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class OriginalPositionComponent implements Component
{
    public float x;
    public float y;

    public OriginalPositionComponent(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
