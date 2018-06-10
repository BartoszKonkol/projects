package net.polishgames.rhenowar.util.protocol;

import java.util.Map;
import org.bukkit.event.Event;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;

public abstract class Packet extends PacketAdapter implements Rhenowar
{
	
	private final PacketType packet;
	
	public Packet(final RhenowarPlugin plugin, final PacketType packet)
	{
		this(plugin, packet, ListenerPriority.NORMAL);
	}
	
	public Packet(final RhenowarPlugin plugin, final PacketType packet, final ListenerPriority priority)
	{
		super(Util.nonNull(plugin), Util.nonNull(priority), Util.nonNull(packet));
		this.packet = packet;
	}
	
	public final PacketType givePacket()
	{
		return this.packet;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("plugin", this.getPlugin());
		map.put("packet", this.givePacket());
		map.put("whitelistReceiving", this.getReceivingWhitelist());
		map.put("whitelistSending", this.getSendingWhitelist());
		map.put("sideConnection", this.connectionSide);
		return map;
	}
	
	public final <E extends Event> E doCallEvent(final E event)
	{
		this.getPlugin().getServer().getPluginManager().callEvent(event);
		return event;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return super.toString();
	}
	
}
