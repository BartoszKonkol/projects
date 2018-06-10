package net.polishgames.rhenowar.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Builder;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

/** ISO 3166-1 alpha-3 */
public enum Language implements Rhenowar
{
	
	POL("pl"),
	USA("en", "us"),
	GBR("en", "uk"),
	DEU("de"),
	FRA("fr"),
	ESP("es"),
	RUS("ru"),
	UKR("uk", "ua"),
	JPN("ja", "jp"),
	;
	
	public static final Language DEFAULT = Util.hasUtil() ? Language.giveValue(Util.giveUtil().giveUtilConfig().getString(Util.giveUtil().giveUtilConfigPrefix() + "DefaultLanguage")) : null;

	private final Locale locale;
	private final String code, region, name;
	
	private Language(final String language, final String region)
	{
		this.locale = new Builder().setLanguage(Util.nonEmpty(language)).setRegion(region != null && !region.isEmpty() ? region : language).build();
		this.code = this.giveLocale().getLanguage() + "_" + this.giveLocale().getCountry();
		this.region = this.giveLocale().getDisplayCountry(this.giveLocale());
		final String name = this.giveLocale().getDisplayLanguage(this.giveLocale());
		this.name = name.substring(0, 1).toUpperCase(this.giveLocale()) + name.substring(1);
	}
	
	private Language(final String language)
	{
		this(language, null);
	}
	
	private ItemStack item;
	
	public final Locale giveLocale()
	{
		return this.locale;
	}
	
	public final String giveCode()
	{
		return this.code;
	}
	
	public final String giveRegion()
	{
		return this.region;
	}
	
	public final String giveName()
	{
		return this.name;
	}
	
	public final ItemStack giveItem()
	{
		if(Util.hasUtil() && this.item == null)
		{
			final Util util = Util.giveUtil();
			ItemStack item = new ItemStack(Material.BANNER);
			try
			{
				item = util.giveItemCraft(item);
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
			final ItemMeta meta = util.giveItemMeta(item);
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			meta.setDisplayName(ChatColor.GREEN + this.giveName() + " (" + this.giveRegion() + ")");
			String flag = null;
			final List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + this.giveCode());
			lore.add("");
			if(meta instanceof BannerMeta)
			{
				final BannerMeta banner = (BannerMeta) meta;
				switch(this)
				{
					case DEU:
					{
						banner.setBaseColor(DyeColor.RED);
						banner.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
						banner.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM));
						flag = "&0#########|&0#########|&4#########|&4#########|&6#########|&6#########";
					} break;
					case ESP:
					{
						banner.setBaseColor(DyeColor.YELLOW);
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
						flag = "&4#########|&6#########|&6#########|&6#########|&6#########|&4#########";
					} break;
					case FRA:
					{
						banner.setBaseColor(DyeColor.WHITE);
						banner.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_LEFT));
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_RIGHT));
						flag = "&1###&f###&4###|&1###&f###&4###|&1###&f###&4###|&1###&f###&4###|&1###&f###&4###|&1###&f###&4###";
					} break;
					case GBR:
					{
						banner.setBaseColor(DyeColor.BLUE);
						banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
						banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT));
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.CROSS));
						banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
						banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRAIGHT_CROSS));
						flag = "&4#&f#&1##&4#&1##&4#&f#|&1#&4#&f#&1#&4#&1#&4#&f#&1#|&f##&4#&f#&4##&f#&4##|&4##&f#&4##&f#&4#&f##|&1#&f#&4#&1#&4#&1#&f#&4#&1#|&f#&4#&1##&4#&1##&f#&4#";
					} break;
					case JPN:
					{
						banner.setBaseColor(DyeColor.WHITE);
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
						flag = "&f#########|&f####&4#&f####|&f###&4###&f###|&f###&4###&f###|&f####&4#&f####|&f#########";
					} break;
					case POL:
					{
						banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.HALF_HORIZONTAL));
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL_MIRROR));
						flag = "&f#########|&f#########|&f#########|&4#########|&4#########|&4#########";
					} break;
					case RUS:
					{
						banner.setBaseColor(DyeColor.BLUE);
						banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
						flag = "&f#########|&f#########|&1#########|&1#########|&4#########|&4#########";
					} break;
					case UKR:
					{
						banner.addPattern(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL));
						banner.addPattern(new Pattern(DyeColor.YELLOW, PatternType.HALF_HORIZONTAL_MIRROR));
						flag = "&1#########|&1#########|&1#########|&e#########|&e#########|&e#########";
					} break;
					case USA:
					{
						banner.setBaseColor(DyeColor.WHITE);
						banner.addPattern(new Pattern(DyeColor.RED, PatternType.BRICKS));
						banner.addPattern(new Pattern(DyeColor.BLUE, PatternType.SQUARE_TOP_LEFT));
						flag = "&1####&4#####|&1####&f#####|&1####&4#####|&f#########|&4#########|&f#########";
					} break;
				}
			}
			if(flag != null)
				for(final String line : flag.replace('|', ':').split(":"))
					lore.add(line.replace('&', '§').replace('#', '\u2589'));
			lore.add("");
			meta.setLore(lore);
			item.setItemMeta(meta);
			try
			{
				final Object tag = util.giveTagNBT(item);
				util.setObjectNBT(tag, "action", "ChangeLanguageAssign");
				util.setObjectNBT(tag, "lang", this.giveCode());
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
			this.item = item;
		}
		return this.item;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("code", this.giveCode());
		map.put("name", this.giveName());
		map.put("region", this.giveRegion());
		map.put("item", this.item);
		return map;
	}
	
	@Override
	public String toString()
	{
		if(Util.hasUtil())
			return Util.giveUtil().toString(this);
		else
			return this.giveCode();
	}
	
	public static final Language giveValue(final String code)
	{
		Util.nonEmpty(code);
		for(final Language language : Language.values())
			if(language.giveCode().equalsIgnoreCase(code))
				return language;
		return null;
	}
	
	public static final Language giveValue(final OfflinePlayer player)
	{
		if(player.isOnline())
			return Language.giveValue(player.getPlayer().spigot().getLocale());
		else
			return Language.DEFAULT;
	}
	
}
