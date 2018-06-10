package net.polishgames.rhenowar.util.craft.v315;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_11_R1.NBTReadLimiter;
import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagFloat;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagLong;
import net.minecraft.server.v1_11_R1.NBTTagShort;
import net.minecraft.server.v1_11_R1.NBTTagString;
import net.polishgames.rhenowar.util.FieldData;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.craft.Tool;

public final class NBTTagTool extends Tool
{
	
	private static volatile NBTTagTool nbtTagTool;
	
	private NBTTagTool() {}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("class", this.giveTagClass());
		return map;
	}
	
	public final Class<NBTTagCompound> giveTagClass()
	{
		return NBTTagCompound.class;
	}
	
	public final Class<NBTBase> giveTagBasicClass()
	{
		return NBTBase.class;
	}
	
	public final NBTTagCompound giveTag()
	{
		return new NBTTagCompound();
	}
	
	public final NBTTagCompound giveTag(final org.bukkit.inventory.ItemStack item)
	{
		if(Util.nonNull(item) instanceof CraftItemStack)
		{
			Object result = null;
			try
			{
				result = new FieldData(item, "handle").giveField().get(item);
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
			if(result != null && result instanceof ItemStack)
				return this.giveTag((ItemStack) result);
			else
				return null;
		}
		else
			return this.giveTag(CraftItemStack.asCraftCopy(item));
	}
	
	public final NBTTagCompound giveTag(final ItemStack item)
	{
		if(!Util.nonNull(item).hasTag())
			item.setTag(this.giveTag());
		return item.getTag();
	}
	
	public final NBTTagCompound giveTagRHN(final NBTTagCompound tag)
	{
		final String rhn = "rhn";
		if(!Util.nonNull(tag).hasKey(rhn))
			tag.set(rhn, this.giveTag());
		final Object result = tag.get(rhn);
		if(result instanceof NBTTagCompound)
			return (NBTTagCompound) result;
		else
			return null;
	}
	
	public final ItemStack giveItemNMS(final org.bukkit.inventory.ItemStack item)
	{
		return CraftItemStack.asNMSCopy(item);
	}
	
	public final ItemStack giveItemNMS(final NBTTagCompound tag)
	{
		return new ItemStack(tag);
	}
	
	public final CraftItemStack giveItemCraft(final org.bukkit.inventory.ItemStack item)
	{
		if(item instanceof CraftItemStack)
			return (CraftItemStack) item;
		else
			return this.giveItemCraft(this.giveItemNMS(item));
	}
	
	public final CraftItemStack giveItemCraft(final ItemStack item)
	{
		return CraftItemStack.asCraftMirror(item);
	}
	
	public final CraftItemStack giveItemCraft(final NBTTagCompound tag)
	{
		return this.giveItemCraft(this.giveItemNMS(tag));
	}
	
	public final NBTTagCompound doAscribeItemToTag(final ItemStack item, final NBTTagCompound tag)
	{
		return item.save(tag);
	}
	
	public final NBTTagCompound doAscribeItemToTag(final org.bukkit.inventory.ItemStack item, final NBTTagCompound tag)
	{
		return this.doAscribeItemToTag(this.giveItemNMS(item), tag);
	}
	
	public final void doWrite(final NBTTagCompound tag, final DataOutput data)
	{
		try
		{
			NBTCompressedStreamTools.a(tag, data);
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public final NBTTagCompound doRead(final DataInput data)
	{
		try
		{
			return NBTCompressedStreamTools.a(data, NBTReadLimiter.a);
		}
		catch(final IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final void set(final NBTTagCompound tag, final String key, final List<?> list)
	{
		final NBTTagList nbt = new NBTTagList();
		for(final Object value : list)
			if(value instanceof NBTBase)
				nbt.add((NBTBase) value);
			else if(value instanceof Boolean)
				nbt.add(new NBTTagByte((byte) ((boolean) value ? 1 : 0)));
			else if(value instanceof Byte)
				nbt.add(new NBTTagByte((byte) value));
			else if(value instanceof Short)
				nbt.add(new NBTTagShort((short) value));
			else if(value instanceof Integer)
				nbt.add(new NBTTagInt((int) value));
			else if(value instanceof Long)
				nbt.add(new NBTTagLong((long) value));
			else if(value instanceof Float)
				nbt.add(new NBTTagFloat((float) value));
			else if(value instanceof Double)
				nbt.add(new NBTTagDouble((double) value));
			else
				nbt.add(new NBTTagString(value.toString()));
		this.set(tag, key, nbt);
	}
	
	public final void set(final NBTTagCompound tag, final String key, final NBTBase value)
	{
		Util.nonNull(tag).set(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final NBTTagCompound value)
	{
		this.set(tag, key, (NBTBase) value);
	}
	
	public final void set(final NBTTagCompound tag, final String key, final String value)
	{
		Util.nonNull(tag).setString(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final Byte value)
	{
		Util.nonNull(tag).setByte(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final Short value)
	{
		Util.nonNull(tag).setShort(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final Integer value)
	{
		Util.nonNull(tag).setInt(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final Long value)
	{
		Util.nonNull(tag).setLong(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final Float value)
	{
		Util.nonNull(tag).setFloat(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final Double value)
	{
		Util.nonNull(tag).setDouble(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final void set(final NBTTagCompound tag, final String key, final Boolean value)
	{
		Util.nonNull(tag).setBoolean(Util.nonEmpty(key), Util.nonNull(value));
	}
	
	public final String get(final NBTTagCompound tag, final String key, final Integer index)
	{
		final NBTBase nbt = this.get(tag, key);
		if(nbt instanceof NBTTagList)
			return ((NBTTagList) nbt).h(index).toString();
		else
			return null;
	}
	
	public final NBTBase get(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).get(Util.nonEmpty(key));
	}
	
	public final String getString(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getString(Util.nonEmpty(key));
	}
	
	public final byte getByte(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getByte(Util.nonEmpty(key));
	}
	
	public final short getShort(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getShort(Util.nonEmpty(key));
	}
	
	public final int getInteger(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getInt(Util.nonEmpty(key));
	}
	
	public final long getLong(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getLong(Util.nonEmpty(key));
	}
	
	public final float getFloat(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getFloat(Util.nonEmpty(key));
	}
	
	public final double getDouble(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getDouble(Util.nonEmpty(key));
	}
	
	public final boolean getBoolean(final NBTTagCompound tag, final String key)
	{
		return Util.nonNull(tag).getBoolean(Util.nonEmpty(key));
	}
	
	public static final NBTTagTool giveNBTTagTool()
	{
		if(NBTTagTool.nbtTagTool == null)
			NBTTagTool.nbtTagTool = new NBTTagTool();
		return NBTTagTool.nbtTagTool;
	}
	
}
