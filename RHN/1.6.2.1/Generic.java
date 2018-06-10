package net.polishgames.rhenowar.util;

import java.io.Serializable;

public interface Generic<E> extends Rhenowar, Serializable
{
	
	public Class<E> giveGenericType();
	
}
