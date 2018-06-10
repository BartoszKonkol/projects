package vw.command;

import java.util.List;
import javax.JNF;
import vw.entity.monster.EntityHerobrine;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;

public class CommandHerobrine extends CommandBase
{
	
	public static final byte SCAN = 1;

	@Override
	public String getName() // getCommandName
	{
		
		return "herobrine";
		
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		
		return 2;
		
	}

	@Override
	public String getCommandUsage(final ICommandSender par1ICommandSender)
	{
		
		return "commands.herobrine.usage";
		
	}
	
	@Override
	public void execute(final ICommandSender par1ICommandSender, final String[] args2String) throws WrongUsageException // processCommand
	{
		
		if (args2String.length == 2 && args2String[0].equals("remove") && (args2String[1].equals("true") || args2String[1].equals("false")))
		{
			
			this.removeHerobrine(Boolean.valueOf(args2String[1]));
			this.notifyOperators(par1ICommandSender, this, "commands.herobrine.remove." + args2String[1], new Object[0]);
			return;
			
		}
		else if(args2String.length > 0 && args2String[0].equals("remove"))
			throw new WrongUsageException("commands.herobrine.remove.usage", new Object[0]);

		throw new WrongUsageException("commands.herobrine.usage", new Object[0]);
		
	}
	
	@Override
	public List<?> addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] args2String, BlockPos par3BlockPos)
	{
		
		if(args2String.length == 1)
			return this.getListOfStringsMatchingLastWord(args2String, new String[] {"remove"});
		else if(args2String.length == 2 && args2String[0].equals("remove"))
			return this.getListOfStringsMatchingLastWord(args2String, new String[] {"true", "false"});
		else
			return null;
		
	}
	
	protected final void removeHerobrine(final boolean par1)
	{
		
		EntityHerobrine.remove = par1;
		JNF.giveFiles().vaSaveReturn(Minecraft.getMinecraft().mcDataDir + "\\vw\\settings.va",
				new Object[]{"RemoveHerobrine", }, 
				new Object[]{par1,					},
			true);
		
	}

	@Override
	public int compareTo(final Object par1Object)
	{
		
		return this.compareTo((ICommand) par1Object);
		
	}
	
}
