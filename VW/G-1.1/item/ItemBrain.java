package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemBrain extends ItemFood
{
	
	public static final byte SCAN = 1;
	
	public ItemBrain(final int par1, final boolean par2)
	{
		
		super(par1, par2);
		this.setMaxStackSize(4);
		this.setCreativeTab(CreativeTabs.tabFood);
		
	}
	
}
