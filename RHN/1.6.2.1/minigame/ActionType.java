package net.polishgames.rhenowar.util.minigame;

import java.util.Map;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;

@SuppressWarnings("rawtypes")
public enum ActionType implements Rhenowar
{
	
	INV(ActionInv.class),
	COM(ActionCom.class),
	END(ActionEnd.class),
	;
	
	private final Class<? extends Action> defaultClass;
	private final String name;
	
	private ActionType(final Class<? extends Action> defaultClass)
	{
		this.defaultClass = defaultClass;
		this.name = this.name().toLowerCase();
	}
	
	public final Class<? extends Action> giveDefaultClass()
	{
		return this.defaultClass;
	}
	
	public final String giveName()
	{
		return this.name;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("defaultClass", this.giveDefaultClass());
		map.put("name", this.giveName());
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return this.giveName();
	}
	
	public static final ActionType giveActionType(final String type)
	{
		Util.nonEmpty(type);
		for(final ActionType actionType : ActionType.values())
			if(actionType.giveName().equalsIgnoreCase(type))
				return actionType;
		return ActionType.END;
	}
	
}
