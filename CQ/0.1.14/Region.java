package net.polishgames.rhenowar.conquest;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class Region
{
	
	protected BlockVector min, max;
	
	public Region(final BlockVector min, final BlockVector max)
	{
		int minX = min.getBlockX();
		int minY = min.getBlockY();
		int minZ = min.getBlockZ();
		int maxX = minX;
		int maxY = minY;
		int maxZ = minZ;
		
		final int x = max.getBlockX();
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
		
		this.min = new BlockVector(minX, minY, minZ);
		this.max = new BlockVector(maxX, maxY, maxZ);
	}
	
	public final BlockVector giveMin()
	{
		return this.min;
	}
	
	public final BlockVector giveMax()
	{
		return this.max;
	}
	
	public boolean isContains(final Vector vector)
	{
		double x = vector.getX(), y = vector.getY(), z = vector.getZ();
		return x >= this.giveMin().getBlockX() && x < this.giveMax().getBlockX() + 1
			&& y >= this.giveMin().getBlockY() && y < this.giveMax().getBlockY() + 1
			&& z >= this.giveMin().getBlockZ() && z < this.giveMax().getBlockZ() + 1;
	}
	
	public final boolean isContains(final Location location)
	{
		return this.isContains(new Vector(location.getX(), location.getY(), location.getZ()));
	}
	
	public Region doSlide(final DimensionPosition dimension, final double min, final double max)
	{
		switch(dimension)
		{
			case X:
				return this.doAdd(new Vector(min, 0.0D, 0.0D), new Vector(max, 0.0D, 0.0D));
			case Y:
				return this.doAdd(new Vector(0.0D, min, 0.0D), new Vector(0.0D, max, 0.0D));
			case Z:
				return this.doAdd(new Vector(0.0D, 0.0D, min), new Vector(0.0D, 0.0D, max));
			default:
				return this;
		}
	}
	
	public Region doAddMin(final Vector vector)
	{
		this.min = this.giveMin().add(vector).toBlockVector();
		return this;
	}
	
	public Region doAddMax(final Vector vector)
	{
		this.max = this.giveMax().add(vector).toBlockVector();
		return this;
	}
	
	public final Region doAdd(final Vector min, final Vector max)
	{
		return this.doAddMin(min).doAddMax(max);
	}
	
	public Region doClone()
	{
		return new Region(this.giveMin(), this.giveMax());
	}
	
	public DualRegion giveCutHalf(final DimensionPosition dimension)
	{
		Vector vector = null, centerMin = null, centerMax = null;
		switch(dimension)
		{
			case X:
				vector = new Vector(this.giveMax().getX(), this.giveMin().getY(), this.giveMin().getZ());
				break;
			case Y:
				vector = new Vector(this.giveMin().getX(), this.giveMax().getY(), this.giveMin().getZ());
				break;
			case Z:
				vector = new Vector(this.giveMin().getX(), this.giveMin().getY(), this.giveMax().getZ());
				break;
		}
		double distance = this.giveMin().distance(vector);
		if((distance + 1.0F) % (2 * Conquest.giveConquest().giveFileConfig().getInt("conquest.DivisorDistance")) != 0)
			return null;
		else
			distance /= 2;
		switch(dimension)
		{
			case X:
				centerMin = new Vector(distance + this.giveMin().getX() - 1, this.giveMax().getY(), this.giveMax().getZ());
				centerMax = new Vector(this.giveMax().getX() - distance, this.giveMin().getY(), this.giveMin().getZ());
				break;
			case Y:
				centerMin = new Vector(this.giveMax().getX(), distance + this.giveMin().getY() - 1, this.giveMax().getZ());
				centerMax = new Vector(this.giveMin().getX(), this.giveMax().getY() - distance, this.giveMin().getZ());
				break;
			case Z:
				centerMin = new Vector(this.giveMax().getX(), this.giveMax().getY(), distance + this.giveMin().getZ() - 1);
				centerMax = new Vector(this.giveMin().getX(), this.giveMin().getY(), this.giveMax().getZ() - distance);
				break;
		}
		return new DualRegion(new Region(this.giveMin(), centerMin.toBlockVector()), new Region(centerMax.toBlockVector(), this.giveMax()));
	}
	
	public double giveSize(final DimensionPosition dimension)
	{
		Vector vector = null;
		switch(dimension)
		{
			case X:
				vector = new Vector(this.giveMax().getX(), this.giveMin().getY(), this.giveMin().getZ());
				break;
			case Y:
				vector = new Vector(this.giveMin().getX(), this.giveMax().getY(), this.giveMin().getZ());
				break;
			case Z:
				vector = new Vector(this.giveMin().getX(), this.giveMin().getY(), this.giveMax().getZ());
				break;
		}
		return this.giveMin().distance(vector) + 1.0F;
	}
	
	public static Region giveRegion(final Player player)
	{
		final Selection selection = Conquest.giveConquest().giveWorldEdit().getSelection(player);
		if(selection == null)
			Conquest.doSendSevereToPlayer("Okreœl region przy u¿yciu pluginu WorldEdit.", player);
		else
			return new Region(selection.getNativeMinimumPoint().toBlockVector(), selection.getNativeMaximumPoint().toBlockVector());
		return null;
	}
	
}
