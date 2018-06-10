package net.polishgames.rhenowar.util.minigame;

import java.io.Serializable;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.Item;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class ItemAction extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final Object item;
	private final String action;
	private final boolean script;
	
	@Deprecated
	ItemAction(final Object item, final String action)
	{
		final boolean script = Util.nonEmpty(action).charAt(0) != '!';
		this.item = item;
		this.action = script ? action : action.substring(1);
		this.script = script;
	}
	
	public ItemAction(final ItemStack itemStack, final String action)
	{
		this(new Item(itemStack), action);
	}
	
	public final ItemStack giveItemStack()
	{
		try
		{
			return (ItemStack) this.item.getClass().getMethod("giveStack").invoke(this.item);
		}
		catch(final ReflectiveOperationException | ClassCastException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final String giveAction()
	{
		return this.action;
	}
	
	public final boolean isScript()
	{
		return this.script;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("item", this.item);
		map.put("itemStack", this.giveItemStack());
		map.put("action", this.giveAction());
		map.put("script", this.isScript());
		return map;
	}
	
}
