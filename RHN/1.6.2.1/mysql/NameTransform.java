package net.polishgames.rhenowar.util.mysql;

import java.util.Map;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;

abstract class NameTransform extends RhenowarObject implements Name
{
	
	public final String giveNameTransform()
	{
		return "`" + Util.nonEmpty(this.giveName().replaceAll("\\W", "")) + "`";
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveName());
		return map;
	}
	
}
