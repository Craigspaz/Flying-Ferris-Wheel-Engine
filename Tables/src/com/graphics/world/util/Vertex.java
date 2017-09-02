package com.graphics.world.util;

import java.util.ArrayList;

import com.graphics.world.Tile;

/**
 * A vertex of a graph
 * @author Craig Ferris
 *
 */
public class Vertex
{
	private Tile tile;
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private int fCost;
	private int gCost;
	private Vertex parent;
	
	/**
	 * Creates a new vertex
	 * @param tile The tile mapped to the vertex
	 */
	public Vertex(Tile tile)
	{
		this.tile = tile;
	}
	
	/**
	 * Adds an edge to the vertex
	 * @param e The edge to add to the vertex
	 */
	public void addEdge(Edge e)
	{
		edges.add(e);
	}

	/**
	 * Returns the tile mapped to the vertex
	 * @return Returns the tile mapped to the vertex
	 */
	public Tile getTile()
	{
		return tile;
	}
	
	/**
	 * Returns a list of edges attached to the vertex
	 * @return Returns a list of edges attached to the vertex
	 */
	public ArrayList<Edge> getEdges()
	{
		return edges;
	}

	/**
	 * Sets the edges attached to the vertex
	 * @param edges The edges attached to the vertex
	 */
	public void setEdges(ArrayList<Edge> edges)
	{
		this.edges = edges;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Tile: " + tile + "Edges: ");
		for(Edge e : edges)
		{
			builder.append(e + ", ");
		}
		return builder.toString();
	}

	/**
	 * Returns the f cost
	 * @return Returns the f cost
	 */
	public int getfCost()
	{
		return fCost;
	}

	/**
	 * Sets the f cost
	 * @param fCost The f cost
	 */
	public void setfCost(int fCost)
	{
		this.fCost = fCost;
	}

	/**
	 * Returns the G cost
	 * @return Returns the G cost
	 */
	public int getgCost()
	{
		return gCost;
	}

	/**
	 * Sets the G cost
	 * @param gCost The new G cost
	 */
	public void setgCost(int gCost)
	{
		this.gCost = gCost;
	}

	/**
	 * Returns the parent vertex
	 * @return Returns the parent vertex
	 */
	public Vertex getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent vertex
	 * @param parent The new parent vertex
	 */
	public void setParent(Vertex parent)
	{
		this.parent = parent;
	}
	
	
}
