package net.polishgames.rhenowar.util.command;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.rank.Entitlement;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class CommandData extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.5.2.2");
	
	private final String label, description, usage, permission, permissionMessage;
	private final String[] sublabel;
	private final List<String> aliases;
	private final Entitlement entitlement;
	
	public CommandData(final String label, final String[] sublabel, final String description, final String usage, final List<String> aliases, final String permission, final String permissionMessage, final Entitlement entitlement)
	{
		this.label = Util.nonEmpty(label);
		this.sublabel = Util.nonNull(sublabel);
		this.description = Util.nonNull(description);
		this.usage = Util.nonEmpty(usage);
		this.aliases = Util.nonNull(aliases);
		this.permission = permission;
		this.permissionMessage = permissionMessage;
		this.entitlement = Util.nonNull(entitlement);
	}
	
	public final String giveLabel()
	{
		return this.label;
	}
	
	public final String[] giveSublabel()
	{
		return this.sublabel;
	}
	
	public final String giveDescription()
	{
		return this.description;
	}
	
	public final String giveUsage()
	{
		return this.usage;
	}
	
	public final List<String> giveAliases()
	{
		return Collections.unmodifiableList(this.aliases);
	}
	
	public final String givePermission()
	{
		return this.permission;
	}
	
	public final String givePermissionMessage()
	{
		return this.permissionMessage;
	}
	
	public final Entitlement giveEntitlement()
	{
		return this.entitlement;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("label", this.giveLabel());
		map.put("sublabel", this.giveSublabel());
		map.put("description", this.giveDescription());
		map.put("usage", this.giveUsage());
		map.put("aliases", this.giveAliases());
		map.put("permission", this.givePermission());
		map.put("permissionMessage", this.givePermissionMessage());
		map.put("entitlement", this.giveEntitlement());
		return map;
	}
	
	public final boolean isSublabel()
	{
		return this.giveSublabel().length > 0;
	}
	
	public final boolean isPermission()
	{
		return this.givePermission() != null;
	}
	
	public final boolean isPermissionMessage()
	{
		return this.givePermissionMessage() != null;
	}
	
	public final boolean isDescription()
	{
		return !this.giveDescription().isEmpty();
	}
	
	public final boolean isAliases()
	{
		return !this.giveAliases().isEmpty();
	}
	
}
