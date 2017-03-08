package com.rpalesh.chaemelonj.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rpalesh.chaemelonj.gameworld.Insect;
import com.rpalesh.chaemelonj.gameworld.Platform;

/**
 * Created by rpalesh on 09/10/16.
 */
public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        insectContact(fixA, fixB);

        if(fixA.getUserData() == "platform" || fixB.getUserData() == "platform"){
            Fixture platfromFixture = fixA.getUserData() == "platform" ? fixA : fixB;
            ((Platform) platfromFixture.getBody().getFixtureList().peek().getUserData()).playerContact();
//            System.out.println((platfromFixture.getBody().getFixtureList().peek().getUserData()).toString());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        // if player changes color when contact has began is still able to eat
        // THIS MAY BE CHANGED
        insectContact(fixA, fixB);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void insectContact(Fixture fixA, Fixture fixB){
        if(fixA.getUserData() == "insect" || fixB.getUserData() == "insect"){
            Fixture insectFixture = fixA.getUserData() == "insect" ? fixA : fixB;
            ((Insect) insectFixture.getBody().getFixtureList().peek().getUserData()).playerContact();
        }
    }
}
