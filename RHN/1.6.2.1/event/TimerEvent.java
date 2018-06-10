package net.polishgames.rhenowar.util.event;

import java.util.Date;
import java.util.Map;

public class TimerEvent extends HandlerEvent
{
	
	private final Date dateInitial, dateCurrent;
	private final long ticks;
	
	public TimerEvent(final Date dateInitial, final Date dateCurrent, final long ticks)
	{
		this.dateInitial = dateInitial;
		this.dateCurrent = dateCurrent;
		this.ticks = ticks;
	}
	
	public final Date giveDateInitial()
	{
		return this.dateInitial;
	}
	
	public final Date giveDateCurrent()
	{
		return this.dateCurrent;
	}
	
	public final long giveTicks()
	{
		return this.ticks;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("dateInitial", this.giveDateInitial());
		map.put("dateCurrent", this.giveDateCurrent());
		map.put("ticks", this.giveTicks());
		return map;
	}
	
}
