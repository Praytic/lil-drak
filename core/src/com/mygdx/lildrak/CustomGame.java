package com.mygdx.lildrak;

import com.badlogic.gdx.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomGame extends Game {

    @Autowired
    private LoadScreen loadScreen;

    @Override
    public void create() {
        setScreen(loadScreen);
    }
}
