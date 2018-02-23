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
public class LevelPlayerStatusProcessor extends PlayerStatusProcessor implements Telegraph {
    private boolean gameOverSignaled;

    public LevelPlayerStatusProcessor(final CurrentPlayerStatus status) {
        super(status);
        MessageManager.getInstance().addListener(this, MessageType.FINISH_LEVEL.code());
    }

    @Override
    public void doUpdate(final Entity entity) {
        // No time or lives left: game over
        if ((getStatus().getTime() < 0 || getStatus().getLives() < 1) && !gameOverSignaled) {
            MessageManager.getInstance().dispatchMessage(this, MessageType.GAME_OVER.code());
            gameOverSignaled = true;
        }
    }

    @Override
    public boolean handleMessage(final Telegram msg) {
        getStatus().setTime(PlayerStatus.DEFAULT_TIME);
        MessageManager.getInstance().dispatchMessage(this, MessageType.BEGIN_LEVEL.code());
        return true;
    }
}
