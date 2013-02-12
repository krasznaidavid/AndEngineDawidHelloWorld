package com.dawid.andengine.helloeorld.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.util.GLState;

import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.SceneManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener
{
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	
	@Override
	public void createScene()
	{
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed()
	{
		System.exit(0);
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene()
	{
		// TODO Auto-generated method stub

	}

	private void createBackground()
	{
		final RepeatingSpriteBackground background = new RepeatingSpriteBackground(GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT, activity.getTextureManager(),
				AssetBitmapTextureAtlasSource.create(activity.getAssets(), "gfx/game/background.png"), vbom);
		setBackground(background);
	}
	
	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourceManager.playRegion, vbom), 1.2f, 1);
	    //final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourceManager.optionsRegion, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    //menuChildScene.addMenuItem(optionsMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY());
	    //optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY());
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY)
	{
		switch(pMenuItem.getID())
        {
        	case MENU_PLAY: 
        		SceneManager.getInstance().loadGameScene(engine);
        		return true;
        	case MENU_OPTIONS: 
        		return true;
        	default: return false;
        }
	}
	
}
