package com.graphics.world.enemys;

import com.graphics.world.RectangleBox;

/**
 * Stores information for pathfinding
 * 
 * @author Craig Ferris
 *
 */
public class Node
{
	public RectangleBox	collider;

	private Node		parent	= null;

	/**
	 * Creates a new node
	 * 
	 * @param collider
	 *            The collider for the node to correspond to
	 */
	public Node(RectangleBox collider)
	{
		this.collider = collider;
	}

	/**
	 * Returns the parent node
	 * 
	 * @return Returns the parent node
	 */
	public Node getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent node
	 * 
	 * @param parent
	 *            The parent node
	 */
	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	/**
	 * Returns a string representation of this object
	 * 
	 * @return Returns a string representation of this object
	 */
	public String toString()
	{
		return collider + "";
	}
}
