package net.polishgames.vis2.server.jweb.api;

import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.Manifest;
import net.polishgames.vis2.server.jweb.ftp.IRecipeFTP;

public abstract class Plugin
{
	
	private Manifest manifest;
	private IRecipeFTP recipe;
	private JWeb jweb;
	private AssembleClassLoader classLoader;
	private JarEntry entry;
	private String hash;
	
	public final Plugin setManifest(final Manifest manifest)
	{
		this.manifest = manifest;
		return this;
	}
	
	public final Plugin setRecipe(final IRecipeFTP recipe)
	{
		this.recipe = recipe;
		return this;
	}
	
	public final Plugin setJWeb(final JWeb jweb)
	{
		this.jweb = jweb;
		return this;
	}
	
	public final Plugin setClassLoader(final AssembleClassLoader classLoader)
	{
		this.classLoader = classLoader;
		return this;
	}
	
	public final Plugin setEntry(final JarEntry entry)
	{
		this.entry = entry;
		return this;
	}
	
	public final Plugin setHash(final String hash)
	{
		this.hash = hash;
		return this;
	}
	
	public final Manifest getManifest()
	{
		return this.manifest;
	}
	
	public final IRecipeFTP getRecipe()
	{
		return this.recipe;
	}
	
	public final JWeb getJWeb()
	{
		return this.jweb;
	}
	
	public final AssembleClassLoader getClassLoader()
	{
		return this.classLoader;
	}
	
	public final JarEntry getEntry()
	{
		return this.entry;
	}
	
	public final String getHash()
	{
		return this.hash;
	}
	
	public final boolean hasManifest()
	{
		return this.getManifest() != null;
	}
	
	public final boolean hasRecipe()
	{
		return this.getRecipe() != null;
	}
	
	public final boolean hasJWeb()
	{
		return this.getJWeb() != null;
	}
	
	public final boolean hasClassLoader()
	{
		return this.getClassLoader() != null;
	}
	
	public final boolean hasEntry()
	{
		return this.getEntry() != null;
	}
	
	public final boolean hasHash()
	{
		return this.getHash() != null && !this.getHash().isEmpty();
	}
	
	public void onLoad() throws Exception {}
	public boolean onWebsite(final BasicGroup group, final Map<String, Object> server, final String resource) throws Exception {return false;}
	
}
