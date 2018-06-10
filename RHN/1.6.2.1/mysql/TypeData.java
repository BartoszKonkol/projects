package net.polishgames.rhenowar.util.mysql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;
import static net.polishgames.rhenowar.util.mysql.TypeGroup.*;

public enum TypeData implements Rhenowar
{
	
				BOOLEAN		(	"BIT(1)"					,	Types.	BIT			,	NUMERIC		,	Boolean		.class	,	false	)	,
	
				BYTE		(	"TINYINT"					,	Types.	TINYINT		,	NUMERIC		,	Byte		.class	,	false	)	,
				SHORT		(	"SMALLINT"					,	Types.	SMALLINT	,	NUMERIC		,	Short		.class	,	false	)	,
				INTEGER		(	"INTEGER"					,	Types.	INTEGER		,	NUMERIC		,	Integer		.class	,	false	)	,
				LONG		(	"BIGINT"					,	Types.	BIGINT		,	NUMERIC		,	Long		.class	,	false	)	,
				FLOAT		(	"FLOAT"						,	Types.	FLOAT		,	NUMERIC		,	Float		.class	,	false	)	,
				DOUBLE		(	"DOUBLE"					,	Types.	DOUBLE		,	NUMERIC		,	Double		.class	,	false	)	,
	
				DATE		(	"DATE"						,	Types.	DATE		,	TEMPORAL	,	Date		.class	,	false	)	,
				TIME		(	"TIME"						,	Types.	TIME		,	TEMPORAL	,	Time		.class	,	false	)	,
				TIMESTAMP	(	"TIMESTAMP"					,	Types.	TIMESTAMP	,	TEMPORAL	,	Timestamp	.class	,	false	)	,
	
				STRING		(	"VARCHAR(%)"				,	Types.	VARCHAR		,	TEXTUAL		,	String		.class	,	true	)	,

	@Deprecated	OBJECT		(	TypeData.	EMPTY_SYNTAX	,	Types.	OTHER		,	UNKNOWN		,	Object		.class	,	false	)	,
	@Deprecated	OBJECTJAVA	(	TypeData.	EMPTY_SYNTAX	,	Types.	JAVA_OBJECT	,	UNKNOWN		,	Object		.class	,	false	)	,
	@Deprecated	NULL		(	TypeData.	EMPTY_SYNTAX	,	Types.	NULL		,	UNKNOWN		,	Object		.class	,	false	)	,
	
	;
	
	public static final TypeData
		BOOL		= 	BOOLEAN		,
		BIT			= 	BOOLEAN		,
		TINYINT		= 	BYTE		,
		SMALLINT	= 	SHORT		,
		INT			= 	INTEGER		,
		MEDIUMINT	= 	INTEGER		,
		BIGINT		= 	LONG		,
		STAMP		= 	TIMESTAMP	,
		CHAR		= 	STRING		,
		CHARACTER	= 	STRING		,
		VARCHAR		= 	STRING		;
	
	@Deprecated	
	public static final TypeData
		OBJ			=	OBJECT		,
		JAVAOBJECT	=	OBJECTJAVA	,
		JOBJECT		=	OBJECTJAVA	,
		JOBJ		=	OBJECTJAVA	,
		OBJECTJ		=	OBJECTJAVA	,
		UNDEFINED	=	OBJECTJAVA	,
		NUL			=	NULL		,
		EMPTY		=	NULL		;
	
	protected static final String EMPTY_SYNTAX = "";
	
	private final String syntax;
	private final int id;
	private final TypeGroup group;
	private final Class<?> representative;
	private final boolean argument;
	
	private <T> TypeData(final String syntax, final int id, final TypeGroup group, final Class<T> representative, final boolean argument)
	{
		this.syntax = syntax;
		this.id = id;
		this.group = group;
		this.representative = representative;
		this.argument = argument;
	}
	
	public final String giveSyntax(final String argument)
	{
		if(this.isKnown() && (this.isArgument() ^ argument == null))
		{
			final String syntax = this.syntax;
			if(this.isArgument())
				return syntax.replace("%", argument);
			else
				return syntax;
		}
		else
		{
			Util.doThrowIAE();
			return null;
		}
	}
	
	public final String giveSyntax(final Object argument)
	{
		return this.giveSyntax(argument == null ? null : argument.toString());
	}
	
	public final String giveSyntax()
	{
		return this.giveSyntax((Object) null);
	}
	
	public final int giveID()
	{
		return this.id;
	}
	
	public final TypeGroup giveGroup()
	{
		return this.group;
	}
	
	public final Class<?> giveRepresentative()
	{
		return this.representative;
	}
	
	public final boolean isArgument()
	{
		return this.argument;
	}
	
	public final boolean isKnown()
	{
		return this.giveGroup() != UNKNOWN;
	}
	
	public final Argument giveArgument(final String name)
	{
		return new Argument(name, this);
	}
	
	public final <T> Value<T> giveValue(final String name, final T value)
	{
		return new Value<T>(this.giveArgument(name), value);
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("type", this.name().toLowerCase());
		map.put("syntax", this.syntax);
		map.put("id", this.giveID());
		map.put("group", this.giveGroup());
		map.put("representative", this.giveRepresentative());
		map.put("argument", this.isArgument());
		map.put("known", this.isKnown());
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return this.name();
	}
	
	public static final TypeData valueOf(final int id)
	{
		for(final TypeData type : TypeData.values())
			if(type.giveID() == id)
				return type;
		return TypeData.UNDEFINED;
	}
	
}
