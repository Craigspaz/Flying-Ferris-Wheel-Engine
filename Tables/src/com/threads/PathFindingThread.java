package com.threads;

import java.util.ArrayList;

import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.enemys.Enemy;
import com.graphics.world.util.Vertex;
import com.util.Utils;

public class PathFindingThread implements Runnable
{
	private ArrayList<RectangleBox> colliders;
	private Player player;
	private Enemy enemy;
	private Thread thread;
	private ArrayList<Vertex> vertices;
	
	/**
	 * Starts the thread
	 * @param enemy The enemy to start the pathfinding from
	 * @param colliders The colliders of the world
	 * @param player A pointer to the player
	 * @param vertices The vertices of the movement graph
	 */
	public void start(Enemy enemy, ArrayList<RectangleBox> colliders, Player player, ArrayList<Vertex> vertices)
	{
		this.enemy = enemy;
		this.colliders = colliders;
		this.player = player;
		this.vertices = vertices;
		if(enemy == null || colliders == null || player == null || vertices == null)
		{
			System.out.println("Failed to Start PathFinding Thread");
			throw new NullPointerException();
		}
		if(thread == null)
		{
			thread = new Thread(this);
		}
		thread.start();
	}
	@Override
	public void run()
	{
		System.out.println("DEBUG1......");
		//enemy.setPath(enemy.generatePath(colliders,player));
		ArrayList<RectangleBox> value = Utils.calculateShortestPathToPlayer(enemy, player, vertices,colliders);
		System.out.println("DEBUG: " + value);
		enemy.setPath(value);
	}
	
}
