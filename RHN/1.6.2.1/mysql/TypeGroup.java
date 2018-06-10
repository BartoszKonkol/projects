package net.polishgames.rhenowar.util.mysql;

import java.util.Map;
import net.polishgames.rhenowar.util.Rhenowar;

public enum TypeGroup implements Rhenowar
{
	
	NUMERIC,
	TEMPORAL,
	TEXTUAL,
	UNKNOWN,
	;

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("group", this.name());
		return map;
	}
	
}
