package net.polishgames.rhenowar.util.auth.task;

import java.util.Map;
import net.polishgames.rhenowar.util.TaskSchedulerAsync;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.auth.PlayerAuth;

public abstract class AsyncAuthTask extends TaskSchedulerAsync implements AuthTask
{
	
	private final PlayerAuth playerAuth;
	
	public AsyncAuthTask(final PlayerAuth playerAuth)
	{
		this.playerAuth = Util.nonNull(playerAuth);
	}
	
	@Override
	public final PlayerAuth givePlayerAuth()
	{
		return this.playerAuth;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		return super.giveProperties(AuthTask.super.giveProperties(map));
	}
	
}
