package net.polishgames.rhenowar.util.world.task;

import java.util.Map;
import org.bukkit.Chunk;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.TaskSchedulerSync;
import net.polishgames.rhenowar.util.Util;

public final class SyncFixLightTask extends TaskSchedulerSync
{
	
	private final Chunk chunk;
	private final boolean fixLuminance;
	
	public SyncFixLightTask(final Chunk chunk, final boolean fixLuminance)
	{
		this.chunk = Util.nonNull(chunk);
		this.fixLuminance = fixLuminance;
	}
	
	public SyncFixLightTask(final Chunk chunk)
	{
		this(chunk, false);
	}
	
	public final Chunk giveChunk()
	{
		return this.chunk;
	}
	
	public final boolean isFixLuminance()
	{
		return this.fixLuminance;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("chunk", this.giveChunk());
		map.put("fixLuminance", this.isFixLuminance());
		return super.giveProperties(map);
	}
	
	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		return this.giveUtil().doFixLight(this.giveChunk(), this.isFixLuminance()) != null;
	}
	
}
