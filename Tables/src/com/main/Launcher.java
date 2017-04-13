package com.main;

public class Launcher
{
	public static void main(String[] args)
	{
		new Thread(new Runnable(){

			@Override
			public void run()
			{
				Window window = new Window();
				window.initOpenGL();
				window.run();
			}}).start();
	}
}
