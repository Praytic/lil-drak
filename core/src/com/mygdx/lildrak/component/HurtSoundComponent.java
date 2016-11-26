package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

public class HurtSoundComponent implements Component
{
    public Sound sound;

    public HurtSoundComponent(Sound sound)
    {
        this.sound = sound;
    }
}
