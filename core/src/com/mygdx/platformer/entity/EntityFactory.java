/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platformer.graphics.GiftGraphicsProcessor;
import com.mygdx.platformer.graphics.GraphicsProcessor;
import com.mygdx.platformer.graphics.LevelGraphicsProcessor;
import com.mygdx.platformer.graphics.PlatformGraphicsProcessor;
import com.mygdx.platformer.graphics.SantaGraphicsProcessor;
import com.mygdx.platformer.input.SantaInputProcessor;
import com.mygdx.platformer.map.LevelRenderer;
import com.mygdx.platformer.physics.BodyEditorLoader;
import com.mygdx.platformer.physics.BodyProcessor;
import com.mygdx.platformer.physics.ContactListenerMultiplexer;
import com.mygdx.platformer.physics.GiftPhysicsProcessor;
import com.mygdx.platformer.physics.LevelPhysicsProcessor;
import com.mygdx.platformer.physics.PhysicsProcessor;
import com.mygdx.platformer.physics.PlatformPhysicsProcessor;
import com.mygdx.platformer.physics.SantaBodyProcessor;
import com.mygdx.platformer.physics.SantaPhysicsProcessor;
import com.mygdx.platformer.player.CurrentPlayerStatus;
import com.mygdx.platformer.player.LevelPlayerStatusProcessor;
import com.mygdx.platformer.player.PlayerStatusObserver;
import com.mygdx.platformer.player.PlayerStatusProcessor;
import com.mygdx.platformer.player.SantaPlayerStatusProcessor;

/**
 *
 * @author Julia
 */
public final class EntityFactory {
    private static final ContactListenerMultiplexer CONTACT_LISTENER = new ContactListenerMultiplexer();

    /**
     * This should be used as a static factory. No instances allowed.
     */
    private EntityFactory() {

    };

    /**
     * Creates a new instance of {@link Collectible}, defining its graphical, audio and physical
     * properties.
     *
     * @param world
     *            The Box2D {@link World} onto which to create the {@link Body} and {@link Fixture}
     *            of the {@link Entity}.
     * @param assets
     *            The {@link AssetManager} from where to extract the graphical and audio resources.
     *            Those resources should be loaded in the manager before calling this method.
     * @return A ready to use instance of a new {@link Collectible}.
     */
    public static Entity createCollectible(final World world, final AssetManager assets) {
        PhysicsProcessor physics = new GiftPhysicsProcessor();
        CONTACT_LISTENER.add(physics);
        world.setContactListener(CONTACT_LISTENER);
        GraphicsProcessor graphics = new GiftGraphicsProcessor(assets);
        return new Collectible(graphics, physics);
    }

    public static Entity createPlatform(final World world, final AssetManager assets, Path path) {
        PhysicsProcessor physics = new PlatformPhysicsProcessor(path);
        CONTACT_LISTENER.add(physics);
        world.setContactListener(CONTACT_LISTENER);
        GraphicsProcessor graphics = new PlatformGraphicsProcessor(assets);
        return new Platform(graphics, physics);
    }
    
    /**
     * Creates a new instance of {@link NinjaRabbit}, defining its graphical, audio and physical
     * properties.
     *
     * @param world
     *            The Box2D {@link World} onto which to create the {@link Body} and {@link Fixture}
     *            of the {@link Entity}.
     * @param batch
     * @param renderer
     * @param loader
     *            A {@link BodyEditorLoader} to handle creation of the Entity body and fixtures.
     * @param assets
     *            The {@link AssetManager} from where to extract the graphical and audio resources.
     *            Those resources should be loaded in the manager before calling this method and
     *            won't be disposed.
     * @param status
     *            A reference to the global status of the player to be updated from the changes in
     *            the returned entity inner state.
     * @param observers
     *            An array of event receivers. Events will fire when the active player status
     *            changes (such as losing lives, collecting items, etc.).
     * @return A ready to use instance of a new {@link NinjaRabbit}.
     */
    public static Entity createSanta(final World world, final BodyEditorLoader loader, final AssetManager assets,
                    final CurrentPlayerStatus status, final PlayerStatusObserver... observers) {
        PhysicsProcessor physics = new SantaPhysicsProcessor();
        CONTACT_LISTENER.add(physics);
        world.setContactListener(CONTACT_LISTENER);
        GraphicsProcessor graphics = new SantaGraphicsProcessor(assets);
        BodyProcessor bodyProcessor = new SantaBodyProcessor(world, loader);
        PlayerStatusProcessor player = new SantaPlayerStatusProcessor(status);
        if (observers != null) {
            for (PlayerStatusObserver o : observers) {
                    player.addObserver(o);
            }
        }
        Santa santa = new Santa(player, bodyProcessor, graphics, physics);

        Gdx.input.setInputProcessor(new SantaInputProcessor(santa));
        
        return santa;
    }

    /**
     * Creates and returns a new instance of {@link Environment}, settings its physical, graphical
     * and audio attributes.
     *
     * @param world
     * @param batch
     * @param renderer
     * @param assets
     * @param status
     * @param observers
     * @return A newly created {@link Environment}.
     */
    public static Entity createEnvironment(final World world, final Batch batch, final LevelRenderer renderer, final AssetManager assets,
                final CurrentPlayerStatus status, final PlayerStatusObserver... observers) {
        PhysicsProcessor physics = new LevelPhysicsProcessor(world, renderer.getTiledMap(), renderer.getUnitScale());
        CONTACT_LISTENER.add(physics);
        world.setContactListener(CONTACT_LISTENER);
        GraphicsProcessor graphics = new LevelGraphicsProcessor(assets, batch, renderer);
        PlayerStatusProcessor player = new LevelPlayerStatusProcessor(status);
        if (observers != null) {
            for (PlayerStatusObserver o : observers) {
                    player.addObserver(o);
            }
        }
        return new Environment(graphics, physics, player);
    }

    /**
     * Removes every {@link ContactListener} added to the {@link World} after the creation of each
     * {@link Entity}.
     *
     * Calling this method will force the Box2D {@link World} to stop sending collision events to
     * current listeners.
     */
    public static void clearContactListeners() {
        CONTACT_LISTENER.clear();
    }
}
