package net.polishgames.vis2.server.jweb.ftp;

import net.polishgames.vis2.server.jweb.api.Clearable;

public interface IRecipeFTP extends Clearable
{
	
	public abstract IRecipeFTP setHostname(final String hostname);
	public abstract IRecipeFTP setUsername(final String username);
	public abstract IRecipeFTP setPassword(final String password);
	public abstract IRecipeFTP setFilename(final String filename);
	public abstract IRecipeFTP setHostport(final int hostport);
	public abstract String getHostname();
	public abstract String getUsername();
	public abstract String getPassword();
	public abstract String getFilename();
	public abstract int getHostport();
	public abstract boolean hasHostname();
	public abstract boolean hasUsername();
	public abstract boolean hasPassword();
	public abstract boolean hasFilename();
	public abstract boolean hasHostport();
	
}
