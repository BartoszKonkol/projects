package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;

public class PlayerLeaveEvent extends PlayerHandlerCancellableEvent
{
	
	private final TypeLeave type;

	public PlayerLeaveEvent(final Player player, final TypeLeave type)
	{
		super(player);
		this.type = type;
	}

	public PlayerLeaveEvent(final Player player, final TypeLeave type, final String message)
	{
		this(player, type);
		this.setMessage(message);
	}

	public PlayerLeaveEvent(final Player player, final TypeLeave type, final String message, final String reason)
	{
		this(player, type, message);
		this.setReason(reason);
	}
	
	private String message, reason;
	
	public final PlayerLeaveEvent setMessage(final String message)
	{
		this.message = message;
		return this;
	}
	
	public PlayerLeaveEvent setReason(final String reason)
	{
		this.reason = reason;
		return this;
	}
	
	public final String getMessage()
	{
		return this.message;
	}
	
	public final String getReason()
	{
		return this.reason;
	}
	
	public final TypeLeave giveType()
	{
		return this.type;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("message", this.getMessage());
		map.put("reason", this.getReason());
		map.put("type", this.giveType());
		return super.giveProperties(map);
	}
	
	public static enum TypeLeave implements Rhenowar
	{
		
		QUIT,
		KICK,
		;
		
		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("type", this.name());
			return map;
		}
		
		@Override
		public String toString()
		{
			if(Util.hasUtil())
				return Util.giveUtil().toString(this);
			else
				return super.toString();
		}
		
	}
	
}
