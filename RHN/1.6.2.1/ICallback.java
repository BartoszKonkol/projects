package net.polishgames.rhenowar.util;

@FunctionalInterface
public interface ICallback extends IRhenowar
{
	
	public boolean onResultReceived(final Object result);
	
}
