package javax.jao.script;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Array implements Iterator<String>, Iterable<String>, Cloneable, Serializable
{
	
	private static final long serialVersionUID = 6885145633276994203L;
	
	protected final String[] array;
	
	public Array(final int length)
	{
		this.array = new String[length];
		this.cursor = -1;
	}
	
	private transient int cursor;
	
	public Array set(final int index, final String element)
	{
		this.array[index] = element;
		return this;
	}
	
	public String get(final int index)
	{
		return this.array[index];
	}
	
	public final int length()
	{
		return this.array.length;
	}

	@Override
	public final boolean hasNext()
	{
		return this.cursor + 1 < this.length();
	}

	@Override
	public final String next()
	{
		if(!this.hasNext())
			throw new NoSuchElementException();
		return this.get(++this.cursor);
	}

	@Override
	public final void remove()
	{
		this.set(this.cursor, null);
	}

	@Override
	public final Iterator<String> iterator()
	{
		return this;
	}
	
	@Override
	public Array clone()
	{
		final Array array = new Array(this.length());
		for(int i = 0; i < array.length(); i++)
			array.set(i, this.get(i));
		return array;
	}
	
	@Override
	public String toString()
	{
		final Array array = this.clone();
		String str = this.getClass().getName() + "[";
		while(true)
		{
			if(array.hasNext())
			{
				str += '\'' + array.next() + '\'';
				if(array.hasNext())
					str += ", ";
			}
			else
				return str + "]";
		}
	}
	
}
