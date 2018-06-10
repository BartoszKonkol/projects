package vw.item;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorRainbowControl extends ItemArmorControl
{
	
	public static final byte SCAN = 1;
	
	public static ItemArmor.ArmorMaterial rainbowArmorMaterial = EnumHelper.addArmorMaterial("rainbow", "rainbow", 15, new int[]{2, 6, 5, 2}, 9);

	public ItemArmorRainbowControl(final int par1)
	{
	
		super(rainbowArmorMaterial, EnumArmorMaterialControl.RAINBOW, par1);
		
	}
	
}
