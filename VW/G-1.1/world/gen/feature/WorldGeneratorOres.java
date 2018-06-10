package vw.world.gen.feature;

import java.util.Random;
import vw.VirtualWorld;
import vw.util.DataCalculations;
import vw.util.ListIdentifiers;
import vw.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorOres implements IWorldGenerator
{
	
	public static final byte SCAN = 1;

	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider)
	{
		
		final int id = world.provider.getDimensionId();
		
		if(id == 0) /* Overworld */
			this.generateOverworld(world, random, chunkX * 16, chunkZ * 16);
		else if(id == -1) /* Nether */
			this.generateNether(world, random, chunkX * 16, chunkZ * 16);
		else if(id == 1) /* The End */
			this.generateTheEnd(world, random, chunkX * 16, chunkZ * 16);
		else if(id == ListIdentifiers.idDimensionDreamland) /* The Dreamland */
			this.generateTheDreamland(world, random, chunkX * 16, chunkZ * 16);
		
	}
	
	protected void generateOverworld(final World world, final Random random, final int x, final int z)
	{
		
		this.doGenerate(
					x, z,
					0, 15,
					30, 50,
					0, 15,
					3,
					new WorldGenMinable(VirtualWorld.rainbowBlock.getDefaultState(), 10),
					world, random
				);
		
		final int x0 = x + DataCalculations.giveRandomNumber(8, 23, random);
		final int z0 = z + DataCalculations.giveRandomNumber(8, 23, random);
		final BiomeGenBase biome0 = world.getBiomeGenForCoords(new BlockPos(x0, 0, z0));
		this.doGenerate(
					x0, z0,
					0, 0,
					0, 255,
					0, 0,
					(int) (biome0.theBiomeDecorator.flowersPerChunk * 0.75F),
					VirtualWorld.plantBlue.getDefaultState(),
					world, random
				);
		
		this.doGenerate(
					x, z,
					0, 15,
					5, 50,
					0, 15,
					DataCalculations.giveRandomNumber(1, 5, random),
					new WorldGenMinable(VirtualWorld.aquamarineOre.getDefaultState(), 3),
					world, random
				);
		
	}
	
	protected void generateNether(final World world, final Random random, final int x, final int z)
	{
		
	}
	
	protected void generateTheEnd(final World world, final Random random, final int x, final int z)
	{
		
	}
	
	protected void generateTheDreamland(final World world, final Random random, final int x, final int z)
	{
		
	}
	
	protected final boolean doGenerate(int x, int z, final int minX, final int maxX, final int minY, final int maxY, final int minZ, final int maxZ, int occurrence, final WorldGenerator generator, final World world, final Random random)
	{
		
		return this.doGenerate(x, z, minX, maxX, minY, maxY, minZ, maxZ, occurrence, null, generator, world, random);
		
	}
	
	protected final boolean doGenerate(int x, int z, final int minX, final int maxX, final int minY, final int maxY, final int minZ, final int maxZ, int occurrence, final IBlockState flower, final World world, final Random random)
	{
		
		return this.doGenerate(x, z, minX, maxX, minY, maxY, minZ, maxZ, occurrence, flower, null, world, random);
		
	}
	
	protected boolean doGenerate(int x, int z, final int minX, final int maxX, final int minY, final int maxY, final int minZ, final int maxZ, int occurrence, final IBlockState flower, final WorldGenerator generator, final World world, final Random random)
	{
		
		if(occurrence < 1)
			occurrence = 0;
		
		final boolean[] success = new boolean[occurrence];
		
		for(int i = 0; i < occurrence; i++)
		{
			
			x += DataCalculations.giveRandomNumber(minX, maxX, random);
			int y = DataCalculations.giveRandomNumber(minY, maxY, random);
			z += DataCalculations.giveRandomNumber(minZ, maxZ, random);
			
			final BlockPos position = new BlockPos(x, y, z);
			
			if(flower != null)
				Util.doGenerateFlower(flower, position, world, random);
			else
				success[i] = generator.generate(world, random, position);
			
		}
		
		if(success.length == 0)
			return false;
		
		for(int i = 0; i < success.length; i++)
			if(!success[i])
				return false;
		
		return true;
		
	}

}
