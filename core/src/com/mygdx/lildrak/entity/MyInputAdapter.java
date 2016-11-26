package com.mygdx.lildrak.entity;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.lildrak.entity.systems.InputSystem;

public class MyInputAdapter extends InputAdapter
{
    InputSystem inputSystem;
    boolean acceptInput = true;
    float inputDelay = 0.1f;

    public MyInputAdapter(InputSystem inputSystem)
    {
        this.inputSystem = inputSystem;
    }

    public boolean keyDown(int keycode)
    {
        if (acceptInput)
        {
            switch (keycode)
            {
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
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    acceptInput = true;
                }
            }, inputDelay);
        }
        return true;
    }


}
