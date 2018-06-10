package net.polishgames.rhenowar.util.task;

import java.util.Map;
import net.polishgames.rhenowar.util.CalculatorTPS;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.TaskSchedulerSync;
import net.polishgames.rhenowar.util.Util;

public final class SyncCalculatorTask extends TaskSchedulerSync
{
	
	private final CalculatorTPS calculatorTPS;
	
	public SyncCalculatorTask(final CalculatorTPS calculatorTPS)
	{
		this.calculatorTPS = Util.nonNull(calculatorTPS);
	}
	
	public SyncCalculatorTask()
	{
		this.calculatorTPS = this.giveUtil().giveCalculatorTPS();
	}
	
	protected volatile long time;
	
	public final CalculatorTPS giveCalculatorTPS()
	{
		return this.calculatorTPS;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("calculatorTPS", this.giveCalculatorTPS());
		return super.giveProperties(map);
	}
	
	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		final long time = System.nanoTime();
		if(this.time > 0)
			this.giveCalculatorTPS().setTPS(CalculatorTPS.STANDARD_VALUE / ((time - this.time) / Math.pow((2 << 2) + 2, (2 << 2) + 1))); // r=s/((n-o)/v)
		this.time = time;
		return this.giveCalculatorTPS().getTPS();
	}
	
}
