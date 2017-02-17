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
	
	private boolean canJump = true;
	
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
	 * @param tiles The world tiles to check for collisions
	 */
	public void input(ArrayList<Tile> tiles)
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
		
		// If the left arrow key is down move the player left
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			super.moveLeft();
		}
		// If the right arrow is down move the player right
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			super.moveRight();
		}
		

		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) == false)
		{
			canJump = true;
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
		for(Tile t: tiles)
		{
			if(super.getCollider().isCollidingWithBox(t.getCollider()))
			{
				//System.out.println("Player with collider: " + collider + " is colliding with collider: " + t.getCollider());
				//System.out.println("Player is rendered at: (" + position.x + ", " + position.y + ", " + position.z + ") (" + getScale().x + ", " + getScale().y + ")");

				super.getCollider().setPosition(new Vector3f(super.getPosition().x,super.getPosition().y, super.getPosition().z));
				
				//System.out.println("New Collider position: " + collider);
				if(jumping)
				{
					jumping = false;
					jumpTimer = 0;
				}
				break;
			}
		}
		super.setPosition(new Vector3f(super.getCollider().getPosition().x, super.getCollider().getPosition().y,super.getCollider().getPosition().z));
	}	
	
	public void update(ArrayList<Tile> tiles)
	{
		input(tiles);
		super.update(tiles);
	}
}
