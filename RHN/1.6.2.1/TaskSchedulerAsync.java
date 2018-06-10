package net.polishgames.rhenowar.util;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class TaskSchedulerAsync extends TaskScheduler
{

	public TaskSchedulerAsync(final BukkitScheduler scheduler, final Plugin plugin)
	{
		super(scheduler, plugin);
		this.sync = false;
	}

	public TaskSchedulerAsync()
	{
		this.sync = false;
	}
	
}
