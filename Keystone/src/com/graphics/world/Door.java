package com.graphics.world;

import javax.xml.ws.handler.HandlerResolver;

import org.lwjgl.util.vector.Vector3f;

import com.input.InputHandler;
import com.main.Game;

public class Door extends Tile
{
	private Game			game;
	private InputHandler	handler;
	private Vector3f		destination;
	private String			levelName;

	public Door(Game game, InputHandler handler)
	{
		super();
		this.game = game;
		this.destination = new Vector3f(0, 0, 0);
	}

	public void setDestination(String levelName, Vector3f destination)
	{
		this.levelName = levelName;
		this.destination = destination;
	}

	public void openDoor()
	{
		game.loadNewLevel(levelName);
		game.exitDoor(destination);
	}

	public void update()
	{
		if (isCollidingWithBox(game.getPlayer().getCollider()) && handler.down())
		{
			openDoor();
		}
	}
}
