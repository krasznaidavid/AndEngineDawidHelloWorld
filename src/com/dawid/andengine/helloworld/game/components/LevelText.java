package com.dawid.andengine.helloworld.game.components;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.IModifier;

import com.dawid.andengine.helloworld.game.GameActivity;

public class LevelText extends Text implements IEntityModifierListener
{
	private final float mDuration = 2f;
	private final BaseGameActivity mActivity;
	
	public LevelText(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions,
			VertexBufferObjectManager pVertexBufferObjectManager, BaseGameActivity pActivity, IEntity pParent)
	{
		super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager);
		mActivity = pActivity;
		final float textX = (GameActivity.CAMERA_WIDTH - getWidth()) / 2;
		final float textY = (GameActivity.CAMERA_HEIGHT - getHeight()) / 2;
		setPosition(textX, textY);
		setScale(mDuration);
		setupModifiers();
		pParent.attachChild(this);
	}
	
	private void setupModifiers() 
	{
		final FadeOutModifier fadeOutModifier = new FadeOutModifier(2f, this);
		fadeOutModifier.setAutoUnregisterWhenFinished(true);
		registerEntityModifier(fadeOutModifier);
	}
	
	@Override
	public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {}
	
	@Override
	public void onModifierFinished(final IModifier<IEntity> pModifier, final IEntity pItem)
	{
		mActivity.runOnUpdateThread(new Runnable()
		{
			@Override
			public void run()
			{
				pItem.detachSelf();
				pItem.dispose();
			}
		});
	}

}
