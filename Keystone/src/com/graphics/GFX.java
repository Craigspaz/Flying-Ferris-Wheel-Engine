package com.graphics;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A helper class full of static methods to render rectangles
 * 
 * @author Craig Ferris
 *
 */
public class GFX
{
	public static TrueTypeFont	font2, font3;

	public static void drawString(float x, float y, String string)
	{
		GL11.glPushMatrix();
		Textures.black.bind();
		GFX.font2.drawString(x, y, string);
		GL11.glPopMatrix();
	}

	/**
	 * Uses a second, larger font for drawing strings of dialogue
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param string the message to draw
	 */
	public static void drawString2(float x, float y, String string)
	{
		GL11.glPushMatrix();
		Textures.black.bind();
		GFX.font3.drawString(x, y, string);
		GL11.glPopMatrix();
	}
	
	/**
	 * Call to initialize font2
	 */
	public static void initString()
	{
		try
		{
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/Px437_IBM_PS2thin4.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(16f); // set font size
			font2 = new TrueTypeFont(awtFont2, false);
			
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			font3 = new TrueTypeFont(awtFont2, false);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// Font awtFont = new Font("Times New Roman", Font.BOLD, 24); //name, style (PLAIN, BOLD, or ITALIC), size
		// font2 = new TrueTypeFont(awtFont, false); //base Font, anti-aliasing true/false
	}

	/**
	 * Draws an entire sprite to the screen
	 * 
	 * @param x
	 *            The width of the sprite on screen in pixels
	 * @param y
	 *            The height of the sprite on screen in pixels
	 * @param xx
	 *            The x position of the top left corner of the sprite
	 * @param yy
	 *            The y position of the top left corner of the sprite
	 * @param texture
	 *            The texture to be rendered at the specified location
	 */
	public static void drawEntireSprite(float x, float y, float xx, float yy, Texture texture)
	{
		// First binds the texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glPushMatrix();

		// Draws a rectangle
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(xx, yy);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(xx, y + yy);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + xx, y + yy);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + xx, yy);

		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Draws an entire sprite at an angle
	 * 
	 * @param x
	 *            The width of the sprite on screen in pixels
	 * @param y
	 *            The height of the sprite on screen in pixels
	 * @param xx
	 *            The x position of the top left corner of the sprite
	 * @param yy
	 *            The y position of the top left corner of the sprite
	 * @param texture
	 *            The texture to be rendered at the specified location
	 * @param angle
	 *            The angle to rotate the texture
	 */
	public static void drawEntireSpriteAtAngle(float x, float y, float xx, float yy, Texture texture, float angle)
	{
		// First binds the texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glPushMatrix();

		// GL11.glLoadIdentity();
		if (angle != 0 && angle != 180)
		{
			GL11.glTranslatef(xx + x / 2, yy + y / 2, 0);
			GL11.glRotatef(-angle, 0, 0, 1);
			GL11.glTranslatef(-xx - x / 2, -yy - y / 2, 0);
		}

		// Draws a rectangle
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(xx, yy);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(xx, y + yy);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + xx, y + yy);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + xx, yy);

		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Draws a sprite from a sprite sheet
	 * 
	 * @param x
	 *            The width of the sprite on screen in pixels
	 * @param y
	 *            The height of the sprite on screen in pixels
	 * @param xx
	 *            The x position of the top left corner of the sprite
	 * @param yy
	 *            The y position of the top left corner of the sprite
	 * @param texture
	 *            The texture to render
	 * @param texCoords
	 *            The coordinates on the texture to render
	 * @param size
	 *            The width and height to render from the sprite sheet
	 */
	public static void drawSpriteFromSpriteSheet(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size)
	{
		// Binds the texture
		// GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glPushMatrix();

		// Draws a rectangle
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(texCoords.x, texCoords.y);
		GL11.glVertex2f(xx, yy);
		GL11.glTexCoord2f(texCoords.x, texCoords.y + size.y);
		GL11.glVertex2f(xx, y + yy);
		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y + size.y);
		GL11.glVertex2f(x + xx, y + yy);
		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y);
		GL11.glVertex2f(x + xx, yy);

		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Draws a sprite from a sprite sheet
	 * 
	 * @param x
	 *            The width of the sprite on screen in pixels
	 * @param y
	 *            The height of the sprite on screen in pixels
	 * @param xx
	 *            The x position of the top left corner of the sprite
	 * @param yy
	 *            The y position of the top left corner of the sprite
	 * @param texture
	 *            The texture to render
	 * @param texCoords
	 *            The coordinates on the texture to render
	 * @param size
	 *            The width and height to render from the sprite
	 * @param angle
	 *            The angle at which to rotate the texture
	 */
	public static void drawSpriteFromSpriteSheetAtAngle(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size, float angle)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

		GL11.glPushMatrix();
		if (angle != 0 && angle != 180)
		{
			GL11.glTranslatef(xx + x / 2, yy + y / 2, 0);
			GL11.glRotatef(-angle, 0, 0, 1);
			GL11.glTranslatef(-xx - x / 2, -yy - y / 2, 0);
		}

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(texCoords.x, texCoords.y);
		GL11.glVertex2f(xx, yy);
		GL11.glTexCoord2f(texCoords.x, texCoords.y + size.y);
		GL11.glVertex2f(xx, y + yy);
		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y + size.y);
		GL11.glVertex2f(x + xx, y + yy);
		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y);
		GL11.glVertex2f(x + xx, yy);

		GL11.glEnd();

		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

	}

	/**
	 * Draws a sprite from a sprite sheet backwards
	 * 
	 * @param x
	 *            The width of the sprite on screen in pixels
	 * @param y
	 *            The height of the sprite on screen in pixels
	 * @param xx
	 *            The x position of the top left corner of the sprite
	 * @param yy
	 *            The y position of the top left corner of the sprite
	 * @param texture
	 *            The texture to render
	 * @param texCoords
	 *            The coordinates on the texture to render
	 * @param size
	 *            The width and height to render from the sprite sheet
	 */
	public static void drawSpriteFromSpriteSheetInverse(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size)
	{
		// Binds the texture
		// GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glPushMatrix();

		// Draws a rectangle
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y);
		GL11.glVertex2f(xx, yy);
		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y + size.y);
		GL11.glVertex2f(xx, y + yy);
		GL11.glTexCoord2f(texCoords.x, texCoords.y + size.y);
		GL11.glVertex2f(x + xx, y + yy);
		GL11.glTexCoord2f(texCoords.x, texCoords.y);
		GL11.glVertex2f(x + xx, yy);

		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Draws a sprite from a sprite sheet backwards
	 * 
	 * @param x
	 *            The width of the sprite on screen in pixels
	 * @param y
	 *            The height of the sprite on screen in pixels
	 * @param xx
	 *            The x position of the top left corner of the sprite
	 * @param yy
	 *            The y position of the top left corner of the sprite
	 * @param texture
	 *            The texture to render
	 * @param texCoords
	 *            The coordinates on the texture to render
	 * @param size
	 *            The width and height to render from the sprite sheet
	 * @param angle
	 *            The angle to rotate the texture
	 */
	public static void drawSpriteFromSpriteSheetInverseAtAngle(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size, float angle)
	{
		// Binds the texture
		// GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glRotatef(angle, 0, 0, 1);// TODO same as above
		GL11.glPushMatrix();

		if (angle != 0 && angle != 180)
		{
			GL11.glTranslatef(xx + x / 2, yy + y / 2, 0);
			GL11.glRotatef(-angle, 0, 0, 1);
			GL11.glTranslatef(-xx - x / 2, -yy - y / 2, 0);
		}

		// Draws a rectangle
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y);
		GL11.glVertex2f(xx, yy);
		GL11.glTexCoord2f(texCoords.x + size.x, texCoords.y + size.y);
		GL11.glVertex2f(xx, y + yy);
		GL11.glTexCoord2f(texCoords.x, texCoords.y + size.y);
		GL11.glVertex2f(x + xx, y + yy);
		GL11.glTexCoord2f(texCoords.x, texCoords.y);
		GL11.glVertex2f(x + xx, yy);
		GL11.glEnd();

		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

	}
}
