package net.polishgames.rhenowar.util.world.area;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockWorldVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.World;
import org.bukkit.block.Block;
import net.polishgames.rhenowar.util.Region;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.minigame.Minigame;
import net.polishgames.rhenowar.util.minigame.RhenowarPluginMinigame;
import net.polishgames.rhenowar.util.serialization.MaterialData;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

@SuppressWarnings("deprecation")
public class Area extends RhenowarSerializable implements Iterable<Short>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.5.1");
	
	private final Region region;
	private final Map<Short, Chunk> chunks;
	private final net.polishgames.rhenowar.util.serialization.Vector translation;
	private final transient World world;
	
	public Area(final World world, final Region region, net.polishgames.rhenowar.util.serialization.Vector translation, final Map<Short, Chunk> chunks)
	{
		this.world = world;
		this.region = Util.nonNull(region);
		this.translation = Util.nonNull(translation);
		this.chunks = Util.nonNull(chunks);
	}
	
	public Area(final World world, final Region region)
	{
		this.world = Util.nonNull(world);
		Util.nonNull(region);
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final BlockVector min = region.giveMin(), max = region.giveMax(), center = region.giveCenter();
			final Region area = new Region(min.subtract(center).toBlockVector(), max.subtract(center).toBlockVector());
			double yMin = area.giveMin().getY(), yMax = area.giveMax().getY();
			if(yMin < 0)
			{
				final double centerY = util.giveDistance(yMin, yMax) / 2;
				area.add(new Vector(0, centerY + 0x3F, 0));
				yMin = area.giveMin().getY();
				yMax = area.giveMax().getY();
				if(yMax > 0x6F)
				{
					final double distance = util.giveDistance(0x6F, yMax);
					area.add(new Vector(0, -(yMin - distance > 0x1F ? distance : 0x20), 0));
					yMax = area.giveMax().getY();
					if(yMax > 0xFF)
						area.add(new Vector(0, -util.giveDistance(0xFF, yMax), 0));
					yMin = area.giveMin().getY();
				}
			}
			final Vector translation = center.multiply(-1.0D).add(0.0D, center.getY() + yMin - min.getY() , 0.0D);
			this.region = area;
			this.translation = new net.polishgames.rhenowar.util.serialization.Vector(translation);
			this.chunks = new HashMap<Short, Chunk>();
			for(final BlockVector vector : new CuboidRegion(region.giveMin(), region.giveMax()))
			{
				final Block block = BukkitUtil.toBlock(new BlockWorldVector(BukkitUtil.getLocalWorld(this.giveWorld()), vector));
				if(block != null && !block.isEmpty())
				{
					final Vector position = vector.add(translation);
					final short index = Area.giveChunkIndex(position);
					if(!this.giveChunks().containsKey(index))
						this.giveChunks().put(index, new Chunk());
					this.giveChunks().get(index).addBlock(position.getBlockX() - Area.giveChunkPosX(index) * (2 << 3), position.getBlockY(), position.getBlockZ() - Area.giveChunkPosZ(index) * (2 << 3), new MaterialData(block.getType(), block.getData()));
				}
			}
		}
		else
		{
			Util.doThrowNPE();
			this.region = null;
			this.translation = null;
			this.chunks = null;
		}
	}
	
	public final Region giveRegion()
	{
		return this.region;
	}
	
	public final Map<Short, Chunk> giveChunks()
	{
		return this.chunks;
	}
	
	public final net.polishgames.rhenowar.util.serialization.Vector giveTranslation()
	{
		return this.translation;
	}
	
	public final World giveWorld()
	{
		return this.world;
	}
	
	public final boolean hasWorld()
	{
		return this.world != null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("region", this.giveRegion());
		map.put("chunks", this.giveChunks());
		map.put("translation", this.giveTranslation());
		map.put("world", this.giveWorld());
		return map;
	}
	
	@Override
	public Area clone()
	{
		final Map<Short, Chunk> chunks = new HashMap<Short, Chunk>();
		for(final short index : this)
			chunks.put(index, this.giveChunks().get(index).clone());
		return new Area(this.giveWorld(), this.giveRegion().clone(), this.giveTranslation().clone(), chunks);
	}
	
	@Override
	public final Iterator<Short> iterator()
	{
		return this.giveChunks().keySet().iterator();
	}
	
	public final Minigame toMinigame(final RhenowarPluginMinigame plugin)
	{
		return new Minigame(Util.nonNull(plugin), this);
	}
	
	public static final byte giveChunkFloor(final int pos)
	{
		return (byte) Math.floor(pos / (double) (2 << 3));
	}
	
	public static final short giveChunkIndex(final byte x, final byte z)
	{
		return (short) ((x & (2 << Byte.SIZE - 1) - 1) + (z << Byte.SIZE));
	}
	
	public static final short giveChunkIndex(final int x, final int z)
	{
		return Area.giveChunkIndex(Area.giveChunkFloor(x), Area.giveChunkFloor(z));
	}
	
	public static final short giveChunkIndex(final Vector vector)
	{
		return Area.giveChunkIndex(vector.getBlockX(), vector.getBlockZ());
	}
	
	public static final byte giveChunkPosX(final short index)
	{
		return (byte) index;
	}
	
	public static final byte giveChunkPosZ(final short index)
	{
		return (byte) (index >> Byte.SIZE);
	}
	
}
