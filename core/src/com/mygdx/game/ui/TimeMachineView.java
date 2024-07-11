package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;

public class TimeMachineView extends Table {
    private ProgressBar progressBar;
    private float progress;
    private static final float MAX_PROGRESS = 100f;

    public TimeMachineView(MyGdxGame myGdxGame) {
        Skin skin = new Skin();
        Texture progressBarBackgroundTexture = new Texture("path/to/progress_bar_background.png");
        Texture progressBarKnobTexture = new Texture("path/to/progress_bar_knob.png");

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle(
                new TextureRegionDrawable(progressBarBackgroundTexture),
                new TextureRegionDrawable(progressBarKnobTexture)
        );

        progressBar = new ProgressBar(0, MAX_PROGRESS, 1, false, progressBarStyle);
        progressBar.setValue(progress);

        this.add("Time Machine Progress").row();
        this.add(progressBar).width(300).height(50);

        this.setFillParent(true);
    }

    public void addProgress(float value) {
        progress = Math.min(progress + value, MAX_PROGRESS);
        progressBar.setValue(progress);
    }

    public float getProgress() {
        return progress;
    }

    public boolean isCompleted() {
        return progress >= MAX_PROGRESS;
    }
}
