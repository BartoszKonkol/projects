package javax.jao.api;

import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;
import javax.jao.registry.IRegistry;
import javax.jao.registry.KeyJAO;
import javax.jao.registry.ValueJAO;

public final class Service
{
	
	private Service(){}

	private static IRegistry<KeyJAO, ValueJAO, String> regTmp, regApp, regAppDir;
	private static Properties data;
	private static ZipFile file;
	private static JarFile main;
	private static Attributes mainManifest;
	private static ClassLoader loader;
	
	public static final void setRegTmp(final IRegistry<KeyJAO, ValueJAO, String> regTmp)
	{
		Service.regTmp = regTmp;
	}
	
	public static final IRegistry<KeyJAO, ValueJAO, String> getRegTmp()
	{
		return Service.regTmp;
	}
	
	public static final void setRegApp(final IRegistry<KeyJAO, ValueJAO, String> regApp)
	{
		Service.regApp = regApp;
	}
	
	public static final IRegistry<KeyJAO, ValueJAO, String> getRegApp()
	{
		return Service.regApp;
	}
	
	public static final void setRegAppDir(final IRegistry<KeyJAO, ValueJAO, String> regAppDir)
	{
		Service.regAppDir = regAppDir;
	}
	
	public static final IRegistry<KeyJAO, ValueJAO, String> getRegAppDir()
	{
		return Service.regAppDir;
	}
	
	public static final void setData(final Properties data)
	{
		Service.data = data;
	}
	
	public static final Properties getData()
	{
		return Service.data;
	}
	
	public static final void setFile(final ZipFile file)
	{
		Service.file = file;
	}
	
	public static final ZipFile getFile()
	{
		return Service.file;
	}
	
	public static final void setMain(final JarFile main)
	{
		Service.main = main;
	}
	
	public static final JarFile getMain()
	{
		return Service.main;
	}
	
	public static final void setMainManifest(final Attributes mainManifest)
	{
		Service.mainManifest = mainManifest;
	}
	
	public static final Attributes getMainManifest()
	{
		return Service.mainManifest;
	}
	
	public static final void setLoader(final ClassLoader loader)
	{
		Service.loader = loader;
	}
	
	public static final ClassLoader getLoader()
	{
		return Service.loader;
	}
	
}
