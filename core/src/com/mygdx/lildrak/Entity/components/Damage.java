package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class Damage implements Component
{
    public int damage;

    public Damage(int damage)
    {
        this.damage = damage;
    }
}
