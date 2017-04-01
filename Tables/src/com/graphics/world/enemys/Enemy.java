package com.graphics.world.enemys;

import java.awt.List;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.Textures;
import com.graphics.world.Entity;
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.projectile.Projectile;

/**
 * Handles enemy
 * 
 * @author Craig Ferris
 *
 */
public class Enemy extends Entity
{
	private boolean					moveLeft				= false;
	private boolean					moveRight				= false;
	private boolean					isJumping				= false;
	private boolean					isUp					= false;

	private ArrayList<Projectile>	projectiles				= new ArrayList<Projectile>();
	private int						shootingDelay			= 5;
	private int						shootingCounter			= 0;
	private boolean					canShoot				= true;
	private boolean					shoot					= false;
	private float					shootAngle				= 0;
	private float					bulletSpeed				= 16;
	private int						id;
	protected String				name					= "";

	private int						maxJumpDistanceWalk		= 3 * 64;
	private int						maxJumpDistanceSprint	= 5 * 64;

	/**
	 * Creates a new enemy based on an already created entity
	 * 
	 * @param e
	 *            The entity
	 */
	public Enemy(Entity e)
	{
		super(e.getPosition(), e.getTexture(), e.getOutlineTexture(), e.getSizeOfSpriteSheet(), e.getNumberOfSpritesX(), e.getNumberOfSpritesY(), e.getScale(), e.getSizeOfSpriteOnSheet());
		super.affectedByGravity = true;
		this.id = -1;
	}

	/**
	 * Creates new Enemy
	 * 
	 * @param position
	 *            The position of the enemy
	 * @param texture
	 *            The texture of the enemy
	 * @param size
	 *            The size of the spritesheet
	 * @param scale
	 *            The size to render the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the sprite sheet
	 */
	public Enemy(Vector3f position, Texture texture, Vector2f size, Vector2f scale, Vector2f sizeOfSpriteOnSheet)
	{
		super(position, texture, size, scale, sizeOfSpriteOnSheet);
		this.id = -1;
	}

	/**
	 * Creates new Enemy
	 * 
	 * @param position
	 *            The position of the enemy
	 * @param texture
	 *            The main texture of the enemy
	 * @param outlineTexture
	 *            The outline texture of the eney
	 * @param sizeOfTexture
	 *            The size of the spritesheet
	 * @param numberOfSpritesX
	 *            The number of sprites in the animation in the x direction
	 * @param numberOfSpritesY
	 *            The row in the spritesheet (zero based)
	 * @param scale
	 *            The size to render the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the spritesheet
	 */
	public Enemy(Vector3f position, Texture texture, Texture outlineTexture, Vector2f sizeOfTexture, int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet)
	{
		super(position, texture, outlineTexture, sizeOfTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
		this.id = -1;
	}

	/**
	 * Handles movement
	 */
	private void movement()
	{
		if (!moveLeft && !moveRight)
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
			if (!left)
			{
				shootAngle = 0;
			} else
			{
				shootAngle = 180;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			{
				shootAngle = 90;
			}
		}

		if (moveRight)
		{
			super.moveRight();
			if (velocity.x > 0)
			{
				shootAngle = 0;
				if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				{
					shootAngle = 45;
				}
			}
		}

		if (moveLeft)
		{
			moveLeft();
			if (velocity.x < 0)
			{
				shootAngle = 180;
				if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				{
					shootAngle = 135;
				}
			}
		}

		if (!isJumping)
		{
			canJump = true;
			jumping = false;
		}

		if (isJumping)
		{
			if (canJump)
			{
				jump();
				canJump = false;
			}
		}

		if (isUp)
		{
			if (!left)
				shootAngle = 45;
			else
				shootAngle = 135;
		}

		if (!shoot)
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

		if (shoot)
		{
			if (canShoot)
			{
				projectiles.add(new Projectile(new Vector3f(super.position.x, super.position.y, 0), Textures.playerLaser, new Vector2f(32, 256), 0, 1, new Vector2f(32, 32), new Vector2f(32, 32), shootAngle, bulletSpeed, velocity.x, velocity.y));
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
			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 0;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
		}

		if (Math.abs(velocity.y) < 10 && isInAir)

		{
			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 4;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
			// System.out.println("Top of Jump");
		} else if (velocity.y < 0 && isInAir)

		{
			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 3;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
		} else if (velocity.y > 0 && isInAir)

		{

			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 5;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
		}

	}

	/**
	 * Creates a new enemy based on an ID and sets it's initial position to x,y
	 * 
	 * @param id
	 *            The ID of the enemy to return
	 * @param x
	 *            The x position of the enemy
	 * @param y
	 *            The y position of the enemy
	 * @return Returns the created enemy
	 */
	public static Enemy generateBasicEnemyBasedOnID(String id, float x, float y)
	{
		Enemy e = null;
		switch (id)
		{
			case "table":
				e = new Table(x, y);
				break;
		}
		return e;
	}

	/**
	 * Updates the enemy
	 * 
	 * @param colliders
	 *            The colliders to check agains the enemy
	 */
	public void update(ArrayList<RectangleBox> colliders, Player player)
	{
		generatePath(colliders, player);
		movement();
		super.update(colliders);
	}

	private int getManhattanDistance(Vector3f start, Vector3f end)
	{
		return (int) (Math.abs(start.x - end.x) + Math.abs(start.y - end.y));
	}


	private ArrayList<RectangleBox> generatePath(ArrayList<RectangleBox> colliders, Player player)
	{

		RectangleBox endCollider = player.getCurrentFloor();
		if(endCollider == null)
		{
			return null;
		}
		RectangleBox startCollider = this.getCurrentFloor();
		if(startCollider == null)
		{
			return null;
		}

		int ticks = (int) Math.floor(-JUMP_VALUE / GRAVITY);
		int maxHeight = (int)((0.5f)*GRAVITY*ticks*ticks);
		int maxDistance = (int) (ticks * 2 * MAX_SPEED_X);
		System.out.println("Ticks: " + ticks + " MaxHeight: " + maxHeight + " MaxDistance: " + maxDistance);
		//Node result = new Node(startCollider,ticks,maxDistance,maxHeight).generatePath(colliders, endCollider);		
		//System.out.println("Destination: " + result + " Start: " + result.getParent());
		
		
		Node startNode = new Node(startCollider,ticks,maxDistance,maxHeight);
		System.out.println("Start: " + startNode);
		Node result = generatePath(colliders,endCollider,startNode);		
		System.out.println("Destination: " + result);
	
		// System.out.println("Start Node: " + startNode);
		
		ArrayList<RectangleBox> resultNodes = new ArrayList<RectangleBox>();
		while(result != null)
		{
			resultNodes.add(result.collider);
			System.out.println(result);
			result = result.getParent();
		}
		return resultNodes;
	}
	
	
	public Node generatePath(ArrayList<RectangleBox> colliders, RectangleBox destination,Node root)
	{	
		int ticks = (int) Math.floor(-JUMP_VALUE / GRAVITY);
		int maxHeight = (int)((0.5f)*GRAVITY*ticks*ticks);
		int maxDistance = (int) (ticks * 2 * MAX_SPEED_X);
		Node result = null;
		if(root != null)
		{
			if(root.collider == destination)
			{
				return root;
			}
			RectangleBox c = new RectangleBox(new Vector3f(root.collider.getPosition().x - maxDistance,root.collider.getPosition().y - maxHeight,0),new Vector2f(root.collider.getSize().x + (2 * maxDistance),maxHeight));
			Node tmp = null;
			int minDistance = Integer.MAX_VALUE;
			for(RectangleBox bo: colliders)
			{
				if(bo.isCollidingWithBox(c) && c.getPosition().y < bo.getPosition().y)
				{
					int dist = getManhattanDistance(bo.getPosition(),destination.getPosition());
					if(dist < minDistance)
					{
						minDistance = dist;
						tmp = new Node(bo,ticks,maxDistance,maxHeight);
						tmp.setParent(root);
						//System.out.println("Found Node");
					}
				}
			}
			//System.out.println("Tmp: " + tmp);
			if(tmp == null)
			{
				return root;
			}
			return generatePath(colliders, destination, tmp);
		}
		return result;		
	}	

	/**
	 * Returns if the enemy is moving left
	 * 
	 * @return Returns if the enemy is moving left
	 */
	public boolean isMoveLeft()
	{
		return moveLeft;
	}

	/**
	 * Sets if the enemy is moving left
	 * 
	 * @param moveLeft
	 *            The value to set
	 */
	public void setMoveLeft(boolean moveLeft)
	{
		this.moveLeft = moveLeft;
	}

	/**
	 * Returns if the enemy is moving right
	 * 
	 * @return Returns if the enemy is moving right
	 */
	public boolean isMoveRight()
	{
		return moveRight;
	}

	/**
	 * Sets if the enemy is moving right
	 * 
	 * @param moveRight
	 *            The value to set
	 */
	public void setMoveRight(boolean moveRight)
	{
		this.moveRight = moveRight;
	}

	/**
	 * Returns if the enemy is jumping
	 * 
	 * @return Returns if the enemy is jumping
	 */
	public boolean isJumping()
	{
		return isJumping;
	}

	/**
	 * Sets if the enemy is jumping
	 * 
	 * @param isJumping
	 *            The value to set
	 */
	public void setJumping(boolean isJumping)
	{
		this.isJumping = isJumping;
	}

	/**
	 * Returns if the enemy is moving up
	 * 
	 * @return Returns if the enemy is moving up
	 */
	public boolean isUp()
	{
		return isUp;
	}

	/**
	 * Sets if the player is moving up
	 * 
	 * @param isUp
	 *            The value to set
	 */
	public void setUp(boolean isUp)
	{
		this.isUp = isUp;
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
	 * Sets the projectiles the enemy is firing
	 * 
	 * @param projectiles
	 *            The projectiles the enemy is firing
	 */
	public void setProjectiles(ArrayList<Projectile> projectiles)
	{
		this.projectiles = projectiles;
	}

	/**
	 * Returns the shooting delay
	 * 
	 * @return Returns the shooting delay
	 */
	public int getShootingDelay()
	{
		return shootingDelay;
	}

	/**
	 * Sets the shooting delay
	 * 
	 * @param shootingDelay
	 *            The delay between each projecitle
	 */
	public void setShootingDelay(int shootingDelay)
	{
		this.shootingDelay = shootingDelay;
	}

	/**
	 * Returns the shooting counter
	 * 
	 * @return Returns the shooting counter
	 */
	public int getShootingCounter()
	{
		return shootingCounter;
	}

	/**
	 * Sets the shooting counter
	 * 
	 * @param shootingCounter
	 *            The new time in the counter
	 */
	public void setShootingCounter(int shootingCounter)
	{
		this.shootingCounter = shootingCounter;
	}

	/**
	 * Returns true if the enemy can shoot
	 * 
	 * @return Returns if the enemy can shoot
	 */
	public boolean isCanShoot()
	{
		return canShoot;
	}

	/**
	 * Sets if the enemy is shooting
	 * 
	 * @param canShoot
	 *            The value to set shoot
	 */
	public void setCanShoot(boolean canShoot)
	{
		this.canShoot = canShoot;
	}

	/**
	 * Returns true if the enemy is shooting
	 * 
	 * @return Returns true if the enemy is shooting
	 */
	public boolean isShoot()
	{
		return shoot;
	}

	/**
	 * Sets shoot which if true shoots and if false does not
	 * 
	 * @param shoot
	 *            Sets if shooting
	 */
	public void setShoot(boolean shoot)
	{
		this.shoot = shoot;
	}

	/**
	 * Returns the shoot angle in degrees
	 * 
	 * @return Returns the shoot angle in degrees
	 */
	public float getShootAngle()
	{
		return shootAngle;
	}

	/**
	 * Sets shoot angle in degrees
	 * 
	 * @param shootAngle
	 *            The shoot angle in degreess
	 */
	public void setShootAngle(float shootAngle)
	{
		this.shootAngle = shootAngle;
	}

	/**
	 * Returns bullet speed
	 * 
	 * @return Returns bullet speed
	 */
	public float getBulletSpeed()
	{
		return bulletSpeed;
	}

	/**
	 * Sets the bulletspeed
	 * 
	 * @param bulletSpeed
	 *            The speed of the bullet
	 */
	public void setBulletSpeed(float bulletSpeed)
	{
		this.bulletSpeed = bulletSpeed;
	}

	/**
	 * Returns the id of the enemy
	 * 
	 * @return Returns the id of the enemy
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Sets the id of the enemy
	 * 
	 * @param id
	 *            The id of the enemy
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Overrides the toString
	 * 
	 * @return Returns a string representation of this object
	 */
	public String toString()
	{
		return name;
	}

}
