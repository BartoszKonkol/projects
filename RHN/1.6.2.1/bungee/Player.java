package net.polishgames.rhenowar.util.bungee;

import java.util.Objects;
import java.util.UUID;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Player
{
	
	private final String username;
	private final UUID uuid;
	
	public Player(final String username, final UUID uuid)
	{
		this.username = Objects.requireNonNull(username);
		this.uuid = uuid;
	}
	
	public Player(final PendingConnection connection)
	{
		this(Objects.requireNonNull(connection).getName(), connection.getUniqueId());
	}
	
	public Player(final ProxiedPlayer player)
	{
		this(Objects.requireNonNull(player).getPendingConnection());
	}
	
	public final String giveName()
	{
		return this.username;
	}
	
	public final UUID giveUniqueID()
	{
		return this.uuid;
	}
	
	public final boolean hasUniqueID()
	{
		return this.giveUniqueID() != null;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + "{username=" + this.giveName() + ",uuid=" + String.valueOf(this.giveUniqueID()) + "}";
	}
	
}
