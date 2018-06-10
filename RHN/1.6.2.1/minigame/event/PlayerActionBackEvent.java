package net.polishgames.rhenowar.util.minigame.event;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.event.PlayerHandlerEvent;
import net.polishgames.rhenowar.util.minigame.Action;

public class PlayerActionBackEvent extends PlayerHandlerEvent
{
	
	private final ItemStack item;
	private final InventoryView inventory;
	private final String actionName;

	public PlayerActionBackEvent(final Player player, final ItemStack item, final InventoryView inventory, final String actionName)
	{
		super(player);
		this.item = Util.nonNull(item);
		this.inventory = inventory;
		this.actionName = Util.nonEmpty(actionName);
	}
	
	private Action<?> action;
	
	public final ItemStack giveItem()
	{
		return this.item;
	}
	
	public final InventoryView giveInventory()
	{
		return this.inventory;
	}
	
	public final boolean hasInventory()
	{
		return this.giveInventory() != null;
	}
	
	public final String giveActionName()
	{
		return this.actionName;
	}
	
	public final void setAction(final Action<?> action)
	{
		this.action = action;
	}
	
	public final Action<?> getAction()
	{
		return this.action;
	}
	
	public final boolean hasAction()
	{
		return this.getAction() != null;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("item", this.giveItem());
		map.put("inventory", this.giveInventory());
		map.put("action", this.getAction());
		map.put("actionName", this.giveActionName());
		return super.giveProperties(map);
	}
	
}
