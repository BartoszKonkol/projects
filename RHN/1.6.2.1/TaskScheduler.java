package net.polishgames.rhenowar.util;

import java.util.Map;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public abstract class TaskScheduler extends BukkitRunnable implements Rhenowar, Task<TaskScheduler, Object>
{
	
	private final Util util;
	private final BukkitScheduler scheduler;
	private final Plugin plugin;
	
	public TaskScheduler(final Util util, final BukkitScheduler scheduler, final Plugin plugin)
	{
		this.util = Util.nonNull(util);
		this.scheduler = Util.nonNull(scheduler);
		this.plugin = Util.nonNull(plugin);
	}
	
	public TaskScheduler(final BukkitScheduler scheduler, final Plugin plugin)
	{
		this.scheduler = Util.nonNull(scheduler);
		this.plugin = Util.nonNull(plugin);
		if(Util.hasUtil())
			this.util = Util.giveUtil();
		else
		{
			Util.doThrowNPE();
			this.util = null;
		}
	}
	
	public TaskScheduler()
	{
		if(Util.hasUtil())
		{
			this.util = Util.giveUtil();
			this.scheduler = this.giveUtil().giveScheduler();
			this.plugin = this.giveUtil().giveUtilPlugin();
		}
		else
		{
			Util.doThrowNPE();
			this.util = null;
			this.scheduler = null;
			this.plugin = null;
		}
	}
	
	private int taskID = -1;
	private Object result;
	
	protected boolean sync = true, async = true;
	
	public final Util giveUtil()
	{
		return this.util;
	}
	
	public final BukkitScheduler giveScheduler()
	{
		return this.scheduler;
	}
	
	public final Plugin givePlugin()
	{
		return this.plugin;
	}
	
	public final int giveTaskID()
	{
		return this.taskID;
	}
	
	public final Object giveResult()
	{
		return this.result;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.giveUtil());
		map.put("scheduler", this.giveScheduler());
		map.put("plugin", this.givePlugin());
		map.put("taskID", this.giveTaskID());
		map.put("result", this.giveResult());
		return map;
	}
	
	@Override
	@Deprecated
	public final synchronized int getTaskId() throws IllegalStateException
	{
		return this.giveTaskID();
	}
	
	@Override
	public synchronized void cancel() throws IllegalStateException
	{
		final int taskID = this.giveTaskID();
		if(taskID >= 0)
			this.giveScheduler().cancelTask(taskID);
	}
	
	@Override
	public final synchronized BukkitTask runTask(final Plugin plugin) throws IllegalArgumentException, IllegalStateException
	{
		if(this.sync)
			return this.doSetupID(this.giveScheduler().runTask(Util.nonNull(plugin), (Runnable) this));
		else
			return null;
	}
	
	public final synchronized BukkitTask runTask() throws IllegalArgumentException, IllegalStateException
	{
		return this.runTask(this.givePlugin());
	}
	
	@Override
	public final synchronized BukkitTask runTaskAsynchronously(final Plugin plugin) throws IllegalArgumentException, IllegalStateException
	{
		if(this.async)
			return this.doSetupID(this.giveScheduler().runTaskAsynchronously(Util.nonNull(plugin), (Runnable) this));
		else
			return null;
	}

	public final synchronized BukkitTask runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException
	{
		return this.runTaskAsynchronously(this.givePlugin());
	}
	
	@Override
	public final synchronized BukkitTask runTaskLater(final Plugin plugin, final long delay) throws IllegalArgumentException, IllegalStateException
	{
		if(this.sync)
			return this.doSetupID(this.giveScheduler().runTaskLater(Util.nonNull(plugin), (Runnable) this, delay));
		else
			return null;
	}

	public final synchronized BukkitTask runTaskLater(final long delay) throws IllegalArgumentException, IllegalStateException
	{
		return this.runTaskLater(this.givePlugin(), delay);
	}
	
	@Override
	public final synchronized BukkitTask runTaskLaterAsynchronously(final Plugin plugin, final long delay) throws IllegalArgumentException, IllegalStateException
	{
		if(this.async)
			return this.doSetupID(this.giveScheduler().runTaskLaterAsynchronously(Util.nonNull(plugin), (Runnable) this, delay));
		else
			return null;
	}

	public final synchronized BukkitTask runTaskLaterAsynchronously(final long delay) throws IllegalArgumentException, IllegalStateException
	{
		return this.runTaskLaterAsynchronously(this.givePlugin(), delay);
	}
	
	@Override
	public final synchronized BukkitTask runTaskTimer(final Plugin plugin, final long delay, final long period) throws IllegalArgumentException, IllegalStateException
	{
		if(this.sync)
			return this.doSetupID(this.giveScheduler().runTaskTimer(Util.nonNull(plugin), (Runnable) this, delay, period));
		else
			return null;
	}

	public final synchronized BukkitTask runTaskTimer(final long delay, final long period) throws IllegalArgumentException, IllegalStateException
	{
		return this.runTaskTimer(this.givePlugin(), delay, period);
	}
	
	@Override
	public final synchronized BukkitTask runTaskTimerAsynchronously(final Plugin plugin, final long delay, final long period) throws IllegalArgumentException, IllegalStateException
	{
		if(this.async)
			return this.doSetupID(this.giveScheduler().runTaskTimerAsynchronously(Util.nonNull(plugin), (Runnable) this, delay, period));
		else
			return null;
	}

	public final synchronized BukkitTask runTaskTimerAsynchronously(final long delay, final long period) throws IllegalArgumentException, IllegalStateException
	{
		return this.runTaskTimerAsynchronously(this.givePlugin(), delay, period);
	}
	
	public final synchronized int runTaskDelayed(final Plugin plugin, final long delay)
	{
		if(this.sync)
			return this.doSetupID(this.giveScheduler().scheduleSyncDelayedTask(plugin, (Runnable) this, delay));
		else
			return -1;
	}
	
	public final synchronized int runTaskDelayed(final long delay)
	{
		return this.runTaskDelayed(this.givePlugin(), delay);
	}
	
	public final synchronized int runTaskDelayed(final Plugin plugin)
	{
		if(this.sync)
			return this.doSetupID(this.giveScheduler().scheduleSyncDelayedTask(plugin, (Runnable) this));
		else
			return -1;
	}
	
	public final synchronized int runTaskDelayed()
	{
		return this.runTaskDelayed(this.givePlugin());
	}
	
	public final synchronized int runTaskRepeating(final Plugin plugin, final long delay, final long period)
	{
		if(this.sync)
			return this.doSetupID(this.giveScheduler().scheduleSyncRepeatingTask(plugin, (Runnable) this, delay, period));
		else
			return -1;
	}
	
	public final synchronized int runTaskRepeating(final long delay, final long period)
	{
		return this.runTaskRepeating(this.givePlugin(), delay, period);
	}
	
	@Override
	public final void run()
	{
		final Object result = this.task(this);
		if(result != null)
			this.result = result;
	}
	
	protected final int doSetupID(final int taskID)
	{
		this.taskID = taskID;
		return this.giveTaskID();
	}
	
	protected final BukkitTask doSetupID(final BukkitTask task)
	{
		this.doSetupID(Util.nonNull(task).getTaskId());
		return task;
	}
	
}
