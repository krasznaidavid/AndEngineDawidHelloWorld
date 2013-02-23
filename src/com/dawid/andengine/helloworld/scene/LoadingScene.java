package com.dawid.andengine.helloworld.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class LoadingScene extends BaseScene
{
	private Text loadingText;
	@Override
	public void createScene()
	{
		setBackground(new Background(Color.BLACK));
		loadingText = new Text(0, 0, ResourceManager.getInstance().getMainFont(), "Loading...", vbom);
		loadingText.setPosition((GameActivity.CAMERA_WIDTH - loadingText.getWidth()) / 2,
								(GameActivity.CAMERA_HEIGHT - loadingText.getHeight()) / 2);
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
		return SceneType.LOADING;
	}

	@Override
	public void disposeScene()
	{
		loadingText.detachSelf();
		loadingText.dispose();
	}

}
