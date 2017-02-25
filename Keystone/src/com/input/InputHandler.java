package com.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHandler
{
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

	public boolean left()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Controllers.getController(2).isButtonPressed(0);
	}

	public boolean right()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Controllers.getController(2).isButtonPressed(1);
	}

	public boolean pause()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Controllers.getController(2).isButtonPressed(7);
	}

	public boolean select()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_TAB) || Controllers.getController(2).isButtonPressed(6);
	}
	
	public boolean sprint()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}

	public boolean up()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_UP);
	}

	public boolean down()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_DOWN);
	}
	
	public boolean aimDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_S);
	}
	
	public boolean jump()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_SPACE);
	}
	
	public boolean shoot()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_E) || Controllers.getController(2).isButtonPressed(2);
	}
}
