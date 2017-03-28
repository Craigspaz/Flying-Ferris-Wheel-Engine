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
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.graphics.world.enemys.Enemy;
import com.input.InputHandler;
import com.main.Window;

/**
 * Lets the user build a level
 * 
 * @author Craig Ferris
 *
 */
public class LevelBuilderGame
{
	private ArrayList<Tile>		tiles			= new ArrayList<Tile>();
	private ArrayList<Enemy>	enemies			= new ArrayList<Enemy>();

	private Player				player;

	private InputHandler		handler;
	private Texture				tileToPlace;
	private boolean				isMouseReady	= true;

	private Texture				down			= Loader.loadTexture("borders/down");
	private Texture				downleft		= Loader.loadTexture("borders/downleft");
	private Texture				downleftright	= Loader.loadTexture("borders/downleftright");
	private Texture				downright		= Loader.loadTexture("borders/downright");
	private Texture				left			= Loader.loadTexture("borders/left");
	private Texture				leftright		= Loader.loadTexture("borders/leftright");
	private Texture				leftupright		= Loader.loadTexture("borders/leftupright");
	private Texture				right			= Loader.loadTexture("borders/right");
	private Texture				rightupdown		= Loader.loadTexture("borders/rightupdown");
	private Texture				topdown			= Loader.loadTexture("borders/topdown");
	private Texture				up				= Loader.loadTexture("borders/up");
	private Texture				updownleftright	= Loader.loadTexture("borders/updownleftright");
	private Texture				updownright		= Loader.loadTexture("borders/updownright");
	private Texture				upleft			= Loader.loadTexture("borders/upleft");
	private Texture				upright			= Loader.loadTexture("borders/upright");
	private Texture				saveLevel		= Loader.loadTexture("saveLevel");
	private Texture				door			= Loader.loadTexture("door");

	/**
	 * Creates a new level builder
	 */
	public LevelBuilderGame()
	{
		new Textures();
		handler = new InputHandler();
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
		if (handler.isMouseLeftClicking() && isMouseReady)
		{
			isMouseReady = false;
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
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 672 && handler.getMousePosition().y < 704)
			{
				saveLevel();
			} else
			{
				if (tileToPlace != null)
				{
					if (tileToPlace == Textures.playerFront)
					{
						if (player == null)
						{
							player = new Player(new Vector3f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, 0), tileToPlace, tileToPlace, new Vector2f(512, 256), 0, 0, new Vector2f(16, 16), new Vector2f(32, 32),
									handler);
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
							enemies.add(new Enemy(new Vector3f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, 0), Textures.crabman, Textures.crabman, new Vector2f(512, 128), 0, 0, new Vector2f(16, 16),
									new Vector2f(64, 64)));
						}

					} else
					{
						Vector2f pos = new Vector2f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16);
						boolean createTile = true;
						for (Tile t : tiles)
						{
							if (t.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && t.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								tiles.remove(t);
								createTile = false;
								break;
							}
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
						if (createTile)
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

		GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.highlight);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 0, Textures.testTile);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 32, Textures.grass);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 64, Textures.grassTop);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 96, Textures.dirt2);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 128, Textures.dirt);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 160, Textures.air);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 192, down);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 224, downleft);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 256, downleftright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 288, downright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 320, left);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 352, leftright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 384, leftupright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 416, right);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 448, rightupdown);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 480, topdown);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 512, up);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 544, updownleftright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 576, updownright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 608, upleft);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 640, upright);

		GFX.drawSpriteFromSpriteSheet(32, 32, Window.width - 64, 0, Textures.playerFront, new Vector2f(0, 0), new Vector2f((float) 32 / 512, (float) 32 / 256));

		GFX.drawEntireSprite(32, 32, Window.width - 32, 672, saveLevel);

		GFX.drawSpriteFromSpriteSheet(32, 32, Window.width - 64, 32, Textures.crabman, new Vector2f(0, 0), new Vector2f((float) 64 / 512, (float) 64 / 128));

		GFX.drawEntireSprite(32, 32, Window.width - 64, 64, door);

		if (tileToPlace != null)
		{
			if (tileToPlace == Textures.playerFront)
			{
				GFX.drawSpriteFromSpriteSheet(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.playerFront, new Vector2f(0, 0), new Vector2f((float) 32 / 512, (float) 32 / 256));
			} else if (tileToPlace == Textures.crabman)
			{
				GFX.drawSpriteFromSpriteSheet(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.crabman, new Vector2f(0, 0), new Vector2f((float) 64 / 512, (float) 64 / 128));
			} else
			{
				GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, tileToPlace);
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
			colliders.add(t.getCollider());
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

		System.out.println("Saving...");
		try
		{
			File output = new File("./res/generated/gen_" + System.currentTimeMillis() + "_level.od");
			PrintWriter writer = new PrintWriter(output);
			writer.println("<LEVEL name=\"" + output.getName() + "\">");
			if (player != null)
			{
				writer.println("<PLAYER x=\"" + (int) player.getPosition().x * 4 + "\" y=\"" + (int) player.getPosition().y * 4 + "\" z=\"" + (int) player.getPosition().z + "\" width=\"" + (int) player.getScale().x + "\" height=\"" + (int) player.getScale().y
						+ "\" tex=\"playerFront\" texOut=\"playerOutline\"/>");
			}
			writer.println("\t<TILES sizex=\"64\" sizey=\"64\">");
			for (int i = 0; i < tiles.size(); i++)
			{
				if (tiles.get(i).getTexture() == door)
				{
					writer.println("\t\t<DOOR x=\"" + (int)tiles.get(i).getPosition().x * 4 + "\" y=\"" + (int)tiles.get(i).getPosition().y * 4 + "\"/>");
					tiles.remove(i);
					i--;
				}
			}
			for (Tile t : tiles)
			{
				String textureName = "";
				Texture tex = t.getTexture();
				if (tex == Textures.testTile)
				{
					textureName = "testTile";
				} else if (tex == Textures.grass)
				{
					textureName = "grass";
				} else if (tex == Textures.grassTop)
				{
					textureName = "grassTop";
				} else if (tex == Textures.dirt2)
				{
					textureName = "dirt2";
				} else if (tex == Textures.dirt)
				{
					textureName = "dirt";
				} else if (tex == Textures.air)
				{
					textureName = "air";
				} else if (tex == down)
				{
					textureName = "down";
				} else if (tex == downleft)
				{
					textureName = "downleft";
				} else if (tex == downleftright)
				{
					textureName = "downleftright";
				} else if (tex == downright)
				{
					textureName = "downright";
				} else if (tex == left)
				{
					textureName = "left";
				} else if (tex == leftright)
				{
					textureName = "leftright";
				} else if (tex == leftupright)
				{
					textureName = "leftupright";
				} else if (tex == right)
				{
					textureName = "right";
				} else if (tex == rightupdown)
				{
					textureName = "rightupdown";
				} else if (tex == topdown)
				{
					textureName = "topdown";
				} else if (tex == up)
				{
					textureName = "up";
				} else if (tex == updownleftright)
				{
					textureName = "updownleftright";
				} else if (tex == updownright)
				{
					textureName = "updownright";
				} else if (tex == upleft)
				{
					textureName = "upleft";
				} else if (tex == upright)
				{
					textureName = "upright";
				}
				writer.println("\t\t<TILE x=\"" + (int) t.getPosition().x * 4 + "\" y=\"" + (int) t.getPosition().y * 4 + "\" z=\"" + (int) t.getPosition().z + "\" texName=\"" + textureName + "\"/>");
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
				writer.println(
						"\t\t<ENEMY x=\"" + (int) e.getPosition().x + "\" y=\"" + (int) e.getPosition().y + "\" z=\"" + (int) e.getPosition().z + "\" width=\"" + (int) e.getScale().x + "\" height=\"" + (int) e.getScale().y + "\" texName=\"" + textureName + "\" outlineName=\"" + outlineName + "\"");
			}

			writer.println("\t</ENEMIES>");
			writer.println("</LEVEL>");
			writer.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}