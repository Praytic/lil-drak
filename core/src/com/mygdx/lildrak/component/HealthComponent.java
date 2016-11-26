package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component
{
    public int health;

    public HealthComponent(int health)
    {
        this.health = health;
    }
}
