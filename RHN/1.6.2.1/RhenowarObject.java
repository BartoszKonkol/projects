package net.polishgames.rhenowar.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class RhenowarObject implements Rhenowar, Comparable<Rhenowar>
{

	public final Map<String, Object> giveProperties()
	{
		return this.giveProperties(new LinkedHashMap<String, Object>());
	}
	
	@Override
	public int compareTo(final Rhenowar rhenowar)
	{
		final int compare = Util.nonEmpty(this.giveClassName()).compareTo(Util.nonEmpty(Util.nonNull(rhenowar).giveClassName()));
		if(compare == 0 && !(rhenowar instanceof RhenowarObject))
			return -1;
		else
			return compare;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1;
		for(final Entry<String, Object> entry : this.giveProperties().entrySet())
		{
			final int value = System.identityHashCode(entry.getValue());
			if(value != 0)
				hash *= value;
			hash |= entry.getKey().hashCode();
		}
		return hash += this.getClass().hashCode() >>> 1;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if(super.equals(Util.nonNull(obj)))
			return true;
		else
		{
			if(obj instanceof Rhenowar)
				return Util.nonNull(this.giveProperties()).equals(Util.nonNull(((Rhenowar) obj).giveProperties(new LinkedHashMap<String, Object>())));
			else
				return false;
		}
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return super.toString();
	}
	
}
