package com.mygdx.lildrak.entity;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.lildrak.entity.systems.InputSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomInputAdapter extends InputAdapter {

    @Autowired
    private InputSystem inputSystem;

    private boolean acceptInput = true;

    @Override
    public boolean keyDown(int keycode) {
        if (!acceptInput) {
            return true;
        }
        switch (keycode) {
            case Input.Keys.A:
                inputSystem.moveLeft();
                break;
            case Input.Keys.LEFT:
                inputSystem.moveLeft();
                break;
            case Input.Keys.W:
                inputSystem.moveUp();
                break;
            case Input.Keys.UP:
                inputSystem.moveUp();
                break;
            case Input.Keys.SPACE:
                inputSystem.moveUp();
                break;
            case Input.Keys.D:
                inputSystem.moveRight();
                break;
            case Input.Keys.RIGHT:
                inputSystem.moveRight();
                break;
        }
        acceptInput = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                acceptInput = true;
            }
        }, 0.1f);
        return true;
    }
}
