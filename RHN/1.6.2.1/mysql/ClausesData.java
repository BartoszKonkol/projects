package net.polishgames.rhenowar.util.mysql;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import net.polishgames.rhenowar.util.Generic;
import net.polishgames.rhenowar.util.GenericList;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Task;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class ClausesData extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final Map<Clause, Map<String, Object>> clauses;
	
	public ClausesData()
	{
		this.clauses = new HashMap<Clause, Map<String, Object>>();
	}
	
	protected final Map<Clause, Map<String, Object>> giveClauses()
	{
		return this.clauses;
	}
	
	protected final ClausesData set(final Clause clause, final Task<Map<String, Object>, Boolean> task)
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		if(task.task(map))
		{
			this.giveClauses().put(Util.nonNull(clause), map);
			return this;
		}
		else
			return null;
	}
	
	protected final ClausesData setWithColumns(final Clause clause, final GenericList<Argument> columns)
	{
		return this.set(clause, (final Map<String, Object> map) ->
		{
			map.put("columns", Util.nonNull(columns));
			return true;
		});
	}
	
	protected final ClausesData setOrderClause(final Clause clause, final GenericList<Argument> columns)
	{
		final List<Clause> list = new ArrayList<Clause>();
		list.add(Clause.ORDER);
		list.add(Clause.ORDER_ASC);
		list.add(Clause.ORDER_DESC);
		if(list.contains(clause))
			list.remove(clause);
		else
			Util.doThrowIAE();
		for(final Clause c : list)
			if(this.giveClauses().containsKey(c))
				this.giveClauses().remove(c);
		return this.setWithColumns(clause, columns);
	}
	
	public final ClausesData setWhere(final Clause.ValuesData values)
	{
		return this.set(Clause.WHERE, (final Map<String, Object> map) ->
		{
			map.put("values", Util.nonNull(values));
			return true;
		});
	}
	
	public final ClausesData setGroup(final GenericList<Argument> columns)
	{
		return this.setWithColumns(Clause.GROUP, columns);
	}
	
	public final ClausesData setOrder(final GenericList<Argument> columns)
	{
		return this.setOrderClause(Clause.ORDER, columns);
	}
	
	public final ClausesData setOrderAsc(final GenericList<Argument> columns)
	{
		return this.setOrderClause(Clause.ORDER_ASC, columns);
	}
	
	public final ClausesData setOrderDesc(final GenericList<Argument> columns)
	{
		return this.setOrderClause(Clause.ORDER_DESC, columns);
	}
	
	public final ClausesData setLimit(final int count)
	{
		return this.set(Clause.LIMIT, (final Map<String, Object> map) ->
		{
			if(count > 0)
			{
				map.put("count", count);
				return true;
			}
			else
				return false;
		});
	}
	
	public final Map<String, Object> getWhere()
	{
		return this.giveClauses().get(Clause.WHERE);
	}
	
	public final Map<String, Object> getGroup()
	{
		return this.giveClauses().get(Clause.GROUP);
	}
	
	public final Map<String, Object> getOrder()
	{
		return this.giveClauses().get(Clause.ORDER);
	}
	
	public final Map<String, Object> getOrderAsc()
	{
		return this.giveClauses().get(Clause.ORDER_ASC);
	}
	
	public final Map<String, Object> getOrderDesc()
	{
		return this.giveClauses().get(Clause.ORDER_DESC);
	}
	
	public final Map<String, Object> getLimit()
	{
		return this.giveClauses().get(Clause.LIMIT);
	}
	
	public final boolean hasWhere()
	{
		return this.getWhere() != null;
	}
	
	public final boolean hasGroup()
	{
		return this.getGroup() != null;
	}
	
	public final boolean hasOrder()
	{
		return this.getOrder() != null;
	}
	
	public final boolean hasOrderAsc()
	{
		return this.getOrderAsc() != null;
	}
	
	public final boolean hasOrderDesc()
	{
		return this.getOrderDesc() != null;
	}
	
	public final boolean hasLimit()
	{
		return this.getLimit() != null;
	}
	
	public final String giveClausesCommand()
	{
		final List<String> list = new ArrayList<String>();
		for(final Clause clause : new TreeSet<Clause>(this.giveClauses().keySet()))
		{
			final Map<String, Object> map = this.giveClauses().get(clause);
			final List<String> values = clause.giveValues();
			for(final String key : map.keySet())
				if(values.size() > 0 ? values.contains(Util.nonEmpty(key)) : true)
				{
					final Object value = Util.nonNull(map.get(key));
					String valueString = value.toString();
					if(value instanceof Collection<?>)
					{
						if(value instanceof Generic<?> && Argument.class.isAssignableFrom(((Generic<?>) value).giveGenericType()))
						{
							valueString = "";
							for(final Argument argument : (Collection<Argument>) value)
								valueString += Util.nonNull(argument).giveNameTransform() + ",";
							final int length = valueString.length();
							if(length > 0)
								valueString = valueString.substring(0, length - 1);
							else
								continue;
						}
						else
							valueString = valueString.replaceAll("[\\[\\]]", "").replace(", ", ",");
					}
					list.add(clause.giveFormatter().replace("%" + key + "%", valueString));
				}
		}
		String result = "";
		for(final String str : list)
			result += str + " ";
		final int length = result.length();
		return length > 0 ? result.substring(0, length - 1) : null;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("clauses", Collections.unmodifiableMap(this.giveClauses()));
		return map;
	}
	
	public static final ClausesData giveClausesData()
	{
		return new ClausesData();
	}
	
	public static enum Clause implements Name
	{
		
		WHERE(10, "%values%", "values"),
		GROUP(20, "BY %columns%", "columns"),
		//HAVING:30
		ORDER(40, "BY %columns%", "columns"),
		ORDER_ASC(41, ORDER.formatter, ORDER.values.toArray(new String[ORDER.values.size()])),
		ORDER_DESC(42, ORDER.formatter, ORDER.values.toArray(new String[ORDER.values.size()])),
		LIMIT(50, "%count%", "count"),
		//PROCEDURE:60
		//INTO:70
		;
		
		private final int priority;
		private final String formatter;
		private final List<String> values;
		
		private Clause(final int priority, final String formatter, final String... values)
		{
			final String[] array = this.name().split("_");
			this.priority = -priority;
			this.formatter = formatter + (array.length >= 2 ? " " + array[1] : "");
			this.values = Util.hasUtil() ? Util.giveUtil().toList(values) : Collections.emptyList();
			
			try
			{
				final Field ordinal = this.getClass().getSuperclass().getDeclaredField("ordinal");
				ordinal.setAccessible(true);
				ordinal.set(this, Math.abs(this.givePriority()));
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
		}
		
		public final int givePriority()
		{
			return this.priority;
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
	
		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("type", this.giveName());
			map.put("priority", this.givePriority());
			map.put("formatter", this.formatter);
			map.put("values", this.giveValues());
			return map;
		}
		
		public static final class ValuesData extends RhenowarSerializable
		{
			
			private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
			
			private final List<Value<?>> values;
			private final List<OperatorType> operators;
			
			public ValuesData(final Value<?> value)
			{
				this.values = new ArrayList<Value<?>>();
				this.operators = new ArrayList<OperatorType>();
				this.addValue(value);
			}
			
			public final List<Value<?>> giveValues()
			{
				return Collections.unmodifiableList(this.values);
			}
			
			public final List<OperatorType> giveOperators()
			{
				return Collections.unmodifiableList(this.operators);
			}
			
			protected final ValuesData addValue(final Value<?> value)
			{
				this.values.add(Util.nonNull(value));
				return this;
			}
			
			protected final ValuesData addOperator(final OperatorType operator)
			{
				this.operators.add(Util.nonNull(operator));
				return this;
			}
			
			protected final ValuesData add(final Value<?> value, final OperatorType operator)
			{
				return this.addValue(value).addOperator(operator);
			}
			
			public final ValuesData and(final Value<?> value)
			{
				return this.add(value, OperatorType.AND);
			}
			
			public final ValuesData or(final Value<?> value)
			{
				return this.add(value, OperatorType.OR);
			}
	
			@Override
			public Map<String, Object> giveProperties(final Map<String, Object> map)
			{
				map.put("values", this.giveValues());
				map.put("operators", this.giveOperators());
				return map;
			}
			
			@Override
			public final ValuesData clone()
			{
				final ValuesData data = new ValuesData(this.giveValues().get(0));
				boolean first = true;
				for(final Value<?> value : this.giveValues())
					if(first)
						first = false;
					else
						data.addValue(value);
				for(final OperatorType operator : this.giveOperators())
					data.addOperator(operator);
				return data;
			}
			
			@Override
			public String toString()
			{
				final StringBuilder result = new StringBuilder();
				for(int i = 0; i < this.giveValues().size(); i++)
				{
					final Value<?> value = this.giveValues().get(i);
					final boolean textual = Util.nonNull(value).giveType().giveGroup() == TypeGroup.TEXTUAL;
					if(i > 0)
						result.append(' ').append(this.giveOperators().get(i - 1)).append(' ');
					result
						.append(value.giveNameTransform())
						.append('=')
						.append
						(
							(textual ? "'" : "") +
							String.valueOf(value.giveValue()).replaceAll(textual ? "" : "\\W", "").replace("'", "''") +
							(textual ? "'" : "")
						);
				}
				return result.toString();
			}
			
			protected enum OperatorType implements Rhenowar
			{
				
				AND,
				OR,
				;
				
				@Override
				public Map<String, Object> giveProperties(final Map<String, Object> map)
				{
					map.put("type", this.name());
					return map;
				}
				
			}
			
		}
		
	}
	
}
