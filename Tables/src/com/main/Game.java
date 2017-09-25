package com.main;

import java.util.ArrayList;
import java.util.Random;

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
import com.graphics.world.menu.ButtonState;
import com.graphics.world.menu.MenuButton;
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

	private Window					currentWindow;

	private Player					player;

	public static Vector2f			playerPosition;

	private ArrayList<RectangleBox>	worldColliders			= new ArrayList<RectangleBox>();
	private ArrayList<Tile>			tiles					= new ArrayList<Tile>();
	private ArrayList<Projectile>	playerProjectiles		= new ArrayList<Projectile>();
	private ArrayList<Projectile>	enemyProjectiles		= new ArrayList<Projectile>();
	private ArrayList<Particle>		particles				= new ArrayList<Particle>();
	private ArrayList<Particle>		backgroundParticles		= new ArrayList<Particle>();
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

	// Menu things
	private Tile					titleText;												// the main title as it appears on the menu
	private ArrayList<MenuButton>	menuButtons				= new ArrayList<MenuButton>();	// the few buttons on the main menu
	private ArrayList<Tile>			optionList				= new ArrayList<Tile>();		// the non-clickable items in the options menu
	private ArrayList<MenuButton>	optionButtons			= new ArrayList<MenuButton>();	// the clickable options

	private ArrayList<Tile>			tutorialButtons			= new ArrayList<Tile>();

	private DialogBox				currentDialogue;

	private float					tmpCounter				= 1f;

	private GameStates				currentState			= GameStates.SPLASH;
	private Thread					loadLevelThread			= null;

	private int						splashScreenTickCounter	= 0;

	private String					nextLevelName			= "level1";

	private boolean					canTogglePause			= true;
	// private Projectile testProjectile;

	/**
	 * Creates the game world
	 */
	public Game(Window currentWindow)
	{
		this.currentWindow = currentWindow;
		new Textures(); // Loads textures
		new SoundEffects(); // Loads Sound effects
		GFX.initString();

		setPlayer(new Player(new Vector3f(32, 32, 0), Textures.playerFront, Textures.playerOutline, 0, 0, new Vector2f(32, 32), handler));
		getPlayer().setAnimateFrameTime(3.0f);

		playerPosition = new Vector2f(getPlayer().getPosition().getX(), getPlayer().getPosition().getY());

		camera = new Camera(new Vector2f(getPlayer().getPosition().x, getPlayer().getPosition().y), new Vector2f(Window.width, Window.height));
		handler = new InputHandler(camera);
		camera.setPositionToPlayer(getPlayer(), Window.width, Window.height);
		terminal = new Terminal(handler, getPlayer(), camera, this);
		sky = new Tile(new Vector3f(-256, -112, 100), new Vector2f(1024, 1024), Textures.sky);
		testTile2 = new Tile(new Vector3f(-256, -112, 10), new Vector2f(1024, 1024), Textures.desert2);
		testTile1 = new Tile(new Vector3f(-256, -112, 5), new Vector2f(1024, 1024), Textures.desert1);
		testTile0 = new Tile(new Vector3f(-256, -112, 2), new Vector2f(1024, 1024), Textures.desert0);

		/**
		 * create all menu interactables and graphics
		 */
		titleText = new Tile(new Vector3f(0, 0, 0), new Vector2f(304, 125), Textures.titletext);
		float titleTextCenter = titleText.getPosition().x + titleText.getSize().x / 2;
		Tile startButton = new Tile(new Vector3f(titleTextCenter - (92 / 2), 80, 0), new Vector2f(92, 18), Textures.menubuttons, 3, 0);
		Tile optionButton = new Tile(new Vector3f(titleTextCenter - (128 / 2), 100, 0), new Vector2f(128, 18), Textures.menubuttons, 3, 1);
		Tile exitButton = new Tile(new Vector3f(titleTextCenter - (74 / 2), 120, 0), new Vector2f(74, 18), Textures.menubuttons, 3, 2);
		Tile backButton = new Tile(new Vector3f(titleTextCenter - (16 / 2), 80, 0), new Vector2f(20, 18), Textures.menubuttons, 3, 3);
		menuButtons.add(new MenuButton(startButton));// the start button
		menuButtons.add(new MenuButton(optionButton));// the option button
		menuButtons.add(new MenuButton(exitButton));// the exit button
		menuButtons.add(new MenuButton(backButton));// the arrow that takes you back

		// option menu stuff
		Tile scaleImg = new Tile(new Vector3f(), new Vector2f(98, 18), Textures.options, 1, 0);
		Tile vsyncImg = new Tile(new Vector3f(), new Vector2f(106, 18), Textures.options, 1, 2);
		Tile resolutionImg = new Tile(new Vector3f(), new Vector2f(188, 18), Textures.options, 1, 3);
		Tile fullscreenImg = new Tile(new Vector3f(), new Vector2f(188, 18), Textures.options, 1, 4);
		Tile scaleNumberImg = new Tile(new Vector3f(), new Vector2f(20, 18), Textures.options, 4, 1);
		scaleNumberImg.setFrame(Math.round(Game.SCALE) - 1);
		optionList.add(scaleImg);
		optionList.add(resolutionImg);
		optionList.add(fullscreenImg);
		optionList.add(vsyncImg);
		optionList.add(scaleNumberImg);

		Tile on = new Tile(new Vector3f(), new Vector2f(38, 18), Textures.menubuttons, 3, 4);
		Tile off = new Tile(new Vector3f(), new Vector2f(56, 18), Textures.menubuttons, 3, 5);
		Tile leftArrow = new Tile(new Vector3f(), new Vector2f(14, 18), Textures.menubuttons, 3, 6);
		Tile rightArrow = new Tile(new Vector3f(), new Vector2f(14, 18), Textures.menubuttons, 3, 7);
		// in order, it's the left and right scale buttons
		optionButtons.add(new MenuButton(leftArrow));
		optionButtons.add(new MenuButton(rightArrow));
		// then the resolution choosing arrows
		optionButtons.add(new MenuButton(leftArrow));
		optionButtons.add(new MenuButton(rightArrow));
		// next it's the fullscreen on/off button
		optionButtons.add(new MenuButton(off, on));
		// and then the vsync on/off button
		optionButtons.add(new MenuButton(off, on));

		// any additional buttons for sound and stuff go after this
		/**
		 * done creating
		 */

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
	}

	/**
	 * Draws objects to the screen. Is executed for every frame
	 */
	public void render()
	{
		GL11.glTranslatef(-camera.getPosition().x, -camera.getPosition().y, 0.0f); // Moves the camera to the
		// correct
		if (currentState == GameStates.SPLASH)
		{
			// GFX.drawEntireSpriteWithVaryingAlpha(2048, 2048, 0, 0, Textures.splash, tmpCounter);
			// GFX.drawEntireSpriteUnscaled(2048, 2048, 0, 0, Textures.splash);
			GFX.drawSpriteFromSpriteSheet(Window.width, Window.height, 0, 0, Textures.splash, new Vector2f(0, 0), new Vector2f(1920f / 2048f, 1080f / 2048f), 1, 1f);
		} else if (currentState == GameStates.MAIN_MENU)
		{
			titleText.render();
			for (int i = 0; i < 3; i++)
			{
				menuButtons.get(i).render();
			}
			for (Particle p : backgroundParticles)
			{
				p.render();
			}
			for (Particle p : particles)
			{
				p.render();
			}
		} else if (currentState == GameStates.LOADING)
		{
			GFX.drawEntireSpriteWithVaryingAlpha(256, 256, 32, 32, Textures.air, 1, -1);
			System.out.println("Loading Please Wait...");
		} else if (currentState == GameStates.GAME)
		{
			// location
			// Draw Movement map
			for (Vertex v : currentLevel.getVertices())
			{
				GFX.drawEntireSprite(32, 32, v.getTile().getPosition().x, v.getTile().getPosition().y - 32, Textures.dirt, -1);
				for (Edge e : v.getEdges())
				{
					GFX.drawLine(v.getTile().getPosition().getX() * Game.SCALE, (v.getTile().getPosition().getY() - 32) * Game.SCALE, e.getDestination().getTile().getPosition().getX() * Game.SCALE, (e.getDestination().getTile().getPosition().getY() - 32) * Game.SCALE);
				}
			}
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
				for (Edge e : v.getEdges())
				{
					GFX.drawLine(v.getTile().getPosition().getX() * Game.SCALE, (v.getTile().getPosition().getY() - 32) * Game.SCALE, e.getDestination().getTile().getPosition().getX() * Game.SCALE, (e.getDestination().getTile().getPosition().getY() - 32) * Game.SCALE);
				}
			}

			terminal.render(camera.getPosition().x, camera.getPosition().y + camera.getSize().y);
			
			if (debugMode)
			{
				GFX.drawString(camera.getPosition().x, camera.getPosition().y, "Player handler.getMousePosition(): X:" + player.getPosition().x);
				GFX.drawString(camera.getPosition().x, camera.getPosition().y + 15, "Y: " + player.getPosition().y);
				GFX.drawString(camera.getPosition().x, camera.getPosition().y + 30, "Z: " + player.getPosition().z);
			}
		} else if (currentState == GameStates.OPTIONS)
		{
			for (MenuButton butt : optionButtons)
			{
				butt.render();
			}
			for (Tile option : optionList)
			{
				option.render();
			}
			menuButtons.get(3).render();
			for (Particle p : backgroundParticles)
			{
				p.render();
			}
			for (Particle p : particles)
			{
				p.render();
			}
		} else if (currentState == GameStates.PAUSE)
			menuButtons.get(3).render();
		{

		}

	}

	/**
	 * Updates objects and handles physics. Is executed a certain number of times a second
	 */
	public void update()
	{
		if (currentState == GameStates.SPLASH)
		{
			camera.setPosition(new Vector2f(0, 0));
			splashScreenTickCounter++;
			if (splashScreenTickCounter > 60 * 2)
			{
				currentState = GameStates.MAIN_MENU;
				// does this so we don't see a single frame of stuff in the wrong place
				titleText.setPosition(new Vector3f(camera.getAbsoluteCenter().x - titleText.getSize().x / 2, camera.getAbsoluteCenter().y - titleText.getSize().y - ((80 / Game.SCALE) - 20), 0));
				for (int i = 0; i < 3; i++)
				{
					menuButtons.get(i).setPosition(new Vector2f(camera.getAbsoluteCenter().x - menuButtons.get(i).getSize().x / 2, camera.getAbsoluteCenter().y + (i * 25) + (Game.SCALE * 2)));
				}
				tmpCounter = 1;
			}
			tmpCounter -= 0.001f;
			if (tmpCounter <= 0)
			{
				tmpCounter = 0;
			}
		} else if (currentState == GameStates.MAIN_MENU)
		{
			// System.out.println("camera center at " + camera.getAbsoluteCenter().x + " " + camera.getAbsoluteCenter().y);
			// camera.setPosition(new Vector2f(0, 0));
			titleText.setPosition(new Vector3f(camera.getAbsoluteCenter().x - titleText.getSize().x / 2, camera.getAbsoluteCenter().y - titleText.getSize().y - ((80 / Game.SCALE) - 20), 0));
			backgroundParticles
					.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 21, 3, new Random().nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(0, -0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, 3));
			backgroundParticles
					.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 21, 3, new Random().nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(0, -0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, 3));

			particles.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 10, 4, new Random().nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(0, -.5f), new Vector2f(5f, 5f), new Vector2f(.5f, .5f), 4, 2));// big
																																																																											// fire
			particles.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 8, 5, false, new Vector2f(16, 16), 0, new Vector2f(0, -.8f), new Vector2f(5f, 5f), new Vector2f(1f, 1f), 4, 3));// small fire
			particles.add(
					new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), 0, new Vector2f(0, -0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 4, new Random().nextInt(4)));

			for (Particle p : backgroundParticles)
			{
				p.update();
			}
			for (Particle p : particles)
			{
				p.update();
			}
			for (int i = 0; i < 3; i++)
			{
				menuButtons.get(i).setPosition(new Vector2f(camera.getAbsoluteCenter().x - menuButtons.get(i).getSize().x / 2, camera.getAbsoluteCenter().y + (i * 25) + (Game.SCALE * 2)));
				menuButtons.get(i).update(handler);
				if (menuButtons.get(i).getCurrentState() == ButtonState.ACTIVE)
				{
					switch (i)
					{
						case 0:
							currentState = GameStates.LOADING;
							break;
						case 1:
							currentState = GameStates.OPTIONS;
							menuButtons.get(3).setPosition(new Vector2f((camera.getAbsoluteCenter().x + camera.getSize().x / (2 * Game.SCALE)) - menuButtons.get(3).getSize().x - (64 / Game.SCALE),
									(camera.getAbsoluteCenter().y + camera.getSize().y / (2 * Game.SCALE)) - menuButtons.get(3).getSize().y - (64 / Game.SCALE) + 4));
							for (int j = 0; j < optionList.size(); j++)
							{
								optionList.get(j).setPosition(new Vector3f(camera.getAbsoluteCenter().x - camera.getSize().x / (2 * Game.SCALE) + (512 / (Game.SCALE * Game.SCALE)) - 14, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (j * 25) + (64 / (Game.SCALE * 2)), 0));
							}
							optionButtons.get(0)
									.setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (0 * 25) + (64 / (Game.SCALE * 2))));
							optionButtons.get(1)
									.setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) + (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (0 * 25) + (64 / (Game.SCALE * 2))));
							float midpoint = (optionButtons.get(0).getPosition().x + optionButtons.get(1).getPosition().x) / 2;
							optionList.get(4).setPosition(new Vector3f(midpoint, optionButtons.get(0).getPosition().y, 0));
							optionButtons.get(2)
									.setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (1 * 25) + (64 / (Game.SCALE * 2))));
							optionButtons.get(3)
									.setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) + (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (1 * 25) + (64 / (Game.SCALE * 2))));
							optionButtons.get(4)
									.setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (2 * 25) + (64 / (Game.SCALE * 2))));
							optionButtons.get(5)
									.setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (3 * 25) + (64 / (Game.SCALE * 2))));

							break;
						case 2:
							cleanUPGame();
							System.exit(0);
						default:
							break;
					}
				}
			}
			// Do stuff for parallax effect based on mouse movement around the title screen
		} else if (currentState == GameStates.LOADING)
		{
			camera.setPosition(new Vector2f(0, 0));
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
				 * if (new Random().nextBoolean()) { if (new Random().nextBoolean()) { e.setMoveLeft(false); e.setMoveRight(true); } else { e.setMoveRight(false); e.setMoveLeft(true); } }
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
				// handles pausing (when terminal is closed
				if (handler.escape() && canTogglePause)
				{
					canTogglePause = false;
					currentState = GameStates.PAUSE;
					menuButtons.get(3).setPosition(
							new Vector2f((camera.getAbsoluteCenter().x + camera.getSize().x / (2 * Game.SCALE)) - menuButtons.get(3).getSize().x - (64 / Game.SCALE), (camera.getAbsoluteCenter().y + camera.getSize().y / (2 * Game.SCALE)) - menuButtons.get(3).getSize().y - (64 / Game.SCALE) + 4));
				} else if (!handler.escape())
				{
					canTogglePause = true;
				}

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
			}
			playerPosition.x = player.getPosition().getX();
			playerPosition.y = player.getPosition().getY();
			camera.setPositionToPlayer(player, Window.width, Window.height);
			camera.update();
			SoundStore.get().poll(0);
			
			if(handler.isMouseLeftClicking())
			{
				currentWindow.destroy();
				currentWindow.width = 800;
				currentWindow.height = 600;
				currentWindow = new Window(800,600,true);
				currentWindow.initOpenGL();
			}
		} else if (currentState == GameStates.OPTIONS)
		{
			setAllOptionPositions();// a method to organize buttons on the screen. otherwise this gets really cluttered
			backgroundParticles
					.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 21, 3, new Random().nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(0, -0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, 3));
			backgroundParticles
					.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 21, 3, new Random().nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(0, -0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, 3));

			particles.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 10, 4, new Random().nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(0, -.5f), new Vector2f(5f, 5f), new Vector2f(.5f, .5f), 4, 2));// big
																																																																											// fire
			particles.add(new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 8, 5, false, new Vector2f(16, 16), 0, new Vector2f(0, -.8f), new Vector2f(5f, 5f), new Vector2f(1f, 1f), 4, 3));// small fire
			particles.add(
					new Particle(new Vector2f(handler.getMousePosition().x - 8, handler.getMousePosition().y - 8), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), 0, new Vector2f(0, -0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 4, new Random().nextInt(4)));

			for (Particle p : backgroundParticles)
			{
				p.update();
			}
			for (Particle p : particles)
			{
				p.update();
			}
			menuButtons.get(3).update(handler);
			for (MenuButton butt : optionButtons)
			{
				butt.update(handler);
			}
			if (optionButtons.get(0).getCurrentState() == ButtonState.ACTIVE)
			{
				if (Game.SCALE > 1)
				{
					Game.SCALE -= 1;
				} else
				{
					Game.SCALE = 4;
				}
				optionList.get(4).setFrame(Math.round(Game.SCALE) - 1);
				setAllOptionPositions();
			}
			if (optionButtons.get(1).getCurrentState() == ButtonState.ACTIVE)
			{
				if (Game.SCALE < 4)
				{
					Game.SCALE += 1;
				} else
				{
					Game.SCALE = 1;
				}
				optionList.get(4).setFrame(Math.round(Game.SCALE) - 1);
				setAllOptionPositions();
			}

			if (handler.escape() || menuButtons.get(3).getCurrentState() == ButtonState.ACTIVE)
			{
				currentState = GameStates.MAIN_MENU;
				titleText.setPosition(new Vector3f(camera.getAbsoluteCenter().x - titleText.getSize().x / 2, camera.getAbsoluteCenter().y - titleText.getSize().y - ((80 / Game.SCALE) - 20), 0));
				for (int i = 0; i < 3; i++)
				{
					menuButtons.get(i).setPosition(new Vector2f(camera.getAbsoluteCenter().x - menuButtons.get(i).getSize().x / 2, camera.getAbsoluteCenter().y + (i * 25) + (Game.SCALE * 2)));
				}
			}
		} else if (currentState == GameStates.PAUSE)
		{
			camera.setPosition(new Vector2f(0, 0));
			menuButtons.get(3).setPosition(
					new Vector2f((camera.getAbsoluteCenter().x + camera.getSize().x / (2 * Game.SCALE)) - menuButtons.get(3).getSize().x - (64 / Game.SCALE), (camera.getAbsoluteCenter().y + camera.getSize().y / (2 * Game.SCALE)) - menuButtons.get(3).getSize().y - (64 / Game.SCALE) + 4));
			System.out.println(handler.getMousePosition() + " " + menuButtons.get(3).getPosition());
			menuButtons.get(3).update(handler);
			if (handler.escape() && canTogglePause || menuButtons.get(3).getCurrentState() == ButtonState.ACTIVE)
			{
				canTogglePause = false;
				currentState = GameStates.GAME;
				camera.setPositionToPlayer(getPlayer(), Window.width, Window.height);
			} else if (!handler.escape())
			{
				canTogglePause = true;
			}
		}
	}

	private void setAllOptionPositions()
	{
		menuButtons.get(3)
				.setPosition(new Vector2f((camera.getAbsoluteCenter().x + camera.getSize().x / (2 * Game.SCALE)) - menuButtons.get(3).getSize().x - (64 / Game.SCALE), (camera.getAbsoluteCenter().y + camera.getSize().y / (2 * Game.SCALE)) - menuButtons.get(3).getSize().y - (64 / Game.SCALE) + 4));

		for (int i = 0; i < 4; i++)
		{
			optionList.get(i).setPosition(new Vector3f(camera.getAbsoluteCenter().x - camera.getSize().x / (2 * Game.SCALE) + (512 / (Game.SCALE * Game.SCALE)) - 14, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (i * 25) + (64 / (Game.SCALE * 2)), 0));
		}
		optionButtons.get(0).setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (0 * 25) + (64 / (Game.SCALE * 2))));
		optionButtons.get(1).setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) + (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (0 * 25) + (64 / (Game.SCALE * 2))));
		float midpoint = (optionButtons.get(0).getPosition().x + optionButtons.get(1).getPosition().x) / 2;
		optionList.get(4).setPosition(new Vector3f(midpoint, optionButtons.get(0).getPosition().y, 0));
		optionButtons.get(2).setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (1 * 25) + (64 / (Game.SCALE * 2))));
		optionButtons.get(3).setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) + (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (1 * 25) + (64 / (Game.SCALE * 2))));
		optionButtons.get(4).setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (2 * 25) + (64 / (Game.SCALE * 2))));
		optionButtons.get(5).setPosition(new Vector2f(camera.getAbsoluteCenter().x + camera.getSize().x / (3 * Game.SCALE) - (512 / (2 * Game.SCALE)) - (192 / Game.SCALE) + 36, camera.getAbsoluteCenter().y - camera.getSize().y / (3 * Game.SCALE) + (3 * 25) + (64 / (Game.SCALE * 2))));

	}

	/**
	 * Cleans up memory if needed
	 */
	public void cleanUPGame()
	{
		AL.destroy();
		currentWindow.destroy();
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
