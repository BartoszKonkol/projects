package net.polishgames.rhenowar.util.mysql;

import java.io.Serializable;
import java.util.Map;
import net.polishgames.rhenowar.util.Password;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class ConnectionData extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	public ConnectionData(final String url, final String user, final Password password)
	{
		this.setURL(url).setUser(user).setPassword(password);
	}
	
	public ConnectionData(final String url, final String user, final Password password, final String dbname)
	{
		this(url, user, password);
		this.setDBName(dbname);
	}
	
	private String url, user, dbname;
	private transient Password password;
	
	public final ConnectionData setURL(final String url)
	{
		this.url = Util.nonEmpty(url);
		return this;
	}
	
	public final ConnectionData setUser(final String user)
	{
		this.user = Util.nonEmpty(user);
		return this;
	}
	
	public final ConnectionData setPassword(final Password password)
	{
		this.password = Util.nonEmpty(password);
		return this;
	}
	
	public final ConnectionData setDBName(final String dbname)
	{
		this.dbname = Util.nonEmpty(Util.nonNull(dbname).replaceAll("\\W", ""));
		return this;
	}
	
	public final String getURL()
	{
		return this.url;
	}
	
	public final String getUser()
	{
		return this.user;
	}
	
	public final Password getPassword()
	{
		return this.password;
	}
	
	public final String getDBName()
	{
		return this.dbname;
	}
	
	public final boolean hasURL()
	{
		return this.getURL() != null;
	}
	
	public final boolean hasUser()
	{
		return this.getUser() != null;
	}
	
	public final boolean hasPassword()
	{
		return this.getPassword() != null;
	}
	
	public final boolean hasDBName()
	{
		return this.getDBName() != null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("url", this.getURL());
		map.put("user", this.getUser());
		map.put("dbname", this.getDBName());
		return map;
	}
	
}
