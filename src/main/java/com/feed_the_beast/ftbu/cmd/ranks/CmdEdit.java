package com.feed_the_beast.ftbu.cmd.ranks;

import com.feed_the_beast.ftblib.FTBLibLang;
import com.feed_the_beast.ftblib.lib.cmd.CmdBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * @author LatvianModder
 */
public class CmdEdit extends CmdBase
{
	public CmdEdit()
	{
		super("edit", Level.OP);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		throw FTBLibLang.FEATURE_DISABLED.commandError();
	}
}