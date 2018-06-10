package net.polishgames.rhenowar.util.minigame;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import net.polishgames.rhenowar.util.Language;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.Item;

public final class UtilMinigame extends RhenowarObject
{
	
	private Object /*Util*/ util;
	
	public final UtilMinigame setUtil(final Object /*Util*/ util)
	{
		this.util = util;
		return this;
	}
	
	public final Object /*Util*/ getUtil()
	{
		return this.util;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.getUtil());
		return map;
	}
	
	public final Language giveLanguage(final String code)
	{
		return Language.giveValue(code);
	}
	
	public final ScriptMinigame giveScript(final File file, final String name, final Player player, final String... tables)
	{
		return new ScriptMinigame(file, name, player, tables);
	}
	
	public final ScriptMinigame giveScript(final File file, final String name, final String... tables)
	{
		return new ScriptMinigame(file, name, tables);
	}
	
	public final List<LuaValue> giveValues(final LuaTable table)
	{
		final List<LuaValue> list = new ArrayList<LuaValue>();
		for(final LuaValue key : Util.nonNull(table).keys())
			list.add(table.get(key));
		return Collections.unmodifiableList(list);
	}
	
	public final List<String> giveValuesString(final LuaTable table)
	{
		final List<String> list = new ArrayList<String>();
		for(final LuaValue value : this.giveValues(table))
			list.add(value.tojstring());
		return Collections.unmodifiableList(list);
	}
	
	public final List<String> giveValuesStringFormatted(final LuaTable table, final Language language) throws ReflectiveOperationException
	{
		final List<String> list = new ArrayList<String>();
		for(final String value : this.giveValuesString(table))
			list.add(this.giveFormattedText(value, language));
		return Collections.unmodifiableList(list);
	}
	
	public final String giveFormattedText(final String text, final Language language) throws ReflectiveOperationException
	{
		String result = "";
		final char first = Util.nonEmpty(text).charAt(0);
		if(first == '@')
			result = text.substring(1);
		else
			result = String.valueOf(this.doInvokeUtil("giveMessage", new Class<?>[]{String.class, String.class}, new Object[]{(first == '*' ? String.valueOf(this.doInvokeUtil("giveUtilConfigPrefix", new Class<?>[]{}, new Object[]{})) + "message" + text.substring(1) : text), Util.nonNull(language).giveCode()}));
		return result.replace('&', '§');
	}
	
	public final LuaValue giveValue(final ScriptMinigame script, final String name)
	{
		return Util.nonNull(script).giveGlobals().get(Util.nonEmpty(name));
	}
	
	public final ItemAction giveItem(final LuaTable table, final Language language, final String script) throws ReflectiveOperationException
	{
		Util.nonNull(table);
		Util.nonNull(language);
		Util.nonEmpty(script);
		final String actionKey = "action", itemKey = "item", metaKey = "item_meta", nameKey = "name", glowKey = "glow", nbtKey = "nbt";
		final Map<String, Object> map = new HashMap<String, Object>();
		for(final String key : new String[]{actionKey, itemKey, metaKey, nameKey, glowKey, nbtKey})
			map.put(key, table.get(key));
		if(map.get(actionKey) instanceof LuaString && map.get(itemKey) instanceof LuaString && map.get(nameKey) instanceof LuaString && map.get(glowKey) instanceof LuaBoolean)
		{
			final String action = Util.nonEmpty(((LuaString) map.get(actionKey)).tojstring()), item = Util.nonEmpty(((LuaString) map.get(itemKey)).tojstring()), name = Util.nonEmpty(((LuaString) map.get(nameKey)).tojstring());
			final boolean glow = Util.nonNull(((LuaBoolean) map.get(glowKey)).toboolean());
			final Material material = Util.nonNull(Material.matchMaterial(item));
			final Object metaObject = Util.nonNull(map.get(metaKey));
			LuaTable meta = null;
			if(metaObject != LuaValue.NIL && metaObject instanceof LuaTable)
				meta = (LuaTable) metaObject;
			int amount = 1, damage = 0;
			if(meta != null)
			{
				final LuaValue amountValue = meta.get("amount");
				if(amountValue != LuaValue.NIL && amountValue instanceof LuaInteger)
					amount = amountValue.toint();
				String damageKey = "damage";
				switch(material)
				{
					case WOOL:
					case STAINED_GLASS:
					case STAINED_CLAY:
					case STAINED_GLASS_PANE:
					case CARPET:
						damageKey = "color";
						break;
					case STONE:
					case DIRT:
					case WOOD:
					case LOG:
					case LOG_2:
					case SAPLING:
					case LEAVES:
					case SANDSTONE:
					case YELLOW_FLOWER:
					case RED_ROSE:
					case MONSTER_EGGS:
					case SMOOTH_BRICK:
					case WOOD_STEP:
					case QUARTZ_BLOCK:
					case DOUBLE_PLANT:
					case INK_SACK:
					case SKULL_ITEM:
						damageKey = "variant";
						break;
					default:
						break;
				}
				final LuaValue damageValue = meta.get(damageKey);
				if(damageValue != LuaValue.NIL && damageValue instanceof LuaInteger)
					damage = damageValue.toint();
			}
			ItemStack itemStack = (ItemStack) this.doInvokeUtil("giveItemCraft", new Class<?>[]{Object.class}, new Object[]{new ItemStack(material, amount, (short) damage)});
			final ItemMeta itemMeta = (ItemMeta) this.doInvokeUtil("giveItemMeta", new Class<?>[]{ItemStack.class}, new Object[]{itemStack});
			itemMeta.setDisplayName(ChatColor.GREEN + this.giveFormattedText(name, language));
			if(meta != null)
			{
				if((material == Material.LEATHER_HELMET || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_BOOTS) && itemMeta instanceof LeatherArmorMeta)
				{
					final LuaValue color = meta.get("color");
					if(color != LuaValue.NIL && color instanceof LuaInteger)
						((LeatherArmorMeta) itemMeta).setColor(Color.fromRGB(color.toint()));
				}
				if(material == Material.WRITTEN_BOOK && itemMeta instanceof BookMeta)
				{
					final BookMeta book = (BookMeta) itemMeta;
					final LuaValue title = meta.get("title"), author = meta.get("author"), pages = meta.get("pages");
					if(title != LuaValue.NIL && title instanceof LuaString)
						book.setTitle(Util.nonEmpty(this.giveFormattedText(title.tojstring(), language)));
					if(author != LuaValue.NIL && author instanceof LuaString)
						book.setAuthor(Util.nonEmpty(this.giveFormattedText(author.tojstring(), language)));
					if(pages != LuaValue.NIL && pages instanceof LuaTable)
						book.setPages(this.giveValuesStringFormatted((LuaTable) pages, language));
				}
				final LuaValue description = meta.get("description");
				if(description != LuaValue.NIL && description instanceof LuaTable)
					itemMeta.setLore(this.giveValuesStringFormatted((LuaTable) description, language));
			}
			itemStack.setItemMeta(itemMeta);
			if(meta != null && material == Material.SKULL_ITEM && damage == SkullType.PLAYER.ordinal())
			{
				final LuaValue owner = meta.get("owner");
				if(owner != LuaValue.NIL && owner instanceof LuaString)
					itemStack = (ItemStack) this.doInvokeUtil("giveHead", new Class<?>[]{String.class, ItemStack.class}, new Object[]{owner.tojstring(), itemStack});
			}
			Object nbtTag = null;
			try
			{
				if(glow)
					this.doInvokeUtil("doGlowItem", new Class<?>[]{ItemStack.class}, new Object[]{itemStack});
				nbtTag = this.doInvokeUtil("giveTagNBT", new Class<?>[]{ItemStack.class}, new Object[]{itemStack});
				final Object nbtObject = map.get(nbtKey);
				if(nbtObject != LuaValue.NIL && nbtObject instanceof LuaTable)
				{
					final LuaTable nbtTable = (LuaTable) nbtObject;
					for(final LuaValue key : nbtTable.keys())
						if(key instanceof LuaString)
						{
							final LuaValue valueLua = nbtTable.get(key);
							Object value = null;
							if(valueLua instanceof LuaTable)
								value = this.giveValuesString((LuaTable) valueLua);
							else if(valueLua instanceof LuaBoolean)
								value = valueLua.toboolean();
							else if(valueLua instanceof LuaInteger)
								value = valueLua.toint();
							else if(valueLua instanceof LuaDouble)
								value = valueLua.todouble();
							else
								value = valueLua.tojstring();
							if(value != null)
								this.doDefineNBT(nbtTag, key.tojstring(), value);
						}
				}
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
			@SuppressWarnings("deprecation")
			final ItemAction result = new ItemAction(this.getUtil().getClass().getClassLoader().loadClass(Item.class.getName()).getConstructor(ItemStack.class).newInstance(itemStack), action);
			if(nbtTag != null)
				try
				{
					this.doDefineNBT(nbtTag, "action", result.giveAction());
					if(result.isScript())
						this.doDefineNBT(nbtTag, "script", script);
				}
				catch(final ReflectiveOperationException e)
				{
					e.printStackTrace();
				}
			return result;
		}
		else
			return null;
	}
	
	public final ItemAction giveItem(final ScriptMinigame script, final String table, final int index, final Language language) throws ReflectiveOperationException
	{
		final LuaValue tableValue = this.giveValue(script, table);
		if(tableValue != LuaValue.NIL && tableValue instanceof LuaTable)
		{
			final LuaValue indexValue = tableValue.get(index);
			if(indexValue != LuaValue.NIL && indexValue instanceof LuaTable)
				return this.giveItem((LuaTable) indexValue, language, script.giveName());
		}
		return null;
	}
	
	public final ItemAction giveItem(final ScriptMinigame script, final String function, final Language language) throws ReflectiveOperationException
	{
		final LuaValue functionValue = this.giveValue(script, function);
		if(functionValue != null)
		{
			final LuaValue functionCall = functionValue.call();
			if(functionCall != LuaValue.NIL && functionCall instanceof LuaTable)
				return this.giveItem((LuaTable) functionCall, language, script.giveName());
		}
		return null;
	}
	
	public final Action<?> giveAction(final ScriptMinigame script, final String action) throws ReflectiveOperationException
	{
		Action<?> result = null;
		if(script.hasPlayer())
		{
			final Player player = script.givePlayer();
			final Object languageObject = this.doInvokeUtil("giveLanguage", new Class<?>[]{OfflinePlayer.class}, new Object[]{player});
			final Language language = this.giveLanguage((String) languageObject.getClass().getMethod("giveCode").invoke(languageObject));
			result = Action.giveAction(action, player);
			if(result == null)
			{
				final LuaValue actionValue = script.giveGlobals().get(action).call();
				if(actionValue != LuaValue.NIL && actionValue instanceof LuaTable)
				{
					final LuaValue typeValue = actionValue.get("type");
					if(typeValue != LuaValue.NIL && typeValue instanceof LuaString)
					{
						final ActionType actionType = ActionType.giveActionType(typeValue.tojstring());
						if(actionType != ActionType.END)
						{
							final LuaValue value = actionValue.get(actionType.giveName());
							if(value != LuaValue.NIL)
							{
								switch(actionType)
								{
									case INV:
									{
										if(value instanceof LuaTable)
										{
											final LuaValue title = value.get("title");
											final LuaValue size = value.get("size");
											if(title != LuaValue.NIL && size != LuaValue.NIL && title instanceof LuaString && size instanceof LuaInteger)
											{
												final int lines = size.toint();
												final Inventory inventory = (Inventory) this.doInvokeUtil("giveInventory", new Class<?>[]{String.class, int.class}, new Object[]{this.giveFormattedText(title.tojstring(), language), lines});
												for(int i = 1; i <= lines; i++)
												{
													final LuaValue line = value.get(i);
													if(line != LuaValue.NIL && line instanceof LuaTable)
													{
														for(int j = 1; j <= 9; j++)
														{
															final LuaValue item = line.get(j);
															if(item != LuaValue.NIL && item instanceof LuaTable)
															{
																final ItemAction itemAction = this.giveItem((LuaTable) item, language, script.giveName());
																if(itemAction != null)
																{
																	final ItemStack itemStack = itemAction.giveItemStack();
																	inventory.setItem((i - 1) * 9 + j - 1, itemStack);
																}
															}
														}
													}
												}
												@SuppressWarnings("deprecation")
												final ActionInv inv = new ActionInv(action, this.getUtil().getClass().getClassLoader().loadClass(net.polishgames.rhenowar.util.serialization.Inventory.class.getName()).getConstructor(Inventory.class).newInstance(inventory), player);
												result = inv;
											}
										}
									} break;
									case COM:
									{
										if(value instanceof LuaString)
											result = new ActionCom(action, this.giveFormattedText(value.tojstring(), language), player);
									} break;
									default:
										break;
								}
							}
						}
					}
				}
				if(result == null)
					result = new ActionEnd<Object>(action, player);
			}
		}
		return result;
	}
	
	protected final void doDefineNBT(final Object tag, final String key, final Object value) throws ReflectiveOperationException
	{
		this.doInvokeUtil("setObjectNBT", new Class<?>[]{Object.class, String.class, Object.class}, new Object[]{tag, key, value});
	}
	
	protected final Object doInvokeUtil(final String method, final Class<?>[] classes, final Object[] parameters) throws ReflectiveOperationException
	{
		return this.getUtil().getClass().getMethod(method, classes).invoke(this.getUtil(), parameters);
	}
	
}
