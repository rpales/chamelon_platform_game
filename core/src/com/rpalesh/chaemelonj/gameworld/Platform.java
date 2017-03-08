package com.rpalesh.chaemelonj.gameworld;

        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.TextureAtlas;
        import com.badlogic.gdx.math.Rectangle;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;
        import com.badlogic.gdx.physics.box2d.EdgeShape;
        import com.badlogic.gdx.physics.box2d.Filter;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.FixtureDef;
        import com.badlogic.gdx.physics.box2d.World;
        import com.rpalesh.chaemelonj.ChameleonJ;
        import com.rpalesh.chaemelonj.sprites.Chameleon;

        import java.util.Random;
//
///**
// * Platform class defines PLATFORMS of ChameleonJ box2d world
// */

public class Platform{
    // platforms spacing
    public static float addedSpacing = 0.5f;

    // b2d variables
    private Body b2body;
    private EdgeShape edgeShape;

    // world variables
    private Chameleon player;
    public World world;

    // platform instance attributes
    private Color color;
    private int width;

    // associated insect
    public Insect insect;

    // utils
    private Random rand;

    public Texture texture;

    // DEV ONLY
    private Rectangle rect;

    public Platform(World world, Chameleon player) {
        rand = new Random();

        this.world = world;
        this.player = player;

        this.color = rand.nextBoolean() ? Color.GREEN : Color.RED;

        definePlatform();

        // DEVELOPMENT RECTANGLE DRAW - THIS WILL BE REPLACED BY TEXTURE
        float addedSpacing = Platform.addedSpacing * ChameleonJ.PPM;

        rect = new Rectangle(((-width/2) + (addedSpacing)) / ChameleonJ.PPM,
                82 / ChameleonJ.PPM, width / ChameleonJ.PPM, 2 / ChameleonJ.PPM);

        // create associated insect
        this.insect = new Insect(this, this.player);

        // increase platform spacing to generate next instance 200 (2) to the right
        Platform.addedSpacing += 2;
    }

    private void definePlatform(){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.KinematicBody;

        // random width: 100, 110, 120, ..., 170 (8 possible lengths)
        width = rand.nextInt(7) * 10 + 100;

        bdef.position.set(player.b2body.getPosition().x + Platform.addedSpacing, player.b2body.getPosition().y - (rand.nextInt(3) + 2) / 10f);

        b2body = world.createBody(bdef);

        edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(getWidth() / -2, -0f),
                        new Vector2(getWidth() / 2, -0f));

        fdef.shape = edgeShape;

        // sensor fixture
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("platform");

        // actual platform fixture
        fdef.isSensor = false;
        fdef.filter.categoryBits = ChameleonJ.PLATFORM_BIT;
        fdef.filter.maskBits = ChameleonJ.PLAYER_BIT;
        b2body.createFixture(fdef).setUserData(this);

        String textureName = toString()+".png";
        texture = new Texture(textureName);
    }

    public void update() {
        if ( player.getX() - b2body.getPosition().x > 2)
            resetPosition();
    }

    // reset platform position
    private void resetPosition(){
        // random color
        color = rand.nextBoolean() ? Color.GREEN : Color.RED;
        reSetTexture();
        // random yPosition
        int yPosition = rand.nextInt(100) + 30;

        // random angle
//        float angle = rand.nextBoolean() ? (rand.nextInt(30)-15) / 100f : 0f;
        // reposition platform
        b2body.setTransform(player.getX()+ addedSpacing - 2.5f, yPosition / ChameleonJ.PPM, 0f);
        // reposition insect
        insect.reset();

        // DEVELOPMENT ONLY
        rect.setPosition(b2body.getPosition().x-((rect.getWidth()+0.05f)/2), b2body.getPosition().y - (rect.getHeight()/2));
    }

    // getters

    public Vector2 getPosition() {
        return b2body.getPosition();
    }

    public Rectangle getRect(){
        return this.rect;
    }

    public Color getColor(){
        return this.color;
    }

    public void playerContact(){
        player.setJumpsLeft(2);
    }

    public float getWidth(){
        return this.width / ChameleonJ.PPM;
    }

    // NOT USED

    public String toString(){
        char platformWidth = String.valueOf(getWidth()).charAt(String.valueOf(getWidth()).length()-1);
        return getColor().toString() + "-" + platformWidth;
    }
    public void reSetTexture(){
        this.texture.dispose();
        this.texture = new Texture(toString()+".png");
    }
    public Texture getTexture(){
        return this.texture;
    }

    public void setTransform(float xPos, float yPos, float angle){
        b2body.setTransform(xPos, yPos, angle);
    }

    public void setCategoryBits(short colorBit){
        Filter b2filter = b2body.getFixtureList().first().getFilterData();
        b2filter.categoryBits = colorBit;
        b2body.getFixtureList().first().setFilterData(b2filter);
    }

    public short getCategoryBits(){
        return b2body.getFixtureList().first().getFilterData().categoryBits;
    }

    public void setMaskBits(short colorBit){
        Filter b2filter = b2body.getFixtureList().first().getFilterData();
        b2filter.maskBits = colorBit;
        b2body.getFixtureList().first().setFilterData(b2filter);
    }

    public void dispose(){
        texture.dispose();
        edgeShape.dispose();
    }
}