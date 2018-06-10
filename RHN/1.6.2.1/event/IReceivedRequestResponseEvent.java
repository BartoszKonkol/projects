package net.polishgames.rhenowar.util.event;

import net.polishgames.rhenowar.util.Rhenowar;

public interface IReceivedRequestResponseEvent extends Rhenowar
{

	public String giveRequest();
	public int giveID();
	public Object giveResult();
	
}
