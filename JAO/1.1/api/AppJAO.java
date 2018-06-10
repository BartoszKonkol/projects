package javax.jao;

public abstract class AppJAO implements App, Runnable
{
	
	@Override
	public final void run()
	{
		try
		{
			this.doRun();
		}
		catch(final Exception e)
		{
			ThrowException.doThrowException(e);
		}
	}
	
}
