/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.extension;

import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.wind.Wind;
import com.mygdx.platformer.wind.WindType;

/**
 *
 * @author Julia
 */
public interface IBot {
    void runRight();
    boolean isInStateSanta(SantaState state);
    WindType getWindType();
    double getWindSpeed();
}
