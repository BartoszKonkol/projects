package net.polishgames.rhenowar.util.world.populator;

import java.util.Map;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.world.task.SyncFixLightTask;

public final class PopulatorFixLight extends Populator
{
	
	public static final PopulatorFixLight LIGHT = new PopulatorFixLight(false);
	public static final PopulatorFixLight LUMINANCE = new PopulatorFixLight(true);
	
	private final boolean fixLuminance;
	
	protected PopulatorFixLight(final boolean fixLuminance)
	{
		this.fixLuminance = fixLuminance;
	}
	
	public final boolean isFixLuminance()
	{
		return this.fixLuminance;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("fixLuminance", this.isFixLuminance());
		return super.giveProperties(map);
	}
	
	@Override
	public final void doPopulate(final World world, final Chunk chunk, final Random random)
	{
		Util.nonNull(world);
		Util.nonNull(random);
		new SyncFixLightTask(Util.nonNull(chunk), this.isFixLuminance()).runTaskLater(50L);
	}
	
}
