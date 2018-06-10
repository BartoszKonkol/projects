package net.polishgames.vis2.server.jweb.jws.parameter;

import java.util.HashMap;
import java.util.Map;

public class StringJWS extends ParameterJWS
{
	
	private final String string;
	
	public StringJWS(String string, final boolean replace)
	{
		if(replace)
		{
			final Map<String, String> map = new HashMap<String, String>();
			map.put("w", " ");
			map.put("n", "\n");
			map.put("r", "\r");
			map.put("t", "\t");
			map.put("p", ",");
			map.put("s", ";");
			map.put("c", ":");
			map.put("a", "&");
			map.put("d", "$");
			map.put("l", "\r\n");
			map.put("bo", "(");
			map.put("bc", ")");
			for(final String s : map.keySet())
				string = string.replace("&" + s, map.get(s));
		}
		this.string = string;
	}
	
	public StringJWS(final String string)
	{
		this(string, true);
	}

	@Override
	public String toString()
	{
		return this.string;
	}

	@Override
	public StringJWS toStringJWS()
	{
		return this;
	}

	@Override
	public int toNumber()
	{
		try
		{
			return Integer.valueOf(this.toString());
		}
		catch(final NumberFormatException e)
		{
			return 0;
		}
	}

	@Override
	public NumberJWS toNumberJWS()
	{
		return new NumberJWS(this.toNumber());
	}

	@Override
	public boolean isNull()
	{
		return this.toString() == null;
	}
	
	@Override
	public StringJWS clone()
	{
		return new StringJWS(this.toString());
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return super.equals(object) && this.toString().equals(((StringJWS) object).toString());
	}
	
}
