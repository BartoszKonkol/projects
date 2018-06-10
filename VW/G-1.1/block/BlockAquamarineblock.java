package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockAquamarineblock extends Block
{
	
	public static final byte SCAN = 1;
	
	public BlockAquamarineblock()
	{
		
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.slipperiness = 1.5F;
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.aquamarineBlock);
		
	}

}
