package net.polishgames.rhenowar.util.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.Configuration;
import net.polishgames.rhenowar.util.TaskScheduler;
import net.polishgames.rhenowar.util.TaskSchedulerSync;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.protocol.PacketServerUpdateSign;

public final class SyncRequestSignSendTask extends TaskSchedulerSync
{
	
	protected final List<World> worlds;
	
	public SyncRequestSignSendTask()
	{
		this.worlds = new ArrayList<World>();
	}
	
	public final List<World> getWorlds()
	{
		return Collections.unmodifiableList(this.worlds);
	}
	
	public final SyncRequestSignSendTask setWorlds(final List<World> worlds)
	{
		this.worlds.clear();
		this.worlds.addAll(Util.nonNull(worlds));
		return this;
	}
	
	public final SyncRequestSignSendTask setWorlds(final World... worlds)
	{
		return this.setWorlds(this.giveUtil().toList(worlds));
	}
	
	public final SyncRequestSignSendTask addWorlds(final World... worlds)
	{
		for(final World world : Util.nonNull(worlds))
			this.worlds.add(Util.nonNull(world));
		return this;
	}
	
	public final SyncRequestSignSendTask delWorlds(final World... worlds)
	{
		for(final World world : Util.nonNull(worlds))
			this.worlds.remove(Util.nonNull(world));
		return this;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("worlds", this.getWorlds());
		return super.giveProperties(map);
	}
	
	public final void doRequestSignSend()
	{
		this.task(this);
	}
	
	@Override
	public synchronized Object task(final TaskScheduler argument)
	{
		final Configuration config = this.giveUtil().giveUtilConfigSigns();
		for(final World world : this.getWorlds())
			for(final Object obj : config.getList(this.giveUtil().giveUtilConfigPrefix() + world.getName(), new ArrayList<String>()))
			{
				final String[] array = String.valueOf(obj).split(",");
				if(array.length == 4)
				{
					final BlockState state = this.giveUtil().giveWorld(array[0]).getBlockAt(Integer.valueOf(array[1]), Integer.valueOf(array[2]), Integer.valueOf(array[3])).getState();
					if(state instanceof Sign)
					{
						final Sign sign = (Sign) state;
						if(this.giveUtil().isSignTP(sign))
							PacketServerUpdateSign.doRequestSend(sign);
					}
				}
			}
		return null;
	}
	
}
