package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class VerticalLimit implements Component
{
    public float limit;

    public VerticalLimit(float limit)
    {
        this.limit = limit;
    }
}
