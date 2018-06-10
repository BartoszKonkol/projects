package net.polishgames.rhenowar.util.world.area;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.MaterialData;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class Chunk extends RhenowarSerializable implements Iterable<Short>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.5.1");

	protected static final Map<Short, MaterialData> materials = new HashMap<Short, MaterialData>();
	
	private final Map<Short, MaterialData> blocks;
	
	public Chunk(final Map<Short, MaterialData> blocks)
	{
		this.blocks = Util.nonNull(blocks);
	}

	public Chunk()
	{
		this(new HashMap<Short, MaterialData>());
	}
	
	public final Map<Short, MaterialData> giveBlocks()
	{
		return Collections.unmodifiableMap(this.blocks);
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("blocks", this.blocks);
		return map;
	}
	
	public final MaterialData giveBlock(final short index)
	{
		return this.giveBlocks().get(index);
	}
	
	public final MaterialData giveBlock(final int x, final int y, final int z)
	{
		try
		{
			return this.giveBlock(Chunk.giveBlockIndex(x, y, z));
		}
		catch(final IllegalArgumentException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final Chunk addBlock(final short index, final MaterialData material)
	{
		@SuppressWarnings("deprecation")
		final short id = (short) (Util.nonNull(material).giveMaterial().getId() * (2 << 3) + material.giveData());
		if(!Chunk.materials.containsKey(id))
			Chunk.materials.put(id, material);
		this.blocks.put(index, Chunk.materials.get(id));
		return this;
	}
	
	public final Chunk addBlock(final int x, final int y, final int z, final MaterialData material)
	{
		try
		{
			return this.addBlock(Chunk.giveBlockIndex(x, y, z), material);
		}
		catch(final IllegalArgumentException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final Chunk delBlock(final short index)
	{
		this.blocks.remove(index);
		return this;
	}
	
	public final Chunk delBlock(final int x, final int y, final int z)
	{
		try
		{
			return this.delBlock(Chunk.giveBlockIndex(x, y, z));
		}
		catch(final IllegalArgumentException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Chunk clone()
	{
		final Map<Short, MaterialData> blocks = new HashMap<Short, MaterialData>();
		for(final short index : this)
			blocks.put(index, this.giveBlocks().get(index).clone());
		return new Chunk(blocks);
	}
	
	@Override
	public final Iterator<Short> iterator()
	{
		return this.giveBlocks().keySet().iterator();
	}
	
	public static final short giveBlockIndex(final int x, final int y, final int z)
	{
		String error = "";
		if(x < 0)
			error += "(x=" + x +" < 0) & ";
		else if(x > 0xF)
			error += "(x=" + x +" > 15) & ";
		if(y < 0)
			error += "(y=" + y +" < 0) & ";
		else if(y > 0xFF)
			error += "(y=" + y +" > 255) & ";
		if(z < 0)
			error += "(z=" + z +" < 0) & ";
		else if(z > 0xF)
			error += "(z=" + z +" > 15) & ";
		if(!error.isEmpty())
		{
			Util.doThrowIAE(error.substring(0, error.length() - 3));
			return Short.MIN_VALUE;
		}
		else
			return (short) ((x << 12) + (z << 8) + y + Short.MIN_VALUE);
	}
	
	public static final int giveBlockPosX(final short index)
	{
		return (index - Short.MIN_VALUE) >> 12;
	}
	
	public static final int giveBlockPosY(final short index)
	{
		return (index - Short.MIN_VALUE) - Chunk.giveBlockPosX(index) * (2 << 11) - Chunk.giveBlockPosZ(index) * (2 << 7);
	}
	
	public static final int giveBlockPosZ(final short index)
	{
		return ((index - Short.MIN_VALUE) >> 8) - Chunk.giveBlockPosX(index) * (2 << 3);
	}
	
}
