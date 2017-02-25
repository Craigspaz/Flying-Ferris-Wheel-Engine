package com.graphics.world.projectile;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.world.Entity;
import com.graphics.world.RectangleBox;

/**
 * Basic Projectile class
 * 
 * @author Craig Ferris
 *
 */
public class Projectile extends Entity
{

	private float				angle;
	private float				speed;
	private static final float	DISTANCE_FROM_ORIGIN	= 16.0f;
	private float				offsetX					= 0;
	private float				offsetY					= 0;

	private int					damage					= 10;

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
	 */
	public Projectile(Vector3f position, Texture texture, Vector2f size, Vector2f scale, Vector2f sizeOfSpriteOnSheet, float angle, float speed)
	{
		super(position, texture, size, scale, sizeOfSpriteOnSheet);
		this.angle = angle;
		this.speed = speed;
	}

	/**
	 * Creates a new projectile
	 * 
	 * @param position
	 *            The position of the projectle
	 * @param texture
	 *            The texture of the projectle
	 * @param outlineTexture
	 *            The texture that contains the outlines
	 * @param sizeOfTexture
	 *            The size of the texture
	 * @param numberOfSpritesX
	 *            The number of sprite animation frames in the x direction to
	 *            animate
	 * @param numberOfSpritesY
	 *            The number of sprite animation frames in the y direction to
	 *            animate
	 * @param scale
	 *            The size at which to render the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the sprite sheet
	 * @param angle
	 *            The angle to rotate the projectile
	 * @param speed
	 *            The speed of the projectile
	 */
	public Projectile(Vector3f position, Texture texture, Vector2f sizeOfTexture, int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet, float angle, float speed, float playerXSpeed, float playerYSpeed)
	{
		super(new Vector3f(position.x - (scale.x / 2f), position.y - (scale.y / 2f), position.z), texture, texture, sizeOfTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
		this.angle = angle;
		this.speed = speed;
	}

	/**
	 * Updates the projectile
	 * 
	 * @param colliders
	 *            The colliders in the world to check for collisions with
	 */
	public void update(ArrayList<RectangleBox> colliders)
	{
		if (animateTime >= 10)
		{
			animSpriteFrameX++;
			if (animSpriteFrameX >= numberOfSpritesX)
			{
				animSpriteFrameX = 0;
			}
			animateTime = 0.0f;
		} else
		{
			animateTime += animateSpeed;
		}

		if (affectedByGravity)
		{
			velocity.y += GRAVITY;
		}

		if (velocity.y > speed)
		{
			velocity.y = MAX_SPEED_Y;
		} else if (velocity.y < -MAX_SPEED_Y)
		{
			velocity.y = -MAX_SPEED_Y;
		}
		for (RectangleBox t : colliders)
		{
			if (t.isCollidingWithBox(collider))
			{
				super.setDead(true);
			}
		}
		collider.setPosition(new Vector3f(velocity.x + position.x, velocity.y + position.y, position.z));
		position.x = collider.getPosition().x;
		position.y = collider.getPosition().y;

		if (angle == 180)
		{
			offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - velocity.x) / super.getSizeOfSpriteOnSheet().x);
		} else
		{
			offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - Math.abs(velocity.x)) / super.getSizeOfSpriteOnSheet().x);
		}
		if (angle == 90)
		{
			offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - Math.abs(velocity.y)) / super.getSizeOfSpriteOnSheet().x);
		} else if (angle == 270)
		{
			offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - velocity.y) / super.getSizeOfSpriteOnSheet().x);
		}
		move();
	}

	/**
	 * Moves the projectile
	 */
	public void move()
	{
		// velocity.x = (float) Math.acos(angle % 90 * (Math.PI / 180)) * speed;
		// velocity.y = -(float) Math.asin(angle % 90 * (Math.PI / 180)) *
		// speed;
		// if (angle > 180)
		// {
		// velocity.y = -velocity.y;
		// }
		// if (angle > 90 && angle < 270)
		// {
		// velocity.x = -velocity.x;
		// }
		// if (angle == 90)
		// {
		// velocity.y = -speed;
		// velocity.x = 0;
		// } else if (angle == 270)
		// {
		// velocity.y = speed;
		// velocity.x = 0;
		// }
		switch ((int) angle)
		{
			case 0:
				velocity.x = speed;
				velocity.y = 0;
				break;
			case 45:
				velocity.x = speed / (float) Math.sqrt(2);
				velocity.y = -speed / (float) Math.sqrt(2);
				break;
			case 90:
				velocity.x = 0;
				velocity.y = -speed;
				break;
			case 135:
				velocity.x = -speed / (float) Math.sqrt(2);
				velocity.y = -speed / (float) Math.sqrt(2);
				break;
			case 180:
				velocity.x = -speed;
				velocity.y = 0;
				break;
			case 225:
				velocity.x = -speed / (float) Math.sqrt(2);
				velocity.y = speed / (float) Math.sqrt(2);
				break;
			case 270:
				velocity.x = 0;
				velocity.y = speed;
				break;
			case 315:
				velocity.x = speed / (float) Math.sqrt(2);
				velocity.y = speed / (float) Math.sqrt(2);
				break;
		}

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

	/**
	 * Returns the amount of damage it does
	 * 
	 * @return Returns the amount of damage it does
	 */
	public int getDamage()
	{
		return damage;
	}

	/**
	 * Sets the amount of damage it does
	 * 
	 * @param damage
	 *            The amount of damage it does
	 */
	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	/**
	 * Renders the projectile
	 */
	public void render()
	{
		GFX.drawSpriteFromSpriteSheetAtAngle(super.getScale().x, super.getScale().y, super.position.x, super.position.y, super.getTexture(), new Vector2f(-offsetX, (float) (super.getSizeOfSpriteOnSheet().y * numberOfSpritesY) / getSizeOfSpriteSheet().y), new Vector2f(1f, (float) 1 / 8), angle);
	}

}
