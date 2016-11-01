package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class Health implements Component
{
    public int health;

    public Health(int health)
    {
        this.health = health;
    }
}
