package com.graphics.world.menu;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.world.RectangleBox;
import com.input.InputHandler;

/**
 * A button for a menu like the main menu
 * @author Craig Ferris
 *
 */
public class MenuButton
{
	private Vector2f position;
	private Vector2f sizeToRender;
	private RectangleBox collider;
	private Texture spriteSheet;
	private Vector2f startPos;
	private Vector2f sizeOfSpriteSheet;
	private Vector2f sizeOfSpriteOnSpriteSheet;
	
	private ButtonState currentState;
	
	/**
	 * Creates a new MenuButton
	 * @param position The position at which the button is drawn
	 * @param sizeToRender The size to render the button
	 * @param spriteSheet The texture of the spritesheet
	 * @param startPos The coordinate of the UP state of the button texture on the spritesheet
	 * @param sizeOfSpriteSheet The size of the spritesheet
	 * @param sizeOfSpriteOnSpriteSheet The size of the sprite on the spritesheet
	 */
	public MenuButton(Vector2f position, Vector2f sizeToRender, Texture spriteSheet, Vector2f startPos, Vector2f sizeOfSpriteSheet, Vector2f sizeOfSpriteOnSpriteSheet)
	{
		this.position = position;
		this.sizeToRender = sizeToRender;
		this.spriteSheet = spriteSheet;
		this.startPos = startPos;
		this.sizeOfSpriteSheet = sizeOfSpriteSheet;
		this.sizeOfSpriteOnSpriteSheet = sizeOfSpriteOnSpriteSheet;
		currentState = ButtonState.UP;
		collider = new RectangleBox(new Vector3f(position.getX(),position.getY(),0),new Vector2f(sizeToRender.getX(),sizeToRender.getY()));
	}
	
	/**
	 * Updates the button
	 * @param handler A pointer to the input handler so the menubutton can check if an action was performed on it
	 */
	public void update(InputHandler handler)
	{
		// Handle mouse input
	}
	
	/**
	 * Draws the button to the screen
	 */
	public void render()
	{
		if(currentState == ButtonState.UP)
		{
			Vector2f offset = new Vector2f(((float) (sizeOfSpriteOnSpriteSheet.getX() * startPos.getX())) / sizeOfSpriteSheet.getX(), (float) (sizeOfSpriteOnSpriteSheet.getY() * startPos.getY()) / sizeOfSpriteSheet.getY());
			Vector2f sizey = new Vector2f((float) (sizeOfSpriteOnSpriteSheet.getX() / sizeOfSpriteSheet.getX()), (float) (sizeOfSpriteOnSpriteSheet.getY() / sizeOfSpriteSheet.getY()));
			GFX.drawSpriteFromSpriteSheet(sizeToRender.getX(), sizeToRender.getY(), position.getX(), position.getY(), spriteSheet, offset, sizey);
		}
		else if(currentState == ButtonState.HOVER)
		{
			Vector2f offset = new Vector2f(((float) (sizeOfSpriteOnSpriteSheet.getX() * startPos.getX())) / sizeOfSpriteSheet.x, (float) (sizeOfSpriteOnSpriteSheet.getY() * (startPos.getY() + 1)) / sizeOfSpriteSheet.getY());
			Vector2f sizey = new Vector2f((float) (sizeOfSpriteOnSpriteSheet.getX() / sizeOfSpriteSheet.getX()), (float) (sizeOfSpriteOnSpriteSheet.getY() / sizeOfSpriteSheet.getY()));
			GFX.drawSpriteFromSpriteSheet(sizeToRender.getX(), sizeToRender.getY(), position.getX(), position.getY(), spriteSheet, offset, sizey);
		}
		else if(currentState == ButtonState.DOWN)
		{
			Vector2f offset = new Vector2f(((float) (sizeOfSpriteOnSpriteSheet.getX() * startPos.getX())) / sizeOfSpriteSheet.x, (float) (sizeOfSpriteOnSpriteSheet.getY() * (startPos.getY() + 2)) / sizeOfSpriteSheet.getY());
			Vector2f sizey = new Vector2f((float) (sizeOfSpriteOnSpriteSheet.getX() / sizeOfSpriteSheet.getX()), (float) (sizeOfSpriteOnSpriteSheet.getY() / sizeOfSpriteSheet.getY()));
			GFX.drawSpriteFromSpriteSheet(sizeToRender.getX(), sizeToRender.getY(), position.getX(), position.getY(), spriteSheet, offset, sizey);
		}
	}

	/**
	 * Returns the position of the button
	 * @return Returns the position of the button
	 */
	public Vector2f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the button
	 * @param position The position to set the button
	 */
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}

	/**
	 * Returns the box collider around the button
	 * @return Returns the box collider around the button
	 */
	public RectangleBox getCollider()
	{
		return collider;
	}

	/**
	 * Sets the box collider around the button
	 * @param collider The new box collider around the button
	 */
	public void setCollider(RectangleBox collider)
	{
		this.collider = collider;
	}

	/**
	 * Returns the size to render the button
	 * @return Returns the size to render the button
	 */
	public Vector2f getSizeToRender()
	{
		return sizeToRender;
	}

	/**
	 * Sets the size to render the button
	 * @param sizeToRender The size to render the button
	 */
	public void setSizeToRender(Vector2f sizeToRender)
	{
		this.sizeToRender = sizeToRender;
	}

	/**
	 * Returns the spritesheet
	 * @return Returns the spritesheet
	 */
	public Texture getSpriteSheet()
	{
		return spriteSheet;
	}

	/**
	 * Sets the spritesheet
	 * @param spriteSheet The spritesheet
	 */
	public void setSpriteSheet(Texture spriteSheet)
	{
		this.spriteSheet = spriteSheet;
	}

	/**
	 * Returns the start position of the texture to render the UP state
	 * @return Returns the start position of the texture to render the UP state
	 */
	public Vector2f getStartPos()
	{
		return startPos;
	}

	/**
	 * Sets the start position of the texture to render the UP state
	 * @param startPos
	 */
	public void setStartPos(Vector2f startPos)
	{
		this.startPos = startPos;
	}

	/**
	 * Returns the size of the spritesheet
	 * @return Returns the size of the spritesheet
	 */
	public Vector2f getSizeOfSpriteSheet()
	{
		return sizeOfSpriteSheet;
	}

	/**
	 * Sets the size of the spritesheet
	 * @param sizeOfSpriteSheet The size of the spritesheet
	 */
	public void setSizeOfSpriteSheet(Vector2f sizeOfSpriteSheet)
	{
		this.sizeOfSpriteSheet = sizeOfSpriteSheet;
	}

	/**
	 * Returns the size of the sprite on the spritesheet
	 * @return Returns the size of the sprite on the spritesheet
	 */
	public Vector2f getSizeOfSpriteOnSpriteSheet()
	{
		return sizeOfSpriteOnSpriteSheet;
	}

	/**
	 * Sets the size of the sprite on the spritesheet
	 * @param sizeOfSpriteOnSpriteSheet
	 */
	public void setSizeOfSpriteOnSpriteSheet(Vector2f sizeOfSpriteOnSpriteSheet)
	{
		this.sizeOfSpriteOnSpriteSheet = sizeOfSpriteOnSpriteSheet;
	}

	/**
	 * Returns the currentbutton state
	 * @return Returns the current buttonstate
	 */
	public ButtonState getCurrentState()
	{
		return currentState;
	}

	/**
	 * Sets the current buttonstate
	 * @param currentState The new buttonstate
	 */
	public void setCurrentState(ButtonState currentState)
	{
		this.currentState = currentState;
	}
	
}
