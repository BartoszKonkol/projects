package javax.jao.winapi.shell;

public enum ShellError
{
	
	UNKNOWN(-1),
	SUCCESS(0),
	FILE_NOT_FOUND(2),
	FNF(2),
	PATH_NOT_FOUND(3),
	PNF(3),
	ACCESSDENIED(5),
	OOM(8),
	BAD_FORMAT(11),
	SHARE(26),
	ASSOCINCOMPLETE(27),
	DDETIMEOUT(28),
	DDEFAIL(29),
	DDEBUSY(30),
	NOASSOC(31),
	DLLNOTFOUND(32),
	;
	
	private static int lastUnknownNumber = Integer.MIN_VALUE;
	
	private final int number;
	
	private ShellError(final int number)
	{
		this.number = number;
	}
	
	public static final ShellError giveError(final int number)
	{
		if(number == ShellError.SUCCESS.number)
			return ShellError.SUCCESS;
		if(number == ShellError.FILE_NOT_FOUND.number)
			return ShellError.FILE_NOT_FOUND;
		if(number == ShellError.PATH_NOT_FOUND.number)
			return ShellError.PATH_NOT_FOUND;
		if(number == ShellError.ACCESSDENIED.number)
			return ShellError.ACCESSDENIED;
		if(number == ShellError.OOM.number)
			return ShellError.OOM;
		if(number == ShellError.BAD_FORMAT.number)
			return ShellError.BAD_FORMAT;
		if(number == ShellError.SHARE.number)
			return ShellError.SHARE;
		if(number == ShellError.ASSOCINCOMPLETE.number)
			return ShellError.ASSOCINCOMPLETE;
		if(number == ShellError.DDETIMEOUT.number)
			return ShellError.DDETIMEOUT;
		if(number == ShellError.DDEFAIL.number)
			return ShellError.DDEFAIL;
		if(number == ShellError.DDEBUSY.number)
			return ShellError.DDEBUSY;
		if(number == ShellError.NOASSOC.number)
			return ShellError.NOASSOC;
		if(number == ShellError.DLLNOTFOUND.number)
			return ShellError.DLLNOTFOUND;

		ShellError.lastUnknownNumber = number;
		
		return ShellError.UNKNOWN;
	}
	
	public static final int giveLastUnknownNumberError()
	{
		return ShellError.lastUnknownNumber;
	}
	
}
