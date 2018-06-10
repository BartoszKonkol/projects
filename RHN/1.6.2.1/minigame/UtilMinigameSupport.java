package net.polishgames.rhenowar.util.minigame;

import java.io.File;
import java.util.Map;
import org.bukkit.entity.Player;
import net.polishgames.rhenowar.util.Language;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;

public final class UtilMinigameSupport extends RhenowarObject
{
	
	private final Object utilMinigame;
	private final Util util;
	
	public UtilMinigameSupport(final Object utilMinigame, final Util util)
	{
		this.utilMinigame = Util.nonNull(utilMinigame);
		this.util = Util.nonNull(util);
	}
	
	public final Util giveUtil()
	{
		return this.util;
	}
	
	public final Object giveUtilMinigame()
	{
		return this.utilMinigame;
	}
	
	public final Class<?> giveUtilMinigameClass()
	{
		return this.giveUtilMinigame().getClass();
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.giveUtil());
		map.put("utilMinigame", this.giveUtilMinigame());
		map.put("utilMinigameClass", this.giveUtilMinigameClass());
		return map;
	}
	
	public final Object doInvoke(final String method, final Class<?>[] classes, final Object[] parameters) throws ReflectiveOperationException
	{
		return this.giveUtilMinigameClass().getMethod(method, classes).invoke(this.giveUtilMinigame(), parameters);
	}
	
	public final Object doInvoke(final String method) throws ReflectiveOperationException
	{
		return this.doInvoke(method, new Class<?>[]{}, new Object[]{});
	}
	
	// =========================
	
	public final Object /*UtilMinigame*/ setUtil(final Util util) throws ReflectiveOperationException
	{
		return this.doInvoke("setUtil", new Class<?>[]{Object.class}, new Object[]{util});
	}
	
	public final Object /*UtilMinigame*/ setUtil() throws ReflectiveOperationException
	{
		return this.setUtil(this.giveUtil());
	}
	
	public final Util getUtil() throws ReflectiveOperationException
	{
		final Object util = this.doInvoke("getUtil");
		if(util != null && util instanceof Util)
			return (Util) util;
		else
			return null;
	}
	
	public final boolean hasUtil() throws ReflectiveOperationException
	{
		return this.getUtil() != null;
	}
	
	public final Object /*Language*/ giveLanguage(final String code) throws ReflectiveOperationException
	{
		return this.doInvoke("giveLanguage", new Class<?>[]{String.class}, new Object[]{code});
	}
	
	public final Object /*ScriptMinigame*/ giveScript(final File file, final String name, final Player player, final String... tables) throws ReflectiveOperationException
	{
		return this.doInvoke("giveScript", new Class<?>[]{File.class, String.class, Player.class, String[].class}, new Object[]{file, name, player, tables});
	}
	
	public final Object /*ScriptMinigame*/ giveScript(final File file, final String name, final String... tables) throws ReflectiveOperationException
	{
		return this.doInvoke("giveScript", new Class<?>[]{File.class, String.class, String[].class}, new Object[]{file, name, tables});
	}
	
	public final String giveFormattedText(final String text, final Object /*Language*/ language) throws ReflectiveOperationException
	{
		final Object result = this.doInvoke("giveFormattedText", new Class<?>[]{String.class, language.getClass()}, new Object[]{text, language});
		if(result != null)
			return String.valueOf(result);
		else
			return null;
	}
	
	public final String giveFormattedText(final String text, final Language language) throws ReflectiveOperationException
	{
		return this.giveFormattedText(text, this.giveLanguage(language.giveCode()));
	}
	
	public final Object /*LuaValue*/ giveValue(final Object /*ScriptMinigame*/ script, final String name) throws ReflectiveOperationException
	{
		return this.doInvoke("giveScript", new Class<?>[]{script.getClass(), String.class}, new Object[]{script, name});
	}
	
	public final ItemAction giveItem(final Object /*ScriptMinigame*/ script, final String table, final int index, final Object /*Language*/ language) throws ReflectiveOperationException
	{
		final Object item = this.doInvoke("giveItem", new Class<?>[]{script.getClass(), String.class, int.class, language.getClass()}, new Object[]{script, table, index, language});
		if(item != null)
			return this.giveUtil().toItemAction(item);
		else
			return null;
	}
	
	public final ItemAction giveItem(final Object /*ScriptMinigame*/ script, final String table, final int index, final Language language) throws ReflectiveOperationException
	{
		return this.giveItem(script, table, index, this.giveLanguage(language.giveCode()));
	}
	
	public final ItemAction giveItem(final Object /*ScriptMinigame*/ script, final String function, final Object /*Language*/ language) throws ReflectiveOperationException
	{
		final Object item = this.doInvoke("giveItem", new Class<?>[]{script.getClass(), String.class, language.getClass()}, new Object[]{script, function, language});
		if(item != null)
			return this.giveUtil().toItemAction(item);
		else
			return null;
	}
	
	public final ItemAction giveItem(final Object /*ScriptMinigame*/ script, final String function, final Language language) throws ReflectiveOperationException
	{
		return this.giveItem(script, function, this.giveLanguage(language.giveCode()));
	}
	
	public final Action<?> giveAction(final Object /*ScriptMinigame*/ script, final String action) throws ReflectiveOperationException
	{
		final Object actionObject = this.doInvoke("giveAction", new Class<?>[]{script.getClass(), String.class}, new Object[]{script, action});
		if(actionObject != null)
			return this.giveUtil().toAction(actionObject);
		else
			return null;
	}
	
}
