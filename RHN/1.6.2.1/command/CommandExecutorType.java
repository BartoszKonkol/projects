package net.polishgames.rhenowar.util.command;

import java.util.Map;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;

public enum CommandExecutorType implements Rhenowar
{
	
	PLAYER,
	CONSOLE,
	CONSOLE_REMOTE,
	BLOCK,
	
	CONSOLE_OR_BLOCK,
	CONSOLE_OR_CONSOLE_REMOTE,
	CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK,
	CONSOLE_REMOTE_OR_BLOCK,
	PLAYER_OR_BLOCK,
	PLAYER_OR_CONSOLE,
	PLAYER_OR_CONSOLE_OR_BLOCK,
	PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE,
	PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK,
	PLAYER_OR_CONSOLE_REMOTE,
	PLAYER_OR_CONSOLE_REMOTE_OR_BLOCK,
	
	OTHER,
	ALL,
	
	;
	
	public final String giveType()
	{
		return this.name().toLowerCase().replace("_", " ");
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("type", this.giveType());
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return this.giveType();
	}
	
}
