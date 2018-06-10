package net.polishgames.rhenowar.util.craft;

import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;

public final class Craft extends RhenowarObject
{
	
	private final Util util;
	
	public Craft(final Util util)
	{
		this.util = Util.nonNull(util);
	}
	
	public final Util giveUtil()
	{
		return this.util;
	}
	
	public final String givePackage()
	{
		return this.getClass().getPackage().getName() + ".v" + this.giveUtil().giveServerVersion().giveFormattedVersion();
	}
	
	public final Class<?> giveClass(final String name) throws ClassNotFoundException
	{
		return Class.forName(this.givePackage() + "." + Util.nonEmpty(name));
	}
	
	public final Class<?> giveClass(final Class<?> type) throws ClassNotFoundException
	{
		return this.giveClass(Util.nonNull(type).getSimpleName());
	}
	
	public final Class<Tool> giveClassTool(final String name) throws ClassNotFoundException
	{
		final Class<Tool> tool = Tool.class;
		final Class<?> clazz = this.giveClass(Util.nonEmpty(name) + tool.getSimpleName());
		if(tool.isAssignableFrom(clazz))
			return (Class<Tool>) clazz;
		else
			return null;
	}
	
	public final <T extends Data> T giveData(final Class<T> type, final Object... parameters) throws ReflectiveOperationException
	{
		final Object obj = this.giveUtil().giveClass(this.giveClass(type), parameters);
		if(type.isAssignableFrom(obj.getClass()))
			return (T) obj;
		else
			return null;
	}
	
	public final PlayerData<?, ?> givePlayerData(final OfflinePlayer player) throws ReflectiveOperationException
	{
		return this.giveData(PlayerData.class, player, this.giveUtil());
	}
	
	public final ServerData<?, ?> giveServerData(final Server server) throws ReflectiveOperationException
	{
		return this.giveData(ServerData.class, server, this.giveUtil());
	}
	
	public final ServerData<?, ?> giveServerData() throws ReflectiveOperationException
	{
		return this.giveData(ServerData.class, this.giveUtil());
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.giveUtil());
		map.put("package", this.givePackage());
		return map;
	}
	
}
