package javax.jao.winapi.winreg;

import javax.jao.registry.IKey;

public final class KeyOver implements IKey
{
	
	private final HKey hkey;
	private final String key;
	
	public KeyOver(final HKey hkey, final String key)
	{
		this.hkey = hkey;
		this.key = key;
	}
	
	public final long giveHKey()
	{
		return this.hkey.number;
	}
	
	@Override
	public final String giveKey()
	{
		return this.key;
	}
	
}
