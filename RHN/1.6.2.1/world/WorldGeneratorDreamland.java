package net.polishgames.rhenowar.util.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.util.Vector;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.world.populator.PopulatorFixLight;

public class WorldGeneratorDreamland extends WorldGenerator
{
	
	private static WorldGeneratorDreamland generator;
	
	protected WorldGeneratorDreamland()
	{
		this.givePopulators().add(PopulatorFixLight.LIGHT);
	}
	
	protected double[] noiseArray, noise1, noise2, noise3;
	
	@Override
	public final TypeWorld giveType()
	{
		return TypeWorld.FLAT;
	}
	
	@Override
	public Location giveSpawnLocation(final World world, final Random random)
	{
		final Location location = super.giveSpawnLocation(world, random);
		final double y = world.getHighestBlockYAt(location);
		if(y > 0)
			location.setY(y);
		return location;
	}
	
	@Override
	public final ChunkData doGenerateTerrain(final Location location, final World world, final Random random, final BiomeGrid biome)
	{
		final ChunkData chunk = this.giveChunkData(world);
		if(super.doGenerateTerrain(location, world, random, biome) == null)
		{
			this.doGenerateFill(chunk, location, world, random);
			this.doReplaceBlocks(chunk, world, random);
		}
		return chunk;
	}
	
	protected void doGenerateFill(final ChunkData chunk, final Location location, final World world, final Random random)
	{
		Util.nonNull(chunk);
		Util.nonNull(location);
		Util.nonNull(world);
		Util.nonNull(random);
		final byte dimXZ = 2 << 1, dimY = 2 << 4;
		final int dimXZPlusOne = dimXZ + 1, dimYPlusOne = dimY + 1, divisor = 2 << 2, divisorIteration = 2 << 1, factorY = world.getMaxHeight() / dimY;
		this.noiseArray = this.doInitializeNoiseField(this.giveNoise(world, random), this.noiseArray, new Vector(location.getBlockX() * dimXZ, 0, location.getBlockZ() * dimXZ), new Vector(dimXZPlusOne, dimYPlusOne, dimXZPlusOne));
		for(int x = 0; x < dimXZ; x++)
			for(int z = 0; z < dimXZ; z++)
				for(int y = 0; y < dimY; y++)
				{
					final int
						product1 = x * dimXZPlusOne + z,
						product3 = (x + 1) * dimXZPlusOne + z,
						index1 = product1 * dimYPlusOne + y,
						index2 = (product1 + 1) * dimYPlusOne + y,
						index3 = product3 * dimYPlusOne + y,
						index4 = (product3 + 1) * dimYPlusOne + y;
					double
						dMinXMinZ = this.noiseArray[index1],
						dMinXMaxZ = this.noiseArray[index2],
						dMaxXMinZ = this.noiseArray[index3],
						dMaxXMaxZ = this.noiseArray[index4];
					final double
						nMinXMinZ = (this.noiseArray[index1 + 1] - dMinXMinZ) / divisor,
						nMinXMaxZ = (this.noiseArray[index2 + 1] - dMinXMaxZ) / divisor,
						nMaxXMinZ = (this.noiseArray[index3 + 1] - dMaxXMinZ) / divisor,
						nMaxXMaxZ = (this.noiseArray[index4 + 1] - dMaxXMaxZ) / divisor;
					for(int yIteration = 0; yIteration < factorY; yIteration++)
					{
						double diMinXMinZ = dMinXMinZ, diMinXMaxZ = dMinXMaxZ;
						final double niMinXMinZ = (dMaxXMinZ - dMinXMinZ) / divisorIteration, niMinXMaxZ = (dMaxXMaxZ - dMinXMaxZ) / divisorIteration;
						for(int xIteration = 0; xIteration < dimXZ; xIteration++)
						{
							final double fiMinXMinZ = (diMinXMaxZ - diMinXMinZ) / divisorIteration;
							double tiMinXMinZ = diMinXMinZ;
							for (int zIteration = 0; zIteration < dimXZ; zIteration++)
							{
								if(tiMinXMinZ > 0.0D)
									chunk.setBlock(xIteration + x * dimXZ, yIteration + y * factorY, zIteration + z * dimXZ, Material.STONE);
								tiMinXMinZ += fiMinXMinZ;
							}
							diMinXMinZ += niMinXMinZ;
							diMinXMaxZ += niMinXMaxZ;
						}
						dMinXMinZ += nMinXMinZ;
						dMinXMaxZ += nMinXMaxZ;
						dMaxXMinZ += nMaxXMinZ;
						dMaxXMaxZ += nMaxXMaxZ;
					}
				}
	}
	
	protected void doReplaceBlocks(final ChunkData chunk, final World world, final Random random)
	{
		Util.nonNull(chunk);
		Util.nonNull(world);
		Util.nonNull(random);
		final int maxXZ = 2 << 3, maxY = world.getMaxHeight();
		for(int x = maxXZ; x >= 0; --x)
			for(int z = maxXZ; z >= 0; --z)
			{
				final int topDepth = 1, fillerDept = random.nextInt(2) + 2;
				int top = 0, filler = 0;
				for(int y = maxY; y >= 0; --y)
				{
					final Material block = chunk.getType(x, y, z);
					if(block == Material.AIR)
					{
						top = 0;
						filler >>= 1;
					}
					else if(block == Material.STONE)
					{
						if(top < topDepth)
						{
							top++;
							chunk.setBlock(x, y, z, Material.GRASS);
						}
						else if(filler < fillerDept)
						{
							filler++;
							chunk.setBlock(x, y, z, Material.DIRT);
						}
					}
				}
			}
	}
	
	protected double[] doInitializeNoiseField(final NoiseGenerator noise, final double[] array, final Vector offset, final Vector size)
	{
		Util.nonNull(noise);
		Util.nonNull(offset);
		Util.nonNull(size);
		final int arraySize = size.getBlockX() * size.getBlockY() * size.getBlockZ();
		final double[] result = array == null ? new double[arraySize] : array;
		if(this.noise1 == null) this.noise1 = new double[arraySize];
		if(this.noise2 == null) this.noise2 = new double[arraySize];
		if(this.noise3 == null) this.noise3 = new double[arraySize];
		final int const1 = 0x2AD, const2 = const1 << 1;
		this.noise1 = noise.giveOctaveNoiseGenerate(this.giveNoise(noise, 1), this.noise1, offset, size, new Vector(const2, const1, const2));
		this.noise2 = noise.giveOctaveNoiseGenerate(this.giveNoise(noise, 2), this.noise2, offset, size, new Vector(const2, const1, const2));
		this.noise3 = noise.giveOctaveNoiseGenerate(this.giveNoise(noise, 3), this.noise3, offset, size, new Vector(const2 / 0x50, const1 / 0xA0, const2 / 0x50));
		final double height = size.getBlockY();
		int index = 0;
		for(int x = 0; x < size.getBlockX(); x++)
			for(int z = 0; z < size.getBlockZ(); z++)
				for(int y = 0; y < height; y++)
				{
					final double sample1 = this.noise1[index] / (2 << 8), sample2 = this.noise2[index] / (2 << 8), sample3 = (this.noise3[index] / 0xA + 1) / 2;
					double value = (sample3 < 0 ? sample1 : sample3 > 1 ? sample2 : sample1 + (sample2 - sample1) * sample3) - (2 << 2);
					final List<Double> list = new ArrayList<Double>();
					if(y > height - (2 << 4))
						list.add((y - (height - (2 << 4))) / 0x1F);
					if(y < (2 << 1))
						list.add((double) ((2 << 1) - y) / ((2 << 1) - 1));
					for(final double dy : list)
					{
						value *= 1 - dy;
						value += (2 - (2 << 4)) * dy;
					}
					result[index++] = value;
				}
		return result;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("noise", new double[][]{this.noise1, this.noise2, this.noise3});
		map.put("noiseArray", this.noiseArray);
		return super.giveProperties(map);
	}
	
	public static final WorldGeneratorDreamland giveGenerator()
	{
		if(WorldGeneratorDreamland.generator == null)
			WorldGeneratorDreamland.generator = new WorldGeneratorDreamland();
		return WorldGeneratorDreamland.generator;
	}
	
	public static final WorldCreator giveCreator(final String name, final boolean structures, final String settings, final long seed)
	{
		return WorldCreator
				.name(name)
				.seed(seed)
				.generator(WorldGeneratorDreamland.giveGenerator())
				.environment(Environment.NORMAL)
				.type(WorldType.FLAT)
				.generateStructures(structures)
				.generatorSettings(settings)
			;
	}
	
	public static final WorldCreator giveCreator(final String name, final boolean structures, final String settings)
	{
		return WorldGeneratorDreamland.giveCreator(name, structures, settings, (long) Math.random());
	}
	
	public static final WorldCreator giveCreator(final String name, final String settings, final long seed)
	{
		return WorldGeneratorDreamland.giveCreator(name, false, settings, seed);
	}
	
	public static final WorldCreator giveCreator(final String name, final boolean structures, final long seed)
	{
		return WorldGeneratorDreamland.giveCreator(name, structures, "", seed);
	}
	
	public static final WorldCreator giveCreator(final String name, final boolean structures)
	{
		return WorldGeneratorDreamland.giveCreator(name, structures, "", (long) Math.random());
	}
	
	public static final WorldCreator giveCreator(final String name, final String settings)
	{
		return WorldGeneratorDreamland.giveCreator(name, false, settings, (long) Math.random());
	}
	
	public static final WorldCreator giveCreator(final String name, final long seed)
	{
		return WorldGeneratorDreamland.giveCreator(name, false, "", seed);
	}
	
	public static final WorldCreator giveCreator(final String name)
	{
		return WorldGeneratorDreamland.giveCreator(name, false, "", (long) Math.random());
	}
	
}
