package vw.block;

import java.util.List;
import java.util.Random;
import javax.jnf.lwjgl.Point3D;
import vw.VirtualWorld;
import vw.tileentity.TileEntitySunshade;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSunshade extends BlockSubControl implements ITileEntityProvider
{
	
	public static final byte SCAN = 1;
	
	public BlockSunshade()
	{
	
		super(Material.wood, (byte) 6);
		this.isBlockContainer = true;
		
		this.metadataIcon = -1;
		
	}
	
	public int metadataIcon;
	
	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final BlockPos par2BlockPos)
	{
		
		switch(this.getMetaFromState(par1IBlockAccess.getBlockState /* getBlockMetadata */ (par2BlockPos)))
		{
			
			case 0:
				this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
				break;
			default:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			
		}
		
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addCollisionBoxesToList(final World par1World, final BlockPos par2BlockPos, final IBlockState par3IBlockState, final AxisAlignedBB par4AxisAlignedBB, final List par5List, final Entity par6Entity)
	{
		
		final int x = par2BlockPos.getX(), y = par2BlockPos.getY(), z = par2BlockPos.getZ();
		
		switch(this.getMetaFromState(par1World.getBlockState /* getBlockMetadata */ (par2BlockPos)))
		{

			case 1:
				this.doAddAABB(new Point3D(x, y + 0.9375F, z), new Point3D(x + 1.0F, y + 1.0F, z + 1.0F), par5List, par4AxisAlignedBB);
			case 0:
				this.doAddAABB(new Point3D(x + 0.375F, y, z + 0.375F), new Point3D(x + 0.625F, y + 1.0F, z + 0.625F), par5List, par4AxisAlignedBB);
				break;
			default:
				this.doAddAABB(new Point3D(x, y, z), par5List, par4AxisAlignedBB);
			
		}
		
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		
		return false;
		
	}
	
	@Override
	public boolean isFullCube() // renderAsNormalBlock
	{
		
		return false;
		
	}
	
	@Override
	public TileEntity createNewTileEntity(final World par1World, final int par2)
	{
		
		if(par2 > 5)
			return new TileEntitySunshade();
		else
			return null;
		
	}
	
	@Override
	public void onBlockHarvested(final World par1World, final BlockPos par2BlockPos, final IBlockState par3IBlockState, final EntityPlayer par2EntityPlayer)
	{
		
		final int x = par2BlockPos.getX(), y = par2BlockPos.getY(), z = par2BlockPos.getZ();
		
		final Point3D[] positions = new Point3D[7];
		
		switch(this.getMetaFromState(par3IBlockState))
		{
			
			case 0:
			{
				
				IBlockState state = par1World.getBlockState(new BlockPos(x, y + 1, z));
				
				if(state.getBlock() == this && this.getMetaFromState(state) == 0)
				{
					
					positions[0] = new Point3D(x, y, z);
					positions[1] = new Point3D(x, y + 1, z);
					positions[2] = new Point3D(x, y + 2, z);
					positions[3] = new Point3D(x, y + 2, z - 1);
					positions[4] = new Point3D(x + 1, y + 2, z);
					positions[5] = new Point3D(x, y + 2, z + 1);
					positions[6] = new Point3D(x - 1, y + 2, z);
					
				}
				else
				{
					
					positions[0] = new Point3D(x, y - 1, z);
					positions[1] = new Point3D(x, y, z);
					positions[2] = new Point3D(x, y + 1, z);
					positions[3] = new Point3D(x, y + 1, z - 1);
					positions[4] = new Point3D(x + 1, y + 1, z);
					positions[5] = new Point3D(x, y + 1, z + 1);
					positions[6] = new Point3D(x - 1, y + 1, z);
					
				}
				
				break;
				
			}
			
			case 1:
			{
				
				positions[0] = new Point3D(x, y - 2, z);
				positions[1] = new Point3D(x, y - 1, z);
				positions[2] = new Point3D(x, y, z);
				positions[3] = new Point3D(x, y, z - 1);
				positions[4] = new Point3D(x + 1, y, z);
				positions[5] = new Point3D(x, y, z + 1);
				positions[6] = new Point3D(x - 1, y, z);
				
				break;
				
			}
			
			case 2:
			{
				
				positions[0] = new Point3D(x, y - 2, z + 1);
				positions[1] = new Point3D(x, y - 1, z + 1);
				positions[2] = new Point3D(x, y, z + 1);
				positions[3] = new Point3D(x, y, z);
				positions[4] = new Point3D(x + 1, y, z + 1);
				positions[5] = new Point3D(x, y, z + 2);
				positions[6] = new Point3D(x - 1, y, z + 1);
				
				break;
				
			}
			
			case 3:
			{
				
				positions[0] = new Point3D(x - 1, y - 2, z);
				positions[1] = new Point3D(x - 1, y - 1, z);
				positions[2] = new Point3D(x - 1, y, z);
				positions[3] = new Point3D(x - 1, y, z - 1);
				positions[4] = new Point3D(x, y, z);
				positions[5] = new Point3D(x - 1, y, z + 1);
				positions[6] = new Point3D(x - 2, y, z);
				
				break;
				
			}
			
			case 4:
			{
				
				positions[0] = new Point3D(x, y - 2, z - 1);
				positions[1] = new Point3D(x, y - 1, z - 1);
				positions[2] = new Point3D(x, y, z - 1);
				positions[3] = new Point3D(x, y, z - 2);
				positions[4] = new Point3D(x + 1, y, z - 1);
				positions[5] = new Point3D(x, y, z);
				positions[6] = new Point3D(x - 1, y, z - 1);
				
				break;
				
			}
			
			case 5:
			{
				
				positions[0] = new Point3D(x + 1, y - 2, z);
				positions[1] = new Point3D(x + 1, y - 1, z);
				positions[2] = new Point3D(x + 1, y, z);
				positions[3] = new Point3D(x + 1, y, z - 1);
				positions[4] = new Point3D(x + 2, y, z);
				positions[5] = new Point3D(x + 1, y, z + 1);
				positions[6] = new Point3D(x, y, z);
				
				break;
				
			}
			
		}
		
		for(final Point3D position : positions)
			if(position != null)
			{
				
				final BlockPos pos = new BlockPos(position.getX(), position.getY(), position.getZ());
				
				if(par1World.getBlockState /* getBlock */ (pos).getBlock() == this)
					par1World.setBlockToAir(pos);
				
			}
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return VirtualWorld.sunshade;
		
	}
	
	@Override
	public Item getItem(World par1World, final BlockPos par2BlockPos)
	{
		
		return VirtualWorld.sunshade;
		
	}
	
	protected final boolean doAddAABB(final Point3D par1Point3D, final Point3D par2Point3D, final List<AxisAlignedBB> par3List, final AxisAlignedBB par4AxisAlignedBB)
	{
		
		final AxisAlignedBB aabb = new AxisAlignedBB(par1Point3D.getX(), par1Point3D.getY(), par1Point3D.getZ(), par2Point3D.getX(), par2Point3D.getY(), par2Point3D.getZ());
		
		if(par4AxisAlignedBB.intersectsWith(aabb))
		{
			
			par3List.add(aabb);
			return true;
			
		}
		else
			return false;
		
	}
	
	protected final boolean doAddAABB(final Point3D par1Point3D, final List<AxisAlignedBB> par2List, final AxisAlignedBB par3AxisAlignedBB)
	{
		
		return this.doAddAABB(par1Point3D, new Point3D(par1Point3D.getX() + 1.0F, par1Point3D.getY() + 1.0F, par1Point3D.getZ() + 1.0F), par2List, par3AxisAlignedBB);
		
	}
	
}
