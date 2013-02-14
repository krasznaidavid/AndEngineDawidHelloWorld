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


public class ScoreText extends Text implements IEntityModifierListener
{
	private final float mDuration = 1f;
	private final float mDistance = -50f;
	private final BaseGameActivity mActivity;
	
	public ScoreText(float pX, float pY, IFont pFont, CharSequence pText,
			VertexBufferObjectManager pVertexBufferObjectManager, BaseGameActivity pActivity, IEntity pParent)
	{
		super(pX, pY, pFont, pText, pVertexBufferObjectManager);
		this.mActivity = pActivity;
		initializeModifiers();
		pParent.attachChild(this);
	}

	private void initializeModifiers()
	{
		final ParallelEntityModifier parallelEntityModifier = new ParallelEntityModifier(this, 
				new FadeOutModifier(mDuration), new MoveYModifier(mDuration, getY(), getY() + mDistance));
		parallelEntityModifier.setAutoUnregisterWhenFinished(true);
		registerEntityModifier(parallelEntityModifier);
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
