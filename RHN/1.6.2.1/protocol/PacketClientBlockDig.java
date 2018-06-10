package net.polishgames.rhenowar.util.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.event.PlayerPressQEvent;

public class PacketClientBlockDig extends Packet
{
	
	public static final PacketType PACKET = PacketType.Play.Client.BLOCK_DIG;

	public PacketClientBlockDig(final RhenowarPlugin plugin)
	{
		super(plugin, PacketClientBlockDig.PACKET);
	}
	
	@Override
	public void onPacketReceiving(final PacketEvent packetEvent)
	{
		final PlayerDigType digType = Util.nonNull(packetEvent).getPacket().getPlayerDigTypes().read(0);
		final boolean pressQ = digType == PlayerDigType.DROP_ITEM, pressCtrlQ = digType == PlayerDigType.DROP_ALL_ITEMS;
		if(pressQ || pressCtrlQ)
			packetEvent.setCancelled(this.doCallEvent(new PlayerPressQEvent(packetEvent.getPlayer(), pressCtrlQ)).getCancelled());
	}
	
}
