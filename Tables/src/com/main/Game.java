package com.main;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Camera;
import com.graphics.world.DialogBox;
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

	private Player					player;

	private Enemy					table;
	private ArrayList<RectangleBox>	worldColliders		= new ArrayList<RectangleBox>();
	private ArrayList<Tile>			tiles				= new ArrayList<Tile>();
	private ArrayList<Projectile>	playerProjectiles	= new ArrayList<Projectile>();
	private ArrayList<Projectile>	enemyProjectiles	= new ArrayList<Projectile>();
	private ArrayList<Particle>		particles			= new ArrayList<Particle>();
	private ArrayList<Enemy>		enemies				= new ArrayList<Enemy>();
	private ArrayList<DialogBox>	dialogue;

	public static ArrayList<Entity>	entities			= new ArrayList<Entity>();

	private Camera					camera;

	private Level					currentLevel;

	private InputHandler			handler;
	private Terminal				terminal;

	private Tile					testTile0;
	private Tile					testTile1;
	private Tile					testTile2;
	private Tile					sky;

	private DialogBox				currentDialogue;
	// private Projectile testProjectile;

	/**
	 * Creates the game world
	 */
	public Game()
	{
		new Textures();
		handler = new InputHandler();
		GFX.initString();

		table = new Enemy(new Vector3f(512, 256, 0), Textures.sean, Textures.sean, new Vector2f(128, 128), 1, 1, new Vector2f(32, 32), new Vector2f(32, 32));
		table.setAffectedByGravity(true);
		table.setAnimateFrameTime(10);
		table.setHostileToPlayer(true);

		entities.add(table);

		setPlayer(new Player(new Vector3f(32, 32, 0), Textures.playerFront, Textures.playerOutline, new Vector2f(512, 256), 0, 0, new Vector2f(32, 32), new Vector2f(32, 32), handler));
		getPlayer().setAnimateFrameTime(3.0f);

		camera = new Camera(new Vector2f(getPlayer().getPosition().x, getPlayer().getPosition().y), new Vector2f(Window.width, Window.height));
		camera.setPositionToPlayer(getPlayer(), Window.width, Window.height);
		terminal = new Terminal(handler, getPlayer(), camera, this);
		sky = new Tile(new Vector3f(-256, -112, 100), new Vector2f(1024, 1024), Textures.sky);
		testTile2 = new Tile(new Vector3f(-256, -112, 10), new Vector2f(1024, 1024), Textures.desert2);
		testTile1 = new Tile(new Vector3f(-256, -112, 5), new Vector2f(1024, 1024), Textures.desert1);
		testTile0 = new Tile(new Vector3f(-256, -112, 2), new Vector2f(1024, 1024), Textures.desert0);

		loadNewLevel("./res/world/level1.ffw");
	}

	/**
	 * Draws objects to the screen. Is executed for every frame
	 */
	public void render()
	{
		GL11.glTranslatef(-camera.getPosition().x, -camera.getPosition().y, 0.0f);

		for (Tile t : tiles)
		{
			if (t.getPosition().z >= 0)
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
		for (Particle p : particles)
		{
			p.render();
		}
		getPlayer().renderOutline();
		getPlayer().render();
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
		for (Tile t : tiles)
		{
			if (t.getPosition().z < 0)
			{
				t.render();
			}
		}
		if (currentDialogue != null && currentDialogue.active())
		{
			float textBoxX = camera.getPosition().x + (camera.getSize().x / 2) - 384;// relative to camera, not world
			float textBoxY = camera.getPosition().y + camera.getSize().y - 156;
			currentDialogue.render(textBoxX, textBoxY);
		}
		terminal.render(camera.getPosition().x, camera.getPosition().y + camera.getSize().y);

	}

	/**
	 * Updates objects and handles physics. Is executed a certain number of times a second
	 */
	public void update()
	{
		terminal.update();
		if (!terminal.active())// pauses game while terminal is active
		{
			if (currentDialogue != null)
			{
				if (currentDialogue.active())
					currentDialogue.update(handler);
			}
			getPlayer().update(worldColliders);
			getPlayer().checkForCollisionWithProjectiles(enemyProjectiles);
			for (Tile t : tiles)
			{
				t.update();
			}

			for (Entity e : entities)
			{
				e.update(worldColliders);
				e.checkForCollisionWithProjectiles(playerProjectiles);
				if (e.isHostileToPlayer())
				{
					enemies.add(new Enemy(e));
					e.setDead(true);
				}
			}
			for (Enemy e : enemies)
			{
				e.update(worldColliders, player);
				e.checkForCollisionWithProjectiles(playerProjectiles);
				/*if (new Random().nextBoolean())
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
				}*/
			}

			if (!getPlayer().getProjectiles().isEmpty())
			{
				playerProjectiles.addAll(getPlayer().getProjectiles());
				getPlayer().getProjectiles().clear();
			}

			if (!getPlayer().getParticles().isEmpty())
			{
				particles.addAll(getPlayer().getParticles());
				getPlayer().getParticles().clear();
			}

			for (Projectile p : playerProjectiles)
			{
				if (!p.getParticles().isEmpty())
				{
					particles.addAll(p.getParticles());
					p.getParticles().clear();
				}
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
			camera.setPositionToPlayer(getPlayer(), Window.width, Window.height);
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

	/**
	 * Loads a new level
	 * 
	 * @param name
	 *            The name of the level to load
	 * @return Returns true if the level loaded successfully else returns false
	 */
	public boolean loadNewLevel(String name)
	{
		currentLevel = World.loadWorld(name);
		if (currentLevel == null)
		{
			return false;
		}

		worldColliders = currentLevel.getColliders();

		tiles = currentLevel.getTiles();
		dialogue = currentLevel.getDialogue();
		if (dialogue != null && dialogue.size() > 0)
		{
			currentDialogue = dialogue.get(0);// this will be changed when an object is interacted with
			currentDialogue.activate();
		}
		tiles.add(sky);
		tiles.add(testTile2);
		tiles.add(testTile1);
		tiles.add(testTile0);

		tiles = World.sortTiles(tiles);

		//entities.addAll(currentLevel.getEntities());

		for (Entity e : entities)
		{
			if (e.isHostileToPlayer())
			{
				enemies.add(new Enemy(e));
				e.setDead(true);
			}
		}
		if (getPlayer() != null && currentLevel.getPlayerSpawnLocation() != null)
		{
			setPlayer(new Player(currentLevel.getPlayerSpawnLocation(), Textures.playerFront, Textures.playerOutline, new Vector2f(512, 256), 0, 0, new Vector2f(32, 32), new Vector2f(32, 32), handler));
		}
		return true;
	}

	/**
	 * Sets a player to the exit door location in the next level
	 * 
	 * @param destination
	 *            The location to set the player
	 */
	public void exitDoor(Vector3f destination)
	{
		player.setPosition(destination);
	}

	/**
	 * Returns a pointer to the player
	 * 
	 * @return Returns a pointer to the player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Sets the player
	 * 
	 * @param player
	 *            The player to set
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	/**
	 * Returns a pointer to the input handler
	 * 
	 * @return Returns a pointer to the input handler
	 */
	public InputHandler getHandler()
	{
		return handler;
	}
}
