package com.mygdx.lildrak.entity;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.lildrak.Lildrak;

import java.util.ArrayList;
import java.util.List;

public enum CollectibleType {

    CANDY("candy", "candy.png"),
    LOLLIPOP("lollipop", "lollipop.png"),
    MONEY("money", "money.png"),
    FLAME("flame", "flame1.gif", "flame2.gif");

    private List<Texture> textures;
    private String name;
    private int width;
    private int height;

    CollectibleType(String name, String ... textures) {
        this.textures = new ArrayList<>();
        for (String texture : textures) {
            this.textures.add(Lildrak.ASSETS.get(texture));
        }
        this.name = name;
        if (!this.textures.isEmpty()) {
            this.width = this.textures.get(0).getWidth();
            this.height = this.textures.get(0).getHeight();
        }
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
