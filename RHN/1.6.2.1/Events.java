package net.polishgames.rhenowar.util;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;
import net.polishgames.rhenowar.util.event.ISignEvent;
import net.polishgames.rhenowar.util.event.PlayerFallEvent;
import net.polishgames.rhenowar.util.event.PlayerLeaveEvent;
import net.polishgames.rhenowar.util.event.PlayerLeaveEvent.TypeLeave;
import net.polishgames.rhenowar.util.event.PlayerSignClickEvent;
import net.polishgames.rhenowar.util.event.PlayerSignSendEvent;
import net.polishgames.rhenowar.util.event.PlayerSignTeleportEvent;
import net.polishgames.rhenowar.util.event.TimerEvent;
import net.polishgames.rhenowar.util.minigame.PlayerMinigame;
import net.polishgames.rhenowar.util.minigame.UtilMinigameSupport;
import net.polishgames.rhenowar.util.minigame.event.PlayerActionBackEvent;
import net.polishgames.rhenowar.util.task.SyncRequestSignSendTask;
import net.polishgames.rhenowar.util.task.SyncTeleportTask;

public final class Events extends AbstractEvents
{
	
	protected final SyncRequestSignSendTask taskRequestSignSend;
	
	public Events(final RhenowarPlugin plugin)
	{
		super(plugin);
		this.taskRequestSignSend = new SyncRequestSignSendTask();
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("taskRequestSignSend", this.taskRequestSignSend);
		return super.giveProperties(map);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(final PlayerInteractEvent event)
	{
		final Action action = event.getAction();
		if(event.hasBlock() && Util.hasUtil() && !(event instanceof PlayerSignClickEvent) && (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK))
		{
			final BlockState state = event.getClickedBlock().getState();
			if(state instanceof Sign)
			{
				final PlayerSignClickEvent eventSignClick = new PlayerSignClickEvent(event.getPlayer(), action, event.getItem(), (Sign) state, event.getBlockFace());
				Util.giveUtil().givePluginManager().callEvent(eventSignClick);
				event.setCancelled(eventSignClick.isCancelled());
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerSignClick(final PlayerSignClickEvent event)
	{
		if(this.isSignTP(event) && event.isRightClick())
		{
			final Sign sign = event.giveSign();
			final String name = sign.getLine(1);
			int maxPlayers = -1;
			try
			{
				maxPlayers = Integer.valueOf(sign.getLine(2));
			}
			catch(final NumberFormatException e) {}
			final Util util = Util.giveUtil();
			final Configuration configLocations = util.giveUtilConfigLocations();
			final String prefix = util.giveUtilConfigPrefix() + "SignTP." + name + ".";
			if(configLocations.isString(prefix + "W"))
			{
				final Location location = util.getLocation(configLocations, prefix);
				final World world = location.getWorld();
				final Player player = event.getPlayer();
				boolean teleport = false;
				final StateSignTP state = this.giveStateSignTP(world, maxPlayers);
				switch(state)
				{
					case ONLYVIP:
						teleport = util.isPlayerRank(player, "vip");
						break;
					case NONFULL:
						teleport = true;
					default:
						break;
				}
				if(teleport)
				{
					final PlayerSignTeleportEvent eventSignTeleport = new PlayerSignTeleportEvent(player, player.getLocation(), location, sign);
					util.givePluginManager().callEvent(eventSignTeleport);
					if(!eventSignTeleport.isCancelled())
						new SyncTeleportTask(player, eventSignTeleport.getTo()).runTaskLater(2L);
				}
				else
				{
					final String stateName = state.name().toLowerCase();
					util.doUtilSendMessage(player, util.giveMessage(util.giveUtilConfigPrefix() + "message.SignTP." + stateName, player).replace("%state%", util.giveMessage(util.giveUtilConfigPrefix() + "SignTP.state." + stateName, player)));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerSignSend(final PlayerSignSendEvent event)
	{
		if(this.isSignTP(event))
		{
			final Sign sign = event.giveSign();
			final String name = sign.getLine(1), description = sign.getLine(3);
			int maxPlayers = -1;
			try
			{
				maxPlayers = Integer.valueOf(sign.getLine(2));
			}
			catch(final NumberFormatException e) {}
			final Util util = Util.giveUtil();
			final Configuration configLocations = util.giveUtilConfigLocations();
			final String prefix = util.giveUtilConfigPrefix() + "SignTP." + name + ".";
			if(configLocations.isString(prefix + "W"))
			{
				final Player player = event.getPlayer();
				final Location location = util.getLocation(configLocations, prefix);
				final World world = location.getWorld();
				final int currentPlayers = this.giveCurrentPlayers(world);
				for(int i = 1; i <= event.giveLines().length; i++)
					event.setLine(i, util.giveMessage(util.giveUtilConfigPrefix() + "SignTP.line." + String.valueOf(i), player)
						.replace("%name%", name)
						.replace("%description%", description)
						.replace("%world%", world.getName())
						.replace("%state%", util.giveMessage(util.giveUtilConfigPrefix() + "SignTP.state." + this.giveStateSignTP(currentPlayers, maxPlayers).name().toLowerCase(), player))
						.replace("%currentplayers%", String.valueOf(currentPlayers))
						.replace("%maxplayers%", maxPlayers < 0 ? "-" : maxPlayers == 0 ? "\u221e" : String.valueOf(maxPlayers))
							);
			}
			else
			{
				final String separator = "---------------";
				event.setLine(1, separator);
				event.setLine(2, ChatColor.DARK_RED + "ERROR!");
				event.setLine(3, name);
				event.setLine(4, separator);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent event)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = event.getPlayer();
			final Configuration config = util.giveUtilConfig();
			if(util.isPlayerInLobby(player) && config.getStringList(util.giveUtilConfigPrefix() + "improve.lobby.list.black").contains(player.getName()))
				util.doKick(player, util.giveMessage(util.giveUtilConfigPrefix() + "message.PlayerInBlacklist", player) + '\n' + String.format(util.giveMessage(util.giveUtilConfigPrefix() + "message.WorldIsLocked", player), util.giveLobby().getName()));
		}
	}
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			try
			{
				PlayerAccount.givePlayerAccount(player);
			}
			catch(final SQLException e)
			{
				util.doUtilSendMessage(player, util.giveMessage(util.giveUtilConfigPrefix() + "message.ErrorSQL", player));
				util.doReportErrorSQL(e);
			}
			this.doReorganizePlayer(util, player);
			util.doTeleportToLobby(player);
			this.taskRequestSignSend.setWorlds(player.getWorld()).doRequestSignSend();
			final Team teamOriginal = player.getScoreboard().getEntryTeam(player.getName());
			final Scoreboard scoreboard = util.giveUtilScoreboard();
			player.setScoreboard(scoreboard);
			util.giveScoreboardTeam(util.giveUtilName(), "collision", team ->
			{
				if(teamOriginal != null)
					util.doAdjustTeam(team, teamOriginal);
				team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
			}).addEntry(player.getName());
		}
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(2 << 9);
		player.saveData();
		event.setJoinMessage(null);
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerQuit(final PlayerQuitEvent event)
	{
		if(Util.hasUtil())
		{
			final PlayerLeaveEvent eventLeave = new PlayerLeaveEvent(event.getPlayer(), TypeLeave.QUIT, event.getQuitMessage());
			Util.giveUtil().givePluginManager().callEvent(eventLeave);
			event.setQuitMessage(eventLeave.getMessage());
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerKick(final PlayerKickEvent event)
	{
		if(Util.hasUtil())
		{
			final PlayerLeaveEvent eventLeave = new PlayerLeaveEvent(event.getPlayer(), TypeLeave.KICK, event.getLeaveMessage(), event.getReason());
			Util.giveUtil().givePluginManager().callEvent(eventLeave);
			event.setCancelled(eventLeave.getCancelled());
			event.setLeaveMessage(eventLeave.getMessage());
			event.setReason(eventLeave.getReason());
		}
	}
	
	@EventHandler
	public void onPlayerLeave(final PlayerLeaveEvent event)
	{
		final Player player = event.getPlayer();
		this.taskRequestSignSend.setWorlds(player.getWorld()).doRequestSignSend();
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			util.giveScoreboardTeam(util.giveUtilName(), "collision").removeEntry(player.getName());
			player.setScoreboard(util.giveMainScoreboard());
		}
		event.setMessage(null);
	}
	
	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent event)
	{
		this.onBlock(event, event.getPlayer());
	}
	
	@EventHandler
	public void onSignChange(final SignChangeEvent event)
	{
		this.onBlock(event, event.getPlayer());
	}
	
	@EventHandler
	public void onBlockBreak(final BlockBreakEvent event)
	{
		this.onBlock(event, event.getPlayer());
	}
	
	public void onBlock(final BlockEvent event, final Player player)
	{
		Util.nonNull(player);
		if(Util.hasUtil())
		{
			final BlockState state = Util.nonNull(event).getBlock().getState();
			if(state instanceof Sign)
			{
				final Util util = Util.giveUtil();
				final Sign sign = (Sign) state;
				final String[] lines = event instanceof SignChangeEvent ? ((SignChangeEvent) event).getLines() : sign.getLines();
				if(util.isSignTP(lines[0]))
				{
					final Configuration configLocations = util.giveUtilConfigLocations();
					final String prefixLocations = util.giveUtilConfigPrefix() + "SignTP." + lines[1] + ".";
					if(configLocations.isString(prefixLocations + "W"))
					{
						final Configuration configSigns = util.giveUtilConfigSigns();
						final String prefix = util.giveUtilConfigPrefix() + util.getLocation(configLocations, prefixLocations).getWorld().getName();
						final List<String> list = configSigns.getStringList(prefix);
						final String content = sign.getWorld().getName() + "," + sign.getX() + "," + sign.getY() + "," + sign.getZ();
						if(event instanceof SignChangeEvent && !list.contains(content))
							list.add(content);
						else if(event instanceof BlockBreakEvent && list.contains(content))
							list.remove(content);
						configSigns.set(prefix, list);
						util.doUtilSaveConfigSigns();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerChangedWorld(final PlayerChangedWorldEvent event)
	{
		final World from = event.getFrom(), to = event.getPlayer().getWorld();
		this.taskRequestSignSend.setWorlds(from, to).runTaskLater(10L);
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(util.isLobby(from) || util.isLobby(to))
				this.doReorganizePlayer(util, event.getPlayer());
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerTeleport(final PlayerTeleportEvent event)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final World world = event.getTo().getWorld();
			if(util.isLobby(world))
			{
				final Configuration config = util.giveUtilConfig();
				final String prefix = util.giveUtilConfigPrefix() + "improve.lobby.";
				final Player player = event.getPlayer();
				final String playerName = player.getName(), prefixList = prefix + "list.";
				if(config.getStringList(prefixList + "black").contains(playerName) || (config.getBoolean(prefix + "lock") && !config.getStringList(prefixList + "white").contains(playerName)))
				{
					util.doUtilSendMessage(player, String.format(util.giveMessage(util.giveUtilConfigPrefix() + "message.WorldIsLocked", player), world.getName()));
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(final EntityDamageEvent event)
	{
		final Entity entity = event.getEntity();
		if(Util.hasUtil() && entity instanceof Player && event.getCause() == DamageCause.FALL)
		{
			final PlayerFallEvent eventFall = new PlayerFallEvent((Player) entity, event.getDamage(), event.getFinalDamage());
			Util.giveUtil().givePluginManager().callEvent(eventFall);
			event.setCancelled(eventFall.getCancelled());
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onAsyncPlayerChat(final AsyncPlayerChatEvent event)
	{
		final Player player = event.getPlayer();
		if(Util.hasUtil())
			event.setFormat(Util.giveUtil().givePlayerRank(player).giveColor() + "%s" + ChatColor.RESET + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + "%s" + ChatColor.RESET);
		final World world = player.getWorld();
		for(final Player recipient : new HashSet<Player>(event.getRecipients()))
			if(world != recipient.getWorld())
				event.getRecipients().remove(recipient);
	}
	
	@EventHandler
	public void onTimer(final TimerEvent event)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(util.hasUtilWorlds())
				try
				{
					util.giveUtilWorlds().doSave();
				}
				catch(final IOException e)
				{
					e.printStackTrace();
				}
			if(event.giveTicks() >= 576000L /* 8h */)
			{
				final int hour = Integer.valueOf(new SimpleDateFormat("HH").format(event.giveDateCurrent()));
				if(hour >= 3 && hour <= 6)
				{
					final Server server = util.giveServer();
					for(final Player player : server.getOnlinePlayers())
						player.kickPlayer(server.getShutdownMessage());
					server.savePlayers();
					for(final World world : server.getWorlds())
						util.doUnloadWorld(world);
					server.shutdown();
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDropItem(final PlayerDropItemEvent event)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = event.getPlayer();
			if(util.isPlayerInLobby(player) && util.isBlocked(player, 4, 4, 7, 6, 5, 8))
				event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerPickupItem(final PlayerPickupItemEvent event)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = event.getPlayer();
			if(util.isPlayerInLobby(player) && util.isBlocked(player, 4, 4, 7, 6, 4, 7))
				event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event)
	{
		if(Util.hasUtil() && Util.giveUtil().isPlayerInLobby(event.getEntity()))
			event.getDrops().clear();
	}
	
	@EventHandler
	public void onPlayerActionBack(final PlayerActionBackEvent event)
	{
		if(Util.hasUtil() && event.hasInventory())
		{
			final Util util = Util.giveUtil();
			if(util.hasUtilMinigame())
			{
				final UtilMinigameSupport utilMinigame = util.giveUtilMinigameSupport();
				final Player player = event.getPlayer();
				if(util.isPlayerInLobby(player) && event.giveInventory().getTitle().equals(util.giveMessage(util.giveUtilConfigPrefix() + "message.minigame.account.change.language", player)))
				{
					net.polishgames.rhenowar.util.minigame.Action<?> action = null;
					try
					{
						action = utilMinigame.giveAction(PlayerMinigame.givePlayerMinigame(player).giveUtilScript(), "AccountSettings");
					}
					catch(final ReflectiveOperationException e)
					{
						e.printStackTrace();
					}
					if(action != null)
						event.setAction(action);
				}
			}
		}
	}
	
	protected final int giveCurrentPlayers(final World world)
	{
		return Util.nonNull(world).getPlayers().size();
	}
	
	protected final StateSignTP giveStateSignTP(final int currentPlayers, final int maxPlayers)
	{
		if(maxPlayers < 0 || !Util.hasUtil())
			return StateSignTP.LOCK;
		else
		{
			final Util util = Util.giveUtil();
			if(maxPlayers == 0 || currentPlayers * 100 / maxPlayers < util.giveUtilConfig().getInt(util.giveUtilConfigPrefix() + "SignTP.percentOnlyVIP"))
				return StateSignTP.NONFULL;
			else if(currentPlayers < maxPlayers)
				return StateSignTP.ONLYVIP;
			else
				return StateSignTP.FULL;
		}
	}
	
	protected final StateSignTP giveStateSignTP(final World world, final int maxPlayers)
	{
		return this.giveStateSignTP(this.giveCurrentPlayers(world), maxPlayers);
	}
	
	protected final boolean isSignTP(final ISignEvent event)
	{
		return Util.hasUtil() && Util.giveUtil().isSignTP(Util.nonNull(event));
	}
	
	protected final void doReorganizePlayer(final Util util, final Player player)
	{
		final int value = Util.nonNull(util).giveUtilConfig().getInt(util.giveUtilConfigPrefix() + "improve.health");
		if(util.isAllowed(Util.nonNull(player), 4, 4, 7, 6, 3, 4))
		{
			player.setGameMode(GameMode.CREATIVE);
			player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		}
		else
		{
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
			player.setHealth(value);
			player.setFlying(false);
		}
		player.setFoodLevel(value);
		final PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.setHeldItemSlot(4);
	}

}
