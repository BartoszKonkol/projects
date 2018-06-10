package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.entity.Player;

public class PlayerFallEvent extends PlayerHandlerCancellableEvent
{
	
	private final double damage, finalDamage;
	
	public PlayerFallEvent(final Player player, final double damage, final double finalDamage)
	{
		super(player);
		this.damage = damage;
		this.finalDamage = finalDamage;
	}
	
	public final double giveDamage()
	{
		return this.damage;
	}
	
	public final double giveFinalDamage()
	{
		return this.finalDamage;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("damage", this.giveDamage());
		map.put("finalDamage", this.giveFinalDamage());
		return super.giveProperties(map);
	}
	
}
