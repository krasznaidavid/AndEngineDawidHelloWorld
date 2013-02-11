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
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.game.SceneManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class GameScene extends BaseScene implements IOnSceneTouchListener, IOnAreaTouchListener
{	
	private HUD gameHUD;
	private Text scoreText;
	private Text timeText;
	private int score = 0;
	private ArrayList<Integer> ballNumbers;
	private PhysicsWorld physicsWorld;

	
	private RepeatingSpriteBackground background;
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
		final boolean isFixedRotation;
		final float scale;
		switch (levelNumber)
		{
			case 1: 
				ballNumbers = getRandomNumbers(3, 10, false); 
				isFixedRotation = true;
				scale = 2f;
				break;
			case 2: 
				ballNumbers = getRandomNumbers(6, 12, false); 
				isFixedRotation = true;
				scale = 2f;
				break;
			case 3: 
				ballNumbers = getRandomNumbers(7, 15, false); 
				isFixedRotation = true;
				scale = 1.8f;
				break;
			case 4: 
				ballNumbers = getRandomNumbers(8, 20, false); 
				isFixedRotation = true;
				scale = 1.8f;
				break;
			case 5: 
				ballNumbers = getRandomNumbers(10, 20, false); 
				isFixedRotation = false;
				scale = 1.5f;
				break;
			case 6: 
				ballNumbers = getRandomNumbers(11, 20, false); 
				isFixedRotation = false;
				scale = 1.5f;
				break;
			case 7: 
				ballNumbers = getRandomNumbers(12, 30, false); 
				isFixedRotation = false;
				scale = 1.2f;
				break;
			case 8: 
				ballNumbers = getRandomNumbers(13, 30, true); 
				isFixedRotation = false;
				scale = 1.2f;
				break;
			case 9: 
				ballNumbers = getRandomNumbers(14, 40, true); 
				isFixedRotation = false;
				scale = 1.2f;
				break;
			case 10: 
				ballNumbers = getRandomNumbers(15, 40, true); 
				isFixedRotation = false;
				scale = 1f;
				break;
			case 11: 
				ballNumbers = getRandomNumbers(16, 50, true); 
				isFixedRotation = false;
				scale = 1f;
				break;
			case 12: 
				ballNumbers = getRandomNumbers(17, 50, true); 
				isFixedRotation = false;
				scale = .8f;
				break;
			case 13: 
				ballNumbers = getRandomNumbers(18, 70, true); 
				isFixedRotation = false;
				scale = .8f;
				break;
			case 14: 
				ballNumbers = getRandomNumbers(19, 70, true); 
				isFixedRotation = false;
				scale = .7f;
				break;
			case 15: 
				ballNumbers = getRandomNumbers(20, 99, true); 
				isFixedRotation = false;
				scale = .7f;
				break;
			default : 
				ballNumbers = getRandomNumbers(20, 99, true); 
				isFixedRotation = true;
				scale = 1f;
				break;
		}
		for (int i = 0; i < ballNumbers.size(); i++)
		{
			addBall(ballNumbers.get(i), isFixedRotation, scale);
		}
		addLevelSprite(levelNumber);
	}
	
	private void addLevelSprite(final int levelNumber)
	{
		final Text levelText = new Text(0, 0, ResourceManager.getInstance().getMainFont(), "Level " + String.valueOf(levelNumber), new TextOptions(HorizontalAlign.CENTER), vbom);
		final float textX = (GameActivity.CAMERA_WIDTH - levelText.getWidth()) / 2;
		final float textY = (GameActivity.CAMERA_HEIGHT - levelText.getHeight()) / 2;
		levelText.setPosition(textX, textY);
		levelText.setScale(2f);
		attachChild(levelText);
		levelText.registerEntityModifier(new FadeOutModifier(2f));
	}

	private void addBall(final int ballNumber, final boolean isFixed, final float scale)
	{
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.99f, 0.1f);
		final BallSprite ballSprite = new BallSprite(0, 0, resourceManager.ball_region, vbom, ballNumber, scale);
		final float ballX = (float) ((GameActivity.CAMERA_WIDTH - ballSprite.getWidth()) * Math.random());
		final float ballY = (float) ((GameActivity.CAMERA_HEIGHT - ballSprite.getHeight()) * Math.random());
		ballSprite.setPosition(ballX, ballY);
		final Body body = PhysicsFactory.createCircleBody(physicsWorld, ballSprite, BodyType.DynamicBody, objectFixtureDef);
		body.setFixedRotation(isFixed);
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
		ground.setColor(Color.BLACK);
		roof.setColor(Color.BLACK);
		left.setColor(Color.BLACK);
		right.setColor(Color.BLACK);

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
		final float hudY = 5;
		gameHUD = new HUD();
		scoreText = new Text(20, hudY, ResourceManager.getInstance().getMainFont(), "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setText("Score: 0");
		timeText = new Text(0, 0, ResourceManager.getInstance().getMainFont(), "Time: 0123456789", new TextOptions(HorizontalAlign.RIGHT), vbom);
		timeText.setText("Time: 0:00");
		final float textX = GameActivity.CAMERA_WIDTH - 10 - timeText.getWidth();
		timeText.setPosition(textX, hudY);
		gameHUD.attachChild(scoreText);
		gameHUD.attachChild(timeText);
		camera.setHUD(gameHUD);
	}
	
	private void createAndStartStopwatch()
	{
		registerUpdateHandler(new TimerHandler(1 / 10f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                    timeText.setText("Time: " + Math.round(engine.getSecondsElapsedTotal()));
            }
    }));
	}
	
	@Override
	public void createScene()
	{
		background = new RepeatingSpriteBackground(GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT, activity.getTextureManager(),
				AssetBitmapTextureAtlasSource.create(activity.getAssets(), "gfx/game/background.png"), vbom);
		setBackground(background);
		setOnSceneTouchListener(this);
		setOnAreaTouchListener(this);

		createHUD();
		createPhysics();
		createBounds();	
		levelNumber = 1;
		createLevel(levelNumber);
		createAndStartStopwatch();
		
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
