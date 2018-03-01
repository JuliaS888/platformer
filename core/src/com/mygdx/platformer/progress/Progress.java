/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.progress;

/**
 *
 * @author Julia
 */
public class Progress implements ProgressStatus{
    private int persent;
    
    public Progress(){
        persent = 0;
    }
    @Override
    public int getPersent() {
        return persent;
    }
    
    protected void setPersent(int persent) {
        this.persent = persent;
    }
    
}
