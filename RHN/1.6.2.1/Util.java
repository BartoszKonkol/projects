package net.polishgames.rhenowar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;
import org.bukkit.util.Vector;
import net.polishgames.rhenowar.util.UtilChannel.RequestData;
import net.polishgames.rhenowar.util.auth.Crypt;
import net.polishgames.rhenowar.util.captcha.Captcha;
import net.polishgames.rhenowar.util.craft.Craft;
import net.polishgames.rhenowar.util.craft.PlayerData;
import net.polishgames.rhenowar.util.craft.ServerData;
import net.polishgames.rhenowar.util.craft.Tool;
import net.polishgames.rhenowar.util.event.ISignEvent;
import net.polishgames.rhenowar.util.minigame.Action;
import net.polishgames.rhenowar.util.minigame.ActionCom;
import net.polishgames.rhenowar.util.minigame.ActionEnd;
import net.polishgames.rhenowar.util.minigame.ActionInv;
import net.polishgames.rhenowar.util.minigame.ItemAction;
import net.polishgames.rhenowar.util.minigame.Minigame;
import net.polishgames.rhenowar.util.minigame.PlayerMinigame;
import net.polishgames.rhenowar.util.minigame.RhenowarPluginMinigame;
import net.polishgames.rhenowar.util.minigame.UtilMinigameSupport;
import net.polishgames.rhenowar.util.mysql.Argument;
import net.polishgames.rhenowar.util.mysql.ClausesData;
import net.polishgames.rhenowar.util.mysql.ConnectionData;
import net.polishgames.rhenowar.util.mysql.MySQL;
import net.polishgames.rhenowar.util.mysql.Query;
import net.polishgames.rhenowar.util.mysql.RhenowarPlayerSQL;
import net.polishgames.rhenowar.util.mysql.Table;
import net.polishgames.rhenowar.util.mysql.TypeData;
import net.polishgames.rhenowar.util.mysql.Value;
import net.polishgames.rhenowar.util.rank.Entitlement;
import net.polishgames.rhenowar.util.rank.Entitlement.TypeEntitlement;
import net.polishgames.rhenowar.util.rank.PlayerRank;
import net.polishgames.rhenowar.util.rank.Rank;
import net.polishgames.rhenowar.util.world.WorldGenerator;
import net.polishgames.rhenowar.util.world.WorldGenerator.TypeWorld;
import net.polishgames.rhenowar.util.world.area.Area;

public final class Util extends RhenowarObject
{

	private static volatile Util util;
	
	private final UtilPlugin utilPlugin;
	private final FileConfiguration utilConfig, utilConfigLocations, utilConfigSigns;
	private final FileRHN utilWorlds;
	private final File utilConfigLocationsFile, utilConfigSignsFile, utilLuaInventoryFile, utilLuaLangFile, utilWorldsFile;
	private final Map<String, Plugin> plugins;
	private final Map<String, Channel> channels;
	private final List<IRhenowar> worn;
	private final List<Rhenowar> actionListeners;
	private final List<Rhenowar> commandListeners;
	private final Map<Language, Properties> lang;
	private final List<Rank> ranks;
	private final Craft craft;
	private final MySQL sql;
	private final CalculatorTPS calculatorTPS;
	
	public Util(final UtilPlugin utilPlugin, final ServicePriority priority)
	{
		this.utilPlugin = Util.nonNull(utilPlugin);
		this.utilConfigLocationsFile = new File(this.giveUtilPlugin().getDataFolder(), this.giveUtilPlugin().locationsFileName);
		this.utilConfigSignsFile = new File(this.giveUtilPlugin().getDataFolder(), this.giveUtilPlugin().signsFileName);
		this.utilLuaInventoryFile = new File(this.giveUtilPlugin().getDataFolder(), this.giveUtilPlugin().inventoryFileName);
		this.utilLuaLangFile = new File(this.giveUtilPlugin().getDataFolder(), this.giveUtilPlugin().langFileName);
		this.utilWorldsFile = new File(this.giveUtilPlugin().getDataFolder(), this.giveUtilPlugin().worldsFileName);
		this.utilConfig = this.giveUtilPlugin().getConfig();
		this.utilConfigLocations = YamlConfiguration.loadConfiguration(this.giveUtilConfigLocationsFile());
		this.utilConfigSigns = YamlConfiguration.loadConfiguration(this.giveUtilConfigSignsFile());
		this.plugins = new HashMap<String, Plugin>();
		this.addPlugin(this.giveUtilPlugin());
		this.channels = new HashMap<String, Channel>();
		this.worn = new ArrayList<IRhenowar>();
		this.actionListeners = new ArrayList<Rhenowar>();
		this.commandListeners = new ArrayList<Rhenowar>();
		this.lang = new HashMap<Language, Properties>();
		this.ranks = new ArrayList<Rank>();
		this.craft = new Craft(this);
		MySQL sql = null;
		try
		{
			final String database = this.giveUtilConfigPrefix() + "database.";
			sql = this.giveSQL(this.giveUtilConfig().getString(database + "url"), this.giveUtilConfig().getString(database + "user"), new Password(this.giveUtilConfig().getString(database + "pass")), this.giveUtilConfig().getString(database + "name"));
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
		}
		catch(final IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		this.sql = sql;
		this.giveServicesManager().register(Util.class, this, this.giveUtilPlugin(), Util.nonNull(priority));
		Util.util = this;
		FileRHN worlds = null;
		try
		{
			worlds = new FileRHN(this.giveUtilWorldsFile());
		}
		catch(final IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		this.utilWorlds = worlds;
		this.calculatorTPS = new CalculatorTPS(this.giveUtilPlugin());
	}
	
	public Util(final UtilPlugin utilPlugin)
	{
		this(utilPlugin, ServicePriority.Low);
	}
	
	private ClassLoader loaderJNF, loaderLuaj;
	private UtilChannel utilChannel;
	private VaultSupport vault;
	private Object utilMinigame;
	private LangPack utilLangPack;
	private UtilMinigameSupport utilMinigameSupport;
	private Scoreboard utilScoreboard;
	private volatile long ping;
	private volatile IOException exceptionIO;
	private volatile SQLException exceptionSQL;
	
	public final UtilPlugin giveUtilPlugin()
	{
		return this.utilPlugin;
	}
	
	public final File giveUtilConfigLocationsFile()
	{
		return this.utilConfigLocationsFile;
	}
	
	public final File giveUtilConfigSignsFile()
	{
		return this.utilConfigSignsFile;
	}
	
	public final File giveUtilLuaInventoryFile()
	{
		return this.utilLuaInventoryFile;
	}
	
	public final File giveUtilLuaLangFile()
	{
		return this.utilLuaLangFile;
	}
	
	public final File giveUtilWorldsFile()
	{
		return this.utilWorldsFile;
	}
	
	public final FileConfiguration giveUtilConfig()
	{
		return this.utilConfig;
	}
	
	public final FileConfiguration giveUtilConfigLocations()
	{
		return this.utilConfigLocations;
	}
	
	public final FileConfiguration giveUtilConfigSigns()
	{
		return this.utilConfigSigns;
	}
	
	public final List<Rank> giveRanks()
	{
		return this.ranks;
	}
	
	public final CalculatorTPS giveCalculatorTPS()
	{
		return this.calculatorTPS;
	}
	
	public final Craft giveCraft()
	{
		return this.craft;
	}
	
	public final Server giveServer()
	{
		return this.giveUtilPlugin().getServer();
	}
	
	public final PluginManager givePluginManager()
	{
		return this.giveServer().getPluginManager();
	}
	
	public final ServicesManager giveServicesManager()
	{
		return this.giveServer().getServicesManager();
	}
	
	public final ScoreboardManager giveScoreboardManager()
	{
		return this.giveServer().getScoreboardManager();
	}
	
	public final ConsoleCommandSender giveConsoleSender()
	{
		return this.giveServer().getConsoleSender();
	}
	
	public final ConsoleCommandSender giveColouredConsoleSender()
	{
		try
		{
			return this.giveServerData().giveColouredConsoleSender();
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
			return this.giveConsoleSender();
		}
	}
	
	public final Logger giveUtilLogger()
	{
		return this.giveUtilPlugin().getLogger();
	}
	
	public final String giveUtilName()
	{
		return this.giveUtilPlugin().giveName();
	}
	
	public final String giveUtilVersion()
	{
		return this.giveUtilPlugin().giveVersion();
	}
	
	public final Series giveUtilSeries()
	{
		return Series.valueOf(this.giveUtilVersion());
	}
	
	public final String giveUtilConfigPrefix()
	{
		return this.giveUtilName() + ".";
	}
	
	public final World giveLobby()
	{
		return this.giveWorld(this.giveUtilConfig().getString(this.giveUtilConfigPrefix() + "improve.lobby.world"));
	}
	
	public final BukkitScheduler giveScheduler()
	{
		return this.giveServer().getScheduler();
	}
	
	public final double giveTPS()
	{
		return this.giveCalculatorTPS().getTPS();
	}
	
	public final UtilMinigameSupport giveUtilMinigameSupport()
	{
		return this.utilMinigameSupport;
	}
	
	public final Series giveSeries()
	{
		return UtilPlugin.SERIES;
	}
	
	public final String giveSeriesVersion()
	{
		return this.giveSeries().giveVersion();
	}
	
	public final short giveSeriesNumber()
	{
		return this.giveSeries().giveSeries();
	}
	
	public final int giveSeriesBuild()
	{
		return this.giveSeries().giveBuild();
	}
	
	public final String giveServerIP()
	{
		return this.giveServer().getIp();
	}
	
	public final String givePlayerIP(final Player player)
	{
		return Util.nonNull(player).getAddress().getAddress().getHostAddress();
	}
	
	public final Rank giveRankDefault()
	{
		return this.giveRanks().get(0);
	}
	
	public final Rank giveRank(final String name, final List<Rank> ranks)
	{
		Util.nonEmpty(name);
		for(final Rank rank : Util.nonNull(ranks))
			if(rank.giveName().equalsIgnoreCase(name))
				return rank;
		return null;
	}
	
	public final Rank giveRank(final String name)
	{
		return this.giveRank(name, this.giveRanks());
	}
	
	public final Rank givePlayerRank(final OfflinePlayer player)
	{
		Rank rank = null;
		try
		{
			rank = PlayerRank.givePlayerRank(Util.nonNull(player)).getRank();
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
		}
		if(rank == null)
			rank = this.giveRankDefault();
		return rank;
	}
	
	public final Entitlement giveEntitlement(final String name, final List<Rank> ranks)
	{
		final Rank rank = this.giveRank(name, ranks);
		if(rank != null)
			return rank.giveEntitlement();
		else
			return null;
	}
	
	public final Entitlement giveEntitlement(final String name)
	{
		return this.giveEntitlement(name, this.giveRanks());
	}
	
	public final Entitlement givePlayerEntitlement(final OfflinePlayer player)
	{
		Entitlement entitlement = null;
		try
		{
			entitlement = PlayerRank.givePlayerRank(Util.nonNull(player)).getEntitlement();
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
		}
		if(entitlement == null)
			this.givePlayerRank(player).giveEntitlement();
		return entitlement;
	}
	
	public final int givePlayerEntitlementLevel(final OfflinePlayer player, final TypeEntitlement type)
	{
		return this.givePlayerEntitlement(player).giveEntitlementLevel(Util.nonNull(type));
	}
	
	public final byte givePlayerEntitlementThreshold(final OfflinePlayer player, final TypeEntitlement type)
	{
		return this.givePlayerEntitlement(player).giveEntitlementThreshold(Util.nonNull(type)).giveThreshold();
	}
	
	public final ServerType giveServerType()
	{
		ServerType type = null;
		try
		{
			type = ServerType.valueOf(this.giveUtilConfig().getString(this.giveUtilConfigPrefix() + "type").toUpperCase());
		}
		catch(final NullPointerException | IllegalArgumentException e) {}
		if(type == null)
			type = ServerType.DEFAULT;
		return type;
	}
	
	public final Argument giveArgumentName()
	{
		return new Argument("name", TypeData.STRING);
	}
	
	public final Argument giveArgumentVer()
	{
		return new Argument("ver", TypeData.SHORT);
	}
	
	public final Argument giveArgumentAgain()
	{
		return new Argument("again", TypeData.INTEGER);
	}
	
	public final Value<String> giveValueName(final String value)
	{
		return new Value<String>(this.giveArgumentName(), value);
	}
	
	public final Value<Short> giveValueVer(final short value)
	{
		return new Value<Short>(this.giveArgumentVer(), value);
	}
	
	public final Value<Short> giveValueVer()
	{
		return this.giveValueVer(this.giveSeriesNumber());
	}
	
	public final Value<Integer> giveValueAgain(final int value)
	{
		return new Value<Integer>(this.giveArgumentAgain(), value);
	}
	
	public final Value<Integer> giveValueAgain()
	{
		return this.giveValueAgain(0);
	}
	
	public final ProtocolVersion giveServerVersion()
	{
		final String[] version = this.giveServer().getBukkitVersion().split("-");
		return ProtocolVersion.giveValue(version[0] + (version[1].startsWith("pre") ? "-" + version[1] : ""));
	}
	
	public final PlayerData<?, ?> givePlayerData(final Player player) throws ReflectiveOperationException
	{
		return this.giveCraft().givePlayerData(Util.nonNull(player));
	}
	
	public final ServerData<?, ?> giveServerData(final Server server) throws ReflectiveOperationException
	{
		return this.giveCraft().giveServerData(Util.nonNull(server));
	}
	
	public final ServerData<?, ?> giveServerData() throws ReflectiveOperationException
	{
		return this.giveCraft().giveServerData();
	}
	
	public final World giveWorld(final String name)
	{
		return this.giveServer().getWorld(Util.nonEmpty(name));
	}
	
	public final World giveWorld(final UUID uuid)
	{
		return this.giveServer().getWorld(Util.nonNull(uuid));
	}
	
	public final World giveWorld(final Entity entity)
	{
		return Util.nonNull(entity).getWorld();
	}
	
	public final Player givePlayer(final String nick)
	{
		return this.giveServer().getPlayer(Util.nonEmpty(nick));
	}
	
	public final Player givePlayer(final UUID uuid)
	{
		return this.giveServer().getPlayer(Util.nonNull(uuid));
	}
	
	@SuppressWarnings("deprecation")
	public final OfflinePlayer givePlayerOffline(final String nick)
	{
		return this.giveServer().getOfflinePlayer(Util.nonEmpty(nick));
	}
	
	public final OfflinePlayer givePlayerOffline(final UUID uuid)
	{
		return this.giveServer().getOfflinePlayer(Util.nonNull(uuid));
	}
	
	public final ProtocolVersion givePlayerVersion(final Player player) throws ReflectiveOperationException
	{
		final List<ProtocolVersion> versions = this.givePlayerData(player).giveProtocolVersions();
		for(final ProtocolVersion version : versions)
			if(version.giveType().isOfficial())
				return version;
		if(versions.size() > 0)
			return versions.get(0);
		else
			return ProtocolVersion.UNKNOWN;
	}
	
	public final String givePlayerName(final OfflinePlayer player)
	{
		return Util.nonEmpty(Util.nonNull(player).getName().replaceAll("\\W", "").toLowerCase());
	}
	
	public final Region giveRegion(final Player player)
	{
		return Region.giveRegion(player);
	}
	
	public final Region giveRegion(final Player player, final boolean messageError)
	{
		return Region.giveRegion(player, messageError);
	}
	
	public final DualRegion giveRegionDual(final Region region, final DimensionPosition dimension)
	{
		return Util.nonNull(region).giveCutHalf(Util.nonNull(dimension));
	}
	
	public final DualRegion giveRegionDual(final Player player, final DimensionPosition dimension)
	{
		return this.giveRegionDual(this.giveRegion(player), dimension);
	}
	
	public final WorldData giveWorldData(final World world)
	{
		return WorldData.giveWorldData(world);
	}
	
	// =========================
	// ==        MySQL        ==
	
	public final MySQL giveSQL(final ConnectionData data) throws SQLException
	{
		return new MySQL(data);
	}
	
	public final MySQL giveSQL(final String url, final String user, final Password password, final String dbname) throws SQLException
	{
		return new MySQL(url, user, password, dbname);
	}
	
	public final MySQL giveSQL(final String url, final String user, final Password password) throws SQLException
	{
		return new MySQL(url, user, password);
	}
	
	public final MySQL giveSQL()
	{
		return this.sql;
	}
	
	public final boolean hasSQL()
	{
		return this.giveSQL() != null;
	}
	
	// =========================
	
	private final MySQL sqlQuery(final MySQL sql, final Task<Statement, Query> taskQuery, final Task<MySQL, Boolean> taskPreclose) throws SQLException
	{
		if(Util.nonNull(sql).isClosed())
			return null;
		final Statement statement = sql.giveStatement();
		final MySQL result = sql.doExecuteStatement(statement, Util.nonNull(taskQuery).task(statement));
		if(result == null || Util.nonNull(Util.nonNull(taskPreclose).task(result)))
			sql.close(statement);
		return result;
	}
	
	private final MySQL sqlQuery(final MySQL sql, final Task<Statement, Query> taskQuery) throws SQLException
	{
		return this.sqlQuery(sql, taskQuery, (final MySQL argument) ->
		{
			return true;
		});
	}
	
	// =========================
	
	public final MySQL doSQL(final MySQL sql, final Table table, final int length, final Argument... columns) throws SQLException
	{
		return this.sqlQuery(sql, (final Statement statement) ->
		{
			return Query.giveQueryCreate(table, this.toList(columns), length);
		});
	}
	
	public final MySQL doSQL(final MySQL sql, final Table table, final Argument... columns) throws SQLException
	{
		return this.doSQL(sql, table, 2 << 5, columns);
	}
	
	public final MySQL doSQL(final Table table, final int length, final Argument... columns) throws SQLException
	{
		return this.doSQL(this.giveSQL(), table, length, columns);
	}
	
	public final MySQL doSQL(final Table table, final Argument... columns) throws SQLException
	{
		return this.doSQL(table, 2 << 5, columns);
	}
	
	public final MySQL addSQL(final MySQL sql, final Table table, final Value<?>... values) throws SQLException
	{
		return this.sqlQuery(sql, (final Statement statement) ->
		{
			return Query.giveQueryInsert(table, this.toList(values));
		});
	}
	
	public final MySQL addSQL(final Table table, final Value<?>... values) throws SQLException
	{
		return this.addSQL(this.giveSQL(), table, values);
	}
	
	public final MySQL delSQL(final MySQL sql, final Table table, final ClausesData clauses) throws SQLException
	{
		return this.sqlQuery(sql, (final Statement statement) ->
		{
			return Query.giveQueryDelete(table, clauses);
		});
	}
	public final MySQL delSQL(final Table table, final ClausesData clauses) throws SQLException
	{
		return this.delSQL(this.giveSQL(), table, clauses);
	}
	
	public final MySQL delSQL(final MySQL sql, final Table table) throws SQLException
	{
		return this.delSQL(sql, table, ClausesData.giveClausesData());
	}
	
	public final MySQL delSQL(final Table table) throws SQLException
	{
		return this.delSQL(this.giveSQL(), table);
	}
	
	public final MySQL setSQL(final MySQL sql, final Table table, final ClausesData clauses, final Value<?>... values) throws SQLException
	{
		return this.sqlQuery(sql, (final Statement statement) ->
		{
			return Query.giveQueryUpdate(table, this.toList(values), clauses);
		});
	}
	
	public final MySQL setSQL(final Table table, final ClausesData clauses, final Value<?>... values) throws SQLException
	{
		return this.setSQL(this.giveSQL(), table, clauses, values);
	}
	
	public final MySQL setSQL(final MySQL sql, final Table table, final Value<?>... values) throws SQLException
	{
		return this.setSQL(sql, table, ClausesData.giveClausesData(), values);
	}
	
	public final MySQL setSQL(final Table table, final Value<?>... values) throws SQLException
	{
		return this.setSQL(this.giveSQL(), table, values);
	}
	
	public final List<List<Value<?>>> getSQL(final MySQL sql, final Table table, final ClausesData clauses, final Argument... columns) throws SQLException
	{
		Util.nonNull(sql);
		final List<List<Value<?>>> result = new ArrayList<List<Value<?>>>();
		this.exceptionSQL = null;
		if(this.sqlQuery(sql, (final Statement statement) ->
		{
			return Util.nonNull(columns).length > 0 ? Query.giveQuerySelect(table, this.toList(columns), clauses) : Query.giveQuerySelect(table, clauses);
		}, (final MySQL argument) ->
		{
			try
			{
				for(final List<Value<?>> values : argument.giveResultList(columns))
					result.add(values);
			}
			catch(final SQLException e)
			{
				this.exceptionSQL = e;
			}
			return true;
		}) == null && this.exceptionSQL == null)
			this.exceptionSQL = new SQLException(sql.isClosed() ? (sql.giveClassName() + ".isClosed() == true") : (this.giveClassName() + ".sqlQuery(" + MySQL.class.getName() + ", " + Task.class.getName() + ", " + Task.class.getName() + ") == null"));
		if(this.exceptionSQL != null)
			throw this.exceptionSQL;
		else
			return Collections.unmodifiableList(result);
	}

	public final List<List<Value<?>>> getSQL(final Table table, final ClausesData clauses, final Argument... columns) throws SQLException
	{
		return this.getSQL(this.giveSQL(), table, clauses, columns);
	}
	
	public final List<List<Value<?>>> getSQL(final MySQL sql, final Table table, final Argument... columns) throws SQLException
	{
		return this.getSQL(sql, table, ClausesData.giveClausesData(), columns);
	}
	
	public final List<List<Value<?>>> getSQL(final Table table, final Argument... columns) throws SQLException
	{
		return this.getSQL(this.giveSQL(), table, columns);
	}
	
	public final boolean hasSQL(final List<List<Value<?>>> list)
	{
		return Util.nonNull(list).size() > 0;
	}
	
	public final boolean hasSQL(final MySQL sql, final Table table, final ClausesData clauses, final Argument... columns) throws SQLException
	{
		return this.hasSQL(this.getSQL(sql, table, clauses, columns));
	}

	public final boolean hasSQL(final Table table, final ClausesData clauses, final Argument... columns) throws SQLException
	{
		return this.hasSQL(this.getSQL(table, clauses, columns));
	}
	
	public final boolean hasSQL(final MySQL sql, final Table table, final Argument... columns) throws SQLException
	{
		return this.hasSQL(this.getSQL(sql, table, columns));
	}
	
	public final boolean hasSQL(final Table table, final Argument... columns) throws SQLException
	{
		return this.hasSQL(this.getSQL(table, columns));
	}

	// ==        MySQL        ==
	// =========================
	
	public final Object giveContentValue(final RhenowarPlayerSQL player, final Argument argument) throws SQLException
	{
		Object result = null;
		for(final List<Value<?>> list : Util.nonNull(player).giveContentTable(Util.nonNull(argument)))
			for(final Value<?> value : list)
				if(argument.giveName().equalsIgnoreCase(value.giveName()) && argument.giveType() == value.giveType())
					result = value.giveValue();
		return result;
	}
	
	public final int giveContentValueAgain(final RhenowarPlayerSQL player) throws SQLException
	{
		final Object obj = this.giveContentValue(player, this.giveArgumentAgain());
		if(obj != null)
			return this.toInteger(obj);
		else
			return 0;
	}
	
	public final String giveFormattedNumber(final Number number, final int length)
	{
		if(length == 0)
		{
			Util.doThrowIAE();
			return null;
		}
		else
			return String.format("%0" + Math.abs(length) + "d", Util.nonNull(number).longValue());
	}
	
	public final String giveFormattedNumber(final Number number)
	{
		return this.giveFormattedNumber(number, 3);
	}
	
	public final <T> T[] giveCombinedArray(final T[] first, final T[] second)
	{
		final Class<?> type = Util.nonNull(first).getClass().getComponentType();
		if(type != Util.nonNull(second).getClass().getComponentType())
			Util.doThrowIAE();
		@SuppressWarnings("unchecked")
		final T[] array = (T[]) Array.newInstance(type, first.length + second.length);
		System.arraycopy(first, 0, array, 0, first.length);
		System.arraycopy(second, 0, array, first.length, second.length);
		return array;
	}
	
	public final List<FieldData> giveFields(final IRhenowar rhenowar)
	{
		final List<FieldData> list = new ArrayList<FieldData>();
		for(final Field field : Util.nonNull(rhenowar).getClass().getDeclaredFields())
			list.add(new FieldData(field).setRhenowar(rhenowar));
		return Collections.unmodifiableList(list);
	}
	
	public final String[] giveCaptcha(final Captcha captcha)
	{
		return String.valueOf(Util.nonNull(captcha)).replace('#', '\u2589').replace(" ", "  ").split("\000");
	}
	
	public final String[] giveCaptcha(final int whitespaces, final String text)
	{
		return this.giveCaptcha(new Captcha(whitespaces, text));
	}
	
	public final String[] giveCaptcha(final String text)
	{
		return this.giveCaptcha(new Captcha(text));
	}
	
	public final Captcha giveCaptcha()
	{
		return new Captcha();
	}
	
	public final Captcha giveCaptcha(final int whitespaces)
	{
		return new Captcha(whitespaces);
	}
	
	public final Crypt giveCrypt(final Password paassword)
	{
		return new Crypt(paassword);
	}
	
	public final Timestamp giveTimestamp(final long time)
	{
		return new Timestamp(time);
	}
	
	public final Timestamp giveTimestamp(final Date date)
	{
		return this.giveTimestamp(Util.nonNull(date).getTime());
	}
	
	public final Timestamp giveTimestamp()
	{
		return this.giveTimestamp(new Date());
	}
	
	public final long giveSeed(final World world)
	{
		return Util.nonNull(world).getSeed();
	}
	
	public final long giveSeed(final Player player)
	{
		return this.giveSeed(Util.nonNull(player).getWorld());
	}
	
	public final Random giveRandom()
	{
		return new Random();
	}
	
	public final Random giveRandom(final long seed)
	{
		return new Random(seed);
	}
	
	public final Random giveRandom(final World world)
	{
		return this.giveRandom(this.giveSeed(world));
	}
	
	public final Random giveRandom(final Player player)
	{
		return this.giveRandom(this.giveSeed(player));
	}
	
	public final double giveDistance(final double point1, final double point2)
	{
		return Math.sqrt(Math.pow(point1 - point2, 2.0D));
	}
	
	public final Class<?>[] giveTypes(final Object... parameters)
	{
		final Class<?>[] types = new Class<?>[Util.nonNull(parameters).length];
		for(int i = 0; i < types.length; i++)
		{
			final Object parameter = Util.nonNull(parameters[i]);
			try
			{
				types[i] =
						parameter instanceof	List		&&	!(parameter instanceof GenericList)			?	List		.class	:
						parameter instanceof	Collection	&&	!(parameter instanceof GenericCollection)	?	Collection	.class	:
						parameter instanceof	Map															?	Map			.class	:
						parameter instanceof	DataOutput													?	DataOutput	.class	:
						parameter instanceof	DataInput													?	DataInput	.class	:
						parameter instanceof	ItemStack													?	ItemStack	.class	:
						this.giveTagBasicClassNBT().isAssignableFrom(parameter.getClass()) && !this.giveTagClassNBT().isAssignableFrom(parameter.getClass()) ? this.giveTagBasicClassNBT() :
						parameter.getClass();
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
		}
		return types;
	}
	
	public final <T> T giveClass(final Class<T> clazz, final Class<?>[] types, final Object... parameters) throws ReflectiveOperationException, RuntimeException
	{
		return Util.nonNull(clazz).getConstructor(Util.nonNull(types)).newInstance(Util.nonNull(parameters));
	}
	
	public final <T> T giveClass(final Class<T> clazz, final Class<?> type, final Object... parameters) throws ReflectiveOperationException, RuntimeException
	{
		Util.nonNull(type);
		final int length = Util.nonNull(parameters).length;
		final Class<?>[] types = new Class<?>[length];
		for(int i = 0; i < length; i++)
			types[i] = type;
		return this.giveClass(clazz, types, parameters);
	}
	
	public final <T> T giveClass(final Class<T> clazz, final Object... parameters) throws ReflectiveOperationException, RuntimeException
	{
		return this.giveClass(clazz, this.giveTypes(parameters), parameters);
	}
	
	public final List<String> giveChatModifier(final String method, final String text)
	{
		Util.nonEmpty(method);
		List<String> result = Collections.emptyList();
		try
		{
			final Class<Tool> clazz = this.giveCraft().giveClassTool("ChatModifier");
			final Object obj = this.doInvokeMethod("give" + String.valueOf(method.charAt(0)).toUpperCase() + method.substring(1), this.giveClass(clazz, text));
			if(obj != null && obj instanceof GenericList)
			{
				final GenericList<?> list = (GenericList<?>) obj;
				if(list.giveGenericType() == String.class)
					result = (GenericList<String>) list;
			}
		}
		catch(final ReflectiveOperationException e) {}
		return Collections.unmodifiableList(result);
	}
	
	public final List<String> giveChatModifierEncode(final String text)
	{
		return this.giveChatModifier("encode", text);
	}
	
	public final List<String> giveChatModifierDecode(final String text)
	{
		return this.giveChatModifier("decode", text);
	}
	
	public final Object giveMethodNBT(final String method, final Object... parameters) throws ReflectiveOperationException
	{
		final Class<Tool> clazz = this.giveCraft().giveClassTool("NBTTag");
		return this.doInvokeMethod(method, this.doInvokeMethod("give" + clazz.getSimpleName(), clazz), parameters);
	}
	
	public final Object giveItemNBT(final ItemStack itemStack) throws ReflectiveOperationException
	{
		return this.giveMethodNBT("giveTag", Util.nonNull(itemStack));
	}
	
	public final Object giveTagNBT(final ItemStack itemStack) throws ReflectiveOperationException
	{
		final Object nbt = this.giveItemNBT(itemStack);
		if(nbt != null)
			return this.giveMethodNBT("giveTagRHN", nbt);
		else
			return null;
	}
	
	public final Object giveTagClearNBT() throws ReflectiveOperationException
	{
		return this.giveMethodNBT("giveTag");
	}
	
	public final Object giveTagNBT() throws ReflectiveOperationException
	{
		return this.giveMethodNBT("giveTagRHN", this.giveTagClearNBT());
	}
	
	public final Class<?> giveTagClassNBT() throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("giveTagClass");
		if(result != null && result instanceof Class)
			return (Class<?>) result;
		else
			return null;
	}
	
	public final Class<?> giveTagBasicClassNBT() throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("giveTagBasicClass");
		if(result != null && result instanceof Class)
			return (Class<?>) result;
		else
			return null;
	}
	
	public final Object giveItemNMS(final Object obj) throws ReflectiveOperationException
	{
		return this.giveMethodNBT("giveItemNMS", Util.nonNull(obj));
	}
	
	public final ItemStack giveItemCraft(final Object obj) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("giveItemCraft", Util.nonNull(obj));
		if(result instanceof ItemStack)
			return (ItemStack) result;
		else
			return null;
	}
	
	public final Util setObjectNBT(final Object tag, final String key, final Object value) throws ReflectiveOperationException
	{
		this.giveMethodNBT("set", Util.nonNull(tag), Util.nonNull(key), Util.nonNull(value));
		return this;
	}
	
	public final Util setObjectNBT(final ItemStack item, final String key, final Object value) throws ReflectiveOperationException
	{
		return this.setObjectNBT(this.giveTagNBT(item), key, value);
	}
	
	public final Object getObjectNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		return this.giveMethodNBT("get", Util.nonNull(tag), Util.nonNull(key));
	}
	
	public final Object getObjectNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getObjectNBT(this.giveTagNBT(item), key);
	}
	
	public final String getListElementNBT(final Object tag, final String key, final int index) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("get", Util.nonNull(tag), Util.nonNull(key), index);
		if(result != null && result instanceof String)
			return (String) result;
		else
			return null;
	}
	
	public final String getListElementNBT(final ItemStack item, final String key, final int index) throws ReflectiveOperationException
	{
		return this.getListElementNBT(this.giveTagNBT(item), key, index);
	}
	
	public final String getStringNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getString", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof String)
			return (String) result;
		else
			return null;
	}
	
	public final String getStringNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getStringNBT(this.giveTagNBT(item), key);
	}
	
	public final byte getByteNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getByte", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof Byte)
			return (byte) result;
		else
			return 0;
	}
	
	public final byte getByteNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getByteNBT(this.giveTagNBT(item), key);
	}
	
	public final short getShortNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getShort", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof Short)
			return (short) result;
		else
			return 0;
	}
	
	public final short getShortNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getShortNBT(this.giveTagNBT(item), key);
	}
	
	public final int getIntegerNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getInteger", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof Integer)
			return (int) result;
		else
			return 0;
	}
	
	public final int getIntegerNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getIntegerNBT(this.giveTagNBT(item), key);
	}
	
	public final long getLongNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getLong", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof Long)
			return (long) result;
		else
			return 0L;
	}
	
	public final long getLongNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getLongNBT(this.giveTagNBT(item), key);
	}
	
	public final float getFloatNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getFloat", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof Float)
			return (float) result;
		else
			return 0.0F;
	}
	
	public final float getFloatNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getFloatNBT(this.giveTagNBT(item), key);
	}
	
	public final double getDoubleNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getDouble", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof Double)
			return (double) result;
		else
			return 0.0D;
	}
	
	public final double getDoubleNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getDoubleNBT(this.giveTagNBT(item), key);
	}
	
	public final boolean getBooleanNBT(final Object tag, final String key) throws ReflectiveOperationException
	{
		final Object result = this.giveMethodNBT("getBoolean", Util.nonNull(tag), Util.nonNull(key));
		if(result != null && result instanceof Boolean)
			return (boolean) result;
		else
			return false;
	}
	
	public final boolean getBooleanNBT(final ItemStack item, final String key) throws ReflectiveOperationException
	{
		return this.getBooleanNBT(this.giveTagNBT(item), key);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name, final boolean structures, final String settings, final long seed)
	{
		return WorldCreator
				.name(Util.nonEmpty(name))
				.seed(seed)
				.generator(generator)
				.environment(Util.nonNull(environment))
				.type(Util.nonNull(type))
				.generateStructures(structures)
				.generatorSettings(Util.nonNull(settings))
			;
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name, final boolean structures, final String settings)
	{
		return this.giveWorldCreator(generator, environment, type, name, structures, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, environment, type, name, false, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name, final boolean structures, final long seed)
	{
		return this.giveWorldCreator(generator, environment, type, name, structures, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name, final boolean structures)
	{
		return this.giveWorldCreator(generator, environment, type, name, structures, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name, final String settings)
	{
		return this.giveWorldCreator(generator, environment, type, name, false, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name, final long seed)
	{
		return this.giveWorldCreator(generator, environment, type, name, false, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final WorldType type, final String name)
	{
		return this.giveWorldCreator(generator, environment, type, name, false, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name, final boolean structures, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, Util.nonNull(type).giveEnvironment(), type.giveVariant(), name, structures, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name, final boolean structures, final String settings)
	{
		return this.giveWorldCreator(generator, type, name, structures, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, type, name, false, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name, final boolean structures, final long seed)
	{
		return this.giveWorldCreator(generator, type, name, structures, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name, final boolean structures)
	{
		return this.giveWorldCreator(generator, type, name, structures, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name, final String settings)
	{
		return this.giveWorldCreator(generator, type, name, false, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name, final long seed)
	{
		return this.giveWorldCreator(generator, type, name, false, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final TypeWorld type, final String name)
	{
		return this.giveWorldCreator(generator, type, name, false, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name, final boolean structures, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, structures, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name, final boolean structures, final String settings)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, structures, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, false, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name, final boolean structures, final long seed)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, structures, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name, final boolean structures)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, structures, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name, final String settings)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, false, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name, final long seed)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, false, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final WorldType type, final String name)
	{
		return this.giveWorldCreator(generator, Environment.NORMAL, type, name, false, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name, final boolean structures, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, structures, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name, final boolean structures, final String settings)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, structures, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, false, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name, final boolean structures, final long seed)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, structures, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name, final boolean structures)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, structures, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name, final String settings)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, false, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name, final long seed)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, false, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final Environment environment, final String name)
	{
		return this.giveWorldCreator(generator, environment, WorldType.NORMAL, name, false, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name, final boolean structures, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, structures, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name, final boolean structures, final String settings)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, structures, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, false, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name, final boolean structures, final long seed)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, structures, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name, final boolean structures)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, structures, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name, final String settings)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, false, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name, final long seed)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, false, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final ChunkGenerator generator, final String name)
	{
		return this.giveWorldCreator(generator, TypeWorld.NORMAL, name, false, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name, final boolean structures, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, Util.nonNull(generator).giveType(), name, structures, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name, final boolean structures, final String settings)
	{
		return this.giveWorldCreator(generator, name, structures, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name, final String settings, final long seed)
	{
		return this.giveWorldCreator(generator, name, false, settings, seed);
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name, final boolean structures, final long seed)
	{
		return this.giveWorldCreator(generator, name, structures, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name, final boolean structures)
	{
		return this.giveWorldCreator(generator, name, structures, "", (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name, final String settings)
	{
		return this.giveWorldCreator(generator, name, false, settings, (long) Math.random());
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name, final long seed)
	{
		return this.giveWorldCreator(generator, name, false, "", seed);
	}
	
	public final WorldCreator giveWorldCreator(final WorldGenerator generator, final String name)
	{
		return this.giveWorldCreator(generator, name, false, "", (long) Math.random());
	}
	
	public final String giveWorldGeneratorOptions(final World world)
	{
		try
		{
			return String.valueOf(this.doInvokeMethod("getGeneratorOptions", this.doInvokeMethod("getWorldData", this.doInvokeMethod("getHandle", Util.nonNull(world)))));
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final Map<String, String> giveGameRule(final World world)
	{
		final Map<String, String> gamerule = new HashMap<String, String>();
		for(final String key : Util.nonNull(world).getGameRules())
			gamerule.put(key, world.getGameRuleValue(key));
		return Collections.unmodifiableMap(gamerule);
	}
	
	public final World giveMinigameWorld(final Minigame minigame)
	{
		return Util.nonNull(minigame).doGameLoad();
	}
	
	public final World giveMinigameWorld(final Area area, final RhenowarPluginMinigame plugin)
	{
		return this.giveMinigameWorld(Util.nonNull(area).toMinigame(Util.nonNull(plugin)));
	}
	
	public final Location giveSpawnLocation(final WorldData data)
	{
		final World world = data.giveWorld();
		final Location spawn = data.getSpawn(), block = world.getHighestBlockAt(spawn).getLocation().add(0.5D, 1.0D, 0.5D);
		return new Location(world, block.getX(), block.getY() < spawn.getY() ? spawn.getY() : block.getY(), block.getZ(), spawn.getYaw(), spawn.getPitch());
	}
	
	public final Location giveSpawnLocation(final World world)
	{
		return this.giveSpawnLocation(WorldData.giveWorldData(world));
	}
	
	public final ItemStack giveHead(final String nick, final ItemStack item)
	{
		Util.nonEmpty(nick);
		final Material skull = Material.SKULL_ITEM;
		final short variant = (short) SkullType.PLAYER.ordinal();
		Material material = skull;
		ItemStack craft = null;
		if(item == null)
			craft = new ItemStack(material, 1, variant);
		else
		{
			material = item.getType();
			craft = item;
		}
		try
		{
			craft = this.giveItemCraft(craft);
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
			craft = null;
		}
		if(craft != null && material == skull && craft.getType() == skull && craft.getDurability() == variant)
			try
			{
				final Object tagSkullOwner = this.giveTagClearNBT();
				final Object tagProperties = this.giveTagClearNBT();
				final Object tagTexture = this.giveTagClearNBT();
				this.setObjectNBT(tagTexture, "Value", new String(Base64.getEncoder().encode(String.format("{\"textures\":{\"SKIN\":{\"url\":\"http://skins.minecraft.net/MinecraftSkins/%s.png\"}}}", nick).getBytes())));
				this.setObjectNBT(tagProperties, "textures", this.toList(new Object[]{tagTexture}));
				this.setObjectNBT(tagSkullOwner, "Properties", tagProperties);
				this.setObjectNBT(tagSkullOwner, "Name", nick);
				this.setObjectNBT(this.giveItemNBT(craft), "SkullOwner", tagSkullOwner);
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
		return craft;
	}
	
	public final ItemStack giveHead(final String nick)
	{
		return this.giveHead(nick, null);
	}
	
	public final ItemStack giveHead(final OfflinePlayer player, final ItemStack item)
	{
		return this.giveHead(Util.nonNull(player).getName(), item);
	}
	
	public final ItemStack giveHead(final OfflinePlayer player)
	{
		return this.giveHead(player, null);
	}
	
	public final InventoryType giveInventoryDefaultType()
	{
		return InventoryType.CHEST;
	}
	
	public final int giveInventoryDefaultSize()
	{
		return this.giveInventoryDefaultType().getDefaultSize();
	}
	
	public final int giveInventoryDefaultLength()
	{
		return this.giveInventoryDefaultSize() / 9;
	}
	
	public final Inventory giveInventory()
	{
		return this.giveInventory(this.giveInventoryDefaultLength());
	}
	
	public final Inventory giveInventory(final int length)
	{
		return this.giveInventory(length, null);
	}
	
	public final Inventory giveInventory(final InventoryHolder holder)
	{
		return this.giveInventory(this.giveInventoryDefaultLength(), holder);
	}
	
	public final Inventory giveInventory(final int length, final InventoryHolder holder)
	{
		return this.giveServer().createInventory(holder, length * 9);
	}
	
	public final Inventory giveInventory(final String title)
	{
		return this.giveInventory(title, this.giveInventoryDefaultLength());
	}
	
	public final Inventory giveInventory(final String title, final int length)
	{
		return this.giveInventory(title, length, null);
	}
	
	public final Inventory giveInventory(final String title, final InventoryHolder holder)
	{
		return this.giveInventory(title, this.giveInventoryDefaultLength(), holder);
	}
	
	public final Inventory giveInventory(final String title, final int length, final InventoryHolder holder)
	{
		return this.giveServer().createInventory(holder, length * 9, Util.nonEmpty(title));
	}

	public final Inventory giveInventory(final InventoryType type)
	{
		return this.giveInventory(Util.nonNull(type).getDefaultTitle(), type);
	}
	
	public final Inventory giveInventory(final String title, final InventoryType type)
	{
		return this.giveInventory(title, type, null);
	}
	
	public final Inventory giveInventory(final InventoryType type, final InventoryHolder holder)
	{
		return this.giveInventory(Util.nonNull(type).getDefaultTitle(), type, holder);
	}
	
	public final Inventory giveInventory(final String title, final InventoryType type, final InventoryHolder holder)
	{
		return this.giveServer().createInventory(holder, Util.nonNull(type), Util.nonEmpty(title));
	}
	
	public final ItemMeta giveItemMeta(final ItemStack item)
	{
		return Util.nonNull(item).hasItemMeta() ? item.getItemMeta() : this.giveServer().getItemFactory().getItemMeta(item.getType());
	}
	
	public final ItemStack giveItemGlow(final ItemStack item)
	{
		try
		{
			final ItemStack itemCraft = this.giveItemCraft(item);
			this.doGlowItem(itemCraft);
			return itemCraft;
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final ItemStack giveItemBack(final PlayerMinigame player)
	{
		return this.giveItemBack(player.giveOfflinePlayer());
	}
	
	public final ItemStack giveItemBack(final OfflinePlayer player)
	{
		return this.giveItemMinigame("back", player);
	}
	
	public final ItemStack giveItemMinigame(final String script, final String function, final PlayerMinigame player)
	{
		final ItemAction action = this.giveItemAction(script, function, player);
		if(action != null)
			return action.giveItemStack();
		else
			return null;
	}
	
	public final ItemStack giveItemMinigame(final String script, final String function, final OfflinePlayer player)
	{
		final ItemAction action = this.giveItemAction(script, function, player);
		if(action != null)
			return action.giveItemStack();
		else
			return null;
	}
	
	public final ItemStack giveItemMinigame(final String function, final PlayerMinigame player)
	{
		final ItemAction action = this.giveItemAction(function, player);
		if(action != null)
			return action.giveItemStack();
		else
			return null;
	}
	
	public final ItemStack giveItemMinigame(final String function, final OfflinePlayer player)
	{
		final ItemAction action = this.giveItemAction(function, player);
		if(action != null)
			return action.giveItemStack();
		else
			return null;
	}
	
	public final ItemAction giveItemAction(final String script, final String function, final PlayerMinigame player)
	{
		if(this.hasUtilMinigame())
			try
			{
				return this.giveUtilMinigameSupport().giveItem(script != null && !script.equals("!") ? player.giveScript(Util.nonEmpty(script)) : player.giveUtilScript(), Util.nonEmpty(function), this.giveLanguage(player.giveOfflinePlayer()));
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
		return null;
	}
	
	public final ItemAction giveItemAction(final String script, final String function, final OfflinePlayer player)
	{
		try
		{
			return this.giveItemAction(script, function, PlayerMinigame.givePlayerMinigame(player));
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final ItemAction giveItemAction(final String function, final PlayerMinigame player)
	{
		return this.giveItemAction(null, function, player);
	}
	
	public final ItemAction giveItemAction(final String function, final OfflinePlayer player)
	{
		try
		{
			return this.giveItemAction(function, PlayerMinigame.givePlayerMinigame(player));
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final Language giveLanguage(final PlayerAccount player)
	{
		try
		{
			return Util.nonNull(player).getLanguage();
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
			return Language.giveValue(player.giveOfflinePlayer());
		}
	}
	
	public final Language giveLanguage(final OfflinePlayer player)
	{
		try
		{
			return this.giveLanguage(PlayerAccount.givePlayerAccount(player));
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
			return Language.giveValue(player);
		}
	}
	
	public final String giveMessage(final String key, final String defaultValue, final Properties translation)
	{
		Util.nonEmpty(key);
		Util.nonNull(translation);
		String value = defaultValue != null ? translation.getProperty(key, defaultValue) : translation.getProperty(key);
		if(value == null)
			value = this.giveLang(Language.DEFAULT).getProperty(key);
		return value != null ? value.replace('&', '§') : null;
	}
	
	public final String giveMessage(final String key, final Properties translation)
	{
		return this.giveMessage(key, null, translation);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final Language language, final Map<Language, Properties> translations)
	{
		if(Util.nonNull(translations).containsKey(Util.nonNull(language)))
			return this.giveMessage(key, defaultValue, translations.get(language));
		else
			return null;
	}
	
	public final String giveMessage(final String key, final Language language, final Map<Language, Properties> translations)
	{
		return this.giveMessage(key, null, language, translations);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final Language language, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, defaultValue, Util.nonNull(langpack).giveTranslations(language));
	}
	
	public final String giveMessage(final String key, final Language language, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, null, language, langpack);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final Language language)
	{
		return this.giveMessage(key, defaultValue, this.giveLang(language));
	}
	
	public final String giveMessage(final String key, final Language language)
	{
		return this.giveMessage(key, null, language);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final PlayerAccount player, final Map<Language, Properties> translations)
	{
		return this.giveMessage(key, defaultValue, this.giveLanguage(player), translations);
	}
	
	public final String giveMessage(final String key, final PlayerAccount player, final Map<Language, Properties> translations)
	{
		return this.giveMessage(key, this.giveLanguage(player), translations);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final PlayerAccount player, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, defaultValue, this.giveLanguage(player), langpack);
	}
	
	public final String giveMessage(final String key, final PlayerAccount player, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, this.giveLanguage(player), langpack);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final PlayerAccount player)
	{
		return this.giveMessage(key, defaultValue, this.giveLanguage(player));
	}
	
	public final String giveMessage(final String key, final PlayerAccount player)
	{
		return this.giveMessage(key, this.giveLanguage(player));
	}
	
	public final String giveMessage(final String key, final String defaultValue, final OfflinePlayer player, final Map<Language, Properties> translations)
	{
		return this.giveMessage(key, defaultValue, this.giveLanguage(player), translations);
	}
	
	public final String giveMessage(final String key, final OfflinePlayer player, final Map<Language, Properties> translations)
	{
		return this.giveMessage(key, this.giveLanguage(player), translations);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final OfflinePlayer player, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, defaultValue, this.giveLanguage(player), langpack);
	}
	
	public final String giveMessage(final String key, final OfflinePlayer player, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, this.giveLanguage(player), langpack);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final OfflinePlayer player)
	{
		return this.giveMessage(key, defaultValue, this.giveLanguage(player));
	}
	
	public final String giveMessage(final String key, final OfflinePlayer player)
	{
		return this.giveMessage(key, this.giveLanguage(player));
	}
	
	public final String giveMessage(final String key, final String defaultValue, final String code, final Map<Language, Properties> translations)
	{
		return this.giveMessage(key, defaultValue, Language.giveValue(code), translations);
	}
	
	public final String giveMessage(final String key, final String code, final Map<Language, Properties> translations)
	{
		return this.giveMessage(key, Language.giveValue(code), translations);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final String code, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, defaultValue, Language.giveValue(code), langpack);
	}
	
	public final String giveMessage(final String key, final String code, final LangPack langpack) throws ReflectiveOperationException
	{
		return this.giveMessage(key, Language.giveValue(code), langpack);
	}
	
	public final String giveMessage(final String key, final String defaultValue, final String code)
	{
		return this.giveMessage(key, defaultValue, Language.giveValue(code));
	}
	
	public final String giveMessage(final String key, final String code)
	{
		return this.giveMessage(key, Language.giveValue(code));
	}
	
	public final String giveMessage(final String key)
	{
		return this.giveMessage(key, Language.DEFAULT);
	}
	
	public final MessageFunction giveMessageFunction(final CommandSender sender)
	{
		return Util.nonNull(sender) instanceof Player ? this.giveMessageFunction((OfflinePlayer) sender) : this.giveMessageFunction();
	}
	
	public final MessageFunction giveMessageFunction(final Language language)
	{
		return key -> this.giveMessage(key, Util.nonNull(language));
	}
	
	public final MessageFunction giveMessageFunction(final OfflinePlayer player)
	{
		return key -> this.giveMessage(key, Util.nonNull(player));
	}
	
	public final MessageFunction giveMessageFunction()
	{
		return this::giveMessage;
	}
	
	@Override
	public final Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("name", this.giveUtilName());
		map.put("plugin", this.giveUtilPlugin());
		map.put("channel", this.getUtilChannel());
		map.put("config", this.giveUtilConfig());
		map.put("configLocations", this.giveUtilConfigLocations());
		map.put("configSigns", this.giveUtilConfigSigns());
		map.put("worlds", this.giveUtilWorlds());
		map.put("configLocationsFile", this.giveUtilConfigLocationsFile());
		map.put("configSignsFile", this.giveUtilConfigSignsFile());
		map.put("luaInventoryFile", this.giveUtilLuaInventoryFile());
		map.put("luaLangFile", this.giveUtilLuaLangFile());
		map.put("worldsFile", this.giveUtilWorldsFile());
		map.put("plugins", this.givePlugins());
		map.put("channels", this.giveChannels());
		map.put("loaderJNF", this.getLoaderJNF());
		map.put("loaderLuaj", this.getLoaderLuaj());
		map.put("lobby", this.giveLobby());
		map.put("ranks", this.giveRanks());
		map.put("actionListeners", this.giveActionListeners());
		map.put("commandListeners", this.giveCommandListeners());
		map.put("tps", this.giveCalculatorTPS());
		map.put("vault", this.getVault());
		map.put("series", this.giveSeries());
		map.put("versionServer", this.giveServerVersion());
		map.put("sql", this.giveSQL());
		map.put("craft", this.giveCraft());
		map.put("utilScoreboard", this.utilScoreboard);
		map.put("utilMinigame", this.getUtilMinigame());
		map.put("utilMinigameSupport", this.giveUtilMinigameSupport());
		map.put("langpack", this.getUtilLangPack());
		map.put("lang", this.giveLang());
		return map;
	}
	
	public final synchronized Util addPlugin(final Plugin plugin)
	{
		if(plugin != null)
		{
			this.plugins.put(plugin.getName().toLowerCase(), plugin);
			return this;
		}
		else
			return null;
	}
	
	public final synchronized Util addPlugin(final String name)
	{
		return this.addPlugin(this.givePluginManager().getPlugin(Util.nonEmpty(name)));
	}
	
	public final synchronized Util delPlugin(final String name)
	{
		return this.plugins.remove(Util.nonEmpty(name).toLowerCase()) != null ? this : null;
	}
	
	public final synchronized Plugin givePlugin(final String name)
	{
		return this.plugins.get(Util.nonEmpty(name).toLowerCase());
	}
	
	public final synchronized Collection<Plugin> givePlugins()
	{
		return Collections.unmodifiableCollection(this.plugins.values());
	}
	
	public final synchronized Util addChannel(final Channel channel)
	{
		if(channel != null)
		{
			this.channels.put(channel.giveName().toLowerCase(), channel);
			return this;
		}
		else
			return null;
	}
	
	public final synchronized Util delChannel(final String name)
	{
		return this.channels.remove(Util.nonEmpty(name).toLowerCase()) != null ? this : null;
	}
	
	public final synchronized Channel giveChannel(final String name)
	{
		return this.channels.get(Util.nonEmpty(name).toLowerCase());
	}
	
	public final synchronized List<Channel> giveChannels()
	{
		return Collections.unmodifiableList(new ArrayList<Channel>(this.channels.values()));
	}
	
	public final synchronized Map<Language, Properties> giveLang()
	{
		return this.lang;
	}
	
	public final synchronized Properties giveLang(final Language language)
	{
		final Map<Language, Properties> lang = this.giveLang();
		if(!lang.containsKey(Util.nonNull(language)))
			lang.put(language, new Properties());
		return lang.get(language);
	}
	
	public final synchronized Util addLangPack(final LangPack langpack) throws ReflectiveOperationException
	{
		return this.addLangPack(Util.nonNull(langpack).giveTranslations());
	}
	
	public final synchronized Util addLangPack(final Map<Language, Properties> translations)
	{
		final Map<Language, Properties> lang = this.giveLang();
		for(final Language language : Util.nonNull(translations).keySet())
		{
			if(!lang.containsKey(language))
				lang.put(language, new Properties());
			final Properties langTranslation = lang.get(language), translation = translations.get(language);
			for(final Object key : translation.keySet())
				if(!langTranslation.containsKey(key))
					langTranslation.setProperty(String.valueOf(key), translation.getProperty(String.valueOf(key)));
		}
		return this;
	}
	
	public final synchronized Util delLangPack(final LangPack langpack) throws ReflectiveOperationException
	{
		return this.delLangPack(Util.nonNull(langpack).giveTranslations());
	}
	
	public final synchronized Util delLangPack(final Map<Language, Properties> translations)
	{
		final Map<Language, Properties> lang = this.giveLang();
		for(final Language language : Util.nonNull(translations).keySet())
			if(lang.containsKey(language))
			{
				final Properties translation = lang.get(language);
				for(final Object key : translations.get(language).keySet())
					if(translation.containsKey(key))
						translation.remove(key);
			}
		return this;
	}
	
	public final synchronized List<Rhenowar> giveActionListeners()
	{
		return Collections.unmodifiableList(this.actionListeners);
	}
	
	public final synchronized Util addActionListener(final Rhenowar listener)
	{
		this.actionListeners.add(Util.nonNull(listener));
		return this;
	}
	
	public final synchronized Util delActionListener(final Rhenowar listener)
	{
		this.actionListeners.remove(Util.nonNull(listener));
		return this;
	}
	
	public final synchronized List<Rhenowar> giveCommandListeners()
	{
		return Collections.unmodifiableList(this.commandListeners);
	}
	
	public final synchronized Util addCommandListener(final Rhenowar listener)
	{
		this.commandListeners.add(Util.nonNull(listener));
		return this;
	}
	
	public final synchronized Util delCommandListener(final Rhenowar listener)
	{
		this.commandListeners.remove(Util.nonNull(listener));
		return this;
	}
	
	public final synchronized Util addWorld(final FileRHN file, final WorldData world)
	{
		return Util.nonNull(file).addObject(Util.nonNull(world).giveName(), world) != null ? this : null;
	}
	
	public final synchronized Util addWorld(final WorldData world)
	{
		if(this.hasUtilWorlds())
			return this.addWorld(this.giveUtilWorlds(), world);
		else
			return null;
	}
	
	public final synchronized Util addWorld(final FileRHN file, final World world)
	{
		return this.addWorld(file, WorldData.giveWorldData(world));
	}
	
	public final synchronized Util addWorld(final World world)
	{
		if(this.hasUtilWorlds())
			return this.addWorld(this.giveUtilWorlds(), world);
		else
			return null;
	}
	
	public final synchronized Util delWorld(final FileRHN file, final String world)
	{
		return Util.nonNull(file).delObject(world) != null ? this : null;
	}
	
	public final synchronized Util delWorld(final String world)
	{
		if(this.hasUtilWorlds())
			return this.delWorld(this.giveUtilWorlds(), world);
		else
			return null;
	}
	
	public final synchronized Util delWorld(final FileRHN file, final WorldData world)
	{
		return this.delWorld(file, Util.nonNull(world).giveName());
	}
	
	public final synchronized Util delWorld(final WorldData world)
	{
		if(this.hasUtilWorlds())
			return this.delWorld(this.giveUtilWorlds(), world);
		else
			return null;
	}
	
	public final synchronized Util delWorld(final FileRHN file, final World world)
	{
		return this.delWorld(file, Util.nonNull(world).getName());
	}
	
	public final synchronized Util delWorld(final World world)
	{
		if(this.hasUtilWorlds())
			return this.delWorld(this.giveUtilWorlds(), world);
		else
			return null;
	}
	
	public final FileRHN giveUtilWorlds()
	{
		return this.utilWorlds;
	}

	public final boolean hasUtilWorlds()
	{
		return this.giveUtilWorlds() != null;
	}
	
	public final Util setLoaderJNF(final ClassLoader loaderJNF)
	{
		this.loaderJNF = Util.nonNull(loaderJNF);
		return this;
	}
	
	public final ClassLoader getLoaderJNF()
	{
		return this.loaderJNF;
	}
	
	public final boolean hasLoaderJNF()
	{
		return this.getLoaderJNF() != null;
	}
	
	public final Util setLoaderLuaj(final ClassLoader loaderLuaj)
	{
		this.loaderLuaj = Util.nonNull(loaderLuaj);
		return this;
	}
	
	public final ClassLoader getLoaderLuaj()
	{
		return this.loaderLuaj;
	}
	
	public final boolean hasLoaderLuaj()
	{
		return this.getLoaderLuaj() != null;
	}
	
	public final Util setUtilChannel(final UtilChannel channel)
	{
		this.utilChannel = Util.nonNull(channel);
		return this;
	}
	
	public final UtilChannel getUtilChannel()
	{
		return this.utilChannel;
	}
	
	public final boolean hasUtilChannel()
	{
		return this.getUtilChannel() != null;
	}
	
	public final Util setUtilMinigame(final Object utilMinigame)
	{
		this.utilMinigame = Util.nonNull(utilMinigame);
		this.utilMinigameSupport = new UtilMinigameSupport(this.getUtilMinigame(), this);
		return this;
	}
	
	public final Object getUtilMinigame()
	{
		return this.utilMinigame;
	}
	
	public final boolean hasUtilMinigame()
	{
		return this.getUtilMinigame() != null;
	}
	
	public final Util setUtilLangPack(final LangPack utilLangPack)
	{
		this.utilLangPack = Util.nonNull(utilLangPack);
		return this;
	}
	
	public final LangPack getUtilLangPack()
	{
		return this.utilLangPack;
	}
	
	public final boolean hasUtilLangPack()
	{
		return this.getUtilLangPack() != null;
	}
	
	public final Scoreboard giveMainScoreboard()
	{
		return this.giveScoreboardManager().getMainScoreboard();
	}
	
	public final Scoreboard giveUtilScoreboard()
	{
		if(!this.hasUtilScoreboard())
			this.utilScoreboard = this.giveScoreboardManager().getNewScoreboard();
		return this.utilScoreboard;
	}
	
	public final boolean hasUtilScoreboard()
	{
		return this.utilScoreboard != null;
	}
	
	public final Team giveScoreboardTeam(Scoreboard scoreboard, final String major, final String minor, final String name, final Consumer<Team> consumer)
	{
		Util.nonEmpty(name);
		String teamName = "";
		if(major != null && !major.isEmpty())
		{
			teamName += major + ":";
			if(minor != null && !minor.isEmpty())
				teamName += minor + ":";
		}
		teamName += name;
		if(teamName.length() > 16)
		{
			String hash = "";
			final Iterator<Integer> iterator = Integer.toHexString(teamName.hashCode()).chars().iterator();
			for(int i = 0; i < 8; i++)
				hash += iterator.hasNext() ? (char) (int) iterator.next() : '?';
			teamName = teamName.substring(0, 3) + hash + teamName.substring(teamName.length() - 5, teamName.length() - 1) + "~";
		}
		if(scoreboard == null)
			scoreboard = this.giveUtilScoreboard();
		Team team = scoreboard.getTeam(teamName);
		if(team == null)
		{
			team = scoreboard.registerNewTeam(teamName);
			if(consumer != null)
				consumer.accept(team);
		}
		return team;
	}
	
	public final Team giveScoreboardTeam(final Scoreboard scoreboard, final String major, final String name, final Consumer<Team> consumer)
	{
		return this.giveScoreboardTeam(scoreboard, major, null, name, consumer);
	}
	
	public final Team giveScoreboardTeam(final Scoreboard scoreboard, final String name, final Consumer<Team> consumer)
	{
		return this.giveScoreboardTeam(scoreboard, null, name, consumer);
	}
	
	public final Team giveScoreboardTeam(final Scoreboard scoreboard, final String major, final String minor, final String name)
	{
		return this.giveScoreboardTeam(scoreboard, major, minor, name, null);
	}
	
	public final Team giveScoreboardTeam(final Scoreboard scoreboard, final String major, final String name)
	{
		return this.giveScoreboardTeam(scoreboard, major, null, name);
	}
	
	public final Team giveScoreboardTeam(final Scoreboard scoreboard, final String name)
	{
		return this.giveScoreboardTeam(scoreboard, null, name);
	}
	
	public final Team giveScoreboardTeam(final String major, final String minor, final String name, final Consumer<Team> consumer)
	{
		return this.giveScoreboardTeam(null, major, minor, name, consumer);
	}
	
	public final Team giveScoreboardTeam(final String major, final String name, final Consumer<Team> consumer)
	{
		return this.giveScoreboardTeam((Scoreboard) null, major, name, consumer);
	}
	
	public final Team giveScoreboardTeam(final String name, final Consumer<Team> consumer)
	{
		return this.giveScoreboardTeam((Scoreboard) null, name, consumer);
	}
	
	public final Team giveScoreboardTeam(final String major, final String minor, final String name)
	{
		return this.giveScoreboardTeam(null, major, minor, name);
	}
	
	public final Team giveScoreboardTeam(final String major, final String name)
	{
		return this.giveScoreboardTeam((Scoreboard) null, major, name);
	}
	
	public final Team giveScoreboardTeam(final String name)
	{
		return this.giveScoreboardTeam((Scoreboard) null, name);
	}
	
	public final Util setVault(final VaultSupport vault)
	{
		this.vault = Util.nonNull(vault);
		return this;
	}
	
	public final VaultSupport getVault()
	{
		return this.vault;
	}
	
	public final boolean hasVault()
	{
		return this.getVault() != null;
	}
	
	@Deprecated
	public final Util setBalance(final OfflinePlayer player, final double amount)
	{
		return this.setMoney(player, (int) amount);
	}
	
	@Deprecated
	public final Util addBalance(final OfflinePlayer player, final double amount)
	{
		return this.addMoney(player, (int) amount);
	}
	
	@Deprecated
	public final Util delBalance(final OfflinePlayer player, final double amount)
	{
		return this.delMoney(player, (int) amount);
	}
	
	@Deprecated
	public final Double getBalance(final OfflinePlayer player)
	{
		return (double) this.getMoney(player);
	}
	
	@Deprecated
	public final double giveBalance(final OfflinePlayer player)
	{
		if(this.hasBalance(player))
			return this.getBalance(player);
		else
			return 0.0D;
	}
	
	@Deprecated
	public final boolean hasBalance(final OfflinePlayer player)
	{
		final Double balance = this.getBalance(player);
		return balance != null && balance > 0;
	}
	
	@Deprecated
	public final boolean hasBalance()
	{
		return this.getVault().hasEconomy();
	}
	
	public final Util setMoney(final OfflinePlayer player, final int amount)
	{
		try
		{
			return PlayerAccount.givePlayerAccount(player).setBalance(amount) != null ? this : null;
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
			return null;
		}
	}
	
	public final Util addMoney(final OfflinePlayer player, final int amount)
	{
		return this.setMoney(player, this.getMoney(player) + amount);
	}
	
	public final Util delMoney(final OfflinePlayer player, final int amount)
	{
		return this.setMoney(player, this.getMoney(player) - amount);
	}
	
	public final int getMoney(final OfflinePlayer player)
	{
		Integer money = null;
		try
		{
			money = PlayerAccount.givePlayerAccount(player).getBalance();
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
		}
		if(money != null)
			return money;
		else
			return 0;
	}
	
	public final PlayerRhenowar givePlayerRhenowar(final OfflinePlayer player)
	{
		return PlayerRhenowar.givePlayerRhenowar(player);
	}
	
	public final List<IRhenowarPlayer> giveRhenowarPlayers(final OfflinePlayer player)
	{
		return this.givePlayerRhenowar(player).giveRhenowarPlayers();
	}
	
	public final <T extends IRhenowarPlayer> List<T> giveRhenowarPlayers(final OfflinePlayer player, final Class<T> type)
	{
		return this.givePlayerRhenowar(player).giveRhenowarPlayers(type);
	}
	
	public final synchronized Util addRhenowarPlayer(final OfflinePlayer player, final IRhenowarPlayer rhenowarPlayer)
	{
		return this.givePlayerRhenowar(player).addRhenowarPlayer(rhenowarPlayer) != null ? this : null;
	}
	
	public final synchronized Util delRhenowarPlayer(final OfflinePlayer player, final IRhenowarPlayer rhenowarPlayer)
	{
		return this.givePlayerRhenowar(player).delRhenowarPlayer(rhenowarPlayer) != null ? this : null;
	}
	
	public final Util setLocation(final Configuration configuration, final String prefix, final Location location, final boolean world)
	{
		Util.nonNull(configuration);
		Util.nonEmpty(prefix);
		Util.nonNull(location);
		if(world)
			configuration.set(prefix + "W", location.getWorld().getName());
		configuration.set(prefix + "x", location.getX());
		configuration.set(prefix + "y", location.getY());
		configuration.set(prefix + "z", location.getZ());
		configuration.set(prefix + "Y", location.getYaw());
		configuration.set(prefix + "P", location.getPitch());
		return this;
	}
	
	public final Util setLocation(final Configuration configuration, final String prefix, final Location location)
	{
		return this.setLocation(configuration, prefix, location, true);
	}
	
	public final Util setLocation(final String prefix, final Location location, final boolean world)
	{
		return this.setLocation(this.giveUtilConfigLocations(), prefix, location, world);
	}
	
	public final Util setLocation(final String prefix, final Location location)
	{
		return this.setLocation(prefix, location, true);
	}
	
	public final Location getLocation(final Configuration configuration, final String prefix, final World world)
	{
		Util.nonNull(configuration);
		Util.nonEmpty(prefix);
		return new Location(
			world != null ? world : this.giveWorld(configuration.getString(prefix + "W")),
			configuration.getDouble(prefix + "x"), configuration.getDouble(prefix + "y"), configuration.getDouble(prefix + "z"),
			(float) configuration.getDouble(prefix + "Y"), (float) configuration.getDouble(prefix + "P")
				);
	}
	
	public final Location getLocation(final Configuration configuration, final String prefix)
	{
		return this.getLocation(configuration, prefix, null);
	}
	
	public final Location getLocation(final String prefix, final World world)
	{
		return this.getLocation(this.giveUtilConfigLocations(), prefix, world);
	}
	
	public final Location getLocation(final String prefix)
	{
		return this.getLocation(prefix, null);
	}
	
	public final boolean isSignTP(final String line)
	{
		return Util.nonNull(line).equals("[" + this.giveUtilConfig().getString(this.giveUtilConfigPrefix() + "SignTP.rate") + "]");
	}
	
	public final boolean isSignTP(final Sign sign)
	{
		return this.isSignTP(Util.nonNull(sign).getLine(0));
	}
	
	public final boolean isSignTP(final ISignEvent event)
	{
		return this.isSignTP(Util.nonNull(event).giveSign());
	}
	
	public final boolean isPlayerRank(final OfflinePlayer player, final Rank rank)
	{
		Util.nonNull(rank);
		final Rank playerRank = this.givePlayerRank(player);
		return playerRank.giveName().equalsIgnoreCase(rank.giveName()) && playerRank.giveEntitlement().giveEntitlementLevel() == rank.giveEntitlement().giveEntitlementLevel();
	}
	
	public final boolean isPlayerRank(final OfflinePlayer player, final String rank, final List<Rank> ranks)
	{
		final Rank objRank = this.giveRank(rank, ranks);
		if(objRank != null)
			return this.isPlayerRank(player, objRank);
		else
			return false;
	}
	
	public final boolean isPlayerRank(final OfflinePlayer player, final String rank)
	{
		return this.isPlayerRank(player, rank, this.giveRanks());
	}
	
	public final boolean isBlockLuminance(final Material material)
	{
		switch(Util.nonNull(material))
		{
			case LAVA:
			case STATIONARY_LAVA:
			case BROWN_MUSHROOM:
			case TORCH:
			case FIRE:
			case BURNING_FURNACE:
			case GLOWING_REDSTONE_ORE:
			case REDSTONE_TORCH_ON:
			case GLOWSTONE:
			case PORTAL:
			case JACK_O_LANTERN:
			case BREWING_STAND:
			case ENDER_PORTAL:
			case ENDER_PORTAL_FRAME:
			case DRAGON_EGG:
			case REDSTONE_LAMP_ON:
			case ENDER_CHEST:
			case BEACON:
			case SEA_LANTERN:
			case END_ROD:
			case MAGMA:
				return true;
			default:
				return false;
		}
	}
	
	public final boolean isBlockLuminance(final Block block)
	{
		return this.isBlockLuminance(Util.nonNull(block).getType());
	}
	
	public final boolean isPlayerInLobby(final Player player)
	{
		return this.isEntityInLobby(player);
	}
	
	public final boolean isEntityInLobby(final Entity entity)
	{
		return this.isLobby(entity.getWorld());
	}
	
	public final boolean isLobby(final World world)
	{
		return this.giveLobby() == world;
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final byte buildThresholdBuild, final byte buildThresholdSpecial, final byte developThresholdBuild, final byte developThresholdSpecial, final byte normalThresholdBuild, final byte normalThresholdSpecial)
	{
		Util.nonNull(player);
		final ServerType type = this.giveServerType();
		final byte thresholdBuild = this.givePlayerEntitlementThreshold(player, TypeEntitlement.BUILD), thresholdSpecial = this.givePlayerEntitlementThreshold(player, TypeEntitlement.SPECIAL);
		return this.isPlayerOP(player) || type == ServerType.BUILD ? thresholdBuild >= buildThresholdBuild && thresholdSpecial >= buildThresholdSpecial : type == ServerType.DEVELOP ? thresholdBuild >= developThresholdBuild && thresholdSpecial >= developThresholdSpecial : type.isNormal() ? thresholdBuild >= normalThresholdBuild && thresholdSpecial >= normalThresholdSpecial : false;
	}
	public final boolean isBlocked(final OfflinePlayer player, final byte buildThresholdBuild, final byte buildThresholdSpecial, final byte developThresholdBuild, final byte developThresholdSpecial, final byte normalThresholdBuild, final byte normalThresholdSpecial)
	{
		return !this.isAllowed(player, buildThresholdBuild, buildThresholdSpecial, developThresholdBuild, developThresholdSpecial, normalThresholdBuild, normalThresholdSpecial);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final byte specificThresholdBuild, final byte specificThresholdSpecial, final byte normalThresholdBuild, final byte normalThresholdSpecial)
	{
		return this.isAllowed(player, specificThresholdBuild, specificThresholdSpecial, specificThresholdBuild, specificThresholdSpecial, normalThresholdBuild, normalThresholdSpecial);
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final byte specificThresholdBuild, final byte specificThresholdSpecial, final byte normalThresholdBuild, final byte normalThresholdSpecial)
	{
		return !this.isAllowed(player, specificThresholdBuild, specificThresholdSpecial, normalThresholdBuild, normalThresholdSpecial);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final byte thresholdBuild, final byte thresholdSpecial)
	{
		return this.isAllowed(player, thresholdBuild, thresholdSpecial, thresholdBuild, thresholdSpecial);
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final byte thresholdBuild, final byte thresholdSpecial)
	{
		return !this.isAllowed(player, thresholdBuild, thresholdSpecial);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final int buildLevelBuild, final int buildLevelSpecial, final int developLevelBuild, final int developLevelSpecial, final int normalLevelBuild, final int normalLevelSpecial)
	{
		Util.nonNull(player);
		final ServerType type = this.giveServerType();
		final int levelBuild = this.givePlayerEntitlementLevel(player, TypeEntitlement.BUILD), levelSpecial = this.givePlayerEntitlementLevel(player, TypeEntitlement.SPECIAL);
		return this.isPlayerOP(player) || type == ServerType.BUILD ? levelBuild >= buildLevelBuild && levelSpecial >= buildLevelSpecial : type == ServerType.DEVELOP ? levelBuild >= developLevelBuild && levelSpecial >= developLevelSpecial : type.isNormal() ? levelBuild >= normalLevelBuild && levelSpecial >= normalLevelSpecial : false;
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final int buildLevelBuild, final int buildLevelSpecial, final int developLevelBuild, final int developLevelSpecial, final int normalLevelBuild, final int normalLevelSpecial)
	{
		return !this.isAllowed(player, buildLevelBuild, buildLevelSpecial, developLevelBuild, developLevelSpecial, normalLevelBuild, normalLevelSpecial);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final int specificLevelBuild, final int specificLevelSpecial, final int normalLevelBuild, final int normalLevelSpecial)
	{
		return this.isAllowed(player, specificLevelBuild, specificLevelSpecial, specificLevelBuild, specificLevelSpecial, normalLevelBuild, normalLevelSpecial);
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final int specificLevelBuild, final int specificLevelSpecial, final int normalLevelBuild, final int normalLevelSpecial)
	{
		return !this.isAllowed(player, specificLevelBuild, specificLevelSpecial, normalLevelBuild, normalLevelSpecial);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final int levelBuild, final int levelSpecial)
	{
		return this.isAllowed(player, levelBuild, levelSpecial, levelBuild, levelSpecial);
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final int levelBuild, final int levelSpecial)
	{
		return !this.isAllowed(player, levelBuild, levelSpecial);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final Entitlement entitlementBuild, final Entitlement entitlementDevelop, final Entitlement entitlementNormal)
	{
		return this.isAllowed(player,
				Util.nonNull(entitlementBuild).giveEntitlementLevel(TypeEntitlement.BUILD), entitlementBuild.giveEntitlementLevel(TypeEntitlement.SPECIAL),
				Util.nonNull(entitlementDevelop).giveEntitlementLevel(TypeEntitlement.BUILD), entitlementDevelop.giveEntitlementLevel(TypeEntitlement.SPECIAL),
				Util.nonNull(entitlementNormal).giveEntitlementLevel(TypeEntitlement.BUILD), entitlementNormal.giveEntitlementLevel(TypeEntitlement.SPECIAL)
			);
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final Entitlement entitlementBuild, final Entitlement entitlementDevelop, final Entitlement entitlementNormal)
	{
		return !this.isAllowed(player, entitlementBuild, entitlementDevelop, entitlementNormal);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final Entitlement entitlementSpecific, final Entitlement entitlementNormal)
	{
		return this.isAllowed(player, entitlementSpecific, entitlementSpecific, entitlementNormal);
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final Entitlement entitlementSpecific, final Entitlement entitlementNormal)
	{
		return !this.isAllowed(player, entitlementSpecific, entitlementNormal);
	}
	
	public final boolean isAllowed(final OfflinePlayer player, final Entitlement entitlement)
	{
		return this.isAllowed(player, entitlement, entitlement);
	}
	
	public final boolean isBlocked(final OfflinePlayer player, final Entitlement entitlement)
	{
		return !this.isAllowed(player, entitlement);
	}
	
	public final boolean isProtect(final WorldData world, final Region region)
	{
		for(final Region protect : world.giveProtect())
			if(protect.equals(region))
				return true;
		return false;
	}
	
	public final boolean isProtect(final World world, final Region region)
	{
		return this.isProtect(WorldData.giveWorldData(world), region);
	}
	
	public final boolean isProtect(final WorldData world, final Vector vector)
	{
		for(final Region region : world.giveProtect())
			if(region.isContains(vector))
				return true;
		return false;
	}
	
	public final boolean isProtect(final World world, final Vector vector)
	{
		return this.isProtect(WorldData.giveWorldData(world), vector);
	}
	
	public final boolean isProtect(final Location location)
	{
		return this.isProtect(location.getWorld(), location.toVector());
	}
	
	public final boolean isBuildBlocked(final Player player, final Location location)
	{
		return (!WorldData.giveWorldData(location.getWorld()).getBuild() || this.isProtect(location)) && this.isBlocked(player, (byte) 2, (byte) 0, (byte) 3, (byte) 0, (byte) 4, (byte) 0);
	}
	
	public final boolean isBuildBlocked(final Player player, final Block block)
	{
		return this.isBuildBlocked(player, block.getLocation());
	}
	
	public final boolean isBuildBlocked(final Player player)
	{
		return this.isBuildBlocked(player, player.getLocation());
	}
	
	public final boolean isSeries(final short series)
	{
		return this.giveSeriesNumber() == series;
	}
	
	public final boolean isServerNormal()
	{
		return this.giveServerType().isNormal();
	}
	
	public final boolean isServerSpecific()
	{
		return this.giveServerType().isSpecific();
	}
	
	public final boolean isSeries(final Series series)
	{
		return this.isSeries(series.giveSeries());
	}
	
	public final boolean isSeries(final String series)
	{
		return this.isSeries(Series.giveValue(series));
	}
	
	public final boolean isPlayerVersion(final Player player, final ProtocolVersion version) throws ReflectiveOperationException
	{
		final ProtocolVersion playerVersion = this.givePlayerVersion(player);
		return playerVersion == Util.nonNull(version) || playerVersion.giveVersion() == version.giveVersion();
	}
	
	public final boolean isPlayerVersion(final Player player, final int version) throws ReflectiveOperationException
	{
		final ProtocolVersion[] array = ProtocolVersion.giveValues(version);
		return this.isPlayerVersion(player, array.length > 0 ? array[0] : ProtocolVersion.UNKNOWN);
	}
	
	public final boolean isPlayerVersion(final Player player, final String version) throws ReflectiveOperationException
	{
		return this.isPlayerVersion(player, ProtocolVersion.giveValue(version));
	}
	
	public final boolean isPlayerVersion(final Player player) throws ReflectiveOperationException
	{
		return this.isPlayerVersion(player, this.giveServerVersion());
	}
	
	public final boolean isPlayerOP(final OfflinePlayer player)
	{
		return player.isOp();
	}
	
	public final boolean isAnnotation(final AnnotatedElement element, final Class<? extends Annotation> annotation)
	{
		return element.isAnnotationPresent(annotation);
	}
	
	public final boolean isAnnotation(final Class<?> clazz, final String field, final Class<? extends Annotation> annotation) throws NoSuchFieldException, SecurityException
	{
		return this.isAnnotation(clazz.getField(field), annotation);
	}
	
	public final boolean isAnnotation(final Object object, final String field, final Class<? extends Annotation> annotation) throws NoSuchFieldException, SecurityException
	{
		return this.isAnnotation(object.getClass(), field, annotation);
	}
	
	public final boolean isAnnotation(final Enum<?> enumeration, final Class<? extends Annotation> annotation) throws NoSuchFieldException, SecurityException
	{
		return this.isAnnotation(enumeration, enumeration.name(), annotation);
	}
	
	public final boolean isDeprecated(final Field field)
	{
		return this.isAnnotation(field, Deprecated.class);
	}
	
	public final boolean isDeprecated(final Class<?> clazz, final String field) throws NoSuchFieldException, SecurityException
	{
		return this.isAnnotation(clazz, field, Deprecated.class);
	}
	
	public final boolean isDeprecated(final Object object, final String field) throws NoSuchFieldException, SecurityException
	{
		return this.isAnnotation(object, field, Deprecated.class);
	}
	
	public final boolean isDeprecated(final Enum<?> enumeration) throws NoSuchFieldException, SecurityException
	{
		return this.isAnnotation(enumeration, Deprecated.class);
	}
	
	public final Util hasPaid(final OfflinePlayer player, final ICallback callback) throws IOException
	{
		final RequestData data = new RequestData();
		data.setCallback(Util.nonNull(callback));
		data.setPlayer(player.getPlayer());
		final DataOutputStream stream = new DataOutputStream(data.giveStream());
		stream.writeUTF(player.getName());
		stream.writeUTF(player.getUniqueId().toString());
		this.exceptionIO = null;
		final boolean value = this.doUtilChannelPing((final Object result) ->
		{
			try
			{
				return this.getUtilChannel().doRequest("haspaid", data);
			}
			catch(final IOException e)
			{
				this.exceptionIO = e;
				return false;
			}
		});
		if(this.exceptionIO != null)
			throw this.exceptionIO;
		else
			return value ? this : null;
	}
	
	public final Util hasPremium(final OfflinePlayer player, final ICallback callback) throws IOException
	{
		Util.nonNull(callback);
		return this.hasPaid(player, (final Object result) ->
		{
			if(result instanceof Boolean)
				return callback.onResultReceived((boolean) result && player.isOnline());
			else
				return false;
		});
	}
	
	public final boolean hasPermission(final Player player, final String permission)
	{
		try
		{
			return Util.nonNull(player).hasPermission(Util.nonEmpty(permission)) || PlayerRank.givePlayerRank(player).hasPermission(permission);
		}
		catch(final SQLException e)
		{
			this.doReportErrorSQL(e);
			return false;
		}
	}
	
	public final Util doSleep(final int ticks)
	{
		if(ticks < 1)
		{
			Util.doThrowIAE("(ticks = " + ticks + ") < 1");
			return null;
		}
		else
			try
			{
				Thread.sleep(Math.round(ticks / (this.giveTPS() / CalculatorTPS.STANDARD_VALUE) * Math.pow((2 << 2) + 2, (2 << 1) - 1)));
				return this;
			}
			catch(final InterruptedException e)
			{
				e.printStackTrace();
				return null;
			}
	}
	
	public final Util doDeleteDirectory(final File directory)
	{
		if(Util.nonNull(directory).exists() && directory.isDirectory())
		{
			final File[] files = directory.listFiles();
			if(files != null && files.length > 0)
				for(final File file : files)
					if(file != null && file.exists())
					{
						if(file.isDirectory())
							this.doDeleteDirectory(file);
						else
							file.delete();
					}
			directory.delete();
			return this;
		}
		else
			return null;
	}
	
	public final boolean doUtilChannelPing(final ICallback callback) throws IOException
	{
		return this.getUtilChannel().doRequest("ping", new RequestData().setCallback(Util.nonNull(callback)));
	}
	
	public final long doUtilChannelPing(final int delay) throws IOException
	{
		this.ping = -1L;
		final long time = System.nanoTime();
		this.doUtilChannelPing((final Object result) ->
		{
			if(result instanceof Long)
			{
				this.ping = (long) result;
				return true;
			}
			else
				return false;
		});
		this.doSleep(delay);
		return this.ping >= 0 ? this.ping - time  : this.ping;
	}
	
	public final boolean doSaveConfig(final FileConfiguration config, final File file)
	{
		try
		{
			Util.nonNull(config).save(Util.nonNull(file));
		}
		catch(final IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public final Util doUtilSaveConfigLocations()
	{
		this.doSaveConfig(this.giveUtilConfigLocations(), this.giveUtilConfigLocationsFile());
		return this;
	}
	
	public final Util doUtilSaveConfigSigns()
	{
		this.doSaveConfig(this.giveUtilConfigSigns(), this.giveUtilConfigSignsFile());
		return this;
	}
	
	public final Util doSend(final CommandSender sender, final String message)
	{
		Util.nonNull(sender).sendMessage(Util.nonNull(message).replace('&', '§'));
		return this;
	}
	
	public final Util doSend(final CommandSender sender, final String... messages)
	{
		Util.nonNull(sender).sendMessage(Util.nonNull(messages));
		return this;
	}
	
	public final Util doSendMessage(final Player player, final String pluginName, final String message)
	{
		return this.doSend(player, "&6[&2" + pluginName + "&6] &r" + Util.nonNull(message));
	}
	
	public final Util doSendMessage(final Player player, final RhenowarPlugin plugin, final String message)
	{
		return this.doSendMessage(player, Util.nonNull(plugin).giveName(), message);
	}
	
	public final Util doUtilSendMessage(final Player player, final String message)
	{
		return this.doSendMessage(player, this.giveUtilName(), message);
	}
	
	public final Util doSendConsole(final Logger logger, final Level level, final String message)
	{
		Util.nonNull(logger).log(Util.nonNull(level), Util.nonEmpty(message));
		return this;
	}
	
	public final Util doSendConsole(final Level level, final String message)
	{
		return this.doSendConsole(this.giveUtilLogger(), level, message);
	}
	
	public final Util doSendConsole(final Logger logger, final String message)
	{
		Util.nonNull(logger).info(Util.nonEmpty(message));
		return this;
	}
	
	public final Util doSendConsole(final String message)
	{
		return this.doSend(this.giveConsoleSender(), message);
	}
	
	public final Util doSendConsole(final String pluginName, final String message)
	{
		return this.doSendConsole("[" + Util.nonEmpty(pluginName) + "] " + Util.nonEmpty(message));
	}
	
	public final Util doSendConsole(final RhenowarPlugin plugin, final String message)
	{
		return this.doSendConsole(Util.nonNull(plugin).giveName(), message);
	}
	
	public final Util doSendConsole(final String... messages)
	{
		return this.doSend(this.giveConsoleSender(), messages);
	}
	
	public final Util doUtilSendConsole(final String message)
	{
		return this.doSendConsole(this.giveUtilName(), message);
	}
	
	public final Util doSendColouredConsole(final String message)
	{
		return this.doSend(this.giveColouredConsoleSender(), message);
	}
	
	public final Util doSendColouredConsole(final String pluginName, final String message)
	{
		return this.doSendColouredConsole("[" + Util.nonEmpty(pluginName) + "] " + Util.nonEmpty(message));
	}
	
	public final Util doSendColouredConsole(final RhenowarPlugin plugin, final String message)
	{
		return this.doSendColouredConsole(Util.nonNull(plugin).giveName(), Util.nonEmpty(message));
	}
	
	public final Util doSendColouredConsole(final String... messages)
	{
		return this.doSend(this.giveColouredConsoleSender(), messages);
	}
	
	public final Util doUtilSendColouredConsole(final String message)
	{
		return this.doSendColouredConsole(this.giveUtilName(), message);
	}
	
	public final Util doReportError(final String message, final Throwable throwable)
	{
		return this.doSendConsole(Level.SEVERE, Util.nonEmpty(message) + "! (" + Util.nonNull(throwable).getMessage() + ")");
	}
	
	public final Util doReportErrorSQL(final SQLException exception)
	{
		return this.doReportError("Problem occurred with the database", exception);
	}
	
	public final SendFunction giveSendFunction(final CommandSender sender, final RhenowarPlugin plugin)
	{
		return Util.nonNull(sender) instanceof Player ? this.giveSendFunction((Player) sender, plugin) : this.giveSendFunction(plugin);
	}
	
	public final SendFunction giveSendFunction(final CommandSender sender)
	{
		return Util.nonNull(sender) instanceof Player ? this.giveSendFunction((Player) sender) : this.giveSendFunction();
	}
	
	public final SendFunction giveSendFunction(final Player player, final RhenowarPlugin plugin)
	{
		return message -> this.doSendMessage(player, plugin, message);
	}
	
	public final SendFunction giveSendFunction(final Player player)
	{
		return message -> this.doSend(player, message);
	}
	
	public final SendFunction giveSendFunction(final RhenowarPlugin plugin)
	{
		return message -> this.doSendColouredConsole(plugin, message);
	}
	
	public final SendFunction giveSendFunction()
	{
		return this::doSendColouredConsole;
	}
	
	public final SendFunction giveUtilSendFunction(final CommandSender sender)
	{
		return Util.nonNull(sender) instanceof Player ? this.giveUtilSendFunction((Player) sender) : this.giveUtilSendFunction();
	}
	
	public final SendFunction giveUtilSendFunction(final Player player)
	{
		return message -> this.doUtilSendMessage(player, message);
	}
	
	public final SendFunction giveUtilSendFunction()
	{
		return this::doUtilSendColouredConsole;
	}
	
	public final boolean doTeleportToLobby(final Player player)
	{
		final String prefix = this.giveUtilConfigPrefix() + "spawn.";
		final Configuration configLocations = this.giveUtilConfigLocations();
		if(configLocations.isString(prefix + "W"))
			return Util.nonNull(player).teleport(this.getLocation(configLocations, prefix));
		return false;
	}
	
	public final Util doKick(final Player player, final String reason)
	{
		Util.nonNull(player).kickPlayer(Util.nonEmpty(reason).replace('&', '§'));
		return this;
	}
	
	public final Util doKick(final Player player, final Throwable throwable)
	{
		String reason = Util.nonNull(throwable).toString();
		final StackTraceElement[] traces = throwable.getStackTrace();
		final int length = traces.length;
		if(length > 0)
		{
			reason += " at " + traces[0];
			for(int i = 1; i < Math.min(3, length); i++)
				reason += (i + 1 == length ? " and " : ", ") + traces[i];
			if(length > 3)
				reason += " and " + (length - 3) + " more";
		}
		return this.doKick(player, reason);
	}
	
	public final Util doAdjustTeam(final Team team, final Map<Option, OptionStatus> options, final ChatColor color)
	{
		Util.nonNull(team);
		if(options != null)
			for(final Option option : options.keySet())
				team.setOption(option, options.get(option));
		if(color != null)
		{
			team.setPrefix(color.toString());
			team.setSuffix(ChatColor.RESET.toString());
		}
		return this;
	}
	
	public final Util doAdjustTeam(final Team team, final boolean neverCollision, final ChatColor color)
	{
		final Map<Option, OptionStatus> options = new HashMap<Option, OptionStatus>();
		if(neverCollision)
			options.put(Option.COLLISION_RULE, OptionStatus.NEVER);
		return this.doAdjustTeam(team, options, color);
	}
	
	public final Util doAdjustTeam(final Team team, final boolean neverCollision)
	{
		return this.doAdjustTeam(team, neverCollision, null);
	}
	
	public final Util doAdjustTeam(final Team team, final ChatColor color)
	{
		return this.doAdjustTeam(team, null, color);
	}
	
	public final Util doAdjustTeam(final Team team, final Team original)
	{
		Util.nonNull(original);
		final Map<Option, OptionStatus> options = new HashMap<Option, OptionStatus>();
		final String prefix = original.getPrefix(), suffix = original.getSuffix();
		for(final Option option : Option.values())
			options.put(option, original.getOption(option));
		return this.doAdjustTeam(team, options, prefix != null && suffix != null && prefix.length() == 2 && prefix.charAt(0) == '&' && suffix.equals(ChatColor.RESET.toString()) ? ChatColor.getByChar(prefix.charAt(1)) : null);
	}
	
	public final World doCreateWorld(final WorldCreator creator)
	{
		return this.giveServer().createWorld(Util.nonNull(creator));
	}
	
	public final World doLoadWorld(final WorldData data)
	{
		WorldGenerator generator = null;
		if(Util.nonNull(data).hasGenerator())
			try
			{
				generator = (WorldGenerator) data.getGenerator().getMethod("giveGenerator").invoke(null);
			}
			catch(final ReflectiveOperationException | ClassCastException e)
			{
				e.printStackTrace();
			}
		final World world = this.doCreateWorld(this.giveWorldCreator(generator, data.getEnvironment(), data.getVariant(), data.giveName(), data.getStructures(), data.getSettings(), data.getSeed()));
		if(world != null)
			data.doExportData(world);
		return world;
	}
	
	public final boolean doUnloadWorld(final World world, final boolean save)
	{
		for(final Player player : Util.nonNull(world).getPlayers())
			this.doTeleportToLobby(player);
		return this.giveServer().unloadWorld(world, save);
	}
	
	public final boolean doUnloadWorld(final World world)
	{
		return this.doUnloadWorld(world, true);
	}
	
	public final Util doDeleteWorldMinigame(final World world)
	{
		if(Minigame.giveWorlds().contains(Util.nonNull(world)))
		{
			final boolean unload = this.doUnloadWorld(world, false);
			final Util util = this.doDeleteDirectory(world.getWorldFolder());
			return (unload && util != null) & Minigame.giveWorlds().remove(world) ? util : null;
		}
		else
			return null;
	}
	
	@SuppressWarnings("deprecation")
	public final Util doRefreshChunk(final Chunk chunk)
	{
		if(Util.nonNull(chunk).getWorld().refreshChunk(chunk.getX(), chunk.getZ()))
			return this;
		else
			return null;
	}
	
	public final Util doFixLight(final Chunk chunk, final boolean fixLuminance)
	{
		try
		{
			this.doInvokeMethod("o", this.doInvokeMethod("getHandle", Util.nonNull(chunk)));
			if(fixLuminance)
			{
				final World world = chunk.getWorld();
				for(int x = 0; x < 0x10; x++)
					for(int y = 0; y < world.getMaxHeight(); y++)
						for(int z = 0; z < 0x10; z++)
						{
							final Block block = chunk.getBlock(x, y, z);
							if(block != null && !block.isEmpty() && this.isBlockLuminance(block))
								this.doInvokeMethod("w", this.doInvokeMethod("getHandle", world), this.giveClass(Class.forName(this.giveServerData().giveMinecraftServerType().getPackage().getName() + ".BlockPosition"), int.class, x, y, z));
						}
			}
			return this.doRefreshChunk(chunk);
		}
		catch(final ReflectiveOperationException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final Util doFixLight(final Chunk chunk)
	{
		return this.doFixLight(chunk, false);
	}
	
	public final Util doFixLight(final World world, final int x, final int z, final boolean fixLuminance)
	{
		return this.doFixLight(Util.nonNull(world).getChunkAt(x, z), fixLuminance);
	}
	
	public final Util doFixLight(final World world, final int x, final int z)
	{
		return this.doFixLight(world, x, z, false);
	}
	
	public final Util doGlowItem(final ItemStack item) throws ReflectiveOperationException
	{
		List<Object> list = null;
		if(this.giveServerVersion().giveVersion() > ProtocolVersion.giveValue("1.10.2").giveVersion())
		{
			list = this.toList(this.giveTagClearNBT());
			final ItemMeta meta = this.giveItemMeta(item);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
		}
		else
			list = new ArrayList<Object>();
		if(list != null)
			return this.setObjectNBT(this.giveItemNBT(item), "ench", list);
		else
			return null;
	}
	
	public final Object doAscribeItemToTagNBT(final ItemStack item, final Object tag) throws ReflectiveOperationException
	{
		return this.giveMethodNBT("doAscribeItemToTag", Util.nonNull(item), Util.nonNull(tag));
	}
	
	public final Util doWriteTagNBT(final Object tag, final DataOutput data) throws ReflectiveOperationException
	{
		this.giveMethodNBT("doWrite", Util.nonNull(tag), Util.nonNull(data));
		return this;
	}
	
	public final Util doWriteTagNBT(final Object tag, final OutputStream stream) throws ReflectiveOperationException
	{
		return this.doWriteTagNBT(tag, (DataOutput) new DataOutputStream(Util.nonNull(stream)));
	}
	
	public final byte[] doWriteTagNBT(final Object tag) throws ReflectiveOperationException
	{
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		this.doWriteTagNBT(tag, stream);
		return stream.toByteArray();
	}
	
	public final Object doReadTagNBT(final DataInput data) throws ReflectiveOperationException
	{
		return this.giveMethodNBT("doRead", Util.nonNull(data));
	}
	
	public final Object doReadTagNBT(final InputStream stream) throws ReflectiveOperationException
	{
		return this.doReadTagNBT((DataInput) new DataInputStream(Util.nonNull(stream)));
	}
	
	public final Object doReadTagNBT(final byte[] array) throws ReflectiveOperationException
	{
		return this.doReadTagNBT(new ByteArrayInputStream(Util.nonNull(array)));
	}
	
	public final Object doInvokeMethod(final Method method, final Object object, final Object... parameters) throws ReflectiveOperationException
	{
		return Util.nonNull(method).invoke(object, Util.nonNull(parameters));
	}
	
	public final Object doInvokeMethod(final String method, final Object object, final Class<?> clazz, final Object... parameters) throws ReflectiveOperationException
	{
		return this.doInvokeMethod(Util.nonNull(clazz).getMethod(Util.nonEmpty(method), this.giveTypes(parameters)), object, parameters);
	}
	
	public final Object doInvokeMethod(final String method, final Object object, final Object... parameters) throws ReflectiveOperationException
	{
		return this.doInvokeMethod(method, object, Util.nonNull(object).getClass(), parameters);
	}
	
	public final Object doInvokeMethod(final String method, final Class<?> clazz, final Object... parameters) throws ReflectiveOperationException
	{
		return this.doInvokeMethod(method, null, clazz, parameters);
	}
	
	public final Action<?> toAction(final Object action) throws ReflectiveOperationException
	{
		Util.nonNull(action);
		Action<?> result = null;
		final Object nameObject = this.doInvokeMethod("giveName", action), playerObject = this.doInvokeMethod("givePlayer", action);
		if(nameObject != null && nameObject instanceof String && playerObject != null && playerObject instanceof Player)
		{
			final String name = (String) nameObject;
			final Player player = (Player) playerObject;
			result = Action.giveAction(name, player);
			if(result == null)
			{
				final Object type = this.doInvokeMethod("giveType", action);
				if(type != null && type instanceof Enum)
					switch(((Enum<?>) type).name().toLowerCase())
					{
						case "inv":
						{
							final Object inventory = this.doInvokeMethod("giveInventory", action);
							if(inventory != null && inventory instanceof Inventory)
								result = new ActionInv(name, (Inventory) inventory, player);
						} break;
						case "com":
						{
		
							final Object message = this.doInvokeMethod("giveMessage", action);
							if(message != null && message instanceof String)
								result = new ActionCom(name, (String) message, player);
						} break;
						case "end":
						{
							result = new ActionEnd<Object>(name, player);
						} break;
					}
			}
		}
		return result;
	}
	
	public final ItemAction toItemAction(final Object item) throws ReflectiveOperationException
	{
		Util.nonNull(item);
		final Object itemObject = this.doInvokeMethod("giveItemStack", item);
		final Object actionObject = this.doInvokeMethod("giveAction", item);
		final Object scriptObject = this.doInvokeMethod("isScript", item);
		if(itemObject != null && itemObject instanceof ItemStack && actionObject != null && actionObject instanceof String && scriptObject != null && scriptObject instanceof Boolean)
		{
			final ItemStack itemStack = this.giveItemCraft((ItemStack) itemObject);
			final String action = (String) actionObject;
			return new ItemAction(itemStack, ((boolean) scriptObject ? "" : "!") + action);
		}
		else
			return null;
	}
	
	public final <T> List<T> toList(final T... array)
	{
		final List<T> list = new ArrayList<T>();
		for(final T content : Util.nonNull(array))
			list.add(content);
		return list;
	}
	
	public final <T> GenericList<T> toListGeneric(final Class<T> type, final T... array)
	{
		return GenericList.valueOf(this.toList(array), type);
	}
	
	public final int toInteger(final Object obj)
	{
		return Integer.valueOf(Util.nonEmpty(String.valueOf(Util.nonNull(obj))));
	}
	
	public final synchronized String toString(final Rhenowar rhenowar, final String className, final boolean hash, final boolean fields)
	{
		String content = ChatColor.RESET.toString();
		final String regex = "[\\s\\[\\]{}=,]";
		final Map<String, Object> map =  Util.nonNull(Util.nonNull(rhenowar).giveProperties(new LinkedHashMap<String, Object>()));
		if(hash)
			map.put("hash", Integer.toHexString(rhenowar.hashCode()) + "@" + Integer.toHexString(System.identityHashCode(rhenowar)));
		if(fields)
			map.put("fields", this.giveFields(rhenowar));
		final boolean clear = this.worn.size() == 0;
		this.worn.add(rhenowar);
		for(final String key : new TreeSet<String>(map.keySet()))
		{
			boolean append = true;
			final Object value = map.get(key);
			final Class<?> clazz = value != null ? value.getClass() : Object.class;
			final boolean array = clazz.isArray() && !clazz.getComponentType().isPrimitive(), rhenowarArray = array && IRhenowar.class.isAssignableFrom(clazz.getComponentType()), rhenowarList = !array && value instanceof List;
			if(value instanceof IRhenowar || rhenowarArray || rhenowarList)
			{
				final List<?> list = rhenowarArray ? this.toList((Object[]) value) : rhenowarList ? (List<?>) value : this.toList(new IRhenowar[]{(IRhenowar) value});
				for(int i = 0; i < list.size(); i++)
				{
					final Object obj = list.get(i);
					if(obj instanceof IRhenowar)
					{
						if(this.worn.contains(obj))
						{
							if(rhenowarArray)
								((Object[]) value)[i] = null;
							else if(rhenowarList)
								try
								{
									list.remove(i);
								}
								catch(final UnsupportedOperationException e) {}
							else
								append = false;
						}
						else
							this.worn.add((IRhenowar) obj);
					}
				}
			}
			content += key.replaceAll(regex, "") + ChatColor.RESET + "=" + (append ? String.valueOf(value != null ? (array ? this.toList((Object[]) value) : (clazz == Boolean.class ? ((boolean) value ? ChatColor.GREEN : ChatColor.RED).toString() : "") + value) : null).trim() + ChatColor.RESET : "<repeat>") + ", ";
		}
		if(clear)
			this.worn.clear();
		final int contentLength = content.length();
		return (className == null ? Util.nonEmpty(rhenowar.giveClassName()) : className) + ChatColor.RESET +  "{" + (contentLength > 0x1900 ? content.substring(0, 0x1900) + ChatColor.RESET +  "..." : contentLength > 1 ? content.substring(0, contentLength - 2) : "") + ChatColor.RESET + "}";
	}
	
	public final synchronized String toString(final Rhenowar rhenowar, final boolean hash, final boolean fields)
	{
		return this.toString(rhenowar, null, hash, fields);
	}
	
	public final synchronized String toString(final Rhenowar rhenowar, final String className, final boolean hash)
	{
		return this.toString(rhenowar, className, hash, false);
	}
	
	public final synchronized String toString(final Rhenowar rhenowar, final boolean hash)
	{
		return this.toString(rhenowar, null, hash);
	}
	
	public final synchronized String toString(final Rhenowar rhenowar, final String className)
	{
		return this.toString(rhenowar, className, false);
	}
	
	public final synchronized String toString(final Rhenowar rhenowar)
	{
		return this.toString(rhenowar, null);
	}
	
	public final synchronized String toString(final Object obj)
	{
		if(obj instanceof Rhenowar)
			return this.toString((Rhenowar) obj);
		else
			return String.valueOf(obj);
	}
	
	public static final <T> T nonNull(final T obj, final String message) throws NullPointerException
	{
		if(obj == null)
		{
			Util.doThrowNPE(message);
			return null;
		}
		else
			return obj;
	}
	
	public static final <T> T nonNull(final T obj) throws NullPointerException
	{
		return Util.nonNull(obj, null);
	}
	
	public static final <T> T[] nonEmpty(final T[] array, final String message) throws NullPointerException, IllegalArgumentException
	{
		if(Util.nonNull(array, message).length == 0)
		{
			Util.doThrowIAE(message);
			return null;
		}
		else
			return array;
	}
	
	public static final <T> T[] nonEmpty(final T[] array) throws NullPointerException, IllegalArgumentException
	{
		return Util.nonEmpty(array, null);
	}
	
	public static final <T extends Collection<E>, E> T nonEmpty(final T collection, final String message) throws NullPointerException, IllegalArgumentException
	{
		if(Util.nonNull(collection, message).isEmpty())
		{
			Util.doThrowIAE(message);
			return null;
		}
		else
			return collection;
	}
	
	public static final <T extends Collection<E>, E> T nonEmpty(final T collection) throws NullPointerException, IllegalArgumentException
	{
		return Util.nonEmpty(collection, null);
	}
	
	public static final <T extends Map<K, V>, K, V> T nonEmpty(final T map, final String message) throws NullPointerException, IllegalArgumentException
	{
		if(Util.nonNull(map, message).isEmpty())
		{
			Util.doThrowIAE(message);
			return null;
		}
		else
			return map;
	}
	
	public static final <T extends Map<K, V>, K, V> T nonEmpty(final T map) throws NullPointerException, IllegalArgumentException
	{
		return Util.nonEmpty(map, null);
	}
	
	public static final <T extends CharSequence> T nonEmpty(final T str, final String message) throws NullPointerException, IllegalArgumentException
	{
		if(Util.nonNull(str, message).length() == 0)
		{
			Util.doThrowIAE(message);
			return null;
		}
		else
			return str;
	}
	
	public static final <T extends CharSequence> T nonEmpty(final T str) throws NullPointerException, IllegalArgumentException
	{
		return Util.nonEmpty(str, null);
	}
	
	public static final void doThrowNPE(final String message) throws NullPointerException
	{
		throw message != null ? new NullPointerException(message) : new NullPointerException();
	}
	
	public static final void doThrowNPE() throws NullPointerException
	{
		Util.doThrowNPE(null);
	}
	
	public static final void doThrowIAE(final String message) throws IllegalArgumentException
	{
		throw message != null ? new IllegalArgumentException(message) : new IllegalArgumentException();
	}
	
	public static final void doThrowIAE() throws IllegalArgumentException
	{
		Util.doThrowIAE(null);
	}
	
	public static final void doThrowFNFE(final String message) throws FileNotFoundException
	{
		throw message != null ? new FileNotFoundException(message) : new FileNotFoundException();
	}
	
	public static final void doThrowFNFE() throws FileNotFoundException
	{
		Util.doThrowFNFE("File does not exist");
	}
	
	public static final Util giveUtilProvider(final RegisteredServiceProvider<Util> service)
	{
		if(service != null)
			return service.getProvider();
		else
			return null;
	}
	
	public static final Util giveUtilProvider()
	{
		return Util.giveUtilProvider(Bukkit.getServicesManager().getRegistration(Util.class));
	}
	
	public static final Util[] giveUtilProviders()
	{
		final List<Util> list = new ArrayList<Util>();
		for(final RegisteredServiceProvider<Util> service : Bukkit.getServicesManager().getRegistrations(Util.class))
			if(Util.hasUtilProvider(service))
				list.add(Util.giveUtilProvider(service));
		return list.toArray(new Util[list.size()]);
	}
	
	public static final Util giveUtil()
	{
		return Util.util;
	}
	
	public static final boolean hasUtilProvider(final RegisteredServiceProvider<Util> service)
	{
		return Util.giveUtilProvider(service) != null;
	}
	
	public static final boolean hasUtilProvider()
	{
		return Util.giveUtilProvider() != null;
	}
	
	public static final boolean hasUtilProviders()
	{
		return Util.giveUtilProviders().length > 0;
	}
	
	public static final boolean hasUtil()
	{
		return Util.giveUtil() != null;
	}

}
