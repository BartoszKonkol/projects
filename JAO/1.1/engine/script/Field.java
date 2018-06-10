package javax.jao.script;

import javax.jao.script.Type.BaseType;

public final class Field
{
	
	private final String name;
	private final Qualifier qualifier;
	private final BaseType type;
	
	public Field(final String name, final Qualifier qualifier, final BaseType type)
	{
		this.name = name;
		this.qualifier = qualifier;
		this.type = type;
	}
	
	private Value<?> value;
	
	public final Field setValue(final Value<?> value)
	{
		if(value.giveType() != this.giveType())
			return null;
		this.value = value;
		return this;
	}
	
	public final Value<?> getValue()
	{
		return this.value;
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final Qualifier giveQualifier()
	{
		return this.qualifier;
	}
	
	public final BaseType giveType()
	{
		return this.type;
	}
	
}
