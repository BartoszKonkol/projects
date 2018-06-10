package net.polishgames.rhenowar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import net.polishgames.rhenowar.util.event.ChannelReceivedRequestResponseEvent;
import net.polishgames.rhenowar.util.event.IReceivedRequestResponseEvent;
import net.polishgames.rhenowar.util.event.PlayerReceivedRequestResponseEvent;

public final class UtilChannel extends Channel
{
	
	public static final int DEFAULT_VALUE = Integer.MIN_VALUE;
	protected static volatile int id = UtilChannel.DEFAULT_VALUE;
	
	protected final Map<Integer, RequestData> caller;
	
	public UtilChannel(final String name, final UtilPlugin plugin)
	{
		super(name, plugin);
		this.caller = new HashMap<Integer, RequestData>();
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("id", UtilChannel.id);
		map.put("caller", Collections.unmodifiableMap(this.caller));
		return super.giveProperties(map);
	}
	
	public boolean doRequest(final String request, final RequestData data) throws IOException
	{
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream stream = new DataOutputStream(byteStream);
		byteStream.write("r".hashCode());
		stream.writeUTF(Util.nonEmpty(request).toLowerCase());
		byteStream.write(Util.nonNull(data).giveStream().toByteArray());
		this.caller.put(++UtilChannel.id, data.setName(request));
		return this.doSend(byteStream);
	}
	
	public final boolean doRequest(final String request, final ByteArrayOutputStream stream) throws IOException
	{
		return this.doRequest(request, new RequestData(stream));
	}
	
	@Override
	protected boolean onSend(final byte[] data) throws IOException
	{
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream stream = new DataOutputStream(byteStream);
		final ByteArrayInputStream input = new ByteArrayInputStream(Util.nonNull(data));
		final char[] word = Character.toChars(input.read());
		final boolean request = word.length == 1 && word[0] == 'r';
		stream.writeInt(request ? UtilChannel.id : ++UtilChannel.id);
		byteStream.write(data, request ? 1 : 0, data.length - (request ? 1 : 0));
		return super.onSend(byteStream.toByteArray());
	}

	@Override
	protected boolean onReceive(final DataInputStream stream) throws IOException
	{
		final int id = Util.nonNull(stream).readInt();
		final RequestData data = this.caller.get(id);
		if(data != null)
		{
			this.caller.remove(id);
			Object result = null;
			final String request = this.giveRequestName(data);
			switch(request)
			{
				case "ping":
				{
					if(stream.readUTF().equals("pong"))
						result = stream.readLong();
				} break;
				case "haspaid":
				{
					result = stream.readBoolean();
				} break;
			}
			if(result != null)
			{
				IReceivedRequestResponseEvent event = null;
				if(data.hasPlayer())
					event = new PlayerReceivedRequestResponseEvent(data.getPlayer(), request, id, result);
				else
					event = new ChannelReceivedRequestResponseEvent(request, id, result);
				if(event != null && event instanceof Event)
					this.givePlugin().getServer().getPluginManager().callEvent((Event) event);
				if(data.hasCallback())
					data.getCallback().onResultReceived(result);
				return true;
			}
		}
		return false;
	}
	
	protected final String giveRequestName(final RequestData data)
	{
		return Util.nonNull(data).getName();
	}
	
	public static final class RequestData extends RhenowarObject
	{
		
		private final ByteArrayOutputStream stream;
		
		public RequestData(final ByteArrayOutputStream stream)
		{
			this.stream = Util.nonNull(stream);
		}
		
		public RequestData()
		{
			this(new ByteArrayOutputStream());
		}
		
		private ICallback callback;
		private Player player;
		private String name;
		
		public final ByteArrayOutputStream giveStream()
		{
			return this.stream;
		}

		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("callback", this.getCallback());
			map.put("player", this.getPlayer());
			map.put("name", this.getName());
			return map;
		}
		
		public final RequestData setCallback(final ICallback callback)
		{
			this.callback = callback;
			return this;
		}
		
		public final ICallback getCallback()
		{
			return this.callback;
		}
		
		public final boolean hasCallback()
		{
			return this.getCallback() != null;
		}
		
		public final RequestData setPlayer(final Player player)
		{
			this.player = player;
			return this;
		}
		
		public final Player getPlayer()
		{
			return this.player;
		}
		
		public final boolean hasPlayer()
		{
			return this.getPlayer() != null;
		}
		
		final RequestData setName(final String name)
		{
			this.name = Util.nonEmpty(name);
			return this;
		}
		
		final String getName()
		{
			return this.name;
		}
		
	}
	
}
