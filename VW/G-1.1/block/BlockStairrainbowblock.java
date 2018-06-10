package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockStairrainbowblock extends BlockStairsControl 
{
	
	public static final byte SCAN = 1;
	
	public BlockStairrainbowblock(IBlockState par1IBlockState)
	{
		
		super(par1IBlockState);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.stairRainbowBlock);
		
	}

}
