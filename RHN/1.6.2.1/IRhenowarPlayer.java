package net.polishgames.rhenowar.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface IRhenowarPlayer extends Rhenowar
{
	
	public OfflinePlayer giveOfflinePlayer();
	public Player givePlayer();
	public boolean isOnline();
	
}
