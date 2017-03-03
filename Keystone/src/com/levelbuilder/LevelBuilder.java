package com.levelbuilder;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Creates a new level
 * 
 * @author Craig Ferris
 *
 */
public class LevelBuilder
{

	public static int			width	= 1366;
	public static int			height	= 768;

	private LevelBuilderGame	game;

	/**
	 * Creates a level
	 */
	public LevelBuilder()
	{
		try
		{
			setDisplayMode(width, height, false);
			Display.setTitle("Keystone");
			Display.setResizable(false);

			Display.create();
			Keyboard.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}

		game = new LevelBuilderGame();

	}

	/**
	 * Updates the game
	 */
	private void update()
	{
		game.update();
	}

	/**
	 * Renders the game
	 */
	private void render()
	{
		game.render();
	}

	/**
	 * The main game loop
	 */
	public void run()
	{
		final int TICKS_PER_SECOND = 60;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMESKIP = 5;

		double nextTick = System.currentTimeMillis();
		int loops;
		while (!Display.isCloseRequested())
		{
			loops = 0;
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(.4f, .4f, .4f, 1);
			GL11.glLoadIdentity();
			while (System.currentTimeMillis() > nextTick && loops < MAX_FRAMESKIP)
			{
				update();
				nextTick += SKIP_TICKS;
				loops++;
			}
			render();
			Display.update();// This needs to be the last line of code in the
								// while loop
			// Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	/**
	 * Initializes opengl
	 */
	public void initOpenGL()
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Sets the displaymode. Allows the displaymode to be changed
	 * 
	 * @param width
	 *            The width of the window
	 * @param height
	 *            The height of the window
	 * @param fullscreen
	 *            If the window is to be fullscreen
	 * @author LWJGL WIKI
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen)
	{

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen))
		{
			return;
		}

		try
		{
			DisplayMode targetDisplayMode = null;

			if (fullscreen)
			{
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++)
				{
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height))
					{
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq))
						{
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))
							{
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
						{
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else
			{
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null)
			{
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e)
		{
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
		}
	}

	/**
	 * Launches the level builder
	 * 
	 * @param args
	 *            The commandline parameters
	 */
	public static void main(String[] args)
	{
		LevelBuilder builder = new LevelBuilder();
		builder.initOpenGL();
		builder.run();
	}
}
