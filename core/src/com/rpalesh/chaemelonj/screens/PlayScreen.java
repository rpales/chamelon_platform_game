package com.rpalesh.chaemelonj.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rpalesh.chaemelonj.ChameleonJ;
import com.rpalesh.chaemelonj.gameworld.Platform;
import com.rpalesh.chaemelonj.helpers.WorldContactListener;
import com.rpalesh.chaemelonj.scenes.Hud;
import com.rpalesh.chaemelonj.scenes.Pause;
import com.rpalesh.chaemelonj.sprites.Chameleon;

/**
 * Created by rpalesh on 16/09/16.
 */
public class PlayScreen extends GameScreen {
    private ChameleonJ game;
//    private TextureAtlas atlas;

    private Hud hud;
    private OrthographicCamera gamecam;

    //box3d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Chameleon player;

    private Array<Platform> platforms;

    private ShapeRenderer renderer;
    private FitViewport gamePort;

    // paused variables
    private boolean isPaused;
    private Pause pause;

    public PlayScreen(ChameleonJ game, GameScreenManager gsm){
        super(gsm);

        this.game = game;

        isPaused = false;
        pause = new Pause(game.batch);

        // Hud for score, level, lives info
        hud = new Hud(game.batch);

        gamecam = new OrthographicCamera();
        // fitviewport to maintain aspect ratio despite device
        gamePort = new FitViewport(ChameleonJ.V_WIDTH / ChameleonJ.PPM, ChameleonJ.V_HEIGHT / ChameleonJ.PPM, gamecam);
        //set cam centered
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -9), true);
        b2dr = new Box2DDebugRenderer();

        // create player
        player = new Chameleon(world);

        // create platforms & insects
        platforms = new Array();

        for(int i=0; i<7; i+=1){
            platforms.add(new Platform(world, player));
        }

        renderer = new ShapeRenderer();

        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(player.getJumpsLeft() > 1) {
                player.b2body.applyLinearImpulse(new Vector2(0, 3.75f), player.b2body.getWorldCenter(), true);
                player.setJumpsLeft(1);
            }
            else if(player.getJumpsLeft() == 1){
                player.b2body.applyLinearImpulse(new Vector2(0, 1.25f), player.b2body.getWorldCenter(), true);
                player.setJumpsLeft(0);
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.getColor() == Color.GREEN) {
                player.setColor(Color.RED);
            }
            else{
                player.setColor(Color.GREEN);
            }
        }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);

        //takes 1 step in the physics simulation (60 times per second)
        world.step(1/60f, 6, 2);

        //update world elements
        for(Platform platform : platforms){
            platform.update();
        }

        //update player
        player.update(dt);

        //update hud
        hud.update(dt);

        //cam follows player (with offset)
        gamecam.position.x = player.b2body.getPosition().x + (130/ChameleonJ.PPM);

        //update camera
        gamecam.update();
    }

    @Override
    public void render(float delta) {
        // clear screen with black background
        Gdx.gl.glClearColor(0.175f, 0.238f, 0.238f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        // shapeRenderer
        renderer.setProjectionMatrix(gamecam.combined);
        for(Platform platform : platforms){
//            renderer.begin(ShapeRenderer.ShapeType.Filled);
//            renderer.setColor(platform.getColor());
//            renderer.rect(platform.getRect().x, platform.getRect().y, platform.getRect().width, platform.getRect().height);
//            renderer.end();

            // render insects
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(platform.insect.getColor());
//            renderer.circle(platform.insect.getBody().getPosition().x, platform.insect.getBody().getPosition().y, 2 / ChameleonJ.PPM, 8);
            renderer.end();
        }

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        // draw all elements
        for(Platform platform : platforms) {
            game.batch.draw(platform.texture, platform.getPosition().x - platform.getWidth()/2 -0.02f, platform.getPosition().y - 0.23f, platform.getWidth() + 0.07f, 0.30f);
            game.batch.draw(platform.insect.texture, platform.insect.getPosition().x - 0.1f, platform.insect.getPosition().y - 0.12f, 0.20f, 0.20f);
        }
        player.draw(game.batch);
        game.batch.end();

        // if game is over change to GameOverScreen
        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        // if pause button is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause();
        }

        // update() only if isPaused false
        if(isPaused){
            game.batch.setProjectionMatrix(pause.stage.getCamera().combined);
            pause.stage.draw();
            game.batch.setProjectionMatrix(gamecam.combined);
        } else {
            //separate update logic from render
            update(delta);

            //Set batch to draw what the Hud camera sees
            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();
        }
    }

    public boolean gameOver(){
        return player.isDead();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
        isPaused = isPaused ? false : true;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        for(Platform platform : platforms){
            platform.dispose();
        }
        Platform.addedSpacing = 0.5f;
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        pause.dispose();
    }
}
