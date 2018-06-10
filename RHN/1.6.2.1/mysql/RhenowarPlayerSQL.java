package net.polishgames.rhenowar.util.mysql;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import net.polishgames.rhenowar.util.RhenowarPlayer;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.mysql.ClausesData.Clause.ValuesData;

public abstract class RhenowarPlayerSQL extends RhenowarPlayer
{
	
	private final Util util;

	protected RhenowarPlayerSQL(final OfflinePlayer player) throws SQLException
	{
		super(player);
		if(Util.hasUtil())
		{
			this.util = Util.giveUtil();
			this.registered = this.giveUtil().hasSQL(this.giveContentTable());
		}
		else
		{
			Util.doThrowNPE();
			this.util = null;
		}
	}
	
	protected boolean registered;
	
	public final Util giveUtil()
	{
		return this.util;
	}
	
	public final String giveName()
	{
		return this.giveUtil().givePlayerName(this.giveOfflinePlayer());
	}
	
	public final Value<String> giveValueName()
	{
		return this.giveUtil().giveValueName(this.giveName());
	}
	
	public final ValuesData giveValuesDataName()
	{
		return new ValuesData(this.giveValueName());
	}
	
	public final ClausesData giveClausesData()
	{
		return ClausesData.giveClausesData().setLimit(1).setWhere(this.giveValuesDataName());
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.giveUtil());
		map.put("name", this.giveName());
		map.put("registered", this.isRegistered());
		return super.giveProperties(map);
	}
	
	public final boolean isRegistered()
	{
		return this.registered;
	}
	
	public final List<List<Value<?>>> giveContentTable(final Argument... columns) throws SQLException
	{
		return this.giveContentTable(this.giveClausesData(), this.giveUtil().giveCombinedArray(new Argument[]{this.giveUtil().giveArgumentName()}, Util.nonNull(columns)));
	}
	
	public abstract List<List<Value<?>>> giveContentTable(final ClausesData clauses, final Argument... columns) throws SQLException;
	
}
