package com.graphics.world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

/**
 * Stores the information about a level
 * 
 * @author Craig Ferris
 *
 */
public class Level
{
	private String					name;
	private ArrayList<Tile>			tiles;
	private ArrayList<RectangleBox>	colliders;
	private ArrayList<Entity>		entities;
	private ArrayList<DialogBox>	dialogue;
	private Vector3f				playerSpawnLocation;

	/**
	 * Creates a new level
	 * 
	 * @param name
	 *            The name of the level
	 */
	public Level(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the name of the level
	 * 
	 * @return Returns the name of the level
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the level
	 * 
	 * @param name
	 *            The name of the level
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the tiles in the level
	 * 
	 * @return Returns the tiles in the level
	 */
	public ArrayList<Tile> getTiles()
	{
		return tiles;
	}

	/**
	 * Sets the tiles in the level
	 * 
	 * @param tiles
	 *            The tiles in the level
	 */
	public void setTiles(ArrayList<Tile> tiles)
	{
		this.tiles = tiles;
	}

	/**
	 * Returns the colliders in the level
	 * 
	 * @return Returns the colliders in the level
	 */
	public ArrayList<RectangleBox> getColliders()
	{
		return colliders;
	}

	/**
	 * Sets the colliders in the level
	 * 
	 * @param colliders
	 *            The colliders in the level
	 */
	public void setColliders(ArrayList<RectangleBox> colliders)
	{
		this.colliders = colliders;
	}

	/**
	 * Returns the enemies in the level
	 * 
	 * @return Returns the enemies in the level
	 */
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	/**
	 * Sets the enemies in the level
	 * 
	 * @param enemies
	 *            The enemies in the level
	 */
	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}

	/**
	 * Sets the dialogue that the player can find in the level
	 * 
	 * @param dialogue
	 *            The possible dialogue boxes
	 */
	public void setDialogue(ArrayList<DialogBox> dialogue)
	{
		this.dialogue = dialogue;
	}

	/**
	 * Returns the dialogue in the level
	 * 
	 * @return Returns the dialogue in the level
	 */
	public ArrayList<DialogBox> getDialogue()
	{
		return dialogue;
	}

	/**
	 * Returns the player spawn location in the level
	 * 
	 * @return Returns the player spawn location in the level
	 */
	public Vector3f getPlayerSpawnLocation()
	{
		return playerSpawnLocation;
	}

	/**
	 * Sets the players spawn location in the level
	 * 
	 * @param playerSpawnLocation
	 *            The location to spawn the player
	 */
	public void setPlayerSpawnLocation(Vector3f playerSpawnLocation)
	{
		this.playerSpawnLocation = playerSpawnLocation;
	}
}
