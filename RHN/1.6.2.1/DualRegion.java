package net.polishgames.rhenowar.util;

import java.util.Map;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class DualRegion extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.5.2.1");
	
	private final Region first, second;
	
	public DualRegion(final Region first, final Region second)
	{
		this.first = Util.nonNull(first);
		this.second = Util.nonNull(second);
	}
	
	public final Region giveFirst()
	{
		return this.first;
	}
	
	public final Region giveSecond()
	{
		return this.second;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("first", this.giveFirst());
		map.put("second", this.giveSecond());
		return map;
	}

	@Override
	public DualRegion clone()
	{
		return new DualRegion(this.giveFirst().clone(), this.giveSecond().clone());
	}
	
}
