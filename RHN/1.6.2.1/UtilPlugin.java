package net.polishgames.rhenowar.util;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public final class UtilPlugin extends RhenowarPlugin
{
	
	public static final Series SERIES = Series.values()[0];
	
	Util util;
	String locationsFileName, signsFileName, worldsFileName, inventoryFileName, langFileName;
	private Conduct conduct;
	
	@Override
	public final void onLoad()
	{
		final Series series = UtilPlugin.SERIES;
		this.getLogger().info(this.giveName() + " (version: " + series.giveVersion() + "; series: " + series.giveSeries() + "; build: " + series.giveBuild() + ") by " + this.getDescription().getAuthors().get(0) + "!");
		this.locationsFileName = "locations.yml";
		this.signsFileName = "signs.yml";
		this.inventoryFileName = "inventory.lua";
		this.langFileName = "lang.lua";
		this.worldsFileName = "worlds.rhn";
		this.saveDefaultConfig();
		for(final String file : new String[]{this.locationsFileName, this.signsFileName, this.inventoryFileName, this.langFileName})
			if(!new File(this.getDataFolder(), file).exists())
				this.saveResource(file, false);
		this.util = new Util(this, ServicePriority.Normal);
	}
	
	@Override
	public final void onEnable()
	{
		boolean error = false;
		for(final String plugin : this.getDescription().getSoftDepend())
			if(this.util.addPlugin(plugin) == null)
			{
				this.util.doSendConsole(Level.SEVERE, String.format("Plugin %s doesn't exist on this server!", plugin));
				error |= true;
			}
		if(error)
			this.getServer().getPluginManager().disablePlugin(this);
		else
		{
			this.conduct = new Conduct(this);
			this.conduct.onEnable();
			this.util.doSendColouredConsole(this, ChatColor.GREEN + this.giveName() + " is enabled.");
		}
	}
	
	@Override
	public final void onDisable()
	{
		if(this.conduct != null)
			this.conduct.onDisable();
		this.util.doSendColouredConsole(this, ChatColor.RED + this.giveName() + " is disabled.");
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.util);
		map.put("conduct", this.conduct);
		return super.giveProperties(map);
	}
	
}
