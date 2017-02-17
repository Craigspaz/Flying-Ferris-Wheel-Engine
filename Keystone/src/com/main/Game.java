package com.main;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.Loader;
import com.graphics.Textures;
import com.graphics.world.Player;
import com.graphics.world.Tile;

/**
 * Handles the operation of the game
 * @author Craig Ferris
 *
 */
public class Game
{
	
	private Player test;
	
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	/**
	 * Creates the game world
	 */
	public Game()
	{
		new Textures();
		
		
		test = new Player(new Vector3f(32,32,0),Textures.playerTest,new Vector2f(512,32),10,new Vector2f(32,32));
		for(int i = 0; i < 10; i++)
		{
			Tile testTile = new Tile(new Vector3f(0 + (i * 64),128,0),new Vector2f(64,64),Textures.testTile);
			tiles.add(testTile);
		}
		for(int i = 0; i < 10; i++)
		{
			Tile testTile = new Tile(new Vector3f(187 + (i * 64),256,0),new Vector2f(64,64),Textures.testTile);
			tiles.add(testTile);
		}
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
		test.update(tiles);
		for(Tile t : tiles)
		{
			t.update();
			/*if(t.isCollidingWithEntity2D(test))
			{
				Vector3f nPos = new Vector3f(test.getPosition().x,t.getPosition().y - test.getScale().y,0);
				test.setPosition(nPos);
				//System.out.println("Colliding");
			}*/
		}
	}

	/**
	 * Cleans up memory if needed
	 */
	public void cleanUPGame()
	{

	}
}
