package com.dawid.andengine.helloworld.game;

import java.util.Random;

import com.dawid.andengine.helloworld.game.BallGameLevelOptions.BallGameType;
import com.dawid.andengine.helloworld.game.BallGameLevelOptions.Direction;

public class BallGameLevelManager
{
	public static BallGameLevelOptions GetLevel(final int pLevelNumber)
	{
		Random r = new Random();
		int ballCount = 5;
		int bound = r.nextInt(100);
		if (bound < 5)
			bound = 5;
		float scale = 1f;
		boolean isFixedRotation = r.nextFloat() >= 0.5 ? true : false;
		boolean isOrdered = r.nextFloat() >= 0.5 ? true : false;
		boolean canBeNegative = r.nextFloat() >= 0.5 ? true : false;
		int maxSecodns = 30;
		
		BallGameType ballGameType = r.nextFloat() >= 0.5 
				? BallGameType.Egesz 
				: r.nextFloat() >= 0.5 
				? BallGameType.Vegyes 
				: BallGameType.Tort;
				
		Direction direction = r.nextFloat() >= 0.5
				? Direction.LowestToHighest
				: Direction.HighestToLowest;

		return new BallGameLevelOptions(ballGameType, direction, ballCount, bound, scale, isFixedRotation,
				canBeNegative, isOrdered, maxSecodns);
	}
	
	public static BallGameLevelOptions GetLevel2(final int pLevelNumber)
	{
		int ballCount = 3;
		int bound = 10;
		float scale = 1f;
		boolean isFixedRotation = true;
		boolean isOrdered = false;
		boolean canBeNegative = false;
		int maxSecodns = 30;
		BallGameType ballGameType = BallGameType.Egesz;
		
		if (pLevelNumber <= 5)
		{
			ballCount = 3;
			bound = 10;
			scale = 1f;
			isFixedRotation = true;
			canBeNegative = false;
			isOrdered = false;
			maxSecodns = 30;
			ballGameType = BallGameType.Egesz;
		}
		else if (pLevelNumber > 5 && pLevelNumber <= 10)
		{
			ballCount = 5;
			bound = 30;
			scale = 0.9f;
			isFixedRotation = false;
			canBeNegative = false;
			isOrdered = false;
			maxSecodns = 30;
			ballGameType = BallGameType.Egesz;
		}
		else if (pLevelNumber > 10 && pLevelNumber <= 15)
		{
			ballCount = pLevelNumber - 5;
			bound = 50;
			scale = 0.85f;
			isFixedRotation = false;
			canBeNegative = true;
			isOrdered = false;
			maxSecodns = 30;
			ballGameType = BallGameType.Egesz;
		}
		else if (pLevelNumber > 15 && pLevelNumber <= 20)
		{
			ballCount = 12;
			bound = 50;
			scale = 0.7f;
			isFixedRotation = true;
			canBeNegative = false;
			isOrdered = true;
			maxSecodns = 30;
			ballGameType = BallGameType.Vegyes;
		}
		else if (pLevelNumber > 20 && pLevelNumber <= 25)
		{
			ballCount = 13;
			bound = 50;
			scale = 0.6f;
			isFixedRotation = true;
			canBeNegative = true;
			isOrdered = true;
			maxSecodns = 30;
			ballGameType = BallGameType.Vegyes;
		}
		else if (pLevelNumber > 25 && pLevelNumber <= 30)
		{
			ballCount = 15;
			bound = 80;
			scale = 0.6f;
			isFixedRotation = false;
			canBeNegative = true;
			isOrdered = true;
			maxSecodns = 30;
			ballGameType = BallGameType.Tort;
		}
		else if (pLevelNumber > 30)
		{
			ballCount = 20;
			bound = 99;
			scale = 0.5f;
			isFixedRotation = false;
			canBeNegative = true;
			isOrdered = true;
			maxSecodns = 30;
			ballGameType = BallGameType.Tort;
		} 
		return new BallGameLevelOptions(ballGameType, Direction.LowestToHighest, ballCount, bound, scale, 
				isFixedRotation, canBeNegative, isOrdered, maxSecodns);
	}
}
