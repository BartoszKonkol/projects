package vw.world.gen;

import java.util.Random;
import javax.JNF;
import vw.world.biome.BiomeGenDreamlandBase;
import vw.world.gen.structure.StructureGreatHallGenerate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.terraingen.TerrainGen;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA;

public class ChunkProviderDreamland extends ChunkProviderGenerate
{
	
	public static final byte SCAN = 1;
	
	protected final World world;
	protected final Random seed;
	protected final WorldProvider worldProvider;
	protected final Block fundamentalBlock;
	protected final boolean flyingIsland;
	protected final StructureGreatHallGenerate structureGreatHall;
	
	public ChunkProviderDreamland(final World par1World, final long par2, final WorldProvider par3WorldProvider, final String par4String)
	{
		
		super(par1World, par2, false, par4String);
		
		this.world = par1World;
		this.seed = new Random(par2);
		this.worldProvider = par3WorldProvider;
		this.fundamentalBlock = Blocks.stone;
		this.flyingIsland = true;
		this.structureGreatHall = new StructureGreatHallGenerate(this, this.worldProvider, this.seed);
		
		this.noiseGen1 = new NoiseGeneratorOctaves(this.seed, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.seed, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.seed, 8);
		
	}
	
	protected BiomeGenBase[] biomesForGeneration;
	protected double[] noiseArray;
	protected NoiseGeneratorOctaves noiseGen1;
	protected NoiseGeneratorOctaves noiseGen2;
	protected NoiseGeneratorOctaves noiseGen3;
	protected double[] noise1;
	protected double[] noise2;
	protected double[] noise3;
	protected double[] noise5;
	protected double[] noise6;
	
	@Override
	public Chunk provideChunk(final int par1, final int par2)
	{
		
		this.seed.setSeed(par1 * 341873128712L + par2 * 132897987541L);
		ChunkPrimer primer = new ChunkPrimer();
		
		if(this.flyingIsland)
			this.setBlocksInChunk /* generateTerrain */ (par1, par2, primer);
		else
			super.setBlocksInChunk /* generateTerrain */ (par1, par2, primer);
		
		this.biomesForGeneration = this.world.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
		this.func_180517_a /* replaceBlocksForBiome */ (par1, par2, primer, this.biomesForGeneration);
		
		final Chunk chunk = new Chunk(this.world, primer, par1, par2);
		
		byte[] array = chunk.getBiomeArray();

		for (int i = 0; i < array.length; ++i)
			array[i] = (byte)this.biomesForGeneration[i].biomeID;

		chunk.generateSkylightMap();
		
		return chunk;
		
	}
	
	@Override
	public void setBlocksInChunk(final int par1, final int par2, final ChunkPrimer par3ChunkPrimer) // generateTerrain
	{
		
		final byte byte0 = 4;
		final int k = byte0 + 1;
		final byte byte1 = 33;
		final int l = byte0 + 1;
		this.noiseArray = this.initializeNoiseField(this.noiseArray, par1 * byte0, 0, par2 * byte0, k, byte1, l);
		
		for(int i1 = 0; i1 < byte0; i1++)
			for(int j1 = 0; j1 < byte0; j1++)
				for(int k1 = 0; k1 < 32; k1++)
				{
					
					final double d = 0.125D;
					double d1 = this.noiseArray[((i1 + 0) * l + j1 + 0) * byte1 + k1 + 0];
					double d2 = this.noiseArray[((i1 + 0) * l + j1 + 1) * byte1 + k1 + 0];
					double d3 = this.noiseArray[((i1 + 1) * l + j1 + 0) * byte1 + k1 + 0];
					double d4 = this.noiseArray[((i1 + 1) * l + j1 + 1) * byte1 + k1 + 0];
					final double d5 = (this.noiseArray[((i1 + 0) * l + j1 + 0) * byte1 + k1 + 1] - d1) * d;
					final double d6 = (this.noiseArray[((i1 + 0) * l + j1 + 1) * byte1 + k1 + 1] - d2) * d;
					final double d7 = (this.noiseArray[((i1 + 1) * l + j1 + 0) * byte1 + k1 + 1] - d3) * d;
					final double d8 = (this.noiseArray[((i1 + 1) * l + j1 + 1) * byte1 + k1 + 1] - d4) * d;
					
					for(int l1 = 0; l1 < 8; l1++)
					{
						
						final double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						final double d12 = (d3 - d1) * d9;
						final double d13 = (d4 - d2) * d9;
						
						for(int i2 = 0; i2 < 4; i2++)
						{
							
							int j2 = i2 + i1 * 4 << 12 | 0 + j1 * 4 << 8 | k1 * 8 + l1;
							final int i = 256;
							j2 -= i;
							final double d14 = 0.25D;
							final double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;
							
							for(int k2 = 0; k2 < 4; k2++)
							{
								
								if((d15 += d16) > 0.0D)
									par3ChunkPrimer.setBlockState(j2 += i, this.fundamentalBlock.getDefaultState());
								else
									par3ChunkPrimer.setBlockState(j2 += i, Blocks.air.getDefaultState());
								
							}
							
							d10 += d12;
							d11 += d13;
							
						}
						
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
						
					}
					
				}
		
	}
	
	protected double[] initializeNoiseField(double[] args1, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7)
	{
		
		if(args1 == null)
			args1 = new double[par5 * par6 * par7];
		
		double d = 685D;
		final double d1 = 685D;
		this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, par2, par4, par5, par7, 1D, 1D, 0.5D);
		this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, par2, par4, par5, par7, 200D, 200D, 0.5D);
		d *= 2D;
		this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, par2, par3, par4, par5, par6, par7, d / 80D, d1 / 160D, d / 80D);
		this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, par2, par3, par4, par5, par6, par7, d, d1, d);
		this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, par2, par3, par4, par5, par6, par7, d, d1, d);
		int k1 = 0;
		int l1 = 0;
		for(int j2 = 0; j2 < par5; j2++)
		{
			
			for(int l2 = 0; l2 < par7; l2++)
			{
				
				double d4 = 1.0D;
				d4 *= d4;
				d4 *= d4;
				d4 = 1.0D - d4;
				double d5 = (this.noise5[l1] + 256D) / 512D;
				d5 *= d4;
				
				if(d5 > 1.0D)
					d5 = 1.0D;
				
				double d6 = this.noise6[l1] / 8000D;
				
				if(d6 < 0.0D)
					d6 = -d6 * 0.3D;
				
				d6 = d6 * 3D - 2D;
				
				if(d6 > 1.0D)
					d6 = 1.0D;
				
				d6 /= 8D;
				d6 = 0.0D;
				
				if(d5 < 0.0D)
					d5 = 0.0D;
				
				d5 += 0.5D;
				d6 = (d6 * par6) / 16D;
				l1++;
				double d7 = par6 / 2D;
				
				for(int j3 = 0; j3 < par6; j3++)
				{
					
					double d8 = 0.0D;
					double d9 = ((j3 - d7) * 8D) / d5;
					
					if(d9 < 0.0D)
						d9 *= -1D;
					
					final double d10 = this.noise1[k1] / 512D;
					final double d11 = this.noise2[k1] / 512D;
					final double d12 = (this.noise3[k1] / 10D + 1.0D) / 2D;
					
					if(d12 < 0.0D)
						d8 = d10;
					else if(d12 > 1.0D)
						d8 = d11;
					else
						d8 = d10 + (d11 - d10) * d12;
					
					d8 -= 8D;
					int k3 = 32;
					
					if(j3 > par6 - k3)
					{
						
						final double d13 = (j3 - (par6 - k3)) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d13) + -30D * d13;
						
					}
					
					k3 = 4;
					
					if(j3 < k3)
					{
						
						final double d14 = (k3 - j3) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d14) + -30D * d14;
						
					}
					
					args1[k1] = d8;
					k1++;
					
				}
				
			}
			
		}
		
		return args1;
		
	}
	
	@Override
	public void func_180517_a(final int par1, final int par2, final ChunkPrimer par3ChunkPrimer, final BiomeGenBase[] args4BiomeGenBase) // replaceBlocksForBiome
	{
		
		final int heightChunk = 256;
		final int widthChunk = 16;
		final int layerChunk = (int) JNF.giveMaths().powerAutomatic(widthChunk);
		
		final int areaHeight = 32;
		int hillHeightMinimum = 0;
		int hillHeightMaximum = 3;
		byte topSizeMinimum = 1;
		final byte topSizeMaximum = 1;
		byte fillerSizeMinimum = 2;
		final byte fillerSizeMaximum = 4;
		
		final IBlockState grass = Blocks.grass.getDefaultState();
		final IBlockState dirt = Blocks.dirt.getDefaultState();
		final IBlockState air = Blocks.air.getDefaultState();
		
		final IBlockState areaBlock = this.fundamentalBlock.getDefaultState();
		final IBlockState topBlock = grass;
		final IBlockState fillerBlock = dirt;
		
		final boolean defaultTerrain = true;
		
		if(hillHeightMinimum > hillHeightMaximum)
			hillHeightMinimum = hillHeightMaximum;
		if(topSizeMinimum > topSizeMaximum)
			topSizeMinimum = topSizeMaximum;
		if(fillerSizeMinimum > fillerSizeMaximum)
			fillerSizeMinimum = fillerSizeMaximum;
		
		hillHeightMaximum += 1;
		
		for(int i = 0; i < layerChunk; i++)
		{
			
			final int hillHeight = hillHeightMaximum - hillHeightMinimum > 0 ? this.seed.nextInt(hillHeightMaximum - hillHeightMinimum) + hillHeightMinimum : hillHeightMinimum;
			final int topSize = topSizeMaximum - topSizeMinimum > 0 ? this.seed.nextInt(topSizeMaximum - topSizeMinimum) + topSizeMinimum : topSizeMinimum;
			final int fillerSize = fillerSizeMaximum - fillerSizeMinimum > 0 ? this.seed.nextInt(fillerSizeMaximum - fillerSizeMinimum) + fillerSizeMinimum : fillerSizeMinimum;
			
			final IBlockState[] terrain = new IBlockState[65536];
			
			if(!this.flyingIsland)
			{
				
				if(defaultTerrain)
					for(int j = 0; j < heightChunk; j++)
						if(j > areaHeight)
							terrain[i * heightChunk + j - areaHeight + 1] = par3ChunkPrimer.getBlockState(i * heightChunk + j);
				
				for(int j = 0; j < heightChunk; j++)
					if(j > areaHeight - 1)
						par3ChunkPrimer.setBlockState(i * heightChunk + j, air);
					else
						par3ChunkPrimer.setBlockState(i * heightChunk + j, areaBlock);
				
				if(defaultTerrain)
					for(int j = 0; j < heightChunk; j++)
						par3ChunkPrimer.setBlockState(i * heightChunk + j, terrain[i * heightChunk + j]);
				else
					for(int j = 0; j < hillHeight; j++)
						par3ChunkPrimer.setBlockState(i * heightChunk + areaHeight + j, areaBlock);
				
			}
			
			int top = 0;
			int filler = 0;
			
			for(int j = heightChunk - 1; j >= 0; --j)
				if(par3ChunkPrimer.getBlockState(i * heightChunk + j).getBlock() == null || par3ChunkPrimer.getBlockState(i * heightChunk + j).getBlock() == air.getBlock())
				{
					
					top = 0;
					filler /= 2;
					
				}
				else
				{
					
					if(par3ChunkPrimer.getBlockState(i * heightChunk + j).getBlock() == areaBlock.getBlock() && top < topSize)
					{
					
						par3ChunkPrimer.setBlockState(i * heightChunk + j, topBlock);
						top += 1;
						
					}
					else if(par3ChunkPrimer.getBlockState(i * heightChunk + j).getBlock() == areaBlock.getBlock() && filler < fillerSize)
					{
						
						par3ChunkPrimer.setBlockState(i * heightChunk + j, fillerBlock);
						filler += 1;
						
					}
					
				}
			
		}
		
		if(par1 == 0 && par2 == 0)
			this.structureGreatHall.generateStructure(par3ChunkPrimer);
		
	}
	
	@Override
	public void populate(final IChunkProvider par1IChunkProvider, final int par2, final int par3)
	{
		
		BlockSand.fallInstantly = true;
		
		final int i = par2 * 16;
		final int j = par3 * 16;
		
		final BiomeGenDreamlandBase biome = (BiomeGenDreamlandBase) this.world.getBiomeGenForCoords(new BlockPos(i + 16, 0, j + 16));
		
		this.seed.setSeed(this.world.getSeed());
		this.seed.setSeed(par2 * (this.seed.nextLong() / 2L * 2L + 1L) + par3 * (this.seed.nextLong() / 2L * 2L + 1L) ^ this.world.getSeed());
		
		int x;
		int y;
		int z;
		
		if(par2 == 0 && par3 == 0)
			this.structureGreatHall.generateDecoration(this.world, new BlockPos(par2, 0, par3));
		
		if (this.seed.nextInt(4) == 0 && TerrainGen.populate(par1IChunkProvider, this.world, this.seed, par2, par3, false, LAKE))
		{
			
			x = i + this.seed.nextInt(16) + 8;
			y = this.seed.nextInt(256);
			z = j + this.seed.nextInt(16) + 8;
			
			new WorldGenLakes(Blocks.water).generate(this.world, this.seed, new BlockPos(x, y, z));
			
		}
		
		if (TerrainGen.populate(par1IChunkProvider, world, this.seed, par2, par3, false, LAVA) && this.seed.nextInt(8) == 0)
		{
			
			x = i + this.seed.nextInt(16) + 8;
			y = this.seed.nextInt(this.seed.nextInt(248) + 8);
			z = j + this.seed.nextInt(16) + 8;
			
			if (y < 63 || this.seed.nextInt(10) == 0)
			{
				
				new WorldGenLakes(Blocks.lava).generate(this.world, this.seed, new BlockPos(x, y, z));
				
			}
			
		}
		
		biome.decorate(this.world, this.seed, new BlockPos(i, 0, j));
		SpawnerAnimals.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.seed);
		
		BlockSand.fallInstantly = false;
		
	}
	
}
