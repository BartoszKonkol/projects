package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDarkmatter extends Item
{
	
	public static final byte SCAN = 1;
	
	public ItemDarkmatter()
	{
		
		this.setMaxStackSize(64);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		
	}
	
}
