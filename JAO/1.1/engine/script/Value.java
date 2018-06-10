package javax.jao.script;

import javax.jao.script.Type.BaseType;

public final class Value<V>
{
	
	private final BaseType type;
	private final V value;
	
	public Value(final BaseType type, final V value)
	{
		this.type = type;
		final Class<?> typeClass = this.type.giveClassType();
		final Class<?> valueClass = value.getClass();
		if(valueClass.isAssignableFrom(Object.class))
			this.value = null;
		else if(!typeClass.isAssignableFrom(valueClass))
			throw new ClassCastException(valueClass.getName() + " cannot be assignable to " + typeClass.getName());
		else
			this.value = value;
	}
	
	public final BaseType giveType()
	{
		return this.type;
	}
	
	public final V giveValue()
	{
		return this.value;
	}
	
}
