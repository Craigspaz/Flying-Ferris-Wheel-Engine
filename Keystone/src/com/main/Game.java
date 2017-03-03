package com.main;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Camera;
import com.graphics.world.Entity;
import com.graphics.world.Level;
import com.graphics.world.Particle;
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.graphics.world.World;
import com.graphics.world.enemys.Enemy;
import com.graphics.world.projectile.Projectile;
import com.input.InputHandler;
import com.input.Terminal;

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
	private ArrayList<RectangleBox>	worldColliders		= new ArrayList<RectangleBox>();
	private ArrayList<Tile>			tiles				= new ArrayList<Tile>();
	private ArrayList<Projectile>	playerProjectiles	= new ArrayList<Projectile>();
	private ArrayList<Projectile>	enemyProjectiles	= new ArrayList<Projectile>();
	private ArrayList<Particle>		particles			= new ArrayList<Particle>();
	private ArrayList<Enemy>		enemies				= new ArrayList<Enemy>();

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	private Camera camera;

	private Level testLevel;
	private World testWorld;

	private InputHandler handler;
	private Terminal terminal;

	private Tile					testTile0;
	private Tile					testTile1;
	private Tile					testTile2;
	private Tile					sky;

	// private Projectile testProjectile;

	/**
	 * Creates the game world
	 */
	public Game()
	{
		new Textures();
		handler = new InputHandler();
		camera = new Camera(new Vector2f(0, 0), new Vector2f(Window.width, Window.height));
		GFX.initString();

		table = new Entity(new Vector3f(64, 256, 0), Textures.sean, Textures.sean, new Vector2f(128, 128), 1, 1, new Vector2f(32, 32), new Vector2f(32, 32));
		// table = new Entity(new Vector3f(64, 256, 0), Textures.sean, new
		// Vector2f(32, 32), 1, 1, new Vector2f(128, 128),new Vector2f(32, 32));
		table.setAffectedByGravity(true);
		table.setAnimateFrameTime(10);
		// sean.setAffectedByGravity(true);

		entities.add(table);

		player = new Player(new Vector3f(32, 32, 0), Textures.playerFront, Textures.playerOutline,
				new Vector2f(512, 256), 0, 0, new Vector2f(32, 32), new Vector2f(32, 32), handler);

		terminal = new Terminal(handler,player);
		camera = new Camera(new Vector2f(player.getPosition().x, player.getPosition().y), new Vector2f(Window.width, Window.height));
		camera.setPositionToPlayer(player, Window.width, Window.height);

		sky = new Tile(new Vector3f(-256, -112, 100), new Vector2f(1024, 1024), Textures.sky);
		testTile2 = new Tile(new Vector3f(-256, -112, 10), new Vector2f(1024, 1024), Textures.desert2);
		testTile1 = new Tile(new Vector3f(-256, -112, 5), new Vector2f(1024, 1024), Textures.desert1);
		testTile0 = new Tile(new Vector3f(-256, -112, 2), new Vector2f(1024, 1024), Textures.desert0);

		testWorld = new World();
		testLevel = testWorld.loadWorld("./res/world/level1.od");
		worldColliders = testLevel.getColliders();

		tiles = testLevel.getTiles();

		tiles.add(sky);
		tiles.add(testTile2);
		tiles.add(testTile1);
		tiles.add(testTile0);

		tiles = World.sortTiles(tiles);

		entities.addAll(testLevel.getEntities());

		for (Entity e : entities)
		{
			if (e.isHostileToPlayer())
			{
				enemies.add(new Enemy(e));
				e.setDead(true);
			}
		}
	}

	/**
	 * Draws objects to the screen. Is executed for every frame
	 */
	public void render()
	{
		GL11.glTranslatef(-camera.getPosition().x, -camera.getPosition().y, 0.0f);
		// sean.render();

		for (Tile t : tiles)
		{
			if (t.getPosition().z > 0)
			{
				t.render();
			}
		}
		for (Entity e : entities)
		{
			e.renderOutline();
		}
		for (Enemy e : enemies)
		{
			e.renderOutline();
		}
		player.renderOutline();
		player.render();
		for (Entity e : entities)
		{
			e.render();
		}
		for (Enemy e : enemies)
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
		for (Particle p : particles)
		{
			p.render();
		}
		for (Tile t : tiles)
		{
			if (t.getPosition().z <= 0)
			{
				t.render();
			}
		}
		// testProjectile.render();
		// GFX.drawString(64,600, "Press Enter to continue!");
		terminal.render(camera.getPosition().x, camera.getPosition().y + camera.getSize().y);
	}

	/**
	 * Updates objects and handles physics. Is executed a certain number of times a second
	 */
	public void update()
	{
		terminal.update();
		if (!terminal.active())
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
			for (Enemy e : enemies)
			{
				e.update(worldColliders);
				e.checkForCollisionWithProjectiles(playerProjectiles);
				if (new Random().nextBoolean())
				{
					if (new Random().nextBoolean())
					{
						e.setMoveLeft(false);
						e.setMoveRight(true);
					} else
					{
						e.setMoveRight(false);
						e.setMoveLeft(true);
					}
				}
			}

			if (!player.getProjectiles().isEmpty())
			{
				playerProjectiles.addAll(player.getProjectiles());
				player.getProjectiles().clear();
			}

			if (!player.getParticles().isEmpty())
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

			for (Particle p : particles)
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
			i = 0;
			while (i < enemies.size())
			{
				while (i < enemies.size())
				{
					if (enemies.get(i).isDead())
					{
						enemies.remove(i);
						break;
					}
					i++;
				}
			}
			// testProjectile.update(colliders);
			camera.setPositionToPlayer(player, Window.width, Window.height);
			for (Tile t : tiles)
			{
				if (t.getPosition().z > 1)
				{
					t.getPosition().x -= camera.getOffset().x / t.getPosition().z;
					// System.out.println(camera.getOffset().x);
				} else if (t.getPosition().z < 0)
				{
					t.getPosition().x += camera.getOffset().x / t.getPosition().z;
				}
			}
			camera.update();
		}

	}

	/**
	 * Cleans up memory if needed
	 */
	public void cleanUPGame()
	{

	}
}
