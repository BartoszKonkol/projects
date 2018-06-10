package net.polishgames.rhenowar.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassLoader extends URLClassLoader implements Rhenowar
{
	
	protected ClassLoader(final URL[] urls)
	{
		super(Util.nonEmpty(urls));
	}
	
	protected Object object;
	
	public ClassLoader doElicitClass(final String clazz) throws ReflectiveOperationException
	{
		this.object = this.loadClass(Util.nonEmpty(clazz)).newInstance();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public final <T> T giveLastObject(final Class<T> clazz)
	{
		Util.nonNull(clazz);
		return (T) this.object;
	}
	
	public final Object giveLastObject()
	{
		return this.giveLastObject(Object.class);
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("lastObject", this.giveLastObject());
		map.put("urls", this.getURLs());
		map.put("packages", this.getPackages());
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return super.toString();
	}
	
	public static ClassLoader giveClassLoader(final URL[] urls, final java.lang.ClassLoader parent)
	{
		if(Util.nonNull(parent) instanceof URLClassLoader)
		{
			final List<URL> list = new ArrayList<URL>();
			for(final URL url : ((URLClassLoader) parent).getURLs())
				list.add(url);
			for(final URL url : Util.nonEmpty(urls))
				list.add(url);
			return new ClassLoader(list.toArray(new URL[list.size()]));
		}
		else
			return null;
	}
	
	public static final ClassLoader giveClassLoader(final URL[] urls, final Class<?> clazz)
	{
		return ClassLoader.giveClassLoader(urls, ClassLoader.giveClassLoader(clazz));
	}
	
	public static final ClassLoader giveClassLoader(final URL[] urls)
	{
		return ClassLoader.giveClassLoader(urls, ClassLoader.giveClassLoader());
	}

	public static final ClassLoader giveClassLoader(final URL url, final java.lang.ClassLoader parent)
	{
		return ClassLoader.giveClassLoader(new URL[]{Util.nonNull(url)}, parent);
	}

	public static final ClassLoader giveClassLoader(final URL url, final Class<?> clazz)
	{
		return ClassLoader.giveClassLoader(url, ClassLoader.giveClassLoader(clazz));
	}

	public static final ClassLoader giveClassLoader(final URL url)
	{
		return ClassLoader.giveClassLoader(url, ClassLoader.giveClassLoader());
	}
	
	public static final java.lang.ClassLoader giveClassLoader(final Class<?> clazz)
	{
		return Util.nonNull(clazz).getClassLoader();
	}
	
	public static final java.lang.ClassLoader giveClassLoader()
	{
		return Thread.currentThread().getContextClassLoader();
	}
	
	public static final URL giveURL(final File file) throws MalformedURLException
	{
		return Util.nonNull(file).toURI().toURL();
	}
	
}
