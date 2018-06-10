package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import net.polishgames.rhenowar.util.Util;

public class PlayerSignClickEvent extends PlayerInteractEvent implements ISignEvent
{
	
	private final Sign sign;

	public PlayerSignClickEvent(final Player player, final Action action, final ItemStack itemHand, final Sign sign, final BlockFace face)
	{
		super(Util.nonNull(player), Util.nonNull(action), itemHand, Util.nonNull(sign).getBlock(), Util.nonNull(face));
		this.sign = sign;
	}
	
	@Override
	public final Sign giveSign()
	{
		return this.sign;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.getEventName());
		map.put("player", this.getPlayer());
		map.put("cancelled", this.isCancelled());
		map.put("action", this.getAction());
		map.put("item", this.getItem());
		map.put("block", this.getClickedBlock());
		map.put("face", this.getBlockFace());
		map.put("sign", this.giveSign());
		return map;
	}
	
	public final boolean isLeftClick()
	{
		return this.getAction() == Action.LEFT_CLICK_BLOCK;
	}
	
	public final boolean isRightClick()
	{
		return this.getAction() == Action.RIGHT_CLICK_BLOCK;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this, false, true);
		else
			return super.toString();
	}
	
}
