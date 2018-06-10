package net.polishgames.rhenowar.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class Balance extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.6.1.1");
	
	protected static final Map<String, Balance> balances = new HashMap<String, Balance>();
	
	private final String name;
	private final TypeBalance type;
	
	private Balance(final String name, final TypeBalance type)
	{
		this.name = Util.nonEmpty(name);
		this.type = Util.nonNull(type);
	}
	
	private int money;
	
	public final Balance add(final int amount)
	{
		switch(this.giveType())
		{
			case PLAYER:
			{
				final OfflinePlayer player = this.givePlayer();
				if(player != null)
				{
					if(Util.hasUtil())
						return Util.giveUtil().addMoney(player, amount) != null ? this : null;
					else
						return this.set(this.get() + amount);
				}
				else
					return null;
			}
			case ACCOUNT:
			{
				this.money += amount;
				return this;
			}
		}
		return null;
	}
	
	public final Balance del(final int amount)
	{
		switch(this.giveType())
		{
			case PLAYER:
			{
				final OfflinePlayer player = this.givePlayer();
				if(player != null)
				{
					if(Util.hasUtil())
						return Util.giveUtil().delMoney(player, amount) != null ? this : null;
					else
						return this.set(this.get() - amount);
				}
				else
					return null;
			}
			case ACCOUNT:
			{
				this.money -= amount;
				return this;
			}
		}
		return null;
	}
	
	public final Balance set(final int amount)
	{
		switch(this.giveType())
		{
			case PLAYER:
			{
				final OfflinePlayer player = this.givePlayer();
				if(player != null)
				{
					if(Util.hasUtil())
						return Util.giveUtil().setMoney(player, amount) != null ? this : null;
					else
						try
						{
							return PlayerAccount.givePlayerAccount(player).setBalance(amount) != null ? this : null;
						}
						catch(final SQLException e)
						{
							e.printStackTrace();
							return null;
						}
				}
				else
					return null;
			}
			case ACCOUNT:
			{
				this.money = amount;
				return this;
			}
		}
		return null;
	}
	
	public final int get()
	{
		switch(this.giveType())
		{
			case PLAYER:
			{
				final OfflinePlayer player = this.givePlayer();
				if(player != null)
				{
					if(Util.hasUtil())
						return Util.giveUtil().getMoney(player);
					else
						try
						{
							return PlayerAccount.givePlayerAccount(player).getBalance();
						}
						catch(final SQLException e)
						{
							e.printStackTrace();
							return 0;
						}
				}
				else
					return 0;
			}
			case ACCOUNT:
				return this.give();
		}
		return 0;
	}
	
	public final int give()
	{
		return this.money;
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final TypeBalance giveType()
	{
		return this.type;
	}
	
	public final IntConsumer giveConsumerAdd()
	{
		return this::add;
	}
	
	public final IntFunction<Balance> giveFunctionAdd()
	{
		return this::add;
	}
	
	public final IntConsumer giveConsumerDel()
	{
		return this::del;
	}
	
	public final IntFunction<Balance> giveFunctionDel()
	{
		return this::del;
	}
	
	public final IntConsumer giveConsumerSet()
	{
		return this::set;
	}
	
	public final IntFunction<Balance> giveFunctionSet()
	{
		return this::set;
	}
	
	public final IntSupplier giveSupplierGet()
	{
		return this::get;
	}
	
	@SuppressWarnings("deprecation")
	protected final OfflinePlayer givePlayer()
	{
		if(this.giveType() == TypeBalance.PLAYER)
		{
			if(Util.hasUtil())
				return Util.giveUtil().givePlayerOffline(this.giveName());
			else
				return Bukkit.getOfflinePlayer(this.giveName());
		}
		else
			return null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveName());
		map.put("type", this.giveType());
		map.put("money", this.give());
		return map;
	}

	@Override
	public Balance clone()
	{
		final Balance balance = new Balance(this.giveName(), this.giveType());
		balance.money = this.give();
		return balance;
	}
	
	private static final synchronized Balance giveBalance(final String name, final TypeBalance type)
	{
		final String key = Util.nonNull(type) + ":" + Util.nonEmpty(name);
		if(!Balance.balances.containsKey(key))
			Balance.balances.put(key, new Balance(name, type));
		return Balance.balances.get(key);
	}
	
	public static final synchronized Balance giveBalance(final OfflinePlayer player)
	{
		return Balance.giveBalance(player.getName(), TypeBalance.PLAYER);
	}
	
	public static final synchronized Balance giveBalance(final String name)
	{
		return Balance.giveBalance(name, TypeBalance.ACCOUNT);
	}
	
	public static enum TypeBalance implements Rhenowar
	{
		
		PLAYER,
		ACCOUNT,
		;

		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("type", this.name());
			return map;
		}
		
		@Override
		public String toString()
		{
			if(Util.hasUtil())
				return Util.giveUtil().toString(this);
			else
				return this.name().toLowerCase();
		}
		
	}
	
}
