package vw.item;

import net.minecraft.item.Item;

public final class ItemArmorData
{
	
	public static final byte SCAN = 1;
	
	private ItemArmorData()
	{
		
		;
		
	}
	
	public static final Item
		helmetRainbow,
		plateRainbow,
		legsRainbow,
		bootsRainbow
	;
	
	static 
	{
		
		helmetRainbow = new ItemHelmetRainbow(0).setUnlocalizedName("helmetRainbow");
		plateRainbow = new ItemPlateRainbow(1).setUnlocalizedName("plateRainbow");
		legsRainbow = new ItemLegsRainbow(2).setUnlocalizedName("legsRainbow");
		bootsRainbow = new ItemBootsRainbow(3).setUnlocalizedName("bootsRainbow");
		
	}

}
