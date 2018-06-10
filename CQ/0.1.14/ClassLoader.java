package net.polishgames.rhenowar.conquest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassLoader extends URLClassLoader
{
	
	protected ClassLoader(final URL[] urls)
	{
		super(urls);
	}
	
	protected Object object;
	
	public ClassLoader doElicitClass(final String clazz) throws ReflectiveOperationException
	{
		this.object = this.loadClass(clazz).newInstance();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public final <T> T giveLastObject(final Class<T> clazz)
	{
		return (T) this.object;
	}
	
	public final Object giveLastObject()
	{
		return this.giveLastObject(Object.class);
	}
	
	public static ClassLoader giveClassLoader(final URL[] urls, final java.lang.ClassLoader parent)
	{
		if(parent instanceof URLClassLoader)
		{
			final List<URL> list = new ArrayList<URL>();
			for(final URL url : ((URLClassLoader) parent).getURLs())
				list.add(url);
			for(final URL url : urls)
				list.add(url);
			return new ClassLoader(list.toArray(new URL[list.size()]));
		}
		else
			return null;
	}
	
	public static final ClassLoader giveClassLoader(final URL[] urls, final Class<?> clazz)
	{
		return giveClassLoader(urls, giveClassLoader(clazz));
	}
	
	public static final ClassLoader giveClassLoader(final URL[] urls)
	{
		return giveClassLoader(urls, giveClassLoader());
	}

	public static final ClassLoader giveClassLoader(final URL url, final java.lang.ClassLoader parent)
	{
		return giveClassLoader(new URL[]{url}, parent);
	}

	public static final ClassLoader giveClassLoader(final URL url, final Class<?> clazz)
	{
		return giveClassLoader(url, giveClassLoader(clazz));
	}

	public static final ClassLoader giveClassLoader(final URL url)
	{
		return giveClassLoader(url, giveClassLoader());
	}
	
	public static final java.lang.ClassLoader giveClassLoader(final Class<?> clazz)
	{
		return clazz.getClassLoader();
	}
	
	public static final java.lang.ClassLoader giveClassLoader()
	{
		return Thread.currentThread().getContextClassLoader();
	}
	
	public static final URL giveURL(final File file) throws MalformedURLException
	{
		return file.toURI().toURL();
	}
	
}
