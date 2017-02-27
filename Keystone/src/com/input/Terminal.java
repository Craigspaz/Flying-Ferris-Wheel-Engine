package com.input;

public class Terminal
{
	private boolean active;
	private InputHandler handler;
	private boolean canToggle = true;
	private String command;

	public Terminal(InputHandler handler)
	{
		this.handler = handler;
	}

	public boolean active()
	{
		return this.active;
	}

	public void update()
	{
		if (active)
		{
			if (handler.enter())
			{
				active = false;
				read(command);
				handler.clearBuffer();
			} else if (handler.escape())
			{
				active = false;
				handler.clearBuffer();
			} else
			{
				command = handler.getCommand();
			}
		} else
		{

			handler.clearBuffer();
		}
		if (handler.tildedown() && canToggle)
		{
			handler.clearBuffer();
			active = !active;
			canToggle = false;
		}
		if (!handler.tildedown())
		{
			canToggle = true;
		}
	}

	public void render()
	{
		if (active)
		{

		}
	}

	private void read(String cmd)
	{
		System.out.println(cmd);
	}
}
