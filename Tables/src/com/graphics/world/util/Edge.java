package com.graphics.world.util;

import java.util.ArrayList;

/**
 * An edge of a graph. Note these edges are directed
 * 
 * @author Craig Ferris
 *
 */
public class Edge
{
	private Vertex						source;
	private Vertex						destination;
	private ArrayList<EnemyMovement>	enemyMovement;

	private int							weight;

	/**
	 * Creates a new edge between the source and destination Vertexs
	 * 
	 * @param source
	 *            The source Vertex
	 * @param destination
	 *            The destination Vertex
	 * @param weight
	 *            The weight of taveling over this edge
	 */
	public Edge(Vertex source, Vertex destination, int weight)
	{
		if (source == null)
		{
			throw new NullPointerException("Can't have an edge that has no source");
		}
		if (destination == null)
		{
			throw new NullPointerException("Can't have an edge that has no destination");
		}
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		enemyMovement = new ArrayList<EnemyMovement>();
	}

	/**
	 * Returns the source Vertex of the edge
	 * 
	 * @return Returns the source Vertex of the edge
	 */
	public Vertex getSource()
	{
		return source;
	}

	/**
	 * Returns the destination Vertex of the edge
	 * 
	 * @return Returns the destination Vertex of the edge
	 */
	public Vertex getDestination()
	{
		return destination;
	}

	/**
	 * Returns the weight of the edge
	 * 
	 * @return Returns the weight of the edge
	 */
	public int getWeight()
	{
		return weight;
	}

	/**
	 * Overrides the tostring method in object
	 */
	public String toString()
	{
		return "[Edge Source: " + source + " Destination: " + destination + " Weight: " + weight + "]";
	}

	/**
	 * Returns the possible enemy movements
	 * 
	 * @return Returns the possible enemy movements
	 */
	public ArrayList<EnemyMovement> getEnemyMovement()
	{
		return enemyMovement;
	}

	/**
	 * Adds enemy movements
	 * 
	 * @param enemyTypeID
	 *            The type of enemy
	 * @param method
	 *            The method to move
	 */
	public void addEnemyMovementMethod(int enemyTypeID, MovementMethod method)
	{
		enemyMovement.add(new EnemyMovement(enemyTypeID, method));
	}
}
