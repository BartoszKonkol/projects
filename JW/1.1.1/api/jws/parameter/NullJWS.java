package net.polishgames.vis2.server.jweb.jws.parameter;

public class NullJWS extends ParameterJWS
{
	
	public static final NullJWS NULL = new NullJWS();
	
	protected NullJWS() {}
	
	@Override
	public String toString()
	{
		return String.valueOf((Object) null);
	}
	
	@Override
	public StringJWS toStringJWS()
	{
		return new StringJWS(null);
	}
	
	@Override
	public int toNumber()
	{
		return 0;
	}
	
	@Override
	public NumberJWS toNumberJWS()
	{
		return new NumberJWS(-1);
	}
	
	@Override
	public boolean isNull()
	{
		return true;
	}
	
	@Override
	public NullJWS clone()
	{
		return this;
	}
	
	@Override
	public int hashCode()
	{
		return 0;
	}
	
}
