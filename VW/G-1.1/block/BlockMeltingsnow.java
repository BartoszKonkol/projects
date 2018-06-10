package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockMeltingsnow extends BlockSoftControl
{
	
	public static final byte SCAN = 1;
	
	public BlockMeltingsnow()
	{
		
		super(Material.ice);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.meltingSnow);
		
	}
	
	@Override
	public void onEntityCollidedWithBlock(final World par1World, final BlockPos par2BlockPos, final Entity par3Entity)
	{
		
		super.onEntityCollidedWithBlock(par1World, par2BlockPos, par3Entity);
		
		par3Entity.extinguish();
		
		if(par1World.getBlockState /* getBlock */ (par2BlockPos.down()).getBlock() == VirtualWorld.meltingSnow && par1World.getBlockState /* getBlock */ (par2BlockPos.down(2)).getBlock() != Blocks.air)
			this.penetration = true;
		else
			this.penetration = false;
		
	}
	
}
