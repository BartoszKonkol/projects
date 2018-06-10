package javax.jao.winapi.winreg;

import javax.jao.registry.IValue;

public final class ValueOver implements IValue
{
	
	private final KeyOver key;
	private final String value;
	
	public ValueOver(final KeyOver key, final String value)
	{
		this.key = key;
		this.value = value;
	}
	
	public ValueOver(final HKey hkey, final String key, final String value)
	{
		this(new KeyOver(hkey, key), value);
	}
	
	@Override
	public final String giveValue()
	{
		return this.value;
	}
	
	@Override
	public final KeyOver giveKey()
	{
		return this.key;
	}
	
}
