package com.graphics.world;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.main.Game;

/**
 * A basic unit of the world
 * 
 * @author Craig Ferris
 *
 */
public class Tile
{
	private Vector3f		position;
	private Vector2f		size;
	private Texture			texture;

	private RectangleBox	collider;
	private float			texCoordX	= -1;
	private float			texCoordY	= -1;
	private Vector2f		coordinate;

	private boolean			isDoor		= false;

	/**
	 * Creates a new tile at (0,0,0) with size (0,0) and no texture
	 */
	public Tile()
	{
		position = new Vector3f(0, 0, 0);
		size = new Vector2f(0, 0);
		collider = new RectangleBox(position, size);
	}

	/**
	 * Creates a new tile
	 * 
	 * @param position
	 *            The position of the tile
	 * @param texture
	 *            The texture of the tile
	 * @param texX
	 *            The number of textures in the animation (X direction)
	 * @param texY
	 *            The row in the spritesheet
	 */
	public Tile(Vector3f position, Texture texture, float texCoordX, float texCoordY)
	{
		this(position, texture);
		this.texCoordX = texCoordX;
		this.texCoordY = texCoordY;
		coordinate = new Vector2f(texCoordX, texCoordY);
	}

	/**
	 * Creates a new tile at the position passed in with size (0,0) and no texture
	 * 
	 * @param position
	 *            The position of the tile
	 */
	public Tile(Vector3f position)
	{
		this.position = position;
		texture = null;
		size = new Vector2f(0, 0);
		collider = new RectangleBox(position, size);
	}

	/**
	 * Creates a new tile at the specified position and texture
	 * 
	 * @param position
	 *            The position of the tile
	 * @param texture
	 *            The texture of the tile
	 */
	public Tile(Vector3f position, Texture texture)
	{
		this(position);
		this.texture = texture;
	}

	/**
	 * Creates a new tile with the specified texture at (0,0,0) with size (0,0)
	 * 
	 * @param texture
	 *            The texture of the tile
	 */
	public Tile(Texture texture)
	{
		this();
		this.texture = texture;
	}

	/**
	 * Creates a new tile at the specified position with the specified size and texture
	 * 
	 * @param position
	 *            The position of the tile
	 * @param size
	 *            The size of the tile
	 * @param texture
	 *            The texture of the tile
	 */
	public Tile(Vector3f position, Vector2f size, Texture texture)
	{
		this(position, texture);
		this.size = size;
		collider = new RectangleBox(position, size);
	}

	/**
	 * Creates a new tile
	 * 
	 * @param position
	 *            The position of the tile
	 * @param size
	 *            The size of the tile
	 * @param texture
	 *            The texture of the tile
	 * @param texCoordX
	 *            The number of animation frames in the animation in the x direction
	 * @param texCoordY
	 *            The row number of the animation frames
	 */
	public Tile(Vector3f position, Vector2f size, Texture texture, float texCoordX, float texCoordY)
	{
		this(position, texture, texCoordX, texCoordY);
		this.size = size;
	}

	/**
	 * Updates the tile
	 */
	public void update()
	{
		
	}

	/**
	 * Renders the tile to the screen
	 */
	public void render()
	{
		if (texCoordX != -1 && texCoordY != -1) // For outline
		{
			// Vector2f offset = new Vector2f(((float) (32.0f * texCoordX)) / 512.0f, (float) (32.0f * texCoordY) / 32.0f);
			Vector2f sizey = new Vector2f((float) (64.0f / 1024.0f), (float) (64.0f / 1024.0f));
			GFX.drawSpriteFromSpriteSheet(size.x, size.y, position.x, position.y, texture, coordinate, sizey);
		} else
		{
			GFX.drawEntireSprite(size.x, size.y, position.x, position.y, texture);
		}
	}

	/**
	 * Checks if a tile is colliding with an entity
	 * 
	 * @param entity
	 *            The entity to check for collisions with
	 * @return Returns true if the two are colliding and false otherwise
	 */
	public boolean isCollidingWithEntity2D(Entity entity)
	{
		return collider.isCollidingWithBox(entity.getCollider());
	}

	/**
	 * Returns true if the tile is colliding with the box
	 * 
	 * @param box
	 *            The box to check if colliding with
	 * @return Returns true if the tile is colliding with the box
	 */
	public boolean isCollidingWithBox(RectangleBox box)
	{
		return collider.isCollidingWithBox(box);
	}

	/**
	 * Returns the position of the tile
	 * 
	 * @return Returns the position of the tile
	 */
	public Vector3f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the tile
	 * 
	 * @param position
	 *            The new position of the tile
	 */
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	/**
	 * Returns the size of the tile
	 * 
	 * @return Returns the size of the tile
	 */
	public Vector2f getSize()
	{
		return size;
	}

	/**
	 * Sets the size of the tile
	 * 
	 * @param size
	 *            The size of the tile
	 */
	public void setSize(Vector2f size)
	{
		this.size = size;
	}

	/**
	 * Returns the texture of the tile
	 * 
	 * @return Returns the texture of the tile
	 */
	public Texture getTexture()
	{
		return texture;
	}

	/**
	 * Sets the texture of the tile
	 * 
	 * @param texture
	 *            The texture of the tile
	 */
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	/**
	 * Returns the rectangular collider of the tile
	 * 
	 * @return Returns the rectangular collider of the tile
	 */
	public RectangleBox getCollider()
	{
		return collider;
	}

	/**
	 * Sets the rectangular collider of the tile
	 * 
	 * @param collider
	 *            The rectangular collider
	 */
	public void setCollider(RectangleBox collider)
	{
		this.collider = collider;
	}

	/**
	 * Returns if this object is a door
	 * 
	 * @return Returns true if this object is a door
	 */
	public boolean isDoor()
	{
		return isDoor;
	}

	/**
	 * Sets this object to a door or not
	 * 
	 * @param isDoor
	 *            The value to set
	 */
	public void setDoor(boolean isDoor)
	{
		this.isDoor = isDoor;
	}

	public String toString()
	{
		return "Pos: " + position + " Size: " + size;
	}

}
