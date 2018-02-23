/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.platformer.graphics.GraphicsProcessor;
import com.mygdx.platformer.physics.PhysicsProcessor;
import com.mygdx.platformer.player.PlayerStatusProcessor;

/**
 *
 * @author Julia
 */
public class Environment extends Entity {

    /**
     * A component that updates the status of the player during the gaming session according to the
     * Environments inner state.
     */
    private final PlayerStatusProcessor player;

    public Environment(final GraphicsProcessor graphics, final PhysicsProcessor physics, final PlayerStatusProcessor player) {
        super(graphics, physics);
        this.player = player;
    }

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.entity.Entity#step(com.badlogic.gdx.graphics.g2d.Batch)
     */
    @Override
    public void step(final Batch batch) {
        //player.update(this);
        player.doUpdate(this);
        super.step(batch);
    }
    
}
