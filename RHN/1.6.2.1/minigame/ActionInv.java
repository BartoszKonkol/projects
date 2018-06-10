package net.polishgames.rhenowar.util.minigame;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class ActionInv extends Action<Inventory>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final Object inventory;
	
	@Deprecated
	ActionInv(final String name, final Object inventory, final Player player)
	{
		super(name, ActionType.INV, player);
		this.inventory = inventory;
	}

	public ActionInv(final String name, final Inventory inventory, final Player player)
	{
		this(name, new net.polishgames.rhenowar.util.serialization.Inventory(inventory), player);
	}
	
	private transient Inventory inventoryBukkit;
	
	public final Inventory giveInventory()
	{
		if(this.inventoryBukkit == null)
			try
			{
				this.inventoryBukkit = (Inventory) this.inventory.getClass().getMethod("toInventoryBukkit").invoke(this.inventory);
			}
			catch(final ReflectiveOperationException | ClassCastException e)
			{
				e.printStackTrace();
			}
		return this.inventoryBukkit;
	}

	@Override
	public Inventory giveResult()
	{
		return this.giveInventory();
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("inventory", this.inventory);
		map.put("inventoryBukkit", this.giveInventory());
		return super.giveProperties(map);
	}
	
}
