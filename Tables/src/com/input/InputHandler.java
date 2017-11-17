package com.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.graphics.world.Camera;
import com.main.Game;
import com.main.Window;

/**
 * Handles input from the user
 * 
 * @author Craig Ferris
 *
 */
public class InputHandler
{

	StringBuilder	cmd;
	String			previous	= "";
	int				deleteTimer	= 0;
	boolean			startDelay	= true;
	Camera			camera;
	boolean			canEscape	= false;

	/**
	 * Creates a new Input Handler
	 */
	public InputHandler(Camera camera)
	{
		if (camera == null)
		{
			throw new NullPointerException("A input handler must have access to the camera to handle mouse input");
		}
		this.camera = camera;
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

	/**
	 * Tilde to activate the Terminal. Technically uses the backquote key, should probably have multiple activations to
	 * account for different keyboards
	 * 
	 * @return true if the tilde key is pressed
	 */
	public boolean tildedown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_GRAVE);
	}

	/**
	 * Returns if escape is pressed
	 * 
	 * @return true if escape is currently being pressed
	 */
	public boolean escape()
	{
		if (canEscape && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			canEscape = false;
			return true;
		} else
		{
			if (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			{
				canEscape = true;
			}
			return false;
		}
	}

	/**
	 * Returns if enter is being pressed
	 * 
	 * @return true if enter is being pressed
	 */
	public boolean enter()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_RETURN);
	}

	// public boolean backspace()
	// {
	// return Keyboard.isKeyDown(Keyboard.KEY_BACK);
	// }

	/**
	 * ses the specified key to determine dialogue progression
	 * 
	 * @return true if the "next page" predetermined key is being pressed
	 */
	public boolean nextPage()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_F);
	}

	/**
	 * reads backspace key
	 */
	public boolean backspace()
	{
		return Keyboard.isKeyDown(14);
	}

	/**
	 * reads in a command to the Terminal, ignoring certain special characters. Includes backspace functionality
	 * (keycode 14) and up arrow recognition to re-buffer the previous command
	 * 
	 * @return the currently typed string in the buffer
	 */
	public String getCommand()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (cmd.length() < 58)// fits in terminal window
				{
					if ((Keyboard.getEventKey() < 54 && Keyboard.getEventKey() > 1 && Keyboard.getEventKey() != 14 && Keyboard.getEventKey() != 15 && Keyboard.getEventKey() != 28 && Keyboard.getEventKey() != 29 && Keyboard.getEventKey() != 41 && Keyboard.getEventKey() != 42)
							|| Keyboard.getEventKey() == 57)// discounts everything not on the main keyboard, as well as
															// some special characters on the main board. 57 is spacebar
					{

						cmd.append(Keyboard.getEventCharacter());
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					cmd.replace(0, cmd.length(), previous);// if up is pressed, the entire command becomes the previous
															// command
				}
			}
		}
		if (backspace() && deleteTimer == 0)
		{
			if (startDelay)
				deleteTimer = 15;
			else
				deleteTimer = 2;
			startDelay = false;
			if (cmd.length() > 0)
				cmd.deleteCharAt(cmd.length() - 1);// backspace functionality
		} else
		{
			deleteTimer--;
		}
		if (!backspace())
		{
			startDelay = true;
			deleteTimer = 0;
		}
		return cmd.toString();
	}

	/**
	 * sets the previous entered terminal command, this is called in Terminal
	 * 
	 * @param str
	 *            the command entered
	 */
	public void setPrevious(String str)
	{
		this.previous = str;
	}

	/**
	 * used to clear from the Terminal when exiting
	 */
	public void clearBuffer()
	{
		cmd.delete(0, cmd.length());
		while (Keyboard.next())
		{
		}
	}

	/**
	 * Returns if the left mouse button is down
	 * 
	 * @return Returns if the left mouse button is down
	 */
	public boolean isMouseLeftClicking()
	{
		return Mouse.isButtonDown(0);
	}

	/**
	 * Returns the position of the mouse
	 * 
	 * @return Returns the position of the mouse
	 */
	public Vector2f getMousePosition()
	{
		int mouseX = Mouse.getX();
		int mouseY = Window.height - Mouse.getY() - 1;
		float trueX = (mouseX - camera.getPosition().x) / Game.SCALE;
		float trueY = (mouseY - camera.getPosition().y) / Game.SCALE;
		return new Vector2f(trueX, trueY);
	}

}
