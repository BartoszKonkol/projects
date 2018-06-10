package net.polishgames.rhenowar.util.auth.event;

import java.util.Map;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.auth.PlayerAuth.StageAuthorization;
import net.polishgames.rhenowar.util.event.PlayerHandlerEvent;

public class PlayerAuthEvent extends PlayerHandlerEvent
{
	
	private final StageAuthorization stage;

	public PlayerAuthEvent(final Player player, final StageAuthorization stage)
	{
		super(player);
		this.stage = Util.nonNull(stage);
	}
	
	public final StageAuthorization giveStage()
	{
		return this.stage;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("stage", this.giveStage());
		return super.giveProperties(map);
	}
	
}
