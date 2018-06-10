package net.polishgames.rhenowar.util.world.populator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import net.polishgames.rhenowar.util.IRhenowar;

@FunctionalInterface
public interface IPopulator extends IRhenowar
{
	
	public void populate(final World world, final Random random, final Chunk chunk);
	
}
