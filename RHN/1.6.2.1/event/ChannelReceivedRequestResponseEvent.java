package net.polishgames.rhenowar.util.event;

import java.util.Map;
import net.polishgames.rhenowar.util.Util;

public class ChannelReceivedRequestResponseEvent extends HandlerEvent implements IReceivedRequestResponseEvent
{
	
	private final String request;
	private final int id;
	private final Object result;
	
	public ChannelReceivedRequestResponseEvent(final String request, final int id, final Object result)
	{
		this.request = Util.nonEmpty(request);
		this.id = id;
		this.result = Util.nonNull(result);
	}
	
	@Override
	public final String giveRequest()
	{
		return this.request;
	}
	
	@Override
	public final int giveID()
	{
		return this.id;
	}
	
	@Override
	public final Object giveResult()
	{
		return this.result;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("request", this.giveRequest());
		map.put("id", this.giveID());
		map.put("result", this.giveResult());
		return super.giveProperties(map);
	}
	
}
