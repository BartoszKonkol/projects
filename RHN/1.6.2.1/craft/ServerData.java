package net.polishgames.rhenowar.util.craft;

import java.util.Map;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.ConsoleCommandSender;
import net.polishgames.rhenowar.util.Util;

public abstract class ServerData<CraftServer extends Server, MinecraftServer> extends Data
{
	
	private final Server server;

	public ServerData(final Server server, final Util util)
	{
		super(util);
		this.server = Util.nonNull(server);
	}

	public ServerData(final Util util)
	{
		this(util.giveServer(), util);
	}
	
	public final Server giveServer()
	{
		return this.server;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("server", this.giveServer());
		map.put("serverCraft", this.giveCraftServer());
		map.put("serverMinecraft", this.giveMinecraftServer());
		map.put("classCraft", this.giveCraftServerType());
		map.put("classMinecraf", this.giveMinecraftServerType());
		map.put("consoleColoured", this.giveColouredConsoleSender());
		return super.giveProperties(map);
	}
	
	public final boolean hasCraftServer()
	{
		return this.giveCraftServer() != null;
	}
	
	public final boolean hasMinecraftServer()
	{
		return this.giveMinecraftServer() != null;
	}
	
	public abstract Class<CraftServer> giveCraftServerType();
	public abstract Class<MinecraftServer> giveMinecraftServerType();
	public abstract CraftServer giveCraftServer();
	public abstract MinecraftServer giveMinecraftServer();
	public abstract ConsoleCommandSender giveColouredConsoleSender();
	public abstract CommandMap giveCommandMap();
	
}
