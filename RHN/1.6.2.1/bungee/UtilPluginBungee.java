package net.polishgames.rhenowar.util.bungee;

import java.util.Objects;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

public final class UtilPluginBungee extends Plugin
{

	UtilBungee utilBungee;
	private ConductBungee conductBungee;
	
	@Override
	public void onLoad()
	{
		final PluginDescription description = this.getDescription();
		this.getLogger().info(description.getName() + " (" + description.getVersion() + ") by " + description.getAuthor() + "!");
		this.utilBungee = new UtilBungee(this);
	}
	
	@Override
	public void onEnable()
	{
		this.conductBungee = new ConductBungee(this);
		this.conductBungee.onEnable();
		this.getLogger().info(Objects.requireNonNull(this.utilBungee).giveUtilBungeeName() + " is enabled.");
	}
	
	@Override
	public void onDisable()
	{
		if(this.conductBungee != null)
			this.conductBungee.onDisable();
		this.getLogger().info(Objects.requireNonNull(this.utilBungee).giveUtilBungeeName() + " is disabled.");
	}
	
}
