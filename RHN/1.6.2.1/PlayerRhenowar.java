package net.polishgames.rhenowar.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.OfflinePlayer;

public final class PlayerRhenowar extends RhenowarPlayer
{

	protected static final Map<OfflinePlayer, PlayerRhenowar> players = new HashMap<OfflinePlayer, PlayerRhenowar>();
	
	protected final List<IRhenowarPlayer> rhenowarPlayers;
	
	private PlayerRhenowar(final OfflinePlayer player)
	{
		super(player);
		this.rhenowarPlayers = new ArrayList<IRhenowarPlayer>();
	}
	
	public final List<IRhenowarPlayer> giveRhenowarPlayers()
	{
		return Collections.unmodifiableList(this.rhenowarPlayers);
	}
	
	public final <T extends IRhenowarPlayer> List<T> giveRhenowarPlayers(final Class<T> type)
	{
		final List<T> list = new ArrayList<T>();
		for(final IRhenowarPlayer rhenowarPlayer : this.giveRhenowarPlayers())
			if(type.isAssignableFrom(rhenowarPlayer.getClass()))
				list.add((T) rhenowarPlayer);
		return Collections.unmodifiableList(list);
	}
	
	public final synchronized PlayerRhenowar addRhenowarPlayer(final IRhenowarPlayer rhenowarPlayer)
	{
		if(Util.nonNull(rhenowarPlayer) instanceof PlayerRhenowar)
			return null;
		else
		{
			this.rhenowarPlayers.add(rhenowarPlayer);
			return this;
		}
	}
	
	public final synchronized PlayerRhenowar delRhenowarPlayer(final IRhenowarPlayer rhenowarPlayer)
	{
		this.rhenowarPlayers.remove(Util.nonNull(rhenowarPlayer));
		return this;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("players", this.giveRhenowarPlayers());
		return super.giveProperties(map);
	}
	
	public static final synchronized PlayerRhenowar givePlayerRhenowar(final OfflinePlayer player)
	{
		if(!PlayerRhenowar.players.containsKey(Util.nonNull(player)))
			PlayerRhenowar.players.put(player, new PlayerRhenowar(player));
		return PlayerRhenowar.players.get(player);
				
	}
	
}
