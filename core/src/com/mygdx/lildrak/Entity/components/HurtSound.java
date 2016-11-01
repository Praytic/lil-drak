package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

public class HurtSound implements Component
{
    public Sound sound;

    public HurtSound(Sound sound)
    {
        this.sound = sound;
    }
}
