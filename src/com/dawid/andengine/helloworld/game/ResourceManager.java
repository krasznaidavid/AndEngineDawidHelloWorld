package com.dawid.andengine.helloworld.game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
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
	
	private Engine engine;
	private GameActivity activity;
	private Camera camera;
	public void setEngine(Engine engine)
	{
		this.engine = engine;
	}

	public void setActivity(GameActivity activity)
	{
		this.activity = activity;
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

	public void setVbom(VertexBufferObjectManager vbom)
	{
		this.vbom = vbom;
	}

	private VertexBufferObjectManager vbom;
	
	public Engine getEngine()
	{
		return engine;
	}

	public GameActivity getActivity()
	{
		return activity;
	}

	public Camera getCamera()
	{
		return camera;
	}

	public VertexBufferObjectManager getVbom()
	{
		return vbom;
	}

	public void setMainFont(StrokeFont mainFont)
	{
		this.mainFont = mainFont;
	}

	private Font plainFont;
	private StrokeFont mainFont;
	
	//Splash scene
	public ITextureRegion splashRegion;
	private BitmapTextureAtlas spalshTextureAtlas;
	
	//Menu scene
	public ITextureRegion menuBackgroundRegion;
	public ITextureRegion playRegion;
	public ITextureRegion optionsRegion;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	
	//Game select scene
	

	
	// Game Texture
	public BuildableBitmapTextureAtlas gameTextureAtlas;
	    
	// Game Texture Regions
	private ITextureRegion ballRegion;
	private ITextureRegion directionArrowRegion;
	private ITextureRegion directionArrowMinusRegion;
	
	public static void prepareResourceManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
	{
		getInstance().setEngine(engine);
		getInstance().setActivity(activity);
		getInstance().setCamera(camera);
		getInstance().setVbom(vbom);
		getInstance().loadMainFont();
	}
	
	public void loadMenuResources()
	{
		loadMenuGraphics();
		loadMenuAudio();
		loadMainFont();
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
		spalshTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
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

	private void loadMainFont()
	{
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		mainFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), 
				"DroidSans-Bold.ttf", 48, true, Color.WHITE, 2, Color.BLACK);
		mainFont.load();
	}
	
	private void loadGameFonts()
	{
		
	}

	private void loadGameGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
	    gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
	    ballRegion = 
	    		BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ball.png");   
	    directionArrowRegion = 
	    		BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "direction_arrow.png");   
	    directionArrowMinusRegion = 
	    		BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "direction_arrow_minus.png");   
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
		playRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
//		optionsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
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
	    plainFont = FontFactory.create(activity.getFontManager(), mainFontTexture, 30, true, Color.BLACK);
	    plainFont.load();
	}
	
	public static ResourceManager getInstance()
	{
		return INSTANCE;
	}
	
	public StrokeFont getMainFont()
	{
		return mainFont;
	}

	public ITextureRegion getBallRegion()
	{
		return ballRegion;
	}
	
	public ITextureRegion getDirectionArrowRegion()
	{
		return directionArrowRegion;
	}
	
	public ITextureRegion getDirectionArrowMinusRegion()
	{
		return directionArrowMinusRegion;
	}
}
