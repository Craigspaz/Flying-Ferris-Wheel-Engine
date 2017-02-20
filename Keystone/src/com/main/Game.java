package com.main;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.Textures;
import com.graphics.world.Camera;
import com.graphics.world.Entity;
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
	
	private Entity table;
	private Entity sean;
	
	private ArrayList<RectangleBox> colliders = new ArrayList<RectangleBox>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	private Camera camera;
	
	/**
	 * Creates the game world
	 */
	public Game()
	{
		new Textures();
		camera = new Camera(new Vector2f(0,0),new Vector2f(Window.width,Window.height));
		
		table = new Entity(new Vector3f(64,128,0), Textures.table, new Vector2f(256,32), 6, 1, new Vector2f(32,32), new Vector2f(32,32));
		sean = new Entity(new Vector3f(64,128,0), Textures.sean, new Vector2f(256,32), 1, 1, new Vector2f(128,128), new Vector2f(32,32));
		table.setAffectedByGravity(true);
		sean.setAffectedByGravity(true);
		
		
		test = new Player(new Vector3f(32,60,0),Textures.playerTest,new Vector2f(512,32),10,1,new Vector2f(32,32), new Vector2f(32,32));
		//tiles.add(new Tile(new Vector3f(64,256,0),new Vector2f(64,64),Textures.testTile));
		//tiles.add(new Tile(new Vector3f(128,320,0),new Vector2f(64,64),Textures.testTile));
		
		tiles.add(new Tile(new Vector3f(0,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(64,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(128,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(192,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256 + 64,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256 + 128,550,0),new Vector2f(64,64),Textures.testTile));
		tiles.add(new Tile(new Vector3f(256 + 192,550,0),new Vector2f(64,64),Textures.testTile));
		
		Tile tmp = new Tile(new Vector3f(-64,486,0),new Vector2f(64,64),Textures.testTile);
		Tile tmp1 = new Tile(new Vector3f(512,486,0),new Vector2f(64,64), Textures.testTile);
		
		
		tiles.add(tmp);
		tiles.add(tmp1);
		
		
		//colliders.add(new RectangleBox(new Vector3f(64,256,0),new Vector2f(64,64)));
		//colliders.add(new RectangleBox(new Vector3f(128,320,0),new Vector2f(64,64)));
		colliders.add(new RectangleBox(new Vector3f(0,550,0),new Vector2f(512,64)));
		colliders.add(tmp.getCollider());
		colliders.add(tmp1.getCollider());
		
		
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
		GL11.glTranslatef(-camera.getPosition().x,-camera.getPosition().y,0.0f);

		table.render();
		sean.render();
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
		table.update(colliders);
		sean.update(colliders);
		if(new Random().nextBoolean())
		{
			table.moveLeft();
		}
		else
		{
			table.moveRight();
		}
		
		if(new Random().nextBoolean())
		{
			sean.moveLeft();
		}
		else
		{
			sean.moveRight();
		}
		for(Tile t : tiles)
		{
			t.update();
		}
		camera.setPositionToPlayer(test, Window.width, Window.height);
		camera.update();
	}

	/**
	 * Cleans up memory if needed
	 */
	public void cleanUPGame()
	{

	}
}
