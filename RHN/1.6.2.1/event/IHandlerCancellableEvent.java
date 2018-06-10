package net.polishgames.rhenowar.util.event;

import org.bukkit.event.Cancellable;
import net.polishgames.rhenowar.util.Rhenowar;

public interface IHandlerCancellableEvent extends Rhenowar, Cancellable
{
	
	public boolean getCancelled();

	@Override
	public default boolean isCancelled()
	{
		return this.getCancelled();
	}
	
}
