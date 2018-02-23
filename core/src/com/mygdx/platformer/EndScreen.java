/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.platformer.input.EndScreenInputProcessor;

/**
 *
 * @author Julia
 */
public class EndScreen extends AbstractScreen {
    private static final String TITLE = "Santa's Gifts";
    private final Stage stage;
    
    //endGame - 0 - game over, 1 - finish
    public EndScreen(PlatformManGame game,int endGame) {
        super(game);
        //стиль текста
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pacifico.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 31;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!':";
        BitmapFont font = generator.generateFont(parameter);
        style.font = font;
        generator.dispose();
        
        //название
        Label titleLabel = new Label(TITLE,style);
        Table table = new Table();
        Image santa_logo = new Image(game.getAssetsManager().get(Assets.SANTA_LOGO));
        table.add(santa_logo).colspan(2).row();
        table.add(titleLabel).padBottom(20.0f).colspan(2).row();
        Label endGameLabel;
        if(endGame == 0){
            endGameLabel = new Label("Game over!",style);
        }else{
            endGameLabel = new Label("Finish!",style);
        }
        table.add(endGameLabel).padBottom(20.0f).colspan(2).row();
        Label pointLabel = new Label("Score: "+Integer.toString(game.getPlayerStatus().getScore()),style);
        table.add(pointLabel).padBottom(20.0f).colspan(2).row();
        table.add(new Label("Press Enter...",style)).padBottom(60.0f).colspan(2).row();
        table.setFillParent(true);
        
        stage = new Stage(new ScreenViewport(), game.getBatch());
        stage.addActor(table);
        stage.setKeyboardFocus(table);
        Gdx.input.setInputProcessor(new EndScreenInputProcessor());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
