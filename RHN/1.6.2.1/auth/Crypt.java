package net.polishgames.rhenowar.util.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import net.polishgames.rhenowar.util.Password;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;

public final class Crypt extends RhenowarObject implements AutoCloseable
{
	
	private static MessageDigest md5, sha512;
	
	static
	{
		try
		{
			md5 = MessageDigest.getInstance("MD5");
			sha512 = MessageDigest.getInstance("SHA-512");
		}
		catch(final NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}
	
	private final transient Password password;
	
	public Crypt(final Password password)
	{
		Util.nonNull(password);
		Crypt.doReset();
		if(Crypt.hasMD5() && Crypt.hasSHA512())
		{
			Crypt.giveSHA512().update(password.toString().getBytes());
			Crypt.giveMD5().update(Crypt.giveSHA512().digest());
			this.password = new Password("pass");
			this.password.append(":").append("crypt").append(":");
			for(final byte value : Crypt.giveMD5().digest())
				this.password.append(Integer.toHexString(value + Math.abs(Byte.MIN_VALUE)).toUpperCase());
			this.password.append("$\u0000\u0000\u0000\u0000\u0000\u0000");
			Crypt.doReset();
		}
		else
			this.password = password;
	}
	
	@Override
	public final Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		return this.password.giveProperties(map);
	}

	@Override
	public final void close()
	{
		this.password.close();
	}
	
	@Override
	public final int compareTo(final Rhenowar rhenowar)
	{
		return this.password.compareTo(rhenowar);
	}
	
	@Override
	public final int hashCode()
	{
		return this.password.hashCode();
	}
	
	@Override
	public final boolean equals(final Object obj)
	{
		return this.toString().equalsIgnoreCase(Util.nonNull(obj).toString());
	}
	
	@Override
	public final String toString()
	{
		return this.password.toString();
	}
	
	protected static final void doReset()
	{
		if(Crypt.hasMD5())
			Crypt.giveMD5().reset();
		if(Crypt.hasSHA512())
			Crypt.giveSHA512().reset();
	}
	
	protected static final MessageDigest giveMD5()
	{
		return Crypt.md5;
	}
	
	protected static final MessageDigest giveSHA512()
	{
		return Crypt.sha512;
	}
	
	protected static final boolean hasMD5()
	{
		return Crypt.giveMD5() != null;
	}
	
	protected static final boolean hasSHA512()
	{
		return Crypt.giveSHA512() != null;
	}
	
}
