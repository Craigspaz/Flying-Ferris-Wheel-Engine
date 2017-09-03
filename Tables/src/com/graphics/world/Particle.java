package com.graphics.world;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.main.Game;

/**
 * Handles small particles
 * 
 * @author Craig Ferris
 *
 */
public class Particle
{
	private Vector2f	position;
	private Vector2f	size;
	private Vector2f	velocity;
	private Texture		spriteSheet;
	private int			ticksPerFrame	= 0;
	private int			tickCounter		= 0;
	private int			numFramesX;
	private int			numFramesY;
	private int			animFrameX		= -1;
	private int			animFrameY;
	private boolean		flip;
	private Vector2f	sizeOfSpriteOnSpriteSheet;
	private Vector2f	sizeOfSpriteSheet;
	private boolean		isDead			= false;
	private boolean		loop;

	/**
	 * Creates a new particle
	 * 
	 * @param position
	 *            The position of the particle
	 * @param size
	 *            The size of the particle
	 * @param spriteSheet
	 *            The spritesheet
	 * @param numFramesX
	 *            The number of animation frames X
	 * @param numFramesY
	 *            The row in the spritesheet
	 * @param flip
	 *            Should the texture be flipped over y axis
	 * @param sizeOfSpriteOnSpriteSheet
	 *            The size of the sprite on the sprite sheet
	 * @param loop
	 *            Should the animation loop
	 */
	public Particle(Vector2f position, Vector2f size, Texture spriteSheet, int numFramesX, int numFramesY, boolean flip, Vector2f sizeOfSpriteOnSpriteSheet, boolean loop)
	{
		this.position = position;
		this.size = size;
		this.spriteSheet = spriteSheet;
		this.sizeOfSpriteOnSpriteSheet = sizeOfSpriteOnSpriteSheet;
		this.sizeOfSpriteSheet = new Vector2f(spriteSheet.getImageWidth(), spriteSheet.getImageHeight());
		this.numFramesX = numFramesX;
		this.numFramesY = numFramesY;
		velocity = new Vector2f(0, 0);
		animFrameX = -1;
		animFrameY = 0;
		this.flip = flip;
		this.loop = loop;
	}

	/**
	 * Creates a new particle
	 * 
	 * @param position
	 *            The position of the particle
	 * @param size
	 *            The size of the particle
	 * @param spriteSheet
	 *            The spritesheet
	 * @param numFramesX
	 *            The number of animation frames X
	 * @param numFramesY
	 *            The row in the spritesheet
	 * @param flip
	 *            Should the texture be flipped over y axis
	 * @param sizeOfSpriteOnSpriteSheet
	 *            The size of the sprite on the sprite sheet
	 * @param loop
	 *            Should the animation loop
	 * @param velocity
	 *            the initial velocity of the particle
	 * @param positionScatterX
	 *            the x radius to disperse the particle spawns
	 * @param positionScatterY
	 *            the y radius to disperse the particle spawns
	 * @param velocityMod
	 *            the randomness of the velocity
	 */
	public Particle(Vector2f position, Vector2f size, Texture spriteSheet, int numFramesX, int numFramesY, boolean flip, Vector2f sizeOfSpriteOnSpriteSheet, boolean loop, Vector2f velocity, float positionScatterX, float positionScatterY, float velocityModX, float velocityModY)
	{
		// offsetAmount is the particle's random offset generation from the center
		float offset_x = (new Random().nextFloat() - 0.5f) * positionScatterX;
		float offset_y = (new Random().nextFloat() - 0.5f) * positionScatterY;
		// change scatterAmount to 5 for a cool "underwater bubble scatter" effect
		float randvelocity_x = (new Random().nextFloat() - 0.5f) * velocityModX;
		float randvelocity_y = (new Random().nextFloat() - 0.5f) * velocityModY;
		this.position = new Vector2f(position.x + offset_x * Game.SCALE, position.y + offset_y * Game.SCALE);
		this.size = size;
		this.spriteSheet = spriteSheet;
		this.sizeOfSpriteOnSpriteSheet = sizeOfSpriteOnSpriteSheet;
		this.sizeOfSpriteSheet = new Vector2f(spriteSheet.getImageWidth(), spriteSheet.getImageHeight());
		this.numFramesX = numFramesX;
		this.numFramesY = numFramesY;
		this.velocity = new Vector2f((velocity.x + randvelocity_x) * Game.SCALE, (velocity.y + randvelocity_y) * Game.SCALE);
		animFrameX = -1;
		animFrameY = 0;
		this.flip = flip;
		this.loop = loop;
	}

	/**
	 * Updates the particle
	 */
	public void update()
	{
		if (tickCounter >= ticksPerFrame)
		{
			if (animFrameX >= numFramesX)
			{
				animFrameX = 0;
				if (!loop)
				{
					isDead = true;
				}
			}
			tickCounter = 0;
			animFrameX++;
		} else
		{
			tickCounter++;
		}
		this.setPosition(new Vector2f(getPosition().x + getVelocity().x, getPosition().y + getVelocity().y));
	}

	/**
	 * Renders the particle
	 */
	public void render()
	{
		Vector2f offset = new Vector2f(((float) (sizeOfSpriteOnSpriteSheet.x * animFrameX)) / sizeOfSpriteSheet.x, (float) (sizeOfSpriteOnSpriteSheet.y * numFramesY) / sizeOfSpriteSheet.y);
		Vector2f sizey = new Vector2f((float) (sizeOfSpriteOnSpriteSheet.x / sizeOfSpriteSheet.x), (float) (sizeOfSpriteOnSpriteSheet.y / sizeOfSpriteSheet.y));
		if (flip)
		{
			GFX.drawSpriteFromSpriteSheetInverse(size.x * Game.SCALE, size.y * Game.SCALE, position.x, position.y, spriteSheet, offset, sizey);
		} else
		{
			GFX.drawSpriteFromSpriteSheet(size.x * Game.SCALE, size.y * Game.SCALE, position.x, position.y, spriteSheet, offset, sizey);
		}
	}

	/**
	 * Returns the position of the particle
	 * 
	 * @return Returns the position of the particle
	 */
	public Vector2f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the particle
	 * 
	 * @param position
	 *            The position of the particle
	 */
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}

	/**
	 * Returns the size of the particle
	 * 
	 * @return Returns the size of the particle
	 */
	public Vector2f getSize()
	{
		return size;
	}

	/**
	 * Sets the size of the particle
	 * 
	 * @param size
	 *            The size of the particle
	 */
	public void setSize(Vector2f size)
	{
		this.size = size;
	}

	/**
	 * Returns the velocity of the particle
	 * 
	 * @return Returns the velocity of the particle
	 */
	public Vector2f getVelocity()
	{
		return velocity;
	}

	/**
	 * Sets the velocity of the particle
	 * 
	 * @param velocity
	 *            The velocity of the particle
	 */
	public void setVelocity(Vector2f velocity)
	{
		this.velocity = velocity;
	}

	/**
	 * Returns the spritesheet
	 * 
	 * @return Returns the spritesheet
	 */
	public Texture getSpriteSheet()
	{
		return spriteSheet;
	}

	/**
	 * Sets the spritesheet
	 * 
	 * @param spriteSheet
	 *            The spritesheet
	 */
	public void setSpriteSheet(Texture spriteSheet)
	{
		this.spriteSheet = spriteSheet;
	}

	/**
	 * Returns the number of animation frames X
	 * 
	 * @return Returns the number of animation frames X
	 */
	public int getNumFramesX()
	{
		return numFramesX;
	}

	/**
	 * Sets the number of animation frames X
	 * 
	 * @param numFramesX
	 *            The number of animation frames X
	 */
	public void setNumFramesX(int numFramesX)
	{
		this.numFramesX = numFramesX;
	}

	/**
	 * Returns the row in the sprite sheet
	 * 
	 * @return Returns the row in the sprite sheet
	 */
	public int getNumFramesY()
	{
		return numFramesY;
	}

	/**
	 * Sets the row in the sprite sheet
	 * 
	 * @param numFramesY
	 *            The row in the spritesheet
	 */
	public void setNumFramesY(int numFramesY)
	{
		this.numFramesY = numFramesY;
	}

	/**
	 * Returns the animation frameX
	 * 
	 * @return REturns the animation frameX
	 */
	public int getAnimFrameX()
	{
		return animFrameX;
	}

	/**
	 * Sets the animation frame X
	 * 
	 * @param animFrameX
	 *            The animation frame X
	 */
	public void setAnimFrameX(int animFrameX)
	{
		this.animFrameX = animFrameX;
	}

	/**
	 * Returns the animation frame Y
	 * 
	 * @return Returns the anmation frame Y
	 */
	public int getAnimFrameY()
	{
		return animFrameY;
	}

	/**
	 * Sets the animation frame Y
	 * 
	 * @param animFrameY
	 *            The animation frame Y
	 */
	public void setAnimFrameY(int animFrameY)
	{
		this.animFrameY = animFrameY;
	}

	/**
	 * Returns if the texture should be flipped over y axis
	 * 
	 * @return Returns if the texture should be flipped over y axis
	 */
	public boolean isFlip()
	{
		return flip;
	}

	/**
	 * Sets if the texture should be flipped over y axis
	 * 
	 * @param flip
	 *            The value of flip
	 */
	public void setFlip(boolean flip)
	{
		this.flip = flip;
	}

	/**
	 * Returns the size of the sprite on the sprite sheet
	 * 
	 * @return Returne the size of the sprite on the sprite sheet
	 */
	public Vector2f getSizeOfSpriteOnSpriteSheet()
	{
		return sizeOfSpriteOnSpriteSheet;
	}

	/**
	 * Sets the size of the sprite on the spritesheet
	 * 
	 * @param sizeOfSpriteOnSpriteSheet
	 *            The size of the sprite on the sprite sheet
	 */
	public void setSizeOfSpriteOnSpriteSheet(Vector2f sizeOfSpriteOnSpriteSheet)
	{
		this.sizeOfSpriteOnSpriteSheet = sizeOfSpriteOnSpriteSheet;
	}

	/**
	 * Returns the size of the spritesheet
	 * 
	 * @return Returns the size of the spritesheet
	 */
	public Vector2f getSizeOfSpriteSheet()
	{
		return sizeOfSpriteSheet;
	}

	/**
	 * Sets the size of the sprite sheet
	 * 
	 * @param sizeOfSpriteSheet
	 *            The size of the sprite sheet
	 */
	public void setSizeOfSpriteSheet(Vector2f sizeOfSpriteSheet)
	{
		this.sizeOfSpriteSheet = sizeOfSpriteSheet;
	}

	/**
	 * Returns if the particle should be deleted
	 * 
	 * @return Returns if the particle should be deleted
	 */
	public boolean isDead()
	{
		return isDead;
	}

	/**
	 * Sets if the particle should be deleted
	 * 
	 * @param isDead
	 *            Should the particle be deleted
	 */
	public void setDead(boolean isDead)
	{
		this.isDead = isDead;
	}

	/**
	 * Returns if the animation should continue after making it to the end of the animation
	 * 
	 * @return Returns if the animation should continue after making it to the end of the animation
	 */
	public boolean isLoop()
	{
		return loop;
	}

	/**
	 * Sets if the animation should continue after making it to the end of the animation
	 * 
	 * @param loop
	 *            The value of loop
	 */
	public void setLoop(boolean loop)
	{
		this.loop = loop;
	}

}
