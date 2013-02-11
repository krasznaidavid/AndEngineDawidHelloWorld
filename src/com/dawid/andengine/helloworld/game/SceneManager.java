package com.dawid.andengine.helloworld.game;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.dawid.andengine.helloeorld.scene.BaseScene;
import com.dawid.andengine.helloeorld.scene.GameScene;
import com.dawid.andengine.helloeorld.scene.LoadingScene;
import com.dawid.andengine.helloeorld.scene.MainMenuScene;
import com.dawid.andengine.helloeorld.scene.SplashScene;

public class SceneManager
{
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	
	private BaseScene currentScene;
	
	private Engine engine = ResourceManager.getInstance().engine;
	
	public enum SceneType
	{
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING
	}
	
	public void setScene(BaseScene scene)
	{
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType)
	{
		switch (sceneType)
		{
			case SCENE_MENU:
				setScene(menuScene);
				break;
			case SCENE_GAME:
				setScene(gameScene);
				break;
			case SCENE_LOADING:
				setScene(loadingScene);
				break;
			case SCENE_SPLASH:
				setScene(splashScene);
				break;
		}
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{
		ResourceManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	private void disposeSplashScene()
	{
		ResourceManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	public void createMenuScene()
	{
	    ResourceManager.getInstance().loadMenuResources();
	    menuScene = new MainMenuScene();
	    loadingScene = new LoadingScene();
	    setScene(menuScene);
	    disposeSplashScene();
	}
	
	public void loadGameScene(final Engine mEngine)
	{
	    setScene(loadingScene);
	    ResourceManager.getInstance().unloadMenuTextures();
	    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            ResourceManager.getInstance().loadGameResources();
	            gameScene = new GameScene();
	            setScene(gameScene);
	        }
	    }));
	}
	
	public void loadMenuScene(final Engine mEngine)
	{
	    setScene(loadingScene);
	    gameScene.disposeScene();
	    ResourceManager.getInstance().unloadGameTextures();
	    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            ResourceManager.getInstance().loadMenuTextures();
	            setScene(menuScene);
	        }
	    }));
	}

	
	public static SceneManager getInstance()
	{
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType()
	{
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene()
	{
		return currentScene;
	}
}
