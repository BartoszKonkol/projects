package net.polishgames.rhenowar.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommands extends Listener
{

	public AbstractCommands(final RhenowarPlugin plugin)
	{
		super(plugin);
	}
	
	public static final void doSendSuccess(final CommandSender sender)
	{
		AbstractCommands.doSend(sender, "SuccessCommand", "Command has been just executed.");
	}
	
	public static final void doSendNoSuccess(final CommandSender sender, final String label)
	{
		AbstractCommands.doSend(sender, "NoSuccessCommand", "This command has wrong syntax!", Util.nonEmpty(label));
	}
	
	public static final void doSendNoPermission(final CommandSender sender)
	{
		AbstractCommands.doSend(sender, "NoPermissionCommand", "You don't have permission to perform this command!");
	}
	
	public static final void doSendPlayerIsOffline(final CommandSender sender, final String username)
	{
		AbstractCommands.doSend(sender, "PlayerIsOffline", null, Util.nonEmpty(username));
	}
	
	public static final void doSendNumberFormat(final CommandSender sender, final int index)
	{
		String number = "-?-";
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final String key = util.giveUtilConfigPrefix() + "message.number";
			if(sender instanceof Player)
				number = util.giveMessage(key, (Player) sender);
			else
				number = util.giveMessage(key);
		}
		AbstractCommands.doSend(sender, "NumberFormat", null, number, index);
	}
	
	public static final void doSendNumberIncorrect(final CommandSender sender, final String argument)
	{
		AbstractCommands.doSend(sender, "NumberIncorrect", null, argument);
	}
	
	public static final void doSend(final CommandSender sender, final String messageKey, final String messageError, final Object... arguments)
	{
		if(Util.hasUtil())
			AbstractCommands.doSend(sender, Util.giveUtil().giveUtilPlugin(), messageKey, messageError, arguments);
		else if(messageError != null && !messageError.isEmpty())
			sender.sendMessage(messageError);
	}
	
	public static final void doSend(final CommandSender sender, final RhenowarPlugin plugin, final String messageKey, final String messageError, final Object... arguments)
	{
		Util.nonNull(sender);
		Util.nonNull(plugin);
		Util.nonEmpty(messageKey);
		if(Util.hasUtil())
			AbstractCommands.doSend(AbstractCommands.giveSendFunction(sender, plugin), AbstractCommands.giveMessageFunction(sender), messageKey.startsWith("@") ? messageKey.substring(1) : Util.giveUtil().giveUtilConfigPrefix() + "message." + messageKey, arguments);
		else if(messageError != null && !messageError.isEmpty())
			sender.sendMessage(messageError);
	}
	
	public static final void doSend(final SendFunction send, final MessageFunction message, final String key, final Object... arguments)
	{
		AbstractCommands.doSend(Util.nonNull(send), String.format(Util.nonNull(message).apply(Util.nonEmpty(key)), arguments));
	}
	
	public static final void doSend(final SendFunction function, final String message)
	{
		Util.nonNull(function).apply(message);
	}
	
	public static final void doSend(final CommandSender sender, final String message)
	{
		AbstractCommands.giveSendFunction(sender).apply(message);
	}
	
	public static final void doSend(final CommandSender sender, final String message, final RhenowarPlugin plugin)
	{
		AbstractCommands.giveSendFunction(sender, plugin).apply(message);
	}
	
	public static final void doSendUtil(final CommandSender sender, final String message)
	{
		AbstractCommands.giveSendFunctionUtil(sender).apply(message);
	}
	
	public static final MessageFunction giveMessageFunction(final CommandSender sender)
	{
		return Util.hasUtil() ? Util.giveUtil().giveMessageFunction(Util.nonNull(sender)) : null;
	}
	
	public static final SendFunction giveSendFunction(final CommandSender sender)
	{
		return Util.hasUtil() ? Util.giveUtil().giveSendFunction(Util.nonNull(sender)) : null;
	}
	
	public static final SendFunction giveSendFunction(final CommandSender sender, final RhenowarPlugin plugin)
	{
		return Util.hasUtil() ? Util.giveUtil().giveSendFunction(Util.nonNull(sender), Util.nonNull(plugin)) : null;
	}
	
	public static final SendFunction giveSendFunctionUtil(final CommandSender sender)
	{
		return Util.hasUtil() ? Util.giveUtil().giveUtilSendFunction(Util.nonNull(sender)) : null;
	}
	
}
