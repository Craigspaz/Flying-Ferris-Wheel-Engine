package com.graphics;

import java.io.FileInputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Load files and textures
 * 
 * @author Craig Ferris
 *
 */
public class Loader
{
	/**
	 * Load a texture into memory from a file
	 * 
	 * @param fileName
	 *            The name of the file to be loaded. Must be in "./res/" folder
	 * @return Returns a texture object which points to the images location
	 */
	public static Texture loadTexture(String fileName)
	{
		Texture texture = null;
		try
		{
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"), GL11.GL_NEAREST);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Failed to load: " + fileName + ".png");
			System.exit(-1);
		}
		return texture;
	}

}