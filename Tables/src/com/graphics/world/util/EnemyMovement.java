package com.graphics.world.util;

public class EnemyMovement
{
	private int enemyTypeID; // A value from the values in Enemies
	private MovementMethod movementMethod;
	
	public EnemyMovement(int enemyTypeID, MovementMethod movementMethod)
	{
		this.enemyTypeID = enemyTypeID;
		this.movementMethod = movementMethod;
	}

	public int getEnemyTypeID()
	{
		return enemyTypeID;
	}

	public void setEnemyTypeID(int enemyTypeID)
	{
		this.enemyTypeID = enemyTypeID;
	}

	public MovementMethod getMovementMethod()
	{
		return movementMethod;
	}

	public void setMovementMethod(MovementMethod movementMethod)
	{
		this.movementMethod = movementMethod;
	}
	
	public String toString()
	{
		if(movementMethod == MovementMethod.FALL)
		{
			return "FALL";
		}
		else if(movementMethod == MovementMethod.JUMP)
		{
			return "JUMP";
		}
		return "WALK";
	}
}
