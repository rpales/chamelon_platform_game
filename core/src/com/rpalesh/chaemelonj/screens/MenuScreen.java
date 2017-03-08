package com.rpalesh.chaemelonj.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.rpalesh.chaemelonj.ChameleonJ;
import com.rpalesh.chaemelonj.sprites.Chameleon;

/**
 * Created by rpalesh on 12/10/16.
 */
public class MenuScreen extends GameScreen {
    private Texture background;
    private Texture playBtn;

    private ChameleonJ game;
    private OrthographicCamera gamecam;

    public MenuScreen(ChameleonJ game, GameScreenManager gsm){
        super(gsm);
        this.game = game;

        background = new Texture("bg.png");
        playBtn = new Texture("play.png");
        gamecam.setToOrtho(false, ChameleonJ.V_WIDTH, ChameleonJ.V_HEIGHT);
    }

    @Override
    public void render(float dt) {

        update(dt);
        Gdx.gl.glClearColor(0.175f, 0.238f, 0.238f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, ChameleonJ.V_WIDTH, ChameleonJ.V_HEIGHT);
        game.batch.draw(playBtn, (ChameleonJ.V_WIDTH/2) - (playBtn.getWidth() / 2), ChameleonJ.V_HEIGHT / 2);
        game.batch.end();
    }

    private void update(float dt){
        handleInput();
    }

    private void handleInput(){
        if (Gdx.input.justTouched()){
            gsm.set(new PlayScreen(game, gsm));
            dispose();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

    }
}
