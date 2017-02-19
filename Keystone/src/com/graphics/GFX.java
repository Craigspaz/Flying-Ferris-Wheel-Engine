package com.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

/**
 * A helper class full of static methods to render rectangles
 * @author Craig Ferris
 *
 */
public class GFX
{
	/**
	 * Draws an entire sprite to the screen
	 * @param x The width of the sprite on screen in pixels
	 * @param y The height of the sprite on screen in pixels
	 * @param xx The x position of the top left corner of the sprite
	 * @param yy The y position of the top left corner of the sprite
	 * @param texture The texture to be rendered at the specified location
	 */
	public static void drawEntireSprite(float x, float y, float xx, float yy, Texture texture)
	{
		//First binds the texture
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
	}
	
	/**
	 * Draws a sprite from a sprite sheet
	 * @param x The width of the sprite on screen in pixels
	 * @param y The height of the sprite on screen in pixels
	 * @param xx The x position of the top left corner of the sprite
	 * @param yy The y position of the top left corner of the sprite
	 * @param texture The texture to render
	 * @param texCoords The coordinates on the texture to render
	 * @param size The width and height to render from the sprite sheet
	 */
	public static void drawSpriteFromSpriteSheet(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size)
	{
		//Binds the texture
		//GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glPushMatrix();

		//Draws a rectangle
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
	}
	
	/**
	 * Draws a sprite from a sprite sheet backwards
	 * @param x The width of the sprite on screen in pixels
	 * @param y The height of the sprite on screen in pixels
	 * @param xx The x position of the top left corner of the sprite
	 * @param yy The y position of the top left corner of the sprite
	 * @param texture The texture to render
	 * @param texCoords The coordinates on the texture to render
	 * @param size The width and height to render from the sprite sheet
	 */
	public static void drawSpriteFromSpriteSheetInverse(float x, float y, float xx, float yy, Texture texture, Vector2f texCoords, Vector2f size)
	{
		//Binds the texture
		//GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glPushMatrix();

		//Draws a rectangle
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
	}
}
