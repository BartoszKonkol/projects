package net.polishgames.rhenowar.util.auth.task;

import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.auth.PlayerAuth;

public final class AsyncTimeoutTask extends AsyncAuthTask
{
	
	public AsyncTimeoutTask(final PlayerAuth playerAuth)
	{
		super(playerAuth);
	}

	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		final PlayerAuth playerAuth = this.givePlayerAuth();
		final boolean logged = playerAuth.isLogged();
		if(!logged)
			playerAuth.onError(playerAuth.giveMessage("timeout"));
		return logged;
	}
	
}
