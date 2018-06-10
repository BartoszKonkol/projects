package net.polishgames.vis2.server.api;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public interface RequestHTTP
{
	
	public abstract String giveProtocol();
	public abstract String giveMethod();
	public abstract String giveResource();
	public abstract Properties giveHeader();
	public abstract Properties giveContent();
	public abstract Map<String, Collection<String>> giveContentKit();
	public abstract Collection<MultipartElement> giveMultipart();
	public abstract String giveJson();
	public abstract Collection<CookieHTTP> giveCookies();
	public abstract StatusCodeHTTP giveStatusCode();
	public abstract SocketData giveSocketData();
	public abstract CookieHTTP giveNewCookie(final String name);
	public abstract CookieHTTP giveNewCookie(final String name, final String value);
	public abstract ResponseHTTP giveNewResponse();
	public abstract boolean hasContent();
	public abstract boolean hasContentKit();
	public abstract boolean hasMultipart();
	public abstract boolean hasJson();
	
}
