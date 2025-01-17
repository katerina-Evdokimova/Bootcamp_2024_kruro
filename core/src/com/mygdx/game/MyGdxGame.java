package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Managers.MemoryManager;
import com.mygdx.game.screens.DialogScreen;
import com.mygdx.game.screens.FinalScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.ui.FontBuilder;
import com.mygdx.game.utility.GameResources;
import com.mygdx.game.utility.GameSettings;

public class MyGdxGame extends Game {
	public World world;

	public BitmapFont commonWhiteFont, commonRedFont, largeWhiteFont, commonBlackFont, smallRedFont,
	largeRedFont;

	public Vector3 touch;
	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	public GameScreen gameScreen;
	float accumulator = 0;
	public MenuScreen menuScreen;
	public SettingsScreen settingsScreen;
	public DialogScreen dialogScreen;
	public FinalScreen finalScreen;

	@Override
	public void create() {
		Box2D.init();
		world = new World(new Vector2(0, 0), false);

		//Шрифты
		commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
		commonRedFont = FontBuilder.generate(24, Color.RED, GameResources.FONT_PATH);
		largeWhiteFont = FontBuilder.generate(52, Color.WHITE, GameResources.FONT_PATH);
		largeRedFont = FontBuilder.generate(48, Color.RED, GameResources.FONT_PATH);
		commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);
		smallRedFont = FontBuilder.generate(20, Color.RED, GameResources.FONT_PATH);
		commonRedFont.getData().setScale(1f, -1f);
		commonWhiteFont.getData().setScale(1f, -1f);
		largeWhiteFont.getData().setScale(1f, -1f);
		largeRedFont.getData().setScale(1f, -1f);
		commonBlackFont.getData().setScale(1f, -1f);
		smallRedFont.getData().setScale(1f, -1f);
		camera = new OrthographicCamera();
		camera.setToOrtho(true, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		dialogScreen = new DialogScreen(this);
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		finalScreen = new FinalScreen(this);
		/*if (MemoryManager.loadDialogue()){
			setScreen(dialogScreen);
		}
		else {
			setScreen(menuScreen);
		}*/

		setScreen(menuScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= GameSettings.STEP_TIME) {
			accumulator -= GameSettings.STEP_TIME;
			world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
		}
	}
}