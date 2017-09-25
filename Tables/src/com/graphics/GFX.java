package com.graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import com.main.Game;

/**
 * A helper class full of static methods to render rectangles
 * 
 * @author Craig Ferris
 *
 */
public class GFX
{
	public static TrueTypeFont font2, font3;

	/**
	 * Draws a string of text at a position x,y
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @param string
	 *            The string to draw
	 */
	public static void drawString(float x, float y, String string)
	{
		GL11.glPushMatrix();
		Textures.black.bind();
		GFX.font2.drawString(x, y, string);
		GL11.glPopMatrix();
	}

	/**
	 * Uses a second, larger font for drawing strings of dialogue
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param string
	 *            the message to draw
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
	
	public static void drawEntireSpriteTEST(float x, float y, float xx, float yy, Texture texture)
	{
		x = x * Game.SCALE;
		y = y * Game.SCALE;
		xx = (float) (Math.round(xx) * Game.SCALE);
		yy = (float) (Math.round(yy) * Game.SCALE);
		// First binds the texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

		// Draws a rectangle
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(xx, yy);
		GL11.glVertex2f(xx, y + yy);
		GL11.glVertex2f(x + xx, y + yy);
		GL11.glVertex2f(x + xx, yy);

		GL11.glEnd();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
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
	 * @param scale
	 *            the scale to render the sprite at (-1 for default)
	 */
	public static void drawEntireSprite(float x, float y, float xx, float yy, Texture texture, int scale)
	{
		if (scale == -1)
		{
			x = x * Game.SCALE;
			y = y * Game.SCALE;
			xx = (float) (Math.round(xx) * Game.SCALE);
			yy = (float) (Math.round(yy) * Game.SCALE);
		} else
		{
			x = x * scale;
			y = y * scale;
			xx = (float) (Math.round(xx) * scale);
			yy = (float) (Math.round(yy) * scale);
		}
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
	 * @param scale
	 *            Scale to draw the sprite (-1 for game's default)
	 */
	public static void drawEntireSpriteAtAngle(float x, float y, float xx, float yy, Texture texture, float angle, int scale)
	{
		if (scale == -1)
		{
			x = x * Game.SCALE;
			y = y * Game.SCALE;
			xx = (float) (Math.round(xx) * Game.SCALE);
			yy = (float) (Math.round(yy) * Game.SCALE);
		} else
		{
			x = x * scale;
			y = y * scale;
			xx = (float) (Math.round(xx) * scale);
			yy = (float) (Math.round(yy) * scale);
		}
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
	 * @param scale
	 *            the scale to draw the sprite, -1 for game default
	 */
	public static void drawSpriteFromSpriteSheet(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size, int scale, float alpha)
	{
		if (scale == -1)
		{
			x = x * Game.SCALE;
			y = y * Game.SCALE;
			xx = (float) (Math.round(xx) * Game.SCALE);
			yy = (float) (Math.round(yy) * Game.SCALE);
		} else
		{
			x = x * scale;
			y = y * scale;
			xx = (float) (Math.round(xx) * scale);
			yy = (float) (Math.round(yy) * scale);
		}
		// Binds the texture
		// GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glColor4f(1f, 1f, 1f, alpha);
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
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
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
	 * @param scale
	 *            the scale at which to draw the sprite, -1 for default
	 */
	public static void drawSpriteFromSpriteSheetAtAngle(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size, float angle, int scale)
	{
		if (scale == -1)
		{
			x = x * Game.SCALE;
			y = y * Game.SCALE;
			xx = (float) (Math.round(xx) * Game.SCALE);
			yy = (float) (Math.round(yy) * Game.SCALE);
		} else
		{
			x = x * scale;
			y = y * scale;
			xx = (float) (Math.round(xx) * scale);
			yy = (float) (Math.round(yy) * scale);
		}
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
	 * @param scale
	 *            the scale to multiply the sprite, -1 for game default
	 */
	public static void drawSpriteFromSpriteSheetInverse(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size, int scale)
	{
		if (scale == -1)
		{
			x = x * Game.SCALE;
			y = y * Game.SCALE;
			xx = (float) (Math.round(xx) * Game.SCALE);
			yy = (float) (Math.round(yy) * Game.SCALE);
		} else
		{
			x = x * scale;
			y = y * scale;
			xx = (float) (Math.round(xx) * scale);
			yy = (float) (Math.round(yy) * scale);
		}
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
	 * @param scale
	 *            the amount to scale the sprite, -1 for game default
	 */
	public static void drawSpriteFromSpriteSheetInverseAtAngle(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size, float angle, int scale)
	{
		if (scale == -1)
		{
			x = x * Game.SCALE;
			y = y * Game.SCALE;
			xx = (float) (Math.round(xx) * Game.SCALE);
			yy = (float) (Math.round(yy) * Game.SCALE);
		} else
		{
			x = x * scale;
			y = y * scale;
			xx = (float) (Math.round(xx) * scale);
			yy = (float) (Math.round(yy) * scale);
		}
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

	public static void drawLine(float startX, float startY, float destX, float destY)
	{
		GL11.glPushMatrix();
		GL11.glColor3f(0.0f, 1.0f, 0.0f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2f(startX, startY);
		GL11.glVertex2f(destX, destY);
		GL11.glEnd();
		GL11.glColor3f(1, 1, 1);
		GL11.glPopMatrix();
	}

	public static void drawEntireSpriteWithVaryingAlpha(float x, float y, float xx, float yy, Texture texture, float alpha, int scale)
	{
		if (scale == -1)
		{
			x = x * Game.SCALE;
			y = y * Game.SCALE;
			xx = (float) (Math.round(xx) * Game.SCALE);
			yy = (float) (Math.round(yy) * Game.SCALE);
		} else
		{
			x = x * scale;
			y = y * scale;
			xx = (float) (Math.round(xx) * scale);
			yy = (float) (Math.round(yy) * scale);
		}
		// First binds the texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glColor4f(1f, 1f, 1f, alpha);
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
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	// Found http://www.java-gaming.org/topics/solved-opengl-context/27956/view.html
	public static void screenshot()
	{
		GL11.glReadBuffer(GL11.GL_FRONT);
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(Display.getWidth() * Display.getHeight() * bpp);
		GL11.glReadPixels(0, 0, Display.getWidth(), Display.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		File file = new File("Screenshot_" + System.currentTimeMillis() + ".PNG"); // The file to save to.
		String format = "PNG"; // Example: "PNG" or "JPG"
		BufferedImage image = new BufferedImage(Display.getWidth(), Display.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < Display.getWidth(); x++)
		{
			for (int y = 0; y < Display.getHeight(); y++)
			{
				int i = (x + (Display.getWidth() * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, Display.getHeight() - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}

		try
		{
			ImageIO.write(image, format, file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
