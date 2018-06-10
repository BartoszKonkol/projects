package net.polishgames.vis2.client;

import java.util.Objects;
import java.util.Properties;

public abstract class Part
{
	
	private final byte[] content;
	private final Properties header;
	
	public Part(final String name, final byte[] content)
	{
		this.content = Objects.requireNonNull(content);
		this.header = new Properties();
		this.giveHeader().setProperty("Name", Objects.requireNonNull(name));
		this.giveHeader().setProperty("Length", String.valueOf(this.giveContent().length));
	}
	
	public Part(final String name)
	{
		this(name, new byte[0]);
	}
	
	public final byte[] giveContent()
	{
		return this.content;
	}
	
	public final Properties giveHeader()
	{
		return this.header;
	}
	
	public final String giveName()
	{
		return this.giveHeader().getProperty("Name");
	}
	
	public final boolean hasContent()
	{
		return this.giveContent().length > 0;
	}
	
	public final Part addHeader(final String key, final String value)
	{
		this.giveHeader().setProperty(Objects.requireNonNull(key), Objects.requireNonNull(value));
		return this;
	}
	
	public final Part delHeader(final String key)
	{
		this.giveHeader().remove(Objects.requireNonNull(key));
		return this;
	}
	
	public abstract void onReceive(final Properties header, final byte[] content);
	
}
