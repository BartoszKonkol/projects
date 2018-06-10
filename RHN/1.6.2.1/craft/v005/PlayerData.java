package net.polishgames.rhenowar.util.craft.v005;

import java.util.Collections;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.polishgames.rhenowar.util.ProtocolVersion;
import net.polishgames.rhenowar.util.Util;

public class PlayerData extends net.polishgames.rhenowar.util.craft.PlayerData<CraftPlayer, EntityPlayer>
{

	public PlayerData(final OfflinePlayer player, final Util util)
	{
		super(player, util);
	}

	@Override
	public final Class<CraftPlayer> giveCraftPlayerType()
	{
		return CraftPlayer.class;
	}

	@Override
	public final Class<EntityPlayer> giveEntityPlayerType()
	{
		return EntityPlayer.class;
	}

	@Override
	public final CraftPlayer giveCraftPlayer()
	{
		if(this.isOnline() && this.givePlayer() instanceof CraftPlayer)
			return (CraftPlayer) this.givePlayer();
		else
			return null;
	}

	@Override
	public final EntityPlayer giveEntityPlayer()
	{
		if(this.hasCraftPlayer())
			return this.giveCraftPlayer().getHandle();
		else
			return null;
	}

	@Override
	public final List<ProtocolVersion> giveProtocolVersions()
	{
		if(this.hasEntityPlayer())
			return Collections.unmodifiableList(this.giveUtil().toList(ProtocolVersion.giveValues(this.giveEntityPlayer().playerConnection.b().getVersion())));
		else
			return Collections.emptyList();
	}
	
}
