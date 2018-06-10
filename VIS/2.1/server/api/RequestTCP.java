package net.polishgames.vis2.server.api;

import java.util.Properties;

public interface RequestTCP
{

	public abstract String giveProtocol();
	public abstract Properties giveHeaderGlobal();
	public abstract Properties giveHeaderPart();
	public abstract byte[] giveContent();
	public abstract SocketData giveSocketData();
	public abstract ResponseTCP giveNewResponse();
	public abstract boolean hasContent();
	
}
