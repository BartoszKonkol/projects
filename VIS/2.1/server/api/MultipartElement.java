package net.polishgames.vis2.server.api;

import java.util.Properties;

public interface MultipartElement
{
	
	public abstract Properties giveDescription();
	public abstract Properties giveDisposition();
	public abstract byte[] giveContent();
	
}
