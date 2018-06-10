package net.polishgames.rhenowar.util;

import java.util.Map;
import net.polishgames.rhenowar.util.task.SyncCalculatorTask;

public final class CalculatorTPS extends RhenowarObject
{
	
	public static final long STANDARD_VALUE = 20L;
	
	private final RhenowarPlugin plugin;
	private final SyncCalculatorTask task;
	
	public CalculatorTPS(final RhenowarPlugin plugin)
	{
		this.plugin = Util.nonNull(plugin);
		this.task = new SyncCalculatorTask(this);
		this.cancelled = true;
		this.setTPS(CalculatorTPS.STANDARD_VALUE);
	}
	
	private double tps;
	protected boolean cancelled;
	
	public final RhenowarPlugin givePlugin()
	{
		return this.plugin;
	}
	
	public final SyncCalculatorTask giveTask()
	{
		return this.task;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("plugin", this.givePlugin());
		map.put("task", this.giveTask());
		map.put("tps", this.getTPS());
		map.put("cancelled", this.isCancelled());
		return map;
	}
	
	public final boolean isCancelled()
	{
		return this.cancelled;
	}
	
	public final CalculatorTPS setTPS(final double tps)
	{
		this.tps = tps;
		return this;
	}
	
	public final double getTPS()
	{
		return this.tps < 1 || this.tps > CalculatorTPS.STANDARD_VALUE ? 1.0D : this.tps;
	}
	
	public final CalculatorTPS doStart()
	{
		if(this.isCancelled())
		{
			this.giveTask().runTaskRepeating(0, CalculatorTPS.STANDARD_VALUE);
			this.cancelled = false;
			return this;
		}
		else
			return null;
	}
	
	public final CalculatorTPS doStop()
	{
		if(!this.isCancelled())
		{
			this.giveTask().cancel();
			this.cancelled = true;
			return this;
		}
		else
			return null;
	}
	
}
