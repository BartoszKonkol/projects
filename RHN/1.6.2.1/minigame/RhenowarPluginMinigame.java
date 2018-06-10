package net.polishgames.rhenowar.util.minigame;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import net.polishgames.rhenowar.util.LangPack;
import net.polishgames.rhenowar.util.Language;
import net.polishgames.rhenowar.util.Rhenowar;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;

public abstract class RhenowarPluginMinigame extends RhenowarPlugin
{
	
	private LangPack langpack;
	private Util util;
	private UtilMinigameSupport utilMinigame;
	protected Map<Language, Properties> translations;
	
	@Override
	public final void onLoad()
	{
		if(Util.hasUtil())
		{
			this.util = Util.giveUtil();
			this.giveUtil().addPlugin(this);
		}
		else
		{
			this.util = null;
			Util.doThrowNPE();
		}
		final ItemAction item = Util.nonNull(this.giveMinigameItem());
		final Action<?> action = this.giveMinigameAction();
		if(action != null)
		{
			final String itemName = item.giveAction(), actionName = action.giveName();
			if(!itemName.equals(actionName))
				Util.doThrowIAE(itemName + " != " + actionName);
		}
	}
	
	@Override
	public void onEnable()
	{
		if(this.hasUtil())
		{
			final Util util = this.giveUtil();
			if(util.hasUtilMinigame())
				this.utilMinigame = util.giveUtilMinigameSupport();
			if(util.hasLoaderLuaj())
			{
				final String langpackFileName = util.giveUtilLuaLangFile().getName();
				final File langpackFile = new File(this.getDataFolder(), langpackFileName);
				if(this.getResource(langpackFileName) != null && !langpackFile.exists())
					this.saveResource(langpackFileName, false);
				if(langpackFile.exists() && langpackFile.isFile())
					try
					{
						this.langpack = new LangPack(langpackFile);
						this.translations = Collections.unmodifiableMap(this.giveLangPack().giveTranslations());
					}
					catch(final ReflectiveOperationException e)
					{
						e.printStackTrace();
					}
			}
			if(this.translations != null)
				util.addLangPack(this.translations);
			util.addActionListener(this.giveMinigameListener());
		}
	}
	
	@Override
	public void onDisable()
	{
		if(this.hasUtil())
		{
			final Util util = this.giveUtil();
			if(this.translations != null)
				util.delLangPack(this.translations);
			util.delActionListener(this.giveMinigameListener());
		}
	}
	
	public abstract ItemAction giveMinigameItem();
	public abstract Rhenowar giveMinigameListener();
	
	public final Util giveUtil()
	{
		return this.util;
	}
	
	public final boolean hasUtil()
	{
		return this.giveUtil() != null;
	}
	
	public final UtilMinigameSupport giveUtilMinigame()
	{
		return this.utilMinigame;
	}
	
	public final boolean hasUtilMinigame()
	{
		return this.giveUtilMinigame() != null;
	}
	
	public final LangPack giveLangPack()
	{
		return this.langpack;
	}
	
	public final boolean hasLangPack()
	{
		return this.giveLangPack() != null;
	}
	
	public Action<?> giveMinigameAction()
	{
		return null;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("util", this.giveUtil());
		map.put("utilMinigame", this.giveUtilMinigame());
		map.put("langpack", this.giveLangPack());
		map.put("item", this.giveMinigameItem());
		map.put("action", this.giveMinigameAction());
		map.put("listener", this.giveMinigameListener());
		return super.giveProperties(map);
	}
	
}
