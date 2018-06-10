package net.polishgames.rhenowar.conquest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum MessageType implements IType
{
	
	NORMAL,
	DEBUG,
	SEVERE,
	POSITIVE;
	
	public static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.DARK_GREEN + Conquest.giveConquest().getName() + ChatColor.GOLD + "]" + ChatColor.WHITE + " ";
	
	public void doSend(final Player player, final String message)
	{
		final Conquest conquest = Conquest.giveConquest();
		String text = "";
		switch(this)
		{
			case SEVERE:
				text = ChatColor.RED.toString();
				break;
			case POSITIVE:
				text = ChatColor.GREEN.toString();
				break;
			case DEBUG:
				if(conquest.giveFileConfig().getBoolean("conquest.DebugMode") || !conquest.isRegionConfigurationAlready() || conquest.giveManagers().contains(player.getName()))
					text = "[" + this.toString() + "] ";
				else
					return;
			default:
				break;
		}
		player.sendMessage(PREFIX + text + message);
	}
	
}
