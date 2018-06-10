package net.polishgames.rhenowar.util.bungee;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import net.md_5.bungee.api.config.ServerInfo;

public final class UtilChannelBungee extends ChannelBungee
{
	
	private final UtilPluginBungee plugin;
	
	public UtilChannelBungee(final String name, final UtilPluginBungee plugin)
	{
		super(name);
		this.plugin = Objects.requireNonNull(plugin);
	}
	
	public final UtilPluginBungee givePlugin()
	{
		return this.plugin;
	}

	@Override
	protected boolean onReceive(final ServerInfo server, final DataInputStream data) throws IOException
	{
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		final DataOutputStream response = new DataOutputStream(stream);
		boolean send = false;
		response.writeInt(Objects.requireNonNull(data).readInt());
		switch(data.readUTF().toLowerCase())
		{
			case "ping":
			{
				send = true;
				response.writeUTF("pong");
				response.writeLong(System.nanoTime());
			} break;
			case "haspaid":
			{
				if(UtilBungee.hasUtilBungee())
				{
					response.writeBoolean(UtilBungee.giveUtilBungee().hasPaid(new Player(data.readUTF(), UUID.fromString(data.readUTF()))));
					send = true;
				}
			} break;
		}
		if(send && response.size() > 0)
			return this.doSend(server, stream);
		else
			return false;
	}
	
}
