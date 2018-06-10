package net.polishgames.rhenowar.util.serialization;

import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import net.polishgames.rhenowar.util.Util;

public class Player extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final String name;
	private final UUID uuid;
	
	public Player(final String name, final UUID uuid)
	{
		this.name = Util.nonEmpty(name);
		this.uuid = Util.nonNull(uuid);
	}
	
	public Player(final OfflinePlayer player)
	{
		this(Util.nonNull(player).getName(), player.getUniqueId());
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final UUID giveUUID()
	{
		return this.uuid;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveName());
		map.put("uuid", this.giveUUID());
		return map;
	}
	
	public final org.bukkit.entity.Player toPlayerBukkitFromUUID()
	{
		return Util.hasUtil() ? Util.giveUtil().givePlayer(this.giveUUID()) : Bukkit.getPlayer(this.giveUUID());
	}
	
	public final org.bukkit.entity.Player toPlayerBukkitFromName()
	{
		return Util.hasUtil() ? Util.giveUtil().givePlayer(this.giveName()) : Bukkit.getPlayer(this.giveName());
	}
	
	public final OfflinePlayer toPlayerOfflineBukkitFromUUID()
	{
		return Util.hasUtil() ? Util.giveUtil().giveServer().getOfflinePlayer(this.giveUUID()) : Bukkit.getOfflinePlayer(this.giveUUID());
	}
	
	@SuppressWarnings("deprecation")
	public final OfflinePlayer toPlayerOfflineBukkitFromName()
	{
		return Util.hasUtil() ? Util.giveUtil().giveServer().getOfflinePlayer(this.giveName()) : Bukkit.getOfflinePlayer(this.giveName());
	}
	
	public final org.bukkit.entity.Player toPlayerBukkit()
	{
		final org.bukkit.entity.Player playerFromUUID = this.toPlayerBukkitFromUUID(), playerFromName = this.toPlayerBukkitFromName();
		return playerFromUUID == playerFromName ? playerFromUUID : null;
	}
	
	public final OfflinePlayer toPlayerOfflineBukkit()
	{
		final OfflinePlayer playerFromUUID = this.toPlayerOfflineBukkitFromUUID(), playerFromName = this.toPlayerOfflineBukkitFromName();
		return playerFromUUID == playerFromName ? playerFromUUID : null;
	}
	
	@Override
	public Player clone()
	{
		return new Player(this.giveName(), this.giveUUID());
	}
	
}
