package javax.jao;

import java.util.Scanner;

public class Read
{
	
	protected Read(){}
	
	public String doCin()
	{
		return Native.giveReadCin();
	}
	
	public String doCin(final String prefix)
	{
		return Native.giveReadCin(prefix);
	}
	
	public String doIn()
	{
		return new Scanner(System.in).nextLine();
	}
	
	public String doIn(final String prefix)
	{
		Print.givePrint().doOut(prefix, false);
		return this.doIn();
	}
	
	public static Read giveRead()
	{
		return new Read();
	}
	
}
