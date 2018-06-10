package net.polishgames.rhenowar.util.minigame;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.FileRHN;
import net.polishgames.rhenowar.util.Region;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.WorldData;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;
import net.polishgames.rhenowar.util.world.WorldGeneratorArea;
import net.polishgames.rhenowar.util.world.area.Area;

public class Minigame extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.5.3");
	
	protected static int index;
	private static List<World> worlds = new ArrayList<World>();
	
	private final transient RhenowarPluginMinigame plugin;
	private final Area area;
	
	public Minigame(final RhenowarPluginMinigame plugin, final Area area)
	{
		this.plugin = Util.nonNull(plugin);
		this.area = Util.nonNull(area);
	}
	
	protected Minigame(final RhenowarPluginMinigame plugin, final Area area, final Map<String, String> rule, final WorldData data, final Long time)
	{
		this(plugin, area);
		if(rule != null && !rule.isEmpty())
			this.rule = rule;
		if(data != null)
			this.setWorldData(data);
		if(time != null)
			this.setTime(time);
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final World world, final Area area)
	{
		this(plugin, area);
		this.setWorldData(WorldData.giveWorldData(Util.nonNull(world)));
		this.setTime(world.getFullTime());
		if(Util.hasUtil())
		{
			final Map<String, String> rule = Util.giveUtil().giveGameRule(world);
			if(!rule.isEmpty())
				this.rule = rule;
		}
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final World world, final Region region)
	{
		this(plugin, new Area(Util.nonNull(world), Util.nonNull(region)));
		this.setWorldData(WorldData.giveWorldData(world));
		this.setTime(world.getFullTime());
		if(Util.hasUtil())
		{
			final Map<String, String> rule = Util.giveUtil().giveGameRule(world);
			if(!rule.isEmpty())
				this.rule = rule;
		}
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final String name, final Region region)
	{
		this(plugin, Util.hasUtil() ? Util.giveUtil().giveWorld(Util.nonEmpty(name)) : Bukkit.getWorld(Util.nonEmpty(name)), region);
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final World world, final Player player)
	{
		this(plugin, world, Region.giveRegion(Util.nonNull(player)));
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final String name, final Player player)
	{
		this(plugin, Util.hasUtil() ? Util.giveUtil().giveWorld(Util.nonEmpty(name)) : Bukkit.getWorld(Util.nonEmpty(name)), player);
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final Player player)
	{
		this(plugin, Util.nonNull(player).getWorld(), player);
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final FileRHN file)
	{
		this.plugin = Util.nonNull(plugin);
		final Area area = Minigame.giveArea(file);
		if(area != null)
		{
			this.area = area;
			this.rule = Minigame.giveGameRule(file);
			final WorldData data = Minigame.giveValue(file, "data", WorldData.class);
			final Long time = Minigame.giveValue(file, "time", Long.class);
			if(data != null)
				this.setWorldData(data);
			if(time != null)
				this.setTime(time);
		}
		else
		{
			Util.doThrowNPE();
			this.area = null;
			this.rule = null;
		}
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final File file)
	{
		this.plugin = Util.nonNull(plugin);
		Area area = null;
		Map<String, String> rule = null;
		try
		{
			final FileRHN fileRHN = new FileRHN(file, true);
			area = Minigame.giveArea(fileRHN);
			rule = Minigame.giveGameRule(fileRHN);
			final WorldData data = Minigame.giveValue(fileRHN, "data", WorldData.class);
			final Long time = Minigame.giveValue(fileRHN, "time", Long.class);
			if(data != null)
				this.setWorldData(data);
			if(time != null)
				this.setTime(time);
			fileRHN.close();
		}
		catch(final IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		if(area != null)
		{
			this.area = area;
			this.rule = rule;
		}
		else
		{
			Util.doThrowNPE();
			this.area = null;
			this.rule = null;
		}
	}
	
	public Minigame(final RhenowarPluginMinigame plugin, final String name)
	{
		this(plugin, new File(Util.nonNull(plugin).getDataFolder(), Util.nonEmpty(name).toLowerCase() + "." + FileRHN.EXTENSION));
	}

	private Map<String, String> rule;
	private WorldData worldData;
	private Long time;
	
	public final RhenowarPluginMinigame givePlugin()
	{
		return this.plugin;
	}
	
	public final Area giveArea()
	{
		return this.area;
	}
	
	public final Map<String, String> giveGameRule()
	{
		return this.rule;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("plugin", this.givePlugin());
		map.put("area", this.giveArea());
		map.put("rule", this.giveGameRule());
		map.put("worldData", this.getWorldData());
		map.put("time", this.getTime());
		return map;
	}

	@Override
	public final Minigame clone()
	{
		return this.hasPlugin() ? this.clone(this.givePlugin()) : null;
	}
	
	public Minigame clone(final RhenowarPluginMinigame plugin)
	{
		return new Minigame(Util.nonNull(plugin), this.giveArea().clone(), this.hasGameRule() ? new HashMap<String, String>(this.giveGameRule()) : null, this.getWorldData(), this.getTime());
	}
	
	public final Minigame setWorldData(final WorldData worldData)
	{
		this.worldData = Util.nonNull(worldData);
		return this;
	}
	
	public final Minigame setTime(final Long time)
	{
		this.time = Util.nonNull(time);
		return this;
	}
	
	public final WorldData getWorldData()
	{
		return this.worldData;
	}
	
	public final Long getTime()
	{
		return this.time;
	}
	
	public final boolean hasWorldData()
	{
		return this.getWorldData() != null;
	}
	
	public final boolean hasTime()
	{
		return this.getTime() != null;
	}
	
	public final boolean hasPlugin()
	{
		return this.givePlugin() != null;
	}
	
	public final boolean hasGameRule()
	{
		return this.giveGameRule() != null && !this.giveGameRule().isEmpty();
	}
	
	public Minigame doGameSave(final String name, final String description, final Map<String, ? extends Serializable> objects)
	{
		if(this.hasPlugin())
			try
			{
				final FileRHN file = new FileRHN(this.givePlugin(), Util.nonEmpty(name).toLowerCase());
				file.setTitle(name);
				if(description != null && !description.isEmpty())
					file.setDescription(description);
				final Class<? extends Area> area = this.giveArea().getClass();
				file.addObject(Class.class.getSimpleName().toLowerCase(), area);
				file.addObject(area.getSimpleName().toLowerCase(), this.giveArea());
				if(this.hasWorldData())
				{
					final WorldData data = this.getWorldData();
					file.addObject("data", data);
					if(data.hasWorld())
					{
						final World world = data.giveWorld();
						if(Util.hasUtil())
						{
							final Map<String, String> rule = Util.giveUtil().giveGameRule(world);
							if(!rule.isEmpty())
								file.addObject("rule", new HashMap<String, String>(rule));
						}
						if(!this.hasTime())
							this.setTime(world.getFullTime());
					}
				}
				if(this.hasTime())
					file.addObject("time", this.getTime());
				if(objects != null && !objects.isEmpty())
					for(final String key : objects.keySet())
						if(!file.hasObject(key))
							try
							{
								file.addObject(key, objects.get(key));
							}
							catch(final NullPointerException | IllegalArgumentException e)
							{
								e.printStackTrace();
							}
				file.close();
				return this;
			}
			catch(final IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
				return null;
			}
		else
			return null;
	}
	
	public final Minigame doGameSave(final String name, final Map<String, ? extends Serializable> objects)
	{
		return this.doGameSave(name, null, objects);
	}
	
	public final Minigame doGameSave(final String name, final String description)
	{
		return this.doGameSave(name, description, null);
	}
	
	public final Minigame doGameSave(final String name)
	{
		return this.doGameSave(name, (String) null);
	}
	
	public World doGameLoad()
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final World world = util.doCreateWorld(WorldGeneratorArea.giveCreator("game#" + util.giveFormattedNumber(++Minigame.index, 9), this.giveArea()));
			if(this.hasWorldData())
			{
				final WorldData dataOld = this.getWorldData(), dataNew = WorldData.giveWorldData(world);
				dataNew.setAnimals(dataOld.getAnimals());
				dataNew.setMonsters(dataOld.getMonsters());
				dataNew.setPvP(dataOld.getPvP());
				dataNew.setDifficulty(dataOld.getDifficulty());
				dataNew.setGameMode(dataOld.getGameMode());
				dataNew.setWeather(dataOld.getWeather());
				dataNew.setHunger(dataOld.getHunger());
				dataNew.setFall(dataOld.getFall());
				dataNew.setBuild(dataOld.getBuild());
				final Location spawn = dataOld.getSpawn();
				dataNew.setSpawn(new Location(world, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch()));
				for(final Region region : dataOld.giveProtect())
					dataNew.addProtect(region.clone().add(this.giveArea().giveTranslation().toVectorBlockWE()));
				dataNew.doExportData(world);
			}
			if(this.hasGameRule())
			{
				final Map<String, String> rule = this.giveGameRule();
				for(final String key : rule.keySet())
					world.setGameRuleValue(key, rule.get(key));
			}
			if(this.hasTime())
				world.setFullTime(this.getTime());
			Minigame.giveWorlds().add(world);
			return world;
		}
		else
			return null;
	}
	
	public static final Area giveArea(final FileRHN file)
	{
		Object area = null;
		final String keyClass = Class.class.getSimpleName().toLowerCase();
		if(Util.nonNull(file).hasObject(keyClass))
		{
			final Object clazz = file.giveObject(keyClass);
			if(clazz instanceof Class)
			{
				final String keyArea = ((Class<?>) clazz).getSimpleName().toLowerCase();
				if(file.hasObject(keyArea))
					area = file.giveObject(keyArea);
			}
		}
		if(area != null && area instanceof Area)
			return (Area) area;
		else
			return null;
	}
	
	public static final Map<String, String> giveGameRule(final FileRHN file)
	{
		final Map<String, String> rule = new HashMap<String, String>();
		final String keyRule = "rule";
		if(Util.nonNull(file).hasObject(keyRule))
		{
			final Object object = file.giveObject(keyRule);
			if(object instanceof Map)
			{
				final Map<Object, Object> map = (Map<Object, Object>) object;
				for(final Object key : map.keySet())
					rule.put(String.valueOf(key), String.valueOf(map.get(key)));
			}
		}
		return rule.isEmpty() ? null : Collections.unmodifiableMap(rule);
	}
	
	public static final <T extends Serializable> T giveValue(final FileRHN file, final String key, final Class<T> type)
	{
		T value = null;
		if(Util.nonNull(file).hasObject(Util.nonEmpty(key)))
		{
			final Object object = file.giveObject(key);
			if(Util.nonNull(type).isAssignableFrom(object.getClass()))
				value = (T) file.giveObject(key);
		}
		return value;
	}
	
	public static final List<World> giveWorlds()
	{
		return Minigame.worlds;
	}
	
}
