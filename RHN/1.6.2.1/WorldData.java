package net.polishgames.rhenowar.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.Vector;
import net.polishgames.rhenowar.util.serialization.Location;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;
import net.polishgames.rhenowar.util.world.WorldGenerator;

public final class WorldData extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	protected static final List<WorldData> worlds = new ArrayList<WorldData>();
	
	private transient World world;
	private final String name;
	private final List<Region> protect;
	
	private WorldData(final World world)
	{
		this.world = Util.nonNull(world);
		this.name = this.giveWorld().getName();
		this.protect = new ArrayList<Region>();
		this.doImportData().
			setGameMode(Util.hasUtil() ? Util.giveUtil().giveServer().getDefaultGameMode() : Bukkit.getDefaultGameMode()).
			setWeather(true).setHunger(true).setFall(true).setBuild(true);
	}
	
	private long seed;
	private Class<? extends WorldGenerator> generator;
	private Environment environment;
	private WorldType variant;
	private boolean structures, animals, monsters, pvp, memory, weather, hunger, fall, build;
	private String settings;
	private Difficulty difficulty;
	private GameMode gamemode;
	private Location spawn;
	
	public final World giveWorld()
	{
		if(!this.hasWorld())
			this.doAdjustWorld(Util.hasUtil() ? Util.giveUtil().giveWorld(this.giveName()) : Bukkit.getWorld(this.giveName()));
		return this.world;
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final List<Region> giveProtect()
	{
		return Collections.unmodifiableList(this.protect);
	}
	
	public final WorldData addProtect(final Region region)
	{
		this.protect.add(Util.nonNull(region));
		return this;
	}
	
	public final WorldData delProtect(final Region region)
	{
		this.protect.remove(Util.nonNull(region));
		return this;
	}
	
	public final WorldData setSeed(final long seed)
	{
		this.seed = seed;
		return this;
	}
	
	public final WorldData setGenerator(final Class<? extends WorldGenerator> generator)
	{
		this.generator = Util.nonNull(generator);
		return this;
	}
	
	public final WorldData setEnvironment(final Environment environment)
	{
		this.environment = Util.nonNull(environment);
		return this;
	}
	
	public final WorldData setVariant(final WorldType variant)
	{
		this.variant = Util.nonNull(variant);
		return this;
	}
	
	public final WorldData setStructures(final boolean structures)
	{
		this.structures = structures;
		return this;
	}
	
	public final WorldData setSettings(final String settings)
	{
		this.settings = Util.nonNull(settings);
		return this;
	}
	
	public final WorldData setAnimals(final boolean animals)
	{
		this.animals = animals;
		return this;
	}
	
	public final WorldData setMonsters(final boolean monsters)
	{
		this.monsters = monsters;
		return this;
	}
	
	public final WorldData setPvP(final boolean pvp)
	{
		this.pvp = pvp;
		return this;
	}
	
	public final WorldData setDifficulty(final Difficulty difficulty)
	{
		this.difficulty = Util.nonNull(difficulty);
		return this;
	}
	
	public final WorldData setMemory(final boolean memory)
	{
		this.memory = memory;
		return this;
	}
	
	public final WorldData setGameMode(final GameMode gamemode)
	{
		this.gamemode = Util.nonNull(gamemode);
		return this;
	}
	
	public final WorldData setWeather(final boolean weather)
	{
		this.weather = weather;
		return this;
	}
	
	public final WorldData setHunger(final boolean hunger)
	{
		this.hunger = hunger;
		return this;
	}
	
	public final WorldData setFall(final boolean fall)
	{
		this.fall = fall;
		return this;
	}
	
	public final WorldData setBuild(final boolean build)
	{
		this.build = build;
		return this;
	}
	
	public final WorldData setSpawn(final org.bukkit.Location spawn)
	{
		this.spawn = new Location(Util.nonNull(spawn));
		return this;
	}
	
	public final long getSeed()
	{
		return this.seed;
	}
	
	public final Class<? extends WorldGenerator> getGenerator()
	{
		return this.generator;
	}
	
	public final Environment getEnvironment()
	{
		return this.environment;
	}
	
	public final WorldType getVariant()
	{
		return this.variant;
	}
	
	public final boolean getStructures()
	{
		return this.structures;
	}
	
	public final String getSettings()
	{
		return this.settings == null ? "" : this.settings;
	}
	
	public final boolean getAnimals()
	{
		return this.animals;
	}
	
	public final boolean getMonsters()
	{
		return this.monsters;
	}
	
	public final boolean getPvP()
	{
		return this.pvp;
	}
	
	public final Difficulty getDifficulty()
	{
		return this.difficulty;
	}
	
	public final boolean getMemory()
	{
		return this.memory;
	}
	
	public final GameMode getGameMode()
	{
		return this.gamemode;
	}
	
	public final boolean getWeather()
	{
		return this.weather;
	}
	
	public final boolean getHunger()
	{
		return this.hunger;
	}
	
	public final boolean getFall()
	{
		return this.fall;
	}
	
	public final boolean getBuild()
	{
		return this.build;
	}
	
	public final org.bukkit.Location getSpawn()
	{
		return this.spawn.toLocationBukkit();
	}
	
	public final boolean hasWorld()
	{
		return this.world != null;
	}
	
	public final boolean hasGenerator()
	{
		return this.getGenerator() != null;
	}
	
	public final boolean hasProtect()
	{
		return !this.giveProtect().isEmpty();
	}
	
	public final WorldData doImportData(final World world)
	{
		Util.nonNull(world);
		this.doAdjustWorld(world);
		final WorldData data = this.
			setSeed(world.getSeed()).
			setEnvironment(world.getEnvironment()).
			setVariant(world.getWorldType()).
			setStructures(world.canGenerateStructures()).
			setAnimals(world.getAllowAnimals()).
			setMonsters(world.getAllowMonsters()).
			setPvP(world.getPVP()).
			setDifficulty(world.getDifficulty()).
			setMemory(world.getKeepSpawnInMemory()).
			setSpawn(world.getSpawnLocation());
		final ChunkGenerator generator = world.getGenerator();
		if(generator != null && generator instanceof WorldGenerator)
			data.setGenerator((Class<? extends WorldGenerator>) generator.getClass());
		if(Util.hasUtil())
			data.setSettings(Util.giveUtil().giveWorldGeneratorOptions(world));
		return data;
	}
	
	public final WorldData doImportData()
	{
		return this.doImportData(this.giveWorld());
	}
	
	public final WorldData doExportData(final World world)
	{
		Util.nonNull(world);
		this.doAdjustWorld(world);
		world.setPVP(this.getPvP());
		world.setDifficulty(this.getDifficulty());
		world.setKeepSpawnInMemory(this.getMemory());
		final Vector spawn = this.getSpawn().toVector();
		world.setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
		return this;
	}
	
	public final WorldData doExportData()
	{
		return this.doExportData(this.giveWorld());
	}
	
	protected final void doAdjustWorld(final World world)
	{
		if(!this.hasWorld())
		{
			this.world = world;
			WorldData.addWorldData(this);
		}
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("world", this.world);
		map.put("name", this.giveName());
		map.put("protect", this.giveProtect());
		map.put("seed", this.getSeed());
		map.put("generator", this.getGenerator());
		map.put("environment", this.getEnvironment());
		map.put("variant", this.getVariant());
		map.put("structures", this.getStructures());
		map.put("settings", this.getSettings());
		map.put("animals", this.getAnimals());
		map.put("monsters", this.getMonsters());
		map.put("pvp", this.getPvP());
		map.put("difficulty", this.getDifficulty());
		map.put("memory", this.getMemory());
		map.put("gamemode", this.getGameMode());
		map.put("weather", this.getWeather());
		map.put("hunger", this.getHunger());
		map.put("fall", this.getFall());
		map.put("build", this.getBuild());
		map.put("spawn", this.spawn);
		return map;
	}
	
	public static final WorldData giveWorldData(final World world)
	{
		Util.nonNull(world);
		for(final WorldData data : WorldData.worlds)
			if(data.giveName().equals(world.getName()))
				return data;
		return WorldData.addWorldData(new WorldData(world));
	}
	
	public static final WorldData addWorldData(final WorldData world)
	{
		if(!WorldData.worlds.contains(Util.nonNull(world)))
		{
			WorldData.worlds.add(world);
			return world;
		}
		else
			return null;
	}
	
	public static final WorldData delWorldData(final WorldData world)
	{
		if(WorldData.worlds.contains(Util.nonNull(world)))
		{
			WorldData.worlds.remove(Util.nonNull(world));
			return world;
		}
		else
			return null;
	}
	
}
