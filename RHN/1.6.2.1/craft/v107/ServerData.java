package net.polishgames.rhenowar.util.craft.v107;

import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.command.ColouredConsoleSender;
import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.polishgames.rhenowar.util.Util;

public class ServerData extends net.polishgames.rhenowar.util.craft.ServerData<CraftServer, MinecraftServer>
{

	public ServerData(final Server server, final Util util)
	{
		super(server, util);
	}

	public ServerData(final Util util)
	{
		super(util);
	}

	@Override
	public final Class<CraftServer> giveCraftServerType()
	{
		return CraftServer.class;
	}

	@Override
	public final Class<MinecraftServer> giveMinecraftServerType()
	{
		return MinecraftServer.class;
	}

	@Override
	public final CraftServer giveCraftServer()
	{
		if(this.giveServer() instanceof CraftServer)
			return (CraftServer) this.giveServer();
		else
			return null;
	}

	@Override
	public final MinecraftServer giveMinecraftServer()
	{
		if(this.hasCraftServer())
			return this.giveCraftServer().getServer();
		else
			return null;
	}

	@Override
	public final ConsoleCommandSender giveColouredConsoleSender()
	{
		return ColouredConsoleSender.getInstance();
	}
	
	@Override
	public final CommandMap giveCommandMap()
	{
		return this.giveCraftServer().getCommandMap();
	}
	
}
