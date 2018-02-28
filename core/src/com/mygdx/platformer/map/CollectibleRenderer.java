/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.map;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.mygdx.platformer.entity.Collectible;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.entity.EntityFactory;
import com.mygdx.platformer.msg.MessageType;
import com.mygdx.platformer.physics.BodyEditorLoader;
import com.mygdx.platformer.physics.BodyFactory;
import com.mygdx.platformer.physics.GiftBodyFactory;

/**
 *
 * @author Julia
 */
public class CollectibleRenderer implements Telegraph {
    private static final String GIFT_TYPE = "gift";
    private static final String TYPE_PROPERTY = "type";
    private final Array<Entity> collectibles;
    private final ObjectSet<Entity> removed;
    private final float unitScale;
    private boolean isGameOver;

    public CollectibleRenderer() {
        this(1.0f);
    }

    public CollectibleRenderer(final float unitScale) {
        this.collectibles = new Array<Entity>();
        this.removed = new ObjectSet<Entity>(16);
        this.unitScale = unitScale;
        MessageManager.getInstance().addListeners(this, MessageType.COLLECTED.code(),MessageType.GAME_OVER.code(),MessageType.FINISH_LEVEL.code());
        isGameOver = false;
    }

    public void load(final World world, final BodyEditorLoader loader, final AssetManager assets, final MapLayer layer) {
        load(world, loader, assets, layer, null);
    }

    public void load(final World world, final BodyEditorLoader loader, final AssetManager assets, final MapLayer layer,
                    final Object userData) {
        if (layer == null) {
                return;
        }

        for (MapObject mo : layer.getObjects()) {
            BodyDef bodyDefinition = new BodyDef();
            bodyDefinition.type = BodyType.KinematicBody;
            float x = ((Float) mo.getProperties().get("x")).floatValue() * unitScale;
            float y = ((Float) mo.getProperties().get("y")).floatValue() * unitScale;
            bodyDefinition.position.set(x, y);

            BodyFactory bodyFactory = null;
            Entity entity = null;

            if (GIFT_TYPE.equals(mo.getProperties().get(TYPE_PROPERTY, GIFT_TYPE, String.class))) {
                bodyFactory = new GiftBodyFactory(loader);
                entity = EntityFactory.createCollectible(world, assets);
            } else {
                    throw new IllegalArgumentException("Unknown collectible type {" + mo.getProperties().get(TYPE_PROPERTY, String.class) + "}");
            }

            Body body = bodyFactory.create(world, bodyDefinition);

            entity.setBody(body);
            body.setUserData(entity);
            collectibles.add(entity);
        }
    }

    public void update(final Batch batch, final Rectangle viewBounds) {
        for (Entity e : collectibles) {
            if (viewBounds == null) {
                e.update(null);
                e.step(batch,null);
            } else {
                Body bb = e.getBody();
                if(e.getBody() != null)
                {
                    Vector2 vv=e.getBody().getPosition();
                    if (viewBounds.contains(e.getBody().getPosition())) {
                        e.getBody().setActive(true);
                        e.update(null);
                        e.step(batch,null);
                    } else {
                        e.getBody().setActive(false);
                        //removed.add(e);
                    }
                }
            }
        }

        for (Entity c : removed) {
            collectibles.removeValue(c, true);
        }
        removed.clear();
    }

    public void update(final Batch batch) {
        update(batch, null);
    }

    @Override
    public boolean handleMessage(final Telegram msg) {
        if (msg.message == MessageType.COLLECTED.code()) {
            Collectible collectible = (Collectible) msg.extraInfo;
            removed.add(collectible);
        }else if(msg.message == MessageType.GAME_OVER.code() || msg.message == MessageType.FINISH_LEVEL.code()){
            collectibles.clear();
        }
        return true;
    }    
}
