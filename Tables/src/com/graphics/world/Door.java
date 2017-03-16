package com.graphics.world;

import org.lwjgl.util.vector.Vector3f;

import com.input.InputHandler;
import com.main.Game;

/**
 * An object that once the player collides with teleports the player to a new level
 * 
 * @author Kyle Falicov
 *
 */
public class Door extends Tile
{
	private Game			game;
	private InputHandler	handler;
	private Vector3f		destination;
	private String			levelName;

	/**
	 * Creates a new door
	 * 
	 * @param game
	 *            A pointer to the current running game
	 * @param handler
	 *            A pointer to the input handler
	 */
	public Door(Game game, InputHandler handler)
	{
		super();
		this.game = game;
		this.destination = new Vector3f(0, 0, 0);
	}

	/**
	 * Sets the destination in the next level
	 * 
	 * @param levelName
	 *            The name of the next level
	 * @param destination
	 *            The start position of the player in the next level
	 */
	public void setDestination(String levelName, Vector3f destination)
	{
		this.levelName = levelName;
		this.destination = destination;
	}

	/**
	 * Opens the door
	 */
	public void openDoor()
	{
		game.loadNewLevel(levelName);
		game.exitDoor(destination);
	}

	/**
	 * Updates the door
	 */
	public void update()
	{
		if (isCollidingWithBox(game.getPlayer().getCollider()) && handler.down())
		{
			openDoor();
		}
	}
}
