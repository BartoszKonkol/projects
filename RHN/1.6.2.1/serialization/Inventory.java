package net.polishgames.rhenowar.util.serialization;

import java.util.Iterator;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import net.polishgames.rhenowar.util.ArrayIterator;
import net.polishgames.rhenowar.util.Util;

public class Inventory extends RhenowarSerializable implements Iterable<Item>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.5.1");
	
	private final String title;
	private final InventoryType type;
	private final Item[] items;
	
	public Inventory(final org.bukkit.inventory.Inventory inventory)
	{
		Util.nonNull(inventory);
		this.title = inventory.getTitle();
		this.type = inventory.getType();
		final int size = inventory.getSize();
		final Item[] items = new Item[size];
		for(int i = 0; i < size; i++)
		{
			final ItemStack item = inventory.getContents()[i];
			if(item != null && item.getType() != Material.AIR)
				items[i] = new Item(item);
		}
		this.items = items;
	}
	
	protected Inventory(final String title, final InventoryType type, final Item[] items)
	{
		this.title = Util.nonNull(title);
		this.type = Util.nonNull(type);
		this.items = Util.nonNull(items);
	}
	
	public final String giveTitle()
	{
		return this.title;
	}
	
	public final InventoryType giveType()
	{
		return this.type;
	}
	
	public final Item[] giveItems()
	{
		return this.items;
	}
	
	public final int giveSize()
	{
		return this.giveItems().length;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("title", this.giveTitle());
		map.put("type", this.giveType());
		map.put("items", this.giveItems());
		map.put("size", this.giveSize());
		return map;
	}
	
	@Override
	public final Iterator<Item> iterator()
	{
		return new ArrayIterator<Item>(this.giveItems());
	}
	
	public final org.bukkit.inventory.Inventory toInventoryBukkit()
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final org.bukkit.inventory.Inventory inventory =
				util.giveInventoryDefaultType() == this.giveType()			?
				util.giveInventory(this.giveTitle(), this.giveSize() / 9)	:
				util.giveInventory(this.giveTitle(), this.giveType())		;
			for(int i = 0; i < this.giveSize(); i++)
			{
				final Item item = this.giveItems()[i];
				if(item != null)
					inventory.setItem(i, item.giveStack());
			}
			return inventory;
					
		}
		else
			return null;
	}
	
	@Override
	public Inventory clone()
	{
		return new Inventory(this.giveTitle(), this.giveType(), this.giveItems().clone());
	}
	
}
