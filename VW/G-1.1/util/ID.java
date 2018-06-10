package vw.util;

import java.util.ArrayList;
import java.util.List;
import vw.item.ItemBlockControl;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ID
{
	
	public static final byte SCAN = 1;
	
	protected static final List<Data> blockList = new ArrayList<Data>();
	protected static final List<Data> itemList = new ArrayList<Data>();
	protected static final List<Data> itemBlockList = new ArrayList<Data>();
	
	protected static void doRegisterBlock(final int index)
	{
		
		final Data data = blockList.get(index);
		
		GameRegistry.registerBlock((Block) data.object, data.named);
		
		if(data.name != null)
			Language.add(((Block) data.object).getUnlocalizedName() + ".name", data.name);
		
	}
	
	protected static void doRegisterItem(final int index)
	{
		
		final Data data = itemList.get(index);
		
		GameRegistry.registerItem((Item) data.object, data.named);
		
		if(data.name != null)
			Language.add(((Item) data.object).getUnlocalizedName() + ".name", data.name);
		
	}
	
	protected static void doRegisterItemBlock(final int index)
	{
		
		final Data data = itemBlockList.get(index);
		
		GameRegistry.registerBlock(((ItemBlockControl) data.object).giveBlock(), ((ItemBlockControl) data.object).getClass(), data.named);
		
		if(data.name != null)
			Language.add(((ItemBlockControl) data.object).getUnlocalizedName() + ".name", data.name);
		
	}
	
	public static final void addBlock(final Block block, final String named)
	{
		
		addBlock(block, named, null);
		
	}
	
	public static void addBlock(final Block block, final String named, final String name)
	{
		
		blockList.add(new Data(block, named, name));
		
	}
	
	public static final void addItem(final Item item, final String named)
	{
		
		addItem(item, named, null);
		
	}
	
	public static void addItem(final Item item, final String named, final String name)
	{
			
		itemList.add(new Data(item, named, name));
			
	}
	
	public static final void addItemBlock(final ItemBlockControl item, final String named)
	{
		
		addItemBlock(item, named, null);
		
	}
	
	public static void addItemBlock(final ItemBlockControl item, final String named, final String name)
	{
		
		itemBlockList.add(new Data(item, named, name));
		
	}
	
	public static void doRegister()
	{
		
		Language.clean();
		
		for(int i = 0; i < blockList.size(); i++)
			doRegisterBlock(i);
		
		for(int i = 0; i < itemList.size(); i++)
			doRegisterItem(i);
		
		for(int i = 0; i < itemBlockList.size(); i++)
			doRegisterItemBlock(i);
		
		Language.register();
		
		doClear();
		
	}
	
	public static void doClear()
	{
		
		blockList.clear();
		itemList.clear();
		
	}
	
	public static Block getBlock(final int id)
	{
		
		return (Block) Block.blockRegistry.getObjectById(id);
		
	}
	
	public static Item getItem(final int id)
	{
		
		return (Item) Item.itemRegistry.getObjectById(id);
		
	}
	
	protected static class Data
	{
		
		public final Object object;
		public final String named, name;
		
		public Data(final Object object,  final String named, final String name)
		{
			
			this.object = object;
			this.named = named;
			this.name = name;
			
		}
		
	}
	
}
