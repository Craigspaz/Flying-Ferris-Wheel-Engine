package com.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.main.Window;

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

	StringBuilder	cmd;
	String			previous;

	public InputHandler()
	{

		cmd = new StringBuilder();
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

	public boolean tildedown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_GRAVE);
	}

	public boolean escape()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}

	public boolean enter()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_RETURN);
	}

	public boolean backspace()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_BACK);
	}

	public String getCommand()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (cmd.length() < 58)// fits in terminal window
				{
					if ((Keyboard.getEventKey() < 54 && Keyboard.getEventKey() > 1 && Keyboard.getEventKey() != 14 && Keyboard.getEventKey() != 15 && Keyboard.getEventKey() != 28 && Keyboard.getEventKey() != 29 && Keyboard.getEventKey() != 41 && Keyboard.getEventKey() != 42)
							|| Keyboard.getEventKey() == 57)
					{

						cmd.append(Keyboard.getEventCharacter());
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					cmd.replace(0, cmd.length(), previous);
				}
				if (Keyboard.getEventKey() == 14 && cmd.length() > 0)
				{
					cmd.deleteCharAt(cmd.length() - 1);
				}
			}
		}
		return cmd.toString();
	}

	public void setPrevious(String str)
	{
		this.previous = str;
	}

	public void clearBuffer()
	{
		cmd.delete(0, cmd.length());
		while (Keyboard.next())
		{
		}
	}

	public boolean isMouseLeftClicking()
	{
		return Mouse.isButtonDown(0);
	}

	public Vector2f getMousePosition()
	{
		int Mousex = Mouse.getX();
		int Mousey = Window.height - Mouse.getY() - 1;
		return new Vector2f(Mousex, Mousey);
	}

}
