package javax.jao;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Properties;
import javax.jao.winapi.shell.ShellError;
import javax.jao.winapi.shell.ShellShow;
import javax.jao.winapi.winreg.KeyOver;
import javax.jao.winapi.winreg.ValueOver;

final class Native
{
	
	static
	{
		Native.doRegisterNatives();
	}
	
	private static final native boolean jao_library_winreg_new(final long hkey, final String key);
	private static final native boolean jao_library_winreg_set(final long hkey, final String key, final String value, final String content);
	private static final native boolean jao_library_winreg_set_old(final long hkey, final String key, final String content);
	private static final native String jao_library_winreg_get(final long hkey, final String key, final String value);
	private static final native String jao_library_winreg_get_old(final long hkey, final String key, final String value);
	private static final native boolean jao_library_winreg_fit(final long hkey, final String key, final String value);
	private static final native boolean jao_library_winreg_del(final long hkey, final String key, final String value);
	private static final native boolean jao_library_winreg_del(final long hkey, final String key);
	private static final native String jao_library_text_concat(final String textA, final String textB);
	private static final native boolean jao_native_register();
	private static final native boolean jao_native_print_echo(final String text);
	private static final native int jao_native_print_printf(final String text);
	private static final native void jao_native_print_cout(final String text);
	private static final native boolean jao_native_println_echo(final String text);
	private static final native int jao_native_println_printf(final String text);
	private static final native void jao_native_println_cout(final String text);
	private static final native String jao_native_read_cin();
	private static final native String jao_native_read_cin(final String prefix);
	private static final native int jao_native_execute_system(final String path, final boolean wait);
	private static final native int jao_native_execute_system_cmd(final String command, final String args);
	private static final native int jao_native_execute_system_cmd(final String command, final boolean remain);
	private static final native boolean jao_native_execute_system_pause();
	private static final native int jao_native_execute_shell(final String file, final String parms, final String dir, final int show);
	private static final native int jao_native_execute_shell(final String file, final String parms, final String dir);
	private static final native int jao_native_execute_shell(final String file, final String dir, final int show);
	private static final native int jao_native_execute_shell(final String file, final String dir);
	private static final native int jao_native_execute_shell(final String file, final int show);
	private static final native int jao_native_execute_shell(final String file);

	private Native(){}
	
	private static boolean support;
	
	protected static final void doRegisterNatives()
	{
		try
		{
			System.loadLibrary("jao_native_" + System.getProperty("sun.arch.data.model"));
			Native.support = Native.jao_native_register();
		}
		catch(final UnsatisfiedLinkError e0)
		{
			e0.printStackTrace();
			final Properties properties = System.getProperties();
			final String keyClass = "jao.native.onError.class", keyMethod = "jao.native.onError.method";
			if(properties.containsKey(keyClass) && properties.containsKey(keyMethod))
			{
				final String valueClass = properties.getProperty(keyClass), valueMethod = properties.getProperty(keyMethod);
				if(!valueClass.isEmpty() && !valueMethod.isEmpty())
					try
					{
						final Class<?> clazz = Class.forName(valueClass);
						clazz.getMethod(valueMethod).invoke(clazz.getConstructor().newInstance());
					}
					catch (final Exception e1)
					{
						e1.printStackTrace();
					}
					finally
					{
						System.exit(-0x80);
					}
			}
			System.exit(-0xff);
		}
	}
	
	public static final int giveControl()
	{
		int code = 0xffffff01;
		if(!Native.support)
			code = 0xffff;
		else
		{
			OperatingSystemMXBean bean = null;
			boolean controlMemory;
			try
			{
				bean = sun.management.ManagementFactory.getOperatingSystemMXBean();
				controlMemory = true;
			}
			catch(final IllegalAccessError e)
			{
				bean = ManagementFactory.getOperatingSystemMXBean();
				controlMemory = false;
			}
			if(controlMemory && ((com.sun.management.OperatingSystemMXBean) bean).getFreePhysicalMemorySize() / 0x100000 < 0xa0)
				code += 0x010f;
			if(!(bean.getName().toLowerCase().startsWith("windows") && bean.getVersion().replace('.', ',').split(",")[0x0].equalsIgnoreCase("6")))
				code += 0x011f;
			if(code < 0x0)
				code = 0x0000;
		}
		return code;
	}
	
	public static final boolean setWinRegKey(final KeyOver key)
	{
		if(Native.support)
			return Native.jao_library_winreg_new(key.giveHKey(), key.giveKey());
		else
			return false;
	}
	
	public static final boolean setWinRegValue(final ValueOver value, final Object content)
	{
		if(Native.support)
			return Native.jao_library_winreg_set(value.giveKey().giveHKey(), value.giveKey().giveKey(), value.giveValue(), String.valueOf(content));
		else
			return false;
	}
	
	public static final boolean setWinRegValueOld(final KeyOver key, final Object content)
	{
		if(Native.support)
			return Native.jao_library_winreg_set_old(key.giveHKey(), key.giveKey(), String.valueOf(content));
		else
			return false;
	}
	
	public static final String getWinRegValue(final ValueOver value)
	{
		if(Native.support)
			return Native.jao_library_winreg_get(value.giveKey().giveHKey(), value.giveKey().giveKey(), value.giveValue());
		else
			return null;
	}
	
	public static final String getWinRegValueOld(final ValueOver value)
	{
		if(Native.support)
			return Native.jao_library_winreg_get_old(value.giveKey().giveHKey(), value.giveKey().giveKey(), value.giveValue());
		else
			return null;
	}
	
	public static final boolean giveWinRegValueExist(final ValueOver value)
	{
		if(Native.support)
			return Native.jao_library_winreg_fit(value.giveKey().giveHKey(), value.giveKey().giveKey(), value.giveValue());
		else
			return false;
	}
	
	public static final boolean doWinRegDeleteValue(final ValueOver value)
	{
		if(Native.support)
			return Native.jao_library_winreg_del(value.giveKey().giveHKey(), value.giveKey().giveKey(), value.giveValue());
		else
			return false;
	}
	
	public static final boolean doWinRegDeleteKey(final KeyOver key)
	{
		if(Native.support)
			return Native.jao_library_winreg_del(key.giveHKey(), key.giveKey());
		else
			return false;
	}
	
	public static final String giveTextConcat(final String textA, final String textB)
	{
		if(Native.support)
			return Native.jao_library_text_concat(textA, textB);
		else
			return null;
	}
	
	public static final boolean doPrintEcho(final String text)
	{
		if(Native.support)
			return Native.jao_native_print_echo(text);
		else
			return false;
	}
	
	public static final int doPrintF(final String text)
	{
		if(Native.support)
			return Native.jao_native_print_printf(text);
		else
			return Integer.MIN_VALUE;
	}
	
	public static final void doPrintCout(final String text)
	{
		if(Native.support)
			Native.jao_native_print_cout(text);
	}
	
	public static final boolean doPrintlnEcho(final String text)
	{
		if(Native.support)
			return Native.jao_native_println_echo(text);
		else
			return false;
	}
	
	public static final int doPrintlnF(final String text)
	{
		if(Native.support)
			return Native.jao_native_println_printf(text);
		else
			return Integer.MIN_VALUE;
	}
	
	public static final void doPrintlnCout(final String text)
	{
		if(Native.support)
			Native.jao_native_println_cout(text);
	}
	
	public static final String giveReadCin()
	{
		if(Native.support)
			return Native.jao_native_read_cin();
		else
			return null;
	}
	
	public static final String giveReadCin(final String prefix)
	{
		if(Native.support)
			return Native.jao_native_read_cin(prefix);
		else
			return null;
	}
	
	public static final int doExecSystem(final File file, final boolean wait)
	{
		if(Native.support)
			return Native.jao_native_execute_system(file.getAbsolutePath(), wait);
		else
			return Integer.MIN_VALUE;
	}
	
	public static final int doExecSystemCMD(final String command, final String args)
	{
		if(Native.support)
			return Native.jao_native_execute_system_cmd(command, args);
		else
			return Integer.MIN_VALUE;
	}
	
	public static final int doExecSystemCMD(final String command, final boolean remain)
	{
		if(Native.support)
			return Native.jao_native_execute_system_cmd(command, remain);
		else
			return Integer.MIN_VALUE;
	}
	
	public static final boolean doExecSystemPause()
	{
		if(Native.support)
			return Native.jao_native_execute_system_pause();
		else
			return false;
	}
	
	public static final ShellError doExecShell(final File file, final File dir, final String parms, final ShellShow show)
	{
		if(Native.support)
			return ShellError.giveError(Native.jao_native_execute_shell(file.getAbsolutePath(), parms, dir.getAbsolutePath(), show.giveNumber()));
		else
			return null;
	}
	
	public static final ShellError doExecShell(final File file, final File dir, final String parms)
	{
		if(Native.support)
			return ShellError.giveError(Native.jao_native_execute_shell(file.getAbsolutePath(), parms, dir.getAbsolutePath()));
		else
			return null;
	}
	
	public static final ShellError doExecShell(final File file, final File dir, final ShellShow show)
	{
		if(Native.support)
			return ShellError.giveError(Native.jao_native_execute_shell(file.getAbsolutePath(), dir.getAbsolutePath(), show.giveNumber()));
		else
			return null;
	}
	
	public static final ShellError doExecShell(final File file, final File dir)
	{
		if(Native.support)
			return ShellError.giveError(Native.jao_native_execute_shell(file.getAbsolutePath(), dir.getAbsolutePath()));
		else
			return null;
	}
	
	public static final ShellError doExecShell(final File file, final ShellShow show)
	{
		if(Native.support)
			return ShellError.giveError(Native.jao_native_execute_shell(file.getAbsolutePath(), show.giveNumber()));
		else
			return null;
	}
	
	public static final ShellError doExecShell(final File file)
	{
		if(Native.support)
			return ShellError.giveError(Native.jao_native_execute_shell(file.getAbsolutePath()));
		else
			return null;
	}
	
}
