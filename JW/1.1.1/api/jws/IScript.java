package net.polishgames.vis2.server.jweb.jws;

import java.io.PrintWriter;
import java.util.Collection;
import net.polishgames.vis2.server.jweb.api.BasicGroup;
import net.polishgames.vis2.server.jweb.jws.operation.OperationJWS;

public interface IScript
{
	
	public abstract IScript setContent(final String content);
	public abstract IScript setWriter(final PrintWriter writer);
	public abstract IScript setGroup(final BasicGroup group);
	public abstract String getContent();
	public abstract PrintWriter getWriter();
	public abstract BasicGroup getGroup();
	public abstract boolean hasContent();
	public abstract boolean hasWriter();
	public abstract boolean hasGroup();
	public abstract IScript addOperation(final OperationJWS operation);
	public abstract IScript delOperation(final OperationJWS operation);
	public abstract Collection<OperationJWS> giveOperations();
	public abstract IScript doInduce();
	
}
