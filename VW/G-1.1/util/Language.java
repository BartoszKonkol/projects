package vw.util;

import java.util.HashMap;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class Language
{
	
	public static final byte SCAN = 1;
	
	protected static final HashMap<String, String> hm = new HashMap<String, String>();
	
	public static void add(final String id, String text)
	{
		
		if(text == null)
			text = "";
		
		hm.put(id, text);
		
	}
	
	public static void del(final String id)
	{
		
		hm.remove(id);
		
	}
	
	public static void clean()
	{
		
		hm.clear();
		
	}
	
	public static final void register()
	{
		
		register("en_US");
		
	}
	
	public static void register(final String lang)
	{
		
		LanguageRegistry.instance().injectLanguage(lang, hm);
		
		clean();
		
	}
	
}
