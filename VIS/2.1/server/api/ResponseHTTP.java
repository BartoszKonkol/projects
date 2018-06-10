package net.polishgames.vis2.server.api;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.function.Consumer;

public interface ResponseHTTP
{

	public abstract ResponseHTTP setContent(final byte[] content);
	public abstract ResponseHTTP setContentConsumer(final Consumer<OutputStream> consumer);
	public abstract byte[] getContent();
	public abstract Consumer<OutputStream> getContentConsumer();
	public abstract Properties giveHeader();
	public abstract Collection<CookieHTTP> giveCookies();
	public abstract StatusCodeHTTP giveStatusCode();
	public abstract boolean hasContent();
	public abstract boolean hasContentConsumer();
	
}
