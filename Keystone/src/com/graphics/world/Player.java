package com.graphics.world;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;

/**
 * Handles the player input and player specifics
 * @author Craig Ferris
 *
 */
public class Player extends Entity
{	
	
	/**
	 * Creates a new player
	 * @param position The initial position of the player
	 * @param texture The texture of the player
	 * @param size The size of the the sprite on the texture
	 * @param scale The size to draw the player
	 */
	public Player(Vector3f position, Texture texture, Vector2f size, Vector2f scale)
	{
		super(position,texture,size,scale);
		affectedByGravity = true;
	}
	
	/**
	 * Creates a new player
	 * @param position The initial position of the player
	 * @param texture The texture of the player
	 * @param sizeOfTexture The size of the texture
	 * @param numberOfSprites The number of sprites on the texture
	 * @param scale The size at which to draw the player
	 */
	public Player(Vector3f position, Texture texture, Vector2f sizeOfTexture, int numberOfSprites, Vector2f scale)
	{
		super(position,texture,sizeOfTexture,numberOfSprites,scale);
		affectedByGravity = true;
	}
	
	/**
	 * Handles input from the user
	 * @param tiles The world collision boxes to check for collisions
	 */
	public void input(ArrayList<RectangleBox> tiles)
	{
		//If left shift is held the player is sprinting
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			super.setSprinting(true);
		}
		else
		{
			super.setSprinting(false);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) == false && Keyboard.isKeyDown(Keyboard.KEY_LEFT) == false)
		{
			velocity.x = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			super.moveRight();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			super.moveLeft();
		}
		

		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) == false)
		{
			canJump = true;
			jumping = false;
		}
		// If the space bar is pressed make the player jump
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			if(canJump)
			{
				super.jump();
				canJump = false;
			}
		}
	}	
	
	public void update(ArrayList<RectangleBox> colliders)
	{
		input(colliders);
		super.update(colliders);
	}
}
