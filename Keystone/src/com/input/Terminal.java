package com.input;

import java.util.ArrayList;
import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Player;

public class Terminal
{
	private boolean active;
	private InputHandler handler;
	private boolean canToggle = true;
	private boolean canEnter = true;
	private String command = "";
	private ArrayList<String> messages;
	int lineheight;

	private Player player;

	public Terminal(InputHandler handler, Player player)
	{
		this.handler = handler;
		messages = new ArrayList<String>(5);
		this.player = player;
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
			GFX.drawString(x + 10, y - lineheight - 10, "> " + command);
		}
	}

	private void read(String cmd)
	{
		String[] commands = cmd.split(" ");
		if (commands.length == 0)
		{
			messages.add(0, "please enter a command");
		} else
		{
			messages.add(0, "> " + cmd);
			if (commands[0].equals("nodamage"))
			{
				player.setImmune(!player.isImmune());
				messages.add(0, "nodamage is now " + player.isImmune());
			} else if (commands[0].equals("spawn"))
			{
				if (commands.length >= 4)
				{
					int x, y;
					try
					{
						x = Integer.parseInt(commands[2]);
						y = Integer.parseInt(commands[3]);
					} catch (NumberFormatException e)
					{
						messages.add(0, "failed to read integers");
						return;
					}
					// TODO create enemy with ID, X, and Y coords
					messages.add(0, "spawned " + commands[1] + " at " + x + ", " + y);
				} else
				{
					messages.add(0, "invalid arguments");
				}
			} else if (commands[0].equals("hello"))
			{
				messages.add(0, "hello :)");
			} else if (commands[0].equals("help"))
			{
				messages.add(0, "nodamage: toggles whether player can receive damage");
				messages.add(0, "spawn [ID] [x] [y]: spawns entity at x, y from camera corner");
			} else
			{
				// messages.add(0, "unknown command");
			}

		}
		while (messages.size() > 5)
		{
			messages.remove(messages.size() - 1);
		}
	}
}
