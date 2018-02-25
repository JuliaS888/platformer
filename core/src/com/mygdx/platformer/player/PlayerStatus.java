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
public interface PlayerStatus {
    public static final short DEFAULT_TIME = 600;
    public static final short DEFAULT_LIVES = 5;
    /**
     * Returns the count of gathered collectibles so far.
     *
     * @return The number of collectibles owned by the player.
     */
    short getCollectibles();

    /**
     * Returns the number of remaining lives. When lives reaches zero, it's game over.
     *
     * @return The number of lives left for the player.
     */
    short getLives();

    /**
     * Returns the number of remaining in-game time units to finish the level. When this reaches
     * zero it's game over.
     *
     * @return The time that remains to finish the current level.
     */
    short getTime();
    
    /**
     * Returns the current score of the player.
     *
     * @return The total amount of points.
     */
    int getScore();
}
