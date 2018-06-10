package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockAquamarineDamagedBlock extends BlockSubControl
{
	
	public static final byte SCAN = 1;

	public BlockAquamarineDamagedBlock()
	{
		
		super(Material.iron, (byte) names.length);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
		this.slipperiness = 1.5F;
		
	}
	
	public static final String[] names = new String[] {"strongly", "slightly"};
	
	@Override
	public int damageDropped(final IBlockState par1IBlockState)
	{
		
		return this.getMetaFromState(par1IBlockState);
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.aquamarineDamagedBlock);
		
	}
	
}
