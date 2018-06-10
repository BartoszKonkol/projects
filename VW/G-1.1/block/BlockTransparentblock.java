package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumWorldBlockLayer;

public class BlockTransparentblock extends Block 
{
	
	public static final byte SCAN = 1;
	
	public BlockTransparentblock(final Material par1Material)
	{
		
		super(par1Material);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
	}
	
	@Override
	public int quantityDropped(final Random par1Random)
	{
		
		return 0;
		
	}
	
	@Override
	public EnumWorldBlockLayer getBlockLayer() // getRenderBlockPass
	{
		
		return EnumWorldBlockLayer.CUTOUT;
		
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		
		return false;
		
	}
	
	@Override
	public boolean isFullCube() // renderAsNormalBlock
	{
		
		return false;
		
	}
	
	@Override
	protected boolean canSilkHarvest()
	{
		
		return true;
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.transparentBlock);
		
	}
	
}
