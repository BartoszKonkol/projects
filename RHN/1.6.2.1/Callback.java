package net.polishgames.rhenowar.util;

import java.util.Map;

public abstract class Callback extends RhenowarObject implements ICallback
{
	
	private Object result;
	private boolean change;

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("result", this.getResult());
		map.put("change", this.isChange());
		return map;
	}
	
	public final Callback setResult(final Object result)
	{
		if(!this.isChange())
		{
			this.result = Util.nonNull(result);
			this.change = true;
			return this;
		}
		else
			return null;
	}
	
	public final Object getResult()
	{
		if(this.isChange())
			return Util.nonNull(this.result);
		else
			return null;
	}
	
	public final boolean isChange()
	{
		return this.change;
	}
	
	@Override
	public final boolean onResultReceived(final Object result)
	{
		return this.setResult(result).onResultReceived();
	}
	
	public abstract boolean onResultReceived();
	
}
