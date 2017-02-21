package com.graphics.world.projectile;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.world.Entity;
import com.graphics.world.EntityType;
import com.graphics.world.RectangleBox;

/**
 * Basic Projectile class
 * 
 * @author Craig Ferris
 *
 */
public class Projectile extends Entity
{

	private float angle;
	private float speed;
	private float originSpeedX;
	private float originSpeedY;

	/**
	 * Creates a new projectile
	 * 
	 * @param position
	 *            The position of the projectile
	 * @param texture
	 *            The texture of the projectile
	 * @param size
	 *            The size of the sprite on the texture
	 * @param scale
	 *            The size at which to draw the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the spritesheet
	 * @param angle
	 *            The angle to rotate the projectile
	 * @param speed
	 *            The speed of the projectile
	 * @param playerXSpeed
	 *            The speed of the player in the X direction
	 * @param playerYSpeed
	 *            The speed of the player in the Y direction
	 */
	public Projectile(Vector3f position, Texture texture, Vector2f size, Vector2f scale, Vector2f sizeOfSpriteOnSheet,
			float angle, float speed, float playerXSpeed, float playerYSpeed)
	{
		super(position, texture, size, scale, sizeOfSpriteOnSheet);
		this.angle = angle;
		this.speed = speed;
		this.originSpeedX = playerXSpeed;
		this.originSpeedY = playerYSpeed;
		type = EntityType.PROJECTILE;
	}

	/**
	 * Creates a new projectile
	 * 
	 * @param position
	 *            The position of the projectle
	 * @param texture
	 *            The texture of the projectle
	 * @param sizeOfTexture
	 *            The size of the texture
	 * @param numberOfSpritesX
	 *            The number of sprite animation frames in the x direction to animate
	 * @param numberOfSpritesY
	 *            The number of sprite animation frames in the y direction to animate
	 * @param scale
	 *            The size at which to render the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the sprite sheet
	 * @param angle
	 *            The angle to rotate the projectile
	 * @param speed
	 *            The speed of the projectile
	 * @param playerXSpeed
	 *            The speed of the player in the X direction
	 * @param playerYSpeed
	 *            The speed of the player in the Y direction
	 */
	public Projectile(Vector3f position, Texture texture, Vector2f sizeOfTexture, int numberOfSpritesX,
			int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet, float angle, float speed,
			float playerXSpeed, float playerYSpeed)
	{
		super(position, texture, sizeOfTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
		this.angle = angle;
		this.speed = speed;
		this.originSpeedX = playerXSpeed;
		this.originSpeedY = playerYSpeed;
		type = EntityType.PROJECTILE;
	}

	/**
	 * Updates the projectle
	 */
	public void update(ArrayList<RectangleBox> colliders)
	{
		super.update(colliders);
		move();
	}

	/**
	 * Moves the projectile up and right
	 */
	public void moveUpRight()
	{
		velocity.x += walkSpeed;
		velocity.y -= walkSpeed;
		if (velocity.x > MAX_SPEED_X)
		{
			velocity.x = MAX_SPEED_X;
		}
		if (velocity.y < -MAX_SPEED_X)
		{
			velocity.y = -MAX_SPEED_X;
		}
	}

	/**
	 * Moves the projctile down and right
	 */
	public void moveDownRight()
	{
		velocity.x += walkSpeed;
		velocity.y += walkSpeed;
		if (velocity.x > MAX_SPEED_X)
		{
			velocity.x = MAX_SPEED_X;
		}
		if (velocity.y > MAX_SPEED_X)
		{
			velocity.y = MAX_SPEED_X;
		}
	}

	/**
	 * Moves the projctile up and left
	 */
	public void moveUpLeft()
	{
		velocity.x -= walkSpeed;
		velocity.y -= walkSpeed;
		if (velocity.x < -MAX_SPEED_X)
		{
			velocity.x = -MAX_SPEED_X;
		}
		if (velocity.y < -MAX_SPEED_X)
		{
			velocity.y = -MAX_SPEED_X;
		}
	}

	/**
	 * Moves the projectile down and left
	 */
	public void moveDownLeft()
	{
		velocity.x -= walkSpeed;
		velocity.y += walkSpeed;
		if (velocity.x < -MAX_SPEED_X)
		{
			velocity.x = -MAX_SPEED_X;
		}
		if (velocity.y > MAX_SPEED_X)
		{
			velocity.y = MAX_SPEED_X;
		}
	}

	/**
	 * Moves the projectile
	 */
	public void move()
	{
		/*
		 * craig what the fuck
		 * 
		 * 
		 * if(angle == 0) { super.moveRight(); } else if(angle == 45) { this.moveUpRight(); } else if(angle == 90) {
		 * super.moveUp(); } else if(angle == 135) { this.moveUpLeft(); } else if(angle == 180) { super.moveLeft(); }
		 * else if(angle == 225) { this.moveDownLeft(); } else if(angle == 270) { super.moveDown(); } else if(angle ==
		 * 315) { this.moveDownRight(); }
		 */
		velocity.x = (float) Math.acos(angle % 90 * (Math.PI / 180)) * speed;
		velocity.y = -(float) Math.asin(angle % 90 * (Math.PI / 180)) * speed;
		if (angle > 180)
		{
			velocity.y = -velocity.y;
		}
		if (angle > 90 && angle < 270)
		{
			velocity.x = -velocity.x;
		}
		if (angle == 90)
		{
			velocity.y = -speed;
			velocity.x = 0;
		} else if (angle == 270)
		{
			velocity.y = speed;
			velocity.x = 0;
		}
		// velocity.x += originSpeedX;
		// velocity.y += originSpeedY;

	}

	/**
	 * Returns the angle of rotation
	 * 
	 * @return Returns the angle of rotation
	 */
	public float getAngle()
	{
		return angle;
	}

	/**
	 * Sets the angle of rotation
	 * 
	 * @param angle
	 *            The angle of rotation
	 */
	public void setAngle(float angle)
	{
		this.angle = angle;
	}

}
