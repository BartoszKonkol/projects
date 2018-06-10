package javax.jao.winapi.shell;

public enum ShellShow
{
	
	HIDE(0),
	SHOWNORMAL(1),
	SHOWMINIMIZED(2),
	MAXIMIZE(3),
	SHOWMAXIMIZED(3),
	SHOWNOACTIVATE(4),
	SHOW(5),
	MINIMIZE(6),
	SHOWMINNOACTIVE(7),
	SHOWNA(8),
	RESTORE(9),
	SHOWDEFAULT(10),
	;
	
	private final int number;
	
	private ShellShow(final int number)
	{
		this.number = number;
	}
	
	public final int giveNumber()
	{
		return this.number;
	}
	
}
