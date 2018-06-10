package net.polishgames.vis2.server.jweb.jws.parameter;

public abstract class ParameterJWS implements Cloneable
{
	
	@Override
	public abstract String toString();
	public abstract StringJWS toStringJWS();
	public abstract int toNumber();
	public abstract NumberJWS toNumberJWS();
	
	public abstract boolean isNull();
	
	@Override
	public abstract ParameterJWS clone();
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this.getClass().isInstance(object) && (this.toString().equals(object.toString()) || this.toNumber() == ((ParameterJWS) object).toNumber());
	}
	
}
