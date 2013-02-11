package com.dawid.andengine.helloeorld.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class LoadingScene extends BaseScene
{

	@Override
	public void createScene()
	{
		setBackground(new Background(Color.BLACK));
		Text loadingText = new Text(0, 0, resourceManager.font, "Loading", vbom);
		loadingText.setPosition(400 - (loadingText.getWidth()), 240 - (loadingText.getHeight()));
		attachChild(loadingText);
	}

	@Override
	public void onBackKeyPressed()
	{
		return;
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene()
	{
		
	}

}
