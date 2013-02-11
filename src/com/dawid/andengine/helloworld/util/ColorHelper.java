package com.dawid.andengine.helloworld.util;

import java.util.Random;

import org.andengine.util.color.Color;

public class ColorHelper
{
	private static final Random RANDOM = new Random();
	
	private static final Color[] COLORS = new Color[] {
		Color.CYAN, Color.YELLOW, Color.PINK, Color.WHITE, Color.GREEN
	};
	
	public static Color GetRandomColor()
	{
		final int colorIndex = RANDOM.nextInt(COLORS.length - 1);
		final Color color = COLORS[colorIndex];
		return color;
	}
	
	public static Color GetRandomColorWithAlpha(final float alpha)
	{
		final Random random = new Random();
		final float r = random.nextFloat();
		final float g = random.nextFloat();
		final float b = random.nextFloat();
		return new Color(r, g, b, alpha);
	}
}
