package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class ItemData 
{
	
	public static final byte SCAN = 1;
	
	private ItemData()
	{
		
		;
		
	}
	
	public static final Item
		wateryDiamond,
		darkMatter,
		whiteDust,
		whiteMatter,
		aquamarineItem,
		wateryDiamondDust,
		wateryDiamondShard,
		sunshade
	;

	static 
	{
		
		wateryDiamond = new ItemWaterydiamond().setUnlocalizedName("waterydiamond").setCreativeTab(CreativeTabs.tabMaterials);
		darkMatter = new ItemDarkmatter().setUnlocalizedName("darkmatter").setCreativeTab(CreativeTabs.tabMaterials);
		whiteDust = new ItemWhitedust().setUnlocalizedName("whitedust").setCreativeTab(CreativeTabs.tabMaterials);
		whiteMatter = new ItemWhitematter().setUnlocalizedName("whitematter").setCreativeTab(CreativeTabs.tabMaterials);
		aquamarineItem = new ItemAquamarineitem().setUnlocalizedName("aquamarineitem").setCreativeTab(CreativeTabs.tabMaterials);
		wateryDiamondDust = new ItemWaterydiamonddust().setUnlocalizedName("waterydiamonddust").setCreativeTab(CreativeTabs.tabMaterials);
		wateryDiamondShard = new ItemWaterydiamondshard().setUnlocalizedName("waterydiamondshard").setCreativeTab(CreativeTabs.tabMaterials);
		sunshade = new ItemSunshade().setUnlocalizedName("sunshade").setCreativeTab(CreativeTabs.tabDecorations);
		
	}
	
}
