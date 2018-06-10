package net.polishgames.rhenowar.util.world;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;

public class WorldGeneratorEmpty extends WorldGenerator
{
	
	private static WorldGeneratorEmpty generator;
	
	protected WorldGeneratorEmpty() {}
	
	@Override
	public final TypeWorld giveType()
	{
		return TypeWorld.FLAT;
	}
	
	@Override
	public boolean hasSpawnLocation(final Location location)
	{
		return true;
	}
	
	@Override
	public final ChunkData doGenerateTerrain(final Location location, final World world, final Random random, final BiomeGrid biome)
	{
		return this.giveChunkData(world);
	}
	
	public static final WorldGeneratorEmpty giveGenerator()
	{
		if(WorldGeneratorEmpty.generator == null)
			WorldGeneratorEmpty.generator = new WorldGeneratorEmpty();
		return WorldGeneratorEmpty.generator;
	}
	
	public static final WorldCreator giveCreator(final String name, final String settings, final long seed)
	{
		return WorldCreator
				.name(name)
				.seed(seed)
				.generator(WorldGeneratorEmpty.giveGenerator())
				.environment(Environment.NORMAL)
				.type(WorldType.FLAT)
				.generateStructures(false)
				.generatorSettings(settings)
			;
	}
	
	public static final WorldCreator giveCreator(final String name, final String settings)
	{
		return WorldGeneratorEmpty.giveCreator(name, settings, (long) Math.random());
	}
	
	public static final WorldCreator giveCreator(final String name, final long seed)
	{
		return WorldGeneratorEmpty.giveCreator(name, "", seed);
	}
	
	public static final WorldCreator giveCreator(final String name)
	{
		return WorldGeneratorEmpty.giveCreator(name, "", (long) Math.random());
	}
	
}
