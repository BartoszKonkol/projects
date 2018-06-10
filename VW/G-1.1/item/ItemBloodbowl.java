package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBloodbowl extends ItemFood
{
	
	public static final byte SCAN = 1;
	
	public ItemBloodbowl(int par1, boolean par2)
	{
		
		super(par1, par2);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabFood);
		
	}
	
	@Override
	public ItemStack onItemUseFinish(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) // onEaten
	{
		super.onItemUseFinish(par1ItemStack, par2World, par3EntityPlayer);
		return new ItemStack(Items.bowl);
	}
	
}
