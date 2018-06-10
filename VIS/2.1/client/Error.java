package net.polishgames.vis2.client;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Error
{
	
	private final ErrorType type;
	private final String code;
	private final List<String> details;
	private final int size;
	
	public Error(final ErrorType type, final int size, final String code, final List<String> details)
	{
		this.type = Objects.requireNonNull(type);
		this.code = Objects.requireNonNull(code);
		this.details = Collections.unmodifiableList(Objects.requireNonNull(details));
		this.size = this.giveDetails().size() == size ? size : -1;
	}
	
	public final ErrorType giveType()
	{
		return this.type;
	}
	
	public final int giveSize()
	{
		return this.size;
	}
	
	public final String giveCode()
	{
		return this.code;
	}
	
	public final List<String> giveDetails()
	{
		return this.details;
	}
	
	public final boolean isNormal()
	{
		return this.giveType() == ErrorType.NORMAL;
	}
	
	public final boolean isCritical()
	{
		return this.giveType() == ErrorType.CRITICAL;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + "{" + this.giveType().name().toLowerCase() + "," + this.giveSize() + "," + this.giveCode() + "}" + this.giveDetails();
	}
	
}
