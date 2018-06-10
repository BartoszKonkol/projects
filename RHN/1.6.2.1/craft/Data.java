package net.polishgames.rhenowar.util.craft;

import java.util.Map;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.UtilPlugin;

public abstract class Data extends RhenowarObject
{
	
	private final Util util;
	
	public Data(final Util util)
	{
		this.util = Util.nonNull(util);
	}
	
	public final Util giveUtil()
	{
		return this.util;
	}
	
	public final UtilPlugin givePlugin()
	{
		return this.giveUtil().giveUtilPlugin();
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.giveUtil());
		map.put("plugin", this.givePlugin());
		return map;
	}
	
}
