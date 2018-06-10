package net.polishgames.rhenowar.util.auth.task;

import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.auth.PlayerAuth;

public final class AsyncLeaveTask extends AsyncAuthTask
{
	
	public AsyncLeaveTask(final PlayerAuth playerAuth)
	{
		super(playerAuth);
	}

	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		return this.givePlayerAuth().onLeave();
	}
	
}
