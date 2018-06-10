package net.polishgames.rhenowar.conquest;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;

public class RegionDetermine
{
	
	protected RegionDetermine(){}
	
	public static Region giveRegion(final RegionType type, final World world)
	{
		String path = givePath(world) + "region.";
		switch(type)
		{
			case AREA:
				path += "area.";
				break;
			case BATTLE:
				path += "battle.";
				break;
		}
		final FileConfiguration file = Conquest.giveConquest().giveFileRegion();
		final String pathMin = path + "min.";
		final String pathMax = path + "max.";
		return new Region(new BlockVector(file.getDouble(pathMin + "x"), file.getDouble(pathMin + "y"), file.getDouble(pathMin + "z")), new BlockVector(file.getDouble(pathMax + "x"), file.getDouble(pathMax + "y"), file.getDouble(pathMax + "z")));
	}
	
	public static Vector giveVector(final VectorType type, final World world)
	{
		final String path = givePathVector(type, world);
		final FileConfiguration file = Conquest.giveConquest().giveFileRegion();
		return new Vector(file.getDouble(path + "x"), file.getDouble(path + "y"), file.getDouble(path + "z"));
	}
	
	public static Vector giveVector(final Location location)
	{
		return new Vector(location.getX(), location.getY(), location.getZ());
	}
	
	public static Location giveLocation(final VectorType type, final World world)
	{
		final Vector vector = giveVector(type, world);
		final String path = givePathVector(type, world);
		final FileConfiguration file = Conquest.giveConquest().giveFileRegion();
		return new Location(world, vector.getX(), vector.getY(), vector.getZ(), (float) file.getDouble(path + "yaw"), (float) file.getDouble(path + "pitch"));
	}
	
	@SuppressWarnings("deprecation")
	public static void doReplaceBlocks(final int filterID, final int filterData, final int replacementID, final int replacementData, final Region region, final World world)
	{
		Bukkit.getScheduler().runTask(Conquest.giveConquest(), new Runnable()
		{
			@Override
			public void run()
			{
				final BukkitWorld worldBukkit = new BukkitWorld(world);
				final Set<BaseBlock> filter = new HashSet<BaseBlock>();
				filter.add(new BaseBlock(filterID, filterData));
				try
				{
					new EditSession(worldBukkit, -1).replaceBlocks(new CuboidRegion(worldBukkit, region.giveMin(), region.giveMax()), filter, new BaseBlock(replacementID, replacementData));
				}
				catch (MaxChangedBlocksException e)
				{
					e.printStackTrace();
				}
			}});
	}
	
	protected static String givePath(final World world)
	{
		return "conquest." + world.getName() + ".";
	}
	
	protected static String givePathVector(final VectorType type, final World world)
	{
		String path = givePath(world);
		switch(type)
		{
			case LOBBY:
				path += "lobby.";
				break;
			case SPAWN_BLUE:
				path += "spawn.blue.";
				break;
			case SPAWN_RED:
				path += "spawn.red.";
				break;
		}
		return path;
	}
	
}
