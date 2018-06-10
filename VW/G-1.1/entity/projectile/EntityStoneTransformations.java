package vw.entity.projectile;

import static net.minecraft.init.Blocks.*;
import static vw.VirtualWorld.*;
import vw.VirtualWorld;
import vw.entity.monster.EntityHerobrine;
import vw.entity.passive.EntityHuman;
import vw.util.DataCalculations;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityStoneTransformations extends EntityThrowable
{
	
	public static final byte SCAN = 1;
	
	protected final boolean give;
	
	public EntityStoneTransformations(final World par1World, final EntityLivingBase par2EntityLiving)
	{
		
		super(par1World, par2EntityLiving);
		
		this.give = true;
		
	}
	
	public EntityStoneTransformations(final World par1World, final double par2, final double par3, final double par4)
	{
		
		super(par1World, par2, par3, par4);
		
		this.give = false;
		
	}
	
	@Override
	protected void onImpact(final MovingObjectPosition par1MovingObjectPosition)
	{
		
		for (int i = 0; i < 8; ++i)
			this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, Item.getIdFromItem(VirtualWorld.stoneTransformations));
		
		boolean mode = true;
		
		if(this.getThrower() instanceof EntityPlayer)
			mode = !(((EntityPlayer) this.getThrower()).capabilities.isCreativeMode);
		
		if(!this.replaces(par1MovingObjectPosition) && this.give && mode)
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(VirtualWorld.stoneTransformations)));
		
		if(!this.worldObj.isRemote)
			this.setDead();
		
	}
	
	protected boolean replaces(final MovingObjectPosition par1MovingObjectPosition)
	{
		
		if(par1MovingObjectPosition.entityHit != null)
		{
			
			if(this.replaceEntity(	EntityHerobrine.class,	new EntityHuman(this.worldObj),	par1MovingObjectPosition	))	return true;
			
		}
		else
		{
			
			if(this.replaceBlock(	grass,							mycelium,																						par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	dirt,							grass,																							par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	stone,							stonebrick,																						par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	cobblestone,					stone,																							par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	sandstone,					0,	sandstone,				DataCalculations.giveRandomNumber(1, 2),								par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	sand,							sandstone,																						par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	quartz_block,				0,	quartz_block,			DataCalculations.giveRandomNumber(1, 2),								par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	piston,							sticky_piston,																					par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	rainbowBlock,					improvedRainbowBlock,																			par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	aquamarineBlock,				magicBlock,																						par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	aquamarineDamagedBlock,		1,	aquamarineBlock,		0,																		par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	aquamarineDamagedBlock,		0,	aquamarineDamagedBlock,	1,																		par1MovingObjectPosition	))	return true;
			if(this.replaceBlock(	diamond_block,				0,	aquamarineDamagedBlock,	this.rand.nextBoolean() ? 0 : DataCalculations.giveRandomNumber(0, 1),	par1MovingObjectPosition	))	return true;
			
		}
		
		return false;
		
	}
	
	protected final boolean replaceBlock(final Block par1Block, final Block par2Block, final MovingObjectPosition par3MovingObjectPosition)
	{
		
		return this.replaceBlock(par1Block, 0, par2Block, 0, par3MovingObjectPosition);
		
	}
	
	protected boolean replaceBlock(final Block par1Block, final int par2, final Block par3Block, final int par4, final MovingObjectPosition par5MovingObjectPosition)
	{
		
		final BlockPos position = par5MovingObjectPosition.getBlockPos();
		
		final IBlockState oldBlockState = this.worldObj.getBlockState /* getBlock */ (position);
		final Block oldBlock = oldBlockState.getBlock();
		final int oldBlockMetadata = oldBlock.getMetaFromState(oldBlockState);
		final Block newBlock = par3Block;
		final int newBlockMetadata = par4;
		
		if(oldBlock == par1Block && oldBlockMetadata == par2)
		{
			
			this.worldObj.setBlockState /* setBlock */ (position, newBlock.getStateFromMeta(newBlockMetadata));
			
			return true;
			
		}
		
		return false;
		
	}
	
	protected boolean replaceEntity(final Class<? extends Entity> par1Class, final Entity par2Entity, final MovingObjectPosition par3MovingObjectPosition)
	{
		
		final Entity oldEntity = par3MovingObjectPosition.entityHit;
		final Class<? extends Entity> oldEntityClass = oldEntity.getClass();
		final Entity newEntity =  par2Entity;
		
		if(oldEntityClass == par1Class)
		{
			
			oldEntity.setDead();
			
			newEntity.setLocationAndAngles(oldEntity.posX, oldEntity.posY, oldEntity.posZ, oldEntity.rotationYaw, oldEntity.rotationPitch);
			this.worldObj.spawnEntityInWorld(newEntity);
			
			return true;
			
		}
		
		return false;
		
	}

}
