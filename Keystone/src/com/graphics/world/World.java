package com.graphics.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.Textures;

public class World 
{
	private ArrayList<Tile> tiles;
	
	
	public World()
	{
		tiles = new ArrayList<Tile>();
	}
	
	public Level loadWorld(String filename)
	{
		tiles = new ArrayList<Tile>();
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
				if(line.startsWith("<LEVEL"))
				{
					String levelName = "";
					String nameParam = line.substring(line.indexOf("name=\"") + 6);
					levelName = nameParam.substring(0,nameParam.indexOf("\""));
					newLevel = new Level(levelName);
				}
				else if(line.startsWith("<TILES "))
				{
					String param = line.substring(line.indexOf("sizex=\"") + 7);
					String x = param.substring(0,param.indexOf("\""));
					width = Integer.parseInt(x);
					
					String param1 = param.substring(param.indexOf("sizey=\"") + 7);
					String y = param1.substring(0,param1.indexOf("\""));
					height = Integer.parseInt(y);
				}
				else if(line.startsWith("<TILE "))
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
				}
			}
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
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
