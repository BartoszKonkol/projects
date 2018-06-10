package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.entity.Player;

public class PlayerPressQEvent extends PlayerHandlerCancellableEvent
{
	
	private final boolean ctrl;

	public PlayerPressQEvent(final Player player, final boolean ctrl)
	{
		super(player);
		this.ctrl = ctrl;
	}
	
	public final boolean isCtrl()
	{
		return this.ctrl;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("ctrl", this.isCtrl());
		return super.giveProperties(map);
	}
	
}
