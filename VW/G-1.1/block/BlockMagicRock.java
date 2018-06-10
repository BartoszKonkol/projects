package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockMagicRock extends Block
{
	
	public static final byte SCAN = 1;
	
	public BlockMagicRock()
	{
		
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
	}
	
	@Override
	public int quantityDropped(final Random par1Random)
	{
		
		return par1Random.nextBoolean() ? 1 : 2;
		
	}
	
	@Override
	public int damageDropped(final IBlockState par1IBlockState)
	{
		
		return 1;
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return VirtualWorld.magic;
		
	}
	
}
