/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platformer.entity.Direction;

/**
 *
 * @author Julia
 */
public interface BodyFactory {
   /**
     * Creates a Box2D {@link Body} in the given {@link World}. The body will be facing the
     * direction indicated by {@link Direction}.
     *
     * @param world
     *            An instance of a Box2D {@link World} used to create bodies and fixtures.
     * @param direction
     *            Indicates where the resulting body should be facing. For symmetrical or simple
     *            bodies, this may have no effect.
     * @return A newly constructed {@link Body} and all of its {@link Fixture}.
     */
    Body create(World world, Direction direction);

    /**
     * Creates a Box2D {@link Body} in the given {@link World} using the given {@link BodyDef}. The
     * body will be facing a default direction (defaults may vary between implementations).
     *
     * @param world
     *            An instance of a Box2D {@link World} used to create bodies and fixtures.
     * @param definition
     *            The {@link BodyDef} that defines initial body properties.
     * @return A newly constructed {@link Body} from the {@link BodyDef}, and all of its
     *         {@link Fixture}.
     */
    Body create(World world, BodyDef definition);

    /**
     * Creates a Box2D {@link Body} in the given {@link World} using the given {@link BodyDef}. The
     * body will be facing the direction indicated by {@link Direction}.
     *
     * @param world
     *            An instance of a Box2D {@link World} used to create bodies and fixtures.
     * @param definition
     *            The {@link BodyDef} that defines initial body properties.
     * @param direction
     *            Indicates where the resulting body should be facing. For symmetrical or simple
     *            bodies, this may have no effect.
     * @return A newly constructed {@link Body} from the {@link BodyDef}, and all of its
     *         {@link Fixture}.
     */
    Body create(World world, BodyDef definition, Direction direction); 
}
