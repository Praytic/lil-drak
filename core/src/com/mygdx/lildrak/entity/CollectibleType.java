package com.mygdx.lildrak.entity;

import java.util.ArrayList;
import java.util.List;

public enum CollectibleType {

    CANDY("candy", "candy.png"),
    LOLLIPOP("lollipop", "lollipop.png"),
    MONEY("money", "money.png"),
    FLAME("flame", "flame1.gif", "flame2.gif");

    private List<String> textureNames;
    private String name;
    private int width;
    private int height;

    CollectibleType(String name, String... textureNames) {
        this.textureNames = new ArrayList<>();
        for (String textureName : textureNames) {
            this.textureNames.add(textureName);
        }
        this.name = name;
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

    public List<String> getTextureNames() {
        return textureNames;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}