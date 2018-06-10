package net.polishgames.rhenowar.util;

import java.util.Map;

public enum DimensionPosition implements Rhenowar
{
	
	X,
	Y,
	Z,
	;
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("dimension", this.name());
		return map;
	}
	
}
