package com.dawid.andengine.helloworld.game;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.dawid.andengine.helloworld.scene.BaseScene;
import com.dawid.andengine.helloworld.scene.GameScene;
import com.dawid.andengine.helloworld.scene.LoadingScene;
import com.dawid.andengine.helloworld.scene.MainMenuScene;
import com.dawid.andengine.helloworld.scene.SplashScene;

public class SceneManager
{
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SPLASH;
	
	private BaseScene currentScene;
	
	private Engine engine = ResourceManager.getInstance().getEngine();
	
	public enum SceneType
	{
		SPLASH,
		MENU,
		GAME,
		LOADING
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
			case MENU:
				setScene(menuScene);
				break;
			case GAME:
				setScene(gameScene);
				break;
			case LOADING:
				setScene(loadingScene);
				break;
			case SPLASH:
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
	    ResourceManager.getInstance().loadGameResources();
        gameScene = new GameScene();
        setScene(gameScene);
	}
	
	public void loadMenuScene(final Engine mEngine)
	{
	    setScene(loadingScene);
	    gameScene.disposeScene();
	    ResourceManager.getInstance().unloadGameTextures();
	    ResourceManager.getInstance().loadMenuTextures();
        setScene(menuScene);
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
