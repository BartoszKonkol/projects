package javax.jao;

public abstract class Main extends AppJAO
{
	
	private static Thread threadStatic;
	private static ClassLoader loaderStatic;
	private static int argcStatic;
	private static String[] argvStatic;
	private static StackTraceElement[] stackTraceStatic;
	
	protected Main()
	{
		this.thread = Main.threadStatic;
		this.loader = Main.loaderStatic;
		this.argc = Main.argcStatic;
		this.argv = Main.argvStatic;
		this.stackTrace = Main.stackTraceStatic;
	}
	
	private Thread thread;
	private ClassLoader loader;
	private int argc;
	private String[] argv;
	private StackTraceElement[] stackTrace;
	
	public final Thread giveThread()
	{
		return this.thread;
	}
	
	public final ClassLoader giveLoader()
	{
		return this.loader;
	}
	
	protected final int giveArgumentsSize()
	{
		return this.argc;
	}
	
	protected final String[] giveArguments()
	{
		return this.argv;
	}
	
	public final StackTraceElement[] giveStackTrace()
	{
		return this.stackTrace;
	}
	
	@Override
	public boolean doRun(final String... args) throws Exception
	{
		return true;
	}
	
	public static final void main(final String[] args)
	{
		try
		{
			final Thread thread = Thread.currentThread();
			final ClassLoader loader = thread.getContextClassLoader();
			final Class<?> clazz = loader.loadClass(args[0]);
			if(Main.class.isAssignableFrom(clazz))
			{
				Main.threadStatic = thread;
				Main.loaderStatic = loader;
				Main.argcStatic = args.length - 1;
				final String[] array = new String[Main.argcStatic];
				for(int i = 1; i < args.length; i++)
					array[i - 1] = args[i];
				Main.argvStatic = array;
				Main.stackTraceStatic = Main.threadStatic.getStackTrace();
				final Main main = (Main) clazz.newInstance();
				if(!main.doRun(main.giveArguments()))
				{
					Print.givePrint().doErr("Error has occurred!");
					System.exit(3);
				}
			}
			else
				throw new ClassCastException();
		}
		catch(final Exception e)
		{
			ThrowException.doThrowExceptionCritical(e);
		}
	}
	
}
