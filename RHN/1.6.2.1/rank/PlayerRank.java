package net.polishgames.rhenowar.util.rank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scoreboard.Team;
import net.polishgames.rhenowar.util.PlayerRhenowar;
import net.polishgames.rhenowar.util.ServerType;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.mysql.Argument;
import net.polishgames.rhenowar.util.mysql.ClausesData;
import net.polishgames.rhenowar.util.mysql.RhenowarPlayerSQL;
import net.polishgames.rhenowar.util.mysql.Table;
import net.polishgames.rhenowar.util.mysql.TypeData;
import net.polishgames.rhenowar.util.mysql.Value;

public final class PlayerRank extends RhenowarPlayerSQL
{
	
	public static final Table TABLE = new Table("rank");
	public static final Map<String, Argument> COLUMNS = new HashMap<String, Argument>();
	
	static
	{
		if(!Util.hasUtil())
			Util.doThrowNPE();
		final Util util = Util.giveUtil();
		final List<Argument> list = new ArrayList<Argument>();
		list.add(util.giveArgumentName());
		list.add(util.giveArgumentVer());
		list.add(util.giveArgumentAgain());
		list.add(new Argument(			"rank"						,	TypeData.STRING		));
		list.add(new Argument(			"level"						,	TypeData.INTEGER	));
		list.add(new Argument(			"perm_normal"				,	TypeData.STRING		));
		for(final ServerType type : ServerType.values())
			if(type.isSpecific())
				list.add(new Argument(	"perm_" + type.giveType()	,	TypeData.STRING		));
		for(final Argument column : list)
			PlayerRank.COLUMNS.put(column.giveName(), column);
	}
	
	private final Map<Player, PermissionAttachment> attachments;
	
	private PlayerRank(final OfflinePlayer player) throws SQLException
	{
		super(player);
		this.attachments = new HashMap<Player, PermissionAttachment>();
		if(!this.isRegistered())
		{
			final List<Value<?>> list = new ArrayList<Value<?>>();
			list.add(this.giveValueName());
			list.add(this.giveUtil().giveValueVer());
			list.add(this.giveUtil().giveValueAgain());
			list.add(new Value<String>(PlayerRank.COLUMNS.get("rank"), this.giveUtil().giveRankDefault().giveName()));
			list.add(new Value<Integer>(PlayerRank.COLUMNS.get("level"), 0));
			for(final String name : PlayerRank.COLUMNS.keySet())
				if(name.startsWith("perm_"))
					list.add(new Value<String>(PlayerRank.COLUMNS.get(name), ","));
			this.giveUtil().addSQL(PlayerRank.TABLE, list.toArray(new Value<?>[list.size()]));
		}
	}
	
	public final PlayerRank setRank(final Rank rank) throws SQLException
	{
		this.giveUtil().setSQL(PlayerRank.TABLE, this.giveClausesData(),
				this.giveUtil().giveValueVer(),
				this.giveUtil().giveValueAgain(this.giveUtil().giveContentValueAgain(this) + 1),
				new Value<String>(PlayerRank.COLUMNS.get("rank"), Util.nonNull(rank).giveName())
			);
		return this.isOnline() ? this.onLeave(this.givePlayer()).onJoin(this.givePlayer()) : this;
	}
	
	public final PlayerRank setLevel(final int level) throws SQLException
	{
		this.giveUtil().setSQL(PlayerRank.TABLE, this.giveClausesData(),
				this.giveUtil().giveValueVer(),
				this.giveUtil().giveValueAgain(this.giveUtil().giveContentValueAgain(this) + 1),
				new Value<Integer>(PlayerRank.COLUMNS.get("level"), level)
			);
		return this;
	}
	
	public final PlayerRank setEntitlement(final Entitlement entitlement) throws SQLException
	{
		return this.setLevel(Util.nonNull(entitlement).giveEntitlementLevel());
	}
	
	public final Rank getRank() throws SQLException
	{
		Rank rank = null;
		final Object obj = this.giveUtil().giveContentValue(this, PlayerRank.COLUMNS.get("rank"));
		rank = this.giveUtil().giveRank(String.valueOf(obj));
		if(rank == null)
			rank = this.giveUtil().giveRankDefault();
		return rank;
	}
	
	public final int getLevel() throws SQLException
	{
		int level = 0;
		final Object obj = this.giveUtil().giveContentValue(this, PlayerRank.COLUMNS.get("level"));
		if(obj != null)
			try
			{
				level = this.giveUtil().toInteger(obj);
			}
			catch(final NumberFormatException e) {}
		return level;
	}
	
	public final Entitlement getEntitlement() throws SQLException
	{
		return new Entitlement(this.getLevel());
	}
	
	public final PlayerRank addPermission(final String permission) throws SQLException
	{
		final Set<String> permissions = new HashSet<String>(this.givePermissions());
		Rank.addPermission(permission, permissions);
		this.doExportPerm(permissions);
		return this;
	}
	
	public final PlayerRank delPermission(final String permission) throws SQLException
	{
		final Set<String> permissions = new HashSet<String>(this.givePermissions());
		Rank.delPermission(permission, permissions);
		this.doExportPerm(permissions);
		return this;
	}
	
	public final Set<String> givePermissions() throws SQLException
	{
		final Set<String> set = new HashSet<String>();
		final Object obj = this.giveUtil().giveContentValue(this, this.giveArgumentPerm());
		if(obj != null)
			set.addAll(this.giveUtil().toList(String.valueOf(obj).split(",")));
		return Collections.unmodifiableSet(set);
	}
	
	public final Set<String> givePermissionsAll() throws SQLException
	{
		final Collection<String> permissionsPlayer = this.givePermissions(), permissionsRank = this.getRank().givePermissions();
		final Set<String> permissions = new HashSet<String>();
		for(final String permission : permissionsRank)
			if(permission.startsWith("-"))
			{
				if(!permissionsPlayer.contains(permission.substring(1)))
					permissions.add(permission);
			}
			else if(!permissionsPlayer.contains("-" + permission))
				permissions.add(permission);
		for(final String permission : permissionsPlayer)
			if(!permissions.contains(permission))
				permissions.add(permission);
		return Collections.unmodifiableSet(permissions);
	}
	
	protected final Argument giveArgumentPerm()
	{
		return PlayerRank.COLUMNS.get("perm_" + (this.giveUtil().isServerNormal() ? "normal" : this.giveUtil().giveServerType().giveType()));
	}
	
	protected final Map<Player, PermissionAttachment> giveAttachments()
	{
		return this.attachments;
	}
	
	public final PermissionAttachment giveAttachment(final Player player)
	{
		if(!this.giveAttachments().containsKey(Util.nonNull(player)))
			this.giveAttachments().put(player, player.addAttachment(this.giveUtil().giveUtilPlugin()));
		return this.giveAttachments().get(player);
	}
	
	public final PermissionAttachment giveAttachment()
	{
		if(this.isOnline())
			return this.giveAttachment(this.givePlayer());
		else
			return null;
	}

	@Override
	public final List<List<Value<?>>> giveContentTable(final ClausesData clauses, final Argument... columns) throws SQLException
	{
		return this.giveUtil().getSQL(PlayerRank.TABLE, clauses, columns);
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("attachments", this.giveAttachments());
		return super.giveProperties(map);
	}
	
	public final boolean hasPermission(final String permission) throws SQLException
	{
		return this.givePermissionsAll().contains(Util.nonEmpty(permission));
	}
	
	protected final PlayerRank doExportPerm(final Set<String> permissions) throws SQLException
	{
		String value = "";
		for(final String permission : permissions)
			if(!permission.isEmpty())
				value += permission + ",";
		this.giveUtil().setSQL(PlayerRank.TABLE, this.giveClausesData(),
				this.giveUtil().giveValueVer(),
				this.giveUtil().giveValueAgain(this.giveUtil().giveContentValueAgain(this) + 1),
				new Value<String>(this.giveArgumentPerm(), value.isEmpty() ? "," : value.substring(0, value.length() - 1))
			);
		return this;
	}
	
	public final PlayerRank onJoin(final Player player) throws SQLException
	{
		final PermissionAttachment attachment = this.giveAttachment(player);
		for(String permission : this.givePermissionsAll())
		{
			final boolean negation = permission.startsWith("-");
			if(negation)
				permission = permission.substring(1);
			attachment.setPermission(permission, !negation);
		}
		this.getRank().giveScoreboardTeam((team) ->
		{
			final Team teamOriginal = this.giveUtil().giveUtilScoreboard().getEntryTeam(player.getName());
			if(teamOriginal != null)
				this.giveUtil().doAdjustTeam(team, teamOriginal);
		}).addEntry(player.getName());
		return this;
	}
	
	public final PlayerRank onLeave(final Player player) throws SQLException
	{
		if(this.giveAttachments().containsKey(Util.nonNull(player)))
		{
			player.removeAttachment(this.giveAttachments().get(player));
			this.giveAttachments().remove(player);
		}
		this.getRank().giveScoreboardTeam().removeEntry(player.getName());
		return this;
	}
	
	public static final PlayerRank givePlayerRank(final OfflinePlayer player) throws SQLException
	{
		final PlayerRhenowar playerRhenowar = PlayerRhenowar.givePlayerRhenowar(player);
		final List<PlayerRank> list = playerRhenowar.giveRhenowarPlayers(PlayerRank.class);
		if(list.size() > 0)
			return list.get(0);
		else
		{
			playerRhenowar.addRhenowarPlayer(new PlayerRank(player));
			return PlayerRank.givePlayerRank(player);
		}
				
	}
	
}
