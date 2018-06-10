package vw.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemSubtypesControl extends Item
{
	
	public static final byte SCAN = 1;
	
	public ItemSubtypesControl()
	{
		
		this.setHasSubtypes(true);
		
	}
	
	protected final Map<Integer, String> types = new HashMap<Integer, String>();
	
	private int number;
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubItems(final Item par1Item, final CreativeTabs par2CreativeTabs, final List par3List)
	{
		
		for(int i = 0; i < this.giveQuantityTypes(); i++)
			par3List.add(new ItemStack(this, 1, i));
		
	}
	
	protected final void addType(final String type)
	{
		
		this.types.put(++this.number, type);
		
	}
	
	protected final String giveType(final int id)
	{
		
		return this.types.get(id);
		
	}
	
	public final int giveQuantityTypes()
	{
		
		return this.types.size();
		
	}
	
	protected final boolean doCompareSubtypes(final int par1, final int par2)
	{
		
		return this.giveType(par1).equals(this.giveType(par2));
		
	}
	
	protected final boolean doCompareSubtypes(final ItemStack par1ItemStack, final int par2)
	{
		
		return this.doCompareSubtypes(par1ItemStack.getItemDamage(), par2);
		
	}
	
}
