package net.polishgames.vis2.server.api;

import java.net.InetAddress;

public interface SocketData
{
	
	public abstract InetAddress giveLocalAddress();
	public abstract int giveLocalPort();
	public abstract InetAddress giveAddress();
	public abstract int givePort();
	public abstract Object giveOption(final int id);
	
}
