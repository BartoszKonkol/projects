package net.polishgames.vis2.server.jweb.api;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public interface FunctionManager
{
	
	public abstract FunctionManager doLoad();
	public abstract boolean isLoaded();
	
	public default FunctionManager doRegister(final Consumer<Method> consumer)
	{
		if(consumer != null)
		{
			for(final Method method : this.getClass().getDeclaredMethods())
				if(!Modifier.isAbstract(method.getModifiers()))
					consumer.accept(method);
			return this;
		}
		else
			return null;
	}
	
}
