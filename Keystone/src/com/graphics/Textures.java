package com.graphics;

import java.util.ArrayList;

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
	public static Texture table;
	
	public static Texture testLine;
	public static Texture sean;
	
	public Textures()
	{
		playerTest = Loader.loadTexture("playerSpriteSheet");
		testTile = Loader.loadTexture("Floating_Platform_2");
		table = Loader.loadTexture("table-v3");
		sean = Loader.loadTexture("sean");
		testLine = Loader.loadTexture("testLine");
	}
}
