package net.polishgames.rhenowar.util.event;

import java.util.Map;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.Util;

public class PlayerSignSendEvent extends PlayerHandlerEvent implements ISignEvent
{
	
	private final Sign sign;
	private final String[] lines;
	
	public PlayerSignSendEvent(final Player player, final Sign sign, final String... lines)
	{
		super(player);
		this.sign = Util.nonNull(sign);
		this.lines = Util.nonEmpty(lines);
		if(!this.isCorrect())
			Util.doThrowNPE();
	}

	@Override
	public Sign giveSign()
	{
		return this.sign;
	}
	
	public final String[] giveLines()
	{
		return this.lines;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("sign", this.giveSign());
		map.put("lines", this.giveLines());
		map.put("correct", this.isCorrect());
		return super.giveProperties(map);
	}
	
	public final String setLine(final int id, final String text)
	{
		if(id > 0 && text != null)
		{
			final String str = this.getLine(id);
			this.giveLines()[id - 1] = Util.nonNull(text).replace('&', '§');
			return str;
		}
		else
			return null;
	}
	
	public final String getLine(final int id)
	{
		if(id > 0)
			return this.giveLines()[id - 1].replace('§', '&');
		else
			return null;
	}
	
	public final boolean isCorrect()
	{
		return this.getPlayer() != null && this.giveSign() != null && this.giveLines().length == 4;
	}
	
}
