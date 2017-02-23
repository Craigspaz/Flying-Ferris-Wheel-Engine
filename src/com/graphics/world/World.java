package com.graphics.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.Textures;

/**
 * Loads a world from a file
 * @author Craig Ferris
 *
 */
public class World 
{
	private ArrayList<Tile> tiles;
	private ArrayList<RectangleBox> colliders;
	
	
	/**
	 * Creates a new world
	 * Initializes a few variables
	 */
	public World()
	{
		tiles = new ArrayList<Tile>();
		colliders = new ArrayList<RectangleBox>();
	}
	
	/**
	 * Loads a new world from a file and parses it
	 * @param filename The name of the file
	 * @return Returns a level with the values from the file
	 */
	public Level loadWorld(String filename)
	{
		tiles = new ArrayList<Tile>();
		colliders = new ArrayList<RectangleBox>();
		Level newLevel = null;
		int width = 0;
		int height = 0;
		
		File file = new File(filename);
		try 
		{
			Scanner scanner = new Scanner(file);
			String line = "";
			while(scanner.hasNextLine())
			{
				line = scanner.nextLine();
				//System.out.println("Parsing: " + line.trim());
				if(line.trim().startsWith("<LEVEL"))
				{
					String levelName = "";
					String nameParam = line.substring(line.indexOf("name=\"") + 6);
					levelName = nameParam.substring(0,nameParam.indexOf("\""));
					newLevel = new Level(levelName);
					System.out.println("Loading level: " + levelName);
				}
				else if(line.trim().startsWith("<TILES "))
				{
					String param = line.substring(line.indexOf("sizex=\"") + 7);
					String x = param.substring(0,param.indexOf("\""));
					width = Integer.parseInt(x);
					
					String param1 = param.substring(param.indexOf("sizey=\"") + 7);
					String y = param1.substring(0,param1.indexOf("\""));
					height = Integer.parseInt(y);
					System.out.println("Width: " + width + " Height: " + height);
				}
				else if(line.trim().startsWith("<TILE "))
				{
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0,param.indexOf("\""));
					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0,param1.indexOf("\""));
					String param2 = param1.substring(param1.indexOf("z=\"") + 3);
					String z = param2.substring(0,param2.indexOf("\""));
					
					String param3 = param2.substring(param2.indexOf("texName=\"") + 9);
					String tex = param3.substring(0,param3.indexOf("\""));
					
					Texture texture = null;
					
					if(tex.equals("testTile"))
					{
						texture = Textures.testTile;
					}
					
					int xPos = Integer.parseInt(x);
					int yPos = Integer.parseInt(y);
					int zPos = Integer.parseInt(z);
					tiles.add(new Tile(new Vector3f(xPos,yPos,zPos),new Vector2f(width,height),texture));
					System.out.println("New Tile: (" + xPos + ", " + yPos + ", " + zPos + ") (" + width + ", " + height + ")");
				}
				else if(line.trim().startsWith("<COLLIDER "))
				{
					String param = line.substring(line.indexOf("x=\"") + 3);
					String x = param.substring(0,param.indexOf("\""));
					
					String param1 = param.substring(param.indexOf("y=\"") + 3);
					String y = param1.substring(0,param1.indexOf("\""));
					
					String param2 = param1.substring(param1.indexOf("z=\"") + 3);
					String z = param2.substring(0,param2.indexOf("\""));
					
					String param3 = param2.substring(param2.indexOf("width=\"") + 7);
					String width1 = param3.substring(0,param3.indexOf("\""));
					
					String param4 = param3.substring(param3.indexOf("height=\"") + 8);
					String height1 = param4.substring(0,param4.indexOf("\""));
					
					RectangleBox box = new RectangleBox(new Vector3f(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(z)),new Vector2f(Integer.parseInt(width1),Integer.parseInt(height1)));
					colliders.add(box);
					System.out.println("New Collider: (" + x + ", " + y + ", " + z + ") (" + width1 + ", " + height1 + ")");
				}
			}
			
			scanner.close();
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		newLevel.setColliders(colliders);
		newLevel.setTiles(sortTiles());
		return newLevel;
	}
	
	
	/**
	 * Sorts the array of tiles by putting farther tiles in the array first
	 * @return Returns a sorted array where the first items have the lowest z values
	 */
	private ArrayList<Tile> sortTiles()
	{
		ArrayList<Tile> result = new ArrayList<Tile>();
		while(!tiles.isEmpty())
		{
			float minimum = Float.MAX_VALUE;
			Tile min = null;
			int id = -1;
			int counter = 0;
			for(Tile t: tiles)
			{
				if(t.getPosition().z < minimum)
				{
					min = t;
					minimum = t.getPosition().z;
					id = counter;
				}
				counter++;
			}
			result.add(min);
			tiles.remove(id);
		}
		return result;
	}
	
}
