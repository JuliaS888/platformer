/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.wind;

/**
 *
 * @author Julia
 */
public interface WindStatusObserver {
    void onSpeedObserver(final WindStatus event);
}
