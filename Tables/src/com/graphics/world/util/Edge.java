package com.graphics.world.util;

/**
 * An edge of a graph. Note these edges are directed
 * @author Craig Ferris
 *
 */
public class Edge
{
	private Vertex source;
	private Vertex destination;
	
	private int weight;
	
	/**
	 * Creates a new edge between the source and destination Vertexs
	 * @param source The source Vertex
	 * @param destination The destination Vertex
	 * @param weight The weight of taveling over this edge
	 */
	public Edge(Vertex source, Vertex destination, int weight)
	{
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		
	}

	/**
	 * Returns the source Vertex of the edge
	 * @return Returns the source Vertex of the edge
	 */
	public Vertex getSource()
	{
		return source;
	}

	/**
	 * Returns the destination Vertex of the edge
	 * @return Returns the destination Vertex of the edge
	 */
	public Vertex getDestination()
	{
		return destination;
	}

	/**
	 * Returns the weight of the edge
	 * @return Returns the weight of the edge
	 */
	public int getWeight()
	{
		return weight;
	}
	
	public String toString()
	{
		return "[Edge Source: " + source + " Destination: " + destination + " Weight: " + weight + "]";
	}
	
}
