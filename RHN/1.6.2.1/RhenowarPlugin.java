package net.polishgames.rhenowar.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class RhenowarPlugin extends JavaPlugin implements Rhenowar, Comparable<Plugin>
{

	@Override
	public abstract void onLoad();
	
	@Override
	public abstract void onEnable();
	
	@Override
	public abstract void onDisable();
	
	@Override
	public int compareTo(final Plugin plugin)
	{
		final int compare = Util.nonEmpty(this.getName()).compareTo(Util.nonEmpty(plugin.getName()));
		if(compare == 0 && !(plugin instanceof RhenowarPlugin))
			return -1;
		else
			return compare;
	}
	
	public final String giveName()
	{
		return this.getDescription().getName();
	}
	
	public final String giveVersion()
	{
		return this.getDescription().getVersion();
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		final PluginDescriptionFile description = this.getDescription();
		map.put("enabled", this.isEnabled());
		map.put("file", this.getFile());
		map.put("directory", this.getDataFolder());
		map.put("config", this.getConfig());
		map.put("script", description);
		map.put("name", this.giveName());
		map.put("version", this.giveVersion());
		final String
			main = description.getMain(),
			website = description.getWebsite(),
			descriptionText = description.getDescription(),
			prefix = description.getPrefix();
		final List<?>
			depend = description.getDepend(),
			softdepend = description.getSoftDepend(),
			loadbefore = description.getLoadBefore(),
			authors = description.getAuthors(),
			permissions = description.getPermissions();
		final Map<?, ?>
			commands = description.getCommands();
		final Set<?>
			awareness = description.getAwareness();
		final PluginLoadOrder load = description.getLoad();
		final PermissionDefault permission = description.getPermissionDefault();
		if(main != null && !main.isEmpty())
			map.put("main", main);
		if(commands != null && !commands.isEmpty())
			map.put("commands", Collections.unmodifiableMap(commands));
		if(depend != null && !depend.isEmpty())
			map.put("depend",  Collections.unmodifiableList(depend));
		if(softdepend != null && !softdepend.isEmpty())
			map.put("softdepend", Collections.unmodifiableList(softdepend));
		if(loadbefore != null && !loadbefore.isEmpty())
			map.put("loadbefore", Collections.unmodifiableList(loadbefore));
		if(website != null && !website.isEmpty())
			map.put("website", website);
		if(descriptionText != null && !descriptionText.isEmpty())
			map.put("description", descriptionText);
		if(load != null)
			map.put("load", load);
		if(authors != null && !authors.isEmpty())
			map.put("authors", Collections.unmodifiableList(authors));
		if(permission != null)
			map.put("permission", permission);
		if(awareness != null && !awareness.isEmpty())
			map.put("awareness", Collections.unmodifiableSet(awareness));
		if(permissions != null && !permissions.isEmpty())
			map.put("permissions", Collections.unmodifiableList(permissions));
		if(prefix != null && !prefix.isEmpty())
			map.put("prefix", prefix);
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this, true, true);
		else
			return super.toString();
	}
	
}
