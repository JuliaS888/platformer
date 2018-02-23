/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.mygdx.platformer.entity.Entity;

/**
 *
 * @author Julia
 */
public interface BodyProcessor {
    /**
     * Regenerates the character Box2D {@link Body} according to the action it is executing. It
     * destroys the old body if necessary.
     *
     * @param character
     *            The {@link Entity} whose body should be updated.
     */
    public abstract void update(Entity character);
}
