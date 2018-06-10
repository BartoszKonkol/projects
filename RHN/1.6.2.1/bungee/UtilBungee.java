package net.polishgames.rhenowar.util.bungee;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;
import net.md_5.bungee.api.ProxyConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.PluginManager;

@SuppressWarnings("deprecation")
public final class UtilBungee
{
	
	private static volatile UtilBungee utilBungee;
	
	private final UtilPluginBungee utilPluginBungee;
	
	public UtilBungee(final UtilPluginBungee utilPluginBungee)
	{
		this.utilPluginBungee = Objects.requireNonNull(utilPluginBungee);
		this.channels = new HashMap<String, ChannelBungee>();
		UtilBungee.utilBungee = this;
	}
	
	private Map<String, ChannelBungee> channels;
	private ChannelBungee utilChannel;
	
	public final UtilPluginBungee giveUtilPluginBungee()
	{
		return this.utilPluginBungee;
	}
	
	public final ProxyServer giveBungeeServer()
	{
		return this.giveUtilPluginBungee().getProxy();
	}
	
	public final PluginManager givePluginManager()
	{
		return this.giveBungeeServer().getPluginManager();
	}
	
	public final Logger giveUtilBungeeLogger()
	{
		return this.giveUtilPluginBungee().getLogger();
	}
	
	public final String giveUtilBungeeName()
	{
		return this.giveUtilPluginBungee().getDescription().getName();
	}
	
	public final ProxyConfig giveBungeeConfig()
	{
		return this.giveBungeeServer().getConfig();
	}

	public final List<ServerInfo> giveServers()
	{
		return Collections.unmodifiableList(new ArrayList<ServerInfo>(this.giveBungeeConfig().getServers().values()));
	}
	
	public final ServerInfo giveServer(final InetSocketAddress address)
	{
		Objects.requireNonNull(address);
		for(final ServerInfo server : this.giveServers())
			if(server.getAddress().equals(address))
				return server;
		return null;
	}
	
	public final List<ListenerInfo> giveListeners()
	{
		return Collections.unmodifiableList(new ArrayList<ListenerInfo>(this.giveBungeeConfig().getListeners()));
	}
	
	public final ListenerInfo giveMainServerListener()
	{
		for(final ListenerInfo listener : this.giveListeners())
			if(listener.getHost().getPort() == 25565)
				return listener;
		return null;
	}
	
	public final InetSocketAddress giveMainServerAddress()
	{
		final ListenerInfo listener = this.giveMainServerListener();
		if(listener != null)
			return listener.getHost();
		else
			return null;
	}
	
	public final String giveMainServerName()
	{
		final ListenerInfo listener = this.giveMainServerListener();
		if(listener != null)
			return listener.getDefaultServer();
		else
			return null;
	}
	
	public final ServerInfo giveMainServer()
	{
		final String name = this.giveMainServerName();
		if(name != null)
			return this.giveBungeeServer().getServerInfo(name);
		else
		{
			final Iterator<ServerInfo> servers = this.giveBungeeServer().getServers().values().iterator();
			if(servers.hasNext())
				return servers.next();
			else
				return null;
		}
	}
	
	public final UtilBungee setUtilChannel(final ChannelBungee channel)
	{
		this.utilChannel = Objects.requireNonNull(channel);
		return this;
	}
	
	public final ChannelBungee getUtilChannel()
	{
		return Objects.requireNonNull(this.utilChannel);
	}
	
	public final synchronized boolean addChannel(final ChannelBungee channel)
	{
		if(channel != null)
		{
			this.channels.put(channel.giveName().toLowerCase(), channel);
			return true;
		}
		else
			return false;
	}
	
	public final synchronized boolean delChannel(final String name)
	{
		return this.channels.remove(Objects.requireNonNull(name).toLowerCase()) != null;
	}
	
	public final synchronized ChannelBungee giveChannel(final String name)
	{
		return this.channels.get(Objects.requireNonNull(name).toLowerCase());
	}
	
	public final synchronized List<ChannelBungee> giveChannels()
	{
		return Collections.unmodifiableList(new ArrayList<ChannelBungee>(this.channels.values()));
	}
	
	public final boolean hasPaid(final Player player)
	{
		boolean haspaid = false;
		try
		{
			final InputStream stream = new URL("https://api.mojang.com/users/profiles/minecraft/" + Objects.requireNonNull(player).giveName()).openStream();
			final Scanner scanner = new Scanner(stream);
			haspaid = scanner.hasNext();
			scanner.close();
			stream.close();
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
		return haspaid;
	}
	
	public static final UtilBungee giveUtilBungee()
	{
		return UtilBungee.utilBungee;
	}
	
	public static final boolean hasUtilBungee()
	{
		return UtilBungee.giveUtilBungee() != null;
	}
	
}
