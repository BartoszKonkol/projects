package vw.item;

import vw.util.ListIdentifiers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemWandTeleportation extends Item
{
	
	public static final byte SCAN = 1;

	public ItemWandTeleportation()
	{
		
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		
	}
	
	@Override
	public boolean hasEffect(final ItemStack par1ItemStack)
	{
		
		return true;
		
	}
	
	@Override
	public boolean isFull3D()
	{
		
		return true;
		
	}
	
	@Override
	public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final BlockPos par4BlockPos, final EnumFacing par5EnumFacing, final float par6, final float par7, final float par8)
	{
		
		final EntityPlayer player = par2EntityPlayer;
		final World world = par3World;
		final int x = par4BlockPos.getX();
		final int y = par4BlockPos.getY();
		final int z = par4BlockPos.getZ();
		final byte dreamland = ListIdentifiers.idDimensionDreamland;
		
		if(world.provider.getDimensionId() != dreamland && world.getBlockState /* getBlock */ (par4BlockPos).getBlock() == Blocks.bed)
		{
			
			if (!player.capabilities.isCreativeMode)
				--par1ItemStack.stackSize;
			
			player.travelToDimension(dreamland);
			
			world.newExplosion(player, x + 0.5D, y, z + 0.5D, 1.5F, true, true);
			
			return true;
			
		}
		else
			return false;
		
	}
	
}
