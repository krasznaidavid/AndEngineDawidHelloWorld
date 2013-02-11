package com.dawid.andengine.helloeorld.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class SplashScene extends BaseScene
{
	private Sprite splash;
	
	@Override
	public void createScene()
	{
		splash = new Sprite(0, 0, resourceManager.splashRegion, vbom)
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera)
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		splash.setScale(1.5f);
		splash.setPosition(400 - (splash.getWidth() / 2), 240 - (splash.getHeight() / 2));
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed()
	{

	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene()
	{
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

}
