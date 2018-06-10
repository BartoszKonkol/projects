package net.polishgames.rhenowar.util;

import java.sql.SQLException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import net.polishgames.rhenowar.util.auth.PlayerAuth;
import net.polishgames.rhenowar.util.minigame.Action;
import net.polishgames.rhenowar.util.minigame.ActionCom;
import net.polishgames.rhenowar.util.minigame.ActionHandler;
import net.polishgames.rhenowar.util.minigame.ActionInv;
import net.polishgames.rhenowar.util.minigame.ItemAction;
import net.polishgames.rhenowar.util.minigame.RhenowarPluginMinigame;
import net.polishgames.rhenowar.util.minigame.event.PlayerActionBackEvent;

public final class Actions extends AbstractActions
{

	public Actions(final RhenowarPlugin plugin)
	{
		super(plugin);
	}
	
	@ActionHandler("back")
	public Action<?> onBack(final Util util, final Player player, final ItemStack item, final InventoryView openInventory, final String action)
	{
		final PlayerActionBackEvent event = new PlayerActionBackEvent(player, item, openInventory, action);
		util.givePluginManager().callEvent(event);
		if(event.hasAction())
			return event.getAction();
		else
			return this.giveActionEnd(action, player);
	}
	
	@ActionHandler("SelectionGames")
	public Action<?> onSelectionGames(final Util util, final Player player, final String action)
	{
		final Inventory inventory = this.giveInventory("games", player, util);
		final ItemStack glass = util.giveItemMinigame("glass", player);
		if(glass != null)
		{
			for(int i = 0; i < 10; i++)
			{
				inventory.setItem(i, glass);
				inventory.setItem(i + 26, glass);
			}
			for(int i = 17; i < 19; i++)
				inventory.setItem(i, glass);
		}
		for(final Plugin plugin : util.givePlugins())
			if(plugin instanceof RhenowarPluginMinigame)
			{
				final RhenowarPluginMinigame minigame = (RhenowarPluginMinigame) plugin;
				final ItemAction itemAction = minigame.giveMinigameItem();
				if(itemAction != null)
				{
					ItemStack item = null;
					try
					{
						item = util.giveItemCraft(itemAction.giveItemStack());
					}
					catch(final ReflectiveOperationException e)
					{
						e.printStackTrace();
					}
					if(item != null)
					{
						try
						{
							final Object tag = util.giveTagNBT(item);
							util.setObjectNBT(tag, "action", itemAction.giveAction());
							util.setObjectNBT(tag, "minigame", minigame.giveName());
						}
						catch(final ReflectiveOperationException e)
						{
							e.printStackTrace();
						}
						inventory.addItem(item);
					}
				}
			}
		return new ActionInv(action, inventory, player);
	}
	
	@ActionHandler("ChangeLanguage")
	public Action<?> onChangeLanguage(final Util util, final Player player, final String action)
	{
		final Inventory inventory = this.giveInventory("account.change.language", player, util);
		final ItemStack back = util.giveItemBack(player), clayRed = util.giveItemMinigame("clayRed", player), clayGreen = util.giveItemMinigame("clayGreen", player);
		if(back != null)
			inventory.setItem(31, back);
		final Language languagePlayer = util.giveLanguage(player);
		final Language[] languages = Language.values();
		for(int i = 0; i < Math.min(9, languages.length); i++)
		{
			final Language language = languages[i];
			final ItemStack item = language.giveItem();
			if(item != null)
			{
				inventory.setItem(i, language == languagePlayer ? clayGreen : clayRed);
				inventory.setItem(i + 9, item);
			}
		}
		return new ActionInv(action, inventory, player);
	}
	
	@ActionHandler("ChangeLanguageAssign")
	public Action<?> onChangeLanguageAssign(final Util util, final Player player, final ItemStack item, final String action)
	{
		Language language = null;
		try
		{
			language = Language.giveValue(util.getStringNBT(util.giveTagNBT(item), "lang"));
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
		}
		if(language != null)
			try
			{
				PlayerAccount.givePlayerAccount(player).setLanguage(language);
			}
			catch(final SQLException e)
			{
				util.doReportErrorSQL(e);
				return new ActionCom(action, this.giveMessage("ErrorSQL", player, util), player);
			}
		return this.onChangeLanguage(util, player, action);
	}
	
	@ActionHandler("ChangePassword")
	public Action<?> onChangePassword(final Util util, final Player player, final PlayerInventory playerInventory, final InventoryView openInventory, final String action)
	{
		try
		{
			final PlayerAuth auth = PlayerAuth.givePlayerAuth(player);
			util.doTeleportToLobby(player);
			playerInventory.clear();
			playerInventory.setHeldItemSlot(4);
			openInventory.close();
			auth.doChangeInduce();
		}
		catch(final SQLException e)
		{
			util.doKick(player, this.giveMessage("ErrorSQL", player, util));
			util.doReportErrorSQL(e);
		}
		return this.giveActionEnd(action, player);
	}
	
	protected final String giveMessage(final String key, final Player player, final Util util)
	{
		return Util.nonNull(util).giveMessage(util.giveUtilConfigPrefix() + "message." + Util.nonEmpty(key), Util.nonNull(player));
	}
	
	protected final Inventory giveInventory(final String name, final Player player, final Util util)
	{
		return Util.nonNull(util).giveInventory(this.giveMessage("minigame." + Util.nonEmpty(name), player, util), 4);
	}
	
}
