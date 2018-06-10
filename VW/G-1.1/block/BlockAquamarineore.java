package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import vw.util.DataCalculations;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockAquamarineore extends Block
{
	
	public static final byte SCAN = 1;
	
	protected final boolean glowing;

	public BlockAquamarineore(boolean par1)
	{
		
		super(Material.rock);
		this.glowing = par1;
		this.slipperiness = 1.5F; 
		
		this.setTickRandomly(this.glowing);
		
	}

	@Override
	public void onBlockClicked(final World par1World, final BlockPos par2BlockPos, final EntityPlayer par3EntityPlayer)
	{
		
		this.glow(par1World, par2BlockPos);
		super.onBlockClicked(par1World, par2BlockPos, par3EntityPlayer);
		
	}

	@Override
	public void onEntityCollidedWithBlock(final World par1World, final BlockPos par2BlockPos, final Entity par3Entity)
	{
		
		this.glow(par1World, par2BlockPos);
		super.onEntityCollidedWithBlock(par1World, par2BlockPos, par3Entity);
		
	}

	@Override
	public boolean onBlockActivated(final World par1World, final BlockPos par2BlockPos, final IBlockState par3IBlockState, final EntityPlayer par4EntityPlayer, final EnumFacing par5EnumFacing, final float par6, final float par7, final float par8)
	{
		
		this.glow(par1World, par2BlockPos);
		return super.onBlockActivated(par1World, par2BlockPos, par3IBlockState, par4EntityPlayer, par5EnumFacing, par6, par7, par8);
	
	}

	protected void glow(final World par1World, final BlockPos par2BlockPos)
	{
		
		this.sparkle(par1World, par2BlockPos.getX(), par2BlockPos.getY(), par2BlockPos.getZ());

		if (this == VirtualWorld.aquamarineOre)
			par1World.setBlockState /* setBlock */ (par2BlockPos, VirtualWorld.aquamarineOreGlowing.getDefaultState());
		
	}

	@Override
	public void updateTick(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState, final Random par4Random)
	{
		
		if (this == VirtualWorld.aquamarineOreGlowing)
		{
			
			Block block = VirtualWorld.aquamarineOre;
			par1World.setBlockState /* setBlock */ (par2BlockPos, block.getActualState(block.getDefaultState(), par1World, par2BlockPos));
			
		}
		
	}

	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return VirtualWorld.aquamarineItem;
		
	}

	@Override
	public int quantityDropped(final Random par1Random)
	{
		
		return DataCalculations.giveRandomNumber(3, 5, par1Random);
		
	}

	@Override
	public void randomDisplayTick(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState, final Random par4Random)
	{
		
		if (this.glowing)
			this.sparkle(par1World, par2BlockPos.getX(), par2BlockPos.getY(), par2BlockPos.getZ());
		
	}

	protected void sparkle(final World par1World, final int par2, final int par3, final int par4)
	{
		
		final Random random = par1World.rand;
		final double d0 = 0.0625D;

		for (int l = 0; l < 6; ++l)
		{
			
			double d1 = par2 + random.nextFloat();
			double d2 = par3 + random.nextFloat();
			double d3 = par4 + random.nextFloat();

			if (l == 0 && !par1World.getBlockState /* getBlock */ (new BlockPos(par2, par3 + 1, par4)).getBlock().isOpaqueCube())
				d2 = par3 + 1 + d0;

			if (l == 1 && !par1World.getBlockState /* getBlock */ (new BlockPos(par2, par3 - 1, par4)).getBlock().isOpaqueCube())
				d2 = par3 + 0 - d0;

			if (l == 2 && !par1World.getBlockState /* getBlock */ (new BlockPos(par2, par3, par4 + 1)).getBlock().isOpaqueCube())
				d3 = par4 + 1 + d0;

			if (l == 3 && !par1World.getBlockState /* getBlock */ (new BlockPos(par2, par3, par4 - 1)).getBlock().isOpaqueCube())
				d3 = par4 + 0 - d0;

			if (l == 4 && !par1World.getBlockState /* getBlock */ (new BlockPos(par2 + 1, par3, par4)).getBlock().isOpaqueCube())
				d1 = par2 + 1 + d0;

			if (l == 5 && !par1World.getBlockState /* getBlock */ (new BlockPos(par2 - 1, par3, par4)).getBlock().isOpaqueCube())
				d1 = par2 + 0 - d0;

			if (d1 < par2 || d1 > par2 + 1 || d2 < 0.0D || d2 > par3 + 1 || d3 < par4 || d3 > par4 + 1)
				par1World.spawnParticle(EnumParticleTypes.DRIP_WATER, d1, d2, d3, 0.0D, 0.0D, 0.0D);
			
		}
		
	}
	
}
