package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.entity.Player;

public abstract class PlayerHandlerCancellableEvent extends PlayerHandlerEvent implements IHandlerCancellableEvent
{
	
	public PlayerHandlerCancellableEvent(final Player player)
	{
		super(player);
	}
	
	private boolean cancelled;
	
	@Override
	public final void setCancelled(final boolean cancel)
	{
		this.cancelled = cancel;
	}
	
	@Override
	public final boolean getCancelled()
	{
		return this.cancelled;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("cancelled", this.getCancelled());
		return super.giveProperties(map);
	}
	
}
