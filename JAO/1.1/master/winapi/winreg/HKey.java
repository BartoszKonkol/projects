package javax.jao.winapi.winreg;

public enum HKey
{
	
	CLASSES_ROOT(0x80000000),
	CURRENT_USER(0x80000001),
	LOCAL_MACHINE(0x80000002),
	USERS(0x80000003),
	PERFORMANCE_DATA(0x80000004),
	PERFORMANCE_TEXT(0x80000050),
	PERFORMANCE_NLSTEXT(0x80000060),
	CURRENT_CONFIG(0x80000005),
	DYN_DATA(0x80000006),
	;
	
	final long number;
	
	private HKey(final long number)
	{
		this.number = number;
	}
	
}
