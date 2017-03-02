package com.graphics.world;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.Textures;
import com.input.InputHandler;

/**
 * popup text boxes that can be used for plot progression
 * 
 * @author Kyle Falicov
 *
 */
public class DialogBox
{
	private String[]	messages;							// array of lines of text
	private String		speaker;							// label for the text box
	private Texture		portraits	= Textures.portraits;
	private Texture		textBoxes	= Textures.textBoxes;
	private int			portraitNumber;
	private int			textBoxNumber;
	private int			pageNumber	= 0;

	private boolean		canTurn		= true;
	private boolean		isActive	= false;

	public DialogBox(String[] messages, String speaker, int portraitNumber, int textBoxNumber)
	{
		this.messages = messages;
		this.speaker = speaker;
		this.portraitNumber = portraitNumber;
		this.textBoxNumber = textBoxNumber;
	}

	// renders the chosen text box background, the portrait of the speaker, and any text
	public void render(float textBoxX, float textBoxY)
	{
		if (isActive)
		{
			int lineheight = GFX.font3.getLineHeight();
			GFX.drawSpriteFromSpriteSheet(1024, 256, textBoxX, textBoxY, textBoxes, new Vector2f(0, textBoxNumber), new Vector2f(1, 1));// TODO fix it so it's not hardcoded
			GFX.drawSpriteFromSpriteSheet(128, 128, textBoxX + 2, textBoxY + 26, portraits, new Vector2f(portraitNumber, 0), new Vector2f(.5f, 1));
			// GFX.drawEntireSprite(1024, 256, textBoxX, textBoxY, textBoxes);
			// GFX.drawEntireSprite(256, 128, textBoxX + 2, textBoxY + 26, portraits);
			GFX.drawString2(textBoxX + 4, textBoxY + 4, speaker);
			for (int i = 0; i < 5; i++)
			{
				if (i + (5 * pageNumber) < messages.length)// only renders 5 lines per page, and doesn't try to retrieve if index is out of bounds
				{
					GFX.drawString2(textBoxX + 138, textBoxY + 32 + (i * lineheight), messages[i + (5 * pageNumber)]);
				} else
				{
					break;
				}
			}
		}
	}

	/**
	 * used in Game to decide when to display the dialogue box
	 */
	public void activate()
	{
		isActive = true;
	}

	/**
	 * tells the Game when to render
	 * 
	 * @return isActive
	 */
	public boolean active()
	{
		return isActive;
	}

	/**
	 * handles text box scrolling and text updating when a key is pressed
	 * 
	 * @param handler
	 *            the InputHandler used for everything
	 */
	public void update(InputHandler handler)
	{
		if ((pageNumber) * 5 > messages.length - 1)
		{
			isActive = false;// deactivates when there isn't any more text
		}
		if (handler.nextPage() && canTurn)
		{
			pageNumber++;
			canTurn = false;
		} else if (!handler.nextPage())
		{
			canTurn = true;
		}
	}
}
