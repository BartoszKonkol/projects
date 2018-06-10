package net.polishgames.rhenowar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration;

public abstract class Channel extends RhenowarObject implements PluginMessageListener
{
	
	private final String name;
	private final Plugin plugin;
	
	public Channel(final String name, final Plugin plugin)
	{
		this.name = Util.nonEmpty(name);
		this.plugin = Util.nonNull(plugin);
	}
	
	public Channel(final PluginMessageListenerRegistration income)
	{
		this(Util.nonNull(income).getChannel(), income.getPlugin());
		this.setIncome(income);
	}
	
	private PluginMessageListenerRegistration income;
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final Plugin givePlugin()
	{
		return this.plugin;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("plugin", this.givePlugin());
		map.put("name", this.giveName());
		map.put("income", this.getIncome());
		return map;
	}
	
	public final Channel setIncome(final PluginMessageListenerRegistration income)
	{
		this.income = Util.nonNull(income);
		return this;
	}
	
	public final PluginMessageListenerRegistration getIncome()
	{
		return this.income;
	}
	
	public final boolean doRegister()
	{
		if(!Util.hasUtil() || this.giveName().length() < 4)
			return false;
		else
		{
			Messenger messenger = null;
			Plugin plugin = null;
			String channel = null;
			PluginMessageListener listener = null;
			final PluginMessageListenerRegistration income = this.getIncome();
			if(income != null)
			{
				try
				{
					final Object object = new FieldData(income, "messenger").giveField().get(income);
					if(object != null && object instanceof Messenger)
						messenger = (Messenger) object;
				}
				catch(final ReflectiveOperationException e)
				{
					e.printStackTrace();
				}
				plugin = income.getPlugin();
				channel = income.getChannel();
				listener = income.getListener();
			}
			if(messenger == null)
				messenger = Util.giveUtil().giveServer().getMessenger();
			if(plugin == null)
				plugin = this.givePlugin();
			if(channel == null)
				channel = this.giveName();
			if(listener == null)
				listener = this;
			messenger.registerOutgoingPluginChannel(plugin, channel);
			this.setIncome(messenger.registerIncomingPluginChannel(plugin, channel, listener));
			return this.getIncome() != null;
		}
	}
	
	@Override
	@Deprecated
	public final void onPluginMessageReceived(final String channel, final Player player, final byte[] data)
	{
		final PluginMessageListenerRegistration income = this.getIncome();
		if(Util.nonEmpty(channel).equals(income != null ? income.getChannel() : this.giveName()))
			this.doReceive(Util.nonNull(data));
	}
	
	public final boolean doSend(final ByteArrayOutputStream data)
	{
		try
		{
			return this.onSend(Util.nonNull(data).toByteArray());
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public final boolean doReceive(final byte[] data)
	{
		try
		{
			return this.onReceive(new DataInputStream(new ByteArrayInputStream(Util.nonNull(data))));
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	protected boolean onSend(final byte[] data) throws IOException
	{
		if(Util.hasUtil())
		{
			final PluginMessageListenerRegistration income = this.getIncome();
			Util.giveUtil().giveServer().sendPluginMessage(income != null ? income.getPlugin() : this.givePlugin(), income != null ? income.getChannel() : this.giveName(), Util.nonNull(data));
			return true;
		}
		else
			return false;
	}
	
	protected abstract boolean onReceive(final DataInputStream data) throws IOException;
	
}
