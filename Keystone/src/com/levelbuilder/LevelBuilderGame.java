package com.levelbuilder;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Tile;
import com.graphics.world.enemys.Enemy;
import com.input.InputHandler;
import com.main.Window;

public class LevelBuilderGame
{
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private InputHandler handler;
	private Texture tileToPlace;
	
	public LevelBuilderGame()
	{
		new Textures();
		handler = new InputHandler();
	}
	
	public void update()
	{
		if(handler.isMouseLeftClicking())
		{
			if(handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 0 && handler.getMousePosition().y < 32)
			{
				//System.out.println("Clicking tile 0");
				tileToPlace = Textures.testTile;
			}
			else
			{
				if(tileToPlace != null)
				{
					Vector2f pos = new Vector2f(handler.getMousePosition().x - handler.getMousePosition().x % 16,handler.getMousePosition().y - handler.getMousePosition().y % 16);
					tiles.add(new Tile(new Vector3f(pos.x,pos.y,0),new Vector2f(16,16),tileToPlace));
					tileToPlace = null;
				}
			}
		}
	}
	
	public void render()
	{
		for(Tile t : tiles)
		{
			t.render();
		}
		
		GFX.drawEntireSprite(16,16,handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16,Textures.highlight);
		
		GFX.drawEntireSprite(32, 32, Window.width-32, 0, Textures.testTile);
		
		if(tileToPlace != null)
		{
			GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, tileToPlace);
		}
	}
}
