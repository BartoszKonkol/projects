package net.polishgames.vis2.server.jweb.api;

import java.lang.reflect.Method;
import java.util.Objects;
import com.caucho.quercus.QuercusContext;
import com.caucho.quercus.module.AbstractQuercusModule;
import com.caucho.quercus.module.ModuleContext;
import com.caucho.quercus.module.StaticFunction;

public abstract class FunctionManagerPHP extends AbstractQuercusModule implements FunctionManager
{
	
	private QuercusContext quercus;
	private boolean loaded;
	
	public final FunctionManagerPHP setQuercus(final QuercusContext quercus)
	{
		this.quercus = Objects.requireNonNull(quercus);
		return this;
	}
	
	public final QuercusContext getQuercus()
	{
		return this.quercus;
	}
	
	public final boolean hasQuercus()
	{
		return this.getQuercus() != null;
	}
	
	@Override
	public final boolean isLoaded()
	{
		return this.loaded;
	}
	
	public final ModuleContext giveModule()
	{
		if(this.hasQuercus())
			return this.getQuercus().getModuleContext();
		else
			return null;
	}
	
	@Override
	public FunctionManagerPHP doLoad()
	{
		final FunctionManager result = this.doRegister(this::doRegister);
		if(result != null && result instanceof FunctionManagerPHP)
		{
			this.loaded = true;
			return (FunctionManagerPHP) result;
		}
		else
			return null;
	}
	
	protected FunctionManagerPHP doRegister(final Method method)
	{
		if(this.hasQuercus())
		{
			this.getQuercus().setFunction(this.getQuercus().createString(Objects.requireNonNull(method).getName()), new StaticFunction(this.giveModule(), this, method));
			return this;
		}
		else
			return null;
	}
	
	public final FunctionManagerPHP doRegister(final String name, final Class<?>... parameters)
	{
		Method method = null;
		try
		{
			method = this.getClass().getDeclaredMethod(Objects.requireNonNull(name), parameters);
		}
		catch(final NoSuchMethodException e){}
		return method != null ? this.doRegister(method) : null;
	}
	
}
