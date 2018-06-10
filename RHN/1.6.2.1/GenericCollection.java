package net.polishgames.rhenowar.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class GenericCollection<E> extends AbstractCollection<E> implements Generic<E>, Comparable<Collection<E>>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final Collection<E> collection;
	private final Class<E> type;
	
	public GenericCollection(final Collection<E> collection, final Class<E> type)
	{
		this.collection = collection;
		this.type = type;
	}
	
	public final Collection<E> giveCollection()
	{
		return this.collection;
	}
	
	@Override
	public final Class<E> giveGenericType()
	{
		return this.type;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("type", this.giveGenericType());
		return map;
	}

	@Override
	public Iterator<E> iterator()
	{
		return this.giveCollection().iterator();
	}

	@Override
	public int size()
	{
		return this.giveCollection().size();
	}
	
	@Override
	public boolean add(final E e)
	{
		return this.giveCollection().add(e);
	}
	
	@Override
	public int compareTo(final Collection<E> collection)
	{
		return Integer.compare(this.hashCode(), collection.hashCode());
	}

	@Override
	public int hashCode()
	{
		return this.giveCollection().hashCode();
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		return this.giveCollection().equals(obj);
	}
	
	@Override
	public String toString()
	{
		return this.giveCollection().toString();
	}
	
	public static final <E> GenericCollection<E> valueOf(final Collection<E> collection, final Class<E> type)
	{
		return new GenericCollection<E>(collection, type);
	}
	
}
