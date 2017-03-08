package com.rpalesh.chaemelonj.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.util.Stack;

/**
 * Created by rpalesh on 12/10/16.
 */
public class GameScreenManager {
    public Stack<GameScreen> screens;
    public Game game;

    public GameScreenManager(Game game){
        screens = new Stack<GameScreen>();
        this.game = game;
    }

    public void push(GameScreen screen){
        screens.push(screen);
        game.setScreen(screens.peek());
    }

    public void pop(){
        screens.pop();
        game.setScreen(screens.peek());
    }

    public void set(GameScreen screen){
        screens.pop();
        screens.push(screen);
    }
}
