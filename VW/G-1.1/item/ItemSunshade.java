package vw.item;

import vw.VirtualWorld;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSunshade extends Item
{
	
	public static final byte SCAN = 1;
	
	public ItemSunshade()
	{
	
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		
	}
	
	@Override
	public boolean isFull3D()
	{
		
		return true;
		
	}
	
	@Override
	public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final BlockPos par4BlockPos, final EnumFacing par5EnumFacing, final float par6, final float par7, final float par8)
	{
		
		final Block groundBlock = par3World.getBlockState /* getBlock */ (par4BlockPos).getBlock();
		
		if(groundBlock == Blocks.sand || groundBlock == Blocks.grass || groundBlock == Blocks.planks)
		{
			
			if (!par2EntityPlayer.capabilities.isCreativeMode)
				--par1ItemStack.stackSize;
		
			par3World.setBlockState /* setBlock */ (par4BlockPos.up(), VirtualWorld.sunshadeBlock.getStateFromMeta(0));
			par3World.setBlockState /* setBlock */ (par4BlockPos.up(2), VirtualWorld.sunshadeBlock.getStateFromMeta(0));
			par3World.setBlockState /* setBlock */ (par4BlockPos.up(3), VirtualWorld.sunshadeBlock.getStateFromMeta(1));
			par3World.setBlockState /* setBlock */ (par4BlockPos.up(3).north(), VirtualWorld.sunshadeBlock.getStateFromMeta(2));
			par3World.setBlockState /* setBlock */ (par4BlockPos.up(3).east(), VirtualWorld.sunshadeBlock.getStateFromMeta(3));
			par3World.setBlockState /* setBlock */ (par4BlockPos.up(3).south(), VirtualWorld.sunshadeBlock.getStateFromMeta(4));
			par3World.setBlockState /* setBlock */ (par4BlockPos.up(3).west(), VirtualWorld.sunshadeBlock.getStateFromMeta(5));
			
			return true;
		
		}
		
		return false;
		
	}
	
}
