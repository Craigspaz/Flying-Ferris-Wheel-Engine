package com.graphics.world;

import java.util.ArrayList;

public class Level 
{
	private String name;
	private ArrayList<Tile> tiles;
	private ArrayList<RectangleBox> colliders;
	
	public Level(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public ArrayList<RectangleBox> getColliders() {
		return colliders;
	}

	public void setColliders(ArrayList<RectangleBox> colliders) {
		this.colliders = colliders;
	}
	
	
}
