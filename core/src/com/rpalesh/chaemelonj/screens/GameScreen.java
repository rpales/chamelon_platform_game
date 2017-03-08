package com.rpalesh.chaemelonj.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rpalesh.chaemelonj.ChameleonJ;

/**
 * All GameScreen subclasses require GameScreenManager instance and update method (keeping update
 * logic and render logic separated).
 */
public abstract class GameScreen implements Screen {
    protected GameScreenManager gsm;


    public GameScreen(GameScreenManager gsm){
        this.gsm = gsm;
    }
}
