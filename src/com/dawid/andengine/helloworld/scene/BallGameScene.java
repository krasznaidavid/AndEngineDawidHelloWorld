package com.dawid.andengine.helloworld.scene;

import java.util.ArrayList;

import org.andengine.entity.text.Text;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.dawid.andengine.helloworld.game.BallGameLevelManager;
import com.dawid.andengine.helloworld.game.BallGameLevelOptions;
import com.dawid.andengine.helloworld.game.BallGameLevelOptions.Direction;
import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.game.SceneManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;
import com.dawid.andengine.helloworld.game.components.BallSprite;
import com.dawid.andengine.helloworld.game.components.LevelText;
import com.dawid.andengine.helloworld.game.components.ScoreText;
import com.dawid.andengine.helloworld.game.components.TortSzam;

public class BallGameScene extends BaseScene implements IOnSceneTouchListener, IOnAreaTouchListener
{
	private HUD gameHUD;
	private Text scoreText;
	private Text timeText;
	private int score = 0;
	private int bonusMultiplyer = 1;
	private ArrayList<LevelText> levelTexts;
	private ArrayList<BallSprite> ballSprites;
	private PhysicsWorld physicsWorld;
	private BallGameLevelOptions levelOptions;
	private Sprite directionArrow;
	
	public PhysicsWorld getPhysicsWorld()
	{
		return physicsWorld;
	}

	private int maxSeconds;
	private RepeatingSpriteBackground background;
	private int levelNumber;

	private void createPhysics()
	{
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), true);
		registerUpdateHandler(physicsWorld);
	}

	private void createLevel(final int levelNumber)
	{
	    levelOptions = BallGameLevelManager.GetLevel(levelNumber);
		if (ballSprites.size() > 0)
		{
			clearBalls();
		}
		this.maxSeconds = levelOptions.getGameSeconds();
		timeText.setText(String.valueOf(maxSeconds));
		int x = 0;
		Object next;
		while((next = levelOptions.getNext()) != null)
		{
			float scale = levelOptions.getMinScale() + (levelOptions.getScaleUnit() * x);
			x++;
			if (next.getClass() == TortSzam.class)
			{
				TortSzam szam = (TortSzam) next;
				addBallTort(szam.getNevezo(), szam.getSzamlalo(), levelOptions.isFixedRotation(), scale);
			}
			else
			{
				int szam = (Integer) next;
				addBall(szam, levelOptions.isFixedRotation(), scale);
			}
		}
		addLevelSprite(levelNumber);
		setupDirectionArrow(levelOptions.getDirection());
	}
	
	private void setupDirectionArrow(Direction direction)
	{
		if (directionArrow != null)
		{
			directionArrow.detachSelf();
			directionArrow.dispose();
			System.gc();
		}
		ITextureRegion region = direction == Direction.LowestToHighest 
				? ResourceManager.getInstance().getDirectionArrowRegion()
			    : ResourceManager.getInstance().getDirectionArrowMinusRegion();
		directionArrow = new Sprite(0, 0, region, vbom);
		directionArrow.setScale(0.35f);
		final float arrowX = (GameActivity.CAMERA_WIDTH - directionArrow.getWidth()) / 2;
		directionArrow.setPosition(arrowX, -35);
		gameHUD.attachChild(directionArrow);
	}
	
	private void clearBalls()
	{
		for(int i = ballSprites.size() - 1; i >= 0 ; i--)
		{
			BallSprite ballSprite = ballSprites.get(i);
			ballSprite.kill();			
		}
		ballSprites.clear();
	}
	
	private void addLevelSprite(final int levelNumber)
	{
		final String text = "Level " + String.valueOf(levelNumber);
		final LevelText levelText = new LevelText(0, 0, ResourceManager.getInstance().getMainFont(),
				text, new TextOptions(HorizontalAlign.CENTER), vbom, activity, this);
		levelTexts.add(levelText);
	}

	private void addBall(final int ballNumber, final boolean isFixed, final float scale)
	{
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 1f, 0.1f);
		final BallSprite ballSprite = new BallSprite(0, 0, ResourceManager.getInstance().getBallRegion(), 
				vbom, ballNumber, scale, this);
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
		ballSprites.add(ballSprite);
	}
	
	private void addBallTort(final int nevezo, final int szamlalo, final boolean isFixed, final float scale)
	{
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 1f, 0.1f);
		final BallSprite ballSprite = new BallSprite(0, 0, ResourceManager.getInstance().getBallRegion(), 
				vbom, nevezo, szamlalo, scale, this);
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
		ballSprites.add(ballSprite);
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

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0f, 1f, 0.1f);
		PhysicsFactory.createBoxBody(this.physicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.physicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.physicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.physicsWorld, right, BodyType.StaticBody, wallFixtureDef);

		attachChild(ground);
		attachChild(roof);
		attachChild(right);
		attachChild(left);
	}

	private void addToScore(final int pScore)
	{
		score += pScore;
		if (score < 0)
			score = 0;
		scoreText.setText(String.valueOf(score));
	}

	private void createHUD()
	{
		final float hudY = 5;
		gameHUD = new HUD();
		scoreText = new Text(20, hudY, ResourceManager.getInstance().getMainFont(), "0123456789", 
				new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setText("0");
		timeText = new Text(0, 0, ResourceManager.getInstance().getMainFont(), "0123456789",
				new TextOptions(HorizontalAlign.RIGHT),vbom);
		timeText.setText("99");
		final float textX = GameActivity.CAMERA_WIDTH - 10 - timeText.getWidth();
		timeText.setPosition(textX, hudY);
		gameHUD.attachChild(scoreText);
		gameHUD.attachChild(timeText);
		camera.setHUD(gameHUD);
	}

	private void createAndStartStopwatch()
	{
		registerUpdateHandler(new TimerHandler(1f, true,
				new ITimerCallback()
				{
					@Override
					public void onTimePassed(final TimerHandler pTimerHandler)
					{
						if (maxSeconds >= 0)
							timeText.setText(String.valueOf(maxSeconds));
						maxSeconds--;
					}
				}));
	}

	@Override
	public void createScene()
	{
		levelTexts = new ArrayList<LevelText>();
		ballSprites = new ArrayList<BallSprite>();
		background = new RepeatingSpriteBackground(GameActivity.CAMERA_WIDTH,
				GameActivity.CAMERA_HEIGHT, activity.getTextureManager(),
				AssetBitmapTextureAtlasSource.create(activity.getAssets(),
						"gfx/game/background.png"), vbom);
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
		SceneManager.getInstance().loadTitleScene();
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.BALL_GAME;
	}

	@Override
	public void disposeScene()
	{
		camera.setHUD(null);
		camera.setCenter(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2);
	}

	private void addScoreAndText(final float x, final float y, final int score)
	{
		addToScore(score);
		String text = "";
		if (score > 0)
			text = "+";
		text += String.valueOf(score);
		new ScoreText(x, y, ResourceManager.getInstance().getMainFont(), text, vbom, activity, this);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY)
	{
		if (pSceneTouchEvent.isActionDown())
		{
			final float x = pSceneTouchEvent.getX();
			final float y = pSceneTouchEvent.getY();
			final BallSprite ballSprite = (BallSprite) pTouchArea;
			if (isRightBall(ballSprite.getBallNumber()))
			{
				ballSprite.kill();
				ballSprites.remove(ballSprite);
				addScoreAndText(x, y, 10 * bonusMultiplyer);
				bonusMultiplyer++;
				if (checkWin())
				{
					levelNumber++;
					createLevel(levelNumber);
				}
				return true;
			} 
			else
			{
				bonusMultiplyer = 1;
				ballSprite.setColor(Color.RED);
				ballSprite.registerUpdateHandler(new TimerHandler(0.3f,
						new ITimerCallback()
						{
							@Override
							public void onTimePassed(TimerHandler pTimerHandler)
							{
								ballSprite.unregisterUpdateHandler(pTimerHandler);
								ballSprite.setColor(ballSprite.getBallColor());
							}
						}));
				addScoreAndText(x, y, -10);
			}
		}
		return false;
	}

	private boolean checkWin()
	{
		return ballSprites.size() == 0;
	}

	private boolean isRightBall(final float number)
	{
		if (levelOptions.getDirection() == Direction.LowestToHighest)
		{
			if (number == getMin()) return true;
			return false;
		}
		else
		{
			if (number == getMax()) return true;
			return false;
		}
	}
	
	private float getMin()
	{
		float min = ballSprites.get(0).getBallNumber();
		for (BallSprite ballSprite : ballSprites)
		{
			if (ballSprite.getBallNumber() < min)
			{
				min = ballSprite.getBallNumber();
			}
		}
		return min;
	}

	private float getMax()
	{
		float max = ballSprites.get(0).getBallNumber();
		for (BallSprite ballSprite : ballSprites)
		{
			if (ballSprite.getBallNumber() > max)
			{
				max = ballSprite.getBallNumber();
			}
		}
		return max;
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
