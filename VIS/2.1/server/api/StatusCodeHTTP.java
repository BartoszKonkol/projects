package net.polishgames.vis2.server.api;

public interface StatusCodeHTTP
{
	
	public abstract StatusCodeHTTP set(final int code, final String description);
	public abstract String get();
	public abstract int giveCode();
	public abstract String giveDescription();
	
}
