package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class PlayerMovementComponent implements Component
{
    public float moveForce;

    public PlayerMovementComponent(float moveForce)
    {
        this.moveForce  = moveForce;
    }
}
