package net.polishgames.rhenowar.util.minigame;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.minigame.event.PlayerActionMinigameEvent;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public abstract class Action<R> extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	protected static final Map<Player, Map<String, Action<?>>> actions = new HashMap<Player, Map<String, Action<?>>>();
	
	private final String name;
	private final ActionType type;
	private final net.polishgames.rhenowar.util.serialization.Player player;
	
	public Action(final String name, final ActionType type, final Player player)
	{
		this.name = Util.nonEmpty(name);
		this.type = Util.nonNull(type);
		this.player = new net.polishgames.rhenowar.util.serialization.Player(Util.nonNull(player));
		this.doSaveAction();
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final ActionType giveType()
	{
		return this.type;
	}
	
	public final Player givePlayer()
	{
		return this.player.toPlayerBukkit();
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveName());
		map.put("type", this.giveType());
		map.put("result", this.giveResult());
		map.put("player", this.player);
		return map;
	}
	
	public abstract R giveResult();
	
	private final synchronized void doSaveAction()
	{
		final Player player = this.givePlayer();
		if(player == null)
			return;
		final String name = this.giveName();
		final Map<Player, Map<String, Action<?>>> players = Action.actions;
		if(!players.containsKey(player))
			players.put(player, new HashMap<String, Action<?>>());
		final Map<String, Action<?>> actions = players.get(player);
		if(!actions.containsKey(name))
		{
			actions.put(name, this);
			if(Util.hasUtil())
				Util.giveUtil().givePluginManager().callEvent(new PlayerActionMinigameEvent(this));
		}
	}
	
	public static synchronized final Action<?> giveAction(final String name, final Player player)
	{
		if(Action.actions.containsKey(Util.nonNull(player)))
			return Action.actions.get(player).get(Util.nonEmpty(name));
		else
			return null;
	}
	
}
