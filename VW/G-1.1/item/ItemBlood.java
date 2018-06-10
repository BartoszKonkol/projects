package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemBlood extends ItemFood
{
	
	public static final byte SCAN = 1;
	
	public ItemBlood(int par1, boolean par2)
	{
		
		super(par1, par2);
		this.setMaxStackSize(32);
		this.setCreativeTab(CreativeTabs.tabFood);
		
	}
	
}
