package net.polishgames.rhenowar.util;

import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import net.milkbowl.vault.permission.Permission;

public final class VaultSupport extends RhenowarObject
{
	
	private final RhenowarPlugin plugin;
	private final Permission permission;
	private final Economy economy;
	private final Chat chat;
	
	public VaultSupport(final RhenowarPlugin plugin)
	{
		this.plugin = Util.nonNull(plugin);
		if(Util.hasUtil())
		{
			final ServicesManager manager = Util.giveUtil().giveServicesManager();
			final RegisteredServiceProvider<Permission> servicePermission = manager.getRegistration(Permission.class);
			final RegisteredServiceProvider<Economy> serviceEconomy = manager.getRegistration(Economy.class);
			final RegisteredServiceProvider<Chat> serviceChat = manager.getRegistration(Chat.class);
			if(servicePermission != null)
				this.permission = servicePermission.getProvider();
			else
				this.permission = null;
			if(serviceEconomy != null)
				this.economy = serviceEconomy.getProvider();
			else
				this.economy = null;
			if(serviceChat != null)
				this.chat = serviceChat.getProvider();
			else
				this.chat = null;
		}
		else
		{
			this.permission = null;
			this.economy = null;
			this.chat = null;
		}
	}
	
	public final RhenowarPlugin givePlugin()
	{
		return this.plugin;
	}
	
	public final Permission givePermission()
	{
		return this.permission;
	}
	
	public final Economy giveEconomy()
	{
		return this.economy;
	}
	
	public final Chat giveChat()
	{
		return this.chat;
	}
	
	public final boolean hasPermission()
	{
		return this.givePermission() != null;
	}
	
	public final boolean hasEconomy()
	{
		return this.giveEconomy() != null;
	}
	
	public final boolean hasChat()
	{
		return this.giveChat() != null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("permission", this.givePermission());
		map.put("economy", this.giveEconomy());
		map.put("chat", this.giveChat());
		return map;
	}
	
	public final boolean addBalance(final OfflinePlayer player, final double amount)
	{
		if(this.hasEconomy())
			return this.giveEconomy().depositPlayer(Util.nonNull(player), Math.abs(amount)).type == ResponseType.SUCCESS ? true : false;
		else
			return false;
	}
	
	public final boolean delBalance(final OfflinePlayer player, final double amount)
	{
		if(this.hasEconomy())
			return this.giveEconomy().withdrawPlayer(Util.nonNull(player), Math.abs(amount)).type == ResponseType.SUCCESS ? true : false;
		else
			return false;
	}
	
	public final Double getBalance(final OfflinePlayer player)
	{
		if(this.hasEconomy())
			return this.giveEconomy().getBalance(Util.nonNull(player));
		else
			return null;
	}
	
	public final boolean setBalance(final OfflinePlayer player, final double amount)
	{
		final Double balance = this.getBalance(Util.nonNull(player));
		if(balance != null && this.delBalance(player, balance))
			return this.addBalance(player, amount);
		else
			return false;
	}
	
}
