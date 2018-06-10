package net.polishgames.vis2.server.api;

import java.io.File;
import java.util.Map;
import java.util.jar.JarFile;

public interface PluginFile
{
	
	public abstract File giveFile();
	public abstract File giveWorkSpace();
	public abstract JarFile giveJarFile();
	public abstract ClassLoader giveClassLoader();
	public abstract Map<Class<?>, ?> giveClasses();
	
}
