package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class ItemToolData 
{
	
	private ItemToolData()
	{
		
		;
		
	}
	
	public static final byte SCAN = 1;
	
	public static final Item
		daggerRainbow,
		daggerWateryDiamond,
		daggerModel,
		daggerWood,
		daggerStone,
		daggerIron,
		daggerGold,
		daggerDiamond,
		daggerAquamarine,
		daggerLightning
	;
	
	static 
	{
		
		daggerRainbow = new ItemDaggerRainbow().setUnlocalizedName("daggerRainbow").setCreativeTab(CreativeTabs.tabCombat);
		daggerWateryDiamond = new ItemDaggerWateryDiamond().setUnlocalizedName("daggerWateryDiamond").setCreativeTab(CreativeTabs.tabCombat);
		daggerModel = new ItemDaggerControl(true){}.setUnlocalizedName("daggerModel");
		daggerWood = new ItemDaggerWood().setUnlocalizedName("daggerWood").setCreativeTab(CreativeTabs.tabCombat);
		daggerStone = new ItemDaggerStone().setUnlocalizedName("daggerStone").setCreativeTab(CreativeTabs.tabCombat);
		daggerIron = new ItemDaggerIron().setUnlocalizedName("daggerIron").setCreativeTab(CreativeTabs.tabCombat);
		daggerGold = new ItemDaggerGold().setUnlocalizedName("daggerGold").setCreativeTab(CreativeTabs.tabCombat);
		daggerDiamond = new ItemDaggerDiamond().setUnlocalizedName("daggerDiamond").setCreativeTab(CreativeTabs.tabCombat);
		daggerAquamarine = new ItemDaggerAquamarine().setUnlocalizedName("daggerAquamarine").setCreativeTab(CreativeTabs.tabCombat);
		daggerLightning = new ItemDaggerLightning().setUnlocalizedName("daggerLightning").setCreativeTab(CreativeTabs.tabCombat);
		
	}
	
}
