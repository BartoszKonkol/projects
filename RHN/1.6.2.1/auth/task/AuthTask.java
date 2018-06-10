package net.polishgames.rhenowar.util.auth.task;

import java.util.Map;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Task;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.auth.PlayerAuth;

public interface AuthTask extends Rhenowar, Task<TaskScheduler, Object>
{
	
	public PlayerAuth givePlayerAuth();

	@Override
	public default Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("playerAuth", this.givePlayerAuth());
		return map;
	}
	
}
