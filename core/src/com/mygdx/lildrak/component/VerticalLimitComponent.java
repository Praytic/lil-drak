package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class VerticalLimitComponent implements Component
{
    public float limit;

    public VerticalLimitComponent(float limit)
    {
        this.limit = limit;
    }
}
