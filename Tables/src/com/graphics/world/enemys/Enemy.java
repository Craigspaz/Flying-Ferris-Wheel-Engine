package com.graphics.world.enemys;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.Textures;
import com.graphics.world.Entity;
import com.graphics.world.Particle;
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.projectile.Projectile;
import com.graphics.world.util.Vertex;
import com.threads.PathFindingThread;
import com.util.Utils;

/**
 * Handles enemy
 * 
 * @author Craig Ferris
 *
 */
public class Enemy extends Entity
{
	private boolean						moveLeft		= false;
	private boolean						moveRight		= false;
	private boolean						isJumping		= false;
	private boolean						isUp			= false;

	private ArrayList<Projectile>		projectiles		= new ArrayList<Projectile>();
	private int							shootingDelay	= 5;
	private int							shootingCounter	= 0;
	private boolean						canShoot		= true;
	private boolean						shoot			= false;
	private float						shootAngle		= 0;
	private float						bulletSpeed		= 16;
	private int							id;
	protected String					name			= "";

	protected ArrayList<RectangleBox>	path			= null;

	private int							ticks			= (int) Math.floor(-JUMP_VALUE / GRAVITY);
	private int							maxHeight		= (int) ((0.5f) * GRAVITY * ticks * ticks);
	private int							maxDistance		= (int) (ticks * MAX_SPEED_X);

	/**
	 * Creates a new enemy based on an already created entity
	 * 
	 * @param e
	 *            The entity
	 */
	public Enemy(Entity e)
	{
		super(e.getPosition(), e.getTexture(), e.getOutlineTexture(), e.getNumberOfSpritesX(), e.getNumberOfSpritesY(), e.getScale(), e.getSizeOfSpriteOnSheet());
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
	 * @param scale
	 *            The size to render the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the sprite sheet
	 */
	public Enemy(Vector3f position, Texture texture, Vector2f scale, Vector2f sizeOfSpriteOnSheet)
	{
		super(position, texture, scale, sizeOfSpriteOnSheet);
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
	 *            The outline texture of the enemy
	 * @param numberOfSpritesX
	 *            The number of sprites in the animation in the x direction
	 * @param numberOfSpritesY
	 *            The row in the spritesheet (zero based)
	 * @param scale
	 *            The size to render the sprite
	 * @param sizeOfSpriteOnSheet
	 *            The size of the sprite on the spritesheet
	 */
	public Enemy(Vector3f position, Texture texture, Texture outlineTexture, int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet)
	{
		super(position, texture, outlineTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
		this.id = -1;
	}

	/**
	 * Handles movement
	 */
	private void move(Player player)
	{
		if (path != null)
		{
			System.out.println("PATH: ");
			for (RectangleBox b : path)
			{
				System.out.print(b + "->");
			}
			System.out.println();
		}
		RectangleBox targetPlatform = null;
		if (path != null && path.size() > 0)
		{
			targetPlatform = path.get(path.size() - 1);
			System.out.println("heading for: " + targetPlatform);
			if (this.getPosition().getY() > targetPlatform.getPosition().getY()) // Is the enemy below the target
																					// platform
			{
				boolean rightSide = false;
				boolean leftSide = false;
				// check if platform below extends over both side of the current platform and chose a path down
				if (this.getCurrentFloor().getPosition().getX() > targetPlatform.getPosition().getX()) // does the
																										// bottom
																										// platform
																										// extend out
																										// the right
																										// side of the
																										// top platform
				{
					rightSide = true;
				}
				if (this.getCurrentFloor().getPosition().getX() + this.getCurrentFloor().getSize().getX() < targetPlatform.getPosition().getX() + targetPlatform.getSize().getX()) // does
																																													// the
																																													// bottom
																																													// platform
																																													// extend
																																													// out
																																													// of
																																													// the
																																													// left
																																													// side
																																													// of
																																													// the
																																													// top
																																													// platform
				{
					leftSide = true;
				}

				if (leftSide && rightSide)
				{
					if (leftSide && rightSide)
					{
						if (this.getPosition().getX() < player.getPosition().getX())
						{
							this.moveRight();
						} else
						{
							this.moveLeft();
						}
					} else if (leftSide)
					{
						this.moveLeft();
					} else if (rightSide)
					{
						this.moveRight();
					}
				}
			} else if (this.getPosition().getY() < targetPlatform.getPosition().getY()) // Is the enemy above the target
																						// platform
			{
				boolean rightSide = false;
				boolean leftSide = false;
				// check if platform below extends over both side of the current platform and chose a path down
				if (this.getCurrentFloor().getPosition().getX() > targetPlatform.getPosition().getX()) // does the
																										// bottom
																										// platform
																										// extend out
																										// the left side
																										// of the top
																										// platform
				{
					leftSide = true;
				}
				if (this.getCurrentFloor().getPosition().getX() + this.getCurrentFloor().getSize().getX() < targetPlatform.getPosition().getX() + targetPlatform.getSize().getX()) // does
																																													// the
																																													// bottom
																																													// platform
																																													// extend
																																													// out
																																													// of
																																													// the
																																													// right
																																													// side
																																													// of
																																													// the
																																													// top
																																													// platform
				{
					rightSide = true;
				}

				if (leftSide && rightSide)
				{
					if (this.getPosition().getX() < player.getPosition().getX())
					{
						this.moveRight();
					} else
					{
						this.moveLeft();
					}
				} else if (leftSide)
				{
					this.moveLeft();
				} else if (rightSide)
				{
					this.moveRight();
				}
			}
			/*
			 * if ((int)targetPlatform.getPosition().getX() != (int)getCurrentFloor().getPosition().getX() &&
			 * (int)targetPlatform.getPosition().getY() != (int)getCurrentFloor().getPosition().getY())// if it has a
			 * destination and it's not the current tile { //TODO move enemy to next tile in path RectangleBox
			 * nextPlatform = null; boolean useNext = false; for(RectangleBox p : path) { if(useNext) { nextPlatform =
			 * p; break; } if((int)p.getPosition().x == (int)getCurrentFloor().getPosition().x && (int)p.getPosition().y
			 * == (int)getCurrentFloor().getPosition().y) { useNext = true; } } if (nextPlatform.getPosition().x +
			 * targetPlatform.getSize().x < position.x) {// if the destination platform is to the left of this one
			 * super.moveLeft(); if (nextPlatform.getPosition().y - getCurrentFloor().getPosition().y < maxHeight &&
			 * position.x - (nextPlatform.getPosition().x + nextPlatform.getSize().x) < maxDistance) { if (!isInAir) {
			 * super.jump(); if (!isInAir) { particles.add(new Particle(new Vector2f(position.x + (getScale().x / 2) -
			 * 8, position.y + getScale().y - 16), new Vector2f(16, 16), Textures.particles, 12, 1, left, new
			 * Vector2f(16, 16), new Vector2f(256, 128), false)); } // System.out.println("jump " + jumpCount); } } }
			 * else if (nextPlatform.getPosition().x > position.x + getScale().x) { super.moveRight(); if
			 * (nextPlatform.getPosition().y - getCurrentFloor().getPosition().y < maxHeight &&
			 * nextPlatform.getPosition().x - (position.x + getScale().x) < maxDistance) { if (!isInAir) { super.jump();
			 * if (!isInAir) { particles.add(new Particle(new Vector2f(position.x + (getScale().x / 2) - 8, position.y +
			 * getScale().y - 16), new Vector2f(16, 16), Textures.particles, 12, 1, left, new Vector2f(16, 16), new
			 * Vector2f(256, 128), false)); } // System.out.println("jump " + jumpCount); } } } else {
			 * super.stopMoving(); }
			 * 
			 * } else { if((int)super.getPosition().getX() < (int)player.getPosition().getX()) { super.moveRight(); }
			 * else if((int)super.getPosition().getX() > (int)player.getPosition().getX()) { super.moveLeft(); } else {
			 * super.stopMoving(); } //super.stopMoving();// for now, it stops if it's on the right tile // TODO goes up
			 * to the player and attacks }
			 */
		} else

		{
			super.stopMoving();
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
				e.setAffectedByGravity(true);
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
	public void update(ArrayList<RectangleBox> colliders, Player player, ArrayList<Vertex> vertices)
	{
		// if(path == null)path = generatePath(colliders, player);
		/*
		 * if (path == null) { PathFindingThread pathThread = new PathFindingThread(); pathThread.start(this, colliders,
		 * player, vertices); System.out.println("Ran Thread"); path = new ArrayList<RectangleBox>();//tmp }
		 */
		path = Utils.calculateShortestPathToPlayer(this, player, vertices, colliders);
		if (path == null)
		{
			if (player.getCurrentVertex() != null && this.getCurrentVertex() != null)
			{
				System.out.println("Returned null " + player.getCurrentVertex().getTile() + " " + this.getCurrentVertex().getTile());
			}
			// if(position.x > player.getPosition().x)
			// {
			// moveRight = true;
			// moveLeft = false;
			// }
			// else if(position.x < player.getPosition().x)
			// {
			// moveLeft = true;
			// moveRight = false;
			// }
			// else
			// {
			// moveLeft = false;
			// moveRight = false;
			// }
		}
		move(player);
		super.update(colliders, vertices);
	}

	/**
	 * Returns the manhattan distance between two vector3f's
	 * 
	 * @param start
	 *            The first position
	 * @param end
	 *            The second position
	 * @return Returns the manhattan distance between the two points
	 */
	public static int getManhattanDistance(Vector3f start, Vector3f end)
	{
		return (int) (Math.abs(start.x - end.x) + Math.abs(start.y - end.y));
	}

	/**
	 * Returns a list of rectangleboxs that are a path between the enemy and the player
	 * 
	 * @param colliders
	 *            The list of world colliders
	 * @param player
	 *            A pointer to the player
	 * @return Returns a list of rectangleboxs that are a path between the enemy and the player
	 */
	public ArrayList<RectangleBox> generatePath(ArrayList<RectangleBox> colliders, Player player)
	{

		RectangleBox endCollider = player.getCurrentFloor();
		if (endCollider == null)
		{
			return null;
		}
		RectangleBox startCollider = this.getCurrentFloor();
		if (startCollider == null)
		{
			return null;
		}
		System.out.println("Ticks: " + ticks + " MaxHeight: " + maxHeight + " MaxDistance: " + maxDistance);
		// Node result = new Node(startCollider,ticks,maxDistance,maxHeight).generatePath(colliders, endCollider);
		// System.out.println("Destination: " + result + " Start: " + result.getParent());

		Node startNode = new Node(startCollider);
		// System.out.println("Start: " + startNode);
		Node result = generatePath(colliders, endCollider, startNode);
		// System.out.println("Destination: " + result);

		// System.out.println("Start Node: " + startNode);

		ArrayList<RectangleBox> resultNodes = new ArrayList<RectangleBox>();
		while (result != null)
		{
			if (result.collider != startCollider)
				resultNodes.add(result.collider);
			// System.out.println(result);
			result = result.getParent();
		}
		return resultNodes;
	}

	/**
	 * Returns a node in the path between root and destination
	 * 
	 * @param colliders
	 *            A pointer to the list of world colliders
	 * @param destination
	 *            The destination collider
	 * @param root
	 *            The current node to check
	 * @return Returns a node in the path between root and destination
	 */
	public Node generatePath(ArrayList<RectangleBox> colliders, RectangleBox destination, Node root)
	{
		Node result = null;
		if (root != null)
		{
			if (root.collider == destination)
			{
				return root;
			}
			RectangleBox c = new RectangleBox(new Vector3f(root.collider.getPosition().x - maxDistance, root.collider.getPosition().y - maxHeight, 0), new Vector2f(root.collider.getSize().x + (2 * maxDistance), maxHeight));
			Node tmp = null;
			int minDistance = Integer.MAX_VALUE;
			for (RectangleBox bo : colliders)
			{
				if (bo.isCollidingWithBox(c) && bo.getPosition().y >= c.getPosition().y)
				{
					int dist = getManhattanDistance(bo.getPosition(), destination.getPosition());
					if (dist < minDistance)
					{
						minDistance = dist;
						tmp = new Node(bo);
						tmp.setParent(root);
						// System.out.println("Found Node");
					}
				}
			}
			// System.out.println("Tmp: " + tmp);
			if (tmp == null)
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

	/**
	 * Returns the path the enemy will take
	 * 
	 * @return Returns the path the enemy will take
	 */
	public ArrayList<RectangleBox> getPath()
	{
		return path;
	}

	/**
	 * Sets the path the enemy will take
	 * 
	 * @param path
	 *            The path the enemey will take
	 */
	public void setPath(ArrayList<RectangleBox> path)
	{
		this.path = path;
	}

	/**
	 * Returns the max distance the enemy can travel
	 * 
	 * @return Returns the max distance the enemy can travel
	 */
	public int getMaxDistance()
	{
		return maxDistance;
	}

	/**
	 * Sets the max distance the enemy can travel
	 * 
	 * @param maxDistance
	 *            The max distance the enemy can travel
	 */
	public void setMaxDistance(int maxDistance)
	{
		this.maxDistance = maxDistance;
	}
}
