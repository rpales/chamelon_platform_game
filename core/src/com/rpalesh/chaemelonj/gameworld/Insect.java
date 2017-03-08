package com.rpalesh.chaemelonj.gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.rpalesh.chaemelonj.ChameleonJ;
import com.rpalesh.chaemelonj.scenes.Hud;
import com.rpalesh.chaemelonj.sprites.Chameleon;

/**
 * Created by rpalesh on 06/10/16.
 */
public class Insect extends Sprite {

    // public for deleting
    private Body b2body;
    private Platform platform;
    private boolean eaten;
    private Chameleon player;
    private Color color;

//    public Animation texture;
    public Texture texture;


    public Insect(Platform platform, Chameleon player) {
        this.platform = platform;
        this.eaten = false;
        this.player = player;
        this.color = platform.getColor();
        createInsect(this.platform);
    }

    public void reset(){
        b2body.setTransform(platform.getPosition().x, platform.getPosition().y + 0.2f, 0f);
        eaten = false;
        color = platform.getColor();
    }

    public void createInsect(Platform platform){
        BodyDef bdef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();

        // defining body type
        bdef.type = BodyDef.BodyType.KinematicBody;

        // positioning on top of platform
        bdef.position.set(platform.getPosition().x, platform.getPosition().y + 0.2f);

        // create body
        setB2body(platform.world.createBody(bdef));

        shape.setRadius(2/ ChameleonJ.PPM);
        fdef.shape = shape;

        // create sensor fixture
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("insect");

        // create fixture with Insect class object as userData
        fdef.isSensor = false;
        fdef.filter.categoryBits = ChameleonJ.INSECT_BIT;
        b2body.createFixture(fdef).setUserData(this);
//        Array<TextureRegion> frames = new Array<TextureRegion>();
//        frames.add(new TextureRegion(new Texture("fly-0.png")));
//        frames.add(new TextureRegion(new Texture("fly-1.png")));
//        texture = new Animation(0.1f, frames);
//        frames.clear();
        texture = new Texture("fly-0.png");

        shape.dispose();
    }

    private void setB2body(Body body){
        this.b2body = body;
    }

    public void playerContact(){
        if(!eaten && platform.getColor() == player.getColor()) {
            eaten = true;
            Hud.addScore(1);
            setColor(new Color(0.175f, 0.238f, 0.238f, 1));
            player.setState(Chameleon.State.EATING);
        }
    }
    public void setColor(Color color){
        this.color =  color;
    }

    public Color getColor(){
        return this.color;
    }

    public Body getBody(){
        return this.b2body;
    }

    public Vector2 getPosition() {
        return b2body.getPosition();
    }
}
