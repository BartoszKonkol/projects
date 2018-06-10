package vw.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public abstract class BlockStairsControl extends BlockStairs 
{
	
	public static final byte SCAN = 1;

	public BlockStairsControl(IBlockState par1IBlockState) 
	{
		
		super(par1IBlockState);
		
	}

}
