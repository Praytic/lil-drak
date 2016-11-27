package com.mygdx.lildrak.component;

import com.badlogic.ashley.core.Component;

public class NameComponent implements Component {

    private String name;

    public NameComponent(String name) {
        this.name = name + "-" + this.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
