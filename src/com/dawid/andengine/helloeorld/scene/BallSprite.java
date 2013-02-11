package com.dawid.andengine.helloeorld.scene;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.dawid.andengine.helloworld.game.ResourceManager;
import com.dawid.andengine.helloworld.util.ColorHelper;

public class BallSprite extends Sprite
{
	private int ballNumber;
	private Color ballColor;

	public BallSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int pBallNumber)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.ballNumber = pBallNumber;
		final Text ballText = new Text(0, 0, ResourceManager.getInstance().font, String.valueOf(this.ballNumber), new TextOptions(HorizontalAlign.CENTER), pVertexBufferObjectManager);
		final float textX = (getWidth() - ballText.getWidth()) / 2;
		final float textY = (getHeight() - ballText.getHeight()) / 2;
		final Rectangle textLine = new Rectangle(textX, textY + ballText.getHeight(), ballText.getWidth(), 2, pVertexBufferObjectManager);
		textLine.setColor(Color.BLACK);
		ballText.setPosition(textX, textY);
		attachChild(ballText);
		attachChild(textLine);
		ballColor = ColorHelper.GetRandomColor();
		setColor(ballColor);
	}

	public int getBallNumber()
	{
		return ballNumber;
	}
	
	public Color getBallColor()
	{
		return ballColor;
	}
}
