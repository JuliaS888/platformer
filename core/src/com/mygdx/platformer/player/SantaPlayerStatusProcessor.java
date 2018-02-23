/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.player;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.msg.MessageType;

/**
 *
 * @author Julia
 */
public class SantaPlayerStatusProcessor extends PlayerStatusProcessor implements Telegraph {
    /**
     * Points earned by gathering a collectible.
     */
    private static final int COLLECTIBLE_POINTS = 200;

    public SantaPlayerStatusProcessor(final CurrentPlayerStatus status) {
        super(status);
        MessageManager.getInstance().addListeners(this, MessageType.COLLECTED.code(), MessageType.DEAD.code());
    }

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.player.PlayerStatusProcessor#doUpdate(ar.uba.fi.game.entity.Entity)
     */
    @Override
    public void doUpdate(final Entity character) {
    }

    @Override
    public boolean handleMessage(final Telegram msg) {
        if (msg.message == MessageType.COLLECTED.code()) {
            getStatus().setCollectibles((short) (getStatus().getCollectibles() + 1));
            getStatus().setScore(getStatus().getScore() + COLLECTIBLE_POINTS);
        } else if (msg.message == MessageType.DEAD.code()) {
            if (getStatus().getLives() > 0) {
                    getStatus().setLives((short) (getStatus().getLives() - 1));
            }
            System.out.println("Dead received!");
        }
        return true;
    }    
}
