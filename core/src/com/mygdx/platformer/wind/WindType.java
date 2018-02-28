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
public enum WindType {
    calm(1), //штиль
    breeze(2),//слабый ветер - бриз
    high_wind(3), //сильный ветер
    storm(4); //шторм
    
    private int code;
    
    private WindType(final int code) {
        this.code = code;
    }
    
    @Override
    public String toString(){
        switch(code){
            case 1:
                return "CALM";
            case 2:
                return "BREEZE";
            case 3:
                return "HIGH";
            case 4:
                return "STORM";
        }
        return "";
    }
}
