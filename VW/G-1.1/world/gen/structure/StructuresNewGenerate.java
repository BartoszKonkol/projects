package vw.world.gen.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public abstract class StructuresNewGenerate
{
	
	public static final byte SCAN = 1;
	
	protected abstract void actionsStructure(final IBlockState[][][] blocks);
	
	protected abstract void actionsDecoration(final World world, final int x, final int z);
	
	public final void generateStructure(final ChunkPrimer primer)
	{
		
		final IBlockState[][][] blocks = new IBlockState[16][256][16];
		
		for(int x = 0; x < 16; x++)
			for(int y = 0; y < 256; y++)
				for(int z = 0; z < 16; z++)
					blocks[x][y][z] = primer.getBlockState(x*4096 + y + z*256);
		
		this.actionsStructure(blocks);
		
		for(int x = 0; x < 16; x++)
			for(int y = 0; y < 256; y++)
				for(int z = 0; z < 16; z++)
					primer.setBlockState(x*4096 + y + z*256, blocks[x][y][z]);
		
	}
	
	public final void generateDecoration(final World world, final BlockPos position)
	{
		
		this.actionsDecoration(world, position.getX(), position.getZ());
		
	}
	
}
