package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class PlayerMovement implements Component
{
    public float moveForce;

    public PlayerMovement(float moveForce)
    {
        this.moveForce  = moveForce;
    }
}
