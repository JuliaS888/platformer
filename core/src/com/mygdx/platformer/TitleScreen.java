/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author Julia
 */
public class TitleScreen extends AbstractScreen{
    private static final String TITLE = "Santa's Gifts";
    private static final String BEGIN_OPTION = "Start";
    private static final String EXIT_OPTION = "Exit game";

    private final Stage stage;
    public TitleScreen(final PlatformManGame game) {
        super(game);
        
        //стиль текста
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pacifico.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'";
        BitmapFont font = generator.generateFont(parameter);
        style.font = font;
        generator.dispose();
        
        //название
        Label titleLabel = new Label(TITLE,style);
        Table table = new Table();
        Image santa_logo = new Image(game.getAssetsManager().get(Assets.SANTA_LOGO));
        table.add(santa_logo).colspan(2).row();
        table.add(titleLabel).padBottom(60.0f).colspan(2).row();
        
        final Image exitIcon = new Image(game.getAssetsManager().get(Assets.GIFT_LOGO));
        exitIcon.setVisible(false);
        final Image beginIcon = new Image(game.getAssetsManager().get(Assets.GIFT_LOGO));
        
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        
        TextButton beginButton = new TextButton(BEGIN_OPTION, buttonStyle);
        //добавить слушателя
        beginButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                game.setScreen(new LevelScreen(game));
            }

            @Override
            public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                beginIcon.setVisible(true);
                exitIcon.setVisible(false);
            }
        });
        
        TextButton exitButton = new TextButton(EXIT_OPTION, buttonStyle);
        //добавить слушателя
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Gdx.app.exit();
            }

            @Override
            public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                beginIcon.setVisible(false);
                exitIcon.setVisible(true);
            }
        });
        
        table.add(beginIcon);
        table.add(beginButton).right().row();
        table.add(exitIcon);
        table.add(exitButton).right().row();
        table.setFillParent(true);
        //добавить слушателя
        table.addListener(new InputListener() {
            @Override
            public boolean keyDown(final InputEvent event, final int keycode) {
                switch (keycode) {
                case Keys.W:
                case Keys.UP:
                case Keys.S:
                case Keys.DOWN:
                    beginIcon.setVisible(!beginIcon.isVisible());
                    exitIcon.setVisible(!exitIcon.isVisible());
                    break;
                case Keys.SPACE:
                case Keys.ENTER:
                case Keys.BUTTON_A:
                    if (exitIcon.isVisible()) {
                        Gdx.app.exit();
                    } else if (beginIcon.isVisible()) {
                        game.setScreen(new LevelScreen(game));
                    }
                    break;
                }
                return super.keyDown(event, keycode);
            }
        });
        
        stage = new Stage(new ScreenViewport(), game.getBatch());
        stage.addActor(table);
        stage.setKeyboardFocus(table);
        Gdx.input.setInputProcessor(stage);        
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
