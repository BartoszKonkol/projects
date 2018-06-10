package net.polishgames.rhenowar.util.mysql;

import java.io.Serializable;
import java.util.Map;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class Argument extends NameTransform implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final String name;
	private final TypeData type;
	
	public Argument(final String name, final TypeData type)
	{
		this.name = Util.nonEmpty(name);
		this.type = Util.nonNull(type);
	}
	
	public final TypeData giveType()
	{
		return this.type;
	}
	
	@Override
	public final String giveName()
	{
		return this.name;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("type", this.giveType());
		return super.giveProperties(map);
	}
	
}
