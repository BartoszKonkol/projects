package net.polishgames.vis2.server.jweb.ftp;

import java.util.Objects;

public class GuideFTP
{
	
	private final String source, directory;
	private final IRecipeFTP recipe;
	
	public GuideFTP(final String source, final IRecipeFTP recipe, final String directory)
	{
		this.source = Objects.requireNonNull(source).replace('\\', '/');
		this.recipe = Objects.requireNonNull(recipe);
		this.directory = directory == null || directory.isEmpty() ? "/" : directory.replace('\\', '/');
	}
	
	public GuideFTP(final String source, final IRecipeFTP recipe)
	{
		this(source, recipe, null);
	}
	
	public final String giveSource()
	{
		return this.source;
	}
	
	public final IRecipeFTP giveRecipe()
	{
		return this.recipe;
	}
	
	public final String giveDirectory()
	{
		return this.directory;
	}
	
}
