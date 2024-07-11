package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;


public class FinalScreen implements Screen {
    private MyGdxGame myGdxGame;
    private Stage stage;
    private TimeMachineActor timeMachineActor;
    private Texture timeMachineTexture;
    private Texture portalTexture;
    private Label messageLabel;

    public FinalScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        stage = new Stage(new ScreenViewport());

        // Загрузка текстур
        timeMachineTexture = new Texture(Gdx.files.internal("images/car.png"));
        portalTexture = new Texture(Gdx.files.internal("images/portal.png"));

        // Создание актера для машины времени
        timeMachineActor = new TimeMachineActor(timeMachineTexture);
        stage.addActor(timeMachineActor);

        // Настройка лейбла с текстом
        BitmapFont font = new BitmapFont(); // Используйте ваш шрифт
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE);
        messageLabel = new Label("Hope you enjoyed the game. More to come soon!", labelStyle);
        messageLabel.setAlignment(Align.center);
        messageLabel.setVisible(false); // Скрываем текст по умолчанию
        messageLabel.setFontScale(3f);

        Table table = new Table();
        table.setFillParent(true);
        table.add(messageLabel).expand().center();
        stage.addActor(table);
    }

    @Override
    public void show() {
        // Добавление действий для анимации машины времени
        float duration = 5f; // Продолжительность анимации

        Action moveAction = Actions.moveTo(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50, duration);
        Action rotateAction = Actions.rotateBy(180, duration);
        Action scaleAction = Actions.scaleTo(0.1f, 0.1f, duration);
        Action showMessageAction = new Action() {
            @Override
            public boolean act(float delta) {
                messageLabel.setVisible(true);
                return true;
            }
        };

        timeMachineActor.addAction(Actions.sequence(
                Actions.parallel(moveAction, rotateAction, scaleAction),
                showMessageAction
        ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.getBatch().begin();
        stage.getBatch().draw(portalTexture, Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 400, 400, 400);
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        timeMachineTexture.dispose();
        portalTexture.dispose();
    }
}