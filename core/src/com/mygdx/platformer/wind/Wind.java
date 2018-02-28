/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.wind;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.platformer.player.CurrentPlayerStatus;
import com.mygdx.platformer.player.PlayerStatus;
import com.mygdx.platformer.player.PlayerStatusObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Julia
 */
public class Wind implements WindStatus,Poolable{
    private double speed;
    private WindType type;
    //private final Array<Integer> timeChange;
    //private final Map<Integer,Double> windChange;
    public Wind(){
        reset();
    }
    
    protected void setSpeed(double speed){
        this.speed = speed;
        if(Math.abs(speed) < 1.75f){
            type =  WindType.calm;
        }
        else if(Math.abs(speed) < 3.5f){
            type =  WindType.breeze;
        }
        else if(Math.abs(speed) < 5.25f){
            type =  WindType.high_wind;
        }
        else {
            type =  WindType.storm;
        }
    }
        
    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void reset() {
        setSpeed(DEFAULT_SPEED);
    }

    @Override
    public WindType getType() {
        return type;
    }
    
    public void update(){
        Random r = new Random();
        int sign = r.nextInt(10);
        double value = 7*Math.round(r.nextDouble()*100)/100;
                
        if(sign%2==0)
            setSpeed(value);
        else{
            setSpeed((-1)*value);
        }
    }

}
