package com.mygdx.lildrak.entity;

import com.mygdx.lildrak.Asset;

import java.util.ArrayList;
import java.util.List;

public enum CollectibleType {

    CANDY("candy", Asset.Image.CANDY),
    LOLLIPOP("lollipop", Asset.Image.LOLLIPOP),
    MONEY("money", Asset.Image.MONEY),
    FLAME("flame", Asset.Image.FLAME1, Asset.Image.FLAME2);

    private List<Asset.Image> textureNames;
    private String name;

    CollectibleType(String name, Asset.Image... textureNames) {
        this.textureNames = new ArrayList<>();
        for (Asset.Image textureName : textureNames) {
            this.textureNames.add(textureName);
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Asset.Image> getTextureNames() {
        return textureNames;
    }
}