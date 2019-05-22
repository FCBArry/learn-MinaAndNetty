package mina.command;

import mina.command.cmd.c2c.ChatCmd;
import mina.command.cmd.c2s.SendMsgCmd;
import mina.command.cmd.s2c.ReplyCmd;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class MiniCmdFactory
{
	private static Map<String, Class<? extends ICmd>> registry = new HashMap<>();

	private MiniCmdFactory()
	{

	}

	static
	{
		register(SendMsgCmd.NAME, SendMsgCmd.class);
		register(ReplyCmd.NAME, ReplyCmd.class);
		register(ChatCmd.NAME, ChatCmd.class);
	}

	private static void register(String name, Class<? extends ICmd> clazz)
	{
		if (name != null && clazz != null)
		{
			registry.put(name, clazz);
		}
	}

	public static AbstractMiniCmd newCommand(String name)
	{
		if (StringUtils.isNotEmpty(name))
		{
			Class<? extends ICmd> clazz = registry.get(name);
			if (clazz != null)
			{
				if (AbstractMiniCmd.class.isAssignableFrom(clazz))
				{
					try
					{
						return (AbstractMiniCmd) clazz.newInstance();
					}
					catch (Exception e)
					{
						e.printStackTrace();
						return null;
					}
				}
			}
		}

		return null;
	}
}
