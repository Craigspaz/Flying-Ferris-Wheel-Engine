package com.util;

import java.util.ArrayList;

import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.graphics.world.enemys.Enemy;
import com.graphics.world.util.Edge;
import com.graphics.world.util.Vertex;

public class Utils
{	
	
	/**
	 * Returns the vertex associated with a tile in the list of vertices
	 * @param t The tile you are looking for
	 * @param vertices The list of vertices
	 * @return Returns the vertex associated with a tile in the list of vertices
	 */
	public static Vertex findTileVertexInVertices(Tile t, ArrayList<Vertex> vertices)
	{
		if(vertices == null)
		{
			throw new NullPointerException("Can't search for a tile in a null array");
		}
		for(Vertex v: vertices)
		{
			if(v.getTile() == t)
			{
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Returns the Manhattan distance between a source tile and the destination tile
	 * @param source The source tile
	 * @param dest The destination tile
	 * @return Returns the Manhattan distance between a source tile and the destination tile
	 */
	public static int manhattanDistance(Tile source, Tile dest)
	{
		if(source == null)
		{
			throw new NullPointerException("Can't find the manhattan distance between two points if the source is null");
		}
		if(dest == null)
		{
			throw new NullPointerException("Can't find the manhattan distance between two points if the destination is null");
		}
		return Math.abs((int)source.getPosition().x - (int)dest.getPosition().x) + Math.abs((int)source.getPosition().y - (int)dest.getPosition().y);
	}
	
	/**
	 * Returns true if an edge is in the list of vertices
	 * @param list The list of vertices
	 * @param element The edge to look for
	 * @return Returns true if an edge is in the list of vertices
	 */
	private static boolean isVertexInList(ArrayList<Vertex> list, Edge element)
	{
		if(list == null)
		{
			throw new NullPointerException("Can't search a null list");
		}
		for(Vertex v : list)
		{
			if(v.getTile() == element.getDestination().getTile())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculates the path for enemies to take to get from their current location to the player. This uses the A* pathfinding algorithm
	 * @param e A pointer to the enemy involved in the path
	 * @param p A pointer to the player which is the destination
	 * @param vertices The list of the world vertices
	 * @param colliders The list of colliders in the world
	 * @return Returns the path from the enemy to the player
	 */
	public static ArrayList<RectangleBox> calculateShortestPathToPlayer(Enemy e, Player p, ArrayList<Vertex> vertices,ArrayList<RectangleBox> colliders)
	{
		if(e == null)
		{
			throw new NullPointerException("Can't calculate the shortest path if the enemy is null");
		}
		if(p == null)
		{
			throw new NullPointerException("Can't generate the path to the player if the player is null");
		}
		if(vertices == null)
		{
			throw new NullPointerException("Can't generate a path if the graph is null");
		}
		if(colliders == null)
		{
			throw new NullPointerException("Can't generate a path if the colliders are null");
		}
		System.out.println("Running A*...");
		Vertex sourceVertex = e.getCurrentVertex();
		Vertex destinationVertex = p.getCurrentVertex();

		if(sourceVertex == null || destinationVertex == null)
		{
			if(sourceVertex == null && destinationVertex != null)
			{
				return null;
			}
			ArrayList<RectangleBox> ret = new ArrayList<RectangleBox>();
			//ret.add(destinationVertex.getTile().getCollider());
			return ret;
		}
		
		if(sourceVertex == destinationVertex) // Checks if the source is the destination
		{
			ArrayList<RectangleBox> ret = new ArrayList<RectangleBox>();
			//ret.add(destinationVertex.getTile().getCollider());
			return ret;
		}
		
		
		ArrayList<Vertex> openlist = new ArrayList<Vertex>();
		ArrayList<Vertex> closedlist = new ArrayList<Vertex>();
		
		sourceVertex.setfCost(manhattanDistance(sourceVertex.getTile(), destinationVertex.getTile()));
		
		openlist.add(sourceVertex);

		while(!openlist.isEmpty())
		{
			Vertex currentVertex = null;
			int lowestF = Integer.MAX_VALUE;
			for(Vertex v : openlist)
			{
				if(v.getfCost() < lowestF)
				{
					lowestF = v.getfCost();
					currentVertex = v;
				}
			}
			openlist.remove(currentVertex);
			closedlist.add(currentVertex);
			
			//checks all of the adjacent vertices to see if it is directly connected to the destination
			//while doing this it adds any vertices not seen yet to the openlist
			
			for(Edge edge : currentVertex.getEdges())
			{
				if(edge.getDestination().getTile() == destinationVertex.getTile()) // Check if edge leads to destination
				{
					destinationVertex.setParent(currentVertex);
				}
				else
				{
					int gscore = currentVertex.getgCost() + edge.getWeight();
					//Check if destination vertex is already in the openlist
					boolean alreadyInOpenlist = isVertexInList(openlist,edge);
					//Check if the destination vertex is already in the closedlist
					boolean alreadyInClosedList = isVertexInList(closedlist, edge);
					if(!alreadyInOpenlist && !alreadyInClosedList)
					{
						for(Vertex v : vertices)
						{
							if(v.getTile() == edge.getDestination().getTile())
							{
								openlist.add(v);
								break;
							}
						}
					}
					else if(!alreadyInOpenlist || gscore < edge.getDestination().getgCost())
					{
						// Check if the path from the current vertex is better than previously checked paths
						if(edge.getDestination().getgCost() > gscore)
						{
							edge.getDestination().setParent(currentVertex);
							edge.getDestination().setgCost(gscore);
							edge.getDestination().setfCost(manhattanDistance(edge.getDestination().getTile(),destinationVertex.getTile()) + edge.getDestination().getgCost());
							if(!alreadyInOpenlist)
							{
								openlist.add(edge.getDestination());
							}
						}
					}		
				}
			}
			
		}
		
		RectangleBox[] tmp = new RectangleBox[vertices.size()];
		int index = 0;
		Vertex current = destinationVertex;
		while(current != null)
		{
//			RectangleBox c = current.getTile().getCollider();
//			for(RectangleBox r : colliders)
//			{
//				if((int)r.getPosition().x == (int)c.getPosition().x && (int)r.getPosition().y == (int)c.getPosition().y)
//				{
//					tmp[index++] = r;
//					break;
//				}
//			} 
			current = current.getParent();
		}
		
		ArrayList<RectangleBox> ret = new ArrayList<RectangleBox>();
		for(int i = index - 1; i >= 0; i--)
		{
			ret.add(tmp[i]);
		}
		
		//Cleanup
		for(Vertex v : vertices)
		{
			v.setfCost(0);
			v.setgCost(0);
			v.setParent(null);
		}
		
		return ret;
	}
}
