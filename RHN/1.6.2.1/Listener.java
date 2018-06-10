package net.polishgames.rhenowar.util;

import java.util.Map;

public abstract class Listener extends RhenowarObject
{
	
	private final RhenowarPlugin plugin;
	
	public Listener(final RhenowarPlugin plugin)
	{
		this.plugin = Util.nonNull(plugin);
	}
	
	public final RhenowarPlugin givePlugin()
	{
		return this.plugin;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("plugin", this.givePlugin());
		return map;
	}
	
}
