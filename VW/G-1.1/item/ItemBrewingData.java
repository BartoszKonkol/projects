package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBrewingData 
{
	
	public static final byte SCAN = 1;
	
	private ItemBrewingData()
	{
		
		;
		
	}
	
	public static final Item
		wand,
		magic,
		enchantedWand,
		bookMagic,
		stoneTransformations,
		wandTeleportation,
		crystalCosmos
	;
	
	static
	{
		
		wand = new ItemWand().setUnlocalizedName("wand").setCreativeTab(CreativeTabs.tabBrewing);
		magic = new ItemMagic().setUnlocalizedName("magic").setCreativeTab(CreativeTabs.tabBrewing);
		enchantedWand = new ItemEnchantedWand().setUnlocalizedName("enchantedwand").setCreativeTab(CreativeTabs.tabBrewing);
		bookMagic = new ItemBookMagic().setUnlocalizedName("bookmagic").setCreativeTab(CreativeTabs.tabBrewing);
		stoneTransformations = new ItemStoneTransformations().setUnlocalizedName("stonetransformations").setCreativeTab(CreativeTabs.tabBrewing);
		wandTeleportation = new ItemWandTeleportation().setUnlocalizedName("wandteleportation").setCreativeTab(CreativeTabs.tabBrewing);
		crystalCosmos = new ItemCrystalCosmos().setUnlocalizedName("crystalcosmos").setCreativeTab(CreativeTabs.tabBrewing);
		
	}

}
