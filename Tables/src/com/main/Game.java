package com.main;

import java.util.ArrayList;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.openal.SoundStore;

import com.audio.SoundEffects;
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

	public ArrayList<Entity>		entities			= new ArrayList<Entity>();

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
		new Textures(); // Loads textures
		// new SoundEffects(); // Loads Sound effects
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

		if (!loadNewLevel("./res/world/tiletest.ffw"))
		{
			throw new NullPointerException("World Could not be loaded");
		}
	}

	/**
	 * Draws objects to the screen. Is executed for every frame
	 */
	public void render()
	{
		GL11.glTranslatef(-camera.getPosition().x, -camera.getPosition().y, 0.0f); // Moves the camera to the correct
																					// location

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
		// Renders the dialog box
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
		// test
		if (handler.up())
		{
			// SoundEffects.testEffect.playAsSoundEffect(1.0f, 1.0f, false);
		}
		// endtest
		terminal.update();
		if (!terminal.active())// pauses game while terminal is active
		{
			// Updates the dialogue box
			if (currentDialogue != null)
			{
				if (currentDialogue.active())
					currentDialogue.update(handler);
			}
			// Updates the player
			getPlayer().update(worldColliders);
			getPlayer().checkForCollisionWithProjectiles(enemyProjectiles);
			// Updates tiles
			for (Tile t : tiles)
			{
				t.update();
			}

			// Updates entities
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

			// Updates the enemy
			for (Enemy e : enemies)
			{
				e.update(worldColliders, player);
				e.checkForCollisionWithProjectiles(playerProjectiles);
				/*
				 * if (new Random().nextBoolean()) { if (new Random().nextBoolean()) { e.setMoveLeft(false); e.setMoveRight(true); } else { e.setMoveRight(false); e.setMoveLeft(true); } }
				 */
			}

			// Adds all projectiles the player fired to playerProjectiles
			if (!getPlayer().getProjectiles().isEmpty())
			{
				playerProjectiles.addAll(getPlayer().getProjectiles());
				getPlayer().getProjectiles().clear();
			}

			// Adds all particles the player made
			if (!getPlayer().getParticles().isEmpty())
			{
				particles.addAll(getPlayer().getParticles());
				getPlayer().getParticles().clear();
			}

			// Updates the projectiles the player fired
			for (Projectile p : playerProjectiles)
			{
				if (!p.getParticles().isEmpty())
				{
					particles.addAll(p.getParticles());
					p.getParticles().clear();
				}
				p.update(worldColliders);
			}
			// Updates the projectiles the enemies fired
			for (Projectile p : enemyProjectiles)
			{
				p.update(worldColliders);
			}

			// Updates the particles in the world
			for (Particle p : particles)
			{
				p.update();
			}

			// Cleans up the playerProjectiles by removing projectiles that are dead
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
			// Cleans up the enemy projectiles by removing projectiles that are dead
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
			// Cleans up entities by removing dead entities
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
			// Cleans up particles that are done running
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
			// Cleans up enemies that are dead
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
			camera.setPositionToPlayer(getPlayer(), Window.width, Window.height); // Sets the camera to have the player
																					// centered
			// Handles parallax calculations
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
		SoundStore.get().poll(0);
	}

	/**
	 * Cleans up memory if needed
	 */
	public void cleanUPGame()
	{
		AL.destroy();
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
		if (name == null)
		{
			return false;
		}
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

		// Adds parallax tiles
		tiles.add(sky);
		tiles.add(testTile2);
		tiles.add(testTile1);
		tiles.add(testTile0);

		tiles = World.sortTiles(tiles); // adds world tiles

		enemies.addAll(currentLevel.getEnemies());

		// Adds all entities that are of type enemy to the enemy list
		/*
		 * for (Entity e : entities) { if (e.isHostileToPlayer()) { enemies.add(new Enemy(e)); e.setDead(true); } }
		 */

		// Sets the players spawn location to the location specified in the level
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

	/**
	 * Adds an enemy to the world
	 * 
	 * @param ee
	 *            The enemy to add to the world
	 */
	public void addEnemy(Enemy ee)
	{
		enemies.add(ee);
	}

	/**
	 * Adds an enemy to the world
	 * 
	 * @param e
	 *            The entity to add to the world
	 */
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
}
