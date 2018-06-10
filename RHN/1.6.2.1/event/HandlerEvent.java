package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import net.polishgames.rhenowar.util.Util;

public abstract class HandlerEvent extends Event implements IHandlerEvent
{
	
	@Override
	public final HandlerList getHandlers()
	{
		return IHandlerEvent.super.getHandlers();
	}
	
	public static HandlerList getHandlerList()
	{
		return IHandlerEvent.getHandlerList();
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.getEventName());
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
