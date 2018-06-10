package net.polishgames.rhenowar.util.world.populator;

import java.util.Map;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;

public abstract class Populator extends BlockPopulator implements Rhenowar, IPopulator
{
	
	@Override
	@Deprecated
	public final void populate(final World world, final Random random, final Chunk chunk)
	{
		this.doPopulate(Util.nonNull(world), Util.nonNull(chunk), Util.nonNull(random));
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return super.toString();
	}
	
	public abstract void doPopulate(final World world, final Chunk chunk, final Random random);
	
}
