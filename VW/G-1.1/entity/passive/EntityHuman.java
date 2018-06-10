package vw.entity.passive;

import com.google.common.base.Predicate;
import vw.VirtualWorld;
import vw.entity.monster.EntityHerobrine;
import vw.util.DataCalculations;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityHuman extends EntityAnimal
{
	
	public static final byte SCAN = 1;
	
	@SuppressWarnings("rawtypes")
	public EntityHuman(final World par1World) 
	{
		
		super(par1World);
		
		this.setSize(this.width, this.height);
		
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.4F));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, new Predicate(){@Override public boolean apply(Object par1Object){return par1Object instanceof EntityCreeper;}}, 8F, 0.3D, 0.35D));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate(){@Override public boolean apply(Object par1Object){return par1Object instanceof EntityZombie;}}, 8F, 0.3D, 0.35D));
		this.tasks.addTask(4, new EntityAIAvoidEntity(this, new Predicate(){@Override public boolean apply(Object par1Object){return par1Object instanceof EntityHerobrine;}}, 8F, 0.3D, 0.35D));
		this.tasks.addTask(5, new EntityAIMoveIndoors(this));
		this.tasks.addTask(6, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(7, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(8, new EntityAIWander(this, 0.25F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(10, new EntityAITempt(this, 0.35F, VirtualWorld.wateryDiamond, false));
		this.tasks.addTask(11, new EntityAILookIdle(this));
		
		this.setDeathLightning(true);
		
	}
	
	private boolean deathLightning;
	
	public final void setDeathLightning(final boolean par1)
	{
		
		this.deathLightning = par1;
		
	}
	
	public final boolean getDeathLightning()
	{
		
		return this.deathLightning;
		
	}
				
	@Override
	protected boolean canDespawn()
	{
		
		return false;
		
	}
	
	@Override
	public void onDeath(final DamageSource par1DamageSource)
	{
		
		this.entityDropItem(new ItemStack(VirtualWorld.wateryDiamondDust), 1);
		this.entityDropItem(new ItemStack(Items.apple), 1);
		this.entityDropItem(new ItemStack(VirtualWorld.blood), 3);
		
		if(DataCalculations.random.nextInt(10) == 0)
			this.spawnHerobrine();
		else
		{
			
			this.entityDropItem(new ItemStack(VirtualWorld.brain), DataCalculations.giveRandomNumber(0, 1));
			
			for (int i = 0; i < DataCalculations.random.nextInt(2); ++i)
				this.entityDropItem(new ItemStack(VirtualWorld.wateryDiamondDust), DataCalculations.giveRandomNumber(1, 2));
			
			for (int i = 0; i < DataCalculations.random.nextInt(2); ++i)
				this.entityDropItem(new ItemStack(Items.apple), DataCalculations.giveRandomNumber(1, 4));
			
			for (int i = 0; i < DataCalculations.random.nextInt(2); ++i)
				this.entityDropItem(new ItemStack(Items.bread), DataCalculations.giveRandomNumber(1, 3));
			
			for (int i = 0; i < DataCalculations.random.nextInt(2); ++i)
				this.entityDropItem(new ItemStack(VirtualWorld.blood), DataCalculations.giveRandomNumber(1, 21));
			
		}
		
	}
	
	@Override
	public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) 
	{
		
		return null;
		
	}
	
	@Override
	public void onStruckByLightning(final EntityLightningBolt par1EntityLightningBolt)
	{
		
		if(this.getDeathLightning())
		{
			
			this.spawnHerobrine();
			this.setDead();
			
		}
		else
			super.onStruckByLightning(par1EntityLightningBolt);
		
	}
	
	protected void spawnHerobrine()
	{
		
		this.playSound("mob.herobrine.hurt", 1.0F, 1.0F);
		
		final EntityHerobrine entityherobrine = new EntityHerobrine(this.worldObj);
		entityherobrine.setLocationAndAngles(this.posX, this.posY + 3, this.posZ, this.rotationYaw, this.rotationPitch);
		this.worldObj.spawnEntityInWorld(entityherobrine);
		
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue /* setAttribute */ (15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue /* setAttribute */ (1.005D);
		
	}

}

