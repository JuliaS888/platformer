/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.mygdx.platformer.msg.MessageType;

/**
 *
 * @author Julia
 */
public class EndScreenInputProcessor extends InputAdapter{
    @Override
    public boolean keyUp(final int keycode) {
        switch (keycode) {
            case Keys.ENTER:
                MessageManager.getInstance().dispatchMessage(MessageType.RESET.code());
            break;
        }
        return super.keyDown(keycode);
    }
}
