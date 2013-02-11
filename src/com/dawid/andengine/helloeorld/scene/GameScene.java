package com.dawid.andengine.helloeorld.scene;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.SceneManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class GameScene extends BaseScene implements IOnSceneTouchListener, IOnAreaTouchListener
{	
	private HUD gameHUD;
	private Text scoreText;
	private int score = 0;
	private ArrayList<Integer> ballNumbers;
	private PhysicsWorld physicsWorld;

	private int levelNumber;
	
	private void createPhysics()
	{
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), true);
		registerUpdateHandler(physicsWorld);
	}

	private ArrayList<Integer> getRandomNumbers(final int count, final int bound, final boolean canBeNegative)
	{
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int number;
		while (numbers.size() < count)
		{
			if (canBeNegative)
			{
				number = (int) Math.round(Math.random() * bound);
				if (Math.random() < 0.5)
				{
					number = -number;
				}
			} else
			{
				number = (int) Math.round(Math.random() * bound);
			}
			if (!numbers.contains(number))
				numbers.add(number);
		}
		Collections.sort(numbers);
		return numbers;
	}

	private void createLevel(final int levelNumber)
	{
		addLevelSprite(levelNumber);
		switch (levelNumber)
		{
			case 1: ballNumbers = getRandomNumbers(5, 10, false); break;
			case 2: ballNumbers = getRandomNumbers(6, 12, false); break;
			case 3: ballNumbers = getRandomNumbers(7, 15, false); break;
			case 4: ballNumbers = getRandomNumbers(8, 20, false); break;
			case 5: ballNumbers = getRandomNumbers(10, 20, true); break;
			case 6: ballNumbers = getRandomNumbers(11, 20, true); break;
			case 7: ballNumbers = getRandomNumbers(12, 30, true); break;
			case 8: ballNumbers = getRandomNumbers(13, 30, true); break;
			case 9: ballNumbers = getRandomNumbers(14, 40, true); break;
			case 10: ballNumbers = getRandomNumbers(15, 40, true); break;
			case 11: ballNumbers = getRandomNumbers(16, 50, true); break;
			case 12: ballNumbers = getRandomNumbers(17, 50, true); break;
			case 13: ballNumbers = getRandomNumbers(18, 70, true); break;
			case 14: ballNumbers = getRandomNumbers(19, 70, true); break;
			case 15: ballNumbers = getRandomNumbers(20, 99, true); break;
			default : ballNumbers = getRandomNumbers(20, 99, true); break;
		}
		for (int i = 0; i < ballNumbers.size(); i++)
		{
			addBall(ballNumbers.get(i));
		}
	}
	
	private void addLevelSprite(final int levelNumber)
	{
		final Text levelText = new Text(0, 0, resourceManager.font, "Level " + String.valueOf(levelNumber), new TextOptions(HorizontalAlign.CENTER), vbom);
		final float textX = (GameActivity.CAMERA_WIDTH - levelText.getWidth()) / 2;
		final float textY = (GameActivity.CAMERA_HEIGHT - levelText.getHeight()) / 2;
		levelText.setPosition(textX, textY);
		attachChild(levelText);
		levelText.registerEntityModifier(new FadeOutModifier(2f));
	}

	private void addBall(final int ballNumber)
	{
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.99f, 0.1f);
		final BallSprite ballSprite = new BallSprite(0, 0, resourceManager.ball_region, vbom, ballNumber);
		final float ballX = (float) ((GameActivity.CAMERA_WIDTH - ballSprite.getWidth()) * Math.random());
		final float ballY = (float) ((GameActivity.CAMERA_HEIGHT - ballSprite.getHeight()) * Math.random());
		ballSprite.setPosition(ballX, ballY);
		final Body body = PhysicsFactory.createCircleBody(physicsWorld, ballSprite, BodyType.DynamicBody, objectFixtureDef);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(ballSprite, body, true, true));
		ballSprite.setUserData(body);
		registerTouchArea(ballSprite);
		attachChild(ballSprite);
		body.setLinearVelocity((float) Math.random() * 10, (float) Math.random() * 10);
	}

	private void createBounds()
	{
		final Rectangle ground = new Rectangle(0, GameActivity.CAMERA_HEIGHT - 2, GameActivity.CAMERA_WIDTH, 2, vbom);
		final Rectangle roof = new Rectangle(0, 0, GameActivity.CAMERA_WIDTH, 2, vbom);
		final Rectangle left = new Rectangle(0, 0, 2, GameActivity.CAMERA_HEIGHT, vbom);
		final Rectangle right = new Rectangle(GameActivity.CAMERA_WIDTH - 2, 0, 2, GameActivity.CAMERA_HEIGHT, vbom);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.99f, 0.1f);
		PhysicsFactory.createBoxBody(this.physicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.physicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.physicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.physicsWorld, right, BodyType.StaticBody, wallFixtureDef);

		attachChild(ground);
		attachChild(roof);
		attachChild(right);
		attachChild(left);
	}

	private void addToScore(int i)
	{
		score += i;
		if (score < 0)
			score = 0;
		scoreText.setText("Score: " + score);
	}

	private void createHUD()
	{
		gameHUD = new HUD();
		scoreText = new Text(20, 5, resourceManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);
		camera.setHUD(gameHUD);
	}

	@Override
	public void createScene()
	{
		setBackground(new Background(0.86f, 0.86f, 0.86f));
		setOnSceneTouchListener(this);
		setOnAreaTouchListener(this);

		createHUD();
		createPhysics();
		createBounds();	
		levelNumber = 1;
		createLevel(levelNumber);
	}

	@Override
	public void onBackKeyPressed()
	{
		SceneManager.getInstance().loadMenuScene(engine);
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
		camera.setHUD(null);
		camera.setCenter(400, 240);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		if (pSceneTouchEvent.isActionDown())
		{
			final BallSprite sprite = (BallSprite) pTouchArea;
			if (sprite.getBallNumber() == getMin())
			{
				ballNumbers.remove(0);
				removeSprite(sprite);
				addToScore(10);
				checkWin();
				return true;
			} 
			else
			{
				sprite.setColor(Color.RED);
				sprite.registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback()
				{
					@Override
					public void onTimePassed(TimerHandler pTimerHandler)
					{
						sprite.unregisterUpdateHandler(pTimerHandler);
						sprite.setColor(sprite.getBallColor());
					}
				}));
				addToScore(-10);
			}
		}
		return false;
	}
	
	private void checkWin()
	{
		if (ballNumbers.size() == 0)
		{
			levelNumber++;
			createLevel(levelNumber);
		}
	}

	private int getMin()
	{
		int min = ballNumbers.get(0);
		for (int i : ballNumbers)
		{
			if (i < min)
			{
				min = i;
			}
		}
		return min;
	}

	private void removeSprite(final Sprite sprite)
	{
		final PhysicsConnector physicsConnector = physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(sprite);
		physicsWorld.unregisterPhysicsConnector(physicsConnector);
		physicsWorld.destroyBody(physicsConnector.getBody());
		unregisterTouchArea(sprite);
		detachChild(sprite);
		System.gc();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		/*
		 * if(physicsWorld != null) { if(pSceneTouchEvent.isActionDown()) {
		 * this.addBall(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
		 * return true; } }
		 */
		return false;
	}

}
