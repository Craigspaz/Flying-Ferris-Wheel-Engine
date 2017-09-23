package com.main;

import java.util.ArrayList;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.openal.SoundStore;

import com.audio.SoundEffects;
import com.graphics.GFX;
import com.graphics.Mesh;
import com.graphics.Shader;
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
import com.graphics.world.util.Edge;
import com.graphics.world.util.Vertex;
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
	public static float				SCALE					= 2f;
	public static float				counter					= 0f;
	public static boolean			debugMode				= true;

	private Player					player;

	public static Vector2f			playerPosition;

	private ArrayList<RectangleBox>	worldColliders			= new ArrayList<RectangleBox>();
	private ArrayList<Tile>			tiles					= new ArrayList<Tile>();
	private ArrayList<Projectile>	playerProjectiles		= new ArrayList<Projectile>();
	private ArrayList<Projectile>	enemyProjectiles		= new ArrayList<Projectile>();
	private ArrayList<Particle>		particles				= new ArrayList<Particle>();
	private ArrayList<Enemy>		enemies					= new ArrayList<Enemy>();
	private ArrayList<DialogBox>	dialogue;

	public ArrayList<Entity>		entities				= new ArrayList<Entity>();

	private static Camera			camera;

	private Level					currentLevel;

	private InputHandler			handler;
	private Terminal				terminal;

	private Tile					testTile0;
	private Tile					testTile1;
	private Tile					testTile2;
	private Tile					sky;

	private ArrayList<Tile>			tutorialButtons			= new ArrayList<Tile>();

	private DialogBox				currentDialogue;

	private float					tmpCounter				= 1f;

	private GameStates				currentState			= GameStates.SPLASH;
	private Thread					loadLevelThread			= null;

	private int						splashScreenTickCounter	= 0;

	private String					nextLevelName			= "level1";

	private Shader					testShader;

	private Mesh					testMesh;

	// private Projectile testProjectile;

	/**
	 * Creates the game world
	 */
	public Game()
	{
		new Textures(); // Loads textures
		new SoundEffects(); // Loads Sound effects
		handler = new InputHandler();
		GFX.initString();

		setPlayer(new Player(new Vector3f(32, 32, 0), Textures.playerFront, Textures.playerOutline, 0, 0, new Vector2f(32, 32), handler));
		getPlayer().setAnimateFrameTime(3.0f);

		playerPosition = new Vector2f(getPlayer().getPosition().getX(), getPlayer().getPosition().getY());

		camera = new Camera(new Vector2f(getPlayer().getPosition().x, getPlayer().getPosition().y), new Vector2f(Window.width, Window.height));
		camera.setPositionToPlayer(getPlayer(), Window.width, Window.height);
		terminal = new Terminal(handler, getPlayer(), camera, this);
		sky = new Tile(new Vector3f(-256, -112, 100), new Vector2f(1024, 1024), Textures.sky);
		testTile2 = new Tile(new Vector3f(-256, -112, 10), new Vector2f(1024, 1024), Textures.desert2);
		testTile1 = new Tile(new Vector3f(-256, -112, 5), new Vector2f(1024, 1024), Textures.desert1);
		testTile0 = new Tile(new Vector3f(-256, -112, 2), new Vector2f(1024, 1024), Textures.desert0);

		tutorialButtons.add(new Tile(new Vector3f(0, 0, 0), new Vector2f(14, 14), Textures.tutorialButtons, 2, 0, 20, 1));
		tutorialButtons.add(new Tile(new Vector3f(0, 0, 0), new Vector2f(14, 14), Textures.tutorialButtons, 2, 0, 20));
		tutorialButtons.add(new Tile(new Vector3f(0, 0, 0), new Vector2f(14, 14), Textures.tutorialButtons, 1, 3));
		tutorialButtons.add(new Tile(new Vector3f(0, 0, 0), new Vector2f(14, 14), Textures.tutorialButtons, 1, 3));
		// tutorialButtons.add(new Tile(new Vector3f(0, 0, 0), new Vector2f(56, 14), Textures.tutorialButtons, 2, 2,
		// 20));

		// SoundEffects.testEffect.playAsMusic(1.0f, 1.0f, true);
		// if (!loadNewLevel("./res/world/level1.ffw"))
		// {
		// throw new NullPointerException("World Could not be loaded");
		// }
		// SoundEffects.testEffect.playAsMusic(1.0f, 1.0f, true);

		testShader = new Shader("./res/shaders/testVertex.vs", "./res/shaders/testFragment.fs");
		testShader.addUniform("uniformFloat");
		testMesh = new Mesh();
		//com.graphics.Vertex[] v = new com.graphics.Vertex[]{
		//							new com.graphics.Vertex(new Vector3f(-1.0f, 1.0f, 0.0f),new Vector2f(1,0)),
		//							new com.graphics.Vertex(new Vector3f(1.0f, 1.0f, 0.0f),new Vector2f(0,0)),
		//							new com.graphics.Vertex(new Vector3f(1.0f, -1.0f, 0.0f), new Vector2f(0,1)),
		//							new com.graphics.Vertex(new Vector3f(-1.0f, -1.0f, 0.0f),new Vector2f(1,1))
		//							};
		
		
		
		Vector3f ttest = new Vector3f(32,32,0); //new Vector3f(player.getPosition().getX(),player.getPosition().getY(),player.getPosition().getZ());
		ttest.x = (2 / ttest.x) - 1;
		ttest.y = (2 / ttest.y);
		
		com.graphics.Vertex[] v = new com.graphics.Vertex[]{
				new com.graphics.Vertex(new Vector3f(ttest.x, ttest.y + 1, 0.0f),new Vector2f(1,0)),
				new com.graphics.Vertex(new Vector3f(ttest.x + 1, ttest.y + 1, 0.0f),new Vector2f(0,0)),
				new com.graphics.Vertex(new Vector3f(ttest.x + 1, ttest.y, 0.0f), new Vector2f(0,1)),
				new com.graphics.Vertex(new Vector3f(ttest.x, ttest.y, 0.0f),new Vector2f(1,1))
				};
		

		//GL11.glVertex3f(-1.0f, 1.0f, 0.0f);
		//GL11.glVertex3f(1.0f, 1.0f, 0.0f);
		//GL11.glVertex3f(1.0f, -1.0f, 0.0f);
		//GL11.glVertex3f(-1.0f, -1.0f, 0.0f);
		
		int[] indices = new int[]{
				3,2,1,0
		};
		testMesh.addVertices(v, indices);
	}

	/**
	 * Draws objects to the screen. Is executed for every frame
	 */
	public void render()
	{
		if (currentState == GameStates.SPLASH)
		{
			GFX.drawEntireSpriteWithVaryingAlpha(320, 240, 0, 0, Textures.testSplashScreenTexture, tmpCounter);
		} else if (currentState == GameStates.MAIN_MENU)
		{

		} else if (currentState == GameStates.LOADING)
		{
			GFX.drawEntireSpriteWithVaryingAlpha(256, 256, 32, 32, Textures.air, 1);
			System.out.println("Loading Please Wait...");
		} else if (currentState == GameStates.GAME)
		{
			GL11.glTranslatef(-camera.getPosition().x, -camera.getPosition().y, 0.0f); // Moves the camera to the
																						// correct
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
				float textBoxX = camera.getPosition().x + (camera.getSize().x / 2) - 384;// relative to camera, not
																							// world
				float textBoxY = camera.getPosition().y + camera.getSize().y - 156;
				currentDialogue.render(textBoxX, textBoxY);
			}

			for (Tile t : tutorialButtons)
			{
				t.render();
			}

			// location
			// Draw Movement map
			for (Vertex v : currentLevel.getVertices())
			{
				GFX.drawEntireSprite(32, 32, v.getTile().getPosition().x, v.getTile().getPosition().y - 32, Textures.dirt);
				for (Edge e : v.getEdges())
				{
					GFX.drawLine(v.getTile().getPosition().getX() * Game.SCALE, (v.getTile().getPosition().getY() - 32) * Game.SCALE, e.getDestination().getTile().getPosition().getX() * Game.SCALE, (e.getDestination().getTile().getPosition().getY() - 32) * Game.SCALE);
				}
			}

			terminal.render(camera.getPosition().x, camera.getPosition().y + camera.getSize().y);


			testShader.bindShader();
			testShader.setUniformf("uniformFloat", 1f);
			Textures.sean.bind();
			testMesh.draw();
			testShader.unbindShader();
			
			testShader.bindShader();
			testShader.setUniformf("uniformFloat", .5f);
			//Textures.grass.bind();
			GFX.drawEntireSprite(2, 2, 0,0, Textures.sean);
			testShader.unbindShader();
			
			GFX.drawEntireSprite(32, 32, 32, 32, Textures.air);
			
			if (debugMode)
			{
				GFX.drawString(camera.getPosition().x, camera.getPosition().y, "Player position: X:" + player.getPosition().x);
				GFX.drawString(camera.getPosition().x, camera.getPosition().y + 15, "Y: " + player.getPosition().y);
				GFX.drawString(camera.getPosition().x, camera.getPosition().y + 30, "Z: " + player.getPosition().z);
			}
		} else if (currentState == GameStates.OPTIONS)
		{

		} else if (currentState == GameStates.PAUSE)
		{
			// GL11.glPushMatrix();
			// testShader.bindShader();
			int xx = (int) (camera.getPosition().getX()) / (int) Game.SCALE;
			int yy = (int) (camera.getPosition().getY()) / (int) Game.SCALE;
			// GL11.glPushMatrix();
			testShader.bindShader();
			testShader.setUniformf("uniformFloat", 1.0f);
			// Draws a rectangle
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0f, 0.0f, -10.0f);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);// white

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(-1.0f, 1.0f, 0.0f);
			GL11.glVertex3f(1.0f, 1.0f, 0.0f);
			GL11.glVertex3f(1.0f, -1.0f, 0.0f);
			GL11.glVertex3f(-1.0f, -1.0f, 0.0f);
			GL11.glEnd();
			testShader.unbindShader();
			// GL11.glColor4f(1f, 1f, 1f, 1f);
		}

	}

	/**
	 * Updates objects and handles physics. Is executed a certain number of times a second
	 */
	public void update()
	{
		if (currentState == GameStates.SPLASH)
		{
			splashScreenTickCounter++;
			if (splashScreenTickCounter > 60 * 2)
			{
				currentState = GameStates.LOADING;
				tmpCounter = 1;
			}
			tmpCounter -= 0.001f;
			if (tmpCounter <= 0)
			{
				tmpCounter = 0;
			}
		} else if (currentState == GameStates.MAIN_MENU)
		{

		} else if (currentState == GameStates.LOADING)
		{
			if (loadLevelThread == null)
			{
				loadLevelThread = new Thread(new Runnable()
				{

					@Override
					public void run()
					{
						System.out.println("Loading: " + nextLevelName);
						if (!loadNewLevel("./res/world/" + nextLevelName + ".ffw"))
						{
							terminal.printMessage(nextLevelName + " Could not be loaded");
							// throw new NullPointerException("World Could not be loaded");
						} else
						{
							terminal.printMessage(nextLevelName + " has been loaded");
						}
					}

				});
				loadLevelThread.start();
			}
			if (!loadLevelThread.isAlive()) // Note: This is a discouraged way of doing this
			{
				/*
				 * if (new Random().nextBoolean()) { if (new Random().nextBoolean()) { e.setMoveLeft(false);
				 * e.setMoveRight(true); } else { e.setMoveRight(false); e.setMoveLeft(true); } }
				 */
				loadLevelThread = null;
				currentState = GameStates.GAME;
			}
		} else if (currentState == GameStates.GAME)
		{
			tmpCounter -= 0.001f;
			if (tmpCounter <= 0)
			{
				tmpCounter = 0;
			}
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
				getPlayer().update(worldColliders, currentLevel.getVertices());
				getPlayer().checkForCollisionWithProjectiles(enemyProjectiles);
				// Updates tiles
				for (Tile t : tiles)
				{
					t.update();
				}

				// Updates entities
				for (Entity e : entities)
				{
					e.update(worldColliders, currentLevel.getVertices());
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
					e.update(worldColliders, player, currentLevel.getVertices());
					e.checkForCollisionWithProjectiles(playerProjectiles);
					/*
					 * if (new Random().nextBoolean()) { if (new Random().nextBoolean()) { e.setMoveLeft(false);
					 * e.setMoveRight(true); } else { e.setMoveRight(false); e.setMoveLeft(true); } }
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

				// Cleans up the playerProjectiles by removing projectiles that are dead
				int i = 0;
				while (i < playerProjectiles.size())
				{
					while (i < playerProjectiles.size())
					{
						if (playerProjectiles.get(i).isDead())
						{
							particles.addAll(playerProjectiles.get(i).getParticles());
							playerProjectiles.get(i).getParticles().clear();
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

				// Updates the particles in the world
				for (Particle p : particles)
				{
					p.update();
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
				// crazy camera movement
				// Game.SCALE = (float) (2 + Math.sin(counter));
				// counter += 0.01;
				// testProjectile.update(colliders);
				camera.setPositionToPlayer(getPlayer(), Window.width, Window.height); // Sets the camera to have the
																						// player
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
				playerPosition.x = player.getPosition().getX();
				playerPosition.y = player.getPosition().getY();
				camera.update();
				SoundStore.get().poll(0);
			}
			playerPosition.x = player.getPosition().getX();
			playerPosition.y = player.getPosition().getY();
			tutorialButtons.get(0).setPosition(new Vector3f(playerPosition.x - 6, playerPosition.y - 24, 0));
			tutorialButtons.get(1).setPosition(new Vector3f(playerPosition.x + 24, playerPosition.y - 24, 0));
			tutorialButtons.get(2).setPosition(new Vector3f(playerPosition.x + 9, playerPosition.y - 39, 0));
			tutorialButtons.get(3).setPosition(new Vector3f(playerPosition.x + 9, playerPosition.y - 24, 0));
			// tutorialButtons.get(4).setPosition(new Vector3f(playerPosition.x -33, playerPosition.y -24, 0));
			for (Tile t : tutorialButtons)
			{
				t.update();
			}
			camera.update();
			SoundStore.get().poll(0);
			
			//tmp
			//Vector3f ttest = new Vector3f((0 -camera.getPosition().getX() + camera.getSize().getX() / 2),(0 -camera.getPosition().getY() + camera.getSize().getY() / 2),0); //new Vector3f(player.getPosition().getX(),player.getPosition().getY(),player.getPosition().getZ());
			Vector3f ttest = new Vector3f(tiles.get(6).getPosition());
			float ourX = (ttest.x-(camera.getPosition().getX()) + camera.getSize().getX() / 1) - Window.width + 65;//(-ttest.x + tiles.get(6).getSize().getX()) / 2;
			float ourY = (ttest.y-(camera.getPosition().getY()) + camera.getSize().getY() / 1) - Window.height + 192;//(-ttest.y + tiles.get(6).getSize().getY()) / 2;
			
			ttest.x = (((ourX * Game.SCALE) - 1) / (Window.width)) - 1;
			ttest.y = (((-ourY * Game.SCALE)) / (Window.height));
			System.out.println(ttest.x + " " + ttest.y + " " + tiles.get(6).getPosition().getX() + " " + tiles.get(6).getPosition().getY() + " " + ourX + " " + ourY);
			
			com.graphics.Vertex[] v = new com.graphics.Vertex[]{
					new com.graphics.Vertex(new Vector3f(ttest.x, ttest.y + 1, 0.0f),new Vector2f(1,0)),
					new com.graphics.Vertex(new Vector3f(ttest.x + 1, ttest.y + 1, 0.0f),new Vector2f(0,0)),
					new com.graphics.Vertex(new Vector3f(ttest.x + 1, ttest.y, 0.0f), new Vector2f(0,1)),
					new com.graphics.Vertex(new Vector3f(ttest.x, ttest.y, 0.0f),new Vector2f(1,1))
					};
			

			//GL11.glVertex3f(-1.0f, 1.0f, 0.0f);
			//GL11.glVertex3f(1.0f, 1.0f, 0.0f);
			//GL11.glVertex3f(1.0f, -1.0f, 0.0f);
			//GL11.glVertex3f(-1.0f, -1.0f, 0.0f);
			
			int[] indices = new int[]{
					3,2,1,0
			};
			testMesh.addVertices(v, indices);
			
		} else if (currentState == GameStates.OPTIONS)
		{

		} else if (currentState == GameStates.PAUSE)
		{

		}
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

		Level tmp = World.loadWorld(name);
		if (tmp == null)
		{
			return false;
		}

		currentLevel = tmp;

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
			setPlayer(new Player(currentLevel.getPlayerSpawnLocation(), Textures.playerFront, Textures.playerOutline, 0, 0, new Vector2f(32, 32), handler));
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

	/**
	 * allows for changing scale of the game
	 * 
	 * @param scale
	 *            the multiplier that everything is scaled by
	 */
	public static void setScale(float scale)
	{
		Game.SCALE = scale;
	}

	public static Camera getCamera()
	{
		return camera;
	}

	public void setNextLevelName(String string)
	{
		this.nextLevelName = string;
	}

	public void setGameState(GameStates newState)
	{
		this.currentState = newState;
	}
}
