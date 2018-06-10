package net.polishgames.rhenowar.util.serialization;

import java.io.Serializable;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Series;

public abstract class RhenowarSerializable extends RhenowarObject implements Cloneable, Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	@Override
	public abstract RhenowarSerializable clone();
	
	public static final long giveSerialVersion(final short series)
	{
		return Math.max(series, Series.giveValue("1.4.3.1").giveSeries());
	}
	
	public static final long giveSerialVersion(final Series series)
	{
		return RhenowarSerializable.giveSerialVersion(series.giveSeries());
	}
	
	public static final long giveSerialVersion(final String version)
	{
		return RhenowarSerializable.giveSerialVersion(Series.giveValue(version));
	}
	
}
