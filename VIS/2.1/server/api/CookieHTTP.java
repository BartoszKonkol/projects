package net.polishgames.vis2.server.api;

import java.util.Date;

public interface CookieHTTP
{

	public abstract String giveName();
	public abstract CookieHTTP setValue(final String value);
	public abstract CookieHTTP setExpires(final Date expires);
	public abstract CookieHTTP setPath(final String path);
	public abstract CookieHTTP setDomain(final String domain);
	public abstract CookieHTTP setSecure(final boolean secure);
	public abstract CookieHTTP setHttpOnly(final boolean httpOnly);
	public abstract String getValue();
	public abstract Date getExpires();
	public abstract String getPath();
	public abstract String getDomain();
	public abstract boolean getSecure();
	public abstract boolean getHttpOnly();
	public abstract boolean isModified();
	
}
