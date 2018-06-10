package net.polishgames.rhenowar.util.serialization;

import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.polishgames.rhenowar.util.Util;

public class Item extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private transient ItemStack item;
	private final byte[] data;
	
	public Item(final ItemStack item)
	{
		this.item = Util.nonNull(item);
		byte[] data = null;
		if(Util.hasUtil())
			try
			{
				final Util util = Util.giveUtil();
				data = util.doWriteTagNBT(util.doAscribeItemToTagNBT(this.giveStack(), util.giveTagClearNBT()));
			}
			catch(final ReflectiveOperationException | NullPointerException e)
			{
				e.printStackTrace();
			}
		this.data = data;
		if(this.giveData() == null)
			Util.doThrowNPE();
	}
	
	protected Item(final ItemStack item, final byte[] data)
	{
		this.item = Util.nonNull(item);
		this.data = Util.nonNull(data);
	}
	
	public final ItemStack giveStack()
	{
		if(this.item == null && Util.hasUtil())
			try
			{
				final Util util = Util.giveUtil();
				this.item = util.giveItemCraft(util.doReadTagNBT(this.giveData()));
			}
			catch(final ReflectiveOperationException | NullPointerException e)
			{
				e.printStackTrace();
			}
		return this.item;
	}
	
	public final ItemMeta giveMeta()
	{
		return this.giveStack().getItemMeta();
	}
	
	public final byte[] giveData()
	{
		return this.data;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("item", this.giveStack());
		map.put("data", this.giveData());
		return map;
	}
	
	@Override
	public Item clone()
	{
		return new Item(this.giveStack().clone(), this.giveData().clone());
	}
	
}
