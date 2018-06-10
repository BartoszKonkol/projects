package net.polishgames.rhenowar.util.minigame.event;

import java.util.Map;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.event.PlayerHandlerEvent;
import net.polishgames.rhenowar.util.minigame.Action;
import net.polishgames.rhenowar.util.minigame.ActionCom;
import net.polishgames.rhenowar.util.minigame.ActionEnd;
import net.polishgames.rhenowar.util.minigame.ActionInv;

public class PlayerActionMinigameEvent extends PlayerHandlerEvent
{
	
	private final Action<?> action;

	public PlayerActionMinigameEvent(final Action<?> action)
	{
		super(Util.nonNull(action).givePlayer());
		this.action = action;
	}
	
	public final Action<?> giveAction()
	{
		return this.action;
	}
	
	public final boolean isInv()
	{
		return this.giveAction() instanceof ActionInv;
	}
	
	public final boolean isCom()
	{
		return this.giveAction() instanceof ActionCom;
	}
	
	public final boolean isEnd()
	{
		return this.giveAction() instanceof ActionEnd;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("action", this.giveAction());
		map.put("inv", this.isInv());
		map.put("com", this.isCom());
		map.put("end", this.isEnd());
		return super.giveProperties(map);
	}
	
}
