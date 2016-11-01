package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class Invincibility implements Component
{
    public float duration;
    public float counter;

    public Invincibility(float duration)
    {
        this.duration = duration;
        this.counter = 0;
    }
}
