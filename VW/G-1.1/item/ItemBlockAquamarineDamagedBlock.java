package vw.item;

import vw.block.BlockAquamarineDamagedBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockAquamarineDamagedBlock extends ItemBlockControl
{
	
	public static final byte SCAN = 1;
	
	protected final String[] names;

	public ItemBlockAquamarineDamagedBlock(final Block par1Block)
	{
		
		super(par1Block);
		
		this.names = ((BlockAquamarineDamagedBlock) par1Block).names;
		
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		
		return this.getUnlocalizedName() + "." + this.names[this.getDamage(par1ItemStack)];
		
	}


}
