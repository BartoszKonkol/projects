package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemWand extends Item
{
	
	public static final byte SCAN = 1;
	
	public ItemWand()
	{
		
		this.setMaxStackSize(64);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		
	}
	
	@Override
	public boolean isFull3D()
    {
		
        return true;
        
    }
	
}
