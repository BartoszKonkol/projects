package vw.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDaggerLightning extends EntityThrowable
{
	
	public static final byte SCAN = 1;
	
	public EntityDaggerLightning(final World par1World, final EntityLivingBase par2EntityLivingBase)
	{
		
		super(par1World, par2EntityLivingBase);
		
	}
	
	public EntityDaggerLightning(final World par1World, final double par2, final double par3, final double par4)
	{
		
		super(par1World, par2, par3, par4);
		
	}

	@Override
	protected void onImpact(final MovingObjectPosition par1MovingObjectPosition)
	{

		for(int i = 0; i < 8; ++i)
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		
		if(par1MovingObjectPosition.entityHit != null)
		{
			
			int positionX = MathHelper.floor_double(par1MovingObjectPosition.entityHit.getEntityBoundingBox().minX);
			int positionY = MathHelper.floor_double(par1MovingObjectPosition.entityHit.getEntityBoundingBox().minY);
			int positionZ = MathHelper.floor_double(par1MovingObjectPosition.entityHit.getEntityBoundingBox().minZ);
			
			final EntityLightningBolt entitylightningbolt = new EntityLightningBolt(this.worldObj, positionX, positionY, positionZ);
			this.worldObj.addWeatherEffect(entitylightningbolt);
			
		}
		else
		{
			
			final BlockPos position = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
			
			final EntityLightningBolt entitylightningbolt = new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ);
			this.worldObj.spawnEntityInWorld(entitylightningbolt);
			
			if(this.worldObj.getBlockState /* getBlock */ (position).getBlock() == Blocks.air && Blocks.fire.canPlaceBlockAt(this.worldObj, position))
				this.worldObj.setBlockState /* setBlock */ (position, Blocks.fire.getDefaultState());
			else
			{
				
				boolean combustible = false;
				boolean scale = true;
				
				for(int i = 0; !combustible && scale; i++)
				{
					
					scale = i < 256;
					position.up();
					
					if(this.worldObj.getBlockState /* getBlock */ (position) == Blocks.air && Blocks.fire.canPlaceBlockAt(this.worldObj, position))
					{
						
						this.worldObj.setBlockState /*setBlock */ (position, Blocks.fire.getDefaultState());
						
						combustible = true;
						
					}
					
				}
				
			}
			
		}

		if(!this.worldObj.isRemote)
			this.setDead();
		
	}
	
}
