package com.dawid.andengine.helloworld.util;

import java.util.ArrayList;

import android.graphics.Point;

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
	
	public static ArrayList<Point> getTortSzamok(final int count, final int bound, 
			final boolean canBeNegative)
	{
		ArrayList<Point> numbers = new ArrayList<Point>();
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
			Point number = new Point(number1, number2);
			if (!numbers.contains(number) && !containsValue(numbers, number))
				numbers.add(number);
		}
		return numbers;
	}
	
	private static boolean containsValue(ArrayList<Point> pNumbers, Point pPoint)
	{
		for(Point p : pNumbers)
		{
			if (getValue(pPoint) == getValue(p))
				return true;
		}
		return false;
	}
	
	private static float getValue(Point pPoint)
	{
		return pPoint.x / pPoint.y;
	}
}
