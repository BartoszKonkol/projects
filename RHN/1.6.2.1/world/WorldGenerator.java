package net.polishgames.rhenowar.util.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.Vector;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;

public abstract class WorldGenerator extends ChunkGenerator implements Rhenowar
{
	
	private final Map<World, NoiseGenerator> noises;
	
	public WorldGenerator()
	{
		this.noises = new HashMap<World, NoiseGenerator>();
	}
	
	private List<BlockPopulator> populators;
	private Object noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6;
	
	public final Map<World, NoiseGenerator> giveNoises()
	{
		return Collections.unmodifiableMap(this.noises);
	}
	
	public final NoiseGenerator giveNoise(final World world, final Random random)
	{
		if(!this.noises.containsKey(Util.nonNull(world)))
			this.noises.put(world, new NoiseGenerator(random));
		return this.noises.get(world);
	}
	
	public final NoiseGenerator giveNoise(final World world)
	{
		return this.giveNoise(world, new Random(Util.nonNull(world).getSeed()));
	}
	
	public final Object giveNoise(final NoiseGenerator noise, final int id)
	{
		Util.nonNull(noise);
		switch(id)
		{
			case 1:
			{
				if(this.noiseGen1 == null)
					this.noiseGen1 = noise.giveOctaveNoiseGenerator(2 << 3);
				return this.noiseGen1;
			}
			case 2:
			{
				if(this.noiseGen2 == null)
					this.noiseGen2 = noise.giveOctaveNoiseGenerator(2 << 3);
				return this.noiseGen2;
			}
			case 3:
			{
				if(this.noiseGen3 == null)
					this.noiseGen3 = noise.giveOctaveNoiseGenerator(2 << 2);
				return this.noiseGen3;
			}
			case 4:
			{
				if(this.noiseGen4 == null)
					this.noiseGen4 = noise.giveOctaveNoiseGenerator(2 << 1);
				return this.noiseGen4;
			}
			case 5:
			{
				if(this.noiseGen5 == null)
					this.noiseGen5 = noise.giveOctaveNoiseGenerator((2 << 2) + 2);
				return this.noiseGen5;
			}
			case 6:
			{
				if(this.noiseGen6 == null)
					this.noiseGen6 = noise.giveOctaveNoiseGenerator(2 << 3);
				return this.noiseGen6;
			}
		}
		return null;
	}
	
	public final Object giveNoise(final World world, final Random random, final int id)
	{
		return this.giveNoise(this.giveNoise(world, random), id);
	}
	
	public final Object giveNoise(final World world, final int id)
	{
		return this.giveNoise(this.giveNoise(world), id);
	}
	
	public final List<BlockPopulator> givePopulators()
	{
		if(this.populators == null)
			this.populators = new ArrayList<BlockPopulator>();
		return this.populators;
	}
	
	@Override
	@Deprecated
	public final byte[] generate(final World world, final Random random, final int x, final int z)
	{
		final byte[] array = new byte[Util.nonNull(world).getMaxHeight() * (2 << 7)];
		if(this.doGenerateTerrain(this.giveLocation(world, x, z), world, Util.nonNull(random), array) != null)
			return array;
		else
			return super.generate(world, random, x, z);
	}
	
	@Override
	@Deprecated
	public final short[][] generateExtBlockSections(final World world, final Random random, final int x, final int z, final BiomeGrid biome)
	{
		final short[][] array = new short[Util.nonNull(world).getMaxHeight() / (2 << 3)][];
		if(this.doGenerateTerrain(this.giveLocation(world, x, z), world, Util.nonNull(random), Util.nonNull(biome), array) != null)
			return array;
		else
			return super.generateExtBlockSections(world, random, x, z, biome);
	}
	
	@Override
	@Deprecated
	public final byte[][] generateBlockSections(final World world, final Random random, final int x, final int z, final BiomeGrid biome)
	{
		return super.generateBlockSections(Util.nonNull(world), Util.nonNull(random), x, z, Util.nonNull(biome));
	}
	
	@Override
	@Deprecated
	public final ChunkData generateChunkData(final World world, final Random random, final int x, final int z, final BiomeGrid biome)
	{
		return this.doGenerateTerrain(this.giveLocation(world, x, z), Util.nonNull(world), Util.nonNull(random), Util.nonNull(biome));
	}
	
	@Override
	@Deprecated
	public final boolean canSpawn(final World world, final int x, final int z)
	{
		return this.hasSpawnLocation(this.giveLocation(Util.nonNull(world), x, z));
	}
	
	@Override
	@Deprecated
	public final Location getFixedSpawnLocation(final World world, final Random random)
	{
		return this.giveSpawnLocation(Util.nonNull(world), Util.nonNull(random));
	}
	
	@Override
	@Deprecated
	public final List<BlockPopulator> getDefaultPopulators(final World world)
	{
		Util.nonNull(world);
		return this.givePopulators();
	}
	
	@SuppressWarnings("deprecation")
	protected final WorldGenerator setBlock(final short[][] array, final int x, final int y, final int z, final Material block)
	{
		if(Util.nonNull(block).isBlock())
		{
			if(Util.nonEmpty(array)[y >> (2 << 1)] == null)
				array[y >> (2 << 1)] = new short[2 << 11];
			array[y >> (2 << 1)][((y & 0xF) << (2 << 2)) | (z << (2 << 1)) | x] = (short) block.getId();
			return this;
		}
		else
			return null;
	}
	
	protected final WorldGenerator setBlock(final short[][] array, final Vector location, final Material block)
	{
		return this.setBlock(Util.nonEmpty(array), Util.nonNull(location).getBlockX() % (2 << 3), location.getBlockY() & 0xFF, location.getBlockZ() % (2 << 3), Util.nonNull(block));
	}
	
	@SuppressWarnings("deprecation")
	protected final Material getBlock(final short[][] array, final int x, final int y, final int z)
	{
		Material block = null;
		if(Util.nonEmpty(array)[y >> (2 << 1)] != null)
			block = Material.getMaterial(array[y >> (2 << 1)][((y & 0xF) << (2 << 2)) | (z << (2 << 1)) | x]);
		if(block == null || !block.isBlock())
			block = Material.AIR;
		return block;
	}

	protected final Material getBlock(final short[][] array, final Vector location)
	{
		return this.getBlock(Util.nonEmpty(array), Util.nonNull(location).getBlockX() % (2 << 3), location.getBlockY() & 0xFF, location.getBlockZ() % (2 << 3));
	}
	
	protected final Block giveHighestBlock(final World world, final int x, final int z)
	{
		return Util.nonNull(world).getHighestBlockAt(x, z);
	}
	
	protected final Block giveHighestBlock(final World world, final Vector location)
	{
		return this.giveHighestBlock(Util.nonNull(world), Util.nonNull(location).getBlockX(), location.getBlockZ());
	}
	
	protected final Block giveHighestBlock(final Location location)
	{
		return this.giveHighestBlock(Util.nonNull(location).getWorld(), location.toVector());
	}
	
	protected final Location giveLocation(final World world, final Vector location)
	{
		return Util.nonNull(location).toLocation(Util.nonNull(world));
	}
	
	protected final Location giveLocation(final World world, final int x, final int y, final int z)
	{
		return this.giveLocation(Util.nonNull(world), new Vector(x, y, z));
	}
	
	protected final Location giveLocation(final World world, final int x, final int z)
	{
		return this.giveLocation(Util.nonNull(world), x, world.getMaxHeight() - 1, z);
	}
	
	protected final ChunkData giveChunkData(final World world)
	{
		return this.createChunkData(Util.nonNull(world));
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("type", this.giveType());
		map.put("populators", this.givePopulators());
		map.put("noiseMap", this.giveNoises());
		map.put("noiseGen", new Object[]{this.noiseGen1, this.noiseGen2, this.noiseGen3, this.noiseGen4, this.noiseGen5, this.noiseGen6});
		return map;
	}
	
	public TypeWorld giveType()
	{
		return TypeWorld.NORMAL;
	}
	
	public Location giveSpawnLocation(final World world, final Random random)
	{
		Util.nonNull(random);
		return new Location(Util.nonNull(world), 0, 2 << 5, 0);
	}
	
	public boolean hasSpawnLocation(final Location location)
	{
		return !this.giveHighestBlock(Util.nonNull(location)).isEmpty();
	}
	
	public WorldGenerator doGenerateTerrain(final Location location, final World world, final Random random, final byte[] array)
	{
		Util.nonNull(location);
		Util.nonNull(world);
		Util.nonNull(random);
		if(Util.nonNull(array).length == 0)
			Util.doThrowIAE();
		return null;
	}
	
	public WorldGenerator doGenerateTerrain(final Location location, final World world, final Random random, final BiomeGrid biome, final short[][] array)
	{
		Util.nonNull(location);
		Util.nonNull(world);
		Util.nonNull(random);
		Util.nonNull(biome);
		Util.nonEmpty(array);
		return null;
	}
	
	public ChunkData doGenerateTerrain(final Location location, final World world, final Random random, final BiomeGrid biome)
	{
		Util.nonNull(location);
		Util.nonNull(world);
		Util.nonNull(random);
		Util.nonNull(biome);
		return null;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return super.toString();
	}
	
	public static enum TypeWorld implements Rhenowar
	{

		FLAT(Environment.NORMAL, WorldType.FLAT),
		NORMAL(Environment.NORMAL, WorldType.NORMAL),
		NETHER(Environment.NETHER, WorldType.NORMAL),
		END(Environment.THE_END, WorldType.NORMAL),
		;
		
		private final Environment environment;
		private final WorldType variant;
		
		private TypeWorld(final Environment environment, final WorldType variant)
		{
			this.environment = environment;
			this.variant = variant;
		}
		
		public final Environment giveEnvironment()
		{
			return this.environment;
		}
		
		public final WorldType giveVariant()
		{
			return this.variant;
		}
		
		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("type", this.name());
			map.put("environment", this.giveEnvironment());
			map.put("variant", this.giveVariant());
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
		
	}
	
}
