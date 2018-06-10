package net.polishgames.rhenowar.util.task;

import java.util.Date;
import java.util.Map;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.TaskSchedulerSync;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.event.TimerEvent;

public final class SyncTimerTask extends TaskSchedulerSync
{
	
	private final Date date;
	
	public SyncTimerTask(final Date date)
	{
		this.date = Util.nonNull(date);
	}
	
	public SyncTimerTask()
	{
		this(new Date());
	}
	
	public final Date giveDate()
	{
		return this.date;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("date", this.giveDate());
		return super.giveProperties(map);
	}
	
	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		final Date date = new Date();
		final TimerEvent event = new TimerEvent(this.giveDate(), date, Math.round((date.getTime() - this.giveDate().getTime()) / 1000.0F * this.giveUtil().giveTPS()));
		this.giveUtil().givePluginManager().callEvent(event);
		return event;
	}
	
}
