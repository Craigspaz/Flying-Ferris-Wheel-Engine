package com.levelbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.Loader;
import com.graphics.Textures;
import com.graphics.world.Camera;
import com.graphics.world.Entity;
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.graphics.world.World;
import com.graphics.world.enemys.Enemies;
import com.graphics.world.enemys.Enemy;
import com.graphics.world.util.Edge;
import com.graphics.world.util.EnemyMovement;
import com.graphics.world.util.MovementMethod;
import com.graphics.world.util.Vertex;
import com.input.InputHandler;
import com.main.Game;
import com.main.Window;

/**
 * Lets the user build a level
 * 
 * @author Craig Ferris
 *
 */
public class LevelBuilderGame
{
	private ArrayList<Tile>		tiles					= new ArrayList<Tile>();
	private ArrayList<Enemy>	enemies					= new ArrayList<Enemy>();

	private Player				player;

	private InputHandler		handler;
	private Texture				tileToPlace;
	private boolean				isMouseReady			= true;
	private boolean				isDeleting				= false;

	boolean						clickedATile			= false;										// as soon as a
																										// tile is
																										// clicked, the
																										// mouse should
																										// enter "delete
																										// mode" until
																										// the mouse is
																										// released

	private Texture				down					= Loader.loadTexture("borders/down");
	private Texture				downleft				= Loader.loadTexture("borders/downleft");
	private Texture				downleftright			= Loader.loadTexture("borders/downleftright");
	private Texture				downright				= Loader.loadTexture("borders/downright");
	private Texture				left					= Loader.loadTexture("borders/left");
	private Texture				leftright				= Loader.loadTexture("borders/leftright");
	private Texture				leftupright				= Loader.loadTexture("borders/leftupright");
	private Texture				right					= Loader.loadTexture("borders/right");
	private Texture				rightupdown				= Loader.loadTexture("borders/rightupdown");
	private Texture				topdown					= Loader.loadTexture("borders/topdown");
	private Texture				up						= Loader.loadTexture("borders/up");
	private Texture				updownleftright			= Loader.loadTexture("borders/updownleftright");
	private Texture				updownright				= Loader.loadTexture("borders/updownright");
	private Texture				upleft					= Loader.loadTexture("borders/upleft");
	private Texture				upright					= Loader.loadTexture("borders/upright");
	private Texture				saveLevel				= Loader.loadTexture("saveLevel");
	private Texture				door					= Loader.loadTexture("door");

	private int					y_offset, x_offset;
	private boolean				readyAfterClickingSave	= true;

	/**
	 * Creates a new level builder
	 */
	public LevelBuilderGame()
	{
		new Textures();
		Game.SCALE = 1;
		handler = new InputHandler(new Camera(null, null));
	}

	/**
	 * Updates the level builder
	 */
	public void update()
	{
		if (handler.isMouseLeftClicking() == false)
		{
			isMouseReady = true;
		}
		if (handler.isMouseLeftClicking())
		{
			if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 0 && handler.getMousePosition().y < 32)
			{
				tileToPlace = Textures.testTile;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 32 && handler.getMousePosition().y < 64)
			{
				tileToPlace = Textures.grass;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 64 && handler.getMousePosition().y < 96)
			{
				tileToPlace = Textures.grassTop;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 96 && handler.getMousePosition().y < 128)
			{
				tileToPlace = Textures.dirt2;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 128 && handler.getMousePosition().y < 160)
			{
				tileToPlace = Textures.dirt;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 160 && handler.getMousePosition().y < 192)
			{
				tileToPlace = Textures.air;
			}

			else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 192 && handler.getMousePosition().y < 224)
			{
				tileToPlace = down;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 224 && handler.getMousePosition().y < 256)
			{
				tileToPlace = downleft;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 256 && handler.getMousePosition().y < 288)
			{
				tileToPlace = downleftright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 288 && handler.getMousePosition().y < 320)
			{
				tileToPlace = downright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 320 && handler.getMousePosition().y < 352)
			{
				tileToPlace = left;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 352 && handler.getMousePosition().y < 384)
			{
				tileToPlace = leftright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 384 && handler.getMousePosition().y < 416)
			{
				tileToPlace = leftupright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 416 && handler.getMousePosition().y < 448)
			{
				tileToPlace = right;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 448 && handler.getMousePosition().y < 480)
			{
				tileToPlace = rightupdown;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 480 && handler.getMousePosition().y < 512)
			{
				tileToPlace = topdown;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 512 && handler.getMousePosition().y < 544)
			{
				tileToPlace = up;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 544 && handler.getMousePosition().y < 576)
			{
				tileToPlace = updownleftright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 576 && handler.getMousePosition().y < 608)
			{
				tileToPlace = updownright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 608 && handler.getMousePosition().y < 640)
			{
				tileToPlace = upleft;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 640 && handler.getMousePosition().y < 672)
			{
				tileToPlace = upright;
			} else if (handler.getMousePosition().x > Window.width - 64 && handler.getMousePosition().x <= Window.width - 32 && handler.getMousePosition().y >= 0 && handler.getMousePosition().y < 32)
			{
				tileToPlace = Textures.playerFront;
			} else if (handler.getMousePosition().x > Window.width - 64 && handler.getMousePosition().x <= Window.width - 32 && handler.getMousePosition().y >= 32 && handler.getMousePosition().y < 64)
			{
				tileToPlace = Textures.crabman;
			} else if (handler.getMousePosition().x > Window.width - 64 && handler.getMousePosition().x <= Window.width - 32 && handler.getMousePosition().y >= 64 && handler.getMousePosition().y < 96)
			{
				tileToPlace = door;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 672 && handler.getMousePosition().y < 704 && readyAfterClickingSave)
			{
				saveLevel();
				readyAfterClickingSave = false;
			} else
			{
				if (tileToPlace != null)
				{
					if (tileToPlace == Textures.playerFront)
					{
						if (player == null)
						{
							player = new Player(new Vector3f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, 0), tileToPlace, tileToPlace, 0, 0, new Vector2f(16, 16), handler);
						} else
						{
							for (Tile t : tiles)
							{
								if (t.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && t.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
								{
									tiles.remove(t);
									break;
								}
							}
							for (Enemy e : enemies)
							{
								if (e.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && e.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
								{
									enemies.remove(e);
									break;
								}
							}
							if (handler.getMousePosition().x - handler.getMousePosition().x % 16 == player.getPosition().x && handler.getMousePosition().y - handler.getMousePosition().y % 16 == player.getPosition().y)
							{
								player = null;
							}
						}
					} else if (tileToPlace == Textures.crabman)
					{
						boolean removedItem = false;
						for (Tile t : tiles)
						{
							if (t.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && t.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								tiles.remove(t);
								removedItem = true;
								break;
							}
						}
						for (Enemy e : enemies)
						{
							if (e.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && e.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								enemies.remove(e);
								removedItem = true;
								break;
							}
						}
						if (player != null && handler.getMousePosition().x - handler.getMousePosition().x % 16 == player.getPosition().x && handler.getMousePosition().y - handler.getMousePosition().y % 16 == player.getPosition().y)
						{
							player = null;
							removedItem = true;
						}
						if (!removedItem)
						{
							enemies.add(new Enemy(new Vector3f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, 0), Textures.crabman, Textures.crabman, 0, 0, new Vector2f(16, 16), new Vector2f(64, 64)));
						}

					} else
					{
						Vector2f pos = new Vector2f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16);
						boolean createTile = true;
						clickedATile = false;
						for (Tile t : tiles)
						{
							if (t.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && t.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								if (isMouseReady)
								{
									System.out.println("clicked a tile");
									clickedATile = true;
									isDeleting = true;
									isMouseReady = false;
								}
								if (isDeleting)
									tiles.remove(t);
								createTile = false;
								break;
							}
						}
						if (!clickedATile && isMouseReady)
						{
							isMouseReady = false;
							isDeleting = false;
							System.out.println("clicked an empty space");
						}

						if (player != null && handler.getMousePosition().x - handler.getMousePosition().x % 16 == player.getPosition().x && handler.getMousePosition().y - handler.getMousePosition().y % 16 == player.getPosition().y)
						{
							player = null;
							createTile = false;
						}
						for (Enemy e : enemies)
						{
							if (e.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && e.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								enemies.remove(e);
								createTile = false;
								break;
							}
						}
						if (createTile && !isDeleting)
						{
							tiles.add(new Tile(new Vector3f(pos.x, pos.y, 0), new Vector2f(16, 16), tileToPlace));
						}
					}
				}
			}
		}
	}

	/**
	 * Renders the level builder
	 */
	public void render()
	{
		for (Tile t : tiles)
		{
			t.render();
		}
		if (player != null)
		{
			player.render();
		}

		GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.highlight, -1);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 0, Textures.testTile, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 32, Textures.grass, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 64, Textures.grassTop, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 96, Textures.dirt2, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 128, Textures.dirt, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 160, Textures.air, -1);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 192, down, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 224, downleft, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 256, downleftright, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 288, downright, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 320, left, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 352, leftright, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 384, leftupright, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 416, right, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 448, rightupdown, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 480, topdown, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 512, up, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 544, updownleftright, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 576, updownright, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 608, upleft, -1);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 640, upright, -1);

		GFX.drawSpriteFromSpriteSheet(32, 32, Window.width - 64, 0, Textures.playerFront, new Vector2f(0, 0), new Vector2f((float) 32 / 512, (float) 32 / 256), -1, 1f);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 672, saveLevel, -1);

		GFX.drawSpriteFromSpriteSheet(32, 32, Window.width - 64, 32, Textures.crabman, new Vector2f(0, 0), new Vector2f((float) 64 / 512, (float) 64 / 128), -1, 1f);

		GFX.drawEntireSprite(32, 32, Window.width - 64, 64, door, -1);

		if (tileToPlace != null)
		{
			if (tileToPlace == Textures.playerFront)
			{
				GFX.drawSpriteFromSpriteSheet(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.playerFront, new Vector2f(0, 0), new Vector2f((float) 32 / 512, (float) 32 / 256), -1, 1f);
			} else if (tileToPlace == Textures.crabman)
			{
				GFX.drawSpriteFromSpriteSheet(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.crabman, new Vector2f(0, 0), new Vector2f((float) 64 / 512, (float) 64 / 128), -1, 1f);
			} else
			{
				GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, tileToPlace, -1);
			}
		}
		for (Enemy e : enemies)
		{
			e.render();
		}
	}

	/**
	 * Generates colliders
	 * 
	 * @return Returns an arraylist of colliders
	 */
	private ArrayList<RectangleBox> generateColliders()
	{

		System.out.println("Generating Colliders...");
		ArrayList<RectangleBox> colliders = new ArrayList<RectangleBox>();
		for (Tile t : tiles)
		{
			Texture tex = t.getTexture();
			if (tex == down || tex == downleft || tex == downleftright || tex == downright || tex == left || tex == leftright || tex == leftupright || tex == right || tex == rightupdown || tex == topdown || tex == up || tex == updownleftright || tex == updownright || tex == upleft || tex == upright
					|| tex == door)
			{
				continue;
			}
			colliders.add(new RectangleBox(new Vector3f(t.getPosition().getX(), t.getPosition().getY(), t.getPosition().getZ()), new Vector2f(t.getSize().getX(), t.getSize().getY())));
		}

		class ColliderComparatorX implements Comparator<RectangleBox>
		{

			@Override
			public int compare(RectangleBox o1, RectangleBox o2)
			{
				int val = (int) (o1.getPosition().y - o2.getPosition().y);
				if (val == 0)
				{
					val = (int) (o1.getPosition().x - o2.getPosition().x);
				}
				return val;
			}

		}
		class ColliderComparatorY implements Comparator<RectangleBox>
		{

			@Override
			public int compare(RectangleBox o1, RectangleBox o2)
			{
				return (int) (o2.getPosition().y - o1.getPosition().y);
			}

		}

		colliders.sort(new ColliderComparatorY());
		colliders.sort(new ColliderComparatorX());

		for (int i = 1; i < colliders.size(); i++)
		{
			RectangleBox currentBox = colliders.get(i);
			RectangleBox previousBox = colliders.get(i - 1);

			if (currentBox.getPosition().y == previousBox.getPosition().y)
			{
				if (currentBox.getPosition().x == previousBox.getPosition().x + previousBox.getSize().x)
				{
					RectangleBox temp = colliders.get(i - 1);
					temp.setSize(new Vector2f(previousBox.getSize().x + currentBox.getSize().x, previousBox.getSize().y));
					colliders.set(i - 1, temp);
					colliders.remove(i);
					i--;
					System.out.println("merged 2 colliders, now starts at " + colliders.get(i).getPosition().x + " and ends at " + (colliders.get(i).getPosition().x + colliders.get(i).getSize().x));
				}
			}
		}

		for (int i = 0; i < colliders.size(); i++)
		{
			colliders.get(i).setSize(new Vector2f(colliders.get(i).getSize().x * 4, colliders.get(i).getSize().y * 4));
		}

		return colliders;
	}

	/**
	 * Saves the level
	 */
	private void saveLevel()
	{
		ArrayList<RectangleBox> colliders = generateColliders();
		int blockSize = 64;

		System.out.println("Saving...");
		try
		{
			File output = new File("./res/generated/gen_" + System.currentTimeMillis() + "_level.ffw");
			PrintWriter writer = new PrintWriter(output);
			writer.println("<LEVEL name=\"" + output.getName() + "\">");
			if (player != null)
			{
				writer.println("<PLAYER x=\"" + (int) player.getPosition().x * 4 + "\" y=\"" + (int) player.getPosition().y * 4 + "\" z=\"" + (int) player.getPosition().z + "\" width=\"" + (int) player.getSpriteSize().x + "\" height=\"" + (int) player.getSpriteSize().y
						+ "\" tex=\"playerFront\" texOut=\"playerOutline\"/>");
			}
			writer.println("\t<TILES sizex=\"" + blockSize + "\" sizey=\"" + blockSize + "\">");
			for (int i = 0; i < tiles.size(); i++)
			{
				if (tiles.get(i).getTexture() == door)
				{
					writer.println("\t\t<DOOR x=\"" + (int) tiles.get(i).getPosition().x * 4 + "\" y=\"" + (int) tiles.get(i).getPosition().y * 4 + "\"/>");
					tiles.remove(i);
					i--;
				}
			}
			for (Tile t : tiles)
			{
				boolean[] neighbors = new boolean[8];// starts from north
				boolean nwestNeighbor = false;
				boolean northNeighbor = false;
				boolean neastNeighbor = false;
				boolean eastNeighbor = false;
				boolean seastNeighbor = false;
				boolean southNeighbor = false;
				boolean swestNeighbor = false;
				boolean westNeighbor = false;

				for (Tile q : tiles)// reads all tiles for possible neighbors
				{
					if (q.getPosition().x + q.getSize().x == t.getPosition().x)// is west
					{
						if (q.getPosition().y == t.getPosition().y)// is directly west
						{
							westNeighbor = true;
							neighbors[7] = true;
						} else if (q.getPosition().y + q.getSize().y == t.getPosition().y)// is northwest
						{
							nwestNeighbor = true;
							neighbors[0] = true;
						} else if (q.getPosition().y == t.getPosition().y + t.getSize().y)// must be southwest
						{
							swestNeighbor = true;
							neighbors[6] = true;
						}
					} else if (q.getPosition().x == t.getPosition().x + t.getSize().x)// is east
					{
						if (q.getPosition().y == t.getPosition().y)// is directly east
						{
							eastNeighbor = true;
							neighbors[3] = true;
						} else if (q.getPosition().y + q.getSize().y == t.getPosition().y)// is northeast
						{
							neastNeighbor = true;
							neighbors[2] = true;
						} else if (q.getPosition().y == t.getPosition().y + t.getSize().y)// must be southeast
						{
							seastNeighbor = true;
							neighbors[4] = true;
						}
					} else if (q.getPosition().x == t.getPosition().x)// must be above or below this one
					{
						if (q.getPosition().y + q.getSize().y == t.getPosition().y)// is directly north
						{
							northNeighbor = true;
							neighbors[1] = true;
						} else if (q.getPosition().y == t.getPosition().y + t.getSize().y)// is directly south
						{
							southNeighbor = true;
							neighbors[5] = true;
						}
					}
				}
				int count = 0;// the number of effective neighbors
				for (int i = 0; i < neighbors.length; i++)
				{
					if (neighbors[i])
						count++;
				}

				// re-evaluates "pointless" diagonal neighbors (they are only relevant if they are surrounded)
				if (neighbors[0])// upper left diagonal
				{
					if (!neighbors[7] || !neighbors[1])
						count--;
				}
				if (neighbors[2])// upper right diagonal
				{
					if (!neighbors[1] || !neighbors[3])
						count--;
				}
				if (neighbors[4])// lower right diagonal
				{
					if (!neighbors[3] || !neighbors[5])
						count--;
				}
				if (neighbors[6])// lower left diagonal
				{
					if (!neighbors[5] || !neighbors[7])
						count--;
				}

				x_offset = count; // this is which tile to display (0-8)
				switch (count)
				{
					case 0:
						y_offset = 0;
						break;
					case 1:
						if (neighbors[1])
							y_offset = 3;
						else if (neighbors[3])
							y_offset = 0;
						else if (neighbors[5])
							y_offset = 1;
						else if (neighbors[7])
							y_offset = 2;
						break;
					case 2:
						if (neighbors[1] && neighbors[3])
							y_offset = 3;
						else if (neighbors[3] && neighbors[5])
							y_offset = 0;
						else if (neighbors[5] && neighbors[7])
							y_offset = 1;
						else if (neighbors[7] && neighbors[1])
							y_offset = 2;
						else if (neighbors[1] && neighbors[5])
							y_offset = 4;
						else if (neighbors[3] && neighbors[7])
							y_offset = 5;
						break;
					case 3:
						if (neighbors[0] && neighbors[7] && neighbors[1])
							y_offset = 2;
						else if (neighbors[2] && neighbors[1] && neighbors[3])
							y_offset = 3;
						else if (neighbors[4] && neighbors[3] && neighbors[5])
							y_offset = 0;
						else if (neighbors[6] && neighbors[5] && neighbors[7])
							y_offset = 1;
						else if (neighbors[7] && neighbors[3] && neighbors[5])
							y_offset = 4;
						else if (neighbors[1] && neighbors[7] && neighbors[5])
							y_offset = 5;
						else if (neighbors[1] && neighbors[3] && neighbors[5])
							y_offset = 6;
						else if (neighbors[7] && neighbors[1] && neighbors[3])
							y_offset = 7;
						break;
					case 4:
						if (neighbors[7] && neighbors[3] && neighbors[4] && neighbors[5])
							y_offset = 0;
						else if (neighbors[3] && neighbors[5] && neighbors[6] && neighbors[7])
							y_offset = 1;
						else if (neighbors[1] && neighbors[6] && neighbors[7] && neighbors[5])
							y_offset = 2;
						else if (neighbors[5] && neighbors[7] && neighbors[0] && neighbors[1])
							y_offset = 3;
						else if (neighbors[3] && neighbors[7] && neighbors[0] && neighbors[1])
							y_offset = 4;
						else if (neighbors[7] && neighbors[1] && neighbors[2] && neighbors[3])
							y_offset = 5;
						else if (neighbors[1] && neighbors[2] && neighbors[3] && neighbors[5])
							y_offset = 6;
						else if (neighbors[1] && neighbors[3] && neighbors[4] && neighbors[5])
							y_offset = 7;
						else if (neighbors[1] && neighbors[3] && neighbors[5] && neighbors[7])
							y_offset = 8;
						break;
					case 5:
						if (neighbors[3] && neighbors[4] && neighbors[5] && neighbors[6] && neighbors[7])
							y_offset = 0;
						else if (neighbors[0] && neighbors[1] && neighbors[5] && neighbors[6] && neighbors[7])
							y_offset = 1;
						else if (neighbors[7] && neighbors[0] && neighbors[1] && neighbors[2] && neighbors[3])
							y_offset = 2;
						else if (neighbors[1] && neighbors[2] && neighbors[3] && neighbors[4] && neighbors[5])
							y_offset = 3;
						else if (neighbors[1] && neighbors[2] && neighbors[3] && neighbors[5] && neighbors[7])
							y_offset = 4;
						else if (neighbors[1] && neighbors[3] && neighbors[4] && neighbors[5] && neighbors[7])
							y_offset = 5;
						else if (neighbors[7] && neighbors[1] && neighbors[3] && neighbors[5] && neighbors[6])
							y_offset = 6;
						else if (neighbors[0] && neighbors[1] && neighbors[3] && neighbors[5] && neighbors[7])
							y_offset = 7;
						break;
					case 6:
						if (!neighbors[0] && !neighbors[2])
							y_offset = 0;
						else if (!neighbors[2] && !neighbors[4])
							y_offset = 1;
						else if (!neighbors[4] && !neighbors[6])
							y_offset = 2;
						else if (!neighbors[6] && !neighbors[0])
							y_offset = 3;
						else if (!neighbors[0] && !neighbors[4])
							y_offset = 4;
						else if (!neighbors[2] && !neighbors[6])
							y_offset = 5;
						break;
					case 7:
						if (!neighbors[0])
							y_offset = 3;
						if (!neighbors[2])
							y_offset = 0;
						if (!neighbors[4])
							y_offset = 1;
						if (!neighbors[6])
							y_offset = 2;
						break;
					case 8:
						y_offset = 0;
						break;
				}

				String textureName = "";
				Texture tex = t.getTexture();
				if (tex == Textures.testTile)// max height is 9 tiles, so for additional tilesheets you'll need to add 9
												// to get to the start of the next
				{
					textureName = "tilesheet";
					y_offset += 0;
				}
				// TODO add more of these as more tiles are made

				float vectorX = (float) (64 * x_offset) / 1024f;// these are the size of each sprite and size of the
																// sheet itself
				float vectorY = (float) (64 * y_offset) / 1024f;

				writer.println("\t\t<TILE x=\"" + (int) t.getPosition().x * 4 + "\" y=\"" + (int) t.getPosition().y * 4 + "\" z=\"" + (int) t.getPosition().z + "\" texName=\"" + textureName + "\" texCoordX=\"" + vectorX + "\" texCoordY=\"" + vectorY + "\"/>");
			}
			writer.println("\t</TILES>");
			writer.println("\t<COLLIDERS>");
			for (RectangleBox box : colliders)
			{
				writer.println("\t<COLLIDER x=\"" + (int) box.getPosition().x * 4 + "\" y=\"" + (int) box.getPosition().y * 4 + "\" z=\"" + (int) box.getPosition().z + "\" width=\"" + (int) box.getSize().x + "\" height=\"" + (int) box.getSize().y + "\"/>");
			}
			writer.println("\t</COLLIDERS>");

			writer.println("\t<ENEMIES>");
			for (Enemy e : enemies)
			{
				String textureName = "";
				String outlineName = "";
				if (e.getTexture() == Textures.crabman)
				{
					textureName = "crabMan";
					outlineName = "crabMan";
				}
				writer.println("\t\t<ENEMY x=\"" + (int) e.getPosition().x + "\" y=\"" + (int) e.getPosition().y + "\" z=\"" + (int) e.getPosition().z + "\" width=\"" + (int) e.getSpriteSize().x + "\" height=\"" + (int) e.getSpriteSize().y + "\" texName=\"" + textureName + "\" outlineName=\""
						+ outlineName + "\"");
			}

			writer.println("\t</ENEMIES>");

			for (Tile t : tiles)
			{
				t.setPosition(new Vector3f(t.getPosition().x * 4, t.getPosition().y * 4, t.getPosition().z));
				t.setSize(new Vector2f(t.getSize().x * 4, t.getSize().y * 4));
			}
			ArrayList<Tile> sortedTiles = World.sortTiles(tiles);
			// Generate path graph
			ArrayList<Tile> ti = new ArrayList<Tile>();

			// Gathers list of tiles that have nothing above them
			for (Tile t : sortedTiles)
			{
				boolean isAbleToBeWalkedOn = true;
				for (Tile t1 : sortedTiles)
				{
					if (t.getPosition().x == t1.getPosition().x && t.getPosition().y == t1.getPosition().y + t1.getSize().y)
					{
						isAbleToBeWalkedOn = false;
						break;
					}
				}
				if (isAbleToBeWalkedOn)
				{
					ti.add(t);
				}
			}

			ArrayList<Vertex> vertices = new ArrayList<Vertex>();
			for (Tile t : ti)
			{
				vertices.add(new Vertex(t));
			}

			// Handle walking paths. All current enemies can walk between nodes on the same platform.
			for (Vertex v : vertices)
			{
				for (Vertex vv : vertices)
				{
					if (v.getTile().getPosition().x - v.getTile().getSize().x == vv.getTile().getPosition().x && v.getTile().getPosition().y == vv.getTile().getPosition().y) // VV
																																												// is
																																												// to
																																												// the
																																												// left
																																												// of
																																												// V
					{
						Edge e = new Edge(v, vv, 10);
						for (int i = 0; i < Enemies.TOTAL_NUMBER_OF_ENEMY_TYPES; i++)
						{
							e.addEnemyMovementMethod(i, MovementMethod.WALK);
						}
						v.addEdge(e);
					} else if (v.getTile().getPosition().x + v.getTile().getSize().x == vv.getTile().getPosition().x && v.getTile().getPosition().y == vv.getTile().getPosition().y) // VV
																																														// is
																																														// to
																																														// the
																																														// right
																																														// of
																																														// V
					{
						Edge e = new Edge(v, vv, 10);
						for (int i = 0; i < Enemies.TOTAL_NUMBER_OF_ENEMY_TYPES; i++)
						{
							e.addEnemyMovementMethod(i, MovementMethod.WALK);
						}
						v.addEdge(e);
					}
				}
			}

			// TODO: Loop through each enemy type
			float terminalVelocityY = Entity.MAX_SPEED_Y;
			float terminalVelocityX = Entity.MAX_SPEED_X;
			float distanceTillTerminalVelocityY = Math.abs((terminalVelocityY * terminalVelocityY) / (2 * Entity.GRAVITY));
			float distanceEnemyTraveledY = 0.0f;
			int tickCounter = 0;

			Vector2f currentEnemyVelocity = new Vector2f(terminalVelocityX, terminalVelocityX);
			Vector3f currentEnemyPosition = new Vector3f(0, 0, 0);
			Vector2f enemySize = new Vector2f(64, 64);

			int lowestYCoordinate = 0;
			for (Tile t : sortedTiles)
			{
				if (t.getPosition().getY() > lowestYCoordinate)
				{
					lowestYCoordinate = (int) t.getPosition().getY();
				}
			}
			System.out.println("Lowest y: " + lowestYCoordinate);

			// Find vertices that are not in the middle of a platform
			ArrayList<Vertex> edgeOfPlatformVertices = new ArrayList<Vertex>();
			for (Vertex v : vertices)
			{
				boolean right = false;
				boolean left = false;
				for (Edge e : v.getEdges())
				{
					Tile startTile = v.getTile();
					Tile destinationTile = e.getDestination().getTile();

					// Check if destination is to the right
					if (startTile.getPosition().getX() + startTile.getSize().getX() == destinationTile.getPosition().getX() && startTile.getPosition().getY() == destinationTile.getPosition().getY())
					{
						right = true;
						if (left)
						{
							break;
						}
					}

					if (destinationTile.getPosition().getX() + destinationTile.getSize().getX() == startTile.getPosition().getX() && destinationTile.getPosition().getY() == startTile.getPosition().getY())
					{
						left = true;
						if (right)
						{
							break;
						}
					}
				}
				if (!left || !right)
				{
					for (Tile t : sortedTiles)
					{
						Tile startTile = v.getTile();
						if (startTile.getPosition().getX() + startTile.getSize().getX() == t.getPosition().getX() && startTile.getPosition().getY() == t.getPosition().getY())
						{
							right = true;
							if (left)
							{
								break;
							}
						}

						if (t.getPosition().getX() + t.getSize().getX() == startTile.getPosition().getX() && t.getPosition().getY() == startTile.getPosition().getY())
						{
							left = true;
							if (right)
							{
								break;
							}
						}
					}
				}

				if (right && left) // middle tile
				{

				} else
				{
					edgeOfPlatformVertices.add(v);
				}
			}

			boolean isAtTerminalVelocity = false;
			for (Vertex v : edgeOfPlatformVertices)
			{
				System.out.println("Checking new vertex...");
				Tile enemyStartTile = v.getTile();
				currentEnemyPosition.x = v.getTile().getPosition().x + v.getTile().getSize().getX();
				currentEnemyPosition.z = v.getTile().getPosition().x + v.getTile().getSize().getX(); // Z is the x
																										// representation
																										// in the
																										// negative
																										// direction
				currentEnemyPosition.y = v.getTile().getPosition().y - enemySize.getY();
				currentEnemyVelocity = new Vector2f(terminalVelocityX, 0);
				distanceEnemyTraveledY = 0;
				while (currentEnemyPosition.y <= lowestYCoordinate)
				{
					currentEnemyPosition.x += currentEnemyVelocity.x;
					currentEnemyPosition.z -= currentEnemyVelocity.x;
					currentEnemyPosition.y += currentEnemyVelocity.y;
					System.out.println(currentEnemyPosition.y);
					RectangleBox enemyColliderRight = new RectangleBox(currentEnemyPosition, enemySize);
					RectangleBox enemyColliderLeft = new RectangleBox(new Vector3f(currentEnemyPosition.getZ(), currentEnemyPosition.getY(), 0), enemySize);
					int currentModuloPosition = -Integer.MAX_VALUE;
					// Check for collision
					for (Tile t : sortedTiles)
					{
						RectangleBox tileCollider = new RectangleBox(t.getPosition(), t.getSize());
						if (enemyColliderRight.isCollidingWithBox(tileCollider)) // Found collision along arc
						{
							if (t.getPosition().getY() == v.getTile().getPosition().getY()
									&& (t.getPosition().getX() + t.getSize().getX() + t.getSize().getX() == v.getTile().getPosition().getX() || v.getTile().getPosition().getX() + v.getTile().getSize().getX() + v.getTile().getSize().getX() == t.getPosition().getX()))
							{
								continue;
							} else if (t.getPosition().getY() <= v.getTile().getPosition().getY())
							{
								break;
							}
							Tile previousTile = t;
							System.out.println("PreviousTile: " + previousTile.getPosition().getX());
							// assumming falling to the right
							while ((int) previousTile.getPosition().getX() > (int) v.getTile().getPosition().getX() + (int) v.getTile().getSize().getX())
							{
								boolean brokeOutOfLoop = false;
								for (Tile tt : tiles)
								{
									if ((int) tt.getPosition().getX() + (int) tt.getSize().getX() == (int) previousTile.getPosition().getX() && (int) tt.getPosition().getY() == (int) previousTile.getPosition().getY())
									{
										if (!isBlockOnTop(tt, tiles))
										{
											Edge e = new Edge(v, getVertexFromTile(tt, vertices), 14);
											e.addEnemyMovementMethod(0, MovementMethod.FALL);
											v.addEdge(e);
										}
										previousTile = tt;
										brokeOutOfLoop = true;
										break;
									}
								}

								if (!brokeOutOfLoop)
								{
									break;
								}
							}
							if ((int) previousTile.getPosition().getX() == (int) v.getTile().getPosition().getX() && (int) previousTile.getPosition().getY() == (int) v.getTile().getPosition().getY()) // Check
																																																		// if
																																																		// blocked
																																																		// off
							{
								break;
							}
						} else if (tileCollider.isCollidingWithBox(enemyColliderLeft))
						{
							if (t.getPosition().getY() == v.getTile().getPosition().getY()
									&& (t.getPosition().getX() + t.getSize().getX() + t.getSize().getX() == v.getTile().getPosition().getX() || v.getTile().getPosition().getX() + v.getTile().getSize().getX() + v.getTile().getSize().getX() == t.getPosition().getX()))
							{
								continue;
							} else if (t.getPosition().getY() <= v.getTile().getPosition().getY())
							{
								break;
							}
							Tile previousTile = t;
							System.out.println("PreviousTile: " + previousTile.getPosition().getX());
							// assumming falling to the right
							while ((int) previousTile.getPosition().getX() + previousTile.getSize().getX() < (int) v.getTile().getPosition().getX())
							{
								boolean brokeOutOfLoop = false;
								for (Tile tt : tiles)
								{
									if ((int) previousTile.getPosition().getX() + (int) previousTile.getSize().getX() == (int) tt.getPosition().getX() && (int) tt.getPosition().getY() == (int) previousTile.getPosition().getY())
									{
										if (!isBlockOnTop(tt, tiles))
										{
											Edge e = new Edge(v, getVertexFromTile(tt, vertices), 14);
											e.addEnemyMovementMethod(0, MovementMethod.FALL);
											v.addEdge(e);
										}
										previousTile = tt;
										brokeOutOfLoop = true;
										break;
									}
								}
								if (!brokeOutOfLoop)
								{
									break;
								}
							}
							if ((int) previousTile.getPosition().getX() == (int) v.getTile().getPosition().getX() && (int) previousTile.getPosition().getY() == (int) v.getTile().getPosition().getY()) // Check
																																																		// if
																																																		// blocked
																																																		// off
							{
								break;
							}
						}
					}
					int tmp = (int) (currentEnemyPosition.getX() / (int) v.getTile().getSize().getX());
					if (currentModuloPosition != tmp)
					{
						currentModuloPosition = tmp;
						for (Tile ttt : sortedTiles)
						{
							if ((int) ttt.getPosition().getX() / (int) ttt.getSize().getX() == currentModuloPosition && ttt.getPosition().getY() > v.getTile().getPosition().getY())
							{
								System.out.println("Found edge...");
								Vertex tmp2 = getVertexFromTile(ttt, vertices);
								if (tmp2 == null || tmp2 == v)
								{
									System.out.println("Error finding edge...");
									continue;
								}
								Edge e = new Edge(v, tmp2, 14);
								e.addEnemyMovementMethod(0, MovementMethod.FALL);
								v.addEdge(e);
							}
						}
					}

					currentEnemyVelocity.y += Entity.GRAVITY;
					if (currentEnemyVelocity.y > terminalVelocityY)
					{
						currentEnemyVelocity.y = terminalVelocityY;
					}
				}
				// RectangleBox entityCollider = new RectangleBox(currentEnemyPosition,enemySize);
				// for(Vertex vv : vertices)
				// {
				// if(vv.getTile().getPosition().getY() > v.getTile().getPosition().getY())
				// {
				// Edge ee = new Edge(v,vv,14);
				// ee.addEnemyMovementMethod(0, MovementMethod.FALL);
				// v.addEdge(ee);
				// }
				// }
				// currentEnemyPosition.x += terminalVelocityY;
				// currentEnemyPosition.y += currentEnemyVelocity.getY();
				// currentEnemyVelocity.y += Entity.GRAVITY;
				// apply physics
				// }
			}

			for (Vertex v : vertices)
			{
				// System.out.println(v);
				writer.println("\t<VERTEX x=\"" + (int) v.getTile().getPosition().x + "\" y=\"" + (int) v.getTile().getPosition().y + "\">");

				for (Edge e : v.getEdges())
				{
					for (EnemyMovement ee : e.getEnemyMovement())
					{
						writer.println("\t\t<EDGE D x=\"" + (int) e.getDestination().getTile().getPosition().x + "\" y=\"" + (int) e.getDestination().getTile().getPosition().y + "\" weight=\"" + (int) e.getWeight() + "\" enemyType=\"" + ee.getEnemyTypeID() + "\" movementType=\""
								+ ee.getMovementMethod().toString() + "\"/>");
					}
				}

				writer.println("\t</VERTEX>");
			}

			writer.println("</LEVEL>");
			writer.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		System.out.println("Done...");
		readyAfterClickingSave = true;
	}

	public static boolean isBlockOnTop(Tile t, ArrayList<Tile> tiles)
	{
		for (Tile tile : tiles)
		{
			if ((int) tile.getPosition().getX() == (int) t.getPosition().getX() && tile.getPosition().getY() == t.getPosition().getY() - tile.getSize().getY())
			{
				return true;
			}
		}
		return false;
	}

	public static Vertex getVertexFromTile(Tile t, ArrayList<Vertex> vertices)
	{
		for (Vertex v : vertices)
		{
			if (v.getTile() == t)
			{
				return v;
			}
		}
		return null;
	}
}