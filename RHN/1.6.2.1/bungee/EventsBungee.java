package net.polishgames.rhenowar.util.bungee;

import java.util.Objects;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public final class EventsBungee implements Listener
{
	
	private final Plugin plugin;
	
	public EventsBungee(final Plugin plugin)
	{
		this.plugin = Objects.requireNonNull(plugin);
	}
	
	public final Plugin givePlugin()
	{
		return this.plugin;
	}
	
	@EventHandler
	public void onLogin(final PreLoginEvent event)
	{
		final PendingConnection connection = event.getConnection();
		if(UtilBungee.hasUtilBungee() && UtilBungee.giveUtilBungee().hasPaid(new Player(connection)))
			connection.setOnlineMode(true);
	}
	
	@EventHandler
	public void onPluginMessage(final PluginMessageEvent event)
	{
		if(UtilBungee.hasUtilBungee())
		{
			final UtilBungee utilBungee = UtilBungee.giveUtilBungee();
			final ChannelBungee channel = utilBungee.giveChannel(event.getTag());
			final ServerInfo server = utilBungee.giveServer(event.getSender().getAddress());
			if(channel != null && server != null)
				channel.doReceive(server, event.getData());
		}
	}
	
}
