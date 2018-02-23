/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.platformer.entity.Entity;

/**
 *
 * @author Julia
 */
public enum SantaState implements State<Entity> {

    /**
     * The state this {@link Entity} is in when not moving at all.
     */
    IDLE,
    /**
     * The state this {@link Entity} is in when jumping.
     */
    JUMP,
    /**
     * The state this {@link Entity} is in when moving left.
     */
    LEFT,

    /**
     * The state this {@link Entity} is in when moving right.
     */
    RIGHT;

    @Override
    public void enter(Entity e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(Entity e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void exit(Entity e) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onMessage(Entity e, Telegram tlgrm) {
        // TODO Auto-generated method stub
        return false;
    }
}
