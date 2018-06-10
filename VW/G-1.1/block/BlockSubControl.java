package vw.block;

import java.util.List;
import javax.jnf.lwjgl.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class BlockSubControl extends Block
{
	
	public static final byte SCAN = 1;
	
	private final byte amountSubblocks;
	
	public BlockSubControl(final Material par1Material, final byte amountSubblocks)
	{
		
		super(par1Material);
		
		this.amountSubblocks = amountSubblocks > 0 && amountSubblocks <= Util.givePowerOfTwo(4) ? amountSubblocks : 1;
		
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubBlocks(final Item par1Item, final CreativeTabs par2CreativeTabs, final List par3List)
	{
		
		for(int i = 0; i < this.giveAmountSubblocks(); i++)
			par3List.add(new ItemStack(par1Item, 1, i));
		
	}
	
	public final byte giveAmountSubblocks()
	{
		
		return this.amountSubblocks;
		
	}
	
}
