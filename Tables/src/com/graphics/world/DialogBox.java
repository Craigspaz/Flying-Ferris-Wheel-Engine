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
	private String[]	messages;									// array of lines of text
	private String		speaker;									// label for the text box
	private Texture		portraits			= Textures.portraits;
	private Texture		textBoxes			= Textures.textBoxes;
	private int			portraitNumber;
	private int			textBoxNumber;
	private int			pageNumber			= 0;

	// private boolean canTurn = false;
	private boolean		isActive			= false;

	private float		animateSpeed		= 2.0f;
	private float		animateTime			= 0.0f;
	private float		animateFrameTime	= 2;
	private int			currentLetter;								// the current index of a letter in the current string
	private int			currentLine;								// a number 0-4 that represents the current line on a page
	private String		currentString;								// the temporary string for the current line being typed

	/**
	 * Creates a new dialogbox
	 * 
	 * @param messages
	 *            The messages to display in the box
	 * @param speaker
	 *            The speaker saying the messages
	 * @param portraitNumber
	 *            The id of the portrait in the sprite sheet
	 * @param textBoxNumber
	 *            The id of the background texture
	 */
	public DialogBox(String[] messages, String speaker, int portraitNumber, int textBoxNumber)
	{
		this.messages = messages;
		this.speaker = speaker;
		this.portraitNumber = portraitNumber;
		this.textBoxNumber = textBoxNumber;
		currentLetter = 0;
		currentLine = 0;
		currentString = "";
	}

	/**
	 * renders the chosen text box background, the portrait of the speaker, and any text
	 * 
	 * @param textBoxX
	 *            The position to render the dialog box in the x direction
	 * @param textBoxY
	 *            The position to render the dialog box in the y direction
	 */
	public void render(float textBoxX, float textBoxY)
	{
		if (isActive)
		{
			int lineheight = GFX.font3.getLineHeight();
			GFX.drawSpriteFromSpriteSheet(1024, 256, textBoxX, textBoxY, textBoxes, new Vector2f(0, textBoxNumber), new Vector2f(1, 1));// TODO fix the vectors so they aren't hard coded
			GFX.drawSpriteFromSpriteSheet(128, 128, textBoxX + 2, textBoxY + 26, portraits, new Vector2f(portraitNumber, 0), new Vector2f(.5f, 1));
			GFX.drawString2(textBoxX + 4, textBoxY + 1, speaker);
			for (int i = 0; i < currentLine; i++)// draws all strings in previous lines, because they're already "typed"
			{
				GFX.drawString2(textBoxX + 138, textBoxY + 30 + (i * lineheight), messages[i + (5 * pageNumber)]);
			}
			GFX.drawString2(textBoxX + 138, textBoxY + 30 + (currentLine * lineheight), currentString);// draws the typed string
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
		if (animateTime >= animateFrameTime)// for animating text appearance
		{
			if (currentLetter < messages[currentLine + (pageNumber * 5)].length())// if it's not done typing a line
			{
				currentLetter++;
			} else
			{

				if (currentLine + (pageNumber * 5) < messages.length - 1 && currentLine < 4)// increments the line number and resets line letter when at the end of a line (unless on the last line of a page)
				{
					currentLetter = 0;
					currentLine++;
				}

			}
			currentString = messages[currentLine + (pageNumber * 5)].substring(0, currentLetter);// takes a substring from the start to the current letter of the current line
			animateTime = 0.0f;
		} else
		{
			animateTime += animateSpeed;
		}
		if (handler.nextPage())// waits for user input when at the end of a page, or at the end of all messages
		{
			if (currentLine + (pageNumber * 5) == messages.length - 1 && currentLetter == messages[currentLine + (pageNumber * 5)].length())// if it's at the end of the last letter of the last line
			{
				isActive = false;
			} else if (currentLine == 4 && currentLetter == messages[currentLine + (pageNumber * 5)].length())// if it's on the last line of any page, but there's more pages
			{
				pageNumber++;
				currentLetter = 0;
				currentLine = 0;
				currentString = "";// does this because otherwise it briefly displays the current string when going to a
									// new page, before it's overwritten above
			}
		}
	}
}
