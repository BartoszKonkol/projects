package net.polishgames.rhenowar.util.auth.task;

import java.util.Map;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.auth.PlayerAuth;

public final class SyncKickTask extends SyncAuthTask
{
	
	private final String message;
	
	public SyncKickTask(final PlayerAuth playerAuth, final String message)
	{
		super(playerAuth);
		this.message = Util.nonEmpty(message);
	}
	
	public final String giveMessage()
	{
		return this.message;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("message", this.giveMessage());
		return super.giveProperties(map);
	}

	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		return this.giveUtil().doKick(this.givePlayerAuth().givePlayer(), this.giveMessage()) != null;
	}
	
}
