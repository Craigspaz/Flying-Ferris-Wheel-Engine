package com.main;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.Textures;
import com.graphics.world.Camera;
import com.graphics.world.Entity;
import com.graphics.world.Level;
import com.graphics.world.Particle;
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.graphics.world.World;
import com.graphics.world.projectile.Projectile;

/**
 * Handles the operation of the game
 * 
 * @author Craig Ferris
 *
 */
public class Game
{

	private Player player;

	private Entity table;
	// private Entity sean;

	private ArrayList<RectangleBox> worldColliders = new ArrayList<RectangleBox>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
	private ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
	private ArrayList<Particle> particles = new ArrayList<Particle>();

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	private Camera camera;

	private Level testLevel;
	private World testWorld;
	
	private Particle particle;

	// private Projectile testProjectile;

	/**
	 * Creates the game world
	 */
	public Game()
	{
		new Textures();
		camera = new Camera(new Vector2f(0, 0), new Vector2f(Window.width, Window.height));

		table = new Entity(new Vector3f(64, 128, 0), Textures.table, Textures.tableOutline, new Vector2f(256, 32), 6, 1,
				new Vector2f(32, 32), new Vector2f(32, 32));
		// sean = new Entity(new Vector3f(64,256,0), Textures.sean, new Vector2f(32,32), 1, 1, new Vector2f(128,128),
		// new Vector2f(32,32));
		table.setAffectedByGravity(true);
		table.setAnimateFrameTime(10);
		// sean.setAffectedByGravity(true);

		entities.add(table);

		player = new Player(new Vector3f(32, 32, 0), Textures.playerFront, Textures.playerOutline,
				new Vector2f(512, 256), 0, 0, new Vector2f(32, 32), new Vector2f(32, 32));

		testWorld = new World();
		testLevel = testWorld.loadWorld("./res/world/level1.od");
		worldColliders = testLevel.getColliders();
		tiles = testLevel.getTiles();
		
		//particle = new Particle(new Vector2f(96,750),new Vector2f(16,16),Textures.particles,12,0,false, new Vector2f(16,16),new Vector2f(256,128));
		// tiles.add(new Tile(new Vector3f(64,256,0),new Vector2f(64,64),Textures.testTile));
		// tiles.add(new Tile(new Vector3f(128,320,0),new Vector2f(64,64),Textures.testTile));

		/*
		 * tiles.add(new Tile(new Vector3f(0,550,0),new Vector2f(64,64),Textures.testTile)); tiles.add(new Tile(new
		 * Vector3f(64,550,0),new Vector2f(64,64),Textures.testTile)); tiles.add(new Tile(new Vector3f(128,550,0),new
		 * Vector2f(64,64),Textures.testTile)); tiles.add(new Tile(new Vector3f(192,550,0),new
		 * Vector2f(64,64),Textures.testTile)); tiles.add(new Tile(new Vector3f(256,550,0),new
		 * Vector2f(64,64),Textures.testTile)); tiles.add(new Tile(new Vector3f(256 + 64,550,0),new
		 * Vector2f(64,64),Textures.testTile)); tiles.add(new Tile(new Vector3f(256 + 128,550,0),new
		 * Vector2f(64,64),Textures.testTile)); tiles.add(new Tile(new Vector3f(256 + 192,550,0),new
		 * Vector2f(64,64),Textures.testTile));
		 * 
		 * Tile tmp = new Tile(new Vector3f(-64,486,0),new Vector2f(64,64),Textures.testTile); Tile tmp1 = new Tile(new
		 * Vector3f(512,486,0),new Vector2f(64,64), Textures.testTile);
		 * 
		 * 
		 * tiles.add(tmp); tiles.add(tmp1);
		 */

		// colliders.add(new RectangleBox(new Vector3f(64,256,0),new Vector2f(64,64)));
		// colliders.add(new RectangleBox(new Vector3f(128,320,0),new Vector2f(64,64)));
		/*
		 * colliders.add(new RectangleBox(new Vector3f(0,550,0),new Vector2f(512,64)));
		 * colliders.add(tmp.getCollider()); colliders.add(tmp1.getCollider());
		 * 
		 * testProjectile = new Projectile(new Vector3f(64,400,0), Textures.table, new Vector2f(256,32), 6, 1, new
		 * Vector2f(32,32), new Vector2f(32,32),0);
		 */

		/*
		 * for(int i = 0; i < 10; i++) { Tile testTile = new Tile(new Vector3f(0 + (i * 64),500,0),new
		 * Vector2f(64,64),Textures.testTile); tiles.add(testTile); }
		 */
		/*
		 * for(int i = 0; i < 10; i++) { Tile testTile = new Tile(new Vector3f(800 + (i * 64),500,0),new
		 * Vector2f(64,64),Textures.testTile); tiles.add(testTile); }
		 */
		/*
		 * for(int i = 0; i < 10; i++) { Tile testTile = new Tile(new Vector3f(187 + (i * 64),550,0),new
		 * Vector2f(64,64),Textures.testTile); tiles.add(testTile); }
		 */
	}

	/**
	 * Draws objects to the screen. Is executed for every frame
	 */
	public void render()
	{
		GL11.glTranslatef(-camera.getPosition().x, -camera.getPosition().y, 0.0f);
		// sean.render();
		for (Entity e : entities)
		{
			e.renderOutline();
		}
		player.renderOutline();
		player.render();
		for (Tile t : tiles)
		{
			t.render();
		}
		for (Entity e : entities)
		{
			e.render();
		}
		for (Projectile p : playerProjectiles)
		{
			p.render();
		}
		for (Projectile p : enemyProjectiles)
		{
			p.render();
		}
		for(Particle p : particles)
		{
			p.render();
		}
		// testProjectile.render();
		
	}

	/**
	 * Updates objects and handles physics. Is executed a certain number of times a second
	 */
	public void update()
	{
		player.update(worldColliders);
		player.checkForCollisionWithProjectiles(enemyProjectiles);
		for (Tile t : tiles)
		{
			t.update();
		}

		for (Entity e : entities)
		{
			e.update(worldColliders);
			e.checkForCollisionWithProjectiles(playerProjectiles);
		}

		if (!player.getProjectiles().isEmpty())
		{
			playerProjectiles.addAll(player.getProjectiles());
			player.getProjectiles().clear();
		}
		
		if(!player.getParticles().isEmpty())
		{
			particles.addAll(player.getParticles());
			player.getParticles().clear();
		}

		for (Projectile p : playerProjectiles)
		{
			p.update(worldColliders);
		}
		for (Projectile p : enemyProjectiles)
		{
			p.update(worldColliders);
		}
		
		for(Particle p : particles)
		{
			p.update();
		}
		int i = 0;
		while (i < playerProjectiles.size())
		{
			while (i < playerProjectiles.size())
			{
				if (playerProjectiles.get(i).isDead())
				{
					playerProjectiles.remove(i);
					break;
				}
				i++;
			}
		}
		i = 0;
		while (i < enemyProjectiles.size())
		{
			while (i < enemyProjectiles.size())
			{
				if (enemyProjectiles.get(i).isDead())
				{
					enemyProjectiles.remove(i);
					break;
				}
				i++;
			}
		}
		i = 0;
		while (i < entities.size())
		{
			while (i < entities.size())
			{
				if (entities.get(i).isDead())
				{
					entities.remove(i);
					break;
				}
				i++;
			}
		}
		
		i = 0;
		while (i < particles.size())
		{
			while (i < particles.size())
			{
				if (particles.get(i).isDead())
				{
					particles.remove(i);
					break;
				}
				i++;
			}
		}
		// testProjectile.update(colliders);
		camera.setPositionToPlayer(player, Window.width, Window.height);
		camera.update();
	}

	/**
	 * Cleans up memory if needed
	 */
	public void cleanUPGame()
	{

	}
}
