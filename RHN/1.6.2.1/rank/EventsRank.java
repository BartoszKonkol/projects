package net.polishgames.rhenowar.util.rank;

import java.sql.SQLException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import net.polishgames.rhenowar.util.AbstractEvents;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.event.PlayerLeaveEvent;

public final class EventsRank extends AbstractEvents
{

	public EventsRank(final RhenowarPlugin plugin)
	{
		super(plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		try
		{
			PlayerRank.givePlayerRank(player).onJoin(player);
		}
		catch(final SQLException e)
		{
			if(Util.hasUtil())
				Util.giveUtil().doReportErrorSQL(e);
			else
				e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerLeave(final PlayerLeaveEvent event)
	{
		final Player player = event.getPlayer();
		try
		{
			PlayerRank.givePlayerRank(player).onLeave(player);
		}
		catch(final SQLException e)
		{
			if(Util.hasUtil())
				Util.giveUtil().doReportErrorSQL(e);
			else
				e.printStackTrace();
		}
	}
	
}
