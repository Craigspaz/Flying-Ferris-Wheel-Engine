package com.threads;

import java.util.ArrayList;

import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.enemys.Enemy;

public class PathFindingThread implements Runnable
{
	private ArrayList<RectangleBox> colliders;
	private Player player;
	private Enemy enemy;
	private Thread thread;
	
	public void start(Enemy enemy, ArrayList<RectangleBox> colliders, Player player)
	{
		this.enemy = enemy;
		this.colliders = colliders;
		this.player = player;
		if(enemy == null)
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
		enemy.setPath(enemy.generatePath(colliders,player));
	}
	
}