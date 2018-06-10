package net.polishgames.rhenowar.util.mysql;

import java.io.Serializable;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class Table extends NameTransform implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final String name;
	
	public Table(final String name)
	{
		this.name = Util.nonEmpty(name);
	}

	@Override
	public String giveName()
	{
		return this.name;
	}
	
}
