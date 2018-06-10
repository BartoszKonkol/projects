package net.polishgames.rhenowar.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import net.polishgames.rhenowar.util.mysql.Argument;
import net.polishgames.rhenowar.util.mysql.ClausesData;
import net.polishgames.rhenowar.util.mysql.Table;
import net.polishgames.rhenowar.util.mysql.TypeData;
import net.polishgames.rhenowar.util.mysql.Value;
import net.polishgames.rhenowar.util.mysql.RhenowarPlayerSQL;

public final class PlayerAccount extends RhenowarPlayerSQL
{
	
	public static final Table TABLE = new Table("user");
	public static final Map<String, Argument> COLUMNS = new HashMap<String, Argument>();
	
	static
	{
		if(!Util.hasUtil())
			Util.doThrowNPE();
		final Util util = Util.giveUtil();
		for(final Argument column : new Argument[]
				{
					util.giveArgumentName(), util.giveArgumentVer(), util.giveArgumentAgain(),
					new Argument(	"nick"				,	TypeData.STRING		),
					new Argument(	"uuid"				,	TypeData.STRING		),
					new Argument(	"money"				,	TypeData.INTEGER	),
					new Argument(	"lang"				,	TypeData.STRING		),
					new Argument(	"email"				,	TypeData.STRING		),
					new Argument(	"phone"				,	TypeData.INTEGER	),
				})
			PlayerAccount.COLUMNS.put(column.giveName(), column);
	}

	private PlayerAccount(final OfflinePlayer player) throws SQLException
	{
		super(player);
		if(!this.isRegistered())
			this.giveUtil().addSQL(PlayerAccount.TABLE,
					this.giveValueName(),
					this.giveUtil().giveValueVer(),
					this.giveUtil().giveValueAgain(),
					new Value<String>(PlayerAccount.COLUMNS.get("nick"), this.giveNick()),
					new Value<String>(PlayerAccount.COLUMNS.get("uuid"), this.giveUUID().toString()),
					new Value<Integer>(PlayerAccount.COLUMNS.get("money"), 0),
					new Value<String>(PlayerAccount.COLUMNS.get("lang"), Language.giveValue(player).giveCode())
				);
	}
	
	public final String giveNick()
	{
		return this.giveOfflinePlayer().getName();
	}
	
	public final UUID giveUUID()
	{
		return this.giveOfflinePlayer().getUniqueId();
	}
	
	public final PlayerAccount setBalance(final int amount) throws SQLException
	{
		this.giveUtil().setSQL(PlayerAccount.TABLE, this.giveClausesData(),
				this.giveUtil().giveValueVer(),
				this.giveUtil().giveValueAgain(this.giveUtil().giveContentValueAgain(this) + 1),
				new Value<Integer>(PlayerAccount.COLUMNS.get("money"), amount)
			);
		return this;
	}
	
	public final Integer getBalance() throws SQLException
	{
		final Object obj = this.giveUtil().giveContentValue(this, PlayerAccount.COLUMNS.get("money"));
		if(obj != null)
			return this.giveUtil().toInteger(obj);
		else
			return null;
	}
	
	public final PlayerAccount setLanguage(final Language language) throws SQLException
	{
		this.giveUtil().setSQL(PlayerAccount.TABLE, this.giveClausesData(),
				this.giveUtil().giveValueVer(),
				this.giveUtil().giveValueAgain(this.giveUtil().giveContentValueAgain(this) + 1),
				new Value<String>(PlayerAccount.COLUMNS.get("lang"), Util.nonNull(language).giveCode())
			);
		return this;
	}
	
	public final Language getLanguage() throws SQLException
	{
		Language result = null;
		final Object obj = this.giveUtil().giveContentValue(this, PlayerAccount.COLUMNS.get("lang"));
		result = Language.giveValue(String.valueOf(obj));
		if(result == null)
			result = Language.giveValue(this.giveOfflinePlayer());
		return result;
	}
	
	@Override
	public final List<List<Value<?>>> giveContentTable(final ClausesData clauses, final Argument... columns) throws SQLException
	{
		return this.giveUtil().getSQL(PlayerAccount.TABLE, clauses, columns);
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("nick", this.giveNick());
		map.put("uuid", this.giveUUID());
		return super.giveProperties(map);
	}
	
	public static final PlayerAccount givePlayerAccount(final OfflinePlayer player) throws SQLException
	{
		final PlayerRhenowar playerRhenowar = PlayerRhenowar.givePlayerRhenowar(player);
		final List<PlayerAccount> list = playerRhenowar.giveRhenowarPlayers(PlayerAccount.class);
		if(list.size() > 0)
			return list.get(0);
		else
		{
			playerRhenowar.addRhenowarPlayer(new PlayerAccount(player));
			return PlayerAccount.givePlayerAccount(player);
		}
				
	}
	
}
