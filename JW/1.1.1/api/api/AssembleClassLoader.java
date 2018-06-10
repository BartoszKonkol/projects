package net.polishgames.vis2.server.jweb.api;

import java.util.jar.JarEntry;

public abstract class AssembleClassLoader extends ClassLoader
{
	
	protected AssembleClassLoader(final ClassLoader classLoader)
	{
		super(classLoader);
	}
	
	public abstract AssembleClassLoader addEntry(final JarEntry entry, final byte[] content);
	public abstract AssembleClassLoader delEntry(final JarEntry entry);
	public abstract JarEntry giveEntry(final String name);
	public abstract byte[] giveEntryContent(final JarEntry entry);
	public abstract byte[] giveEntryContent(final String name);
	public abstract String giveHash();
	
}
