package javax.jao;

import javax.jao.registry.IRegistry;
import javax.jao.winapi.winreg.HKey;
import javax.jao.winapi.winreg.KeyOver;
import javax.jao.winapi.winreg.ValueOver;

public class WinReg implements IRegistry<KeyOver, ValueOver, String>
{
	
	protected WinReg(){}
	
	@Override
	public boolean doCreateKey(final KeyOver key)
	{
		return Native.setWinRegKey(key);
	}
	
	public final boolean doCreateKey(final HKey hkey, final String key)
	{
		return this.doCreateKey(new KeyOver(hkey, key));
	}
	
	@Override
	public boolean setValue(final ValueOver value, final String content)
	{
		return Native.setWinRegValue(value, content);
	}
	
	public final boolean setValue(final KeyOver key, final String value, final String content)
	{
		return this.setValue(new ValueOver(key, value), content);
	}
	
	public final boolean setValue(final HKey hkey, final String key, final String value, final String content)
	{
		return this.setValue(new ValueOver(hkey, key, value), content);
	}
	
	public boolean setValueOld(final KeyOver key, final String content)
	{
		return Native.setWinRegValueOld(key, content);
	}
	
	public final boolean setValueOld(final HKey hkey, final String key, final String content)
	{
		return this.setValueOld(new KeyOver(hkey, key), content);
	}
	
	@Override
	public String getValue(final ValueOver value)
	{
		return Native.getWinRegValue(value);
	}
	
	public final String getValue(final KeyOver key, final String value)
	{
		return this.getValue(new ValueOver(key, value));
	}
	
	public final String getValue(final HKey hkey, final String key, final String value)
	{
		return this.getValue(new ValueOver(hkey, key, value));
	}
	
	public String getValueOld(final ValueOver value)
	{
		return Native.getWinRegValueOld(value);
	}
	
	public final String getValueOld(final KeyOver key, final String value)
	{
		return this.getValueOld(new ValueOver(key, value));
	}

	public final String getValueOld(final HKey hkey, final String key, final String value)
	{
		return this.getValueOld(new ValueOver(hkey, key, value));
	}
	
	@Override
	public boolean doDeleteKey(final KeyOver key)
	{
		return Native.doWinRegDeleteKey(key);
	}
	
	public final boolean doDeleteKey(final HKey hkey, final String key)
	{
		return this.doDeleteKey(new KeyOver(hkey, key));
	}
	
	@Override
	public boolean doDeleteValue(final ValueOver value)
	{
		return Native.doWinRegDeleteValue(value);
	}
	
	public final boolean doDeleteValue(final KeyOver key, final String value)
	{
		return this.doDeleteValue(new ValueOver(key, value));
	}
	
	public final boolean doDeleteValue(final HKey hkey, final String key, final String value)
	{
		return this.doDeleteValue(new ValueOver(hkey, key, value));
	}
	
	@Override
	@Deprecated
	public boolean giveEmptyKey(final KeyOver key)
	{
		return false;
	}
	
	@Deprecated
	public final boolean giveEmptyKey(final HKey hkey, final String key)
	{
		return this.giveEmptyKey(new KeyOver(hkey, key));
	}
	
	@Override
	public boolean giveEmptyValue(final ValueOver value)
	{
		return !Native.giveWinRegValueExist(value);
	}
	
	public final boolean giveEmptyValue(final KeyOver key, final String value)
	{
		return this.giveEmptyValue(new ValueOver(key, value));
	}
	
	public final boolean giveEmptyValue(final HKey hkey, final String key, final String value)
	{
		return this.giveEmptyValue(new ValueOver(hkey, key, value));
	}
	
	public static WinReg giveWinReg()
	{
		return new WinReg();
	}
	
}
