package net.polishgames.vis2.server.api;

import java.util.Properties;

public interface ResponseTCP
{

	public abstract ResponseTCP setContent(final byte[] content);
	public abstract byte[] getContent();
	public abstract Properties giveHeader();
	
}
