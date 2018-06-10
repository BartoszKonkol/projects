package vw.item;

import vw.util.ListIdentifiers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemCrystalCosmos extends Item
{
	
	public static final byte SCAN = 1;
	
	public ItemCrystalCosmos()
	{
		
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		
	}
	
	@Override
	public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final BlockPos par4BlockPos, final EnumFacing par5EnumFacing, final float par6, final float par7, final float par8)
	{
		
		if(par3World.provider.getDimensionId() == ListIdentifiers.idDimensionDreamland)
		{
			
			if (!par2EntityPlayer.capabilities.isCreativeMode)
				--par1ItemStack.stackSize;
			
			par2EntityPlayer.travelToDimension(0);
			
			return true;
			
		}
		
		return false;
		
	}
	
}
