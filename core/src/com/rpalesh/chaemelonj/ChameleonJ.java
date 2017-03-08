package com.rpalesh.chaemelonj;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpalesh.chaemelonj.screens.GameScreenManager;
import com.rpalesh.chaemelonj.screens.MenuScreen;
import com.rpalesh.chaemelonj.screens.PlayScreen;

public class ChameleonJ extends Game {

    // screen measures
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;

    // scaling factor: box2d units are in meters
    public static final float PPM = 100;

	public SpriteBatch batch;

	private GameScreenManager gsm;

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short INSECT_BIT = 4;
	public static final short PLATFORM_BIT = 8;
	public static final short ENEMY_BIT = 16;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameScreenManager(this);
		gsm.push(new PlayScreen( this, gsm));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
