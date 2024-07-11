package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TimeMachineActor extends Actor {
    private Sprite sprite;

    public TimeMachineActor(Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(100, 100);
        setSize(100, 100);
        setPosition(0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.setRotation(getRotation());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.draw(batch);
    }
}
