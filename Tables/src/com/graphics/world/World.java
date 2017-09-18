package com.graphics.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.Textures;
import com.graphics.world.enemys.Enemy;
import com.graphics.world.util.Edge;
import com.graphics.world.util.MovementMethod;
import com.graphics.world.util.Vertex;
import com.main.Game;
import com.util.Utils;

/**
 * Loads a world from a file
 * 
 * @author Craig Ferris
 *
 */
public class World
{
	private static ArrayList<Tile>			tiles;
	private static ArrayList<RectangleBox>	colliders;
	private static ArrayList<Enemy>			enemies		= new ArrayList<Enemy>();
	private static ArrayList<DialogBox>		dialogue	= new ArrayList<DialogBox>();

	/**
	 * Creates a new world Initializes a few variables
	 */
	public World()
	{
		tiles = new ArrayList<Tile>();
		colliders = new ArrayList<RectangleBox>();
	}

	/**
	 * Loads a new world from a file and parses it
	 * 
	 * @param filename
	 *            The name of the file
	 * @return Returns a level with the values from the file
	 */
	@SuppressWarnings("unused")
	public static Level loadWorld(String filename)
	{
		tiles = new ArrayList<Tile>();
		colliders = new ArrayList<RectangleBox>();
		enemies = new ArrayList<Enemy>();
		dialogue = new ArrayList<DialogBox>();
		Level newLevel = null;
		int width = 0;
		int height = 0;
		boolean inVertex = false;
		Vertex currentVertex = null;
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		try
		{
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			String line = "";
			while (scanner.hasNextLine())
			{
				line = scanner.nextLine();
				// System.out.println("Parsing: " + line.trim());
				if (line.trim().startsWith("<LEVEL"))
				{
					String levelName = "";
					String nameParam = line.substring(line.indexOf("name=\"") + 6);
					levelName = nameParam.substring(0, nameParam.indexOf("\""));
					newLevel = new Level(levelName);
					System.out.println("Loading level: " + levelName);
				} else if (line.trim().startsWith("<DOOR"))
				{
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0, param.indexOf("\""));
					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0, param1.indexOf("\""));

					Tile t = new Tile(new Vector3f(Integer.parseInt(x), Integer.parseInt(y), 0), new Vector2f(width, height), Textures.door);
					t.setDoor(true);
					tiles.add(t);
				} else if (line.trim().startsWith("<TILES "))
				{
					String param = line.substring(line.indexOf("sizex=\"") + 7);
					String x = param.substring(0, param.indexOf("\""));
					width = Integer.parseInt(x);

					String param1 = param.substring(param.indexOf("sizey=\"") + 7);
					String y = param1.substring(0, param1.indexOf("\""));
					height = Integer.parseInt(y);
					System.out.println("Width: " + width + " Height: " + height);
				} else if (line.trim().startsWith("<TILE "))
				{
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0, param.indexOf("\""));
					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0, param1.indexOf("\""));
					String param2 = param1.substring(param1.indexOf("z=\"") + 3);
					String z = param2.substring(0, param2.indexOf("\""));

					String param3 = param2.substring(param2.indexOf("texName=\"") + 9);
					String tex = param3.substring(0, param3.indexOf("\""));

					String param4 = param3.substring(param3.indexOf("texCoordX=\"") + 11);
					String tCoordX = param4.substring(0, param4.indexOf("\""));

					String param5 = param4.substring(param4.indexOf("texCoordY=\"") + 11);
					String tCoordY = param5.substring(0, param5.indexOf("\""));

					Texture texture = null;
					int texX = -1;
					int texY = -1;

					if (tex.equals("tilesheet"))
					{
						texture = Textures.tilesheet;
					} else if (tex.equals("testTile"))
					{
						texture = Textures.testTile;
					} else if (tex.equals("grass"))
					{
						texture = Textures.grass;
					} else if (tex.equals("down"))
					{
						texture = Textures.tileOutline;
						texX = 0;
						texY = 0;
					} else if (tex.equals("right"))
					{
						texture = Textures.tileOutline;
						texX = 1;
						texY = 0;
					} else if (tex.equals("up"))
					{
						texture = Textures.tileOutline;
						texX = 2;
						texY = 0;
					} else if (tex.equals("left"))
					{
						texture = Textures.tileOutline;
						texX = 3;
						texY = 0;
					} else if (tex.equals("downright"))
					{
						texture = Textures.tileOutline;
						texX = 4;
						texY = 0;
					} else if (tex.equals("upright"))
					{
						texture = Textures.tileOutline;
						texX = 5;
						texY = 0;
					} else if (tex.equals("upleft"))
					{
						texture = Textures.tileOutline;
						texX = 6;
						texY = 0;
					} else if (tex.equals("downleft"))
					{
						texture = Textures.tileOutline;
						texX = 7;
						texY = 0;
					} else if (tex.equals("downleftright"))
					{
						texture = Textures.tileOutline;
						texX = 8;
						texY = 0;
					} else if (tex.equals("updownright"))
					{
						texture = Textures.tileOutline;
						texX = 9;
						texY = 0;
					} else if (tex.equals("leftupright"))
					{
						texture = Textures.tileOutline;
						texX = 10;
						texY = 0;
					} else if (tex.equals("rightupdown"))
					{
						texture = Textures.tileOutline;
						texX = 11;
						texY = 0;
					} else if (tex.equals("leftright"))
					{
						texture = Textures.tileOutline;
						texX = 12;
						texY = 0;
					} else if (tex.equals("topdown"))
					{
						texture = Textures.tileOutline;
						texX = 13;
						texY = 0;
					} else if (tex.equals("updownleftright"))
					{
						texture = Textures.tileOutline;
						texX = 14;
						texY = 0;
					}

					int xPos = Integer.parseInt(x);
					int yPos = Integer.parseInt(y);
					int zPos = Integer.parseInt(z);
					float texCoordX = Float.parseFloat(tCoordX);
					float texCoordY = Float.parseFloat(tCoordY);
					tiles.add(new Tile(new Vector3f(xPos, yPos, zPos), new Vector2f(width, height), texture, texCoordX, texCoordY));
					System.out.println("New Tile: (" + xPos + ", " + yPos + ", " + zPos + ") (" + width + ", " + height + ")");
				} else if (line.trim().startsWith("<COLLIDER "))
				{
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0, param.indexOf("\""));

					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0, param1.indexOf("\""));

					String param2 = param1.substring(param1.indexOf("z=\"") + 3);
					String z = param2.substring(0, param2.indexOf("\""));

					String param3 = param2.substring(param2.indexOf("width=\"") + 7);
					String width1 = param3.substring(0, param3.indexOf("\""));

					String param4 = param3.substring(param3.indexOf("height=\"") + 8);
					String height1 = param4.substring(0, param4.indexOf("\""));

					RectangleBox box = new RectangleBox(new Vector3f(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)), new Vector2f(Integer.parseInt(width1), Integer.parseInt(height1)));
					colliders.add(box);
					System.out.println("New Collider: (" + x + ", " + y + ", " + z + ") (" + width1 + ", " + height1 + ")");
				} else if (line.trim().startsWith("<ENEMY "))
				{
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0, param.indexOf("\""));

					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0, param1.indexOf("\""));

					String param2 = param1.substring(param1.indexOf("z=\"") + 3);
					String z = param2.substring(0, param2.indexOf("\""));

					String param3 = param2.substring(param2.indexOf("width=\"") + 7);
					String width1 = param3.substring(0, param3.indexOf("\""));

					String param4 = param3.substring(param3.indexOf("height=\"") + 8);
					String height1 = param4.substring(0, param4.indexOf("\""));

					String param5 = param4.substring(param4.indexOf("texName=\"") + 9);
					String tex1 = param5.substring(0, param5.indexOf("\""));

					String param6 = param5.substring(param5.indexOf("outlineName=\"") + 13);
					String tex2 = param6.substring(0, param6.indexOf("\""));

					int nx = 0;
					int ny = 0;

					// tex names
					Texture t1 = null;
					Texture t2 = null;
					boolean isEnemy = false;
					Vector2f size = new Vector2f(0, 0);
					Vector2f spriteSheet = new Vector2f(0, 0);

					if (tex1.equals("crabMan"))
					{
						t1 = Textures.crabman;
						t2 = Textures.crabman;
						nx = 10;
						isEnemy = true;
						size = new Vector2f(64, 64);
						spriteSheet = new Vector2f(256, 128);
					}
					if (tex1.equals("player"))
					{
						t1 = Textures.playerFront;
						t2 = Textures.playerOutline;
						isEnemy = true;
						size = new Vector2f(32, 32);
						spriteSheet = new Vector2f(512, 256);
						nx = 10;
					}

					Enemy entity = new Enemy(new Vector3f(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)), t1, t2, nx, ny, new Vector2f(Integer.parseInt(width1), Integer.parseInt(height1)), size);
					entity.setAffectedByGravity(true);
					entity.setHostileToPlayer(isEnemy);
					enemies.add(entity);
					System.out.println("New Enemy: (" + x + ", " + y + ", " + z + ") (" + width1 + ", " + height1 + ")");
				} else if (line.trim().startsWith("<TEXT "))// reads speech for dialogue boxes from the level. accepts a
															// name, portrait index, and box background.
				{
					String param = line.substring(line.indexOf("speakerName=\"") + 13);
					String speakerName = param.substring(0, param.indexOf("\""));

					String param1 = param.substring(param.indexOf("texFace=\"") + 9);
					String tex1 = param1.substring(0, param1.indexOf("\""));

					String param2 = param1.substring(param1.indexOf("texBox=\"") + 8);
					String tex2 = param2.substring(0, param2.indexOf("\""));

					ArrayList<String> text = new ArrayList<String>();

					if (scanner.hasNextLine())
					{
						line = scanner.nextLine();
					}
					while (!line.trim().startsWith("</TEXT"))// within 2 TEXT tags, it counts all lines as readable text
					{
						text.add(line.trim());
						if (scanner.hasNextLine())
						{
							line = scanner.nextLine();
						} else
						{
							break;
						}
					}
					int portraitnum = 0;
					int boxnum = 0;
					if (tex1.equals("testImg"))// this will be added to when more portraits become available
					{
						portraitnum = 0;
					}
					if (tex2.equals("standard"))// this will be added to when more text boxes become available
					{
						boxnum = 0;
					}
					String[] text2 = new String[text.size()];
					text.toArray(text2);// sends an array instead of an arraylist, to avoid having lists of lists
					dialogue.add(new DialogBox(text2, speakerName, portraitnum, boxnum));
					System.out.println("New Dialogue: (" + speakerName + ", saying " + text2.length + " lines), using portrait \"" + tex1 + "\" and text box \"" + tex2 + "\"");
				} else if (line.trim().startsWith("<PLAYER"))
				{
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0, param.indexOf("\""));

					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0, param1.indexOf("\""));

					String param2 = param1.substring(param1.indexOf("z=\"") + 3);
					String z = param2.substring(0, param2.indexOf("\""));

					String param3 = param2.substring(param2.indexOf("width=\"") + 7);
					String width1 = param3.substring(0, param3.indexOf("\""));

					String param4 = param3.substring(param3.indexOf("height=\"") + 8);
					String height1 = param4.substring(0, param4.indexOf("\""));

					String param5 = param4.substring(param4.indexOf("tex=\"") + 5);
					String tex1 = param5.substring(0, param5.indexOf("\""));

					String param6 = param5.substring(param5.indexOf("texOut=\"") + 8);
					String tex2 = param6.substring(0, param6.indexOf("\""));
					newLevel.setPlayerSpawnLocation(new Vector3f(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z)));
				} else if (line.trim().startsWith("<VERTEX"))
				{
					inVertex = true;
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0, param.indexOf("\""));

					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0, param1.indexOf("\""));

					int iX = Integer.parseInt(x);
					int iY = Integer.parseInt(y);
					for (Tile t : tiles)
					{
						if (t.getPosition().x == iX && t.getPosition().y == iY)
						{
							Vertex vT = Utils.findTileVertexInVertices(t, vertices);
							if (vT == null)
							{
								currentVertex = new Vertex(t);
							} else
							{
								currentVertex = vT;
							}
							break;
						}
					}
				} else if (line.trim().startsWith("</VERTEX>"))
				{
					vertices.add(currentVertex);
				} else if (line.trim().startsWith("<EDGE"))
				{
					if (currentVertex == null)
					{
						System.out.println("FATAL Error reading in world");
						return null;
					}
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0, param.indexOf("\""));

					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0, param1.indexOf("\""));

					String param2 = param1.substring(param1.indexOf("weight=\"") + 8);
					String weight = param2.substring(0, param2.indexOf("\""));
					
					String param3 = param2.substring(param2.indexOf("enemyType=\"") + 11);
					String enemyType = param3.substring(0,param3.indexOf("\""));
					
					String param4 = param3.substring(param3.indexOf("movementType=\"") + 14);
					String movementType = param4.substring(0,param4.indexOf("\""));							

					int iX = Integer.parseInt(x);
					int iY = Integer.parseInt(y);
					int iWeight = Integer.parseInt(weight);
					for (Tile t : tiles)
					{
						if (t.getPosition().x == iX && t.getPosition().y == iY)
						{
							Vertex vT = Utils.findTileVertexInVertices(t, vertices);
							if (vT == null)
							{
								vT = new Vertex(t);
								vertices.add(vT);
							}
							Edge edge = new Edge(currentVertex, vT, iWeight);
							MovementMethod method = MovementMethod.WALK;
							if(movementType.trim().equals("WALK"))
							{
								method = MovementMethod.WALK;
							}
							else if(movementType.trim().equals("FALL"))
							{
								method = MovementMethod.FALL;
							}
							else if(movementType.trim().equals("JUMP"))
							{
								method = MovementMethod.JUMP;
							}
							edge.addEnemyMovementMethod(Integer.parseInt(enemyType), method);
							currentVertex.addEdge(edge);
							break;
						}
					}
				}
			}

			scanner.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("yuo are dumb");
			e.printStackTrace();
		}
		if (newLevel == null)
		{
			return null;
		}
		newLevel.setColliders(colliders);
		newLevel.setTiles(sortTiles(tiles));
		newLevel.setEnemies(enemies);
		newLevel.setDialogue(dialogue);
		newLevel.setVertices(vertices);

		return newLevel;
	}

	/**
	 * Sorts the array of tiles by putting farther tiles in the array first
	 * 
	 * @return Returns a sorted array where the first items have the lowest z values
	 */
	public static ArrayList<Tile> sortTiles(ArrayList<Tile> tiles)
	{
		tiles.sort(new TileComparator());
		return tiles;
	}

	/**
	 * Compares tiles
	 */
	private static class TileComparator implements Comparator<Tile>
	{

		/**
		 * @param o1
		 *            The first tile to compare
		 * @param o2
		 *            The second tile to compare
		 * @return Returns the difference in the two tiles
		 */
		@Override
		public int compare(Tile o1, Tile o2)
		{
			return (int) (o2.getPosition().z - o1.getPosition().z);
		}

	}

}
