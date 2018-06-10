package vw.command;

import java.util.Arrays;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;

public class CommandRealTime extends CommandBase
{
	
	public static final byte SCAN = 1;
	
	@Override
	public String getName() // getCommandName
	{
		
		return "realtime";
		
	}
	
	@Override
	public List<String> getAliases()
	{
		
		return Arrays.asList(new String[]{"rt"});
		
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		
		return 2;
		
	}
	
	@Override
	public String getCommandUsage(final ICommandSender par1ICommandSender) 
	{
		
		return "commands.realtime.usage";
		
	}
	
	protected static boolean work;
	
	@Override
	public void execute(final ICommandSender par1ICommandSender, final String[] args2String) throws WrongUsageException // processCommand 
	{
		
		if (args2String.length == 1)
		{
			
			final RealTime realtime = new RealTime();
			
			if (args2String[0].equals("true"))
			{
				
				this.work = true;
				realtime.enable();
				this.notifyOperators(par1ICommandSender, this, "commands.realtime.true", new Object[0]);
				return;
				
			}
			else if (args2String[0].equals("false"))
			{
				
				this.work = false;
				realtime.disable();
				this.notifyOperators(par1ICommandSender, this, "commands.realtime.false", new Object[0]);
				return;
				
			}
			
		}
		
		throw new WrongUsageException("commands.realtime.usage", new Object[0]);
		
	}
	
	@Override
	public List<?> addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] args2String, BlockPos par3BlockPos)
	{
		
		if(args2String.length == 1)
			return this.getListOfStringsMatchingLastWord(args2String, new String[] {"true", "false"});
		else
			return null;
		
	}
	
	@Override
	public int compareTo(final Object par1Object)
	{
		
		return this.compareTo((ICommand) par1Object);
		
	}

}
