package com.levelbuilder;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Camera;
import com.graphics.world.Enemy;
import com.graphics.world.Tile;
import com.input.InputHandler;

public class LevelBuilderGame
{
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private InputHandler handler;
	
	public LevelBuilderGame()
	{
		new Textures();
		handler = new InputHandler();
	}
	
	public void update()
	{
		
	}
	
	public void render()
	{
		GFX.drawEntireSprite(16,16,LevelBuilder.width - 150, LevelBuilder.height - 400, Textures.testTile);
		GFX.drawEntireSprite(16,16,LevelBuilder.width - 150, LevelBuilder.height - 416, Textures.testTile);
		GFX.drawEntireSprite(16,16,LevelBuilder.width - 150, LevelBuilder.height - 432, Textures.testTile);
		GFX.drawEntireSprite(16,16,LevelBuilder.width - 150, LevelBuilder.height - 448, Textures.testTile);
		GFX.drawEntireSprite(16,16,LevelBuilder.width - 150, LevelBuilder.height - 464, Textures.testTile);
		for(Tile t : tiles)
		{
			t.render();
		}
	}
}
