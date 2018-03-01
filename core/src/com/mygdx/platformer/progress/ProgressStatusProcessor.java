/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.progress;

import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Julia
 */
public class ProgressStatusProcessor {
    private final Array<ProgressStatusObserver> observers;
    private Progress pr;
    private float widthX;
    public ProgressStatusProcessor(Progress progress,float curX,float widthMap){
        observers = new Array<ProgressStatusObserver>();
        widthX = widthMap;
        pr = progress;
        update(curX);
    }
    
    public void update(float positionX){
        int per = Math.round((positionX*100)/widthX);
        if(pr.getPersent() != per){
            pr.setPersent(per);
            notifyObservers();
        }
    }
    
    public void addObserver(final ProgressStatusObserver observer) {
        if (observer != null && !observers.contains(observer, true)) {
            observers.add(observer);
            observer.onProgressStatusChange(pr);
        }
    }
    
    public void removeObserver(final ProgressStatusObserver observer) {
        if (observer != null) {
            observers.removeValue(observer, true);
        }
    }

    protected void notifyObservers() {
        for (ProgressStatusObserver o : observers) {
            o.onProgressStatusChange(pr);
        }
    }
}
