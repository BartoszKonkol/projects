package net.polishgames.rhenowar.util;

import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class Region extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private net.polishgames.rhenowar.util.serialization.Vector min, max;
	
	public Region(final BlockVector min, final BlockVector max)
	{
		int minX = Util.nonNull(min).getBlockX();
		int minY = min.getBlockY();
		int minZ = min.getBlockZ();
		int maxX = minX;
		int maxY = minY;
		int maxZ = minZ;
		
		final int x = Util.nonNull(max).getBlockX();
		final int y = max.getBlockY();
		final int z = max.getBlockZ();

		if(x < minX)
			minX = x;
		else if(x > maxX)
			maxX = x;
		if(y < minY)
			minY = y;
		else if(y > maxY)
			maxY = y;
		if(z < minZ)
			minZ = z;
		else if(z > maxZ)
			maxZ = z;
		
		this.min = new net.polishgames.rhenowar.util.serialization.Vector(minX, minY, minZ);
		this.max = new net.polishgames.rhenowar.util.serialization.Vector(maxX, maxY, maxZ);
	}
	
	public final BlockVector giveMin()
	{
		return this.min.toVectorBlockWE();
	}
	
	public final BlockVector giveMax()
	{
		return this.max.toVectorBlockWE();
	}
	
	public boolean isContains(final Vector vector)
	{
		final BlockVector min = this.giveMin(), max = this.giveMax();
		final double x = Util.nonNull(vector).getX(), y = vector.getY(), z = vector.getZ();
		return x >= min.getBlockX() && x < max.getBlockX() + 1
			&& y >= min.getBlockY() && y < max.getBlockY() + 1
			&& z >= min.getBlockZ() && z < max.getBlockZ() + 1;
	}
	
	public boolean isContains(final org.bukkit.util.Vector vector)
	{
		return this.isContains(new Vector(Util.nonNull(vector).getX(), vector.getY(), vector.getZ()));
	}
	
	public final boolean isContains(final Location location)
	{
		return this.isContains(Util.nonNull(location).toVector());
	}
	
	public Region doSlide(final DimensionPosition dimension, final double min, final double max)
	{
		switch(Util.nonNull(dimension))
		{
			case X:
				return this.add(new Vector(min, 0.0D, 0.0D), new Vector(max, 0.0D, 0.0D));
			case Y:
				return this.add(new Vector(0.0D, min, 0.0D), new Vector(0.0D, max, 0.0D));
			case Z:
				return this.add(new Vector(0.0D, 0.0D, min), new Vector(0.0D, 0.0D, max));
			default:
				return this;
		}
	}
	
	public final Region addMin(final Vector vector)
	{
		this.min = new net.polishgames.rhenowar.util.serialization.Vector(this.giveMin().add(Util.nonNull(vector)));
		return this;
	}
	
	public final Region addMax(final Vector vector)
	{
		this.max = new net.polishgames.rhenowar.util.serialization.Vector(this.giveMax().add(Util.nonNull(vector)));
		return this;
	}
	
	public final Region add(final Vector min, final Vector max)
	{
		return this.addMin(min).addMax(max);
	}
	
	public final Region add(final Vector vector)
	{
		return this.add(vector, vector);
	}
	
	public DualRegion giveCutHalf(final DimensionPosition dimension)
	{
		final BlockVector min = this.giveMin(), max = this.giveMax();
		Vector vector = null, centerMin = null, centerMax = null;
		switch(Util.nonNull(dimension))
		{
			case X:
				vector = new Vector(max.getX(), min.getY(), min.getZ());
				break;
			case Y:
				vector = new Vector(min.getX(), max.getY(), min.getZ());
				break;
			case Z:
				vector = new Vector(min.getX(), min.getY(), max.getZ());
				break;
		}
		final double distance = min.distance(vector) / 2;
		switch(dimension)
		{
			case X:
				centerMin = new Vector(distance + min.getX() - 1, max.getY(), max.getZ());
				centerMax = new Vector(max.getX() - distance, min.getY(), min.getZ());
				break;
			case Y:
				centerMin = new Vector(max.getX(), distance + min.getY() - 1, max.getZ());
				centerMax = new Vector(min.getX(), max.getY() - distance, min.getZ());
				break;
			case Z:
				centerMin = new Vector(max.getX(), max.getY(), distance + min.getZ() - 1);
				centerMax = new Vector(min.getX(), min.getY(), max.getZ() - distance);
				break;
		}
		return new DualRegion(new Region(min, centerMin.toBlockVector()), new Region(centerMax.toBlockVector(), max));
	}
	
	public double giveSize(final DimensionPosition dimension)
	{
		final BlockVector min = this.giveMin(), max = this.giveMax();
		Vector vector = null;
		switch(Util.nonNull(dimension))
		{
			case X:
				vector = new Vector(max.getX(), min.getY(), min.getZ());
				break;
			case Y:
				vector = new Vector(min.getX(), max.getY(), min.getZ());
				break;
			case Z:
				vector = new Vector(min.getX(), min.getY(), max.getZ());
				break;
		}
		return min.distance(vector) + 1.0F;
	}
	
	public BlockVector giveCenter()
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final BlockVector min = this.giveMin(), max = this.giveMax();
			return new BlockVector(min.getX() + util.giveDistance(min.getX(), max.getX()) / 2.0D, min.getY() + util.giveDistance(min.getY(), max.getY()) / 2.0D, min.getZ() + util.giveDistance(min.getZ(), max.getZ()) / 2.0D);
		}
		else
			return null;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("min", this.min);
		map.put("max", this.max);
		return map;
	}
	
	@Override
	public Region clone()
	{
		final BlockVector min = this.giveMin(), max = this.giveMax();
		return new Region(new BlockVector(min.getX(), min.getY(), min.getZ()), new BlockVector(max.getX(), max.getY(), max.getZ()));
	}
	
	public static final Region giveRegion(final Player player, final boolean messageError)
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final String weName = "WorldEdit";
			final Plugin plugin = util.givePlugin(weName);
			if(plugin != null && plugin instanceof WorldEditPlugin)
			{
				final Selection selection = ((WorldEditPlugin) plugin).getSelection(Util.nonNull(player));
				if(selection != null)
					return new Region(selection.getNativeMinimumPoint().toBlockVector(), selection.getNativeMaximumPoint().toBlockVector());
				else if(messageError)
					util.doUtilSendMessage(player, String.format(util.giveMessage(util.giveUtilConfigPrefix() + "message.RegionError", player), weName));
			}
		}
		return null;
	}
	
	public static final Region giveRegion(final Player player)
	{
		return Region.giveRegion(player, true);
	}
	
}
