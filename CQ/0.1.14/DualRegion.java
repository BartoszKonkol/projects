package net.polishgames.rhenowar.conquest;

public final class DualRegion
{
	
	final Region first, second;
	
	public DualRegion(final Region first, final Region second)
	{
		this.first = first;
		this.second = second;
	}
	
	public final Region giveFirst()
	{
		return this.first;
	}
	
	public final Region giveSecond()
	{
		return this.second;
	}
	
}
