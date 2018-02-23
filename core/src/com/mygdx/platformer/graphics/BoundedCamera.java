/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 *
 * @author Julia
 */
public class BoundedCamera extends OrthographicCamera {
    private float xmin;
    private float xmax;
    
    private float ymin;
    private float ymax;

    public BoundedCamera() {
        this(0.0f, 0.0f,0.0f, 0.0f);
    }

    public BoundedCamera(final float xmin, final float xmax,final float ymin, final float ymax) {
        super();
        setBounds(xmin, xmax,ymin, ymax);
    }

    public void setBounds(final float xmin, final float xmax,final float ymin, final float ymax) {
        this.xmin = xmin;
        this.xmax = xmax;
        
        this.ymin = ymin;
        this.ymax = ymax;
        
        //this.ymax = 5;
    }

    @Override
    public void update(final boolean updateFrustum) {
        fixBounds();
        super.update(updateFrustum);
    }

    private void fixBounds() {
        if (position.x < xmin + viewportWidth / 2.0f) {
                position.x = xmin + viewportWidth / 2.0f;
        }
        if (position.x > xmax - viewportWidth / 2.0f) {
                position.x = xmax - viewportWidth / 2.0f;
        }
        
        if (position.y < ymin + viewportHeight / 2.0f) {
                position.y = ymin + viewportHeight / 2.0f;
        }
        if (position.y > ymax - viewportHeight / 2.0f) {
                position.y = ymax - viewportHeight / 2.0f;
        }
    }
}
