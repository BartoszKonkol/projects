package net.polishgames.rhenowar.util.serialization;

import java.util.Map;
import org.bukkit.util.BlockVector;
import net.polishgames.rhenowar.util.Util;

public class Vector extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final double x, y, z;
	
	public Vector(final double x, final double y, final double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(final org.bukkit.util.Vector vector)
	{
		this(Util.nonNull(vector).getX(), vector.getY(), vector.getZ());
	}
	
	public Vector(final com.sk89q.worldedit.Vector vector)
	{
		this(Util.nonNull(vector).getX(), vector.getY(), vector.getZ());
	}
	
	public final double giveX()
	{
		return this.x;
	}
	
	public final double giveY()
	{
		return this.y;
	}
	
	public final double giveZ()
	{
		return this.z;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("x", this.giveX());
		map.put("y", this.giveY());
		map.put("z", this.giveZ());
		return map;
	}
	
	public final org.bukkit.util.Vector toVectorBukkit()
	{
		return new org.bukkit.util.Vector(this.giveX(), this.giveY(), this.giveZ());
	}
	
	public final com.sk89q.worldedit.Vector toVectorWE()
	{
		return new com.sk89q.worldedit.Vector(this.giveX(), this.giveY(), this.giveZ());
	}
	
	public final BlockVector toVectorBlockBukkit()
	{
		return new BlockVector(this.giveX(), this.giveY(), this.giveZ());
	}
	
	public final com.sk89q.worldedit.BlockVector toVectorBlockWE()
	{
		return new com.sk89q.worldedit.BlockVector(this.giveX(), this.giveY(), this.giveZ());
	}
	
	@Override
	public Vector clone()
	{
		return new Vector(this.giveX(), this.giveY(), this.giveZ());
	}
	
}
