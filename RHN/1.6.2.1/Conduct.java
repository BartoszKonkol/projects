package net.polishgames.rhenowar.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.polishgames.rhenowar.util.auth.EventsAuth;
import net.polishgames.rhenowar.util.auth.PlayerAuth;
import net.polishgames.rhenowar.util.command.CommandHandler;
import net.polishgames.rhenowar.util.command.CommandHandlerData;
import net.polishgames.rhenowar.util.command.RhenowarCommand;
import net.polishgames.rhenowar.util.minigame.EventsMinigame;
import net.polishgames.rhenowar.util.minigame.Minigame;
import net.polishgames.rhenowar.util.minigame.UtilMinigame;
import net.polishgames.rhenowar.util.mysql.Argument;
import net.polishgames.rhenowar.util.mysql.MySQL;
import net.polishgames.rhenowar.util.mysql.Table;
import net.polishgames.rhenowar.util.protocol.Packet;
import net.polishgames.rhenowar.util.protocol.PacketClientBlockDig;
import net.polishgames.rhenowar.util.protocol.PacketServerMapChunk;
import net.polishgames.rhenowar.util.protocol.PacketServerUpdateSign;
import net.polishgames.rhenowar.util.rank.Entitlement;
import net.polishgames.rhenowar.util.rank.EventsRank;
import net.polishgames.rhenowar.util.rank.PlayerRank;
import net.polishgames.rhenowar.util.rank.Rank;
import net.polishgames.rhenowar.util.task.SyncTimerTask;
import net.polishgames.rhenowar.util.world.EventsWorld;

final class Conduct extends RhenowarObject
{
	
	private final UtilPlugin plugin;
	private final Util util;
	
	Conduct(final UtilPlugin plugin)
	{
		this.plugin = Util.nonNull(plugin);
		this.util = this.plugin.util;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("plugin", this.plugin);
		map.put("util", this.util);
		return map;
	}
	
	final void onEnable()
	{
		try
		{
			final MySQL sql = this.util.giveSQL();
			sql.doExecuteStatement(sql.hasStatementBasic() ? sql.giveStatementBasic() : sql.giveStatement(), "SET SQL_MODE='ALLOW_INVALID_DATES';");
		}
		catch(final SQLException e)
		{
			this.util.doReportErrorSQL(e);
		}
		final Map<Table, Collection<Argument>> sql = new HashMap<Table, Collection<Argument>>();
		sql.put(PlayerAuth.TABLE, PlayerAuth.COLUMNS.values());
		sql.put(PlayerAccount.TABLE, PlayerAccount.COLUMNS.values());
		sql.put(PlayerRank.TABLE, PlayerRank.COLUMNS.values());
		for(final Table table : sql.keySet())
			try
			{
				final Collection<Argument> columns = sql.get(table);
				this.util.doSQL(table, 2 << 7, columns.toArray(new Argument[columns.size()]));
			}
			catch(final SQLException e)
			{
				this.util.doReportErrorSQL(e);
			}
		for(final String[] library : new String[][]{
			new String[]{"JNF-" + this.util.giveUtilConfig().getString(this.util.giveUtilConfigPrefix() + "JNF").replace('.', '_'), "Java New Functions"},
			new String[]{"luaj-jse-" + this.util.giveUtilConfig().getString(this.util.giveUtilConfigPrefix() + "Luaj"), "LuaJ"},
		})
			try
			{
				final File file = new File(this.plugin.getDataFolder(), library[0] + ".jar");
				if(!(file.exists() && file.isFile()))
					Util.doThrowFNFE();
				final ClassLoader classLoader = ClassLoader.giveClassLoader(ClassLoader.giveURL(file), this.getClass());
				if(library[1].equalsIgnoreCase("luaj"))
					this.util.setLoaderLuaj(classLoader);
				else
					this.util.setLoaderJNF(classLoader);
			}
			catch(final IOException e)
			{
				this.util.doReportError(library[1] + " isn't loaded", e);
			}
		if(this.util.givePluginManager().getPlugin("Vault") != null)
			this.util.setVault(new VaultSupport(this.plugin));
		try
		{
			final LangPack langPack = new LangPack(this.util.giveUtilLuaLangFile());
			this.util.setUtilLangPack(langPack);
			this.util.addLangPack(langPack);
			
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
		}
		try
		{
			this.util.setUtilMinigame(this.util.getLoaderLuaj().doElicitClass(UtilMinigame.class.getName()).giveLastObject());
			if(this.util.hasUtilMinigame())
				this.util.giveUtilMinigameSupport().setUtil(this.util);
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
		}
		this.util.addCommandListener(new Commands(this.plugin));
		CommandMap commandMap = null;
		try
		{
			commandMap = this.util.giveServerData().giveCommandMap();
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
		}
		if(commandMap != null)
		{
			final Map<String, List<CommandHandlerData>> commands = new HashMap<String, List<CommandHandlerData>>();
			for(final Rhenowar listener : this.util.giveCommandListeners())
				for(final Method method : listener.getClass().getDeclaredMethods())
				{
					final Class<CommandHandler> handlerClass = CommandHandler.class;
					if(this.util.isAnnotation(method, handlerClass))
					{
						final CommandHandler handler = method.getAnnotation(handlerClass);
						if(handler != null)
						{
							String label = handler.label();
							if(label != null && !label.isEmpty())
							{
								label = label.toLowerCase();
								if(!commands.containsKey(label))
									commands.put(label, new ArrayList<CommandHandlerData>());
								commands.get(label).add(new CommandHandlerData(handler, method, listener));
							}
						}
					}
				}
			final List<Command> commandsRhenowar = new ArrayList<Command>();
			for(final String label : commands.keySet())
			{
				final List<CommandHandlerData> handlers = commands.get(label);
				if(!handlers.isEmpty())
					commandsRhenowar.add(new RhenowarCommand(handlers.toArray(new CommandHandlerData[handlers.size()])));
			}
			if(!commandsRhenowar.isEmpty())
			{
				Map<String, Command> commandsBukkit = null;
				if(commandMap instanceof SimpleCommandMap)
					try
					{
						commandsBukkit = (Map<String, Command>) new FieldData(commandMap, "knownCommands").giveField().get(commandMap);
					}
					catch(final ReflectiveOperationException e)
					{
						e.printStackTrace();
					}
				final String fallback = this.util.giveUtilName().toLowerCase(Locale.ENGLISH).trim();
				if(commandsBukkit != null)
					for(final Command command : commandsRhenowar)
					{
						final String name = command.getName().toLowerCase(Locale.ENGLISH).trim();
						final List<String> elements = new ArrayList<String>();
						for(final String alias : command.getAliases())
						{
							final String value = alias.toLowerCase(Locale.ENGLISH).trim();
							elements.add((commandsBukkit.containsKey(value) ? "~" : "") + value);
						}
						elements.add(name);
						for(String element : elements)
						{
							boolean impose = true;
							if(element.startsWith("~"))
							{
								impose = false;
								element = element.substring(1);
							}
							final String elementFallback = fallback + ":" + element;
							if(!commandsBukkit.containsKey(elementFallback))
								commandsBukkit.put(elementFallback, command);
							if(impose)
							{
								if(commandsBukkit.containsKey(element))
									commandsBukkit.remove(element);
								commandsBukkit.put(element, command);
							}
						}
						command.setLabel(name);
						command.register(commandMap);
					}
				else
					commandMap.registerAll(fallback, commandsRhenowar);
			}
		}
		final PluginManager manager = Util.nonNull(this.util).givePluginManager();
		for(final AbstractEvents events : new AbstractEvents[]{new Events(this.plugin), new EventsAuth(this.plugin), new EventsMinigame(this.plugin), new EventsWorld(this.plugin), new EventsRank(this.plugin)})
			manager.registerEvents(events, this.plugin);
		final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		for(final Packet packet : new Packet[]{new PacketServerUpdateSign(this.plugin), new PacketServerMapChunk(this.plugin), new PacketClientBlockDig(this.plugin)})
			protocolManager.addPacketListener(packet);
		this.util.addActionListener(new Actions(this.plugin));
		final UtilChannel channel = new UtilChannel(this.util.giveUtilName().toLowerCase(), this.plugin);
		if(channel.doRegister())
		{
			this.util.addChannel(channel);
			this.util.setUtilChannel(channel);
		}
		else
			this.util.doSendConsole(Level.SEVERE, "Channel of " + this.util.giveUtilName() + " isn't registered! (" + channel.toString() + ")");
		final String rankPrefix = this.util.giveUtilConfigPrefix() + "rank";
		final ConfigurationSection rankSection = this.util.giveUtilConfig().getConfigurationSection(rankPrefix);
		if(rankSection != null)
			for(final String rank : rankSection.getKeys(false))
			{
				final String rankPrefixNew = rankPrefix + "." + rank + ".", rankPrefixLevel = rankPrefixNew + "level.";
				this.util.giveRanks().add(new Rank(rank, ChatColor.getByChar(this.util.giveUtilConfig().getString(rankPrefixNew + "color")), new Entitlement(this.util.giveUtilConfig().getInt(rankPrefixLevel + "build", 1), this.util.giveUtilConfig().getInt(rankPrefixLevel + "special", 1)), this.util.giveUtilConfig().getStringList(rankPrefixNew + "permissions")));
			}
		if(this.util.giveRanks().isEmpty())
			this.util.giveRanks().add(new Rank("default", new Entitlement(0)));
		final File container = this.util.giveServer().getWorldContainer();
		if(container.exists() && container.isDirectory())
			for(final File directory : container.listFiles())
				if(directory.exists() && directory.isDirectory())
				{
					final String name = directory.getName();
					if(name.startsWith("game#") && name.length() >= 0xE)
						this.util.doDeleteDirectory(directory);
				}
		if(this.util.hasUtilWorlds())
		{
			try
			{
				final World world = this.util.giveServer().getWorlds().get(0);
				if(this.util.giveUtilWorlds().giveObject(world.getName()) == null)
					this.util.addWorld(world);
			}
			catch(final IndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
			for(final Object obj : this.util.giveUtilWorlds())
				if(obj instanceof WorldData)
				{
					final WorldData data = (WorldData) obj;
					if(WorldData.addWorldData(data) != null && this.util.giveWorld(data.giveName()) == null && this.util.doLoadWorld(data) != data.giveWorld())
						this.util.doSendConsole(Level.SEVERE, "World " + data.giveName() + " wasn't loaded corectly!");
				}
		}
		this.util.giveCalculatorTPS().doStart();
		new SyncTimerTask().runTaskRepeating(0, this.util.giveUtilConfig().getLong(this.util.giveUtilConfigPrefix() + "timer"));
	}
	
	final void onDisable()
	{
		this.util.giveCalculatorTPS().doStop();
		if(this.util.hasUtilWorlds())
			try
			{
				this.util.giveUtilWorlds().close();
			}
			catch(final IOException e)
			{
				e.printStackTrace();
			}
		for(final World world : new ArrayList<World>(Minigame.giveWorlds()))
			if(world != null)
				this.util.doDeleteWorldMinigame(world);
		try
		{
			this.util.giveSQL().close();
		}
		catch(final SQLException e)
		{
			this.util.doReportErrorSQL(e);
		}
	}
	
}
