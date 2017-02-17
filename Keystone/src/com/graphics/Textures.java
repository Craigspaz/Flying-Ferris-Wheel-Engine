package com.graphics;

import org.newdawn.slick.opengl.Texture;

/**
 * Loads and stores textures
 * @author Craig Ferris
 *
 */
public class Textures
{
	
	public static Texture playerTest;
	public static Texture testTile;
	
	public Textures()
	{
		playerTest = Loader.loadTexture("sprint");
		testTile = Loader.loadTexture("Floating_Platform_2");
	}
}
