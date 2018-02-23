/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.entity;

/**
 *
 * @author Julia
 */
public enum Direction {
    /**
     * The {@link Entity} is facing left.
     */
    LEFT("left"),
    /**
     * The {@link Entity} is facing right.
     */
    RIGHT("right");

    /**
     * A human readable description of the value of the enum. Useful for printing and debugging.
     */
    private String direction;

    private Direction(final String direction) {
            this.direction = direction;
    }

    public String direction() {
            return direction;
    }

    @Override
    public String toString() {
            return direction;
    }
}
