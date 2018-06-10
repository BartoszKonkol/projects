package javax.jao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.jao.winapi.winreg.HKey;
import javax.jao.winapi.winreg.KeyOver;
import javax.jao.winapi.winreg.ValueOver;

public final class Util
{
	
	private Util(){}
	
	public static final String giveStringConcat(final String strA, final String strB)
	{
		return Native.giveTextConcat(strA, strB);
	}
	
	public static final KeyOver giveDefaultWinRegKeyJAO()
	{
		return new KeyOver(HKey.LOCAL_MACHINE, "SOFTWARE\\JAO");
	}
	
	public static final List<String> givePropertiesToKeys(final Properties properties)
	{
		final Set<String> set = new HashSet<String>();
		Collections.addAll(set, properties.keySet().toArray(new String[properties.size()]));
		return Collections.unmodifiableList(new ArrayList<String>(set));
	}
	
	public static final <T> T doRequireNonNull(final T object)
	{
		if(object == null)
			throw new NullPointerException();
		return object;
	}
	
	public static final boolean doRegistryValueNull(final boolean bool, final ValueOver value)
	{
		if(bool)
			throw new NullPointerException(value.giveKey().giveKey() + ": " + value.giveValue() + ": In the system registry this value wasn't found.");
		else
			return false;
	}
	
	public static final int giveControlNative()
	{
		return Native.giveControl();
	}
	
	public static final void doDeleteDirectory(final File directory)
	{
		final File[] files = directory.listFiles();
		if(files != null)
			for(final File file : files)
				if(file.isDirectory())
					Util.doDeleteDirectory(file);
				else
					file.delete();
		directory.delete();
	}
	
}
