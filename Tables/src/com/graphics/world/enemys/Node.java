package com.graphics.world.enemys;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.world.RectangleBox;

public class Node
{
	
	private int ticks;
	private int maxHeight;
	private int maxDistance;
	
	public RectangleBox collider;
	
	private Node parent = null;
	
	public Node(RectangleBox collider)
	{
		this.collider = collider;
	}
	
	public Node(RectangleBox collider,int ticks, int maxDistance, int maxHeight)
	{
		this(collider);
		this.ticks = ticks;
		this.maxDistance = maxDistance;
		this.maxHeight = maxHeight;
	}

	public void setTicks(int ticks)
	{
		this.ticks = ticks;
	}
	
	public void setMaxHeight(int maxHeight)
	{
		this.maxHeight = maxHeight;
	}
	
	public void setMaxDistance(int maxDistance)
	{
		this.maxDistance = maxDistance;
	}


	public Node getParent()
	{
		return parent;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public String toString()
	{
		return collider + "";
	}
}
