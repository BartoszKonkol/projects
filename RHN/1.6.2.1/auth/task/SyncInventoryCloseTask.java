package net.polishgames.rhenowar.util.auth.task;

import java.util.Map;
import org.bukkit.entity.HumanEntity;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.TaskSchedulerSync;
import net.polishgames.rhenowar.util.Util;

public final class SyncInventoryCloseTask extends TaskSchedulerSync
{
	
	private final HumanEntity entity;
	
	public SyncInventoryCloseTask(final HumanEntity entity)
	{
		this.entity = Util.nonNull(entity);
	}
	
	public final HumanEntity giveEntity()
	{
		return this.entity;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("entity", this.giveEntity());
		return super.giveProperties(map);
	}
	
	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		this.giveEntity().closeInventory();
		return null;
	}
	
}
