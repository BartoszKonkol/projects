package vw.command;

import net.minecraft.command.ServerCommandManager;

public class CommandData
{
	
	public static final byte SCAN = 1;
	
	public CommandData(final ServerCommandManager par1ServerCommandManager)
	{
		
		par1ServerCommandManager.registerCommand(new CommandHerobrine());
		par1ServerCommandManager.registerCommand(new CommandRealTime());
		
	}
	
}
