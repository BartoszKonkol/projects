package vw.world.biome;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.BlockSnow;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenMeltingValley extends BiomeGenData
{
	
	//public static final byte SCAN = 1;
	
	protected final BlockSnow snowBlock;
	
	@SuppressWarnings("unchecked")
	public BiomeGenMeltingValley(final int par1)
	{
		
		super(par1);
		this.topBlock = VirtualWorld.meltingSnow.getDefaultState();
		this.snowBlock = (BlockSnow) Blocks.snow_layer;
		this.setHeight(new Height(-0.15F, -0.05F));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
		this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
		
	}
	
	@Override
	public void genTerrainBlocks(final World par1World, final Random par2Random, final ChunkPrimer par3ChunkPrimer, final int par4, final int par5, final double par6)
	{
		
		this.generateBiomeTerrain /* genBiomeTerrain */ (par1World, par2Random, par3ChunkPrimer, par4, par5, par6);
		
		int x = par5 & 15, z = par4 & 15;
		
		for(int y = 255; y >= 0; --y)
			if(par3ChunkPrimer.getBlockState(x, y, z).getBlock() == this.topBlock)
				par3ChunkPrimer.setBlockState(x, y + 1, z, this.snowBlock.getStateFromMeta(par2Random.nextInt(2)));
		
	}
	
}
