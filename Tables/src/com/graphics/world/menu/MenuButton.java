package com.graphics.world.menu;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.input.InputHandler;

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

	private Tile			coldButton;
	private Tile			redButton;
	private Tile			whiteButton;

	// these are only used when button is toggleable
	private Tile			Button;
	private Tile			Button2;

	private boolean			isToggled	= false;

	private boolean			toggleable	= false;
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
		if (Button == null)
		{
			throw new NullPointerException("The button can't have a null tile");
		}
		this.Button = Button;
		this.coldButton = Button.clone();
		this.redButton = Button.clone();
		this.whiteButton = Button.clone();
		redButton.setFrame(1);
		redButton.setAlpha(0);
		whiteButton.setFrame(2);
		whiteButton.setAlpha(0);
		this.collider = new RectangleBox(Button.getPosition(), Button.getSize());
		this.position = new Vector2f(Button.getPosition().x, Button.getPosition().y);
	}

	/**
	 * Creates a new MenuButton
	 * 
	 * @param Button
	 *            The tile to use when rendering the button
	 * @param Button2
	 *            The other tile to use when rendering the button
	 */
	public MenuButton(Tile Button, Tile Button2)
	{
		if (Button == null)
		{
			throw new NullPointerException("The button can't have a null tile");
		}
		if (Button2 == null)
		{
			throw new NullPointerException("The button can't have a null tile");
		}

		this.Button = Button.clone();
		this.Button2 = Button2.clone();// the second button can be copied into cold, red, white at any time
		this.coldButton = Button.clone();
		this.redButton = Button.clone();
		this.whiteButton = Button.clone();

		toggleable = true;

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
		if (handler == null)
		{
			throw new NullPointerException("A button needs access to the input handler");
		}
		// Handle mouse input
		// change ButtonState and heatState
		if (handler.getMousePosition().x > collider.getPosition().x && handler.getMousePosition().y > collider.getPosition().y && handler.getMousePosition().x < collider.getPosition().x + collider.getSize().x && handler.getMousePosition().y < collider.getPosition().y + collider.getSize().y)
		{
			if (handler.isMouseLeftClicking())
			{
				currentState = ButtonState.PRESSED;
			} else
			{
				if (currentState == ButtonState.PRESSED)
				{
					// if the mouse has been pressed and released over the button
					if (toggleable)
					{
						if (isToggled)
						{
							this.coldButton = Button.clone();
							this.redButton = Button.clone();
							this.whiteButton = Button.clone();
							collider = new RectangleBox(Button.getPosition(), Button.getSize());
							isToggled = false;
						} else
						{
							this.coldButton = Button2.clone();
							this.redButton = Button2.clone();
							this.whiteButton = Button2.clone();
							collider = new RectangleBox(Button2.getPosition(), Button2.getSize());
							isToggled = true;
						}
						redheat = 0;
						whiteheat = 0;
						redButton.setFrame(1);
						redButton.setAlpha(0);
						whiteButton.setFrame(2);
						whiteButton.setAlpha(0);
						currentState = ButtonState.ACTIVE;
					} else
					{
						currentState = ButtonState.ACTIVE;
					}
				} else
				{
					currentState = ButtonState.HOVER;
				}
			}
		} else
		{
			if (!handler.isMouseLeftClicking())
			{
				currentState = ButtonState.INACTIVE;
			}
		}

		// Graphics changes based on state
		if (currentState == ButtonState.PRESSED || currentState == ButtonState.HOVER)
		{
			// System.out.println(redheat);
			if (redheat < 1)
			{
				redheat += 0.004;
			} else
			{
				redheat = 1;
				if (whiteheat < 1)
					whiteheat += 0.005;
				else
					whiteheat = 1;
			}
		}
		if (currentState == ButtonState.PRESSED)
		{
			Button.setPosition(new Vector3f(position.x, position.y, 0));
			coldButton.setPosition(new Vector3f(position.x, position.y + 2, 0));
			redButton.setPosition(new Vector3f(position.x, position.y + 2, 0));
			whiteButton.setPosition(new Vector3f(position.x, position.y + 2, 0));
			if (toggleable)
			{
				Button2.setPosition(new Vector3f(position.x, position.y, 0));
			}
		}
		if (currentState == ButtonState.INACTIVE)
		{
			if (whiteheat > 0)
			{
				whiteheat -= 0.05;
			} else
			{
				whiteheat = 0;
				if (redheat > 0)
				{
					redheat -= 0.03;
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
		coldButton.render();
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
		if (position == null)
		{
			throw new NullPointerException("Can't set the position of a menu button to null");
		}
		this.position = position;
		this.Button.setPosition(new Vector3f(position.x, position.y, 0));

		this.coldButton.setPosition(new Vector3f(position.x, position.y, 0));
		this.redButton.setPosition(new Vector3f(position.x, position.y, 0));
		this.whiteButton.setPosition(new Vector3f(position.x, position.y, 0));
		if (toggleable)
		{
			this.Button2.setPosition(new Vector3f(position.x, position.y, 0));
			this.coldButton.setPosition(new Vector3f(position.x, position.y, 0));
			this.redButton.setPosition(new Vector3f(position.x, position.y, 0));
			this.whiteButton.setPosition(new Vector3f(position.x, position.y, 0));
		}
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

	/**
	 * Returns the size of the button
	 * 
	 * @return
	 */
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

	/**
	 * Returns whether the button has been toggled
	 * 
	 * @return Returns whether the button has been toggled
	 */
	public boolean isToggled()
	{
		return isToggled;
	}

}
