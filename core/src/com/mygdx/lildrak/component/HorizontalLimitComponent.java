package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class HorizontalLimitComponent implements Component
{
    public float limit;

    public HorizontalLimitComponent(float limit)
    {
        this.limit = limit;
    }
}
