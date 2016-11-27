package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class FlameAnimationComponent implements Component {

    public Texture texture1;
    public Texture texture2;

    public FlameAnimationComponent(Texture texture1, Texture texture2) {
        this.texture1 = texture1;
        this.texture2 = texture2;
    }
}
