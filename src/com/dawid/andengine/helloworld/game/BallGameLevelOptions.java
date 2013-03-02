package com.dawid.andengine.helloworld.game;

import java.util.ArrayList;
import java.util.Random;

import com.dawid.andengine.helloworld.game.components.TortSzam;
import com.dawid.andengine.helloworld.util.NumberHelper;

public class BallGameLevelOptions
{
	private float maxScale = 2.5f;
	private float minScale = 1f;
	private BallGameType ballGameType;
	private int ballCount;
	private int bound;
	private ArrayList<Integer> numbers;
	private ArrayList<TortSzam> numbersTort;
	private float scaleX;
	private boolean fixedRotation;
	private boolean isOrdered;
	private boolean canBeNegative;
	private int gameSeconds;
	private float scaleUnit;
	private Direction direction;
	

	public BallGameLevelOptions(final BallGameType pBallGameType, final Direction pDirection, final int pBallCount,
								final int pBound, final float pScaleX, final boolean pIsFixedRotation, 
								final boolean pCanBeNegative, final boolean pIsOrdered, final int pGameSeconds)
	{
		ballGameType = pBallGameType;
		direction = pDirection;
		ballCount = pBallCount;
		bound = pBound;
		scaleX = pScaleX;
		fixedRotation = pIsFixedRotation;
		canBeNegative = pCanBeNegative;
		isOrdered = pIsOrdered;
		gameSeconds = pGameSeconds;
		init();
	}
	
	private void init()
	{
		numbers = new ArrayList<Integer>();
		numbersTort = new ArrayList<TortSzam>();
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
		setScaleUnit(((maxScale - minScale) / (numbers.size() + numbersTort.size())) * scaleX);
	}
	
	public Object getNext()
	{
		if (isOrdered)
			return getNextOrdered();
		return getNextRandom();
	}
	
	public Object getNextOrdered()
	{
		if (numbers.size() == 0 && numbersTort.size() == 0) return null;
		Integer minEgesz = getMin(numbers);
		TortSzam minTort = getMinTort(numbersTort);
		if (minTort == null)
		{
			int index = numbers.indexOf(minEgesz);
			numbers.remove(index);
			return minEgesz;
		}
		if (minEgesz == null)
		{
			int index = numbersTort.indexOf(minTort);
			numbersTort.remove(index);
			return minTort;
		}
		if (minEgesz >= minTort.getSzam())
		{
			int index = numbersTort.indexOf(minTort);
			numbersTort.remove(index);
			return minTort;
		}
		int index = numbers.indexOf(minEgesz);
		numbers.remove(index);
		return minEgesz;
	}
	
	public Object getNextRandom()
	{
		if (numbers.size() == 0 && numbersTort.size() == 0) return null;
		Integer randomEgesz = getRandomMin(numbers);
		TortSzam randomTort = getRandomTort(numbersTort);
		if (randomTort == null)
		{
			int index = numbers.indexOf(randomEgesz);
			numbers.remove(index);
			return randomEgesz;
		}
		if (randomEgesz == null)
		{
			int index = numbersTort.indexOf(randomTort);
			numbersTort.remove(index);
			return randomTort;
		}
		if (Math.random() > 0.5)
		{
			int index = numbersTort.indexOf(randomTort);
			numbersTort.remove(index);
			return randomTort;
		}
		int index = numbers.indexOf(randomEgesz);
		numbers.remove(index);
		return randomEgesz;
	}
	
	private TortSzam getRandomTort(ArrayList<TortSzam> numbers)
	{
		if (numbersTort.size() == 0) return null;
		Random r = new Random();
		int index = r.nextInt(numbersTort.size());
		return numbersTort.get(index);

	}
	
	private Integer getRandomMin(ArrayList<Integer> numbers)
	{
		if (numbers.size() == 0) return null;
		Random r = new Random();
		int index = r.nextInt(numbers.size());
		return numbers.get(index);
	}
	
	private TortSzam getMinTort(ArrayList<TortSzam> numbers)
	{
		if (numbersTort.size() == 0) return null;
		TortSzam min = numbersTort.get(0);
		for (TortSzam number : numbersTort)
		{
			if (number.getSzam() < min.getSzam())
				min = number;
		}
		return min;
	}
	
	private Integer getMin(ArrayList<Integer> numbers)
	{
		if (numbers.size() == 0) return null;
		int min = numbers.get(0);
		for (int number : numbers)
		{
			if (number < min)
				min = number;
		}
		return min;
	}
	
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

	public ArrayList<TortSzam> getNumbersTort()
	{
		return numbersTort;
	}

	public float getScaleX()
	{
		return scaleX;
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
	
	public float getMaxScale()
	{
		return maxScale;
	}

	public void setMaxScale(float maxScale)
	{
		this.maxScale = maxScale;
	}

	public float getMinScale()
	{
		return minScale;
	}

	public void setMinScale(float minScale)
	{
		this.minScale = minScale;
	}
	
	public ArrayList<Integer> getmNumbers()
	{
		return numbers;
	}

	public ArrayList<TortSzam> getmNumbersTort()
	{
		return numbersTort;
	}

	public float getScaleUnit()
	{
		return scaleUnit;
	}

	public void setScaleUnit(float scaleUnit)
	{
		this.scaleUnit = scaleUnit;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
	
	public enum Direction
	{
		LowestToHighest,
		HighestToLowest
	}

	public enum BallGameType
	{
		Egesz, 
		Tort,
		Vegyes
	}
}
