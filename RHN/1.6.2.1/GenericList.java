package net.polishgames.rhenowar.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class GenericList<E> extends GenericCollection<E> implements List<E>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	private final List<E> list;
	
	public GenericList(final List<E> list, final Class<E> type)
	{
		super(list, type);
		this.list = list;
	}
	
	public GenericList(final Class<E> type)
	{
		this(new ArrayList<E>(), type);
	}
	
	public final List<E> giveList()
	{
		return this.list;
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends E> c)
	{
		return this.giveList().addAll(c);
	}

	@Override
	public E get(final int index)
	{
		return this.giveList().get(index);
	}

	@Override
	public E set(final int index, final E element)
	{
		return this.giveList().set(index, element);
	}

	@Override
	public void add(final int index, final E element)
	{
		this.giveList().add(index, element);
	}

	@Override
	public E remove(final int index)
	{
		return this.giveList().remove(index);
	}

	@Override
	public int indexOf(final Object o)
	{
		return this.giveList().indexOf(o);
	}

	@Override
	public int lastIndexOf(final Object o)
	{
		return this.giveList().lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator()
	{
		return this.giveList().listIterator();
	}

	@Override
	public ListIterator<E> listIterator(final int index)
	{
		return this.giveList().listIterator(index);
	}

	@Override
	public List<E> subList(final int fromIndex, final int toIndex)
	{
		return this.giveList().subList(fromIndex, toIndex);
	}
	
	public static final <E> GenericList<E> valueOf(final List<E> list, final Class<E> type)
	{
		return new GenericList<E>(list, type);
	}
	
}
