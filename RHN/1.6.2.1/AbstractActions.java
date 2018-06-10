package net.polishgames.rhenowar.util;

import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.minigame.Action;
import net.polishgames.rhenowar.util.minigame.ActionEnd;

public abstract class AbstractActions extends Listener
{
	
	public AbstractActions(final RhenowarPlugin plugin)
	{
		super(plugin);
	}
	
	public final Action<?> giveActionEnd(final String name, final Player player)
	{
		return new ActionEnd<Object>(name, player);
	}
	
	public final Action<?> giveActionEnd(final String name, final RhenowarPlayer player)
	{
		if(player.isOnline())
			return this.giveActionEnd(name, player.givePlayer());
		else
			return null;
	}
	
}
