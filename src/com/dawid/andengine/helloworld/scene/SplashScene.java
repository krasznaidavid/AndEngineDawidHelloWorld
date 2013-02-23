package com.dawid.andengine.helloworld.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class SplashScene extends BaseScene
{
	private Sprite splash;
	
	@Override
	public void createScene()
	{
		splash = new Sprite(0, 0, ResourceManager.getInstance().splashRegion, vbom)
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera)
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		splash.setPosition((GameActivity.CAMERA_WIDTH - splash.getWidth()) / 2, 
						   (GameActivity.CAMERA_HEIGHT - splash.getHeight()) / 2);
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed()
	{

	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SPLASH;
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
