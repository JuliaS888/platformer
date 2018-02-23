/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.player;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 *
 * @author Julia
 */
public class CurrentPlayerStatus implements PlayerStatus,Poolable{
    /**
     * How many collectibles have the player gathered.
     */
    private short collectibles;

    /**
     * How many lives are left for the left.
     */
    private short lives;

    /**
     * Remaining time to complete the level (in seconds).
     */
    private short time;
    
    /**
     * The current score points.
     */
    private int score;
        
    public CurrentPlayerStatus() {
        lives = DEFAULT_LIVES;
        time = DEFAULT_TIME;
    }
    
    @Override
    public short getCollectibles() {
        return collectibles;
    }
    
    protected void setCollectibles(short collectibles) {
        this.collectibles = collectibles;
    }

    @Override
    public short getLives() {
        return lives;
    }
    
    protected void setLives(short lives) {
        this.lives = lives;
    }

    @Override
    public short getTime() {
        return time;
    }
    
    protected void setTime(short time) {
        this.time = time;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.player.PlayerStatus#getScore()
     */
    @Override
    public int getScore() {
        return score;
    }

    protected void setScore(final int score) {
        this.score = score;
    }
        
    @Override
    public void reset() {
        lives = DEFAULT_LIVES;
        time = DEFAULT_TIME;
        collectibles = 0;
        score = 0;
    }
    
}
