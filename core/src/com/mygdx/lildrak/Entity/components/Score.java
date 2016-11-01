package com.mygdx.lildrak.entity.components;

import com.badlogic.ashley.core.Component;

public class Score implements Component
{
    public int score;

    public Score(int score)
    {
        this.score = score;
    }
}
