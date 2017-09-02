package com.graphics.world.projectile;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Entity;
import com.graphics.world.Particle;
import com.graphics.world.RectangleBox;

/**
 * Basic Projectile class
 * 
 * @author Craig Ferris
 *
 */
public class Projectile extends Entity
{
	// private float offsetX = 0;

	private int	damage	= 10;
	private int	lifespan;		// the total time projectile remains
	private int	lifetime;		// the time elapsed

	/**
	 * Creates a new projectile
	 * 
	 * @param position
	 *            The position of the projectile
	 * @param texture
	 *            The texture of the projectile
	 * @param sizeOfSpriteSheet
	 *            The size of the spritesheet
	 * @param scale
	 *            The size at which to draw the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the spritesheet
	 * @param velocity
	 *            The direction and speed of the projectile
	 */
	public Projectile(Vector3f position, Texture texture, Vector2f sizeOfSpriteSheet, Vector2f scale, Vector2f sizeOfSpriteOnSheet, Vector3f velocity)
	{
		super(position, texture, sizeOfSpriteSheet, scale, sizeOfSpriteOnSheet);
		lifespan = 30;
		lifetime = 0;
	}

	/**
	 * Creates a new projectile
	 * 
	 * @param position
	 *            The position of the projectile
	 * @param texture
	 *            The texture of the projectile
	 * @param sizeOfTexture
	 *            The size of the texture
	 * @param numberOfSpritesX
	 *            The number of sprite animation frames
	 * @param numberOfSpritesY
	 *            The specific row of frames to animate
	 * @param scale
	 *            The size at which to render the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the sprite sheet
	 * @param velocity
	 *            the direction and speed of the projectile
	 */
	public Projectile(Vector3f position, Texture texture, Vector2f sizeOfTexture, int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet, Vector3f velocity, float playerXSpeed, float playerYSpeed)
	{
		super(new Vector3f(position.x - (scale.x / 2f), position.y - (scale.y / 2f), position.z), texture, texture, sizeOfTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
		super.setVelocity(velocity);
		lifespan = 30;
		lifetime = 0;
	}

	/**
	 * Updates the projectile
	 * 
	 * @param colliders
	 *            The colliders in the world to check for collisions with
	 */
	public void update(ArrayList<RectangleBox> colliders)
	{
		if (animateTime >= 2)
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
		if (velocity.y > MAX_SPEED_Y)
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
				for (int i = 0; i < 18; i++)// small fire
				{
					particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 8, 5, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(velocity.x / 6, velocity.y / 6),
							5f, 5f, 10f, 10f));// small fire
				}
				for (int i = 0; i < 10; i++)// big fire
				{
					particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false,
							new Vector2f(velocity.x / 6, velocity.y / 6), 10f, 10f, 3f, 3f));// big fire
				}
				for (int i = 0; i < 10; i++)// sparks
				{
					particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false,
							new Vector2f(velocity.x / 6, velocity.y / 6), 16f, 16f, 3f, 3f));// big fire
				}
				super.setDead(true);
			}
		}
		lifetime++;
		if (lifetime >= lifespan)
		{
			for (int i = 0; i < 18; i++)// small fire
			{
				particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 8, 5, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(velocity.x / 4, velocity.y / 4), 5f,
						5f, 10f, 10f));// small fire
			}
			for (int i = 0; i < 10; i++)// big fire
			{
				particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(velocity.x / 4, velocity.y / 4),
						10f, 10f, 3f, 3f));// big fire
			}
			for (int i = 0; i < 10; i++)// sparks
			{
				particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(velocity.x / 4, velocity.y / 4),
						16f, 16f, 3f, 3f));// big fire
			}
			super.setDead(true);
		}
		collider.setPosition(new Vector3f(velocity.x + position.x, velocity.y + position.y, position.z));
		position.x = collider.getPosition().x;
		position.y = collider.getPosition().y;

		// if (angle == 180)
		// {
		// offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - velocity.x) / super.getSizeOfSpriteOnSheet().x);
		// } else
		// {
		// offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - Math.abs(velocity.x)) /
		// super.getSizeOfSpriteOnSheet().x);
		// }
		// if (angle == 90)
		// {
		// offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - Math.abs(velocity.y)) /
		// super.getSizeOfSpriteOnSheet().x);
		// } else if (angle == 270)
		// {
		// offsetX += (float) ((super.getSizeOfSpriteOnSheet().x - velocity.y) / super.getSizeOfSpriteOnSheet().x);
		// }

		// the smoke and fire effects generated by the fireball
		// particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), new Vector2f(256, 128), false, new Vector2f(0, 0), 5f, 5f, .5f, .5f));//smoke
		// particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), new Vector2f(256, 128), false, new Vector2f(0, 0), 5f, 5f, .5f, .5f));//smoke
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(0, 0), 5f, 5f, .5f, .5f));// big fire
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(velocity.x / 4, velocity.y / 4), 5f, 5f,
				.5f, .5f));// big fire
		particles.add(
				new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 8, 5, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(velocity.x / 8, velocity.y / 8), 5f, 5f, 1f, 1f));// small
																																																																											// fire
		// particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 6, true, new Vector2f(16, 16), new Vector2f(256, 128), false, new Vector2f(velocity.x / 4, velocity.y / 4), 5f, 5f, .5f, .5f));
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(0, 0), 5f, 5f, 1.5f, 1.5f));// sparks
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), new Vector2f(Textures.particles.getImageWidth(), Textures.particles.getImageHeight()), false, new Vector2f(0, 0), 5f, 5f, 1f, 1f));// sparks
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
		if (numberOfSpritesX == 1)
		{
			GFX.drawEntireSprite(super.getScale().x, super.getScale().y, position.x, position.y, super.getTexture());
		} else
		{
			Vector2f offset = new Vector2f(((float) (super.getSizeOfSpriteOnSheet().x * animSpriteFrameX)) / super.getSizeOfSpriteSheet().x, (float) (super.getSizeOfSpriteOnSheet().y * numberOfSpritesY) / super.getSizeOfSpriteSheet().y);
			Vector2f sizey = new Vector2f((float) (super.getSizeOfSpriteOnSheet().x / super.getSizeOfSpriteSheet().x), (float) (super.getSizeOfSpriteOnSheet().y / super.getSizeOfSpriteSheet().y));
			if (velocity.x < 0 || left)
			{
				GFX.drawSpriteFromSpriteSheetInverse(super.getScale().x, super.getScale().y, position.x, position.y, super.getTexture(), offset, sizey);
			} else
			{
				GFX.drawSpriteFromSpriteSheet(super.getScale().x, super.getScale().y, position.x, position.y, super.getTexture(), offset, sizey);
			}
		}
		// GFX.drawSpriteFromSpriteSheetAtAngle(super.getScale().x, super.getScale().y, super.position.x,
		// super.position.y, super.getTexture(), new Vector2f(-offsetX, (float) (super.getSizeOfSpriteOnSheet().y *
		// numberOfSpritesY) / getSizeOfSpriteSheet().y), new Vector2f(1f, (float) 1 / 8), angle);
	}

}
