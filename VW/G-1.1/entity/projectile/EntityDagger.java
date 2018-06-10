package vw.entity.projectile;

import vw.VirtualWorld;
import vw.item.ItemDaggerControl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDagger extends EntityThrowable
{
	
	public static final byte SCAN = 1;
	
	protected final byte power;
	protected final ItemDaggerControl dagger;
	
	public EntityDagger(final World par1World, final EntityLivingBase par2EntityLiving, final byte par3, final ItemDaggerControl par4ItemDaggerControl)
	{
		
		super(par1World, par2EntityLiving);
		this.power = par3;
		this.dagger = par4ItemDaggerControl;
		
	}
	
	public EntityDagger(final World par1World, final double par2, final double par3, final double par4, final byte par5, final ItemDaggerControl par6ItemDaggerControl)
	{
		
		super(par1World, par2, par3, par4);
		this.power = par5;
		this.dagger = par6ItemDaggerControl;
		
	}
	
	public EntityDagger(final World par1World, final double par2, final double par3, final double par4)
	{
		
		super(par1World, par2, par3, par4);
		this.power = 0;
		this.dagger = (ItemDaggerControl) VirtualWorld.daggerModel; 
		
	}

	@Override
	protected void onImpact(final MovingObjectPosition par1MovingObjectPosition)
	{
		
		for(int i = 0; i < 8; ++i)
			this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, Item.getIdFromItem(this.dagger));
		
		boolean mode = true;
		
		if(this.getThrower() instanceof EntityPlayer)
			mode = !(((EntityPlayer) this.getThrower()).capabilities.isCreativeMode);
		
		if(par1MovingObjectPosition.entityHit != null)
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.power);
		else if(this.dagger != VirtualWorld.daggerModel && mode)
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(this.dagger)));

		if(!this.worldObj.isRemote)
			this.setDead();
		
	}
	
}
