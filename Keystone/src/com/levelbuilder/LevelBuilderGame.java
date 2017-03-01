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
	private ArrayList<Tile>		tiles			= new ArrayList<Tile>();
	private ArrayList<Enemy>	enemies			= new ArrayList<Enemy>();

	private InputHandler		handler;
	private Texture				tileToPlace;
	private boolean				isMouseReady	= true;

	public LevelBuilderGame()
	{
		new Textures();
		handler = new InputHandler();
	}

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
			}else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 96 && handler.getMousePosition().y < 128)
			{
				tileToPlace = Textures.dirt2;
			}else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 128 && handler.getMousePosition().y < 160)
			{
				tileToPlace = Textures.dirt;
			}else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 160 && handler.getMousePosition().y < 192)
			{
				tileToPlace = Textures.air;
			}else
			{
				if (tileToPlace != null)
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
					if (createTile)
					{
						tiles.add(new Tile(new Vector3f(pos.x, pos.y, 0), new Vector2f(16, 16), tileToPlace));
					}
				}
			}
		}
	}

	public void render()
	{
		for (Tile t : tiles)
		{
			t.render();
		}

		GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.highlight);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 0, Textures.testTile);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 32, Textures.grass);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 64, Textures.grassTop);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 96, Textures.dirt);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 128, Textures.dirt2);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 160, Textures.air);
		

		if (tileToPlace != null)
		{
			GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, tileToPlace);
		}
	}
}
