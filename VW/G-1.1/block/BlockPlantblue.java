package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockPlantblue extends BlockFlowerControl
{
	
	public static final byte SCAN = 1;
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.plantBlue);
		
	}

}
