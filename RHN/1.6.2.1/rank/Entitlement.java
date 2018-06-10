package net.polishgames.rhenowar.util.rank;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class Entitlement extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.5.2.1");
	
	private final Map<TypeEntitlement, Integer> entitlements;
	
	public Entitlement(final int levelBuild, final int levelSpecial)
	{
		this.entitlements = new HashMap<TypeEntitlement, Integer>();
		this.entitlements.put(TypeEntitlement.BUILD, levelBuild);
		this.entitlements.put(TypeEntitlement.SPECIAL, levelSpecial);
	}
	
	public Entitlement(final int level)
	{
		this(level % 10 + 1, level / 10 + 1);
	}
	
	public final Map<TypeEntitlement, Integer> giveEntitlements()
	{
		return Collections.unmodifiableMap(this.entitlements);
	}
	
	public final int giveEntitlementLevel(final TypeEntitlement type)
	{
		return this.giveEntitlements().get(Util.nonNull(type));
	}
	
	public final int giveEntitlementLevel()
	{
		return Entitlement.giveLevel(this.giveEntitlementLevel(TypeEntitlement.BUILD), this.giveEntitlementLevel(TypeEntitlement.SPECIAL));
	}
	
	public final ThresholdEntitlement giveEntitlementThreshold(final TypeEntitlement type)
	{
		return ThresholdEntitlement.giveValue(this.giveEntitlementLevel(type));
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("entitlements", this.giveEntitlements());
		return map;
	}
	
	public static final int giveLevel(int levelBuild, int levelSpecial)
	{
		if(levelBuild < 1)
			levelBuild = 1;
		else if(levelBuild > 10)
			levelBuild = 10;
		if(levelSpecial < 1)
			levelSpecial = 1;
		else if(levelSpecial > 10)
			levelSpecial = 10;
		return levelBuild + (levelSpecial - 1) * 10 - 1;
	}
	
	public static enum TypeEntitlement implements Rhenowar
	{
		
		BUILD,
		SPECIAL,
		;
		
		public final String giveType()
		{
			return this.name().toLowerCase();
		}
		
		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("type", this.giveType());
			return map;
		}
		
		@Override
		public String toString()
		{
			if(Util.hasUtil())
				return Util.giveUtil().toString(this);
			else
				return this.giveType();
		}
		
	}
	public static enum ThresholdEntitlement implements Rhenowar
	{
		
		THRESHOLD_0,
		THRESHOLD_1,
		THRESHOLD_2,
		THRESHOLD_3,
		THRESHOLD_4,
		;
		
		public final byte giveThreshold()
		{
			try
			{
				return Byte.valueOf(this.name().split("_")[1]);
			}
			catch(final ArrayIndexOutOfBoundsException | NumberFormatException e)
			{
				e.printStackTrace();
				return 0;
			}
		}
		
		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("threshold", this.giveThreshold());
			return map;
		}
		
		@Override
		public String toString()
		{
			if(Util.hasUtil())
				return Util.giveUtil().toString(this);
			else
				return this.name();
		}
		
		public static final ThresholdEntitlement giveValue(final byte threshold)
		{
			for(final ThresholdEntitlement value : ThresholdEntitlement.values())
				if(value.giveThreshold() == threshold)
					return value;
			return ThresholdEntitlement.THRESHOLD_0;
		}
		
		public static final ThresholdEntitlement giveValue(final int level)
		{
			return ThresholdEntitlement.giveValue((byte) (level * 0.45F));
		}
		
	}
	
}
