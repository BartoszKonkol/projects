package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemWhitedust extends Item
{
	
	public static final byte SCAN = 1;
	
	public ItemWhitedust()
	{
		
		this.setMaxStackSize(64);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		
	}
	
}
