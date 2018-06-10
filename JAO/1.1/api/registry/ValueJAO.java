package javax.jao.registry;

import java.util.Objects;

public class ValueJAO implements IValue
{
	
	private final KeyJAO key;
	private final String value;
	
	public ValueJAO(final KeyJAO key, final String value)
	{
		this.key = key;
		this.value = Objects.requireNonNull(value);
	}
	
	public ValueJAO(final String key, final String value)
	{
		this(new KeyJAO(key), value);
	}
	
	public ValueJAO(final String value)
	{
		this((KeyJAO) null, value);
	}
	
	@Override
	public final String giveValue()
	{
		return this.value;
	}
	
	@Override
	public final KeyJAO giveKey()
	{
		return this.key;
	}
	
}
