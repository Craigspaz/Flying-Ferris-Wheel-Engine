package com.graphics.world;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.Textures;

public class DialogBox
{
	private String[]	messages;
	private String		speaker;
	private Texture		portraits	= Textures.portraits;
	private Texture		textBoxes	= Textures.textBoxes;
	private int			portraitNumber;
	private int			textBoxNumber;

	public DialogBox(String[] messages, String speaker, int portraitNumber, int textBoxNumber)
	{
		this.messages = messages;
		this.speaker = speaker;
		this.portraitNumber = portraitNumber;
		this.textBoxNumber = textBoxNumber;
	}

	public void render(Camera camera)
	{
		int lineheight = GFX.font3.getLineHeight();
		float textBoxX = camera.getPosition().x + (camera.getSize().x / 2) - 384;
		float textBoxY = camera.getPosition().y + camera.getSize().y - 132;
		GFX.drawSpriteFromSpriteSheet(768, 132, textBoxX, textBoxY, textBoxes, new Vector2f(1, textBoxNumber), new Vector2f(768, 132));
		GFX.drawSpriteFromSpriteSheet(128, 128, textBoxX + 2, textBoxY + 2, portraits, new Vector2f(portraitNumber, 0), new Vector2f(128, 128));
		for (int i = 0; i < messages.length; i++)
		{
			GFX.drawString2(textBoxX + 138, textBoxY + 6 + (i * lineheight), messages[i]);
		}
	}
}
