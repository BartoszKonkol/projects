package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockBlockkilling extends Block 
{
	
	public static final byte SCAN = 1;
	
	public BlockBlockkilling()
	{
		
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.blockKilling);
		
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState) // getCollisionBoundingBoxFromPool
	{
		
		final float i = 0.00101F;
		final int x = par2BlockPos.getX(), y = par2BlockPos.getY(), z = par2BlockPos.getZ();
		return AxisAlignedBB.fromBounds /* getBoundingBox */ (x + i, y, z + i, x + 1 - i, y + 1 - i, z + 1 - i);
		
	}
	
	@Override
	public void onEntityCollidedWithBlock(final World par1World, final BlockPos par2BlockPos, final Entity par3Entity)
	{
		
		par3Entity.attackEntityFrom(DamageSource.generic, 20.0F);
		
	}
	
	@Override
	public boolean isFullCube() // renderAsNormalBlock
	{
		
		return false;
		
	}
	
}
