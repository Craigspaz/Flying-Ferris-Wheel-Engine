package com.graphics.world.menu;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.input.InputHandler;
import com.main.Game;

/**
 * A button for a menu like the main menu
 * 
 * @author Craig Ferris
 *
 */
public class MenuButton
{
	private Vector2f		position;
	private RectangleBox	collider;

	private Tile			Button;
	private Tile			redButton;
	private Tile			whiteButton;
	private ButtonState		currentState;
	private float			redheat		= 0;
	private float			whiteheat	= 0;

	/**
	 * Creates a new MenuButton
	 * 
	 * @param Button
	 *            the tile to use when rendering the button
	 */
	public MenuButton(Tile Button)
	{
		this.Button = Button;
		this.redButton = Button.clone();
		this.whiteButton = Button.clone();
		System.out.println(Button);
		System.out.println(redButton);
		System.out.println(whiteButton);
		redButton.setFrame(1);
		redButton.setAlpha(0);
		whiteButton.setFrame(2);
		whiteButton.setAlpha(0);
		this.collider = new RectangleBox(Button.getPosition(), Button.getSize());
		this.position = new Vector2f(Button.getPosition().x, Button.getPosition().y);
	}

	/**
	 * Updates the button
	 * 
	 * @param handler
	 *            A pointer to the input handler so the menubutton can check if an action was performed on it
	 */
	public void update(InputHandler handler)
	{
		// Handle mouse input
		// change ButtonState and heatState
		if (handler.getMousePosition().x > collider.getPosition().x && handler.getMousePosition().y > collider.getPosition().y && handler.getMousePosition().x < collider.getPosition().x + collider.getSize().x && handler.getMousePosition().y < collider.getPosition().y + collider.getSize().y)
		{
			if (redheat < 1)
			{
				redheat += 0.002;
			} else
			{
				redheat = 1;
				if (whiteheat < 1)
					whiteheat += 0.005;
				else
					whiteheat = 1;
			}

		} else
		{
			if (whiteheat > 0)
			{
				whiteheat -= 0.05;
			} else
			{
				whiteheat = 0;
				if (redheat > 0)
				{
					redheat -= 0.05;
				} else
				{
					redheat = 0;
				}
			}
		}
		redButton.setAlpha(redheat);
		whiteButton.setAlpha(whiteheat);
	}

	/**
	 * Draws the button to the screen
	 */
	public void render()
	{
		Button.render();
		redButton.render();
		whiteButton.render();
	}

	/**
	 * Returns the position of the button
	 * 
	 * @return Returns the position of the button
	 */
	public Vector2f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the button
	 * 
	 * @param position
	 *            The position to set the button
	 */
	public void setPosition(Vector2f position)
	{
		this.position = position;
		this.Button.setPosition(new Vector3f(position.x, position.y, 0));
		this.redButton.setPosition(new Vector3f(position.x, position.y, 0));
		this.whiteButton.setPosition(new Vector3f(position.x, position.y, 0));
		this.collider.setPosition(new Vector3f(position.x, position.y, 0));
	}

	/**
	 * Returns the box collider around the button
	 * 
	 * @return Returns the box collider around the button
	 */
	public RectangleBox getCollider()
	{
		return collider;
	}

	/**
	 * Sets the box collider around the button
	 * 
	 * @param collider
	 *            The new box collider around the button
	 */
	public void setCollider(RectangleBox collider)
	{
		this.collider = collider;
	}

	/**
	 * Returns the currentbutton state
	 * 
	 * @return Returns the current buttonstate
	 */
	public ButtonState getCurrentState()
	{
		return currentState;
	}

	public Vector2f getSize()
	{
		return Button.getSize();
	}

	/**
	 * Sets the current buttonstate
	 * 
	 * @param currentState
	 *            The new buttonstate
	 */
	public void setCurrentState(ButtonState currentState)
	{
		this.currentState = currentState;
	}

}
