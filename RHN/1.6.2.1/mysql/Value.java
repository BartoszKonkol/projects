package net.polishgames.rhenowar.util.mysql;

import java.util.Map;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class Value<T> extends Argument
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final T value;

	@SuppressWarnings("deprecation")
	public Value(final String name, final TypeData type, final T value)
	{
		super(name, type);
		this.value = value;
		if(this.hasValue() ^ this.giveType() != TypeData.NULL || this.hasValue() && !this.giveType().giveRepresentative().isAssignableFrom(this.giveValue().getClass()))
			Util.doThrowIAE();
	}

	public Value(final Argument argument, final T value)
	{
		this(argument.giveName(), argument.giveType(), value);
	}
	
	public final T giveValue()
	{
		return this.value;
	}
	
	public final boolean hasValue()
	{
		return this.giveValue() != null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("value", this.giveValue());
		return super.giveProperties(map);
	}
	
	@SuppressWarnings("deprecation")
	public static final <T> Value<T> valueOf(final String name, final T value)
	{
		TypeData type = value == null ? TypeData.NULL : TypeData.UNDEFINED;
		if(type != TypeData.NULL)
			for(final TypeData typeData : TypeData.values())
			{
				final Class<?> representative = typeData.giveRepresentative();
				if(representative != Object.class && representative.isAssignableFrom(value.getClass()))
					type = typeData;
			}
		return new Value<T>(name, type, value);
	}
	
}
