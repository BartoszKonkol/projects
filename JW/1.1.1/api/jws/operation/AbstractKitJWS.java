package net.polishgames.vis2.server.jweb.jws.operation;

import java.util.HashMap;
import java.util.Map;
import net.polishgames.vis2.server.jweb.jws.parameter.ParameterJWS;

public abstract class AbstractKitJWS<K> extends OperationJWS
{
	
	private final Map<ParameterJWS, K> kit;
	
	public AbstractKitJWS()
	{
		this.kit = new HashMap<ParameterJWS, K>();
	}
	
	public final Map<ParameterJWS, K> giveKit()
	{
		return this.kit;
	}
	
}
