package com.rpalesh.chaemelonj.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rpalesh.chaemelonj.ChameleonJ;

/**
 * Created by rpalesh on 03/10/16.
 */
public class Chameleon extends Sprite {
    public World world;
    public Body b2body;
    private Color color;
    private int jumpsLeft;

    private Array<Texture> standingTexture;
    private Array<Texture> eatingTexture;

    public enum State { STANDING, EATING};
    private State state;

    private int eatingTimer;

    private boolean isDead;

    public Chameleon(World world){
        this.world = world;
        color = Color.GREEN;
        jumpsLeft = 2;
        isDead = false;

        defineChameleon();
        setBounds(0, 0.2f, 37.5f / ChameleonJ.PPM, 20 / ChameleonJ.PPM);

        standingTexture = new Array<Texture>();
        standingTexture.add(new Texture("green-standing.png"));
        standingTexture.add(new Texture("red-standing.png"));

        eatingTexture = new Array<Texture>();
        eatingTexture.add(new Texture("green-eating.png"));
        eatingTexture.add(new Texture("red-eating.png"));

        state = State.STANDING;
        setRegion(getFrame());
        eatingTimer = 0;
    }

    public void update(float dt){
        b2body.setLinearVelocity(2f,b2body.getLinearVelocity().y);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 );
        setRegion(getFrame());

        // EATING STATE TIMER
        if (getState() == State.EATING){
            if(eatingTimer < 10){
                eatingTimer += 1;
            }
            else{
                eatingTimer = 0;
                setState(State.STANDING);
            }
        }
        if (b2body.getPosition().y < 0){
            isDead = true;
        }
    }

    public Texture getFrame(){
        state = getState();
        switch (state){
            case STANDING:
                return getColor() == Color.GREEN ? standingTexture.first() : standingTexture.peek();
            case EATING:
                return getColor() == Color.GREEN ? eatingTexture.first() : eatingTexture.peek();
            default:
                return getColor() == Color.GREEN ? standingTexture.first() : standingTexture.peek();
        }
    }

    private void defineChameleon(){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(0, (105) / ChameleonJ.PPM);

        b2body = world.createBody(bdef);

        CircleShape shape = new CircleShape();
        shape.setRadius(6/ ChameleonJ.PPM);


        // COLLISION LISTENER (FEET)

        fdef.filter.categoryBits = ChameleonJ.PLAYER_BIT;
        fdef.filter.maskBits = ChameleonJ.PLATFORM_BIT | ChameleonJ.DEFAULT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

//        EdgeShape feet = new EdgeShape();
//        feet.set(new Vector2(-4 / ChameleonJ.PPM, -6 / ChameleonJ.PPM), new Vector2(4 / ChameleonJ.PPM, -6 / ChameleonJ.PPM));
//        fdef.shape = feet;
//        fdef.isSensor = true;
//
//        b2body.createFixture(fdef).setUserData("feet");

        CircleShape tong = new CircleShape();
        tong.setRadius(20 / ChameleonJ.PPM);
        fdef.shape = tong;
        fdef.isSensor = true;

        b2body.createFixture(fdef);
    }

    public void setCategoryBits(short colorBit){
        Filter b2filter;
        Array<Fixture> fixtures = b2body.getFixtureList();
        for(Fixture fixture : fixtures){
            b2filter = fixture.getFilterData();
            b2filter.categoryBits = colorBit;
            fixture.setFilterData(b2filter);
        }
    }

    private void setMaskBits(short colorBit){
        Filter b2filter;
        Array<Fixture> fixtures = b2body.getFixtureList();
        for(Fixture fixture : fixtures){
            b2filter = fixture.getFilterData();
            b2filter.maskBits = colorBit;
            fixture.setFilterData(b2filter);
        }
    }

    public void setColor(Color color){
        this.color =  color;
    }

    public Color getColor(){
        return this.color;
    }

    public int getJumpsLeft() {
        return jumpsLeft;
    }

    public void setJumpsLeft(int jumpsLeft) {
        this.jumpsLeft = jumpsLeft;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return this.state;
    }

    public boolean isDead(){
        return isDead;
    }
}
