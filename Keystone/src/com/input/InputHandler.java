package com.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Handles input from the user
 * 
 * @author Craig Ferris
 *
 */
public class InputHandler
{
	/**
	 * Creates a new Input Handler
	 */
	public InputHandler()
	{
		if (!Keyboard.isCreated())
		{
			try
			{
				Keyboard.create();
			} catch (LWJGLException e)
			{
				System.out.println("Keyboard failed to create");
				e.printStackTrace();
			}
		}
		if (!Controllers.isCreated())
		{
			try
			{
				Controllers.create();
			} catch (LWJGLException e)
			{
				System.out.println("Controller failed to create");
				e.printStackTrace();
			}
		}
		if (!Mouse.isCreated())
		{
			try
			{
				Mouse.create();
			} catch (LWJGLException e)
			{
				System.out.println("Mouse failed to create");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns if the player should move left
	 * 
	 * @return Returns if the player should move left
	 */
	public boolean left()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LEFT);
	}

	/**
	 * Returns if the player should move right
	 * 
	 * @return Returns if the player should move right
	 */
	public boolean right()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
	}

	/**
	 * Returns if the game should pause
	 * 
	 * @return Returns if the game should pause
	 */
	public boolean pause()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}

	/**
	 * Returns if the player should be sprinting
	 * 
	 * @return Returns if the player should be sprinting
	 */
	public boolean sprint()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}

	/**
	 * Returns if up is pressed
	 * 
	 * @return Returns if up is pressed
	 */
	public boolean up()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_UP);
	}

	/**
	 * Returns if down is pressed
	 * 
	 * @return Returns if down is pressed
	 */
	public boolean down()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_DOWN);
	}

	/**
	 * Returns if the player should aim down
	 * 
	 * @return Returns if the player should aim down
	 */
	public boolean aimDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_S);
	}

	/**
	 * Returns if the player should jump
	 * 
	 * @return Returns if the player should jump
	 */
	public boolean jump()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_SPACE);
	}

	/**
	 * Returns if the player should shoot
	 * 
	 * @return Returns if the player should shoot
	 */
	public boolean shoot()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_E);
	}
}
