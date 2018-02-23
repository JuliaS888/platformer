/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.msg.MessageType;

/**
 *
 * @author Julia
 */
public class SantaInputProcessor extends InputAdapter implements Telegraph {
    private final static int JUMP_KEY = Keys.UP;
    private final static int LEFT_KEY = Keys.LEFT;
    private final static int RIGHT_KEY = Keys.RIGHT;
    private static final int RESET_KEY = Keys.BACKSPACE;

    private final Entity character;

    public SantaInputProcessor(final Entity ninjaRabbit) {
        if (ninjaRabbit == null) {
            throw new IllegalArgumentException("'character' cannot be null");
        }
        this.character = ninjaRabbit;
        MessageManager.getInstance().addListener(this, MessageType.EXIT.code());
    }

    @Override
    public boolean keyDown(final int keycode) {
        switch (keycode) {
        case JUMP_KEY:
            character.changeState(SantaState.JUMP);
            break;
        case LEFT_KEY:
            character.changeState(SantaState.LEFT);
            break;
        case RIGHT_KEY:
            character.changeState(SantaState.RIGHT);
            break;
        case RESET_KEY:
            MessageManager.getInstance().dispatchMessage(null, MessageType.DEAD.code(), character);
            break;
        default:
            break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(final int keycode) {
        switch (keycode) {
        case JUMP_KEY:
            if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
                character.changeState(SantaState.RIGHT);
            } else if (Gdx.input.isKeyPressed(LEFT_KEY)) {
                character.changeState(SantaState.LEFT);
            } else {
                character.changeState(SantaState.IDLE);
            }
            break;
        case LEFT_KEY:
            if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
                character.changeState(SantaState.RIGHT);
            } else {
                character.changeState(SantaState.IDLE);
            }
            break;
        case RIGHT_KEY:
            if (Gdx.input.isKeyPressed(LEFT_KEY)) {
                character.changeState(SantaState.LEFT);
            } else {
                character.changeState(SantaState.IDLE);
            }
            break;
        default:
            break;
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean handleMessage(final Telegram msg) {
        Gdx.input.setInputProcessor(null);
        return true;
    }    
}
