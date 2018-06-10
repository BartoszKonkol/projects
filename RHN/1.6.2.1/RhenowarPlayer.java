package net.polishgames.rhenowar.util;

import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class RhenowarPlayer extends RhenowarObject implements IRhenowarPlayer
{

	private final OfflinePlayer player;
	
	public RhenowarPlayer(final OfflinePlayer player)
	{
		this.player = Util.nonNull(player);
	}
	
	@Override
	public final OfflinePlayer giveOfflinePlayer()
	{
		return this.player;
	}
	
	@Override
	public final Player givePlayer()
	{
		return this.giveOfflinePlayer().getPlayer();
	}
	
	@Override
	public final boolean isOnline()
	{
		return this.givePlayer() != null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("player", this.givePlayer());
		map.put("playerOnline", this.isOnline());
		map.put("playerOffline", this.giveOfflinePlayer());
		return map;
	}
	
}
