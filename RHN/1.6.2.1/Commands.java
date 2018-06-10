package net.polishgames.rhenowar.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import com.sk89q.worldedit.BlockVector;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import net.polishgames.rhenowar.util.command.CommandHandler;
import net.polishgames.rhenowar.util.rank.Entitlement;
import net.polishgames.rhenowar.util.rank.PlayerRank;
import net.polishgames.rhenowar.util.rank.Rank;
import net.polishgames.rhenowar.util.world.WorldGeneratorDreamland;
import net.polishgames.rhenowar.util.world.WorldGeneratorEmpty;
import static net.polishgames.rhenowar.util.command.CommandExecutorType.*;

public final class Commands extends AbstractCommands
{
	
	public Commands(final RhenowarPlugin plugin)
	{
		super(plugin);
	}
	
	@CommandHandler(
			label = "reload",
			description = "Restarts the server",
			alias = {"start", "restart", "rl", "rs"},
			entitlement = 35,
			executor = ALL)
	public void onReload(final CommandSender sender)
	{
		final String command = "stop";
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(sender instanceof Player)
			{
				final Player player = (Player) sender;
				if(util.isAllowed(player, 6, 4, 7, 6, 9, 10))
					player.performCommand(command);
			}
			else
				util.giveServer().dispatchCommand(sender, command);
		}
		else if(!(sender instanceof Player))
			Bukkit.dispatchCommand(sender, command);
	}
	
	@CommandHandler(
			label = "spawn",
			description = "Returns sender to the lobby",
			alias = {"lobby", "hub", "leave"},
			executor = PLAYER)
	public void onSpawn(final Player sender)
	{
		if(Util.hasUtil())
			Util.giveUtil().doTeleportToLobby(sender);
	}
	
	@CommandHandler(
			label = "setspawn",
			description = "Sets server's spawn",
			alias = {"spawnset", "ss"},
			entitlement = 98,
			executor = PLAYER)
	public void onSetSpawn(final Player sender)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			util.setLocation(util.giveUtilConfigLocations(), util.giveUtilConfigPrefix() + "spawn.", sender.getLocation()).doUtilSaveConfigLocations();
			AbstractCommands.doSendSuccess(sender);
		}
	}
	
	@CommandHandler(
			label = "health",
			description = "Set your health",
			usage = "<value>",
			alias = "h",
			entitlement = 56,
			executor = PLAYER)
	public void onHealth(final Player sender, final String label, final float value)
	{
		if(value < 1)
			AbstractCommands.doSendNumberFormat(sender, 2);
		else
			this.onHealth(sender, label, sender.getName(), value);
	}
	
	@CommandHandler(
			label = "health",
			description = "Set health of player",
			usage = "<username> <value>",
			alias = "h",
			entitlement = 56,
			executor = PLAYER)
	public void onHealth(final Player sender, final String label, final String username, final float value)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(value < 1)
				AbstractCommands.doSendNumberFormat(sender, 3);
			else
			{
				final Player player = util.givePlayer(username);
				if(player != null)
				{
					if(util.isAllowed(sender, 9, 9, 7, 6, 9, 9))
					{
						player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
						player.setHealth(value);
						AbstractCommands.doSendSuccess(sender);
					}
					else
						AbstractCommands.doSendNoPermission(sender);
				}
				else
					AbstractCommands.doSendPlayerIsOffline(sender, username);
			}
		}
	}
	
	@CommandHandler(
			label = "uuid",
			description = "Get player's UUID",
			usage = "<username>",
			entitlement = 54,
			executor = PLAYER_OR_CONSOLE)
	public void onUUID(final CommandSender sender, final String label, final String username)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = util.givePlayer(username);
			if(player != null)
				AbstractCommands.doSend(sender, "PlayerUUID", null, player.getName(), player.getUniqueId().toString());
			else
				AbstractCommands.doSendPlayerIsOffline(sender, username);
		}
	}
	
	@CommandHandler(
			label = "tps",
			description = "Get TPS value",
			entitlement = 34,
			executor = ALL)
	public void onTPS(final CommandSender sender)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final double tps = util.giveTPS();
			AbstractCommands.doSend(sender, "TPS", null, tps / CalculatorTPS.STANDARD_VALUE * 100, tps);
		}
	}
	
	@CommandHandler(
			label = "rank",
			description = "Gets the rank of player",
			usage = "<username>",
			alias = "r",
			entitlement = 32,
			executor = PLAYER_OR_CONSOLE)
	public void onRank(final CommandSender sender, final String label, final String username)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = util.givePlayer(username);
			if(player != null)
				AbstractCommands.doSend(sender, "PlayerRank", null, player.getName(), util.givePlayerRank(player).giveName());
			else
				AbstractCommands.doSendPlayerIsOffline(sender, username);
		}
	}
	
	@CommandHandler(
			label = "rank",
			description = "Sets the rank of player",
			usage = "<username> <rank>",
			alias = "r",
			entitlement = 88,
			executor = PLAYER_OR_CONSOLE)
	public void onRank(final CommandSender sender, final String label, final String username, final String rankname)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = util.givePlayer(username);
			if(player != null)
			{
				final Rank rank = util.giveRank(rankname);
				if(rank != null)
					try
					{
						PlayerRank.givePlayerRank(player).setRank(rank);
					}
					catch(final SQLException e)
					{
						AbstractCommands.doSend(sender, "ErrorSQL", null);
						util.doReportErrorSQL(e);
					}
				else
					AbstractCommands.doSend(sender, "rank.NonExist", null, rankname);
			}
			else
				AbstractCommands.doSendPlayerIsOffline(sender, username);
		}
	}
	
	@CommandHandler(
			label = "rank",
			description = "Sets specified entitlements level of player",
			usage = "<username> <level build> <level special>",
			alias = "r",
			entitlement = 98,
			executor = PLAYER_OR_CONSOLE)
	public void onRank(final CommandSender sender, final String label, final String username, final int levelBuild, final int levelSpecial)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = util.givePlayer(username);
			if(player != null)
				try
				{
					PlayerRank.givePlayerRank(player).setLevel(Entitlement.giveLevel(levelBuild, levelSpecial));
				}
				catch(final SQLException e)
				{
					AbstractCommands.doSend(sender, "ErrorSQL", null);
					util.doReportErrorSQL(e);
				}
			else
				AbstractCommands.doSendPlayerIsOffline(sender, username);
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"list", "ls", "l"},
			description = "Shows list of worlds",
			alias = "w",
			entitlement = 32,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldList(final CommandSender sender, final String label, final String sublabel)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(util.hasUtilWorlds())
			{
				final boolean isPlayer = sender instanceof Player;
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", coupler = isPlayer ? util.giveMessage(prefixMessage + "coupler", (Player) sender) : util.giveMessage(prefixMessage + "coupler");
				final List<String> worlds = new ArrayList<String>();
				final List<World> worldsBukkit = util.giveServer().getWorlds(), worldsUtil = new ArrayList<World>();
				for(final Object world : util.giveUtilWorlds())
					if(world instanceof WorldData)
						worldsUtil.add(((WorldData) world).giveWorld());
				String message = isPlayer ? util.giveMessage(prefixMessage + "world.list", (Player) sender) : util.giveMessage(prefixMessage + "world.list");
				for(final World world : worldsBukkit)
					worlds.add("&" + (worldsUtil.contains(world) ? "a" : "e") + world.getName());
				for(final World world : worldsUtil)
					if(!worldsBukkit.contains(world))
						worlds.add("&c" + world.getName());
				for(final String world : worlds)
					message += world + coupler;
				if(!worlds.isEmpty())
					message = message.substring(0, message.length() - coupler.length());
				if(isPlayer)
					util.doUtilSendMessage((Player) sender, message);
				else
					util.doSendColouredConsole(util.giveUtilPlugin(), message);
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"info", "i"},
			description = "Shows information of world",
			alias = "w",
			entitlement = 32,
			executor = PLAYER)
	public void onWorldInfo(final Player sender, final String label, final String sublabel)
	{
		this.onWorldInfo(sender, label, sublabel, sender.getWorld().getName());
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"info", "i"},
			description = "Shows information of world",
			usage = "<world>",
			alias = "w",
			entitlement = 32,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldInfo(final CommandSender sender, final String label, final String sublabel, final String name)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			World world = util.giveWorld(name);
			if(world == null)
			{
				if(isPlayer)
					world = ((Player) sender).getWorld();
				else
					util.doSendColouredConsole(util.giveUtilPlugin(), String.format(util.giveMessage(util.giveUtilConfigPrefix() + "message.world.NonExist"), name));
			}
			if(world != null)
			{
				final String message = WorldData.giveWorldData(world).toString();
				if(isPlayer)
					util.doUtilSendMessage((Player) sender, message);
				else
					util.doSendColouredConsole(util.giveUtilPlugin(), message);
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = "tp",
			description = "Teleports to the world",
			usage = "<world>",
			alias = "w",
			entitlement = 32,
			executor = PLAYER)
	public void onWorldTP(final Player sender, final String label, final String sublabel, final String name)
	{
		this.onWorldTP(sender, label, sublabel, name, sender.getName());
	}
	
	@CommandHandler(
			label = "world",
			sublabel = "tp",
			description = "Teleports to the world",
			usage = "<world> <player>",
			alias = "w",
			entitlement = 32,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldTP(final CommandSender sender, final String label, final String sublabel, final String name, final String username)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			final String prefixMessage = util.giveUtilConfigPrefix() + "message.";
			if(isPlayer && util.isBlocked((Player) sender, 3, 4, 5, 6, 4, 7))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				String message = "";
				final World world = util.giveWorld(name);
				if(world == null)
					message = String.format(isPlayer ? util.giveMessage(prefixMessage + "world.NonExist", (Player) sender) : util.giveMessage(prefixMessage + "world.NonExist"), name) + " " + (isPlayer ? util.giveMessage(prefixMessage + "CheckName", (Player) sender) : util.giveMessage(prefixMessage + "CheckName"));
				else
				{
					final Player player = util.givePlayer(username);
					if(player == null)
						message = String.format(isPlayer ? util.giveMessage(prefixMessage + "PlayerIsOffline", (Player) sender) : util.giveMessage(prefixMessage + "PlayerIsOffline"), username);
					else
					{
						player.teleport(util.giveSpawnLocation(world));
						message = isPlayer ? util.giveMessage(prefixMessage + "world.tp", (Player) sender) : util.giveMessage(prefixMessage + "world.tp");
					}
				}
				if(!message.isEmpty())
				{
					if(isPlayer)
						util.doUtilSendMessage((Player) sender, message);
					else
						util.doSendColouredConsole(util.giveUtilPlugin(), message);
				}
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"import", "create", "c"},
			description = "Imports or creates the world",
			usage = "<world> [[<generator|gen|g>:<normal|flat|nether|end|empty|dreamland>] [<settings|set>:<settings>] [<seed|s>:<seed>]]",
			alias = "w",
			entitlement = 35,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldImportOrCreate(final CommandSender sender, final String label, final String sublabel, final String name, final String... properties)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			if(isPlayer && util.isBlocked((Player) sender, 6, 4, 7, 6, 9, 9))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final File file = new File(util.giveServer().getWorldContainer(), name);
				final boolean typeImport = sublabel.equalsIgnoreCase("import"), exists = file.exists() && file.isDirectory();
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.", prefix = prefixWorld + "create", coupler = isPlayer ? util.giveMessage(prefixMessage + "coupler", (Player) sender) : util.giveMessage(prefixMessage + "coupler");
				String message = "";
				final List<String> worlds = new ArrayList<String>();
				for(final Object world : util.giveUtilWorlds())
					if(world instanceof WorldData)
						worlds.add(((WorldData) world).giveName().toLowerCase());
				if(worlds.contains(name.toLowerCase()) || util.giveWorld(name) != null || (!typeImport && exists))
					message = String.format(isPlayer ? util.giveMessage(prefixWorld + "AlreadyExist", (Player) sender) : util.giveMessage(prefixWorld + "AlreadyExist"), name);
				else if(typeImport && !exists)
					message = String.format(isPlayer ? util.giveMessage(prefix + ".NonExist", (Player) sender) : util.giveMessage(prefix + ".NonExist"), name) + " " + (isPlayer ? util.giveMessage(prefixMessage + "CheckName", (Player) sender) : util.giveMessage(prefixMessage + "CheckName"));
				else
				{
					boolean success = false;
					String generator = null, settings = null;
					Long seed = null;
					if(properties != null)
						for(final String property : properties)
						{
							final String[] array = property.split(":");
							if(array.length == 2)
							{
								final String key = array[0].toLowerCase(), value = array[1].toLowerCase();
								switch(key)
								{
									case "generator":
									case "gen":
									case "g":
										if(generator == null)
											generator = value;
										break;
									case "settings":
									case "set":
										if(settings == null)
											settings = value;
										break;
									case "seed":
									case "s":
										if(seed == null)
											try
											{
												seed = Long.valueOf(value);
											}
											catch(final NumberFormatException e)
											{
												final String content = String.format(isPlayer ? util.giveMessage(prefixMessage + "NumberIncorrect", (Player) sender) : util.giveMessage(prefixMessage + "NumberIncorrect"), value);
												if(isPlayer)
													util.doUtilSendMessage((Player) sender, content);
												else
													util.doSendColouredConsole(util.giveUtilPlugin(), content);
												success = true;
											}
										break;
									default:
									{
										final String content = String.format(isPlayer ? util.giveMessage(prefix + ".argument.unknown", (Player) sender) : util.giveMessage(prefix + ".argument.unknown"), property, isPlayer ? util.giveMessage(prefixMessage + "key", (Player) sender) : util.giveMessage(prefixMessage + "key"), key, "generator" + coupler + "settings" + coupler + "seed");
										if(isPlayer)
											util.doUtilSendMessage((Player) sender, content);
										else
											util.doSendColouredConsole(util.giveUtilPlugin(), content);
										success = true;
									} break;
								}
							}
							else
							{
								final String content = String.format(isPlayer ? util.giveMessage(prefix + ".argument.incorrect", (Player) sender) : util.giveMessage(prefix + ".argument.incorrect"), property, isPlayer ? util.giveMessage(prefixMessage + "key", (Player) sender) : util.giveMessage(prefixMessage + "key"), isPlayer ? util.giveMessage(prefixMessage + "value", (Player) sender) : util.giveMessage(prefixMessage + "value"));
								if(isPlayer)
									util.doUtilSendMessage((Player) sender, content);
								else
									util.doSendColouredConsole(util.giveUtilPlugin(), content);
								success = true;
							}
						}
					if(!success)
					{
						if(generator == null)
							generator = "normal";
						WorldCreator creator = null;
						switch(generator)
						{
							case "normal":
								creator = WorldCreator.name(name);
								break;
							case "flat":
								creator = WorldCreator.name(name).type(WorldType.FLAT);
								break;
							case "nether":
								creator = WorldCreator.name(name).environment(Environment.NETHER);
								break;
							case "end":
								creator = WorldCreator.name(name).environment(Environment.THE_END);
								break;
							case "empty":
								creator = WorldGeneratorEmpty.giveCreator(name);
								break;
							case "dreamland":
								creator = WorldGeneratorDreamland.giveCreator(name);
								break;
						}
						if(creator != null)
						{
							if(settings != null)
								creator.generatorSettings(settings);
							if(seed != null)
								creator.seed(seed);
							final World world = util.doCreateWorld(creator);
							if(world != null)
								util.addWorld(world);
							message = String.format(isPlayer ? util.giveMessage(prefix + "." + (world != null ? "success" : "failed"), (Player) sender) : util.giveMessage(prefix + "." + (world != null ? "success" : "failed")), name);
						}
						else
							message = String.format(isPlayer ? util.giveMessage(prefix + ".generator.unknown", (Player) sender) : util.giveMessage(prefix + ".generator.unknown"), generator);
					}
					
				}
				if(!message.isEmpty())
				{
					if(isPlayer)
						util.doUtilSendMessage((Player) sender, message);
					else
						util.doSendColouredConsole(util.giveUtilPlugin(), message);
				}
			}
			
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"remove", "rm", "delete", "del"},
			description = "Removes or deletes the world",
			usage = "<world>",
			alias = "w",
			entitlement = 35,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldRemoveOrDelete(final CommandSender sender, final String label, final String sublabel, final String name)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			if(isPlayer && util.isBlocked((Player) sender, 6, 4, 7, 6, 9, 9))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final SendFunction sendFunction = AbstractCommands.giveSendFunction(sender);
				final MessageFunction messageFunction = AbstractCommands.giveMessageFunction(sender);
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.";
				final World world = util.giveWorld(name);
				if(world == null)
					sendFunction.apply(String.format(messageFunction.apply(prefixWorld + "NonExist"), name) + " " + (messageFunction.apply(prefixMessage + "CheckName")));
				else
				{
					this.onWorldUnload(sender, label, sublabel, name);
					if(util.giveServer().getWorld(name) == null)
					{
						if(util.delWorld(name) != null)
						{
							sendFunction.apply(String.format(messageFunction.apply(prefixWorld + "remove.success"), name));
							if(sublabel.equalsIgnoreCase("delete") || sublabel.equalsIgnoreCase("del"))
							{
								if(isPlayer && util.isBlocked((Player) sender, 9, 9, 9, 9, 10, 9))
									AbstractCommands.doSendNoPermission(sender);
								else
									sendFunction.apply(String.format(messageFunction.apply(prefixWorld + "delete." + (util.doDeleteDirectory(world.getWorldFolder()) != null ? "success" : "failed")), name));
							}
						}
						else
							sendFunction.apply(String.format(messageFunction.apply(prefixWorld + "remove.failed"), name));
					}
				}
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = "load",
			description = "Loads the world",
			usage = "<world>",
			alias = "w",
			entitlement = 34,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldLoad(final CommandSender sender, final String label, final String sublabel, final String name)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			if(isPlayer && util.isBlocked((Player) sender, 5, 4, 5, 6, 5, 8))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.";
				final MessageFunction function = AbstractCommands.giveMessageFunction(sender);
				String message = "";
				if(util.giveWorld(name) != null)
					message = String.format(function.apply(prefixWorld + "AlreadyExist"), name);
				else
				{
					final String prefix = prefixWorld + "load";
					WorldData world = null;
					for(final Object obj : util.giveUtilWorlds())
						if(obj instanceof WorldData)
						{
							final WorldData data = (WorldData) obj;
							if(data.giveName().equalsIgnoreCase(name))
							{
								world = data;
								break;
							}
						}
					if(world == null)
						message = String.format(function.apply(prefix + ".failed"), name) + " " + function.apply(prefixMessage + "CheckName");
					else
					{
						util.doLoadWorld(world);
						message = String.format(function.apply(prefix + ".success"), name);
					}
				}
				if(!message.isEmpty())
					AbstractCommands.doSendUtil(sender, message);
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = "unload",
			description = "Unloads the world",
			usage = "<world>",
			alias = "w",
			entitlement = 35,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldUnload(final CommandSender sender, final String label, final String sublabel, final String name)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			if(isPlayer && util.isBlocked((Player) sender, 6, 4, 7, 6, 9, 9))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.";
				final MessageFunction function = AbstractCommands.giveMessageFunction(sender);
				String message = "";
				final World world = util.giveWorld(name);
				if(world == null)
					message = String.format(function.apply(prefixWorld + "NonExist"), name) + " " + function.apply(prefixMessage + "CheckName");
				else
				{
					util.doUnloadWorld(world);
					message = String.format(function.apply(prefixWorld + "unload"), name);
				}
				if(!message.isEmpty())
					AbstractCommands.doSendUtil(sender, message);
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"flag", "f"},
			description = "Gets the world's flag",
			usage = "<world> <*|world|name|protect|seed|generator|environment|variant|structures|settings|animals|monsters|pvp|difficulty|memory|gamemode|weather|hunger|fall|build|spawn>",
			alias = "w",
			entitlement = 34,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldFlag(final CommandSender sender, final String label, final String sublabel, final String name, final String flag)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.", prefix = prefixWorld + "flag";
			final MessageFunction function = AbstractCommands.giveMessageFunction(sender);
			String message = "";
			final World world = util.giveWorld(name);
			if(world == null)
				message = String.format(function.apply(prefixWorld + "NonExist"), name) + " " + function.apply(prefixMessage + "CheckName");
			else
			{
				final WorldData data = WorldData.giveWorldData(world);
				boolean unknown = false;
				Object value = null;
				switch(flag)
				{
					case "*":
						value = data;
						break;
					case "world":
						value = data.giveWorld();
						break;
					case "name":
						value = data.giveName();
						break;
					case "protect":
						value = data.giveProtect();
						break;
					case "seed":
						value = data.getSeed();
						break;
					case "generator":
						value = data.getGenerator();
						break;
					case "environment":
						value = data.getEnvironment();
						break;
					case "variant":
						value = data.getVariant();
						break;
					case "structures":
						value = data.getStructures();
						break;
					case "settings":
						value = data.getSettings();
						break;
					case "animals":
						value = data.getAnimals();
						break;
					case "monsters":
						value = data.getMonsters();
						break;
					case "pvp":
						value = data.getPvP();
						break;
					case "difficulty":
						value = data.getDifficulty();
						break;
					case "memory":
						value = data.getMemory();
						break;
					case "gamemode":
						value = data.getGameMode();
						break;
					case "weather":
						value = data.getWeather();
						break;
					case "hunger":
						value = data.getHunger();
						break;
					case "fall":
						value = data.getFall();
						break;
					case "build":
						value = data.getBuild();
						break;
					case "spawn":
						value = data.getSpawn();
						break;
				}
				if(value == null)
					unknown = true;
				else
					message = String.format(function.apply(prefixWorld + "gamerule.get"), function.apply(prefix), flag, name, value.toString());
				if(unknown && message.isEmpty())
					message = String.format(function.apply(prefixWorld + "gamerule.unknown"), name, function.apply(prefix), flag) + " " + function.apply(prefixMessage + "CheckName");
				if(!message.isEmpty())
					AbstractCommands.doSendUtil(sender, message);
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"flag", "f"},
			description = "Sets the world's flag",
			usage = "<world> <<protect <add|del>>|<animals <true|false>>|<monsters <true|false>>|<pvp <true|false>>|<difficulty <difficulty>>|<memory <true|false>>|<gamemode <gamemode>>|<weather <true|false>>|<hunger <true|false>>|<fall <true|false>>|<build <true|false>>>",
			alias = "w",
			entitlement = 35,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldFlag(final CommandSender sender, final String label, final String sublabel, final String name, final String key, final String value)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			if(isPlayer && util.isBlocked((Player) sender, 6, 4, 7, 6, 9, 9))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.", prefix = prefixWorld + "flag";
				final MessageFunction function = AbstractCommands.giveMessageFunction(sender);
				String message = "";
				final World world = util.giveWorld(name);
				if(world == null)
					message = String.format(function.apply(prefixWorld + "NonExist"), name) + " " + function.apply(prefixMessage + "CheckName");
				else
				{
					final WorldData data = WorldData.giveWorldData(world);
					boolean unknown = false;
					boolean flag = false, export = false, incorrectInteger = false, incorrectBoolean = false;
					Integer valueInteger = null;
					try
					{
						valueInteger = Integer.valueOf(value);
					}
					catch(final NumberFormatException e){}
					Boolean valueBoolean = null;
					if(value.equals("true"))
						valueBoolean = true;
					else if(value.equals("false"))
						valueBoolean = false;
					switch(key)
					{
						case "protect":
						{
							if(isPlayer)
							{
								Region region = null;
								switch(value)
								{
									case "add":
									case "del":
										region = Region.giveRegion((Player) sender);
										break;
								}
								if(region != null)
								{
									final boolean exist = data.giveProtect().contains(region);
									switch(value)
									{
										case "add":
											if(!exist)
												data.addProtect(region);
											break;
										case "del":
											if(exist)
												data.delProtect(region);
											break;
									}
									final BlockVector min = region.giveMin(), max = region.giveMax();
										message = String.format(function.apply(prefix + ".set." + key + "." + value), name, min.getBlockX() + "," + min.getBlockY() + "," + min.getBlockZ() + " – " + max.getBlockX() + "," + max.getBlockY() + "," + max.getBlockZ());
								}
							}
						} break;
						case "animals":
						{
							if(valueBoolean != null)
							{
								data.setAnimals(valueBoolean);
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						case "monsters":
						{
							if(valueBoolean != null)
							{
								data.setMonsters(valueBoolean);
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						case "pvp":
						{
							if(valueBoolean != null)
							{
								data.setPvP(valueBoolean);
								export = true;
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						case "difficulty":
						{
							if(valueInteger != null)
							{
								@SuppressWarnings("deprecation")
								final Difficulty difficulty = Difficulty.getByValue(valueInteger);
								if(difficulty != null)
								{
									data.setDifficulty(difficulty);
									export = true;
									flag = true;
								}
							}
							else
								incorrectInteger = true;
						} break;
						case "memory":
						{
							if(valueBoolean != null)
							{
								data.setMemory(valueBoolean);
								export = true;
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						case "gamemode":
						{
							if(valueInteger != null)
							{
								@SuppressWarnings("deprecation")
								final GameMode difficulty = GameMode.getByValue(valueInteger);
								if(difficulty != null)
								{
									data.setGameMode(difficulty);
									flag = true;
								}
							}
							else
								incorrectInteger = true;
						} break;
						case "weather":
						{
							if(valueBoolean != null)
							{
								data.setWeather(valueBoolean);
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						case "hunger":
						{
							if(valueBoolean != null)
							{
								data.setHunger(valueBoolean);
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						case "fall":
						{
							if(valueBoolean != null)
							{
								data.setFall(valueBoolean);
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						case "build":
						{
							if(valueBoolean != null)
							{
								data.setBuild(valueBoolean);
								flag = true;
							}
							else
								incorrectBoolean = true;
						} break;
						default:
							unknown = true;
							break;
					}
					if(flag)
					{
						if(export)
							data.doExportData(world);
						message = String.format(function.apply(prefix + ".set"), name, key, value);
					}
					else if(incorrectInteger || incorrectBoolean)
						message = String.format(function.apply(prefixMessage + (incorrectInteger ? "Number" : "Boolean") + "Incorrect"), value);
					if(unknown && message.isEmpty())
						message = String.format(function.apply(prefixWorld + "gamerule.unknown"), name, function.apply(prefix), key) + " " + function.apply(prefixMessage + "CheckName");
					if(!message.isEmpty())
						AbstractCommands.doSendUtil(sender, message);
				}
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"gamerule", "gr"},
			description = "Gets gamerule of the world",
			usage = "<world> <gamerule>",
			alias = "w",
			entitlement = 34,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldGameRule(final CommandSender sender, final String label, final String sublabel, final String name, final String gamerule)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.", prefix = prefixWorld + "flag";
			final MessageFunction function = AbstractCommands.giveMessageFunction(sender);
			String message = "";
			final World world = util.giveWorld(name);
			if(world == null)
				message = String.format(function.apply(prefixWorld + "NonExist"), name) + " " + function.apply(prefixMessage + "CheckName");
			else
			{
				boolean unknown = false;
				final String value = world.getGameRuleValue(gamerule);
				if(value != null && !value.isEmpty())
					message = String.format(function.apply(prefix + ".get"), "gamerule", gamerule, name, value);
				else
					unknown = true;
				if(unknown && message.isEmpty())
					message = String.format(function.apply(prefix + ".unknown"), name, "gamerule", gamerule) + " " + function.apply(prefixMessage + "CheckName");
			}
			if(!message.isEmpty())
					AbstractCommands.doSendUtil(sender, message);
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"gamerule", "gr"},
			description = "Sets gamerule of the world",
			usage = "<world> <key> <value>",
			alias = "w",
			entitlement = 35,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldGameRule(final CommandSender sender, final String label, final String sublabel, final String name, final String key, final String value)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			if(isPlayer && util.isBlocked((Player) sender, 6, 4, 7, 6, 9, 9))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefixWorld = prefixMessage + "world.", prefix = prefixWorld + "flag";
				final MessageFunction function = AbstractCommands.giveMessageFunction(sender);
				String message = "";
				final World world = util.giveWorld(name);
				if(world == null)
					message = String.format(function.apply(prefixWorld + "NonExist"), name) + " " + function.apply(prefixMessage + "CheckName");
				else
				{
					boolean unknown = false;
					if(world.setGameRuleValue(key, value))
						message = String.format(function.apply(prefix + ".set"), name, "gamerule", key, value);
					else
						unknown = true;
					if(unknown && message.isEmpty())
						message = String.format(function.apply(prefix + ".unknown"), name, "gamerule", key) + " " + function.apply(prefixMessage + "CheckName");
				}
				if(!message.isEmpty())
					AbstractCommands.doSendUtil(sender, message);
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"setspawn", "spawnset", "ss"},
			description = "Sets spawn of the world",
			alias = "w",
			entitlement = 35,
			executor = PLAYER)
	public void onWorldSetSpawn(final Player sender, final String label, final String sublabel)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(util.isBlocked(sender, 6, 4, 7, 6, 9, 9))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final World world = sender.getWorld();
				final WorldData data = WorldData.giveWorldData(world);
				data.setSpawn(sender.getLocation());
				data.doExportData(world);
			}
		}
	}
	
	@CommandHandler(
			label = "world",
			sublabel = {"save", "s"},
			description = "Save worlds's data",
			alias = "w",
			entitlement = 34,
			executor = PLAYER_OR_CONSOLE)
	public void onWorldSave(final CommandSender sender, final String label, final String sublabel)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			if(isPlayer && util.isBlocked((Player) sender, 5, 4, 5, 6, 5, 8))
				AbstractCommands.doSendNoPermission(sender);
			else
				try
				{
					util.giveUtilWorlds().doSave();
				}
				catch(final IOException e)
				{
					e.printStackTrace();
				}
		}
	}
	
	public void onMoney(final CommandSender sender, String type, final String username, int amount)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			BiConsumer<OfflinePlayer, Integer> consumer = null;
			switch(type = type.toLowerCase().trim())
			{
				case "g":
					type = "get";
				case "get":
					amount = util.getMoney(util.givePlayerOffline(username));
					break;
				case "s":
					type = "set";
				case "set":
					consumer = util::setMoney;
					break;
				case "a":
					type = "add";
				case "add":
					consumer = util::addMoney;
					break;
				case "d":
					type = "del";
				case "del":
					consumer = util::delMoney;
					break;
			}
			if(consumer != null)
				consumer.accept(util.givePlayerOffline(username), amount);
			AbstractCommands.doSendUtil(sender, String.format(AbstractCommands.giveMessageFunction(sender).apply(util.giveUtilConfigPrefix() + "message.money." + type), username, amount));
		}
	}
	
	@CommandHandler(
			label = "money",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			executor = PLAYER)
	public void onMoney(final Player sender, final String label)
	{
		this.onMoneyGet(sender, label, "get");
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"get", "g"},
			description = "Gets player's money balance",
			usage = "<player>",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			executor = PLAYER_OR_CONSOLE)
	public void onMoneyGet(final CommandSender sender, final String label, final String sublabel, final String username)
	{
		this.onMoney(sender, sublabel, username, 0);
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"get", "g"},
			description = "Gets your money balance",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			executor = PLAYER)
	public void onMoneyGet(final Player sender, final String label, final String sublabel)
	{
		this.onMoneyGet(sender, label, sublabel, sender.getName());
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"set", "s"},
			description = "Sets amount of player's money",
			usage = "<player> <amount>",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			entitlement = 80,
			executor = PLAYER_OR_CONSOLE)
	public void onMoneySet(final CommandSender sender, final String label, final String sublabel, final String username, final int amount)
	{
		this.onMoney(sender, sublabel, username, amount);
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"set", "s"},
			description = "Sets amount of your money",
			usage = "<amount>",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			entitlement = 80,
			executor = PLAYER)
	public void onMoneySet(final Player sender, final String label, final String sublabel, final int amount)
	{
		this.onMoneySet(sender, label, sublabel, sender.getName(), amount);
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"add", "a"},
			description = "Adds money to player's balance",
			usage = "<player> <amount>",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			entitlement = 80,
			executor = PLAYER_OR_CONSOLE)
	public void onMoneyAdd(final CommandSender sender, final String label, final String sublabel, final String username, final int amount)
	{
		this.onMoney(sender, sublabel, username, amount);
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"add", "a"},
			description = "Adds money to your balance",
			usage = "<amount>",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			entitlement = 80,
			executor = PLAYER)
	public void onMoneyAdd(final Player sender, final String label, final String sublabel, final int amount)
	{
		this.onMoneyAdd(sender, label, sublabel, sender.getName(), amount);
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"del", "d"},
			description = "Substracts money from player's balance",
			usage = "<player> <amount>",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			entitlement = 80,
			executor = PLAYER_OR_CONSOLE)
	public void onMoneyDel(final CommandSender sender, final String label, final String sublabel, final String username, final int amount)
	{
		this.onMoney(sender, sublabel, username, amount);
	}
	
	@CommandHandler(
			label = "money",
			sublabel = {"del", "d"},
			description = "Substracts money from your ballance",
			usage = "<amount>",
			alias = {"balance", "economy", "eco", "m", "b", "e"},
			entitlement = 80,
			executor = PLAYER)
	public void onMoneyDel(final Player sender, final String label, final String sublabel, final int amount)
	{
		this.onMoneyDel(sender, label, sublabel, sender.getName(), amount);
	}
	
	@CommandHandler(
			label = "rhenowar",
			sublabel = {"signtp", "tpsign", "stp"},
			description = "Sets destination for SignTP",
			usage = "<setdestination|setdest|sd> <name>",
			alias = "rhn",
			entitlement = 54,
			executor = PLAYER)
	public void onRhenowarSignTP(final Player sender, final String label, final String sublabel, final String argument, final String name)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			switch(argument.toLowerCase())
			{
				case "setdestination":
				case "setdest":
				case "sd":
					util.setLocation(util.giveUtilConfigPrefix() + "SignTP." + name.toLowerCase() + ".", sender.getLocation()).doUtilSaveConfigLocations();
					AbstractCommands.doSendSuccess(sender);
					break;
				default:
					AbstractCommands.doSendNoSuccess(sender, label);
					break;
			}
		}
	}
	
	@CommandHandler(
			label = "rhenowar",
			sublabel = {"plugin", "plugins", "pl"},
			description = "Shows list of plugins",
			alias = "rhn",
			entitlement = 33,
			executor = PLAYER_OR_CONSOLE)
	public void onRhenowarPlugin(final CommandSender sender, final String label, final String sublabel)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			final PluginManager manager = util.givePluginManager();
			final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefix = prefixMessage + "plugins";
			final Collection<String> plugins = new TreeSet<String>();
			int pluginsLength = 0;
			for(final Plugin plugin : manager.getPlugins())
			{
				final String name = plugin.getName();
				plugins.add(name);
				final int pluginLength = name.length();
				if(pluginLength > pluginsLength)
					pluginsLength = pluginLength;
			}
			pluginsLength++;
			if(isPlayer)
				util.doUtilSendMessage((Player) sender, util.giveMessage(prefix, (Player) sender) + (plugins.size() <= 0 ? " &f-" : ""));
			else
				util.doSendColouredConsole(util.giveUtilPlugin(), util.giveMessage(prefix) + (plugins.size() <= 0 ? " &f-" : ""));
			for(final String name : plugins)
			{
				String author = null;
				final Plugin plugin = manager.getPlugin(name);
				final boolean enabled = plugin.isEnabled();
				final List<String> authors = plugin.getDescription().getAuthors();
				if(!authors.isEmpty())
					author = authors.get(0);
				String alignment = "";
				for(int i = 0; i < pluginsLength - name.length(); i++)
					alignment += " ";
				util.doSend(sender, " &7- " + (enabled ? "&a" : "&c") + name + (author != null ? alignment + String.format(isPlayer ? util.giveMessage(prefix + ".by", (Player) sender) : util.giveMessage(prefix + ".by"), author) : ""));
			}
		}
	}
	
	@CommandHandler(
			label = "rhenowar",
			sublabel = {"plugin", "plugins", "pl"},
			description = "Allow to enabling or disabling plugins",
			usage = "<enable|disable> <plugin>",
			alias = "rhn",
			entitlement = 56,
			executor = PLAYER_OR_CONSOLE)
	public void onRhenowarPlugin(final CommandSender sender, final String label, final String sublabel, final String type, final String name)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			if(sender instanceof Player && util.isBlocked((Player) sender, 9, 9, 7, 6, 9, 9))
				AbstractCommands.doSendNoPermission(sender);
			else
			{
				final boolean isPlayer = sender instanceof Player;
				final PluginManager manager = util.givePluginManager();
				final String prefixMessage = util.giveUtilConfigPrefix() + "message.", prefix = prefixMessage + "plugins";
				String message = null;
				final Plugin plugin = manager.getPlugin(name);
				if(plugin != null)
				{
					final boolean enabled = manager.isPluginEnabled(plugin);
					boolean correct = false, already = false;
					switch(type)
					{
						case "enable":
						{
							if(!enabled)
								manager.enablePlugin(plugin);
							else
								already = true;
							correct = true;
						} break;
						case "disable":
						{
							if(enabled)
								manager.disablePlugin(plugin);
							else
								already = true;
							correct = true;
						} break;
					}
					if(correct)
					{
						final String prefixReceipt = prefix + ".receipt.";
						message = String.format(isPlayer ? util.giveMessage(prefixReceipt + "message", (Player) sender) : util.giveMessage(prefixReceipt + "message"), plugin.getName(), isPlayer ? util.giveMessage(prefixReceipt + (already ? "already" : "now"), (Player) sender) : util.giveMessage(prefixReceipt + (already ? "already" : "now")), isPlayer ? util.giveMessage(prefixReceipt + type, (Player) sender) : util.giveMessage(prefixReceipt + type));
					}
				}
				else
					message = String.format(isPlayer ? util.giveMessage(prefix + ".NonExist", (Player) sender) : util.giveMessage(prefix + ".NonExist"), name) + " " + (isPlayer ? util.giveMessage(prefixMessage + "CheckName", (Player) sender) : util.giveMessage(prefixMessage + "CheckName"));
				if(message != null)
				{
					if(isPlayer)
						util.doUtilSendMessage((Player) sender, message);
					else
						util.doSendColouredConsole(util.giveUtilPlugin(), message);
				}
				else
					AbstractCommands.doSendNoSuccess(sender, label);
			}
		}
	}
	
	@CommandHandler(
			label = "rhenowar",
			sublabel = {"channel", "c"},
			description = "Sends data to Bungee Server via Rhenowar Channel",
			usage = "<ping|haspaid <username>>",
			alias = "rhn",
			entitlement = 56,
			executor = PLAYER_OR_CONSOLE)
	public void onRhenowarChannel(final CommandSender sender, final String label, final String sublabel, final String... arguments)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final int amount = arguments == null ? 0 : arguments.length;
			final String name = util.getUtilChannel().giveName();
			boolean success = false;
			IOException exception = null;
			if(amount > 0)
				switch(arguments[0].toLowerCase())
				{
					case "ping":
					case "pong":
					case "p":
					{
						if(amount == 1)
						{
							try
							{
								final long time = System.nanoTime();
								util.doUtilChannelPing((final Object result) ->
								{
									if(result instanceof Long)
									{
										AbstractCommands.doSend(sender, "channel.ping", null, name, ((long) result - time) / Math.pow((2 << 2) + 2, (2 << 1) + 2));
										return true;
									}
									else
										return false;
								});
							}
							catch(final IOException e)
							{
								exception = e;
							}
							success = true;
						}
					} break;
					case "haspaid":
					case "paid":
					case "hp":
					{
						if(amount == 2)
						{
							final String username = arguments[1];
							final Player player = util.givePlayer(username);
							if(player != null)
								try
								{
									util.hasPaid(player, (final Object result) ->
									{
										if(result instanceof Boolean)
										{
											AbstractCommands.doSend(sender, "channel.haspaid." + result, null, username);
											return true;
										}
										else
											return false;
									});
								}
								catch(final IOException e)
								{
									exception = e;
								}
							else
								AbstractCommands.doSendPlayerIsOffline(sender, username);
							success = true;
						}
					} break;
				}
			if(!success)
				AbstractCommands.doSendNoSuccess(sender, label);
			else if(exception != null)
				AbstractCommands.doSend(sender, "channel.error", null, name, exception.getMessage());
		}
	}
	
	@CommandHandler(
			label = "rhenowar",
			sublabel = {"premium", "p"},
			description = "Checks wether player has premium",
			usage = "<username>",
			alias = "rhn",
			entitlement = 42,
			executor = PLAYER_OR_CONSOLE)
	public void onRhenowarPremium(final CommandSender sender, final String label, final String sublabel, final String username)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Player player = util.givePlayer(username);
			if(player != null)
				try
				{
					util.hasPremium(player, (final Object result) ->
					{
						if(result instanceof Boolean)
						{
							final boolean premium = (boolean) result;
							AbstractCommands.doSend(sender, "PlayerHasPremium", null, username, premium ? '2' : '4', premium);
							return true;
						}
						else
							return false;
					});
				}
				catch(final IOException e)
				{
					AbstractCommands.doSend(sender, "channel.error", null, util.getUtilChannel().giveName(), e.getMessage());
				}
			else
				AbstractCommands.doSendPlayerIsOffline(sender, username);
		}
	}
	
	@CommandHandler(
			label = "rhenowar",
			sublabel = {"properties", "prs"},
			description = "Dev command.",
			usage = "<...>",
			alias = "rhn",
			entitlement = 54,
			executor = PLAYER_OR_CONSOLE)
	public void onRhenowarProperties(final CommandSender sender, final String label, final String sublabel, final String... arguments)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final boolean isPlayer = sender instanceof Player;
			final int amount = arguments == null ? 0 : arguments.length;
			if(amount >= 1)
			{
					String message = "";
					switch(arguments[0])
					{
						case "net.polishgames.rhenowar.util.UtilPlugin":
							if(amount == 1)
								message += this.givePlugin();
							break;
						case "net.polishgames.rhenowar.util.Util":
							if(amount == 1)
								message += util;
							break;
						case "net.polishgames.rhenowar.util.Commands":
							if(amount == 1)
								message += this;
							break;
						case "net.polishgames.rhenowar.util.ProtocolVersion":
							if(amount == 1 || amount == 2)
								message += amount == 1 ? util.giveServerVersion() : ProtocolVersion.giveValue(arguments[1].toLowerCase());
							break;
						case "net.polishgames.rhenowar.util.WorldData":
							if(amount == 1 || amount == 2)
								message += WorldData.giveWorldData(amount == 1 ? (isPlayer ? ((Player) sender).getWorld() : util.giveLobby()) : util.giveWorld(arguments[1]));
							break;
						case "java.util.Properties":
							if(amount == 1)
								message += System.getProperties();
							break;
						case "org.bukkit.scoreboard.Scoreboard":
							if(amount == 1)
							{
								final Scoreboard scoreboard = isPlayer ? ((Player) sender).getScoreboard() : util.giveUtilScoreboard();
								message += scoreboard.getClass().getName() + "{entries => " + scoreboard.getEntries() + ", objectives => " + scoreboard.getObjectives() + ", teams => [";
								final Set<Team> teams = scoreboard.getTeams();
								for(final Team team : teams)
									message += "{name => " + team.getName() + ", displayName => " + team.getDisplayName() + ", pre/suf => " + team.getPrefix() + "(%)" + team.getSuffix() + ", size => " + team.getSize() + ", entries => " + team.getEntries() + ", options => {COLLISION_RULE => " + team.getOption(Option.COLLISION_RULE) + ", DEATH_MESSAGE_VISIBILITY => " + team.getOption(Option.DEATH_MESSAGE_VISIBILITY) + ", NAME_TAG_VISIBILITY => " + team.getOption(Option.NAME_TAG_VISIBILITY) + "}, scoreboard => " + team.getScoreboard() + "}, ";
								if(teams.size() > 0)
									message = message.substring(0, message.length() - 2);
								message += "]}";
							}
							break;
					}
					if(message.isEmpty())
						AbstractCommands.doSendNoSuccess(sender, label);
					else if(isPlayer)
						util.doUtilSendMessage((Player) sender, message);
					else
						util.doSendColouredConsole(util.giveUtilPlugin(), message);
			}
		}
	}
	
}
