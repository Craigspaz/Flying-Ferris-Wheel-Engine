package com.graphics.world;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.main.Game;

/**
 * A texture that is rendered on screen
 * 
 * @author Craig Ferris
 *
 */
public class Tile
{
	private Vector3f	position;
	private Texture		texture;

	private int			ticksPerFrame	= 1;
	private int			tickCounter		= 0;
	private int			numberOfFrames;
	private int			row;
	private int			currentFrame	= 0;
	private boolean		flip			= false;
	private float		alpha			= 1;
	private Vector2f	spriteSize;

	/**
	 * Creates a new tile at (0,0,0) with spriteSize (0,0) and no texture
	 */
	public Tile()
	{
		position = new Vector3f(0, 0, 0);
		spriteSize = new Vector2f(0, 0);
		numberOfFrames = 1;
		row = 0;
	}

	/**
	 * Creates a new tile at the position passed in with spriteSize (0,0) and no texture
	 * 
	 * @param position
	 *            The position of the tile
	 */
	public Tile(Vector3f position)
	{
		this.position = position;
		texture = null;
		spriteSize = new Vector2f(0, 0);
	}

	/**
	 * Creates a new tile with the specified texture at (0,0,0) with spriteSize (0,0)
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
	 * Creates a new tile at the specified position with the specified spriteSize and texture
	 * 
	 * @param position
	 *            The position of the tile
	 * @param spriteSize
	 *            The spriteSize of the tile
	 * @param texture
	 *            The texture of the tile
	 */
	public Tile(Vector3f position, Vector2f spriteSize, Texture texture)
	{
		this(position, texture);
		this.spriteSize = spriteSize;
	}

	/**
	 * Creates a new tile
	 * 
	 * @param position
	 *            The position of the tile
	 * @param texture
	 *            The texture of the tile
	 * @param numberOfFrames
	 *            The number of textures in the animation (X direction)
	 * @param row
	 *            The row in the spritesheet
	 */
	public Tile(Vector3f position, Texture texture, int numberOfFrames, int row)
	{
		this(position, texture);
		this.numberOfFrames = numberOfFrames;
		this.row = row;
	}

	/**
	 * Creates a new tile
	 * 
	 * @param position
	 *            The position of the tile
	 * @param spriteSize
	 *            The spriteSize of the tile
	 * @param texture
	 *            The texture of the tile
	 * @param numberOfFrames
	 *            The number of animation frames in the animation in the x direction
	 * @param row
	 *            The row number of the animation frames
	 */
	public Tile(Vector3f position, Vector2f spriteSize, Texture texture, int numberOfFrames, int row)
	{
		this(position, texture, numberOfFrames, row);
		this.spriteSize = spriteSize;
	}

	/**
	 * Creates a new tile
	 * 
	 * @param position
	 * @param spriteSize
	 * @param texture
	 * @param numberOfFrames
	 * @param row
	 */
	public Tile(Vector3f position, Vector2f spriteSize, Texture texture, int numberOfFrames, int row, int ticksPerFrame)
	{
		this(position, spriteSize, texture, numberOfFrames, row);
		this.ticksPerFrame = ticksPerFrame;
	}

	/**
	 * Creates a new tile
	 * 
	 * @param position
	 * @param spriteSize
	 * @param texture
	 * @param numberOfFrames
	 * @param row
	 */
	public Tile(Vector3f position, Vector2f spriteSize, Texture texture, int numberOfFrames, int row, int ticksPerFrame, int startingFrame)
	{
		this(position, spriteSize, texture, numberOfFrames, row, ticksPerFrame);
		this.currentFrame = startingFrame % numberOfFrames;
	}

	/**
	 * Updates the tile
	 */
	public void update()
	{
		if (tickCounter >= ticksPerFrame)
		{
			currentFrame++;
			if (currentFrame >= numberOfFrames)
			{
				currentFrame = 0;
			}
			tickCounter = 0;
		} else
		{
			tickCounter++;
		}
	}

	/**
	 * sets how opaque the image is (float from 0 to 1)
	 * 
	 * @param alpha
	 */
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}

	public float getAlpha()
	{
		return alpha;
	}

	/**
	 * sets which frame is being shown
	 * 
	 */
	public void setFrame(int currentFrame)
	{
		this.currentFrame = currentFrame % numberOfFrames;
	}

	/**
	 * Renders the tile to the screen
	 */
	public void render()
	{
		Vector2f offset = new Vector2f(((float) (spriteSize.x * currentFrame)) / texture.getImageWidth(), (float) (spriteSize.y * row) / texture.getImageHeight());
		Vector2f sizey = new Vector2f((float) (spriteSize.x / texture.getImageWidth()), (float) (spriteSize.y / texture.getImageHeight()));
		if (flip)
		{
			GFX.drawSpriteFromSpriteSheetInverse(spriteSize.x, spriteSize.y, position.x, position.y, texture, offset, sizey, -1);
		} else
		{
			GFX.drawSpriteFromSpriteSheet(spriteSize.x, spriteSize.y, position.x, position.y, texture, offset, sizey, -1, alpha);
		}
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
	 * Returns the spriteSize of the tile
	 * 
	 * @return Returns the spriteSize of the tile
	 */
	public Vector2f getSize()
	{
		return spriteSize;
	}

	/**
	 * Sets the spriteSize of the tile
	 * 
	 * @param spriteSize
	 *            The spriteSize of the tile
	 */
	public void setSize(Vector2f spriteSize)
	{
		this.spriteSize = spriteSize;
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

	public String toString()
	{
		return "Pos: " + position + " spriteSize: " + spriteSize;
	}

	public Tile clone()
	{
		return new Tile(position, spriteSize, texture, numberOfFrames, row, ticksPerFrame, currentFrame);
	}

}
