package com.dawid.andengine.helloworld.game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

public class ResourceManager
{
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public GameActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	public Font font;
	
	//Splash scene
	public ITextureRegion splashRegion;
	private BitmapTextureAtlas spalshTextureAtlas;
	
	//Menu scene
	public ITextureRegion menuBackgroundRegion;
	public ITextureRegion playRegion;
	public ITextureRegion optionsRegion;
	private BuildableBitmapTextureAtlas menuTextureAtlas;

	
	// Game Texture
	public BuildableBitmapTextureAtlas gameTextureAtlas;
	    
	// Game Texture Regions
	public ITextureRegion platform1_region;
	public ITextureRegion platform2_region;
	public ITextureRegion platform3_region;
	public ITextureRegion coin_region;
	public ITextureRegion ball_region;
	
	public static void prepareResourceManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}
	
	public void loadMenuResources()
	{
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}

	public void loadGameResources()
	{
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}
	
	public void loadSplashScreen()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		spalshTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(spalshTextureAtlas, activity, "splash.png", 0, 0);
		spalshTextureAtlas.load();
	}
	
	public void unloadSplashScreen()
	{
		spalshTextureAtlas.unload();
		splashRegion = null;
	}
	
	private void loadGameAudio()
	{

	}

	private void loadGameFonts()
	{

	}

	private void loadGameGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
	    gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
	    
//	    platform1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "platform1.png");
//	    platform2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "platform2.png");
//	    platform3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "platform3.png");
//	    coin_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "coin.png");
	    ball_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ball.png");
	   
	    try 
	    {
	        this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
	        this.gameTextureAtlas.load();
	    } 
	    catch (final TextureAtlasBuilderException e)
	    {
	        Debug.e(e);
	    }
	}

	private void loadMenuAudio()
	{

	}
	
	public void unloadMenuTextures()
	{
	    menuTextureAtlas.unload();
	}
	    
	public void loadMenuTextures()
	{
	    menuTextureAtlas.load();
	}
	
	public void unloadGameTextures()
	{
	    gameTextureAtlas.unload();
	}

	private void loadMenuGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
		playRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
		optionsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
		try 
		{
		    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
		    this.menuTextureAtlas.load();
		} 
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
	}

	private void loadMenuFonts()
	{
	    FontFactory.setAssetBasePath("font/");
	    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, 
	    		TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    font = FontFactory.create(activity.getFontManager(), mainFontTexture, 30, true, Color.BLACK);
	    font.load();
	}
	
	public static ResourceManager getInstance()
	{
		return INSTANCE;
	}
	
}
