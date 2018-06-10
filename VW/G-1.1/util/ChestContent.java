package vw.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ChestContent
{
	
	public static final byte SCAN = 1;
	
	protected final World world;
	protected final int x;
	protected final int y;
	protected final int z;
	protected final Block block;
	protected final int metadata;
	protected final ItemStack[][] items;
	
	public ChestContent(final World world, final int x, final int y, final int z, final int metadata)
	{
		
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = Blocks.chest;
		this.metadata = metadata;
		this.items = new ItemStack[3][9];
		
	}
	
	public ChestContent(final World world, final int x, final int y, final int z)
	{
		
		this(world, x, y, z, 0);
		
	}
	
	private TileEntityChest entity;
	
	public ChestContent addItem(final Item item, final int metadata, final int amount, final int positionX, final int positionY)
	{
		
		this.items[positionY][positionX] = new ItemStack(item, amount, metadata);
		return this;
		
	}
	
	public final ChestContent addItem(final Item item, final int amount, final int positionX, final int positionY)
	{
		
		return this.addItem(item, 0, amount, positionX, positionY);
		
	}
	
	public final ChestContent addItem(final Item item, final int positionX, final int positionY)
	{
		
		return this.addItem(item, 1, positionX, positionY);
		
	}
	
	public ChestContent delItem(final int positionX, final int positionY)
	{
		
		this.items[positionY][positionX] = null;
		return this;
		
	}
	
	public int createChest()
	{
		
		BlockPos position = new BlockPos(this.x, this.y, this.z);
		this.world.setBlockState /* setBlock */ (position, this.block.getStateFromMeta(this.metadata));
		this.entity = (TileEntityChest) this.world.getTileEntity /* getBlockTileEntity */ (position);
		
		int result = 0;
		
		for(int i = 0; i < this.items.length; i++)
			for(int j = 0; j < this.items[i].length; j++)
				if(this.items[i][j] != null)
					if(this.items[i][j].getItem() != Item.getItemFromBlock(Blocks.air))
					{
						
						this.entity.setInventorySlotContents(i * 9 + j, this.items[i][j]);
						
						result++;
						
					}
		
		return result;
		
	}
	
}
