package com.dawid.andengine.helloeorld.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.dawid.andengine.helloworld.game.GameActivity;
import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.game.SceneManager.SceneType;

public abstract class BaseScene extends Scene
{
	protected Engine engine;
	protected GameActivity activity;
	protected ResourceManager resourceManager;
	protected VertexBufferObjectManager vbom;
	protected Camera camera;
	
	public BaseScene()
	{
		this.resourceManager = ResourceManager.getInstance();
		this.engine = resourceManager.engine;
		this.activity = resourceManager.activity;
		this.vbom = resourceManager.vbom;
		this.camera = resourceManager.camera;
		createScene();
	}

	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();	
}
