package net.polishgames.rhenowar.util;

import java.util.Iterator;
import java.util.Map;

public final class ArrayIterator<E> extends RhenowarObject implements Iterator<E>
{
	
	private final E[] array;
	private final int size;
	
	public ArrayIterator(final E[] array)
	{
		this.array = Util.nonNull(array);
		this.size = this.giveArray().length;
	}
	
	protected int index = 0;
	
	public final E[] giveArray()
	{
		return this.array;
	}
	
	public final int giveSize()
	{
		return this.size;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("array", this.giveArray());
		map.put("size", this.giveSize());
		map.put("index", this.index);
		return map;
	}

	@Override
	public final boolean hasNext()
	{
		return this.size > this.index;
	}

	@Override
	public final E next()
	{
		return this.giveArray()[this.index++];
	}
	
}
