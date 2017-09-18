package com.graphics;

import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

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
	
	/**
	 * Loads an WAV audio file into memory
	 * @param fileName The name of the file to be loaded from the "./res/" folder
	 * @return Returns an Audio object of the file loaded in
	 */
	public static Audio loadAudioWAV(String fileName)
	{
		Audio audio = null;
		try
		{
			audio = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/audio/" + fileName + ".wav"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return audio;
	}
	
	/**
	 * Loads an OGG audio file into memory
	 * @param fileName The name of the file to be loaded from the "./res/" folder
	 * @return Returns an Audio object of the file loaded in
	 */
	public static Audio loadAudioOGG(String fileName)
	{
		Audio audio = null;
		try
		{
			audio = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/audio/" + fileName + ".OGG"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return audio;
	}

}