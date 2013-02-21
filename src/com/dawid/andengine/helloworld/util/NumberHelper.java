package com.dawid.andengine.helloworld.util;

import java.util.ArrayList;

import com.dawid.andengine.helloworld.game.components.TortSzam;

public class NumberHelper
{
	public static ArrayList<Integer> getRandomNumbers(final int count,
			final int bound, final boolean canBeNegative)
	{
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int number;
		while (numbers.size() < count)
		{
			if (canBeNegative)
			{
				number = (int) Math.round(Math.random() * bound);
				if (Math.random() < 0.5)
				{
					number = -number;
				}
			} 
			else
			{
				number = (int) Math.round(Math.random() * bound);
			}
			if (!numbers.contains(number))
				numbers.add(number);
		}
		return numbers;
	}
	
	public static ArrayList<TortSzam> getTortSzamok(final int count, final int bound, 
			final boolean canBeNegative)
	{
		ArrayList<TortSzam> numbers = new ArrayList<TortSzam>();
		while (numbers.size() < count)
		{
			int number1;
			int number2;
			if (canBeNegative)
			{
				number1 = (int) Math.round(Math.random() * bound);
				number2 = (int) Math.round(Math.random() * bound);
				if (Math.random() < 0.5) number1 = -number1; 
				if (Math.random() < 0.5) number2 = -number2; 
			} 
			else
			{
				number1 = (int) Math.round(Math.random() * bound);
				number2 = (int) Math.round(Math.random() * bound);
			}
			if (number1 == 0) number1 = 1;
			if (number2 == 0) number2 = 1;
			TortSzam number = new TortSzam(number1, number2);
			if (!numbers.contains(number) && !containsValue(numbers, number.getSzam()))
				numbers.add(number);
		}
		return numbers;
	}
	
	private static boolean containsValue(ArrayList<TortSzam> pNumbers, float pSzam)
	{
		for(TortSzam szam : pNumbers)
		{
			if (szam.getSzam() == pSzam)
				return true;
		}
		return false;
	}

}
