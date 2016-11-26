package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class InvincibilityComponent implements Component
{
    public float duration;
    public float counter;

    public InvincibilityComponent(float duration)
    {
        this.duration = duration;
        this.counter = 0;
    }
}
