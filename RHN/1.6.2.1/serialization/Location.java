package net.polishgames.rhenowar.util.serialization;

import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;
import net.polishgames.rhenowar.util.Util;

public class Location extends Vector
{

	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final String world;
	private final float yaw, pitch;
	
	public Location(final String world, final double x, final double y, final double z, final float yaw, final float pitch)
	{
		super(x, y, z);
		this.world = Util.nonEmpty(world);
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Location(final World world, final double x, final double y, final double z, final float yaw, final float pitch)
	{
		this(Util.nonNull(world).getName(), x, y, z, yaw, pitch);
	}
	
	public Location(final String world, final Vector vector, final float yaw, final float pitch)
	{
		this(world, Util.nonNull(vector).giveX(), vector.giveY(), vector.giveZ(), yaw, pitch);
	}
	
	public Location(final World world, final Vector vector, final float yaw, final float pitch)
	{
		this(world, Util.nonNull(vector).giveX(), vector.giveY(), vector.giveZ(), yaw, pitch);
	}
	
	public Location(final String world, final org.bukkit.util.Vector vector, final float yaw, final float pitch)
	{
		super(vector);
		this.world = Util.nonEmpty(world);
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Location(final World world, final org.bukkit.util.Vector vector, final float yaw, final float pitch)
	{
		this(Util.nonNull(world).getName(), vector, yaw, pitch);
	}
	
	public Location(final org.bukkit.Location location)
	{
		this(Util.nonNull(location).getWorld(), location.toVector(), location.getYaw(), location.getPitch());
	}
	
	public Location(final String world, final com.sk89q.worldedit.Vector vector, final float yaw, final float pitch)
	{
		super(vector);
		this.world = Util.nonEmpty(world);
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Location(final World world, final com.sk89q.worldedit.Vector vector, final float yaw, final float pitch)
	{
		this(Util.nonNull(world).getName(), vector, yaw, pitch);
	}
	
	public final String giveWorldName()
	{
		return this.world;
	}
	
	public final World giveWorld()
	{
		return Util.hasUtil() ? Util.giveUtil().giveWorld(this.giveWorldName()) : Bukkit.getWorld(this.giveWorldName());
	}
	
	public final float giveYaw()
	{
		return this.yaw;
	}
	
	public final float givePitch()
	{
		return this.pitch;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("world", this.giveWorldName());
		map.put("yaw", this.giveYaw());
		map.put("pitch", this.givePitch());
		return super.giveProperties(map);
	}
	
	public final org.bukkit.Location toLocationBukkit()
	{
		return new org.bukkit.Location(this.giveWorld(), this.giveX(), this.giveY(), this.giveZ(), this.giveYaw(), this.givePitch());
	}
	
	public final Vector toVector()
	{
		return super.clone();
	}
	
	@Override
	public Location clone()
	{
		return new Location(this.giveWorldName(), this.giveX(), this.giveY(), this.giveZ(), this.giveYaw(), this.givePitch());
	}
	
}
