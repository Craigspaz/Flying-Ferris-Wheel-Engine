package com.graphics.world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;

/**
 * Defines the basic behavior of enemies and players
 * @author Craig Ferris
 *
 */
public class Entity
{
	
	protected static final float GRAVITY = 0.8f;
	protected static final float MAX_SPEED = 20.0f;
	protected Vector3f position;
	protected Vector3f velocity;
	private Texture texture;
	protected int numberOfSprites;
	private Vector2f sizeOfSpriteOnSheet;

	private boolean isAnimated;
	protected int animSpriteFrame;

	private Vector2f scale;

	protected float animateSpeed = 2.0f;
	protected float animateTime = 0.0f;

	protected float walkSpeed = 2.0f;
	protected float sprintSpeed = 4.0f;
	protected boolean isSprinting = false;
	
	protected boolean affectedByGravity = false;
	protected boolean jumping = false;
	protected int jumpTimer = 0;
	
	protected RectangleBox collider;

	/**
	 * Creates a new entity
	 * @param position The initial position of the entity
	 * @param texture The texture of the entity
	 * @param size The size of the sprite on the texture
	 * @param scale The size at which to draw the object
	 */
	public Entity(Vector3f position, Texture texture, Vector2f size, Vector2f scale)
	{
		this.position = position;
		this.texture = texture;
		this.scale = scale;
		numberOfSprites = 1;
		sizeOfSpriteOnSheet = size;
		isAnimated = false;
		animSpriteFrame = 0;
		collider = new RectangleBox(new Vector3f(position.x, position.y, position.z),scale);
		velocity = new Vector3f(0,0,0);
	}

	/**
	 * Creates a new entity
	 * @param position The initial position of the entity
	 * @param texture The texture of the entity
	 * @param sizeOfTexture The size of the texture
	 * @param numberOfSprites The size of each individual sprite on the texture
	 * @param scale The size at which to draw the object
	 */
	public Entity(Vector3f position, Texture texture, Vector2f sizeOfTexture, int numberOfSprites, Vector2f scale)
	{
		this(position, texture, sizeOfTexture, scale);
		this.numberOfSprites = numberOfSprites;
	}

	/**
	 * Returns true if two entities are colliding
	 * @param entity The entity to check if colliding with
	 * @return Returns true if the paramater entity is colliding with the current object entity
	 */
	public boolean isCollidingWithEntity2D(Entity entity)
	{
		return collider.isCollidingWithBox(entity.getCollider());
	}

	/**
	 * Updates the entity
	 */
	public void update(ArrayList<Tile> tiles)
	{
		if (animateTime >= 10)
		{
			animSpriteFrame++;
			if (animSpriteFrame >= numberOfSprites)
			{
				animSpriteFrame = 0;
			}
			animateTime = 0.0f;
		}
		else
		{
			animateTime += animateSpeed;
		}
		
		if(affectedByGravity)
		{
			//collider.getPosition().y += GRAVITY;
			velocity.y += GRAVITY;
		}
		
		if(jumping)
		{
			if(jumpTimer >= 20)
			{
				jumpTimer = 0;
				jumping = false;
			}
			else
			{
				//collider.getPosition().y -= 2 * GRAVITY + 10 - jumpTimer;
				velocity.y = -6;
				jumpTimer++;
				System.out.println("Jumping... " + jumpTimer);
			}
		}
		
		if(velocity.y > MAX_SPEED)
		{
			velocity.y = MAX_SPEED;
		}
		else if(velocity.y < -MAX_SPEED)
		{
			velocity.y = -MAX_SPEED;
		}

		collider.getPosition().y += velocity.y;
		
		for(Tile t: tiles)
		{
			if(t.isCollidingWithBox(collider))
			{
				System.out.println(t.isColliding(collider));
				//System.out.println("Player with collider: " + collider + " is colliding with collider: " + t.getCollider());
				//System.out.println("Player is rendered at: (" + position.x + ", " + position.y + ", " + position.z + ") (" + scale.x + ", " + scale.y + ")");
				//velocity.y = collider.getSize().y - (t.getPosition().y - collider.getPosition().y);
				collider.setPosition(new Vector3f(position.x,position.y,position.z));
				velocity = new Vector3f(0,0,0);
				
				//System.out.println("New Collider position: " + collider);
				
				break;
			}
		}
		position = new Vector3f(collider.getPosition().x,collider.getPosition().y,collider.getPosition().z);
	}
	
	/**
	 * Makes the entity jump
	 */
	protected void jump()
	{
		if(!jumping)
		{
			jumping = true;
		}
	}

	/**
	 * Makes the entity move left;
	 */
	protected void moveLeft()
	{
		if(isSprinting)
		{
			collider.getPosition().x -= sprintSpeed;
		}
		else
		{
			collider.getPosition().x -= walkSpeed;
		}
	}
	
	/**
	 * Makes the entity move right
	 */
	protected void moveRight()
	{
		if(isSprinting)
		{
			collider.getPosition().x += sprintSpeed;
		}
		else
		{
			collider.getPosition().x += walkSpeed;
		}
	}
	
	/**
	 * Makes the entity move up on the screen
	 */
	private void moveUp()
	{
		if(isSprinting)
		{
			collider.getPosition().y -= sprintSpeed;
		}
		else
		{
			collider.getPosition().y -= walkSpeed;
		}
	}
	
	/**
	 * Makes the entity move down on the screen
	 */
	private void moveDown()
	{
		if(isSprinting)
		{
			collider.getPosition().y += sprintSpeed;
		}
		else
		{
			collider.getPosition().y += walkSpeed;
		}
	}

	/**
	 * Draws the object to the screen
	 */
	public void render()
	{
		if (numberOfSprites == 1)
		{
			GFX.drawEntireSprite(scale.x, scale.y, position.x, position.y, texture);
		}
		else
		{
			Vector2f offset = new Vector2f(((float) (32f * animSpriteFrame)) / sizeOfSpriteOnSheet.x, 0);
			Vector2f sizey = new Vector2f((float) (32f / sizeOfSpriteOnSheet.x), 1);
			//GFX.drawSpriteFromSpriteSheet(scale.x, scale.y, position.x, position.y, texture, offset, sizey);
			GFX.drawSpriteFromSpriteSheetInverse(scale.x, scale.y, position.x, position.y, texture, offset, sizey);
		}
	}

	/**
	 * Returns the size at which the entity is rendered on the screen
	 * @return Returns the size at which the entity is rendered on the screen
	 */
	public Vector2f getScale()
	{
		return scale;
	}

	/**
	 * Sets the size at which the entity is rendered on screen
	 * @param scale The value to set the scale to
	 */
	public void setScale(Vector2f scale)
	{
		this.scale = scale;
	}

	/**
	 * Returns the position of the entity
	 * @return Returns the position of the entity
	 */
	public Vector3f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the entity
	 * @param position The new position
	 */
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	/**
	 * Returns the texture of the entity
	 * @return Returns the texture of the entity
	 */
	public Texture getTexture()
	{
		return texture;
	}

	/**
	 * Sets the texture of the entity
	 * @param texture The new texture of the entity
	 */
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	/**
	 * Returns the number of sprites on the spritesheet
	 * @return Returns the number of sprites on the spritesheet
	 */
	public int getNumberOfSprites()
	{
		return numberOfSprites;
	}

	/**
	 * Sets the number of sprites on the spritesheet
	 * @param numberOfSprites The number of sprites on the spritesheet
	 */
	public void setNumberOfSprites(int numberOfSprites)
	{
		this.numberOfSprites = numberOfSprites;
	}

	/**
	 * Returns the size of an individual sprite on the spritesheet
	 * @return Returns the size of an individual sprite on the spritesheet
	 */
	public Vector2f getSizeOfSpriteOnSheet()
	{
		return sizeOfSpriteOnSheet;
	}

	/**
	 * Sets the size of the sprite on the spritesheet
	 * @param sizeOfSpriteOnSheet The size of the sprite on the spritesheet
	 */
	public void setSizeOfSpriteOnSheet(Vector2f sizeOfSpriteOnSheet)
	{
		this.sizeOfSpriteOnSheet = sizeOfSpriteOnSheet;
	}

	/**
	 * Returns true if the entity has animations
	 * @return Returns true if the entity has animations
	 */
	public boolean isAnimated()
	{
		return isAnimated;
	}

	/**
	 * Sets the animation mode. The default is false which means no animations
	 * @param isAnimated The new animation mode
	 */
	public void setAnimated(boolean isAnimated)
	{
		this.isAnimated = isAnimated;
	}

	/**
	 * Returns the animation speed
	 * @return Returns the animation speed
	 */
	public float getAnimateSpeed()
	{
		return animateSpeed;
	}

	/**
	 * Sets the animation speed
	 * @param animateSpeed The new animatin speed
	 */
	public void setAnimateSpeed(float animateSpeed)
	{
		this.animateSpeed = animateSpeed;
	}

	/**
	 * Returns true if the entity is sprinting
	 * @return Returns true if the entity is sprinting
	 */
	public boolean isSprinting()
	{
		return isSprinting;
	}
	/**
	 * Sets the entity's sprint mode
	 * @param isSprinting The new sprint mode
	 */
	public void setSprinting(boolean isSprinting)
	{
		this.isSprinting = isSprinting;
	}

	/**
	 * Returns the walk speed of the entity
	 * @return Returns the walk speed of the entity
	 */
	public float getWalkSpeed()
	{
		return walkSpeed;
	}
	
	/**
	 * Sets the walk speed of the entity
	 * @param walkSpeed The new walk speed
	 */
	public void setWalkSpeed(float walkSpeed)
	{
		this.walkSpeed = walkSpeed;
	}

	/**
	 * Returns the sprint speed
	 * @return Returns the sprint speed
	 */
	public float getSprintSpeed()
	{
		return sprintSpeed;
	}

	/**
	 * Sets the sprint speed
	 * @param sprintSpeed Sets the sprint speed
	 */
	public void setSprintSpeed(float sprintSpeed)
	{
		this.sprintSpeed = sprintSpeed;
	}

	/**
	 * Returns true if the entity is affected by gravity
	 * @return Returns true if the entity is affected by gravity
	 */
	public boolean isAffectedByGravity()
	{
		return affectedByGravity;
	}

	/**
	 * Sets the gravity mode (Default is gravity is off)
	 * @param affectedByGravity
	 */
	public void setAffectedByGravity(boolean affectedByGravity)
	{
		this.affectedByGravity = affectedByGravity;
	}

	/**
	 * Returns the rectangular collider
	 * @return Returns the rectangular collider
	 */
	public RectangleBox getCollider()
	{
		return collider;
	}

	/**
	 * Sets the rectangular collider
	 * @param collider The new rectangular collider
	 */
	public void setCollider(RectangleBox collider)
	{
		this.collider = collider;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
}
