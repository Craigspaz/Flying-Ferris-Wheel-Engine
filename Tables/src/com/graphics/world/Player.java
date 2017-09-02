package com.graphics.world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.Textures;
import com.graphics.world.projectile.Projectile;
import com.graphics.world.util.Vertex;
import com.input.InputHandler;

/**
 * Handles the player input and player specifics
 * 
 * @author Craig Ferris
 *
 */
public class Player extends Entity
{
	private int				shootingDelay				= 5;
	private int				shootingCounter				= 0;
	private boolean			canShoot					= true;
	private float			bulletSpeed					= 13;
	private float			projectileVelocityX			= 0;
	private float			projectileVelocityY			= 0;
	private boolean			canGenerateSprintParticle	= true;
	private boolean			canGenerateSkidParticle		= false;
	private boolean			canJump						= true;	// determines the player's ability to jump via
																// keypresses and releases

	private InputHandler	handler;

	/**
	 * Creates a new player
	 * 
	 * @param position
	 *            The initial position of the player
	 * @param texture
	 *            The texture of the player
	 * @param scale
	 *            The size to draw the player
	 */
	public Player(Vector3f position, Texture texture, Vector2f scale, Vector2f sizeOfSpriteOnSheet, InputHandler handler)
	{
		super(position, texture, scale, sizeOfSpriteOnSheet);
		affectedByGravity = true;
		this.handler = handler;
	}

	/**
	 * Creates a new player
	 * 
	 * @param position
	 *            The initial position of the player
	 * @param texture
	 *            The texture of the player
	 * @param outlineTexture
	 *            The texture with the sprite outlines
	 * @param numberOfSprites
	 *            The number of sprites on the texture
	 * @param scale
	 *            The size at which to draw the player
	 */
	public Player(Vector3f position, Texture texture, Texture outlineTexture, int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet, InputHandler handler)
	{
		super(position, texture, outlineTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
		affectedByGravity = true;
		this.handler = handler;
	}

	/**
	 * Handles input from the user
	 * 
	 * @param tiles
	 *            The world collision boxes to check for collisions
	 */
	public void input(ArrayList<RectangleBox> tiles)
	{
		if (handler.sprint())
		{
			super.setSprinting(true);
			if (!isInAir && canGenerateSprintParticle && Math.abs(velocity.x) > 0)
			{
				if (left)
				{
					particles.add(new Particle(new Vector2f(position.x + getScale().x - 16, position.y + getScale().y - 16), new Vector2f(16, 16), Textures.particles, 12, 0, left, new Vector2f(16, 16), false));
				} else
				{
					particles.add(new Particle(new Vector2f(position.x, position.y + getScale().y - 16), new Vector2f(16, 16), Textures.particles, 12, 0, left, new Vector2f(16, 16), false));
				}
				canGenerateSprintParticle = false;
			}
		} else
		{
			super.setSprinting(false);
			canGenerateSprintParticle = true;
		}
		if (handler.right() == false && handler.left() == false)
		{
			stopMoving();
		}
		if (handler.right())
		{
			super.moveRight();
			if (velocity.x > 0)
			{
				if (Math.abs(velocity.x) > MAX_SPEED_X)
				{
					canGenerateSkidParticle = true;
				}
			} else if (!isInAir && Math.abs(velocity.x) < 0.8 && canGenerateSkidParticle == true)
			{
				particles.add(new Particle(new Vector2f(position.x + velocity.x, position.y + getScale().y - 16), new Vector2f(16, 16), Textures.particles, 12, 2, left, new Vector2f(16, 16), false));
				canGenerateSkidParticle = false;
			}
		}
		if (handler.left())
		{
			super.moveLeft();
			if (velocity.x < 0)
			{
				if (Math.abs(velocity.x) > MAX_SPEED_X)
				{
					canGenerateSkidParticle = true;
				}
			} else if (!isInAir && Math.abs(velocity.x) < 0.8 && canGenerateSkidParticle == true)
			{
				particles.add(new Particle(new Vector2f(position.x + getScale().x / 2 + velocity.x, position.y + getScale().y - 16), new Vector2f(16, 16), Textures.particles, 12, 2, left, new Vector2f(16, 16), false));
				canGenerateSkidParticle = false;
			}
		}
		// player can't jump again until they release the key, and canJump is only made true if it's
		// released and the jump count is still below max
		if (!handler.jump())
		{
			canJump = true;
		}
		// If the jump input key is pressed and canJump make the player jump
		if (handler.jump())
		{
			if (canJump)
			{
				super.jump();
				canJump = false;
				if (!isInAir)
				{
					particles.add(new Particle(new Vector2f(position.x + (getScale().x / 2) - 8, position.y + getScale().y - 16), new Vector2f(16, 16), Textures.particles, 12, 1, left, new Vector2f(16, 16), false));
				}
				// System.out.println("jump " + jumpCount);
			}

		}
		if (handler.shoot() == false)
		{
			if (shootingCounter > shootingDelay)

			{
				shootingCounter = 0;
				canShoot = true;
			} else
			{
				shootingCounter++;
			}
		}
		if (handler.shoot())
		{
			if (canShoot)
			{
				// if (handler.up())
				// {
				projectileVelocityY = (float) (-bulletSpeed / Math.sqrt(2));
				if (!left)
					projectileVelocityX = (float) (bulletSpeed / Math.sqrt(2));
				else
					projectileVelocityX = (float) (-bulletSpeed / Math.sqrt(2));
				// } else
				// {
				// projectileVelocityY = 0;
				// if (!left)
				// {
				// projectileVelocityX = bulletSpeed;
				// } else
				// {
				// projectileVelocityX = -bulletSpeed;
				// }
				// }
				
				
				
				//TODO: Kyle will fix
				/*Projectile fireball = new Projectile(new Vector3f(super.position.x + (super.getScale().x / 2f), super.position.y + (super.getScale().y / 2f), 0), Textures.fireball, new Vector2f(128, 16), 8, 0, new Vector2f(16, 16), new Vector2f(16, 16),
						new Vector3f(projectileVelocityX, projectileVelocityY, 0), velocity.x, velocity.y);
				fireball.setAffectedByGravity(true);
				projectiles.add(fireball);
				*/
				
				
				
				
				// projectiles.add(new Projectile(new Vector3f(super.position.x + (super.getScale().x / 2f) + displacex,
				// super.position.y + (super.getScale().y / 2f) + displacey, 0), Textures.playerLaser, new Vector2f(64,
				// 256), 0, 0, new Vector2f(64, 32), new Vector2f(64, 32), shootAngle,
				// bulletSpeed,
				// velocity.x, velocity.y));
				// System.out.println(shootAngle / 180 + "n");
			}
			canShoot = false;
		}

		if (velocity.x > 0)

		{
			if (Math.abs(super.velocity.x) > MAX_SPEED_X)
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 2;
			} else
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 1;
			}
			left = false;
		}
		if (velocity.x < 0)

		{
			if (Math.abs(super.velocity.x) > MAX_SPEED_X)
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 2;
			} else
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 1;
			}
			left = true;
		}
		if (velocity.x == 0 && velocity.y == 0)

		{
			super.numberOfSpritesX = 10;
			super.numberOfSpritesY = 0;
			// super.animateTime = 0;
			// super.animSpriteFrameX = 0;
			// super.animSpriteFrameY = 0;
		}

		if (getAnimSpriteFrameX() < 9 && flipping)
		{
			super.numberOfSpritesX = 10;
			super.numberOfSpritesY = 6;
		} else
		{
			flipping = false;
			// top of jump
			if (Math.abs(velocity.y) < 3 && isInAir)
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 4;
				// super.animateTime = 0;
				// super.animSpriteFrameX = 0;
				// super.animSpriteFrameY = 0;
				// System.out.println("Top of Jump");
			} else if (velocity.y < 0 && isInAir)
			// rising
			{
				super.numberOfSpritesX = 5;
				super.numberOfSpritesY = 3;
			} else if (velocity.y > 0 && isInAir)
			// falling
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 5;
				// super.animateTime = 0;
				// super.animSpriteFrameX = 0;
				// super.animSpriteFrameY = 0;
			}
		}

	}

	/**
	 * Updates the player
	 */
	public void update(ArrayList<RectangleBox> colliders, ArrayList<Vertex> vertices)
	{
		input(colliders);
		super.update(colliders, vertices);
		particles.add(new Particle(new Vector2f(position.x + 8, position.y + 10f), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 4, -2.5f), 16f, 4f, .5f, .5f));
		particles.add(new Particle(new Vector2f(position.x + 8, position.y + 10f), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 4, -2.5f), 16f, 4f, .75f, .5f));
		particles.add(new Particle(new Vector2f(position.x + 8, position.y + 10f), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 4, -2.5f), 16f, 4f, 1.0f, .5f));
		particles.add(new Particle(new Vector2f(position.x + 8, position.y + 6f), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 4, -2.5f), 16f, 4f, .5f, .5f));
		particles.add(new Particle(new Vector2f(position.x + 8, position.y + 6f), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 4, -2.5f), 16f, 4f, .75f, .5f));
		particles.add(new Particle(new Vector2f(position.x + 8, position.y + 6f), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 4, -2.5f), 16f, 4f, 1.0f, .5f));
	}

	/**
	 * Terminal stuff, only changed via Terminal
	 * 
	 * @return true if the player is invulnerable
	 */
	public boolean isImmune()
	{
		return immune;
	}

	/**
	 * Terminal stuff, only changed via Terminal
	 * 
	 * @param immune
	 */
	public void setImmune(boolean immune)
	{
		this.immune = immune;
	}
}
