package net.polishgames.rhenowar.util;

import java.util.Map;

public enum ServerType implements Rhenowar
{
	
	DEFAULT(true),
	MINIGAME(true),
	BUILD(false),
	DEVELOP(false),
	RESERVE(true),
	;
	
	private final boolean normal;
	
	private ServerType(final boolean normal)
	{
		this.normal = normal;
	}
	
	public final boolean isNormal()
	{
		return this.normal;
	}
	
	public final boolean isSpecific()
	{
		return !this.isNormal();
	}
	
	public final String giveType()
	{
		return this.name().toLowerCase();
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("type", this.giveType());
		map.put("normal", this.isNormal());
		map.put("specific", this.isSpecific());
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
