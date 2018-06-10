package net.polishgames.rhenowar.util;

import java.util.Map;

public enum StateSignTP implements Rhenowar
{
	
	NONFULL,
	FULL,
	ONLYVIP,
	LOCK,
	;
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("state", this.name());
		return map;
	}
	
}
