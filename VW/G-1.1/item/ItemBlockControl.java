package vw.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public abstract class ItemBlockControl extends ItemBlock
{

	public static final byte SCAN = 1;
	
	public ItemBlockControl(final Block par1Block)
	{
		
		super(par1Block);
		this.setHasSubtypes(true);
		
	}
	
	@Override
	public int getMetadata(final int par1)
	{
		
		return par1;
		
	}
	
	public final Block giveBlock()
	{
		
		return this.block;
		
	}
	
}
