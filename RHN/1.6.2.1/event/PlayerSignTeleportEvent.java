package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import net.polishgames.rhenowar.util.Util;

public class PlayerSignTeleportEvent extends PlayerTeleportEvent implements ISignEvent
{
	
	private final Sign sign;

	public PlayerSignTeleportEvent(final Player player, final Location from, final Location to, final Sign sign)
	{
		super(Util.nonNull(player), Util.nonNull(from), Util.nonNull(to));
		this.sign = Util.nonNull(sign);
	}

	@Override
	public final Sign giveSign()
	{
		return this.sign;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.getEventName());
		map.put("player", this.getPlayer());
		map.put("cancelled", this.isCancelled());
		map.put("from", this.getFrom());
		map.put("to", this.getTo());
		map.put("sign", this.giveSign());
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this, false, true);
		else
			return super.toString();
	}
	
}
