/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.wind;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.platformer.player.PlayerStatus;
import com.mygdx.platformer.player.PlayerStatusObserver;

/**
 *
 * @author Julia
 */
public class WindStatusProcessor{
    private final Array<WindStatusObserver> observers;
    private Wind wind;
    private long startTime;
    private float elapsedTime;
    private int frequency;
    public WindStatusProcessor(final Wind wind,int frequencyChangeInSecond) {
        observers = new Array<WindStatusObserver>();
        this.wind = wind;
        startTime=TimeUtils.millis();
        frequency = frequencyChangeInSecond;
    }
        
    public void update(){
        elapsedTime = TimeUtils.timeSinceMillis(startTime);
        if (elapsedTime > frequency * 1500f) {
            wind.update();
            notifyObservers();
            startTime=TimeUtils.millis();
        }
    }
    
    public void addObserver(final WindStatusObserver observer) {
        if (observer != null && !observers.contains(observer, true)) {
            observers.add(observer);
            observer.onSpeedObserver(wind);
        }
    }
    
    public void removeObserver(final WindStatusObserver observer) {
        if (observer != null) {
            observers.removeValue(observer, true);
        }
    }

    protected void notifyObservers() {
        for (WindStatusObserver o : observers) {
            o.onSpeedObserver(wind);
        }
    }
}
