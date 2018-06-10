package net.polishgames.rhenowar.util;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class TaskSchedulerSync extends TaskScheduler
{

	public TaskSchedulerSync(final BukkitScheduler scheduler, final Plugin plugin)
	{
		super(scheduler, plugin);
		this.async = false;
	}

	public TaskSchedulerSync()
	{
		this.async = false;
	}
	
}
