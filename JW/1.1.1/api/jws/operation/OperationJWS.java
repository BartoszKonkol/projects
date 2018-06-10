package net.polishgames.vis2.server.jweb.jws.operation;

import net.polishgames.vis2.server.jweb.jws.parameter.ParameterJWS;

public abstract class OperationJWS
{
	
	public abstract String giveName();
	public abstract ParameterJWS doExecute(final boolean indicator, final ParameterJWS... parameters);
	
	public char giveShort()
	{
		return this.giveName().charAt(0);
	}
	
	public final ParameterJWS doExecute(final ParameterJWS... parameters)
	{
		return this.doExecute(false, parameters);
	}
	
}
