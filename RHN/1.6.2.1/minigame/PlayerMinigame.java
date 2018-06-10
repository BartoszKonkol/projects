package net.polishgames.rhenowar.util.minigame;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import net.polishgames.rhenowar.util.PlayerRhenowar;
import net.polishgames.rhenowar.util.RhenowarPlayer;
import net.polishgames.rhenowar.util.Util;

public final class PlayerMinigame extends RhenowarPlayer
{

	private final Map<String, Object> scripts;
	private final Object utilScript;

	private PlayerMinigame(final OfflinePlayer player) throws ReflectiveOperationException
	{
		super(player);
		this.scripts = new HashMap<String, Object>();
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			this.utilScript = util.hasUtilMinigame() ? util.giveUtilMinigameSupport().giveScript(util.giveUtilLuaInventoryFile(), "!", this.givePlayer(), "hand") : null;
		}
		else
			this.utilScript = null;
	}
	
	public final Object giveUtilScript()
	{
		return this.utilScript;
	}
	
	public final boolean hasUtilScript()
	{
		return this.giveUtilScript() != null;
	}
	
	public final synchronized PlayerMinigame addScript(final String name, final Object script)
	{
		this.scripts.put(Util.nonEmpty(name), Util.nonNull(script));
		return this;
	}
	
	public final synchronized PlayerMinigame delScript(final String name)
	{
		this.scripts.remove(Util.nonEmpty(name));
		return this;
	}
	
	public final synchronized Object giveScript(final String name)
	{
		return this.scripts.get(Util.nonEmpty(name));
	}
	
	public final Object giveScripts()
	{
		return Collections.unmodifiableMap(this.scripts);
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("script", this.giveUtilScript());
		map.put("scripts", this.giveScripts());
		return super.giveProperties(map);
	}
	
	public static final PlayerMinigame givePlayerMinigame(final OfflinePlayer player) throws ReflectiveOperationException
	{
		final PlayerRhenowar playerRhenowar = PlayerRhenowar.givePlayerRhenowar(player);
		final List<PlayerMinigame> list = playerRhenowar.giveRhenowarPlayers(PlayerMinigame.class);
		if(list.size() > 0)
			return list.get(0);
		else
		{
			playerRhenowar.addRhenowarPlayer(new PlayerMinigame(player));
			return PlayerMinigame.givePlayerMinigame(player);
		}
				
	}
	
}
