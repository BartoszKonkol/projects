package net.polishgames.rhenowar.util.event;

import org.bukkit.event.HandlerList;
import net.polishgames.rhenowar.util.Rhenowar;

public interface IHandlerEvent extends Rhenowar
{
	
	static final HandlerList handlers = new HandlerList();
	
	public default HandlerList getHandlers()
	{
		return IHandlerEvent.getHandlerList();
	}
	
	public static HandlerList getHandlerList()
	{
		return IHandlerEvent.handlers;
	}
	
}
