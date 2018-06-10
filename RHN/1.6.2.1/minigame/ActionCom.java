package net.polishgames.rhenowar.util.minigame;

import java.util.Map;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class ActionCom extends Action<String>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final String message;

	public ActionCom(final String name, final String message, final Player player)
	{
		super(name, ActionType.COM, player);
		this.message = Util.nonEmpty(message);
	}
	
	public final String giveMessage()
	{
		return this.message;
	}

	@Override
	public String giveResult()
	{
		return this.giveMessage();
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("message", this.giveMessage());
		return super.giveProperties(map);
	}
	
}
