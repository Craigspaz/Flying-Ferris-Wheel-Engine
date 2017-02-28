package com.levelbuilder;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

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
	private Camera camera;
	
	public LevelBuilderGame()
	{
		new Textures();
		handler = new InputHandler();
		camera = new Camera(new Vector2f(0,0),new Vector2f(LevelBuilder.width,LevelBuilder.height));
	}
	
	public void update()
	{
		
		camera.update();
	}
	
	public void render()
	{
		GL11.glTranslatef(-camera.getPosition().x, -camera.getPosition().y, 0.0f);
		for(Tile t : tiles)
		{
			t.render();
		}
	}
}
