package net.polishgames.rhenowar.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LangPack extends RhenowarObject
{
	
	private final Object script;
	
	public LangPack(final Object script)
	{
		if(LangPack.isScript(script))
			this.script = script;
		else
			this.script = null;
	}
	
	public LangPack(final Script script)
	{
		this((Object) script);
	}
	
	public LangPack(final File script) throws ReflectiveOperationException
	{
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final Object obj = util.giveClass(util.getLoaderLuaj().loadClass(Script.class.getName()), Util.nonNull(script), new String[0]);
			this.script = LangPack.isScript(obj) ? obj : null;
		}
		else
		{
			Util.doThrowNPE();
			this.script = null;
		}
	}
	
	public final Object giveScript()
	{
		return this.script;
	}
	
	public final boolean hasScript()
	{
		return this.giveScript() != null;
	}
	
	protected Properties giveTranslations(final Language language) throws ReflectiveOperationException
	{
		final Properties translations = new Properties();
		if(this.hasScript() && Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			final String prefix = "org.luaj.vm2.Lua";
			final Object value = util.doInvokeMethod("get", util.doInvokeMethod("giveGlobals", this.giveScript()), Util.nonNull(language).giveCode());
			if(!value.getClass().getName().equals(prefix + "Nil"))
			{
				final Object table = util.doInvokeMethod("call", value);
				if(table.getClass().getName().equals(prefix + "Table"))
				{
					final Object[] keys = (Object[]) util.doInvokeMethod("keys", table);
					for(final Object key : keys)
					{
						final String str = key.toString();
						translations.setProperty(str, util.doInvokeMethod("get", table, str).toString());
					}
				}
			}
		}
		return translations;
	}
	
	public final Map<Language, Properties> giveTranslations() throws ReflectiveOperationException
	{
		final Map<Language, Properties> result = new HashMap<Language, Properties>();
		for(final Language language : Language.values())
			result.put(language, this.giveTranslations(language));
		return result;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("script", this.giveScript());
		return map;
	}
	
	protected static final boolean isScript(final Object obj)
	{
		return Util.nonNull(obj).getClass().getName().equals(Script.class.getName());
	}
	
}
