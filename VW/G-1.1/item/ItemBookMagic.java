package vw.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBookMagic extends Item
{
	
	public static final byte SCAN = 1;
	
	public ItemBookMagic()
	{
		
		super();
		this.setMaxStackSize(1);
		
	}
	
	@Override
	public boolean hasEffect(final ItemStack par1ItemStack)
    {
		
		return true;
		
    }

}
