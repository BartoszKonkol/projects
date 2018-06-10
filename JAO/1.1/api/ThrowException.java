package javax.jao;

import javax.jnf.exception.Defect;

public final class ThrowException
{
	
	private ThrowException(){}
	
	public static final void doThrowException(final Exception exception)
	{
		exception.printStackTrace();
	}
	
	public static final void doThrowExceptionCritical(final Exception exception)
	{
		ThrowException.doThrowException(exception);
		try
		{
			throw new Defect(exception.toString());
		}
		catch(final Defect e)
		{
			e.printStackTrace();
			e.exit();
		}
		System.exit(2);
	}
	
}
