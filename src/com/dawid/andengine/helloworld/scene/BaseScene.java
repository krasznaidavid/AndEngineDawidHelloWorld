package com.dawid.andengine.helloworld.scene;

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
	protected VertexBufferObjectManager vbom;
	protected Camera camera;
	
	public BaseScene()
	{
		this.engine = ResourceManager.getInstance().getEngine();
		this.activity = ResourceManager.getInstance().getActivity();
		this.vbom = ResourceManager.getInstance().getVbom();
		this.camera = ResourceManager.getInstance().getCamera();
		createScene();
	}

	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();	
}
