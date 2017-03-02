package com.graphics.world.enemys;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import com.graphics.Textures;
import com.graphics.world.projectile.Projectile;
/**
 * The Table test object
 * 
 * @author Kyle Falicov
 *
 */
public class Table extends Enemy
{

	public Table(float x, float y)
	{
		super(new Vector3f(x, y, 0), Textures.table, Textures.tableOutline, new Vector2f(256, 32), 6, 0, new Vector2f(32, 32), new Vector2f(32, 32));
		this.name = "Table";
		this.setAffectedByGravity(true);
	}

}
