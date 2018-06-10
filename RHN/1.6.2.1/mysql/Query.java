package net.polishgames.rhenowar.util.mysql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Task;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class Query extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final String command;
	private final StatementType type;
	
	private Query(final Map<String, String[]> format, final StatementType type)
	{
		this.command = Util.nonEmpty(Util.nonNull(type).giveCommand(Util.nonEmpty(format)));
		this.type = type;
	}
	
	public final String giveCommand()
	{
		return this.command;
	}
	
	public final StatementType giveType()
	{
		return this.type;
	}
	
	public static final Query giveQuery(final Table table, final StatementType type, final Task<Map<String, String[]>, Boolean> task)
	{
		final Map<String, String[]> map = new HashMap<String, String[]>();
		if(!Util.nonNull(task).task(map))
			return null;
		map.put("table", new String[]{Util.nonNull(table).giveNameTransform()});
		return new Query(map, type);
	}
	
	public static final Query giveQueryClauses(final Table table, final ClausesData clauses, final StatementType type, final Task<Map<String, String[]>, Boolean> task)
	{
		return Query.giveQuery(table, type, (final Map<String, String[]> map) ->
		{
			final String clausesCommand = Util.nonNull(clauses).giveClausesCommand();
			map.put("clauses", new String[]{clausesCommand != null && clausesCommand.length() > 0 ? clausesCommand : ""});
			return Util.nonNull(task).task(map);
		});
	}
	
	public static final Query giveQueryCreate(final Table table, final List<Argument> columns, final int length)
	{
		return Query.giveQuery(table, StatementType.CREATE, (final Map<String, String[]> map) ->
		{
			final List<String> list = new ArrayList<String>();
			for(final Argument argument : Util.nonEmpty(columns))
			{
				final TypeData type =  Util.nonNull(argument).giveType();
				list.add(Util.nonNull(argument).giveNameTransform() + " " + type.giveSyntax(type.isArgument() ? (length == 0 ? 2 << 2 : Math.abs(length)) : null));
			}
			map.put("columns", list.toArray(new String[list.size()]));
			return true;
		});
	}
	
	public static final Query giveQueryInsert(final Table table, final List<Value<?>> values)
	{
		return Query.giveQuery(table, StatementType.INSERT, (final Map<String, String[]> map) ->
		{
			final List<String> listColumns = new ArrayList<String>(), listValues = new ArrayList<String>();
			for(final Value<?> value : Util.nonNull(values))
			{
				final TypeGroup type = Util.nonNull(value).giveType().giveGroup();
				final boolean text = type == TypeGroup.TEXTUAL || type == TypeGroup.TEMPORAL;
				listColumns.add(Util.nonNull(value).giveNameTransform());
				listValues.add((text ? "'" : "") + String.valueOf(value.giveValue()).replaceAll(text ? "" : "\\W", "").replace("'", "''") + (text ? "'" : ""));
			}
			map.put("columns", listColumns.toArray(new String[listColumns.size()]));
			map.put("values", listValues.toArray(new String[listValues.size()]));
			return true;
		});
	}
	
	@Deprecated
	public static final Query giveQueryInsertManual(final Table table, final List<Object> values)
	{
		return Query.giveQuery(table, StatementType.INSERT_MANUAL, (final Map<String, String[]> map) ->
		{
			final List<String> list = new ArrayList<String>();
			for(final Object value : Util.nonNull(values))
				list.add(String.valueOf(value));
			map.put("values", list.toArray(new String[list.size()]));
			return true;
		});
	}
	
	public static final Query giveQueryDelete(final Table table, final ClausesData clauses)
	{
		return Query.giveQueryClauses(table, clauses, StatementType.DELETE, (final Map<String, String[]> map) ->
		{
			return true;
		});
	}
	
	public static final Query giveQueryUpdate(final Table table, final List<Value<?>> values, final ClausesData clauses)
	{
		return Query.giveQueryClauses(table, clauses, StatementType.UPDATE, (final Map<String, String[]> map) ->
		{
			final List<String> list = new ArrayList<String>();
			for(final Value<?> value :  Util.nonEmpty(values))
			{
				final TypeGroup type = Util.nonNull(value).giveType().giveGroup();
				final boolean text = type == TypeGroup.TEXTUAL || type == TypeGroup.TEMPORAL;
				list.add(value.giveNameTransform() + "=" + (text ? "'" : "") + String.valueOf(value.giveValue()).replaceAll(text ? "" : "\\W", "").replace("'", "''") + (text ? "'" : ""));
			}
			map.put("values", list.toArray(new String[list.size()]));
			return true;
		});
	}
	
	public static final Query giveQuerySelect(final Table table, final List<Argument> columns, final ClausesData clauses)
	{
		return Query.giveQueryClauses(table, clauses, StatementType.SELECT, (final Map<String, String[]> map) ->
		{
			final List<String> list = new ArrayList<String>();
			for(final Argument argument : Util.nonEmpty(columns))
				list.add(Util.nonNull(argument).giveNameTransform());
			map.put("columns", list.toArray(new String[list.size()]));
			return true;
		});
	}
	
	public static final Query giveQuerySelect(final Table table, final ClausesData clauses)
	{
		return Query.giveQueryClauses(table, clauses, StatementType.SELECT, (final Map<String, String[]> map) ->
		{
			map.put("columns", new String[]{"*"});
			return true;
		});
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("command", this.giveCommand());
		return map;
	}
	
}
