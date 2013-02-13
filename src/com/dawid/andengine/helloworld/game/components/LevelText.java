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

public class LevelText extends Text
{

	public LevelText(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions,
			VertexBufferObjectManager pVertexBufferObjectManager, final BaseGameActivity activity, IEntity parent)
	{
		super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager);
		final float textX = (GameActivity.CAMERA_WIDTH - getWidth()) / 2;
		final float textY = (GameActivity.CAMERA_HEIGHT - getHeight()) / 2;
		setPosition(textX, textY);
		setScale(2f);
		final FadeOutModifier fadeOutModifier = new FadeOutModifier(2f, new IEntityModifierListener()
		{
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem)
			{}
			
			@Override
			public void onModifierFinished(final IModifier<IEntity> pModifier, final IEntity pItem)
			{
				activity.runOnUpdateThread(new Runnable()
				{
					@Override
					public void run()
					{
						pItem.detachSelf();
						pItem.dispose();
					}
				});
			}
		});
		fadeOutModifier.setAutoUnregisterWhenFinished(true);
		registerEntityModifier(fadeOutModifier);
		parent.attachChild(this);
	}

}
