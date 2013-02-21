package com.dawid.andengine.helloworld.game.components;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.scene.GameScene;
import com.dawid.andengine.helloworld.util.ColorHelper;

public class BallSprite extends Sprite
{
	private int mBallNumber1;
	private int mBallNumber2;
	private Color mBallColor;
	private final GameScene mGameScene;
	private final BallType mBallType;

	public enum BallType
	{
		Egesz,
		Tort
	}
	
	public BallSprite(float pX, float pY, ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, 
			final int pBallNumber1, final float pScale, final GameScene pGameScene)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		mBallNumber1 = pBallNumber1;
		mGameScene = pGameScene;
		mBallType = BallType.Egesz;
		mBallColor = ColorHelper.GetRandomColor();
		initBallEgesz(pVertexBufferObjectManager, pScale);
	}
	
	public BallSprite(float pX, float pY, ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, 
			final int nevezo, final int szamlalo, final float pScale, final GameScene pGameScene)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		mBallNumber1 = nevezo;
		mBallNumber2 = szamlalo;
		mGameScene = pGameScene;
		mBallType = BallType.Tort;
		mBallColor = ColorHelper.GetRandomColor();		
		initBallTort(pVertexBufferObjectManager, pScale);
	}
	
	private void initBallTort(final VertexBufferObjectManager pVertexBufferObjectManager, final float pScale)
	{
		final Text ballText1 = new Text(0, 0, ResourceManager.getInstance().getMainFont(), String.valueOf(this.mBallNumber1), 
				new TextOptions(HorizontalAlign.CENTER), pVertexBufferObjectManager);
		final Text ballText2 = new Text(0, 0, ResourceManager.getInstance().getMainFont(), String.valueOf(this.mBallNumber2), 
				new TextOptions(HorizontalAlign.CENTER), pVertexBufferObjectManager);
		final float lineWidth = Math.max(ballText1.getWidth(), ballText2.getWidth());
		final Rectangle textLine = new Rectangle(0, 0, lineWidth, 2, pVertexBufferObjectManager);
		
		final float textScale = 0.9f;
		ballText1.setScale(textScale);
		ballText2.setScale(textScale);
		textLine.setScale(textScale);
		final float text1X = (getWidth() - ballText1.getWidth()) / 2;
		final float text1Y = ((getHeight() - ballText1.getHeight()) / 2) - 21;
		final float text2X = (getWidth() - ballText2.getWidth()) / 2;
		final float text2Y = ((getHeight() - ballText2.getHeight()) / 2) + 21;
		final float lineX = (getWidth() - lineWidth) / 2;
		final float lineY = text1Y + ballText1.getHeight() - 8;
		textLine.setColor(Color.BLACK);
		textLine.setPosition(lineX, lineY);
		ballText1.setPosition(text1X, text1Y);
		ballText2.setPosition(text2X, text2Y);
		attachChild(ballText1);
		attachChild(textLine);
		attachChild(ballText2);
		setColor(mBallColor);
		setScale(pScale);
	}
	
	private void initBallEgesz(final VertexBufferObjectManager pVertexBufferObjectManager, final float pScale)
	{
		final Text ballText = new Text(0, 0, ResourceManager.getInstance().getMainFont(), String.valueOf(this.mBallNumber1), 
				new TextOptions(HorizontalAlign.CENTER), pVertexBufferObjectManager);
		final float textX = (getWidth() - ballText.getWidth()) / 2;
		final float textY = (getHeight() - ballText.getHeight()) / 2;
		final Rectangle textLine = new Rectangle(textX, textY + ballText.getHeight() - 5, ballText.getWidth(), 2, pVertexBufferObjectManager);
		textLine.setColor(Color.BLACK);
		ballText.setPosition(textX, textY);
		attachChild(ballText);
		attachChild(textLine);
		setColor(mBallColor);
		setScale(pScale);
	}

	public void kill()
	{
		final PhysicsConnector physicsConnector = mGameScene.getPhysicsWorld().getPhysicsConnectorManager().findPhysicsConnectorByShape(this);
		mGameScene.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
		mGameScene.getPhysicsWorld().destroyBody(physicsConnector.getBody());
		mGameScene.unregisterTouchArea(this);
		this.detachSelf();
		this.dispose();
		System.gc();
	}
	
	public float getBallNumber()
	{
		if (mBallType == BallType.Egesz)
			return mBallNumber1;
		return mBallNumber1 / mBallNumber2;
	}
	
	public Color getBallColor()
	{
		return mBallColor;
	}
}
