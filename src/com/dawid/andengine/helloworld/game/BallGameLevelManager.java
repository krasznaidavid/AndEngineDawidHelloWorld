package com.dawid.andengine.helloworld.game;

import com.dawid.andengine.helloworld.game.BallGameLevelOptions.BallGameType;

public class BallGameLevelManager
{
	public static BallGameLevelOptions GetLevel(final int pLevelNumber)
	{
		int ballCount = 3;
		int bound = 10;
		float scale = 1f;
		boolean isFixedRotation = true;
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
			maxSecodns = 30;
			ballGameType = BallGameType.Tort;
		} 
		return new BallGameLevelOptions(ballGameType, ballCount, bound, scale, isFixedRotation, canBeNegative, true, maxSecodns);
	}
	
	/*public static BallGameLevelOptions _GetLevel(final int pLevelNumber)
	{
		BallGameLevelOptions ballGameLevelOptions;
		switch (pLevelNumber)
		{
			case 1: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 30); break;
			case 2: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Tort, 4, 12, 1.9f, true, false, 30); break;
			case 3: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Vegyes, 4, 14, 1.9f, true, false, 30); break;
			case 4: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 5, 16, 1.9f, true, false, 30); break;
			case 5: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Tort, 5, 18, 1.9f, true, false, 30); break;
			case 6: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Vegyes, 6, 20, 1.8f, true, false, 30); break;
			case 7: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 6, 22, 1.8f, true, false, 30); break;
			case 8: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Tort, 7, 24, 1.8f, true, false, 30); break;
			case 9: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Vegyes, 7, 26, 1.8f, true, false, 30); break;
			case 10: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 8, 28, 1.7f, true, false, 30); break;
			case 11: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Tort, 8, 30, 1.7f, true, false, 30); break;
			case 12: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Vegyes, 9, 32, 2f, true, false, 30); break;
			case 13: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 9, 34, 2f, true, false, 20); break;
			case 14: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 36, 2f, true, false, 20); break;
			case 15: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 38, 2f, true, false, 20); break;
			case 16: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 40, 2f, true, false, 20); break;
			case 17: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 42, 2f, true, false, 20); break;
			case 18: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 44, 2f, true, false, 20); break;
			case 19: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 46, 2f, true, false, 20); break;
			case 20: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 21: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 22: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 23: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 24: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 25: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 26: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 27: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 28: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 29: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 30: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 31: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 32: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 33: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 34: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 35: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 36: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 37: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 38: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 39: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			case 40: ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
			default : ballGameLevelOptions = new BallGameLevelOptions(BallGameType.Egesz, 3, 10, 2f, true, false, 20); break;
		}
		return ballGameLevelOptions;
	}*/
}
