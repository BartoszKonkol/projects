package net.polishgames.rhenowar.util.bungee;

import java.util.Objects;

final class ConductBungee
{
	
	private final UtilPluginBungee plugin;
	
	ConductBungee(final UtilPluginBungee plugin)
	{
		this.plugin = Objects.requireNonNull(plugin);
	}
	
	final void onEnable()
	{
		Objects.requireNonNull(this.plugin.utilBungee).givePluginManager().registerListener(this.plugin, new EventsBungee(this.plugin));
		final ChannelBungee channel = new UtilChannelBungee(this.plugin.utilBungee.giveUtilBungeeName().toLowerCase().replace("bungee", ""), this.plugin);
		if(channel.doRegister())
		{
			this.plugin.utilBungee.addChannel(channel);
			this.plugin.utilBungee.setUtilChannel(channel);
		}
		else
			this.plugin.utilBungee.giveUtilBungeeLogger().severe("Channel of " + this.plugin.utilBungee.giveUtilBungeeName() + " isn't registered! (" + channel.toString() + ")");
	}
	
	final void onDisable()
	{
		;
	}
	
}
