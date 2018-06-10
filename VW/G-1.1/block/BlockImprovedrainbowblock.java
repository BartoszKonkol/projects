package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockImprovedrainbowblock extends BlockSoftControl 
{
	
	public static final byte SCAN = 1;
	
	public BlockImprovedrainbowblock()
	{
		
		super(Material.cloth);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
		this.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.improvedRainbowBlock);
		
	}
	
	@Override
	public void onEntityCollidedWithBlock(final World par1World, final BlockPos par2BlockPos, final Entity par3Entity)
	{
		
		super.onEntityCollidedWithBlock(par1World, par2BlockPos, par3Entity);
		
		if(par3Entity.motionY / 0.01D < 0.0D)
			par3Entity.motionY /= 0.01D;
		
		par3Entity.motionY += 2.0F;
		
	}
	
}
