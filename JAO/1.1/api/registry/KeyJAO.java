package javax.jao.registry;

import java.util.Objects;

public final class KeyJAO implements IKey
{
	
	final String key;
	
	public KeyJAO(final String key)
	{
		this.key = Objects.requireNonNull(key).replace(",", "").replace('/', '\\').replace('\\', '.').replace('.', ',').trim().toLowerCase().split(",")[0];
	}
	
	@Override
	public final String giveKey()
	{
		return this.key;
	}
	
}
