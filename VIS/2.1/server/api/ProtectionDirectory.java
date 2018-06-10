package net.polishgames.vis2.server.api;

import java.util.Collection;

public interface ProtectionDirectory
{
	
	public abstract String givePath();
	public abstract Thread giveThread();
	public abstract Collection<Class<?>> giveClasses();
	public abstract boolean isAccept();
	public abstract boolean hasThread();
	public abstract boolean hasClasses();
	
}
