package vw.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockSoftControl extends Block
{
	
	public static final byte SCAN = 1;
	
	protected boolean penetration;
	
	public BlockSoftControl(final Material par1Material)
	{
		
		super(par1Material);
		
		this.penetration = false;
		
	}

	@Override
	public void onEntityCollidedWithBlock(final World par1World, final BlockPos par2BlockPos, final Entity par3Entity)
	{
		
		par3Entity.fallDistance = 0.0F;
		
		par3Entity.motionX *= 0.01D;
		par3Entity.motionZ *= 0.01D;
		
		if(par3Entity.motionY < 0.0D)
			par3Entity.motionY *= 0.01D;
		
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState) // getCollisionBoundingBoxFromPool
	{
		
		final int x = par2BlockPos.getX(), y = par2BlockPos.getY(), z = par2BlockPos.getZ();
		return AxisAlignedBB.fromBounds /* getBoundingBox */ (x, y, z, x + (this.penetration ? 0 : 1), y, z + (this.penetration ? 0 : 1));
		
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
	
}
