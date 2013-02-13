package com.dawid.andengine.helloworld.game.components;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.IModifier;


public class ScoreText extends Text
{
	private float _duration = 1f;
	private float _distance = 50f;
	
	public ScoreText(float pX, float pY, IFont pFont, CharSequence pText,
			VertexBufferObjectManager pVertexBufferObjectManager, final BaseGameActivity activity, IEntity parent)
	{
		super(pX, pY, pFont, pText, pVertexBufferObjectManager);
		final ParallelEntityModifier parallelEntityModifier = new ParallelEntityModifier(new IEntityModifierListener()
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
		}, new FadeOutModifier(_duration), new MoveYModifier(_duration, pY, pY - _distance));
		parallelEntityModifier.setAutoUnregisterWhenFinished(true);
		registerEntityModifier(parallelEntityModifier);
		parent.attachChild(this);
	}
}
