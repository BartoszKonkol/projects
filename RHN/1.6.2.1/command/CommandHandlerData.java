package net.polishgames.rhenowar.util.command;

import java.lang.reflect.Method;
import java.util.Map;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;

public final class CommandHandlerData extends RhenowarObject
{
	
	private final CommandHandler handler;
	private final Method method;
	private final Rhenowar listener;
	
	public CommandHandlerData(final CommandHandler handler, final Method method, final Rhenowar listener)
	{
		this.handler = Util.nonNull(handler);
		this.method = Util.nonNull(method);
		this.listener = Util.nonNull(listener);
	}
	
	public final CommandHandler giveHandler()
	{
		return this.handler;
	}
	
	public final Method giveMethod()
	{
		return this.method;
	}
	
	public final Rhenowar giveListener()
	{
		return this.listener;
	}
	
	public final Class<? extends Rhenowar> giveListenerClass()
	{
		return this.giveListener().getClass();
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("handler", this.giveHandler());
		map.put("method", this.giveMethod());
		map.put("listener", this.giveListener());
		map.put("listenerClass", this.giveListenerClass());
		return map;
	}
	
}
