/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.platformer.Assets;
import com.mygdx.platformer.wind.WindStatus;
import com.mygdx.platformer.wind.WindStatusObserver;
import com.mygdx.platformer.wind.WindType;

/**
 *
 * @author Julia
 */
public class WindBar implements WindStatusObserver{
    private final Stage overlay;
    private final Label windLabel;
    private Image arrow_right;
    private Image arrow_left;
    private Image arrow_around;
    private Image arrow;
    private Table table;
    
    public WindBar(final Batch batch, final AssetManager assets){
        overlay = new Stage(new ScreenViewport(), batch);
        
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Xoxoxa.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-.!:";
        BitmapFont font = generator.generateFont(parameter);
        style.font = font;
        generator.dispose();
        
        windLabel = new Label("WIND - CALM",style);
        arrow_right = new Image(assets.get(Assets.ARROW_RIGHT));
        arrow_left = new Image(assets.get(Assets.ARROW_LEFT));
        arrow_around = new Image(assets.get(Assets.ARROW_AROUND_LOGO));
        arrow = arrow_around;
                
        table = new Table();
        table.add(windLabel);
        table.add(arrow).padLeft(10);
        table.setFillParent(true);
        table.top();
        table.padTop(70);

        overlay.addActor(table);
    }

    @Override
    public void onSpeedObserver(WindStatus event) {
        WindType type = event.getType();
        double speed = event.getSpeed();
        if(speed==0)
            arrow = arrow_around;
        else if(speed > 0)
            arrow = arrow_right;
        else
            arrow = arrow_left;
        table.clearChildren();
        windLabel.setText("WIND - "+type.toString());
        table.add(windLabel);
        table.add(arrow).padLeft(10);
    }
    
    public void resize(final int width, final int height) {
        overlay.getViewport().update(width, height, true);
    }

    public void render() {
        overlay.act();
        overlay.draw();
    }

    public void dispose() {
        overlay.dispose();
    }
}
