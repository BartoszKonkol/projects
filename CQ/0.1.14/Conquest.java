package net.polishgames.rhenowar.conquest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Animo2233.actionbar.ActionBar;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public final class Conquest extends JavaPlugin
{
	
	private static volatile Conquest conquest;

	private Random random;
	private Logger logger;
	private FileConfiguration fileConfig;
	private FileConfiguration fileRegion;
	private FileConfiguration fileTemp;
	private Listener listener;
	private Location lobby;
	private Scoreboard scoreboard;
	private WorldEditPlugin worldedit;
	private ActionBar actionbar;
	private BarAPI barapi;
	private Game game;
	private ClassLoader loaderJNF;
	
	@Override
	public final void onLoad()
	{
		Conquest.conquest = this;
		this.random = new Random();
		this.logger = Conquest.giveConquest().getLogger();
		
		this.fileConfig = this.doPrepareConfiguration();
		this.fileRegion = this.doPrepareConfiguration("region");
		this.fileTemp = this.doPrepareConfiguration("temp", true);
		
		try
		{
			this.loaderJNF = ClassLoader.giveClassLoader(this.giveFileJAR("JNF-1_1_09").toURI().toURL(), this.getClass());
		}
		catch(final MalformedURLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public final void onEnable()
	{
		Conquest.doSend("Conquest!");
		Conquest.doSend("Polish Games Studio");
		Conquest.doSend("Rhenowar");
		Conquest.doSend("Bartosz Konkol");
		
		final World world = Bukkit.createWorld(new WorldCreator(this.giveFileConfig().getString("conquest.MainWorld")));
		final boolean already = this.isRegionConfigurationAlready(world);
		final String pathLobby = "conquest." + world.getName() + ".lobby.";
		final String pathMainLobby = "conquest.MainLobby.";
		this.lobby = new Location(world, already ? this.giveFileRegion().getDouble(pathLobby + "x") : this.giveFileConfig().getDouble(pathMainLobby + "x"), already ? this.giveFileRegion().getDouble(pathLobby + "y") : this.giveFileConfig().getDouble(pathMainLobby + "y"), already ? this.giveFileRegion().getDouble(pathLobby + "z") : this.giveFileConfig().getDouble(pathMainLobby + "z"), already ? (float) this.giveFileRegion().getDouble(pathLobby + "pitch") : 0.0F, already ? (float) this.giveFileRegion().getDouble(pathLobby + "yaw") : 0.0F);
		
		this.listener = new Listeners();
		this.game = new Game();
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		this.worldedit = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		this.actionbar = (ActionBar) Bukkit.getPluginManager().getPlugin("ActionBarAPI");
		this.barapi = (BarAPI) Bukkit.getPluginManager().getPlugin("BarAPI");
		
		if(this.worldedit == null)
		{
			Conquest.doSend("WorldEdit is off!");
			this.setEnabled(false);
		}
		else
		{
			this.getCommand("conquest").setExecutor(new Commands());
			((Listeners) this.giveListener()).doRegister();
			final Objective sidebar = this.giveScoreboard().registerNewObjective("sidebar", "dummy");
			sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
			sidebar.setDisplayName(this.getName());
			sidebar.getScore("").setScore(0);
			if(this.isRegionConfigurationAlready())
				this.giveGame().doPrepare();
		}
		for(final Team team : Team.values())
		{
			final ItemStack item = new ItemStack(Material.WOOL, 1, (short) team.giveBlockHardData());
			final ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("Kamienna we³na");
			item.setItemMeta(meta);
			final ShapedRecipe recipe = new ShapedRecipe(item);
			recipe.shape("##", "##");
			recipe.setIngredient('#', Material.WOOL, team.giveBlockData());
			Bukkit.getServer().addRecipe(recipe);
		}
	}
	
	@Override
	public final void onDisable() 
	{
		Conquest.doSend("Bye, Bye!");
		
		if(this.giveGame().isAlive())
		{
			this.giveGame().doSuspend();
			this.giveGame().doInterrupt();
		}
	}
	
	public final File giveFile(final String name)
	{
		return new File(this.getDataFolder(), name);
	}
	
	public final File giveFileJAR(final String name)
	{
		return this.giveFile(name + ".jar");
	}
	
	public final File giveFileYML(final String name)
	{
		return this.giveFile(name + ".yml");
	}
	
	public final File giveFileTXT(final String name)
	{
		return this.giveFile(name + ".txt");
	}
	
	public final FileConfiguration doLoadConfiguration(final String name)
	{
		if(name.equals("config"))
			return this.getConfig();
		else
			return YamlConfiguration.loadConfiguration(this.giveFileYML(name));
	}
	
	public final FileConfiguration doPrepareConfiguration(final String name, final boolean replace)
	{
		this.saveResource(this.giveFileYML(name).getName(), replace);
		return this.doLoadConfiguration(name);
	}
	
	public final FileConfiguration doPrepareConfiguration(final String name)
	{
		return this.doPrepareConfiguration(name, false);
	}
	
	public final FileConfiguration doPrepareConfiguration()
	{
		return this.doPrepareConfiguration("config");
	}
	
	public final void doSaveConfiguration(final FileConfiguration file, final String name) throws IOException
	{
		file.save(this.giveFileYML(name));
	}
	
	public final boolean isRegionConfigurationAlready(final World world)
	{
		return this.giveFileRegion().getBoolean("conquest." + (world == null ? this.giveLobby().getWorld() : world).getName() + ".already", false);
	}
	
	public final boolean isRegionConfigurationAlready()
	{
		return this.isRegionConfigurationAlready(null);
	}
	
	public final Random giveRandom()
	{
		return Objects.requireNonNull(this.random);
	}
	
	public final Logger giveLogger()
	{
		return Objects.requireNonNull(this.logger);
	}
	
	public final FileConfiguration giveFileConfig()
	{
		return Objects.requireNonNull(this.fileConfig);
	}
	
	public final FileConfiguration giveFileRegion()
	{
		return Objects.requireNonNull(this.fileRegion);
	}
	
	public final FileConfiguration giveFileTemp()
	{
		return Objects.requireNonNull(this.fileTemp);
	}
	
	public final Listener giveListener()
	{
		return Objects.requireNonNull(this.listener);
	}
	
	public final Location giveLobby()
	{
		return Conquest.doCloneLocation(this.lobby);
	}
	
	public final Scoreboard giveScoreboard()
	{
		return Objects.requireNonNull(this.scoreboard);
	}
	
	public final WorldEditPlugin giveWorldEdit()
	{
		return Objects.requireNonNull(this.worldedit);
	}
	
	public final ActionBar giveActionBar()
	{
		return Objects.requireNonNull(this.actionbar);
	}
	
	public final BarAPI giveBarAPI()
	{
		return Objects.requireNonNull(this.barapi);
	}
	
	public final Game giveGame()
	{
		return Objects.requireNonNull(this.game);
	}
	
	public final ClassLoader giveLoaderJNF()
	{
		return Objects.requireNonNull(this.loaderJNF);
	}
	
	public final List<String> giveManagers()
	{
		final List<String> list = new ArrayList<String>();
		list.add(Data.AUTHOR_NICK.giveText());
		list.addAll(this.giveFileConfig().getStringList("conquest.Managers"));
		return list;
	}
	
	public static final void doSendToServer(final String message, final Level level)
	{
		Conquest.giveConquest().giveLogger().log(Objects.requireNonNull(level), Objects.requireNonNull(message));
	}
	
	public static final void doSendInfoToServer(final String message)
	{
		Conquest.doSendToServer(message, Level.INFO);
	}
	
	public static final void doSendToPlayer(final String message, final MessageType type, final Player player)
	{
		Objects.requireNonNull(type).doSend(Objects.requireNonNull(player), Objects.requireNonNull(message));
	}
	
	public static final void doSendNormalToPlayer(final String message, final Player player)
	{
		Conquest.doSendToPlayer(message, MessageType.NORMAL, player);
	}
	
	public static final void doSendDebugToPlayer(final String message, final Player player)
	{
		Conquest.doSendToPlayer(message, MessageType.DEBUG, player);
	}
	
	public static final void doSendSevereToPlayer(final String message, final Player player)
	{
		Conquest.doSendToPlayer(message, MessageType.SEVERE, player);
	}
	
	public static final void doSendPositiveToPlayer(final String message, final Player player)
	{
		Conquest.doSendToPlayer(message, MessageType.POSITIVE, player);
	}
	
	public static final void doSend(final String message, final Level level)
	{
		Conquest.doSendToServer(message, level);
	}
	
	public static final void doSend(final String message)
	{
		Conquest.doSendInfoToServer(message);
	}
	
	public static final void doSend(final String message, final MessageType type, final Player player)
	{
		Conquest.doSendToPlayer(message, type, player);
	}
	
	public static final void doSend(final String message, final Player player)
	{
		Conquest.doSendNormalToPlayer(message, player);
	}
	
	public static final Location doCloneLocation(final Location location)
	{
		return new Location(Objects.requireNonNull(location).getWorld(), location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
	}
	
	public static final Conquest giveConquest()
	{
		return Objects.requireNonNull(Conquest.conquest);
	}
	
}
