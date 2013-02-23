package com.dawid.andengine.helloworld.game.components;

public class TortSzam
{
	private final int nevezo;
	private final int szamlalo;
	
	public TortSzam(final int pNevezo, final int pSzamlalo)
	{
		nevezo = pNevezo;
		szamlalo = pSzamlalo;
	}
	
	public float getSzam()
	{
		return nevezo / szamlalo;
	}
	
	public int getNevezo()
	{
		return nevezo;
	}

	public int getSzamlalo()
	{
		return szamlalo;
	}
}
