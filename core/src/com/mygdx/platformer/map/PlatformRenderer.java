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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.mygdx.platformer.entity.Collectible;
import com.mygdx.platformer.entity.Entity;
import com.mygdx.platformer.entity.EntityFactory;
import com.mygdx.platformer.entity.Path;
import com.mygdx.platformer.fsm.PathType;
import com.mygdx.platformer.msg.MessageType;
import com.mygdx.platformer.physics.BodyEditorLoader;
import com.mygdx.platformer.physics.BodyFactory;
import com.mygdx.platformer.physics.GiftBodyFactory;
import com.mygdx.platformer.physics.PlatformBodyFactory;
import java.util.ArrayList;

/**
 *
 * @author Julia
 */
public class PlatformRenderer implements Telegraph{
    private static final String PLATFORM_TYPE = "platform";
    private static final String TYPE_PROPERTY = "type";
    private final Array<Entity> platforms;
    private final float unitScale;
    public PlatformRenderer() {
        this(1.0f);
    }
    
    public PlatformRenderer(final float unitScale) {
        this.platforms = new Array<Entity>();
        this.unitScale = unitScale;
        MessageManager.getInstance().addListeners(this, MessageType.COLLECTED.code(),MessageType.GAME_OVER.code(),MessageType.FINISH_LEVEL.code());
    }
    
    public void load(final World world, final BodyEditorLoader loader, final AssetManager assets, final MapLayer layer) {
        load(world, loader, assets, layer, null);
    }

    public void load(final World world, final BodyEditorLoader loader, final AssetManager assets, final MapLayer layer, final Object userData) {
        if (layer == null) {
            return;
        }
        
        //1 платформа - вправо-влево
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(4, 2.8f);
        BodyFactory bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        ArrayList<Vector2> points = new ArrayList<Vector2>();
        points.add(new Vector2(4,2.8f));
        points.add(new Vector2(9,2.8f));
        ArrayList<Float> times = new ArrayList<Float>(){{
            add(6f);
            add(6f);
        }};
        Path path = new Path(points,times,PathType.ROUNDTRIP);
        Entity entity = EntityFactory.createPlatform(world, assets, path);
        Body body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //2 платформа - движется по кругу
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(18.2f, 4f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(18f, 4f));        
        points.add(new Vector2(18f, 9f));        
        points.add(new Vector2(21f, 9f));        
        points.add(new Vector2(21f, 4f));                
        times = new ArrayList<Float>(){{
            add(4f);
            add(4f);
            add(4f);
            add(4f);
        }};
        path = new Path(points,times,PathType.ROUND);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //3 платформа - вверх-вниз - самое высокое
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(24.5f, 7.5f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(24.5f, 7.5f));        
        points.add(new Vector2(24.5f, 18f));                
        times = new ArrayList<Float>(){{
            add(6f);
            add(6f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //4 платформа - вверх-вниз - внизу
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(24.5f, 1f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(24.5f, 1f));        
        points.add(new Vector2(24.5f, 5f));                
        times = new ArrayList<Float>(){{
            add(5f);
            add(5f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //5 платформа - вправо-влево
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(30.5f, 5.3f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(30.5f, 5.3f));        
        points.add(new Vector2(40f, 5.3f));                
        times = new ArrayList<Float>(){{
            add(6f);
            add(6f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //6 платформа - вверх-вниз - над пещерой
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(61f, 4f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(61f, 4f));    
        points.add(new Vector2(61f, 5f));
        points.add(new Vector2(61f, 18f));
        points.add(new Vector2(61f, 19f));                
        times = new ArrayList<Float>(){{
            add(3f);
            add(5f);
            add(5f);
            add(3f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //7 платформа - зигзаг - в пещеруе
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(64.5f, 1f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(64.5f, 1f));    
        points.add(new Vector2(71.2f, 3f));
        points.add(new Vector2(64.5f, 5f));
        points.add(new Vector2(71.2f, 7f));
        points.add(new Vector2(64.5f, 9f));        
        points.add(new Vector2(71.2f, 11f));
        points.add(new Vector2(64.5f, 13f));
        points.add(new Vector2(71.2f, 15f));
        points.add(new Vector2(64.5f, 17f));               
        times = new ArrayList<Float>(){{
            add(7f);
            add(7f);
            add(7f);
            add(7f);
            add(7f);
            add(7f);
            add(7f);
            add(7f);
            add(7f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //8 платформа - вверх-вниз - в пещере
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(64f, 17f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(64f, 17f)); 
        points.add(new Vector2(64f, 1f));               
        times = new ArrayList<Float>(){{
            add(13f);
            add(13f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //9 платформа - конем - за пещерой
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(83.2f, 0f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(83.2f, 0f)); 
        points.add(new Vector2(83.2f, 12.2f));      
        points.add(new Vector2(86.5f, 12.2f));
        times = new ArrayList<Float>(){{
            add(8f);
            add(8f);
            add(6f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //10 платформа - вправо-влево - между маленькими платформами
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(103.8f, 4.5f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(103.8f, 4.5f));    
        points.add(new Vector2(116.4f, 4.5f));                
        times = new ArrayList<Float>(){{
            add(8f);
            add(8f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //11 платформа - вверх-вниз - от маленьких платформ до самого верха
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(115f, 5.5f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(115f, 5.5f));    
        points.add(new Vector2(115f, 20f));                
        times = new ArrayList<Float>(){{
            add(8f);
            add(8f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //12 платформа - вверх-вниз - до средней платформы
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(112f, 8f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(112f, 8f));    
        points.add(new Vector2(112f, 13));                
        times = new ArrayList<Float>(){{
            add(6f);
            add(6f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //13 платформа - вверх-вниз - от маленьких платформ просто так
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(107f, 7f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(107f, 7f));    
        points.add(new Vector2(107f, 16));                
        times = new ArrayList<Float>(){{
            add(8f);
            add(8f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
        
        //14 платформа - по диагонали вниз-вверх
        bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.position.set(117f, 18f);
        bodyFactory = new PlatformBodyFactory(loader);
        //координаты передвижения
        points = new ArrayList<Vector2>();
        points.add(new Vector2(117f, 18f));    
        points.add(new Vector2(135f, 16));                
        times = new ArrayList<Float>(){{
            add(20f);
            add(20f);
        }};
        path = new Path(points,times,PathType.ROUNDTRIP);
        entity = EntityFactory.createPlatform(world, assets, path);
        body = bodyFactory.create(world, bodyDefinition);
        entity.setBody(body);
        body.setUserData(entity);
        platforms.add(entity);
               
    }

    public void update(final Batch batch, final Rectangle viewBounds) {
        for (Entity e : platforms) {
            if (viewBounds == null) {
                e.update(null);
                e.step(batch);
            } else {
                Vector2 v = e.getBody().getPosition();
                if (viewBounds.contains(e.getBody().getPosition())) {
                    e.getBody().setActive(true);
                    e.update(null);
                    e.step(batch);
                } else {
                    //e.getBody().setActive(true);
                    e.step(batch);
                    //e.getBody().setActive(false);
                    //e.update(null);                    
                }
            }
        }
    }

    public void update(final Batch batch) {
        update(batch, null);
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if(msg.message == MessageType.GAME_OVER.code() || msg.message == MessageType.FINISH_LEVEL.code()){
            platforms.clear();
        }
        return true;
    }

}
