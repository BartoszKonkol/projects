package vw.item;

import net.minecraft.item.ItemArmor;

public abstract class ItemArmorControl extends ItemArmor
{
	
	public static final byte SCAN = 1;
	
	public static final int[] maxDamageArray = new int[]{	5,	8,	7,	4,	};

	public ItemArmorControl(final ArmorMaterial par1ArmorMaterial, final EnumArmorMaterialControl par2EnumArmorMaterialControl, final int par3)
	{
		
		super(par1ArmorMaterial, -1, par3);
		this.setMaxDamage(par2EnumArmorMaterialControl.getDurability(par3));
		
	}
	
}
