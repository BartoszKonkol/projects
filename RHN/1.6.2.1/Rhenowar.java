package net.polishgames.rhenowar.util;

import java.util.Map;

public interface Rhenowar extends IRhenowar
{
	
	public Map<String, Object> giveProperties(final Map<String, Object> map);
	
	public default String giveClassName()
	{
		return this.getClass().getName();
	}
	
	public default String giveClassSimpleName()
	{
		return this.getClass().getSimpleName();
	}
	
}
