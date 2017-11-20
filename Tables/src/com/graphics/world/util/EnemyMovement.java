package com.graphics.world.util;

/**
 * Stores information on how an enemy should travel between two nodes in a movement graph
 * 
 * @author Craig Ferris
 *
 */
public class EnemyMovement
{
	private int				enemyTypeID;	// A value from the values in Enemies
	private MovementMethod	movementMethod;

	/**
	 * Creates an EnemyMovement
	 * 
	 * @param enemyTypeID
	 *            The type of enemy
	 * @param movementMethod
	 *            The method the enemy will use to travel
	 */
	public EnemyMovement(int enemyTypeID, MovementMethod movementMethod)
	{
		if (movementMethod == null)
		{
			throw new NullPointerException("Can't create an enemy movement if the movement type is null");
		}
		if (enemyTypeID < 0)
		{
			throw new IndexOutOfBoundsException("Can't have an enemy type id less than 0");
		}
		this.enemyTypeID = enemyTypeID;
		this.movementMethod = movementMethod;
	}

	/**
	 * Returns the type of enemy
	 * 
	 * @return Returns the type of enemy
	 */
	public int getEnemyTypeID()
	{
		return enemyTypeID;
	}

	/**
	 * Sets the type of enemy
	 * 
	 * @param enemyTypeID
	 *            The type of enemy
	 */
	public void setEnemyTypeID(int enemyTypeID)
	{
		this.enemyTypeID = enemyTypeID;
	}

	/**
	 * Returns the method of moving the enemy will take between two nodes
	 * 
	 * @return Returns the method of moving the enemy will take between two nodes
	 */
	public MovementMethod getMovementMethod()
	{
		return movementMethod;
	}

	/**
	 * Sets the method of moving the enemy will take between two nodes
	 * 
	 * @param movementMethod
	 *            The new method of moving
	 */
	public void setMovementMethod(MovementMethod movementMethod)
	{
		this.movementMethod = movementMethod;
	}

	/**
	 * Overrides the tostring in object
	 */
	public String toString()
	{
		if (movementMethod == MovementMethod.FALL)
		{
			return "FALL";
		} else if (movementMethod == MovementMethod.JUMP)
		{
			return "JUMP";
		}
		return "WALK";
	}
}
