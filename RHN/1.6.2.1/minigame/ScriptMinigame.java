package net.polishgames.rhenowar.util.minigame;

import java.io.File;
import java.util.Map;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.Script;
import net.polishgames.rhenowar.util.Util;

public class ScriptMinigame extends Script
{
	
	private final String name;
	private final Player player;
	
	public ScriptMinigame(final File file, final String name, final Player player, final String... tables)
	{
		super(file, tables);
		this.name = Util.nonEmpty(name);
		this.player = player;
		if(this.hasPlayer())
			this.giveGlobals().set("player", this.givePlayer().getName());
	}
	
	public ScriptMinigame(final File file, final String name, final String... tables)
	{
		this(file, name, null, tables);
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final Player givePlayer()
	{
		return this.player;
	}
	
	public final boolean hasPlayer()
	{
		return this.givePlayer() != null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveName());
		map.put("player", this.givePlayer());
		return super.giveProperties(map);
	}
	
}
