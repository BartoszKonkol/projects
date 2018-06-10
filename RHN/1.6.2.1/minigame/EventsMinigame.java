package net.polishgames.rhenowar.util.minigame;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import net.polishgames.rhenowar.util.AbstractEvents;
import net.polishgames.rhenowar.util.Language;
import net.polishgames.rhenowar.util.ProtocolVersion;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.RhenowarPlayer;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.WorldData;
import net.polishgames.rhenowar.util.auth.event.PlayerAuthEvent;

public final class EventsMinigame extends AbstractEvents
{
	
	protected final Map<Language, ItemStack[]> hand;

	public EventsMinigame(final RhenowarPlugin plugin)
	{
		super(plugin);
		final int length = 10;
		this.hand = new HashMap<Language, ItemStack[]>();
		for(final Language language : Language.values())
			this.hand.put(language, new ItemStack[length]);
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(util.hasUtilMinigame())
			{
				final UtilMinigameSupport utilMinigame = util.giveUtilMinigameSupport();
				final String hand = "hand";
				try
				{
					final Object script = utilMinigame.giveScript(util.giveUtilLuaInventoryFile(), "!", hand);
					for(int i = 0; i < length; i++)
						for(final Language language : this.hand.keySet())
						{
							final ItemAction item = utilMinigame.giveItem(script, hand, i, language);
							if(item != null)
								this.hand.get(language)[i] = item.giveItemStack();
						}
				}
				catch(final ReflectiveOperationException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerAuth(final PlayerAuthEvent event)
	{
		switch(event.giveStage())
		{
			case LOGIN_COMPLETED:
			case REGISTER_COMPLETED:
			case CHANGE_COMPLETED:
				this.doAdjustInventory(event);
			default:
				break;
		}
	}
	
	@EventHandler
	public void onPlayerChangedWorld(final PlayerChangedWorldEvent event)
	{
		this.doAdjustInventory(event);
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerInteract(final PlayerInteractEvent event)
	{
		try
		{
			if(this.doReactClickItem(event.getPlayer(), event.getItem(), event))
				event.setUseItemInHand(Result.DENY);
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInventoryClick(final InventoryClickEvent event)
	{
		final HumanEntity entity = event.getWhoClicked();
		if(entity instanceof Player && ((Util.hasUtil() && Util.giveUtil().giveServerVersion().giveVersion() <= ProtocolVersion.giveValue("1.10.2").giveVersion()) || event.getClick() != ClickType.CREATIVE))
			try
			{
				this.doReactClickItem((Player) entity, event.getCurrentItem(), event);
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
	}
	
	@EventHandler
	public void onWorldLoad(final WorldInitEvent event)
	{
		final World world = event.getWorld();
		final String name = world.getName();
		if(name.startsWith("game#") && name.length() >= 0xE)
		{
			final WorldData data = WorldData.giveWorldData(world);
			if(data.getMemory() || world.getKeepSpawnInMemory())
			{
				data.setMemory(false);
				world.setKeepSpawnInMemory(false);
			}
		}
	}
	
	protected final void doAdjustInventory(final PlayerEvent event)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = Util.nonNull(event).getPlayer();
			if(util.isPlayerInLobby(player))
			{
				final ItemStack[] items = this.hand.get(util.giveLanguage(player));
				final PlayerInventory inventory = player.getInventory();
				for(int i = 0; i < items.length; i++)
				{
					final ItemStack item = items[i];
					if(item != null)
					{
						if(i == 0)
							inventory.setItemInOffHand(item);
						else
							inventory.setItem(i - 1, item);
					}
					else if(i != 0)
						inventory.clear(i - 1);
				}
			}
		}
	}
	
	protected final boolean doReactClickItem(final Player player, final ItemStack item, final Cancellable event) throws ReflectiveOperationException
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(util.isPlayerInLobby(Util.nonNull(player)) && item != null && item.getType() != Material.AIR)
			{
				Action<?> action = null;
				String actionName = null, scriptName = null, minigameName = null;
				try
				{
					final Object tag = util.giveTagNBT(item);
					actionName = util.getStringNBT(tag, "action");
					scriptName = util.getStringNBT(tag, "script");
					minigameName = util.getStringNBT(tag, "minigame");
				}
				catch(final ReflectiveOperationException e)
				{
					e.printStackTrace();
				}
				if(minigameName != null)
				{
					minigameName = minigameName.trim();
					if(!minigameName.isEmpty())
					{
						final Plugin plugin = util.givePlugin(minigameName);
						if(plugin != null && plugin instanceof RhenowarPluginMinigame)
							action = ((RhenowarPluginMinigame) plugin).giveMinigameAction();
					}
				}
				if(action == null && actionName != null)
				{
					actionName = actionName.trim();
					if(!actionName.isEmpty())
					{
						final PlayerMinigame playerMinigame = PlayerMinigame.givePlayerMinigame(player);
						final UtilMinigameSupport utilMinigame = util.giveUtilMinigameSupport();
						if(scriptName != null)
						{
							scriptName = scriptName.trim();
							if(util.hasUtilMinigame() && !scriptName.isEmpty())
								try
								{
									action = utilMinigame.giveAction(scriptName.equals("!") ? playerMinigame.giveUtilScript() : playerMinigame.giveScript(scriptName), actionName);
								}
								catch(final ReflectiveOperationException e)
								{
									e.printStackTrace();
								}
						}
						if(action == null)
						{
							actionName = actionName.trim();
							if(!actionName.isEmpty())
								loop:for(final Rhenowar listener : util.giveActionListeners())
								{
									final Class<? extends Rhenowar> listenerClass = listener.getClass();
									final Method[] methods = listenerClass.getDeclaredMethods();
									for(final Method method : methods)
									{
										final Class<ActionHandler> handlerClass = ActionHandler.class;
										if(util.isAnnotation(method, handlerClass))
										{
											final ActionHandler handler = method.getAnnotation(handlerClass);
											if(handler != null && actionName.equals(Util.nonEmpty(handler.value())))
											{
												final List<Object> parameters = new ArrayList<Object>();
												for(final Class<?> type : method.getParameterTypes())
													if(Util.class.isAssignableFrom(type))
														parameters.add(util);
													else if(UtilMinigame.class.isAssignableFrom(type))
														parameters.add(util.getUtilMinigame());
													else if(UtilMinigameSupport.class.isAssignableFrom(type))
														parameters.add(utilMinigame);
													else if(ActionHandler.class.isAssignableFrom(type))
														parameters.add(handler);
													else if(Method.class.isAssignableFrom(type))
														parameters.add(method);
													else if(String.class.isAssignableFrom(type))
														parameters.add(actionName);
													else if(ItemStack.class.isAssignableFrom(type))
														parameters.add(item);
													else if(ItemMeta.class.isAssignableFrom(type))
														parameters.add(item.getItemMeta());
													else if(Material.class.isAssignableFrom(type))
														parameters.add(item.getType());
													else if(RhenowarPlayer.class.isAssignableFrom(type))
														parameters.add(playerMinigame);
													else if(Player.class.isAssignableFrom(type))
														parameters.add(playerMinigame.givePlayer());
													else if(OfflinePlayer.class.isAssignableFrom(type))
														parameters.add(playerMinigame.giveOfflinePlayer());
													else if(Inventory.class.isAssignableFrom(type))
														parameters.add(player.getInventory());
													else if(InventoryView.class.isAssignableFrom(type))
														parameters.add(player.getOpenInventory());
													else if(listenerClass.isAssignableFrom(type))
														parameters.add(listener);
													else if(type.isPrimitive())
														switch(type.getSimpleName())
														{
															case "byte": parameters.add((byte) 0); break;
															case "short": parameters.add((short) 0); break;
															case "int": parameters.add((int) 0); break;
															case "long": parameters.add((long) 0L); break;
															case "float": parameters.add((float) 0.0F); break;
															case "double": parameters.add((double) 0.0D); break;
															case "boolean": parameters.add((boolean) false); break;
															case "char": parameters.add((char) '\u0000'); break;
														}
													else
														parameters.add(null);
												final Object result = method.invoke(listener, parameters.toArray());
												if(result != null && result instanceof Action)
												{
													action = (Action<?>) result;
													break loop;
												}
											}
										}
									}
								}
						}
					}
				}
				if(action != null)
				{
					boolean close = false;
					switch(action.giveType())
					{
						case INV:
							if(action instanceof ActionInv)
								player.openInventory(((ActionInv) action).giveInventory());
							break;
						case COM:
							if(action instanceof ActionCom)
								util.doSend(player, ((ActionCom) action).giveMessage());
							close = true;
							break;
						case END:
							break;
					}
					if(close)
						player.closeInventory();
					Util.nonNull(event).setCancelled(true);
					return true;
				}
			}
		}
		return false;
	}
	
}
