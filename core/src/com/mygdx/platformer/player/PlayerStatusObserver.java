/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.player;

/**
 *
 * @author Julia
 */
public interface PlayerStatusObserver {
    /**
     * Callback to be called when an instance of {@link PlayerStatus} changes its states, for
     * example because a player has lost a live or has collected an item.
     *
     * @param status
     */
    void onPlayerStatusChange(final PlayerStatus event);
}
