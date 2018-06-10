package javax.jao;

public class Print
{
	
	protected Print(){}
	
	public boolean doEcho(final String str, final boolean ln)
	{
		return ln ? Native.doPrintlnEcho(str) : Native.doPrintEcho(str);
	}
	
	public final boolean doEcho(final String str)
	{
		return this.doEcho(str, true);
	}
	
	public int doF(final String str, final boolean ln)
	{
		return ln ? Native.doPrintlnF(str) : Native.doPrintF(str);
	}
	
	public final int doF(final String str)
	{
		return this.doF(str, true);
	}
	
	public void doCout(final String str, final boolean ln)
	{
		if(ln)
			Native.doPrintlnCout(str);
		else
			Native.doPrintCout(str);
	}
	
	public final void doCout(final String str)
	{
		this.doCout(str, true);
	}
	
	public void doOut(final String str, final boolean ln)
	{
		if(ln)
			System.out.println(str);
		else
			System.out.print(str);
	}
	
	public final void doOut(final String str)
	{
		this.doOut(str, true);
	}
	
	public void doErr(final String str, final boolean ln)
	{
		if(ln)
			System.err.println(str);
		else
			System.err.print(str);
	}
	
	public final void doErr(final String str)
	{
		this.doErr(str, true);
	}
	
	public static Print givePrint()
	{
		return new Print();
	}
	
}
