package net.polishgames.rhenowar.util.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.sk89q.worldedit.BlockVector;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import net.polishgames.rhenowar.util.Region;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.Vector;
import net.polishgames.rhenowar.util.world.area.Area;
import net.polishgames.rhenowar.util.world.area.Chunk;
import net.polishgames.rhenowar.util.world.populator.PopulatorFixLight;

public class WorldGeneratorArea extends WorldGenerator
{
	
	private static WorldGeneratorArea generator;
	
	private final Area area;
	
	public WorldGeneratorArea(final Area area)
	{
		this.area = Util.nonNull(area);
		this.givePopulators().add(PopulatorFixLight.LUMINANCE);
	}
	
	public final Area giveArea()
	{
		return this.area;
	}
	
	@Override
	public final TypeWorld giveType()
	{
		return TypeWorld.FLAT;
	}
	
	@Override
	public final ChunkData doGenerateTerrain(final Location location, final World world, final Random random, final BiomeGrid biome)
	{
		final ChunkData data = this.giveChunkData(world);
		if(super.doGenerateTerrain(location, world, random, biome) == null)
		{
			final Chunk chunk = this.giveArea().giveChunks().get(Area.giveChunkIndex((byte) location.getBlockX(), (byte) location.getBlockZ()));
			if(chunk != null)
				this.doGenerateArea(data, chunk);
		}
		return data;
	}
	
	protected void doGenerateArea(final ChunkData data, final Chunk chunk)
	{
		Util.nonNull(data);
		for(final short index : Util.nonNull(chunk))
			data.setBlock(Chunk.giveBlockPosX(index), Chunk.giveBlockPosY(index), Chunk.giveBlockPosZ(index), chunk.giveBlocks().get(index).toMaterialDataBukkit());
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("area", this.giveArea());
		return super.giveProperties(map);
	}
	
	public static final WorldGeneratorArea giveGenerator()
	{
		if(WorldGeneratorArea.generator == null)
			WorldGeneratorArea.generator = new WorldGeneratorArea(new Area(null, new Region(new BlockVector(0, 0, 0), new BlockVector(0, 0xFF, 0)), new Vector(0, 0, 0), new HashMap<Short, Chunk>()));
		return WorldGeneratorArea.generator;
	}
	
	public static final WorldCreator giveCreator(final String name, final Area area)
	{
		return WorldCreator
				.name(name)
				.seed(0L)
				.generator(area != null ? new WorldGeneratorArea(area) : WorldGeneratorArea.giveGenerator())
				.environment(Environment.NORMAL)
				.type(WorldType.FLAT)
				.generateStructures(false)
				.generatorSettings("")
			;
	}
	
	public static final WorldCreator giveCreator(final String name)
	{
		return WorldGeneratorArea.giveCreator(name, null);
	}
	
}
