package vw.item;

public enum EnumArmorMaterialControl
{
	
	RAINBOW(3, new int[]{	0,	0,	0,	0,	}, 0);
	
	public static final byte SCAN = 1;

	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;

	private EnumArmorMaterialControl(final int par1, final int[] args2, final int par3)
	{
		
		this.maxDamageFactor = par1;
		this.damageReductionAmountArray = args2;
		this.enchantability = par3;
		
	}

	public final int getDurability(final int par1)
	{
		
		return ItemArmorControl.maxDamageArray[par1] * this.maxDamageFactor;
		
	}

	public final int getDamageReductionAmount(final int par1)
	{
		
		return this.damageReductionAmountArray[par1];
		
	}

	public final int getEnchantability()
	{
		
		return this.enchantability;
		
	}

}
