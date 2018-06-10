package net.polishgames.vis2.server.jweb.api;

import java.io.File;
import java.util.Collection;
import java.util.Properties;
import net.polishgames.vis2.server.api.RequestHTTP;
import net.polishgames.vis2.server.api.ResponseHTTP;
import net.polishgames.vis2.server.jweb.ftp.IRecipeFTP;

public abstract class BasicGroup
{
	
	public abstract RequestHTTP giveRequest();
	public abstract Properties givePhrases();
	public abstract BasicGroup setResponse(final ResponseHTTP response);
	public abstract BasicGroup setRecipe(final IRecipeFTP recipe);
	public abstract BasicGroup setWorkSpace(final File workspace);
	public abstract File getWorkSpace();
	public abstract ResponseHTTP getResponse();
	public abstract IRecipeFTP getRecipe();
	public abstract boolean hasRecipe();
	public abstract boolean hasWorkSpace();
	protected abstract Collection<Object> giveSuspects();
	public abstract Object giveSearch(final Class<?> wanted);
	
}
