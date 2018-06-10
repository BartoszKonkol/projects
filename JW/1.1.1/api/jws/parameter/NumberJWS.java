package net.polishgames.vis2.server.jweb.jws.parameter;

public class NumberJWS extends ParameterJWS
{
	
	private final int number;
	
	public NumberJWS(final int number)
	{
		this.number = number;
	}

	@Override
	public int toNumber()
	{
		return this.number;
	}

	@Override
	public NumberJWS toNumberJWS()
	{
		return this;
	}

	@Override
	public String toString()
	{
		return String.valueOf(this.toNumber());
	}

	@Override
	public StringJWS toStringJWS()
	{
		return new StringJWS(this.toString());
	}

	@Override
	public boolean isNull()
	{
		return false;
	}
	
	@Override
	public NumberJWS clone()
	{
		return new NumberJWS(this.toNumber());
	}
	
	@Override
	public int hashCode()
	{
		return this.toNumber();
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return super.equals(object) && this.toNumber() == ((NumberJWS) object).toNumber();
	}
	
}
