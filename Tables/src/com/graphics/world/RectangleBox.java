package com.graphics.world;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Used as a collider for tiles and entities
 * 
 * @author Craig Ferris
 *
 */
public class RectangleBox
{
	private Vector3f	position;
	private Vector2f	size;

	/**
	 * Creates a new RectangleBox
	 * 
	 * @param position
	 *            The position of the top left corner of the box
	 * @param size
	 *            The width and height of the box
	 */
	public RectangleBox(Vector3f position, Vector2f size)
	{
		this.position = position;
		this.size = size;
	}

	/**
	 * Returns true if the current box overlaps with another box passed in
	 * 
	 * @param box
	 *            The box to check if colliding with
	 * @return Returns true if the current box overlaps with another box passed in and false otherwise
	 */
	public boolean isCollidingWithBox(RectangleBox box)
	{
		float x = position.x;
		float y = position.y;
		float sizex = size.x;
		float sizey = size.y;

		float xx = box.position.x;
		float yy = box.position.y;
		float sizexx = box.size.x;
		float sizeyy = box.size.y;

		// Checks if any of the corners of the current box object are in the box
		if (x > xx && x < xx + sizexx && y > yy && y < yy + sizeyy)
		{
			return true;
		} else if (x > xx && x < xx + sizexx && y + sizey > yy && y < yy + sizeyy)
		{
			return true;
		} else if (x + sizex > xx && x + sizex < xx + sizexx && y > yy && y < yy + sizeyy)
		{
			return true;
		} else if (x + sizex > xx && x + sizex < xx + sizexx && y + sizey > yy && y < yy + sizeyy)
		{
			return true;
		}
		// Checks if and y of the corners of the box are in the current box
		// object
		else if (xx > x && xx < x + sizex && yy > y && yy < y + sizey)
		{
			return true;
		} else if (xx > x && xx < x + sizex && yy + sizeyy > y && yy < y + sizey)
		{
			return true;
		} else if (xx + sizexx > x && xx + sizexx < x + sizex && yy > y && yy < y + sizey)
		{
			return true;
		} else if (xx + sizexx > x && xx + sizexx < x + sizex && yy + sizeyy > y && yy < y + sizey)
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Returns a clone of the current rectanglebox
	 * 
	 * @return Returns a clone of the current rectanglebox
	 */
	public RectangleBox clone()
	{
		return new RectangleBox(new Vector3f(position.x, position.y, position.z), new Vector2f(size.x, size.y));
	}

	/**
	 * Returns the position of the top left corner of the box
	 * 
	 * @return Returns the position of the top left corner of the box
	 */
	public Vector3f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the top left corner of the box
	 * 
	 * @param position
	 *            The position to set the top left corner of the box
	 */
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	/**
	 * Returns the size of the box
	 * 
	 * @return Returns the size of the box
	 */
	public Vector2f getSize()
	{
		return size;
	}

	/**
	 * Sets the size of the box
	 * 
	 * @param size
	 *            The size to set the box
	 */
	public void setSize(Vector2f size)
	{
		this.size = size;
	}

	/**
	 * Over rides the two string method
	 * 
	 * @return Returns a string representation of this object
	 */
	public String toString()
	{
		return "(" + position.x + " , " + position.y + " , " + position.z + ") (" + size.x + " , " + size.y + ")";
	}

}
