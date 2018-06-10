package net.polishgames.rhenowar.util;

@FunctionalInterface
public interface Task<A, R> extends IRhenowar
{
	
	public R task(final A argument);
	
}
