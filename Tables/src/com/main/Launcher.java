package com.main;

/**
 * Launches the game
 * @author Craig Ferris
 *
 */
public class Launcher
{
	public static void main(String[] args)
	{
		new Thread(new Runnable(){

			@Override
			public void run()
			{
				Window window = new Window(1366,768,false);
				window.initOpenGL();
				window.run();
			}}).start();
	}
}
