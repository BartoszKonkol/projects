package net.polishgames.rhenowar.util;

import java.util.Map;

public final class Password extends RhenowarObject implements CharSequence, Appendable, Cloneable, AutoCloseable
{
	
	private final transient StringBuilder bm, bf;
	
	public Password(final CharSequence pass)
	{
		this.bm = new StringBuilder(Util.nonNull(pass));
		this.bf = new StringBuilder(this.length() + (2 << 3));
	}
	
	public Password(final Number pass)
	{
		this(Password.toString(Util.nonNull(pass)));
	}
	
	@Override
	public final Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		return map;
	}
	
	public final boolean isEmpty()
	{
		return this.length() == 0;
	}
	
	@Override
	public final int length()
	{
		return this.bm.length();
	}
	
	@Override
	public final char charAt(int index)
	{
		return this.bf.charAt(index);
	}
	
	@Override
	public final CharSequence subSequence(int start, int end)
	{
		return this.bf.subSequence(start, end);
	}

	@Override
	public final StringBuilder append(final CharSequence csq)
	{
		this.bm.append(Util.nonNull(csq));
		return this.bf;
	}

	@Override
	public final StringBuilder append(final CharSequence csq, final int start, final int end)
	{
		this.bm.append(Util.nonNull(csq), start, end);
		return this.bf;
	}

	@Override
	public final StringBuilder append(final char c)
	{
		this.bm.append(Util.nonNull(c));
		return this.bf;
	}
	
	public final StringBuilder append(final Number n)
	{
		return this.append(Password.toString(Util.nonNull(n)));
	}

	@Override
	public final void close()
	{
		this.bm.setLength(0);
	}
	
	@Override
	public final Password clone()
	{
		return new Password(this.toString());
	}
	
	@Override
	public final int compareTo(final Rhenowar rhn)
	{
		if(Util.nonNull(rhn) instanceof Password)
			return this.toString().compareTo(rhn.toString());
		else
			return -1;
	}
	
	@Override
	public final int hashCode()
	{
		return this.bm.hashCode() | this.bf.hashCode();
	}
	
	@Override
	public final boolean equals(final Object obj)
	{
		return this == obj;
	}
	
	@Override
	public final String toString()
	{
		return (this.bf.toString() + this.bm.toString()).substring(0, this.length());
	}
	
	public final Byte toByte()
	{
		try
		{
			return Byte.valueOf(this.toString());
		}
		catch(final NumberFormatException e)
		{
			return null;
		}
	}
	
	public final Short toShort()
	{
		try
		{
			return Short.valueOf(this.toString());
		}
		catch(final NumberFormatException e)
		{
			return null;
		}
	}
	
	public final Integer toInteger()
	{
		try
		{
			return Integer.valueOf(this.toString());
		}
		catch(final NumberFormatException e)
		{
			return null;
		}
	}
	
	public final Long toLong()
	{
		try
		{
			return Long.valueOf(this.toString());
		}
		catch(final NumberFormatException e)
		{
			return null;
		}
	}
	
	protected static final String toString(final Number n)
	{
		return String.valueOf(Math.abs(Util.nonNull(n).longValue()));
	}
	
	public static final Password valueOf(final String s)
	{
		return new Password(s);
	}
	
	public static final Password valueOf(final Number n)
	{
		return new Password(n);
	}
	
}
