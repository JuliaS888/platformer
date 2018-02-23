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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.platformer.Assets;
import com.mygdx.platformer.player.PlayerStatus;
import com.mygdx.platformer.player.PlayerStatusObserver;

/**
 *
 * @author Julia
 */
public class StatusBar implements PlayerStatusObserver{
    private static final String THREE_DIGITS = "%03d";
    private static final String EIGHT_DIGITS = "%08d";
    private static final String TWO_DIGITS = "%02d";
    private static final String NUMBER_GLYPHS = "0123456789";
    private static final String TIME_REGION = "clock_l";
    private static final String LIVES_REGION = "head_l";
    private static final String SMALL_GIFT_REGION = "gift_l";

    private final Stage overlay;
    private final Label collectiblesLabel;
    private final Label livesLabel;
    private final Label scoreLabel;
    private final Label timeLabel;
    
    public StatusBar(final Batch batch, final AssetManager assets) {
        overlay = new Stage(new ScreenViewport(), batch);

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Xoxoxa.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'";
        BitmapFont font = generator.generateFont(parameter);
        style.font = font;
        generator.dispose();

        collectiblesLabel = new Label(String.format(TWO_DIGITS, 0), style);
        livesLabel = new Label(String.format(TWO_DIGITS, 0), style);
        scoreLabel = new Label(String.format(EIGHT_DIGITS, 0), style);
        timeLabel = new Label(String.format(THREE_DIGITS, 0), style);

        TextureAtlas hudAtlas = assets.get(Assets.SANTA_ATLAS);

        Table table = new Table();
        table.add(new Image(hudAtlas.findRegion(SMALL_GIFT_REGION))).padRight(8.0f);
        table.add(collectiblesLabel).bottom();
        table.add(new Image(hudAtlas.findRegion(LIVES_REGION))).padLeft(15.0f);
        table.add(livesLabel).bottom();
        table.add(scoreLabel).expandX();
        table.add(new Image(hudAtlas.findRegion(TIME_REGION))).padRight(12.0f);
        table.add(timeLabel);
        table.setFillParent(true);
        table.top();
        table.pad(15.0f);

        overlay.addActor(table);
    }

    @Override
    public void onPlayerStatusChange(PlayerStatus event) {
        collectiblesLabel.setText(String.format(TWO_DIGITS, event.getCollectibles()));
        scoreLabel.setText(String.format(EIGHT_DIGITS, event.getScore()));
        timeLabel.setText(String.format(THREE_DIGITS, event.getTime()));
        livesLabel.setText(String.format(TWO_DIGITS, event.getLives()));
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
