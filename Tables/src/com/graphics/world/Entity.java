package com.graphics.world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.world.projectile.Projectile;

/**
 * Defines the basic behavior of enemies and players
 * 
 * @author Craig Ferris
 * @author Kyle Falicov
 *
 */
public class Entity
{

	protected ArrayList<Projectile>	projectiles			= new ArrayList<Projectile>();
	protected ArrayList<Particle>	particles			= new ArrayList<Particle>();

	public static final float		GRAVITY				= 0.8f;
	public static final float		MAX_SPEED_Y			= 20.0f;
	public static final float		MAX_SPEED_X			= 4.0f;
	public static final float		HORIZONTAL_ACCEL	= 0.4f;
	public static final float		DECEL_VALUE			= 0.3f;
	public static final float		JUMP_VALUE			= -15;
	public static final int			MAX_JUMPS			= 2;
	private float					animateFrameTime	= 5;
	protected boolean				left				= false;
	private int						healthPoints		= 100;
	private boolean					isDead				= false;
	protected Vector3f				position;
	protected Vector3f				velocity;
	private Texture					texture;
	private Texture					outlineTexture;
	protected int					numberOfSpritesX;
	protected int					numberOfSpritesY;
	private Vector2f				sizeOfSpriteOnSheet;
	private Vector2f				sizeOfSpriteSheet;

	private boolean					isAnimated;
	protected int					animSpriteFrameX;
	protected int					animSpriteFrameY;

	private Vector2f				scale;

	protected float					animateSpeed		= 2.0f;
	protected float					animateTime			= 0.0f;

	protected float					walkSpeed			= 4.0f;
	protected float					sprintSpeed			= 8.0f;
	protected boolean				isSprinting			= false;

	protected boolean				affectedByGravity	= false;
	protected boolean				jumping				= false;
	protected int					jumpTimer			= 0;
	protected boolean				canJump				= true;
	protected boolean				isInAir				= false;

	protected boolean				canDoubleJump		= false;
	protected RectangleBox			collider;
	protected boolean				isHostileToPlayer	= false;

	/* Terminal stuff */
	protected boolean				immune				= false;						// whether the current entity
																						// can take damage

	private RectangleBox			currentFloor;

	/**
	 * Creates a new entity
	 * 
	 * @param position
	 *            The initial position of the entity
	 * @param texture
	 *            The texture of the entity
	 * @param sizeOfSpriteSheet
	 *            The size of the sprite sheet
	 * @param scale
	 *            The size at which to draw the object
	 */
	public Entity(Vector3f position, Texture texture, Vector2f sizeOfSpriteSheet, Vector2f scale, Vector2f sizeOfSpriteOnSheet)
	{
		this.position = position;
		this.texture = texture;
		this.scale = scale;
		this.sizeOfSpriteOnSheet = sizeOfSpriteOnSheet;
		numberOfSpritesX = 1;
		numberOfSpritesY = 1;
		this.sizeOfSpriteSheet = sizeOfSpriteSheet;
		isAnimated = false;
		animSpriteFrameX = 0;
		animSpriteFrameY = 0;
		collider = new RectangleBox(new Vector3f(position.x, position.y, position.z), scale);
		velocity = new Vector3f(0, 0, 0);
	}

	/**
	 * Creates a new entity
	 * 
	 * @param position
	 *            The initial position of the entity
	 * @param texture
	 *            The texture of the entity
	 * @param outlineTexture
	 *            The texture with the outlines
	 * @param sizeOfTexture
	 *            The size of the texture
	 * @param numberOfSprites
	 *            The size of each individual sprite on the texture
	 * @param scale
	 *            The size at which to draw the object
	 */
	public Entity(Vector3f position, Texture texture, Texture outlineTexture, Vector2f sizeOfTexture, int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet)
	{
		this(position, texture, sizeOfTexture, scale, sizeOfSpriteOnSheet);
		this.numberOfSpritesX = numberOfSpritesX;
		this.numberOfSpritesY = numberOfSpritesY;
		this.outlineTexture = outlineTexture;
	}

	/**
	 * Returns true if two entities are colliding
	 * 
	 * @param entity
	 *            The entity to check if colliding with
	 * @return Returns true if the paramater entity is colliding with the current object entity
	 */
	public boolean isCollidingWithEntity2D(Entity entity)
	{
		return collider.isCollidingWithBox(entity.getCollider());
	}

	/**
	 * Updates the entity
	 * 
	 * @param colliders
	 *            The colliders in the world to check for collisions with
	 */
	public void update(ArrayList<RectangleBox> colliders)
	{
		if (animateTime >= animateFrameTime)
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

		boolean isOnGround = false;

		// Makes two predictive collision boxes
		RectangleBox nBoxX = new RectangleBox(new Vector3f(collider.getPosition().x + velocity.x, position.y, position.z), collider.getSize());
		RectangleBox nBoxY = new RectangleBox(new Vector3f(position.x, collider.getPosition().y + velocity.y, position.z), collider.getSize());
		for (RectangleBox t : colliders)
		{
			if (t.isCollidingWithBox(nBoxX))
			{
				float leftSideOfWall = t.getPosition().x;
				float rightSideOfWall = t.getPosition().x + t.getSize().x;

				float leftOfPlayer = nBoxX.getPosition().x;
				float rightOfPlayer = nBoxX.getPosition().x + nBoxX.getSize().x;
				if (velocity.x > 0)
				{
					float xShift = Math.abs(leftSideOfWall - rightOfPlayer);
					nBoxX.getPosition().x += -xShift;
					velocity.x = 0;
				} else if (velocity.x < 0)
				{
					float xShift = Math.abs(rightSideOfWall - leftOfPlayer);
					nBoxX.getPosition().x += xShift;
					velocity.x = 0;
				}
			}
			if (t.isCollidingWithBox(nBoxY))
			{
				float topOfGround = t.getPosition().y;
				float bottomOfGround = t.getPosition().y + t.getSize().y;

				float topOfPlayer = nBoxY.getPosition().y;
				float bottomOfPlayer = nBoxY.getPosition().y + nBoxY.getSize().y;
				if (velocity.y > 0)
				{
					float yShift = Math.abs(bottomOfPlayer - topOfGround);

					nBoxY.getPosition().y += -yShift;
					isOnGround = true;
					velocity.y = 0;
				} else if (velocity.y < 0)
				{
					float yShift = Math.abs(topOfPlayer - bottomOfGround);
					nBoxY.getPosition().y += yShift;
					velocity.y = 0;
				}
				currentFloor = t;
			}
		}
		if (jumping)
		{
			velocity.y = JUMP_VALUE;
			jumping = false;
		}

		isInAir = !isOnGround;
		collider.setPosition(new Vector3f(nBoxX.getPosition().x, nBoxY.getPosition().y, position.z));
		position.x = collider.getPosition().x;
		position.y = collider.getPosition().y;
	}

	/**
	 * Checks for collisions with projectiles
	 * 
	 * @param projectiles
	 *            The projectiles to check
	 */
	public void checkForCollisionWithProjectiles(ArrayList<Projectile> projectiles)
	{
		for (Projectile p : projectiles)
		{
			if (!p.isDead() && p.isCollidingWithEntity2D(this))
			{
				p.setDead(true);
				this.takeDamage(p.getDamage());
				break;
			}
		}
	}

	/**
	 * Makes the entity jump
	 */
	protected void jump()
	{
		if (!jumping)
		{
			jumping = true;
		}
	}

	/**
	 * Makes the entity move left;
	 */
	public void moveLeft()
	{
		if (isSprinting)
		{
			velocity.x -= 2 * HORIZONTAL_ACCEL;
			if (velocity.x < 2 * -MAX_SPEED_X)
			{
				velocity.x = 2 * -MAX_SPEED_X;
			}
		} else
		{
			velocity.x -= HORIZONTAL_ACCEL;
			if (velocity.x < -(MAX_SPEED_X))
			{
				velocity.x = -(MAX_SPEED_X);
			}
		}
	}

	/**
	 * stops the entity
	 */
	public void stopMoving()
	{
		if (velocity.x > 0)
		{
			velocity.x -= DECEL_VALUE;
		} else if (velocity.x < 0)
		{
			velocity.x += DECEL_VALUE;
		}
		if (Math.abs(velocity.x) < DECEL_VALUE)
		{
			velocity.x = 0;
		}
	}

	/**
	 * Makes the entity move right
	 */
	public void moveRight()
	{
		if (isSprinting)
		{
			velocity.x += 2 * HORIZONTAL_ACCEL;
			if (velocity.x > 2 * MAX_SPEED_X)
			{
				velocity.x = 2 * MAX_SPEED_X;
			}
		} else
		{
			velocity.x += HORIZONTAL_ACCEL;
			if (velocity.x > (MAX_SPEED_X))
			{
				velocity.x = (MAX_SPEED_X);
			}
		}
	}

	/**
	 * Makes the entity move up on the screen
	 */
	public void moveUp()
	{
		if (isSprinting)
		{
			velocity.y -= 2 * GRAVITY;
			if (velocity.y < -2 * GRAVITY)
			{
				velocity.y = -2 * GRAVITY;
			}
		} else
		{
			velocity.y -= GRAVITY;
			if (velocity.y < -(GRAVITY))
			{
				velocity.y = -(GRAVITY);
			}
		}
	}

	/**
	 * Makes the entity move down on the screen
	 */
	public void moveDown()
	{
		if (isSprinting)
		{
			velocity.y += 2 * GRAVITY;
			if (velocity.y > 2 * GRAVITY)
			{
				velocity.y = 2 * GRAVITY;
			}
		} else
		{
			velocity.y += GRAVITY;
			if (velocity.y > (GRAVITY))
			{
				velocity.y = (GRAVITY);
			}
		}
	}

	/**
	 * Draws the object to the screen
	 */
	public void render()
	{
		if (numberOfSpritesX == 1)
		{
			GFX.drawEntireSprite(scale.x, scale.y, position.x, position.y, texture);
		} else
		{
			Vector2f offset = new Vector2f(((float) (sizeOfSpriteOnSheet.x * animSpriteFrameX)) / sizeOfSpriteSheet.x, (float) (sizeOfSpriteOnSheet.y * numberOfSpritesY) / sizeOfSpriteSheet.y);
			Vector2f sizey = new Vector2f((float) (sizeOfSpriteOnSheet.x / sizeOfSpriteSheet.x), (float) (sizeOfSpriteOnSheet.y / sizeOfSpriteSheet.y));
			if (velocity.x < 0 || left)
			{
				GFX.drawSpriteFromSpriteSheetInverse(scale.x, scale.y, position.x, position.y, texture, offset, sizey);
			} else
			{
				GFX.drawSpriteFromSpriteSheet(scale.x, scale.y, position.x, position.y, texture, offset, sizey);
			}
		}
	}

	/**
	 * a separate method for drawing the outlines, will be used to make outlines in a background layer
	 */
	public void renderOutline()
	{
		if (numberOfSpritesX == 1)
		{
			GFX.drawEntireSprite(scale.x, scale.y, position.x, position.y, texture);
		} else
		{
			Vector2f offset = new Vector2f(((float) (sizeOfSpriteOnSheet.x * animSpriteFrameX)) / sizeOfSpriteSheet.x, (float) (sizeOfSpriteOnSheet.y * numberOfSpritesY) / sizeOfSpriteSheet.y);
			Vector2f sizey = new Vector2f((float) (sizeOfSpriteOnSheet.x / sizeOfSpriteSheet.x), (float) (sizeOfSpriteOnSheet.y / sizeOfSpriteSheet.y));
			if (velocity.x < 0 || left)
			{
				GFX.drawSpriteFromSpriteSheetInverse(scale.x, scale.y, position.x, position.y, outlineTexture, offset, sizey);
			} else
			{
				GFX.drawSpriteFromSpriteSheet(scale.x, scale.y, position.x, position.y, outlineTexture, offset, sizey);
			}
		}
	}

	/**
	 * Lowers the entities HP
	 * 
	 * @param damage
	 *            The amount to lower the HP
	 */
	public void takeDamage(int damage)
	{
		if (!immune)
		{
			this.healthPoints -= damage;
		}
		if (healthPoints <= 0)
		{
			isDead = true;
		}
	}

	/**
	 * Returns the size at which the entity is rendered on the screen
	 * 
	 * @return Returns the size at which the entity is rendered on the screen
	 */
	public Vector2f getScale()
	{
		return scale;
	}

	/**
	 * Sets the size at which the entity is rendered on screen
	 * 
	 * @param scale
	 *            The value to set the scale to
	 */
	public void setScale(Vector2f scale)
	{
		this.scale = scale;
	}

	/**
	 * Returns the position of the entity
	 * 
	 * @return Returns the position of the entity
	 */
	public Vector3f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the entity
	 * 
	 * @param position
	 *            The new position
	 */
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	/**
	 * Returns the texture of the entity
	 * 
	 * @return Returns the texture of the entity
	 */
	public Texture getTexture()
	{
		return texture;
	}

	/**
	 * Sets the texture of the entity
	 * 
	 * @param texture
	 *            The new texture of the entity
	 */
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	/**
	 * Returns the size of an individual sprite on the spritesheet
	 * 
	 * @return Returns the size of an individual sprite on the spritesheet
	 */
	public Vector2f getSizeOfSpriteOnSheet()
	{
		return sizeOfSpriteOnSheet;
	}

	/**
	 * Sets the size of the sprite on the spritesheet
	 * 
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the spritesheet
	 */
	public void setSizeOfSpriteOnSheet(Vector2f sizeOfSpriteOnSheet)
	{
		this.sizeOfSpriteOnSheet = sizeOfSpriteOnSheet;
	}

	/**
	 * Returns true if the entity has animations
	 * 
	 * @return Returns true if the entity has animations
	 */
	public boolean isAnimated()
	{
		return isAnimated;
	}

	/**
	 * Sets the animation mode. The default is false which means no animations
	 * 
	 * @param isAnimated
	 *            The new animation mode
	 */
	public void setAnimated(boolean isAnimated)
	{
		this.isAnimated = isAnimated;
	}

	/**
	 * Returns the animation speed
	 * 
	 * @return Returns the animation speed
	 */
	public float getAnimateSpeed()
	{
		return animateSpeed;
	}

	/**
	 * Sets the animation speed
	 * 
	 * @param animateSpeed
	 *            The new animatin speed
	 */
	public void setAnimateSpeed(float animateSpeed)
	{
		this.animateSpeed = animateSpeed;
	}

	/**
	 * Returns true if the entity is sprinting
	 * 
	 * @return Returns true if the entity is sprinting
	 */
	public boolean isSprinting()
	{
		return isSprinting;
	}

	/**
	 * Sets the entity's sprint mode
	 * 
	 * @param isSprinting
	 *            The new sprint mode
	 */
	public void setSprinting(boolean isSprinting)
	{
		this.isSprinting = isSprinting;
	}

	/**
	 * Returns the walk speed of the entity
	 * 
	 * @return Returns the walk speed of the entity
	 */
	public float getWalkSpeed()
	{
		return walkSpeed;
	}

	/**
	 * Sets the walk speed of the entity
	 * 
	 * @param walkSpeed
	 *            The new walk speed
	 */
	public void setWalkSpeed(float walkSpeed)
	{
		this.walkSpeed = walkSpeed;
	}

	/**
	 * Returns the sprint speed
	 * 
	 * @return Returns the sprint speed
	 */
	public float getSprintSpeed()
	{
		return sprintSpeed;
	}

	/**
	 * Sets the sprint speed
	 * 
	 * @param sprintSpeed
	 *            Sets the sprint speed
	 */
	public void setSprintSpeed(float sprintSpeed)
	{
		this.sprintSpeed = sprintSpeed;
	}

	/**
	 * Returns true if the entity is affected by gravity
	 * 
	 * @return Returns true if the entity is affected by gravity
	 */
	public boolean isAffectedByGravity()
	{
		return affectedByGravity;
	}

	/**
	 * Sets the gravity mode (Default is gravity is off)
	 * 
	 * @param affectedByGravity
	 */
	public void setAffectedByGravity(boolean affectedByGravity)
	{
		this.affectedByGravity = affectedByGravity;
	}

	/**
	 * Returns the rectangular collider
	 * 
	 * @return Returns the rectangular collider
	 */
	public RectangleBox getCollider()
	{
		return collider;
	}

	/**
	 * Sets the rectangular collider
	 * 
	 * @param collider
	 *            The new rectangular collider
	 */
	public void setCollider(RectangleBox collider)
	{
		this.collider = collider;
	}

	/**
	 * Returns the velocity of the entity
	 * 
	 * @return Returns the velocity of the entity
	 */
	public Vector3f getVelocity()
	{
		return velocity;
	}

	/**
	 * Sets the velocity of the entity
	 * 
	 * @param velocity
	 *            The new velocity
	 */
	public void setVelocity(Vector3f velocity)
	{
		this.velocity = velocity;
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
	 * Sets the size of the spritesheet
	 * 
	 * @param sizeOfSpriteSheet
	 *            The size of the spritesheet
	 */
	public void setSizeOfSpriteSheet(Vector2f sizeOfSpriteSheet)
	{
		this.sizeOfSpriteSheet = sizeOfSpriteSheet;
	}

	/**
	 * Returns the animation frame counter in the x direction
	 * 
	 * @return Returns the animation frame counter in the x direction
	 */
	public int getAnimSpriteFrameX()
	{
		return animSpriteFrameX;
	}

	/**
	 * Sets the animation frame counter in the x direction
	 * 
	 * @param animSpriteFrameX
	 *            The new animation frame in the x direction
	 */
	public void setAnimSpriteFrameX(int animSpriteFrameX)
	{
		this.animSpriteFrameX = animSpriteFrameX;
	}

	/**
	 * Returns the animation frame in the y direction
	 * 
	 * @return Returns the animation frame in the y direction
	 */
	public int getAnimSpriteFrameY()
	{
		return animSpriteFrameY;
	}

	/**
	 * Sets the animation frame in the y direction
	 * 
	 * @param animSpriteFrameY
	 *            The new animation frame in the y direction
	 */
	public void setAnimSpriteFrameY(int animSpriteFrameY)
	{
		this.animSpriteFrameY = animSpriteFrameY;
	}

	/**
	 * Returns the number of sprites in the x direction of the spritesheet
	 * 
	 * @return Returns the number of sprites in the x direction of the spritesheet
	 */
	public int getNumberOfSpritesX()
	{
		return numberOfSpritesX;
	}

	/**
	 * Sets the number of sprites in the x direction
	 * 
	 * @param numberOfSpritesX
	 *            The number of sprites in the x direction
	 */
	public void setNumberOfSpritesX(int numberOfSpritesX)
	{
		this.numberOfSpritesX = numberOfSpritesX;
	}

	/**
	 * Returns the number of sprites in the y direction
	 * 
	 * @return Returns the numbe rof sprites in the y direction
	 */
	public int getNumberOfSpritesY()
	{
		return numberOfSpritesY;
	}

	/**
	 * Sets the number of sprites in the y direction
	 * 
	 * @param numberOfSpritesY
	 *            The number of sprites in the y direction
	 */
	public void setNumberOfSpritesY(int numberOfSpritesY)
	{
		this.numberOfSpritesY = numberOfSpritesY;
	}

	/**
	 * Returns the number of health points
	 * 
	 * @return Returns the number of health points
	 */
	public int getHealthPoints()
	{
		return healthPoints;
	}

	/**
	 * Sets the number of health points
	 * 
	 * @param healthPoints
	 *            The new number of health points
	 */
	public void setHealthPoints(int healthPoints)
	{
		this.healthPoints = healthPoints;
	}

	/**
	 * Returns true if the entity is dead
	 * 
	 * @return Returns true if the entity is dead
	 */
	public boolean isDead()
	{
		return isDead;
	}

	/**
	 * Sets if the entity is dead
	 * 
	 * @param isDead
	 *            The new value of isDead
	 */
	public void setDead(boolean isDead)
	{
		this.isDead = isDead;
	}

	/**
	 * Returns the outline texture
	 * 
	 * @return Returns the outline texture
	 */
	public Texture getOutlineTexture()
	{
		return outlineTexture;
	}

	/**
	 * Sets the outline texture
	 * 
	 * @param outlineTexture
	 *            The outline texture
	 */
	public void setOutlineTexture(Texture outlineTexture)
	{
		this.outlineTexture = outlineTexture;
	}

	/**
	 * Returns the animate frame time
	 * 
	 * @return Returns the anmimate frame time
	 */
	public float getAnimateFrameTime()
	{
		return animateFrameTime;
	}

	/**
	 * Sets the animate frame time
	 * 
	 * @param animateFrameTime
	 *            The new animate frame time
	 */
	public void setAnimateFrameTime(float animateFrameTime)
	{
		this.animateFrameTime = animateFrameTime;
	}

	/**
	 * Returns the projectiles fired
	 * 
	 * @return Returns the projectiles fired
	 */
	public ArrayList<Projectile> getProjectiles()
	{
		return projectiles;
	}

	/**
	 * Sets the projectiles fired
	 * 
	 * @param projectiles
	 *            The projectiles fired
	 */
	public void setProjectiles(ArrayList<Projectile> projectiles)
	{
		this.projectiles = projectiles;
	}

	/**
	 * Returns the particles
	 * 
	 * @return Returns the particles
	 */
	public ArrayList<Particle> getParticles()
	{
		return particles;
	}

	/**
	 * Sets the particles
	 * 
	 * @param particles
	 *            The particles
	 */
	public void setParticles(ArrayList<Particle> particles)
	{
		this.particles = particles;
	}

	/**
	 * Returns true if the entity is hostile to the player
	 * 
	 * @return Returns true if the entity is hostile to the player
	 */
	public boolean isHostileToPlayer()
	{
		return isHostileToPlayer;
	}

	/**
	 * Sets if the entity is hostile to the player
	 * 
	 * @param isHostileToPlayer
	 *            Is the entity hostile towards the player
	 */
	public void setHostileToPlayer(boolean isHostileToPlayer)
	{
		this.isHostileToPlayer = isHostileToPlayer;
	}

	public RectangleBox getCurrentFloor()
	{
		return currentFloor;
	}

	public void setCurrentFloor(RectangleBox currentFloor)
	{
		this.currentFloor = currentFloor;
	}
}
