package net.polishgames.rhenowar.util.mysql;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.polishgames.rhenowar.util.Util;

public enum StatementType implements Name
{
	
	CREATE("TABLE IF NOT EXISTS %table% (id INT PRIMARY KEY AUTO_INCREMENT,%columns%)", "columns"),
	INSERT("INTO %table% (%columns%) VALUES (%values%)", "columns", "values"),
	DELETE("FROM %table% %clauses%", "clauses"),
	UPDATE("%table% SET %values% %clauses%", "values", "clauses"),
	SELECT("%columns% FROM %table% %clauses%", "columns", "clauses"),
	@Deprecated INSERT_MANUAL("INTO %table% VALUES (%values%)", "values"),
	;
	
	private final String formatter;
	private final List<String> values;
	
	private StatementType(final String formatter, final String... values)
	{
		this.formatter = formatter;
		if(Util.hasUtil())
		{
			this.values = Util.giveUtil().toList(values);
			this.values.add("table");
		}
		else
			this.values = Collections.emptyList();
	}
	
	public final String giveFormatter()
	{
		return this.giveName() + " " + this.formatter;
	}
	
	public final List<String> giveValues()
	{
		return Collections.unmodifiableList(this.values);
	}
	
	@Override
	public final String giveName()
	{
		return this.name().split("_")[0];
	}
	
	public final String giveCommand(final List<String[]> format)
	{
		return this.giveCommand(Util.nonNull(format).toArray(new String[0][format.size()]));
	}
	
	public final String giveCommand(final String[]... format)
	{
		final List<String> values = this.giveValues();
		final int size = values.size();
		if(Util.nonEmpty(format).length == size)
		{
			final Map<String, String[]> map = new HashMap<String, String[]>();
			for(int i = 0; i < size; i++)
				map.put(values.get(i), format[i]);
			return this.giveCommand(map);
		}
		else
		{
			Util.doThrowIAE();
			return null;
		}
	}
	
	public final String giveCommand(final Map<String, String[]> format)
	{
		return this.giveCommand(Util.nonNull(format).entrySet());
	}
	
	public final String giveCommand(final Set<Entry<String, String[]>> format)
	{
		return this.giveCommand(Util.nonNull(format).toArray(new Entry[format.size()]));
	}
	
	public final String giveCommand(final Entry<String, String[]>... format)
	{
		String result = this.giveFormatter();
		for(final Entry<String, String[]> entry : Util.nonNull(format))
		{
			String values = "";
			for(final String value : Util.nonNull(entry).getValue())
				if(Util.nonNull(value).length() > 0)
					values += value + ",";
			final int length = values.length();
			result = result.replace("%" + Util.nonEmpty(entry.getKey()) + "%", length > 1 ? values.subSequence(0, length - 1) : "");
		}
		return result.trim() + ";";
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("type", this.giveName());
		map.put("formatter", this.formatter);
		map.put("values", this.giveValues());
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return this.giveName();
	}
	
}
