package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class BatAnimation implements Component
{
    public Texture idleFrame1;
    public Texture idleFrame2;
    public Texture hurtFrame;
    public Texture deadFrame;

    public BatAnimation(Array<Texture> frames)
    {
        this.idleFrame1 = frames.get(0);
        this.idleFrame2 = frames.get(1);
        this.hurtFrame = frames.get(2);
        this.deadFrame = frames.get(3);
    }
}
