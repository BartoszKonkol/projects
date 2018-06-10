package net.polishgames.rhenowar.util.minigame;

import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class ActionEnd<R> extends Action<R>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	public ActionEnd(final String name, final Player player)
	{
		super(name, ActionType.END, player);
	}
	
	@Override
	public R giveResult()
	{
		return null;
	}
	
}
