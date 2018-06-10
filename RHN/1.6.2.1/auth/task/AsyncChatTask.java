package net.polishgames.rhenowar.util.auth.task;

import java.util.Map;
import net.polishgames.rhenowar.util.Password;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.auth.PlayerAuth;

public final class AsyncChatTask extends AsyncAuthTask
{
	
	private final Password password;
	
	public AsyncChatTask(final PlayerAuth playerAuth, final Password password)
	{
		super(playerAuth);
		this.password = Util.nonNull(password);
	}
	
	public final Password givePassword()
	{
		return this.password;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("password", this.givePassword());
		return super.giveProperties(map);
	}

	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		return this.givePlayerAuth().onChat(this.givePassword());
	}
	
}
