package vw.world.biome;

import java.util.Random;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import vw.VirtualWorld;
import vw.block.BlockRainbowblock;

public class BiomeGenRainbowGlade extends BiomeGenData
{
	
	//public static final byte SCAN = 1;
	
	protected final BlockRainbowblock rainbowBlock;
	
	@SuppressWarnings("unchecked")
	public BiomeGenRainbowGlade(final int par1)
	{
		
		super(par1);
		this.rainbowBlock = (BlockRainbowblock) VirtualWorld.rainbowBlock;
		this.setHeight(new Height(0.0F, 0.0F));
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
		this.theBiomeDecorator.waterlilyPerChunk = 1;
		this.theBiomeDecorator.treesPerChunk = 1;
		this.theBiomeDecorator.deadBushPerChunk = 1;
		this.theBiomeDecorator.reedsPerChunk = 1;
		this.theBiomeDecorator.clayPerChunk = 2;
		this.theBiomeDecorator.bigMushroomsPerChunk = 1;
	}
	
	@Override
	public void genTerrainBlocks(final World par1World, final Random par2Random, final ChunkPrimer par3ChunkPrimer, final int par4, final int par5, final double par6)
	{
		
		this.generateBiomeTerrain /* genBiomeTerrain */ (par1World, par2Random, par3ChunkPrimer, par4, par5, par6);
		
		final int size = 150;
		final float percent = 0.1F;
		
		if(par2Random.nextInt((int)((double) 100 / percent)) == 0)
		{
			
			int x = par2Random.nextInt(16);
			int z = par2Random.nextInt(16);
			
			final boolean[][] filled = new boolean[16][16];
			
			for(int i = size; i <= par2Random.nextInt(1 + (int)((double) 2.5F * size) - size) + 1 + size; i++)
			{
				
				for(int j = 255; j >= 0; --j)
				{
					
					if(par3ChunkPrimer.getBlockState(x, j, z).getBlock() == this.topBlock)
					{
						
						par3ChunkPrimer.setBlockState(x, j, z, this.rainbowBlock.getDefaultState());
						filled[x][z] = true;
						
						do
						{
						
							int l = par2Random.nextInt(3) - 1;
							int m = par2Random.nextInt(3) - 1;
							
							if(l == 0 && m == 0)
								if(par2Random.nextBoolean())
									l = par2Random.nextBoolean() ? (+1) : (-1);
								else
									m = par2Random.nextBoolean() ? (+1) : (-1);
							
							x += l;
							z += m;
							
							if(x > 15) x -= 2;
							if(x < 0) x += 2;
							if(z > 15) z -= 2;
							if(z < 0) z += 2;
							
						}
						while(filled[x][z]);
						
						j = -1;
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
