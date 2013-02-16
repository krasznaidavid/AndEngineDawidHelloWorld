package com.dawid.andengine.helloworld.game;

import java.util.ArrayList;

import com.dawid.andengine.helloworld.util.NumberHelper;

import android.graphics.Point;

public class BallGameLevelOptions
{
	private BallGameType ballGameType;
	private int ballCount;
	private int bound;
	private ArrayList<Integer> numbers;
	private ArrayList<Point> numbersTort;
	private float scale;
	private boolean fixedRotation;
	private boolean canBeNegative;
	private int gameSeconds;
	
	public BallGameType getBallGameType()
	{
		return ballGameType;
	}

	public int getBallCount()
	{
		return ballCount;
	}

	public int getBound()
	{
		return bound;
	}

	public ArrayList<Integer> getNumbers()
	{
		return numbers;
	}

	public ArrayList<Point> getNumbersTort()
	{
		return numbersTort;
	}

	public float getScale()
	{
		return scale;
	}

	public boolean isFixedRotation()
	{
		return fixedRotation;
	}

	public boolean canBeNegative()
	{
		return canBeNegative;
	}

	public int getGameSeconds()
	{
		return gameSeconds;
	}

	public BallGameLevelOptions(final BallGameType pBallGameType, final int pBallCount, final int pBound,
			final float pScale, final boolean pIsFixedRotation, final boolean pCanBeNegative, final int pGameSeconds)
	{
		ballGameType = pBallGameType;
		ballCount = pBallCount;
		bound = pBound;
		scale = pScale;
		fixedRotation = pIsFixedRotation;
		canBeNegative = pCanBeNegative;
		gameSeconds = pGameSeconds;
		init();
	}
	
	private void init()
	{
		switch (ballGameType)
		{
			case Egesz:
				numbers = NumberHelper.getRandomNumbers(ballCount, bound, canBeNegative);
				break;
			case Tort:
				numbersTort = NumberHelper.getTortSzamok(ballCount, bound, canBeNegative);
				break;
			case Vegyes:
				int egeszCount = Math.round(ballCount / 2);
				int tortCount = ballCount - egeszCount;
				numbers = NumberHelper.getRandomNumbers(egeszCount, bound, canBeNegative);
				numbersTort = NumberHelper.getTortSzamok(tortCount, bound, canBeNegative);
				break;
		}
	}


	public ArrayList<Integer> getmNumbers()
	{
		return numbers;
	}

	public ArrayList<Point> getmNumbersTort()
	{
		return numbersTort;
	}

	public enum BallGameType
	{
		Egesz, 
		Tort,
		Vegyes
	}
	
}
