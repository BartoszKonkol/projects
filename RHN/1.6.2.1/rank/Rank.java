package net.polishgames.rhenowar.util.rank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class Rank extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.5.4.8");
	
	private final String name;
	private final ChatColor color;
	private final Entitlement entitlement;
	private final List<String> permissions;
	
	public Rank(final String name, final ChatColor color, final Entitlement entitlement, final List<String> permissions)
	{
		this.name = Util.nonEmpty(name);
		this.color = Util.nonNull(color);
		this.entitlement = Util.nonNull(entitlement);
		this.permissions = Util.nonNull(permissions);
	}
	
	public Rank(final String name, final ChatColor color, final Entitlement entitlement)
	{
		this(name, color, entitlement, new ArrayList<String>());
	}
	
	public Rank(final String name, final Entitlement entitlement)
	{
		this(name, ChatColor.WHITE, entitlement);
	}
	
	public Rank(final String name, final Entitlement entitlement, final List<String> permissions)
	{
		this(name, ChatColor.WHITE, entitlement, permissions);
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final ChatColor giveColor()
	{
		return this.color;
	}
	
	public final Entitlement giveEntitlement()
	{
		return this.entitlement;
	}
	
	public final List<String> givePermissions()
	{
		return Collections.unmodifiableList(this.permissions);
	}
	
	public final Team giveScoreboardTeam(final Consumer<Team> consumer)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			return util.giveScoreboardTeam(util.giveUtilName(), this.getClass().getSimpleName().toLowerCase(), this.giveName(), team ->
			{
				if(consumer != null)
					consumer.accept(team);
				util.doAdjustTeam(team, this.giveColor());
			});
		}
		else
			return null;
	}
	
	public final Team giveScoreboardTeam()
	{
		return this.giveScoreboardTeam(null);
	}
	
	public final Rank addPermission(final String permission)
	{
		Rank.addPermission(permission, this.permissions);
		return this;
	}
	
	public final Rank delPermission(final String permission)
	{
		Rank.delPermission(permission, this.permissions);
		return this;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveName());
		map.put("color", this.giveColor());
		map.put("entitlement", this.giveEntitlement());
		map.put("permissions", this.permissions);
		return map;
	}
	
	public static final void addPermission(final String permission, final Collection<String> permissions)
	{
		if(Util.nonEmpty(permission).startsWith("-"))
			Rank.delPermission(permission.substring(1), permissions);
		else
		{
			if(permissions.contains("-" + permission))
				permissions.remove("-" + permission);
			if(!permissions.contains(permission))
				permissions.add(permission);
		}
	}
	
	public static final void delPermission(String permission, final Collection<String> permissions)
	{
		if(Util.nonEmpty(permission).startsWith("-"))
			permission = permission.substring(1);
		if(permissions.contains(permission))
			permissions.remove(permission);
		if(!permissions.contains("-" + permission))
			permissions.add("-" + permission);
	}
	
}
