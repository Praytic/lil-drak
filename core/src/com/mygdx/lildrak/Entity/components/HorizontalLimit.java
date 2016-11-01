package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class HorizontalLimit implements Component
{
    public float limit;

    public HorizontalLimit(float limit)
    {
        this.limit = limit;
    }
}
