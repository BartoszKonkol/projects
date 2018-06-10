package net.polishgames.vis2.server.jweb.api;

import java.util.Objects;
import java.util.function.Function;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public abstract class FunctionManagerLUA extends TwoArgFunction implements FunctionManager
{
	
	private Globals globals;
	private boolean loaded;
	
	public final FunctionManagerLUA setGlobals(final Globals globals)
	{
		this.globals = Objects.requireNonNull(globals);
		return this;
	}
	
	public final Globals getGlobals()
	{
		return this.globals;
	}
	
	public final boolean hasGlobals()
	{
		return this.getGlobals() != null;
	}
	
	@Override
	public final boolean isLoaded()
	{
		return this.loaded;
	}
	
	@Override
	public FunctionManagerLUA doLoad()
	{
		if(this.hasGlobals())
		{
			this.getGlobals().load(this);
			this.loaded = true;
			return this;
		}
		else
			return null;
	}
	
	@Override
	public LuaValue call(final LuaValue modname, final LuaValue env)
	{
		this.doRegister((method) ->
		{
			final Class<?> type = method.getReturnType();
			if(type == void.class || Varargs.class.isAssignableFrom(type))
			{
				final int count = method.getParameterCount();
				if(count < 2)
				{
					final Function<Varargs, Varargs> invoke = (args) ->
					{
						Object result = null;
						try
						{
							result = method.invoke(FunctionManagerLUA.this, count == 1 ? new Object[]{args} : new Object[0]);
						}
						catch(final ReflectiveOperationException e){}
						return type != void.class && result != null && result instanceof Varargs ? (Varargs) result : LuaValue.NIL;
					};
					if(count == 0 || Varargs.class.isAssignableFrom(method.getParameterTypes()[0]))
						env.set(method.getName(), count == 1 ? new VarArgFunction()
						{
							@Override
							public final Varargs invoke(final Varargs args)
							{
								return invoke.apply(args);
							}
						} : new ZeroArgFunction()
						{
							@Override
							public LuaValue call()
							{
								return this.invoke((Varargs) null).arg1();
							}
							
							@Override
							public Varargs invoke(final Varargs args)
							{
								return invoke.apply(args);
							}
						});
				}
			}
		});
		return env;
	}
	
}
