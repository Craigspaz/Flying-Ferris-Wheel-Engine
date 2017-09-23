package com.graphics.world;

import java.util.ArrayList;
import java.util.Random;

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
	private float			bulletSpeed					= 13f;
	private float			projectileVelocityX			= 0;
	private float			projectileVelocityY			= 0;
	private boolean			canGenerateSprintParticle	= true;
	private boolean			canGenerateSkidParticle		= false;
	private boolean			canJump						= true;	// prevents player from jumping by holding key down

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
	public Player(Vector3f position, Texture texture, Vector2f sizeOfSpriteOnSheet, InputHandler handler)
	{
		super(position, texture, sizeOfSpriteOnSheet);
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
	 *            The texture of the outlines
	 * @param numberOfFrames
	 *            The number of frames to animate through
	 * @param row
	 *            the row of frames on the spritesheet
	 * @param spriteSize
	 *            the dimensions of each sprite
	 * @param handler
	 *            the InputHandler
	 * 
	 */
	public Player(Vector3f position, Texture texture, Texture outlineTexture, int numberOfFrames, int row, Vector2f spriteSize, InputHandler handler)
	{
		super(position, texture, outlineTexture, numberOfFrames, row, spriteSize);
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
					particles.add(new Particle(new Vector2f(position.x + (getSpriteSize().x) / 2, position.y + (getSpriteSize().y) / 2), new Vector2f(16, 16), Textures.particles, 12, 0, left, new Vector2f(16, 16), 0));
				} else
				{
					particles.add(new Particle(new Vector2f(position.x, position.y + (getSpriteSize().y) / 2), new Vector2f(16, 16), Textures.particles, 12, 0, left, new Vector2f(16, 16), 0));
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
			} else if (!isInAir && Math.abs(velocity.x) < (0.8) && canGenerateSkidParticle == true)
			{
				particles.add(new Particle(new Vector2f(position.x + velocity.x, position.y + (getSpriteSize().y) / 2), new Vector2f(16, 16), Textures.particles, 12, 2, left, new Vector2f(16, 16), 0));
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
			} else if (!isInAir && Math.abs(velocity.x) < (0.8) && canGenerateSkidParticle == true)
			{
				particles.add(new Particle(new Vector2f(position.x + (getSpriteSize().x) / 2 + velocity.x, position.y + (getSpriteSize().y) / 2), new Vector2f(16, 16), Textures.particles, 12, 2, left, new Vector2f(16, 16), 0));
				canGenerateSkidParticle = false;
			}
		}

		// player can't jump again until they release the key
		if (!handler.jump())
		{
			canJump = true;
			if (!isInAir)
			{
				airJumpCount = 0;// gives back all air jumps
			}
		}

		// If the jump input key is pressed and canJump, make the player jump based on jumpcounts
		if (handler.jump())
		{
			if (canJump)
			{
				if (!isInAir)
				{
					super.jump();
					canJump = false;
					particles.add(new Particle(new Vector2f(position.x + (getSpriteSize().x / 2) - (8), position.y + (getSpriteSize().y) / 2), new Vector2f(16, 16), Textures.particles, 12, 1, left, new Vector2f(16, 16), 0, 1));
				} else
				{
					if (airJumpCount < MAX_AIR_JUMPS)
					{
						airJumpCount++;
						super.jump();
						canJump = false;
					}
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

				Projectile fireball = new Projectile(new Vector3f(super.position.x + (super.getSpriteSize().x / 2f) + (velocity.x), super.position.y + (super.getSpriteSize().y / 2f) + (velocity.y), 0), Textures.fireball, 6, 0, new Vector2f(16, 16),
						new Vector2f(projectileVelocityX, projectileVelocityY));
				fireball.setAffectedByGravity(true);
				projectiles.add(fireball);

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
				super.numberOfFrames = 10;
				super.row = 2;
			} else
			{
				super.numberOfFrames = 10;
				super.row = 1;
			}
			left = false;
		}
		if (velocity.x < 0)

		{
			if (Math.abs(super.velocity.x) > MAX_SPEED_X)
			{
				super.numberOfFrames = 10;
				super.row = 2;
			} else
			{
				super.numberOfFrames = 10;
				super.row = 1;
			}
			left = true;
		}
		if (velocity.x == 0 && velocity.y == 0)

		{
			super.numberOfFrames = 10;
			super.row = 0;
			// super.animateTime = 0;
			// super.animSpriteFrameX = 0;
			// super.animSpriteFrameY = 0;
		}

		if (getAnimSpriteFrameX() < 9 && flipping)
		{
			super.numberOfFrames = 10;
			super.row = 6;
		} else
		{
			flipping = false;
			// top of jump
			if (Math.abs(velocity.y) < 3 && isInAir)
			{
				super.numberOfFrames = 10;
				super.row = 4;
				// super.animateTime = 0;
				// super.animSpriteFrameX = 0;
				// super.animSpriteFrameY = 0;
				// System.out.println("Top of Jump");
			} else if (velocity.y < 0 && isInAir)
			// rising
			{
				super.numberOfFrames = 5;
				super.row = 3;
			} else if (velocity.y > 0 && isInAir)
			// falling
			{
				super.numberOfFrames = 10;
				super.row = 5;
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
		int offsetx = (int) (8);
		int offsety = (int) (velocity.y);
		int offsety2 = (int) (6);
		input(colliders);
		super.update(colliders, vertices);
		Random bool = new Random();
		// smoke
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y - offsety + 12), new Vector2f(16, 16), Textures.particles, 21, 3, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, 3));
		particles
				.add(new Particle(new Vector2f(position.x + offsetx, position.y - offsety + 12), new Vector2f(16, 16), Textures.particles, 21, 3, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 4, 3));
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y + offsety2), new Vector2f(16, 16), Textures.particles, 21, 3, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, 3));
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y + offsety2), new Vector2f(16, 16), Textures.particles, 21, 3, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 4, 3));
		// sparks
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y - offsety + 12), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, bool.nextInt(4)));
		particles
				.add(new Particle(new Vector2f(position.x + offsetx, position.y - offsety + 12), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 4, bool.nextInt(4)));
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y + offsety2), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 4, bool.nextInt(4)));
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y + offsety2), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 4, bool.nextInt(4)));

		// sparks
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y - offsety + 12), new Vector2f(16, 16), Textures.particles, 8, 5, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 1, 2));
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y - offsety + 12), new Vector2f(16, 16), Textures.particles, 8, 5, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 1, 2));
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y + offsety2), new Vector2f(16, 16), Textures.particles, 8, 5, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.5f, .5f), 1, 2));
		particles.add(new Particle(new Vector2f(position.x + offsetx, position.y + offsety2), new Vector2f(16, 16), Textures.particles, 8, 5, bool.nextBoolean(), new Vector2f(16, 16), 0, new Vector2f(velocity.x / 6, velocity.y / 15 - 0.5f), new Vector2f(16f, 4f), new Vector2f(.25f, .5f), 1, 2));
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
