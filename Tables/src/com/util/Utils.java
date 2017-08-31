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
	
	public static Vertex fileTileVertexInVertices(Tile t, ArrayList<Vertex> vertices)
	{
		for(Vertex v: vertices)
		{
			if(v.getTile() == t)
			{
				return v;
			}
		}
		return null;
	}
	
	public static int manhattanDistance(Tile source, Tile dest)
	{
		return Math.abs((int)source.getPosition().x - (int)dest.getPosition().x) + Math.abs((int)source.getPosition().y - (int)dest.getPosition().y);
	}
	
	public static ArrayList<RectangleBox> calculateShortestPathToPlayer(Enemy e, Player p, ArrayList<Vertex> vertices,ArrayList<RectangleBox> colliders)
	{
		Vertex sourceVertex = e.getCurrentVertex();
		Vertex destinationVertex = p.getCurrentVertex();

		if(sourceVertex == null || destinationVertex == null)
		{
			if(sourceVertex == null && destinationVertex != null)
			{
				return null;
			}
			ArrayList<RectangleBox> ret = new ArrayList<RectangleBox>();
			ret.add(destinationVertex.getTile().getCollider());
			return ret;
		}
		
		if(sourceVertex == destinationVertex) // Checks if the source is the destination
		{
			ArrayList<RectangleBox> ret = new ArrayList<RectangleBox>();
			ret.add(destinationVertex.getTile().getCollider());
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
					boolean alreadyInOpenlist = false;
					for(Vertex v : openlist)
					{
						if(v.getTile() == edge.getDestination().getTile())
						{
							alreadyInOpenlist = true;
							break;
						}
					}
					//Check if the destination vertex is already in the closedlist
					boolean alreadyInClosedList = false;
					for(Vertex v : closedlist)
					{
						if(v.getTile() == edge.getDestination().getTile())
						{
							alreadyInClosedList = true;
							break;
						}
					}
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
			RectangleBox c = current.getTile().getCollider();
			for(RectangleBox r : colliders)
			{
				if((int)r.getPosition().x == (int)c.getPosition().x && (int)r.getPosition().y == (int)c.getPosition().y)
				{
					tmp[index++] = r;
					break;
				}
			} 
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
