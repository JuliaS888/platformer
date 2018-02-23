/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.entity;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.graphics.GraphicsProcessor;
import com.mygdx.platformer.physics.BodyProcessor;
import com.mygdx.platformer.physics.PhysicsProcessor;
import com.mygdx.platformer.player.PlayerStatusProcessor;

/**
 *
 * @author Julia
 */
public class Santa extends Entity {
    /**
     * A component used to change the body of this {@link Entity} according the action being
     * executed or the {@link Direction} that is facing, if necessary.
     */
    private final BodyProcessor bodyProcessor;

    /**
     * A component that handles the score, lives and other player-related data.
     */
    private final PlayerStatusProcessor player;

    public Santa(final PlayerStatusProcessor player, final BodyProcessor bodyProcessor, final GraphicsProcessor graphics, final PhysicsProcessor physics) {
        super(graphics, physics, SantaState.IDLE);
        this.bodyProcessor = bodyProcessor;
        this.player = player;
    }

    /*
     * (non-Javadoc)
     *
     * @see ar.uba.fi.game.entity.Entity#update(com.badlogic.gdx.graphics.Camera)
     */
    @Override
    public void update(final Camera camera) {
        player.update(this);
        super.update(camera);
        bodyProcessor.update(this);
    }
}
