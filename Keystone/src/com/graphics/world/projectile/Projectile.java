package com.graphics.world.projectile;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.world.Entity;
import com.graphics.world.RectangleBox;

/**
 * Basic Projectile class
 * @author Craig Ferris
 *
 */
public class Projectile extends Entity
{
	
	private float angle;

	public Projectile(Vector3f position, Texture texture, Vector2f size, Vector2f scale, Vector2f sizeOfSpriteOnSheet,float angle) 
	{
		super(position, texture, size, scale, sizeOfSpriteOnSheet);
		this.angle = angle;
	}

	
	public Projectile(Vector3f position, Texture texture, Vector2f sizeOfTexture, int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet,float angle) 
	{
		super(position, texture, sizeOfTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
		this.angle = angle;
	}
	
	public void update(ArrayList<RectangleBox> colliders)
	{
		super.update(colliders);
		move();
	}
	
	public void moveUpRight()
	{
		velocity.x += walkSpeed;
		velocity.y -= walkSpeed;
		if(velocity.x > MAX_SPEED_X)
		{
			velocity.x = MAX_SPEED_X;
		}
		if(velocity.y < -MAX_SPEED_X)
		{
			velocity.y = -MAX_SPEED_X;
		}
	}
	
	public void moveDownRight()
	{
		velocity.x += walkSpeed;
		velocity.y += walkSpeed;
		if(velocity.x > MAX_SPEED_X)
		{
			velocity.x = MAX_SPEED_X;
		}
		if(velocity.y > MAX_SPEED_X)
		{
			velocity.y = MAX_SPEED_X;
		}
	}
	
	public void moveUpLeft()
	{
		velocity.x -= walkSpeed;
		velocity.y -= walkSpeed;
		if(velocity.x < -MAX_SPEED_X)
		{
			velocity.x = -MAX_SPEED_X;
		}
		if(velocity.y < -MAX_SPEED_X)
		{
			velocity.y = -MAX_SPEED_X;
		}
	}
	
	public void moveDownLeft()
	{
		velocity.x -= walkSpeed;
		velocity.y += walkSpeed;
		if(velocity.x < -MAX_SPEED_X)
		{
			velocity.x = -MAX_SPEED_X;
		}
		if(velocity.y > MAX_SPEED_X)
		{
			velocity.y = MAX_SPEED_X;
		}
	}
	
	public void move()
	{
		if(angle == 0)
		{
			super.moveRight();
		}
		else if(angle == 45)
		{
			this.moveUpRight();
		}
		else if(angle == 90)
		{
			super.moveUp();
		}
		else if(angle == 135)
		{
			this.moveUpLeft();
		}
		else if(angle == 180)
		{
			super.moveLeft();
		}
		else if(angle == 225)
		{
			this.moveDownLeft();
		}
		else if(angle == 270)
		{
			super.moveDown();
		}
		else if(angle == 315)
		{
			this.moveDownRight();
		}
	}


	public float getAngle() {
		return angle;
	}


	public void setAngle(float angle) {
		this.angle = angle;
	}
	
}
