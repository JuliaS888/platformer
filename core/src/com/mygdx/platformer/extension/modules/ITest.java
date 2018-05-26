/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.extension.modules;

import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.wind.Wind;

/**
 *
 * @author Julia
 */
public interface ITest {
    Wind getWind();
    void setSantaState(SantaState state);
}
