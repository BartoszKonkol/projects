package vw.world.gen.structure;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import vw.VirtualWorld;
import vw.util.ChestContent;
import vw.util.DataCalculations;
import vw.world.gen.ChunkProviderDreamland;

public class StructureGreatHallGenerate extends StructuresNewGenerate
{
	
	public static final byte SCAN = 1;
	
	protected final int height;
	protected final boolean side0;
	protected final boolean side1;
	protected final boolean side2;
	protected final boolean side3;
	
	public StructureGreatHallGenerate(final ChunkProviderDreamland chunkProvider, final WorldProvider worldProvider, final Random seed)
	{
		
		this.setProvider(chunkProvider);
		
		final int cloud = MathHelper.ceiling_double_int(worldProvider.getCloudHeight());
		final int from = 32;
		final int width = 12;
		final int max = 256;
		this.height = (cloud + from + width) < max ? (cloud + from) : (cloud + width) < max ? (cloud) : (cloud / 2);
		
		this.side0 = seed.nextBoolean();
		this.side1 = seed.nextBoolean();
		this.side2 = seed.nextBoolean();
		this.side3 = seed.nextBoolean();
		
	}
	
	private ChunkProviderDreamland provider;
	
	protected final void setProvider(final ChunkProviderDreamland provider)
	{
		
		this.provider = provider;
		
	}
	
	protected final ChunkProviderDreamland getProvider()
	{
		
		return this.provider;
		
	}
	
	@Override
	protected void actionsStructure(final IBlockState[][][] blocks)
	{
		
		final IBlockState air = Blocks.air.getDefaultState();
		final IBlockState meltingSnow = VirtualWorld.meltingSnow.getDefaultState();
		final IBlockState planks = Blocks.planks.getDefaultState();
		final IBlockState emerald_block = Blocks.emerald_block.getDefaultState();
		final IBlockState beacon = Blocks.beacon.getDefaultState();
		final IBlockState glass = Blocks.glass.getDefaultState();
		final IBlockState whiteBlock = VirtualWorld.whiteBlock.getDefaultState();
		
		for(int i = 1; i < 15; i++)
			for(int j = 1; j < 15; j++)
				if(!(
				(i == 1 && j == 1) ||
				(i == 2 && j == 1) ||
				(i == 1 && j == 2) ||
				(i == 14 && j == 1) ||
				(i == 13 && j == 1) ||
				(i == 14 && j == 2) ||
				(i == 1 && j == 14) ||
				(i == 2 && j == 14) ||
				(i == 1 && j == 13) ||
				(i == 14 && j == 14) ||
				(i == 13 && j == 14) ||
				(i == 14 && j == 13)
				))
					blocks[i][this.height + 0][j] = meltingSnow;
		
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				if(!(
				(i == 0 && j == 0) ||
				(i == 1 && j == 0) ||
				(i == 0 && j == 1) ||
				(i == 15 && j == 0) ||
				(i == 14 && j == 0) ||
				(i == 15 && j == 1) ||
				(i == 0 && j == 15) ||
				(i == 1 && j == 15) ||
				(i == 0 && j == 14) ||
				(i == 15 && j == 15) ||
				(i == 14 && j == 15) ||
				(i == 15 && j == 14)
				))
					blocks[i][this.height + 1][j] = meltingSnow;
		
		for(int i = this.height + 2; i < this.height + 12; i++)
		{
			
			for(int j = 2; j < 14; j++)
			{
				
				blocks[j][i][2] = planks;
				blocks[2][i][j] = planks;
				blocks[j][i][13] = planks;
				blocks[13][i][j] = planks;
				
			}
			
			blocks[13][i][13] = planks;
			
		}
		
		for(int i = 3; i < 13; i++)
			for(int j = 3; j < 13; j++)
				for(int k = this.height + 3; k < this.height + 12; k++)
					blocks[i][k][j] = air;
		
		for(int i = 2; i < 14; i++)
			for(int j = 2; j < 14; j++)
				for(int k = this.height + 2; k < this.height + 13; k = k+5)
					blocks[i][k][j] = planks;
		
		if(this.side0)
		{
			
			blocks[2][this.height + 3][7] = air;
			blocks[2][this.height + 4][7] = air;
			blocks[2][this.height + 3][8] = air;
			blocks[2][this.height + 4][8] = air;
			
			for(int i = 6; i < 10; i++)
				blocks[1][this.height + 2][i] = air;
			
		}
		
		if(this.side1)
		{
			
			blocks[7][this.height + 3][2] = air;
			blocks[7][this.height + 4][2] = air;
			blocks[8][this.height + 3][2] = air;
			blocks[8][this.height + 4][2] = air;
			
			for(int i = 6; i < 10; i++)
				blocks[i][this.height + 2][1] = air;
			
		}
		
		if(this.side2)
		{
			
			blocks[13][this.height + 3][7] = air;
			blocks[13][this.height + 4][7] = air;
			blocks[13][this.height + 3][8] = air;
			blocks[13][this.height + 4][8] = air;
			
			for(int i = 6; i < 10; i++)
				blocks[14][this.height + 2][i] = air;
			
		}
		
		if(this.side3)
		{
			
			blocks[7][this.height + 3][13] = air;
			blocks[7][this.height + 4][13] = air;
			blocks[8][this.height + 3][13] = air;
			blocks[8][this.height + 4][13] = air;
			
			for(int i = 6; i < 10; i++)
				blocks[i][this.height + 2][14] = air;
			
		}
		
		blocks[3][this.height + 7][3] = air;
		blocks[12][this.height + 7][3] = air;
		blocks[3][this.height + 7][12] = air;
		blocks[12][this.height + 7][12] = air;
		
		for(int i = 6; i < 10; i++)
			for(int j = 6; j < 10; j++)
				blocks[i][this.height + 8][j] = emerald_block;
		
		for(int i = 7; i < 9; i++)
			for(int j = 7; j < 9; j++)
			{
				
				blocks[i][this.height + 9][j] = beacon;
				blocks[i][this.height + 12][j] = glass;
				
			}
		
		blocks[3][this.height + 3][3] = whiteBlock;
		blocks[12][this.height + 3][3] = whiteBlock;
		blocks[3][this.height + 3][12] = whiteBlock;
		blocks[12][this.height + 3][12] = whiteBlock;
		blocks[3][this.height + 11][3] = whiteBlock;
		blocks[12][this.height + 11][3] = whiteBlock;
		blocks[3][this.height + 11][12] = whiteBlock;
		blocks[12][this.height + 11][12] = whiteBlock;
		
	}

	@Override
	protected void actionsDecoration(final World world, final int chunkX, final int chunkZ)
	{
		
		final int x = chunkX * 16;
		final int z = chunkZ * 16;
		
		if(this.side0)
		{
			
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 2, this.height + 3, z + 7), EnumFacing.getFront(0), Blocks.oak_door);
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 2, this.height + 3, z + 8), EnumFacing.getFront(0), Blocks.oak_door);
			
			for(int i = 7; i < 9; i++)
				world.setBlockState /* setBlock */ (new BlockPos(x + 1, this.height + 2, z + i), Blocks.oak_stairs.getStateFromMeta(0));
			
			world.setBlockState /* setBlock */ (new BlockPos(x + 1, this.height + 2, z + 6), Blocks.oak_stairs.getStateFromMeta(2));
			world.setBlockState /* setBlock */ (new BlockPos(x + 1, this.height + 2, z + 9), Blocks.oak_stairs.getStateFromMeta(3));
			
			new ChestContent(world, x + 5, this.height + 8, z + 7)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			new ChestContent(world, x + 5, this.height + 8, z + 8)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			
		}
		
		if(this.side1)
		{
			
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 7, this.height + 3, z + 2), EnumFacing.getFront(1), Blocks.oak_door);
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 8, this.height + 3, z + 2), EnumFacing.getFront(1), Blocks.oak_door);
			
			for(int i = 7; i < 9; i++)
				world.setBlockState /* setBlock */ (new BlockPos(x + i, this.height + 2, z + 1), Blocks.oak_stairs.getStateFromMeta(2));
			
			world.setBlockState /* setBlock */ (new BlockPos(x + 6, this.height + 2, z + 1), Blocks.oak_stairs.getStateFromMeta(0));
			world.setBlockState /* setBlock */ (new BlockPos(x + 9, this.height + 2, z + 1), Blocks.oak_stairs.getStateFromMeta(1));
			
			new ChestContent(world, x + 7, this.height + 8, z + 5)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			new ChestContent(world, x + 8, this.height + 8, z + 5)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			
		}
		
		if(this.side2)
		{
			
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 13, this.height + 3, z + 7), EnumFacing.getFront(2), Blocks.oak_door);
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 13, this.height + 3, z + 8), EnumFacing.getFront(2), Blocks.oak_door);
			
			for(int i = 7; i < 9; i++)
				world.setBlockState /* setBlock */ (new BlockPos(x + 14, this.height + 2, z + i), Blocks.oak_stairs.getStateFromMeta(1));
			
			world.setBlockState /* setBlock */ (new BlockPos(x + 14, this.height + 2, z + 6), Blocks.oak_stairs.getStateFromMeta(2));
			world.setBlockState /* setBlock */ (new BlockPos(x + 14, this.height + 2, z + 9), Blocks.oak_stairs.getStateFromMeta(3));
			
			new ChestContent(world, x + 10, this.height + 8, z + 7)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			new ChestContent(world, x + 10, this.height + 8, z + 8)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			
		}
		
		if(this.side3)
		{
			
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 7, this.height + 3, z + 13), EnumFacing.getFront(3), Blocks.oak_door);
			ItemDoor.placeDoor /* placeDoorBlock */ (world, new BlockPos(x + 8, this.height + 3, z + 13), EnumFacing.getFront(3), Blocks.oak_door);
			
			for(int i = 7; i < 9; i++)
				world.setBlockState /* setBlock */ (new BlockPos(x + i, this.height + 2, z + 14), Blocks.oak_stairs.getStateFromMeta(3));
			
			world.setBlockState /* setBlock */ (new BlockPos(x + 6, this.height + 2, z + 14), Blocks.oak_stairs.getStateFromMeta(0));
			world.setBlockState /* setBlock */ (new BlockPos(x + 9, this.height + 2, z + 14), Blocks.oak_stairs.getStateFromMeta(1));
			
			new ChestContent(world, x + 7, this.height + 8, z + 10)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			new ChestContent(world, x + 8, this.height + 8, z + 10)
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.addItem(VirtualWorld.crystalCosmos, DataCalculations.giveRandomNumber(0, 8), DataCalculations.giveRandomNumber(0, 2))
				.createChest();
			
		}
		
		for(int i = 0; i < 7; i++)
		{
			
			world.setBlockState /* setBlock */ (new BlockPos(x + 3, this.height + 4 + i, z + 3), Blocks.ladder.getStateFromMeta(3));
			world.setBlockState /* setBlock */ (new BlockPos(x + 12, this.height + 4 + i, z + 3), Blocks.ladder.getStateFromMeta(3));
			world.setBlockState /* setBlock */ (new BlockPos(x + 3, this.height + 4 + i, z + 12), Blocks.ladder.getStateFromMeta(2));
			world.setBlockState /* setBlock */ (new BlockPos(x + 12, this.height + 4 + i, z + 12), Blocks.ladder.getStateFromMeta(2));
			
		}
		
	}
	
}
