package net.polishgames.rhenowar.util.bungee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;
import net.md_5.bungee.api.config.ServerInfo;

public abstract class ChannelBungee
{
	
	private final String name;
	
	public ChannelBungee(final String name)
	{
		this.name = Objects.requireNonNull(name);
	}
	
	protected volatile ServerInfo lastServer;
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final boolean doRegister()
	{
		if(!UtilBungee.hasUtilBungee() || this.giveName().length() < 4)
			return false;
		else
		{
			UtilBungee.giveUtilBungee().giveBungeeServer().registerChannel(this.giveName());
			return true;
		}
	}
	
	public final boolean doSend(final ServerInfo server, final ByteArrayOutputStream data)
	{
		try
		{
			final boolean result = this.onSend(Objects.requireNonNull(server), Objects.requireNonNull(data).toByteArray());
			this.lastServer = server;
			return result;
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public final boolean doSend(final ByteArrayOutputStream data)
	{
		if(UtilBungee.hasUtilBungee())
		{
			final ServerInfo server = UtilBungee.giveUtilBungee().giveMainServer();
			if(server != null)
				return this.doSend(server, data);
		}
		return false;
	}
	
	public final boolean doReceive(final ServerInfo server, final byte[] data)
	{
		try
		{
			boolean result = this.onReceive(Objects.requireNonNull(server), new DataInputStream(new ByteArrayInputStream(Objects.requireNonNull(data))));
			this.lastServer = server;
			return result;
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public final boolean doReceive(final byte[] data)
	{
		if(UtilBungee.hasUtilBungee())
		{
			final ServerInfo server = UtilBungee.giveUtilBungee().giveMainServer();
			if(server != null)
				return this.doReceive(server, data);
		}
		return false;
	}
	
	protected boolean onSend(final ServerInfo server, final byte[] data) throws IOException
	{
		Objects.requireNonNull(server).sendData(this.giveName(), Objects.requireNonNull(data));
		return true;
	}
	
	protected abstract boolean onReceive(final ServerInfo server, final DataInputStream data) throws IOException;
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + "{name=" + this.giveName() + "}";
	}
	
}
