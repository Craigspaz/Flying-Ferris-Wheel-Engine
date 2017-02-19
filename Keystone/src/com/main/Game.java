package com.main;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.Textures;
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;

/**
 * Handles the operation of the game
 * @author Craig Ferris
 *
 */
public class Game
{
	
	private Player test;
	
	private ArrayList<RectangleBox> colliders = new ArrayList<RectangleBox>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	/**
	 * Creates the game world
	 */
	public Game()
	{
		new Textures();
		
		
		test = new Player(new Vector3f(32,32,0),Textures.playerTest,new Vector2f(512,32),10,new Vector2f(32,32));
		tiles.add(new Tile(new Vector3f(32,160,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(128,192,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(0,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(64,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(128,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(192,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256 + 64,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256 + 128,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256 + 192,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(512,550,0),new Vector2f(64,64),Textures.testTile));
		
		
		colliders.add(new RectangleBox(new Vector3f(32,160,0),new Vector2f(64,64)));
		colliders.add(new RectangleBox(new Vector3f(128,192,0),new Vector2f(64,64)));
		colliders.add(new RectangleBox(new Vector3f(0,550,0),new Vector2f(512,64)));
		
		
		/*for(int i = 0; i < 10; i++)
		{
			Tile testTile = new Tile(new Vector3f(0 + (i * 64),500,0),new Vector2f(64,64),Textures.testTile);
			tiles.add(testTile);
		}*/
		/*for(int i = 0; i < 10; i++)
		{
			Tile testTile = new Tile(new Vector3f(800 + (i * 64),500,0),new Vector2f(64,64),Textures.testTile);
			tiles.add(testTile);
		}*/
		/*for(int i = 0; i < 10; i++)
		{
			Tile testTile = new Tile(new Vector3f(187 + (i * 64),550,0),new Vector2f(64,64),Textures.testTile);
			tiles.add(testTile);
		}*/
	}

	/**
	 * Draws objects to the screen. Is executed for every frame
	 */
	public void render()
	{
		test.render();
		for(Tile t : tiles)
		{
			t.render();
		}
	}

	/**
	 * Updates objects and handles physics. Is executed a certain number of times a second
	 */
	public void update()
	{
		test.update(colliders);
		for(Tile t : tiles)
		{
			t.update();
		}
	}

	/**
	 * Cleans up memory if needed
	 */
	public void cleanUPGame()
	{

	}
}
