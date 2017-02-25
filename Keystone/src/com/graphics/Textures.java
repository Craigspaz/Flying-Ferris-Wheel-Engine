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
	public static Texture playerFront;
	public static Texture playerOutline;
	
	public static Texture playerLaser;
	
	
	public static Texture testTile;
	
	public static Texture table;
	public static Texture tableOutline;
	
	public static Texture testLine;
	public static Texture sean;
	
	public static Texture particles;
	
	/**
	 * Initializes all of the textures
	 */
	public Textures()
	{
		playerFront = Loader.loadTexture("playerSpriteSheet");
		playerOutline = Loader.loadTexture("playerSpriteSheet_outline");
		playerLaser = Loader.loadTexture("lasers-v4");
		testTile = Loader.loadTexture("Floating_Platform_2");
		table = Loader.loadTexture("table-v3");
		tableOutline = Loader.loadTexture("table-v3-outline");
		sean = Loader.loadTexture("sean");
		testLine = Loader.loadTexture("testLine");
		particles = Loader.loadTexture("particle");
	}
}
