package net.polishgames.rhenowar.util.task;

import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.TaskSchedulerSync;
import net.polishgames.rhenowar.util.Util;

public final class SyncTeleportTask extends TaskSchedulerSync
{
	
	private final Player player;
	private final Location location;
	
	public SyncTeleportTask(final Player player, final Location location)
	{
		this.player = Util.nonNull(player);
		this.location = Util.nonNull(location);
	}
	
	public final Player givePlayer()
	{
		return this.player;
	}
	
	public final Location giveLocation()
	{
		return this.location;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("player", this.givePlayer());
		map.put("location", this.giveLocation());
		return super.giveProperties(map);
	}
	
	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		return this.givePlayer().teleport(this.giveLocation());
	}
	
}
