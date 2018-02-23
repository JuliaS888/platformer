/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.platformer.entity.Entity;

/**
 *
 * @author Julia
 */
public interface PhysicsProcessor extends ContactListener, Disposable {

    /**
     * Updates all physics related concerns for the given entity on the current frame.
     *
     * @param character
     *            The game entity whose physical properties are to be updated.
     */
    void update(final Entity character);
}

