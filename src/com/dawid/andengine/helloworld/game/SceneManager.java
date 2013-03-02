package com.dawid.andengine.helloworld.game;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.dawid.andengine.helloworld.scene.BaseScene;
import com.dawid.andengine.helloworld.scene.BallGameScene;
import com.dawid.andengine.helloworld.scene.GameSelectScene;
import com.dawid.andengine.helloworld.scene.LoadingScene;
import com.dawid.andengine.helloworld.scene.TitleScene;
import com.dawid.andengine.helloworld.scene.SplashScene;

public class SceneManager
{
	private BaseScene splashScene;
	private BaseScene titleScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	private BaseScene gameSelectScene;
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SPLASH;
	
	private BaseScene currentScene;
	
	private Engine engine = ResourceManager.getInstance().getEngine();
	
	public enum SceneType
	{
		SPLASH,
		TITLE,
		BALL_GAME,
		GAME_SELECT,
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
			case TITLE:
				setScene(titleScene);
				break;
			case GAME_SELECT:
				setScene(gameSelectScene);
				break;
			case BALL_GAME:
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
	
	public void createTitleScene()
	{
	    ResourceManager.getInstance().loadMenuResources();
	    titleScene = new TitleScene();
	    loadingScene = new LoadingScene();
	    setScene(titleScene);
	    disposeSplashScene();
	}
	
	public void loadGameSelectScene()
	{
		setScene(loadingScene);
	    //ResourceManager.getInstance().unloadMenuTextures();
		gameSelectScene = new GameSelectScene();
		setScene(gameSelectScene);
	}
	
	public void loadBallGameScene()
	{
	    setScene(loadingScene);
	    if (gameSelectScene != null)
	    	gameSelectScene.disposeScene();
	    ResourceManager.getInstance().loadGameResources();
        gameScene = new BallGameScene();
        setScene(gameScene);
	}
	
	public void loadTitleScene()
	{
	    setScene(loadingScene);
	    gameScene.disposeScene();
	    ResourceManager.getInstance().unloadGameTextures();
	    ResourceManager.getInstance().loadMenuTextures();
        setScene(titleScene);
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
