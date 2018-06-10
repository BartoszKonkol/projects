package javax.jao;

import java.io.File;
import javax.jao.winapi.shell.ShellError;
import javax.jao.winapi.shell.ShellShow;

public class Execute
{
	
	protected Execute(){}
	
	public int doExec(final File file, final boolean wait)
	{
		return Native.doExecSystem(file, wait);
	}
	
	public final int doExec(final String path, final boolean wait)
	{
		return this.doExec(new File(path), wait);
	}
	
	public final int doExec(final File file)
	{
		return this.doExec(file, false);
	}
	
	public final int doExec(final String path)
	{
		return this.doExec(new File(path));
	}
	
	public int doCMD(final String command, final String args)
	{
		return Native.doExecSystemCMD(command, args);
	}
	
	public int doCMD(final String command, final boolean remain)
	{
		return Native.doExecSystemCMD(command, remain);
	}
	
	public final int doCMD(final String command)
	{
		return this.doCMD(command, false);
	}
	
	public boolean doPause()
	{
		return Native.doExecSystemPause();
	}
	
	public ShellError doShell(final File file, final File dir, final String parms, final ShellShow show)
	{
		return Native.doExecShell(file, dir, parms, show);
	}
	
	public final ShellError doShell(final String path, final File dir, final String parms, final ShellShow show)
	{
		return this.doShell(new File(path), dir, parms, show);
	}
	
	public final ShellError doShell(final File file, final String dir, final String parms, final ShellShow show)
	{
		return this.doShell(file, new File(dir), parms, show);
	}
	
	public final ShellError doShell(final String path, final String dir, final String parms, final ShellShow show)
	{
		return this.doShell(new File(path), new File(dir), parms, show);
	}
	
	public ShellError doShell(final File file, final File dir, final String parms)
	{
		return Native.doExecShell(file, dir, parms);
	}
	
	public final ShellError doShell(final String path, final File dir, final String parms)
	{
		return this.doShell(new File(path), dir, parms);
	}
	
	public final ShellError doShell(final File file, final String dir, final String parms)
	{
		return this.doShell(file, new File(dir), parms);
	}
	
	public final ShellError doShell(final String path, final String dir, final String parms)
	{
		return this.doShell(new File(path), new File(dir), parms);
	}
	
	public ShellError doShell(final File file, final File dir, final ShellShow show)
	{
		return Native.doExecShell(file, dir, show);
	}
	
	public final ShellError doShell(final String path, final File dir, final ShellShow show)
	{
		return this.doShell(new File(path), dir, show);
	}
	
	public final ShellError doShell(final File file, final String dir, final ShellShow show)
	{
		return this.doShell(file, new File(dir), show);
	}
	
	public final ShellError doShell(final String path, final String dir, final ShellShow show)
	{
		return this.doShell(new File(path), new File(dir), show);
	}
	
	public ShellError doShell(final File file, final File dir)
	{
		return Native.doExecShell(file, dir);
	}
	
	public final ShellError doShell(final String path, final File dir)
	{
		return this.doShell(new File(path), dir);
	}
	
	public final ShellError doShell(final File file, final String dir)
	{
		return this.doShell(file, new File(dir));
	}
	
	public final ShellError doShell(final String path, final String dir)
	{
		return this.doShell(new File(path), new File(dir));
	}
	
	public ShellError doShell(final File file, final ShellShow show)
	{
		return Native.doExecShell(file, show);
	}
	
	public final ShellError doShell(final String path, final ShellShow show)
	{
		return this.doShell(new File(path), show);
	}
	
	public ShellError doShell(final File file)
	{
		return Native.doExecShell(file);
	}
	
	public final ShellError doShell(final String path)
	{
		return this.doShell(new File(path));
	}
	
	public static Execute giveExecute()
	{
		return new Execute();
	}
	
}
