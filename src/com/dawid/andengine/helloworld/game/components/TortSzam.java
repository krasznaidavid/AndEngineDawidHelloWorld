package com.dawid.andengine.helloworld.game.components;

public class TortSzam
{
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

	final int nevezo;
	final int szamlalo;
}
