package net.polishgames.rhenowar.util.command;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.AbstractCommands;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.rank.Entitlement;

public final class RhenowarCommand extends Command implements Rhenowar
{
	
	private final Map<CommandHandlerData, CommandData> handlers;
	
	public RhenowarCommand(final CommandHandlerData[] handlers)
	{
		super(Util.nonEmpty(handlers[0].giveHandler().label()));
		this.handlers = new HashMap<CommandHandlerData, CommandData>();
		String descriptionGlobal = "", usageGlobal = this.getUsage() + (handlers.length > 1 ? " [%%]" : "");
		final List<String> aliasesGlobal = new ArrayList<String>();
		for(final CommandHandlerData data : handlers)
		{
			final CommandHandler handler = data.giveHandler();
			final String label = Util.nonEmpty(handler.label());
			String[] sublabel = handler.sublabel();
			String[] aliasesArray = handler.alias();
			String description = handler.description(), usage = handler.usage(), permission = handler.permission(), permissionMessage = handler.permissionMessage();
			if(sublabel == null || (sublabel.length == 1 && sublabel[0].equals("")))
				sublabel = new String[0];
			if(aliasesArray == null || (aliasesArray.length == 1 && aliasesArray[0].equals("")))
				aliasesArray = new String[0];
			if(description == null)
				description = "";
			String usages = "";
			if(sublabel.length > 0)
			{
				usages += " [";
				for(final String sl : sublabel)
					usages += sl + "|";
				usages = usages.substring(0, usages.length() - 1) + "]";
			}
			final String usageDefault = "/" + label;
			if(usage == null || usage.isEmpty())
				usage = usageDefault;
			if(!usage.startsWith(usageDefault))
				usage = usageDefault + usages + " " + usage;
			else
				usage += usages;
			if(permission != null && permission.isEmpty())
				permission = null;
			if(permissionMessage != null && permissionMessage.isEmpty())
				permissionMessage = null;
			final List<String> aliases = new ArrayList<String>();
			for(String alias : aliasesArray)
				if(alias != null && !alias.isEmpty())
				{
					alias = alias.toLowerCase();
					aliases.add(alias);
					if(!aliasesGlobal.contains(alias))
						aliasesGlobal.add(alias);
				}
			if(!description.isEmpty())
				descriptionGlobal += (handlers.length > 1 ? "\n" + usage + ": " : "") + description;
			usageGlobal = usageGlobal.contains("%%") ? usageGlobal.replace("%%", usage.replace(usageDefault, "").replace("%%", "?").trim() + "|%%") : usage;
			this.handlers.put(data, new CommandData(label, sublabel, description, usage, aliases, permission, permissionMessage, new Entitlement(handler.entitlement())));
		}
		this.setDescription(descriptionGlobal).setUsage(usageGlobal.replace("|%%", ""));
		if(!aliasesGlobal.isEmpty())
			this.setAliases(aliasesGlobal);
	}
	
	public final Map<CommandHandlerData, CommandData> giveHandlers()
	{
		return Collections.unmodifiableMap(this.handlers);
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("handlers", this.handlers);
		return map;
	}
	
	@Override
	public final boolean execute(final CommandSender sender, final String label, final String[] arguments)
	{
		boolean result = false, permission = false;
		final int length = arguments.length;
		final Set<CommandHandlerData> handlers = new HashSet<CommandHandlerData>(this.giveHandlers().keySet());
		for(final CommandHandlerData handler : this.giveHandlers().keySet())
		{
			int index = 0, size = 0;
			boolean array = false;
			for(final Parameter parameter : handler.giveMethod().getParameters())
			{
				final Class<?> type = parameter.getType();
				if(type == String.class)
				{
					if(index != 0)
						size++;
					index++;
				}
				else if(type.isPrimitive() || type == Integer.class || type == Float.class || type == Boolean.class)
					size++;
				else if(type.isArray())
				{
					array = true;
					break;
				}
			}
			if(array ? length == 0 : size != length)
				handlers.remove(handler);
		}
		loop:for(final CommandHandlerData handler : handlers)
		{
			final CommandData data = this.giveHandlers().get(handler);
			if(data.isSublabel() && length > 0)
			{
				boolean accept = false;
				for(final String sublabel : data.giveSublabel())
					if(sublabel != null && !sublabel.isEmpty() && sublabel.equalsIgnoreCase(arguments[0]))
					{
						accept = true;
						break;
					}
				if(!accept)
					continue;
			}
			if(data.isPermission())
				this.setPermission(data.givePermission());
			if(data.isPermissionMessage())
				this.setPermissionMessage(data.givePermissionMessage());
			if(!this.testPermissionSilent(sender))
			{
				permission |= true;
				continue;
			}
			else
			{
				final CommandExecutorType executorAccept = handler.giveHandler().executor();
				CommandExecutorType executor = CommandExecutorType.OTHER;
				if(sender instanceof BlockCommandSender)
					executor = CommandExecutorType.BLOCK;
				else if(sender instanceof ConsoleCommandSender)
					executor = CommandExecutorType.CONSOLE;
				else if(sender instanceof RemoteConsoleCommandSender)
					executor = CommandExecutorType.CONSOLE_REMOTE;
				else if(sender instanceof Player)
				{
					executor = CommandExecutorType.PLAYER;
					if(Util.hasUtil() && Util.giveUtil().isBlocked((Player) sender, data.giveEntitlement()))
					{
						permission |= true;
						continue;
					}
				}
				if(executorAccept != null && executorAccept != CommandExecutorType.ALL)
					switch(executor)
					{
						case OTHER:
						{
							if(executorAccept != CommandExecutorType.OTHER)
								continue;
						} break;
						case BLOCK:
						{
							switch(executorAccept)
							{
								case BLOCK:
								case CONSOLE_OR_BLOCK:
								case CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK:
								case CONSOLE_REMOTE_OR_BLOCK:
								case PLAYER_OR_BLOCK:
								case PLAYER_OR_CONSOLE_OR_BLOCK:
								case PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK:
								case PLAYER_OR_CONSOLE_REMOTE_OR_BLOCK:
									break;
								default:
									continue;
							}
						} break;
						case CONSOLE:
						{
							switch(executorAccept)
							{
								case CONSOLE:
								case CONSOLE_OR_BLOCK:
								case CONSOLE_OR_CONSOLE_REMOTE:
								case CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK:
								case PLAYER_OR_CONSOLE:
								case PLAYER_OR_CONSOLE_OR_BLOCK:
								case PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE:
								case PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK:
									break;
								default:
									continue;
							}
						} break;
						case CONSOLE_REMOTE:
						{
							switch(executorAccept)
							{
								case CONSOLE_REMOTE:
								case CONSOLE_OR_CONSOLE_REMOTE:
								case CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK:
								case CONSOLE_REMOTE_OR_BLOCK:
								case PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE:
								case PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK:
								case PLAYER_OR_CONSOLE_REMOTE:
								case PLAYER_OR_CONSOLE_REMOTE_OR_BLOCK:
									break;
								default:
									continue;
							}
						} break;
						case PLAYER:
						{
							switch(executorAccept)
							{
								case PLAYER:
								case PLAYER_OR_BLOCK:
								case PLAYER_OR_CONSOLE:
								case PLAYER_OR_CONSOLE_OR_BLOCK:
								case PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE:
								case PLAYER_OR_CONSOLE_OR_CONSOLE_REMOTE_OR_BLOCK:
								case PLAYER_OR_CONSOLE_REMOTE:
								case PLAYER_OR_CONSOLE_REMOTE_OR_BLOCK:
									break;
								default:
									continue;
							}
						} break;
						default:
							continue;
					}
				int index = 0;
				final List<Object> parameters = new ArrayList<Object>();
				for(final Parameter parameter : handler.giveMethod().getParameters())
				{
					Object value = null;
					final String name = parameter.isNamePresent() ? parameter.getName() : null;
					final Class<?> type = parameter.getType();
					if(Command.class.isAssignableFrom(type) || (type == Object.class && name != null && name.equalsIgnoreCase("command")))
						value = this;
					else if(CommandData.class.isAssignableFrom(type) || (type == Object.class && name != null && name.equalsIgnoreCase("data")))
						value = data;
					else if(CommandSender.class.isAssignableFrom(type) || (type == Object.class && name != null && name.equalsIgnoreCase("sender")))
						value = sender;
					else if(CommandExecutorType.class.isAssignableFrom(type) || (type == Object.class && name != null && name.equalsIgnoreCase("executor")))
						value = executor;
					else if(handler.giveListenerClass().isAssignableFrom(type) || (Rhenowar.class.isAssignableFrom(type) && (name != null ? name.equalsIgnoreCase("listener") : true)))
						value = handler.giveListener();
					else if(type == String.class && (index == 0 || (name != null && name.equalsIgnoreCase("label"))))
					{
						value = label;
						if(index == 0)
							index++;
					}
					else if(index > 0 && index <= length)
					{
						if(type.isArray())
						{
							final String[] array = Arrays.copyOfRange(arguments, index - 1, length);
							final int lengthArray = array.length;
							if(type == String[].class)
								value = array;
							else if(type == Object[].class)
							{
								final Object[] arrayConvert = new Object[lengthArray];
								for(int i = 0; i < lengthArray; i++)
									arrayConvert[i] = array[i];
								value = arrayConvert;
							}
							else if(type.getComponentType().isPrimitive())
								try
								{
									switch(type.getSimpleName())
									{
										case "int":
										{
											final int[] arrayConvert = new int[lengthArray];
											for(int i = 0; i < lengthArray; i++)
												arrayConvert[i] = Integer.valueOf(array[i]);
											value = arrayConvert;
										} break;
										case "float":
										{
											final float[] arrayConvert = new float[lengthArray];
											for(int i = 0; i < lengthArray; i++)
												arrayConvert[i] = Float.valueOf(array[i]);
											value = arrayConvert;
										} break;
										case "boolean":
										{
											final boolean[] arrayConvert = new boolean[lengthArray];
											for(int i = 0; i < lengthArray; i++)
												arrayConvert[i] = Boolean.valueOf(array[i]);
											value = arrayConvert;
										} break;
									}
								}
								catch(final NumberFormatException e)
								{
									switch(type.getSimpleName())
									{
										case "int":
											value = new int[lengthArray];
											break;
										case "float":
											value = new float[lengthArray];
											break;
									}
								}
							index += lengthArray;
						}
						else
						{
							final String argument = arguments[index - 1];
							if(type == String.class)
								value = argument;
							else if(type == Object.class)
								value = (Object) argument;
							else
								try
								{
									if(type.isPrimitive())
										switch(type.getSimpleName())
										{
											case "int":
												value = (int) Integer.valueOf(argument);
												break;
											case "float":
												value = (float) Float.valueOf(argument);
												break;
											case "boolean":
												value = (boolean) Boolean.valueOf(argument);
												break;
										}
									else if(type == Integer.class)
										value = Integer.valueOf(argument);
									else if(type == Float.class)
										value = Float.valueOf(argument);
									else if(type == Boolean.class)
										value = Boolean.valueOf(argument);
								}
								catch(final NumberFormatException e)
								{
									AbstractCommands.doSendNumberIncorrect(sender, argument);
									result |= true;
									break loop;
								}
							index++;
						}
					}
					if(value == null && type.isPrimitive())
						switch(type.getSimpleName())
						{
							case "byte": value = (byte) 0; break;
							case "short": value = (short) 0; break;
							case "int": value = (int) 0; break;
							case "long": value = (long) 0L; break;
							case "float": value = (float) 0.0F; break;
							case "double": value = (double) 0.0D; break;
							case "boolean": value = (boolean) false; break;
							case "char": value = (char) '\u0000'; break;
						}
					parameters.add(value);
				}
				try
				{
					handler.giveMethod().invoke(handler.giveListener(), parameters.toArray());
					result |= true;
				}
				catch(final ReflectiveOperationException e)
				{
					e.printStackTrace();
				}
			}
		}
		if(permission)
		{
			AbstractCommands.doSendNoPermission(sender);
			result |= true;
		}
		if(!result)
		{
			if(length == 0)
			{
				final String command = "? " + label;
				if(Util.hasUtil())
					Util.giveUtil().giveServer().dispatchCommand(sender, command);
				else
					Bukkit.dispatchCommand(sender, command);
			}
			else
				AbstractCommands.doSendNoSuccess(sender, label);
		}
		return result;
	}
	
}
