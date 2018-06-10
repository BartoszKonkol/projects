package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

public class ItemFoodData 
{
	
	public static final byte SCAN = 1;
	
	public static final Item
		brain,
		blood,
		bloodPotion,
		bloodBowl
	;

	static 
	{
		
		brain = new ItemBrain(3, false).setPotionEffect(Potion.confusion.id, 60, 1, 0.9F).setUnlocalizedName("brain").setCreativeTab(CreativeTabs.tabFood);
		blood = new ItemBlood(1, false).setPotionEffect(Potion.wither.id, 2, 3, 1F).setUnlocalizedName("blood").setCreativeTab(CreativeTabs.tabFood);
		 bloodPotion = new ItemBloodpotion(2, false).setPotionEffect(Potion.confusion.id, 30, 0, 1F).setUnlocalizedName("bloodpotion").setCreativeTab(CreativeTabs.tabFood);
		 bloodBowl = new ItemBloodbowl(3, false).setPotionEffect(Potion.resistance.id, 45, 0, 0.8F).setUnlocalizedName("bloodbowl").setCreativeTab(CreativeTabs.tabFood);
		
	}

}
