/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.entity;

import com.mygdx.platformer.fsm.GiftState;
import com.mygdx.platformer.graphics.GraphicsProcessor;
import com.mygdx.platformer.physics.PhysicsProcessor;

/**
 *
 * @author Julia
 */
public class Collectible extends Entity{
    
    /**
     * Signals whether this {@link Collectible} has been collected by the main character or not.
     */
    private boolean collected;
    private boolean isDeleted;
    
    public Collectible(GraphicsProcessor graphics, PhysicsProcessor physics) {
        super(graphics, physics, GiftState.DOWN);
        isDeleted = false;
    }

    /**
     * Indicates whether this {@link Collectible} has already been picked up by the main character.
     *
     * @return true if it has been collected; false, otherwise.
     */
    public boolean isCollected() {
            return collected;
    }

    /**
     * Used to indicate if this {@link Collectible} has been collected by the main character.
     *
     * @param collected
     *            wheter it has been gatehred or not.
     */
    public void setCollected(final boolean collected) {
            this.collected = collected;
    }
    
    public void del(){
        isDeleted = true;
    }
    
    public boolean isDel(){
        return isDeleted;
    }
    
}
