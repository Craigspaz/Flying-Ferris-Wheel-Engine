package com.input;

import java.util.ArrayList;

import com.graphics.GFX;
import com.graphics.Textures;

public class Terminal
{
	private boolean active;
	private InputHandler handler;
	private boolean canToggle = true;
	private boolean canEnter = true;
	private String command = "";
	private ArrayList<String> messages;
	int lineheight;

	public Terminal(InputHandler handler)
	{
		this.handler = handler;
		messages = new ArrayList<String>(5);
	}

	public boolean active()
	{
		return this.active;
	}

	public void update()
	{
		if (active)
		{
			if (handler.enter() && canEnter && !command.isEmpty())
			{
				read(command);
				command = "";
				handler.clearBuffer();
				canEnter = false;
			} else if (handler.escape())
			{
				active = false;
				handler.clearBuffer();
				command = "";
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
			command = "";
			handler.clearBuffer();
			active = !active;
			canToggle = false;
		}
		if (!handler.tildedown())
		{
			canToggle = true;
		}
		if (!handler.enter())
		{
			canEnter = true;
		}
	}

	public void render(float x, float y)
	{
		lineheight = GFX.font2.getLineHeight();
		if (active)
		{
			GFX.drawEntireSprite(512, 128, x, y - 128, Textures.terminalWindow);
			for (int i = 0; i < messages.size(); i++)
			{
				GFX.drawString(x + 10, (y - 2 * lineheight) - (lineheight * i) - 11, messages.get(i));
			}
			GFX.drawString(x + 10, y - lineheight - 10, command);
		}
	}

	private void read(String cmd)
	{
		messages.add(0, cmd);
		if (messages.size() > 5)
		{
			messages.remove(messages.size() - 1);
		}
	}
}
