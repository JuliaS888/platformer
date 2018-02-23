/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.platformer.fsm.PathType;
import java.util.ArrayList;

/**
 *
 * @author Julia
 */
public class Path {
    private ArrayList<Vector2>  positions;
    private ArrayList<Float> times;
    private Vector2 velocity;
    private int currentPointIndex;
    private int nextPointIndex;
    private int direction;
    private static final float CHECK_RADIUS = 1f;
    
    PathType type;
    
    public Path(){
        positions=new ArrayList<Vector2>();
        times=new ArrayList<Float>();
        velocity=new Vector2();
        type = PathType.ROUNDTRIP;
        direction = 1;
    }
    
    public Path(ArrayList<Vector2> points,ArrayList<Float> time, PathType type){
        positions = points;
        times = time;
        velocity = new Vector2();
        this.type = type;
        direction = 1;
    }
    
    public void addPoint(Vector2 pos,float time){
        positions.add(pos);
        times.add(time);
    }
    
    public void reset(){
        currentPointIndex = 0;
        nextPointIndex = getNextPoint();
        setNextPointVelocity();
    }
    
    public boolean updatePath(Vector2 bodyPosition){
        return reachedNextPoint(bodyPosition);
    }
    
    public Vector2 getCurrentPoint(){
        return positions.get(currentPointIndex);
    }
    
    boolean reachedNextPoint(Vector2 bodyPosition){
        Vector2 nextPointPosition = positions.get(nextPointIndex);
        float d = nextPointPosition.dst2(bodyPosition);
        if(d < CHECK_RADIUS){
            currentPointIndex=nextPointIndex;
            nextPointIndex=getNextPoint();
            setNextPointVelocity();
            return true;
        }
        return false;
    } 

    int getNextPoint(){
        int nextPoint=currentPointIndex+direction;
        switch(type){
            case ROUND:
                if(nextPoint==positions.size())
                    nextPoint = 0;
            break;
            case ONEWAY:
                if(nextPoint==positions.size()){
                    nextPoint = positions.size()-1;
                    direction = 0;
                }
            break;
            case ROUNDTRIP:
                if(nextPoint==positions.size()){
                    direction = -1;
                    nextPoint=currentPointIndex+direction;
                }
                else if(nextPoint == -1){
                    direction = 1;
                    nextPoint=currentPointIndex+direction;
                }
            break;
        }
        return nextPoint;
    }
   void setNextPointVelocity(){
        Vector2 nextPosition=positions.get(nextPointIndex);
        Vector2 currentPosition=positions.get(currentPointIndex);
        float dx = nextPosition.x - currentPosition.x;
        float dy = nextPosition.y - currentPosition.y;
        float time = times.get(nextPointIndex);
        velocity.set(dx/time,dy/time);
    }

    public Vector2 getVelocity(){
        return velocity;
    }
}
