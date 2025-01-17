package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import com.mygdx.game.utility.GameSettings;

public abstract class GameObject {
    public int width, height;
    public Body body;
    public short cBits;

    Texture texture;

    protected GameObject(String texturePath, float x, float y, int width, int height, short cBits, int density, World world) {
        this.width = width;
        this.height = height;
        this.cBits = cBits;

        texture = new Texture(texturePath);
        body = createBody(x, y, density, world);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
    }

    public Texture getTexture() {
        return texture;
    }

    public int getX() {
        return (int) (body.getPosition().x / GameSettings.SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / GameSettings.SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * GameSettings.SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * GameSettings.SCALE, 0);
    }

    public void hit(int x) {
        // all physics objects could be hit
    }

    public void kill() {

    }

    public void returnXY() {

    }

    private Body createBody(float x, float y, int density, World world) {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Math.max(width, height) * GameSettings.SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = cBits;

        body.resetMassData();

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        circleShape.dispose();

        body.setTransform(x * GameSettings.SCALE, y * GameSettings.SCALE, 0);
        return body;
    }

    public void dispose() {
        texture.dispose();
    }
}
