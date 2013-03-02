package com.dawid.andengine.helloworld.scene;

import java.util.ArrayList;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;

import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.game.SceneManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class GameSelectScene extends BaseScene implements IOnSceneTouchListener, IScrollDetectorListener, IClickDetectorListener
{
	private static final int LEVEL_PADDING = 100;
	private static final int LEVELS = 10;
	private boolean isScrolling = false;
	private ClickDetector clickDetector;
	private SurfaceScrollDetector surfaceScrollDetector;
	private ArrayList<Sprite> levelBoxes;

	@Override
	public void createScene()
	{
		this.surfaceScrollDetector = new SurfaceScrollDetector(this);
		this.surfaceScrollDetector.setEnabled(true);

		clickDetector = new ClickDetector(this);
		clickDetector.setEnabled(true);

		this.setOnSceneTouchListener(this);
		createBackground();
		createLevelBoxes();
		createMenu();
	}

	@Override
	public void onBackKeyPressed()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.GAME_SELECT;
	}

	@Override
	public void disposeScene()
	{
		camera.setCenter(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2);
	}

	private void createBackground()
	{
		final RepeatingSpriteBackground background = new RepeatingSpriteBackground(
				GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT,
				activity.getTextureManager(),
				AssetBitmapTextureAtlasSource.create(activity.getAssets(),
						"gfx/game/background.png"), vbom);
		setBackground(background);
	}

	private void createMenu()
	{

	}

	// ///////////////////////////////////////////////////////////////////////////
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		// TODO Auto-generated method stub
		clickDetector.onTouchEvent(pSceneTouchEvent);
		surfaceScrollDetector.onTouchEvent(pSceneTouchEvent);
		return true;
	}

	@Override
	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY)
	{
		isScrolling = true;

	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY)
	{
		camera.offsetCenter(-pDistanceX, 0);
	}

	@Override
	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY)
	{
		isScrolling = false;
	}

	@Override
	public void onClick(ClickDetector pClickDetector, int pPointerID,
			float pSceneX, float pSceneY)
	{
		if (!isScrolling)
			SceneManager.getInstance().loadBallGameScene();
	}

	private void createLevelBoxes()
	{
		levelBoxes = new ArrayList<Sprite>();
		int level = 0;
		final int boxWidth = 300;
		final int boxHeight = 300;
		int boxX = (GameActivity.CAMERA_WIDTH - boxWidth) / 2;
		int boxY = (GameActivity.CAMERA_HEIGHT - boxHeight) / 2;
		Sprite box;

		for (int i = 0; i < LEVELS; i++)
		{
			box = new Sprite(boxX, boxY, boxWidth, boxHeight,
					ResourceManager.getInstance().playRegion, vbom)
			{
				@Override
				public boolean onAreaTouched(
						TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY)
				{
					return false;
				}
			};
			levelBoxes.add(box);
			attachChild(box);
			registerTouchArea(box);
			Text text = new Text(0, 0, ResourceManager.getInstance().getMainFont(), String
					.valueOf(level + 1), vbom);
			float textX = (box.getWidth() - text.getWidth()) / 2;
			float textY = (box.getHeight() - text.getHeight()) / 2;
			text.setPosition(textX, textY);
			box.attachChild(text);
			level++;
			boxX += box.getWidth() + LEVEL_PADDING;
		}
	}
}
