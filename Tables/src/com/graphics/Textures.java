package com.graphics;

import org.newdawn.slick.opengl.Texture;
import com.graphics.Textures;

/**
 * Loads and stores textures
 * 
 * @author Craig Ferris
 *
 */
public class Textures
{
	public static Texture	playerFront;
	public static Texture	playerOutline;

	public static Texture	playerLaser;

	public static Texture	testTile;

	public static Texture	table;
	public static Texture	tableOutline;

	public static Texture	testLine;
	public static Texture	sean;

	public static Texture	particles;

	public static Texture	black;
	public static Texture	terminalWindow;

	public static Texture	crabman;

	public static Texture	desert0;
	public static Texture	desert1;
	public static Texture	desert2;
	public static Texture	sky;

	public static Texture	highlight;

	public static Texture	air;
	public static Texture	bridge;
	public static Texture	dirt2;
	public static Texture	dirt;
	public static Texture	grass;
	public static Texture	grassTop;

	public static Texture	portraits;
	public static Texture	textBoxes;

	/**
	 * Initializes all of the textures
	 */
	public Textures()
	{
		playerFront = Loader.loadTexture("tableSpriteSheet");
		playerOutline = Loader.loadTexture("tableSpriteSheet_outline");
		playerLaser = Loader.loadTexture("lasers-v4");
		testTile = Loader.loadTexture("Floating_Platform_2");
		table = Loader.loadTexture("table-v3");
		tableOutline = Loader.loadTexture("table-v3-outline");
		sean = Loader.loadTexture("sean");
		testLine = Loader.loadTexture("testLine");
		particles = Loader.loadTexture("particle");

		black = Loader.loadTexture("black");
		crabman = Loader.loadTexture("crabman");

		desert0 = Loader.loadTexture("desert0");
		desert1 = Loader.loadTexture("desert1");
		desert2 = Loader.loadTexture("desert2");
		sky = Loader.loadTexture("sky");

		highlight = Loader.loadTexture("highlight");
		terminalWindow = Loader.loadTexture("terminal");

		air = Loader.loadTexture("air");
		bridge = Loader.loadTexture("bridge");
		dirt2 = Loader.loadTexture("Dirt (2)");
		dirt = Loader.loadTexture("dirt");
		grass = Loader.loadTexture("grass");
		grassTop = Loader.loadTexture("grassTop");
		portraits = Loader.loadTexture("portraits");
		textBoxes = Loader.loadTexture("textboxes");
	}
}
