package net.polishgames.rhenowar.util.craft;

import java.util.List;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.IRhenowarPlayer;
import net.polishgames.rhenowar.util.PlayerRhenowar;
import net.polishgames.rhenowar.util.ProtocolVersion;
import net.polishgames.rhenowar.util.Util;

public abstract class PlayerData<CraftPlayer extends Player, EntityPlayer> extends Data implements IRhenowarPlayer
{

	private final OfflinePlayer player;
	
	public PlayerData(final OfflinePlayer player, final Util util)
	{
		super(util);
		this.player = Util.nonNull(player);
		final PlayerRhenowar playerRhenowar = PlayerRhenowar.givePlayerRhenowar(this.giveOfflinePlayer());
		if(!playerRhenowar.giveRhenowarPlayers().contains(this))
			playerRhenowar.addRhenowarPlayer(this);
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
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("player", this.givePlayer());
		map.put("playerOnline", this.isOnline());
		map.put("playerOffline", this.giveOfflinePlayer());
		map.put("playerCraft", this.giveCraftPlayer());
		map.put("playerEntity", this.giveEntityPlayer());
		map.put("classCraft", this.giveCraftPlayerType());
		map.put("classEntity", this.giveEntityPlayerType());
		map.put("versionProtocol", this.giveProtocolVersions());
		return super.giveProperties(map);
	}
	
	@Override
	public final boolean isOnline()
	{
		return this.givePlayer() != null;
	}
	
	public final boolean hasCraftPlayer()
	{
		return this.giveCraftPlayer() != null;
	}
	
	public final boolean hasEntityPlayer()
	{
		return this.giveEntityPlayer() != null;
	}
	
	public abstract Class<CraftPlayer> giveCraftPlayerType();
	public abstract Class<EntityPlayer> giveEntityPlayerType();
	public abstract CraftPlayer giveCraftPlayer();
	public abstract EntityPlayer giveEntityPlayer();
	public abstract List<ProtocolVersion> giveProtocolVersions();
	
}
