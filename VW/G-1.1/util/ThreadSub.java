package vw.util;

public abstract class ThreadSub extends Thread
{
	
	public static final byte SCAN = 1;
	
	public ThreadSub()
	{
		
		this.setName(this.getClass().getSimpleName());
		this.setPriority(this.getPriority() - (this.getPriority() - 1 < MIN_PRIORITY ? 0 : 1));
		
	}
	
	@Override
	protected final Object clone() throws CloneNotSupportedException
	{
		
		return super.clone();
		
	}
	
	@Override
	@Deprecated
    public native final int countStackFrames();
	
	@Override
	@Deprecated
	public final void destroy()
	{
		
        super.destroy();
        
    }
	
	@Override
    public final ClassLoader getContextClassLoader()
	{
		
		return super.getContextClassLoader();
		
	}
	
	@Override
	public final long getId()
	{
		
        return super.getId();
        
    }
	
	@Override
	public final StackTraceElement[] getStackTrace()
	{
		
		return super.getStackTrace();
		
	}
	
	@Override
	public final State getState()
	{
		
		return super.getState();
		
	}
	
	@Override
	public final UncaughtExceptionHandler getUncaughtExceptionHandler()
	{
		
		return super.getUncaughtExceptionHandler();
		
	}
	
	@Override
	public final void interrupt()
	{
		
		super.interrupt();
		
	}
	
	@Override
	public final boolean isInterrupted()
	{
		
		return super.isInterrupted();
		
	}
	
	@Override
	public final void run()
	{
		
		this.action();
		
	}
	
	@Override
	public final void setContextClassLoader(ClassLoader cl)
	{
		
		super.setContextClassLoader(cl);
		
	}
	
	@Override
	public final void setUncaughtExceptionHandler(UncaughtExceptionHandler eh)
	{
		
		super.setUncaughtExceptionHandler(eh);
		
	}
	
	@Override
	public synchronized final void start()
	{
		
		super.start();
		
	}
	
	@Override
	public final String toString()
	{
		
		return this.getName() + "[" + this.getClass().getName() + "]";
		
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		
		return super.equals(obj);
		
    }
	
	@Override
	protected final void finalize() throws Throwable
	{
		
		super.finalize();
		
	}
	
	@Override
	public native final int hashCode();
	
	protected abstract void action();
	
	public final void enable()
	{
		
		this.start();
		
	}
	
	@SuppressWarnings("deprecation")
	public final void disable()
	{
		
		this.stop();
		
	}
	
}
