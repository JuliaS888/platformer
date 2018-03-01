/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.platformer.progress.ProgressStatus;
import com.mygdx.platformer.progress.ProgressStatusObserver;

/**
 *
 * @author Julia
 */
public class ProgressBar implements ProgressStatusObserver{
    private final Stage overlay;
    private final Label progressLabel;
    public ProgressBar(final Batch batch){
        overlay = new Stage(new ScreenViewport(), batch);
        
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Xoxoxa.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVW0123456789-%";
        BitmapFont font = generator.generateFont(parameter);
        style.font = font;
        generator.dispose();
        
        progressLabel = new Label("Progress - 0 per",style);
        
        Table table = new Table();
        table.add(progressLabel);
        table.setFillParent(true);
        table.bottom();

        overlay.addActor(table);
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

    @Override
    public void onProgressStatusChange(ProgressStatus event) {
        progressLabel.setText("Progress - "+event.getPersent()+" per");
    }
}
