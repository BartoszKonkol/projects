package vw.entity.monster;

import java.util.ArrayList;
import java.util.List;
import javax.JNF;
import vw.VirtualWorld;
import vw.entity.passive.EntityHuman;
import vw.util.DataCalculations;
import vw.util.ThreadSub;
import vw.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityHerobrine extends EntityMob
{
	
	public static final byte SCAN = 1;
	
	public EntityHerobrine(final World par1World) 
	{
		
		super(par1World);
		
		this.setSize(this.width, this.height);
		
		this.isImmuneToFire = true;
		
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIMoveIndoors(this));
		this.tasks.addTask(2, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(4, new EntityAIWander(this, 0.25F));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.tasks.addTask(7, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.4F, false));
		this.tasks.addTask(8, new EntityAIAttackOnCollide(this, EntityHuman.class, 0.4F, true));
		
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityHuman.class, false));
		
		this.messagesPreface.add("HaHaHa");
		this.messagesPreface.add("Boisz siê?!");
		this.messagesPreface.add("Giñ! Zgiñ!");
		this.messagesPreface.add("Widzê Ciê!");
		this.messagesPreface.add("Creeper to nic!");
		
		if (DataCalculations.random.nextInt(5) == 0)
			this.chatMessage("Ja jestem Herobrine! B\u00f3j si\u0119! Zaatakuje Ci\u0119, kiedy si\u0119 b\u0119dziesz tego najmniej spodziewa\u0142!");
		
	}
	
	protected final List<String> messagesPreface = new ArrayList<String>();
	
	public static boolean remove = Boolean.valueOf(JNF.giveFiles().vaLoadReturn(Minecraft.getMinecraft().mcDataDir + "\\vw\\settings.va", "RemoveHerobrine").toString());
	
	@Override
	protected String getHurtSound()
	{
		
		return "mob.herobrine.hurt";
		
	}
	
	@Override
	protected String getDeathSound()
	{
		
		return "mob.herobrine.hurt";
		
	}
	
	@Override
	protected void playStepSound(final BlockPos par1BlockPos, final Block par2Block)
	{
		
		this.playSound("mob.herobrine.step", 0.4F, 1.0F);
		
	}
	
	@Override
	public void onDeath(final DamageSource par1DamageSource)
	{
		
		this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ));
		
		this.chatMessage("Mnie si\u0119 nie da zabi\u0107! Ja si\u0119 odradzam!");
		
		final EntityHuman entityhuman = new EntityHuman(this.worldObj);
		final EntityHerobrine entityherobrine = new EntityHerobrine(this.worldObj);
		entityhuman.setDeathLightning(false);
		entityhuman.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		entityherobrine.setLocationAndAngles(this.posX + DataCalculations.giveRandomNumber(100, 200), 129, this.posZ + DataCalculations.giveRandomNumber(100, 200), this.rotationYaw, this.rotationPitch);
		this.worldObj.spawnEntityInWorld(entityhuman);
		this.worldObj.spawnEntityInWorld(entityherobrine);
		
		new EffectWheelsLightning(this.worldObj, this.posX, this.posY, this.posZ).enable();
		
	}
	
	@Override
	public void fall(final float par1, final float par2) 
	{
		
		;
		
	}
	
	@Override
	public void onLivingUpdate()
	{
		
		if (DataCalculations.random.nextInt(5000) == 0)
			this.chatMessage("B\u00f3j si\u0119! Jestem w pobli\u017cu i Ci\u0119 obserwuje!");
		
		super.onLivingUpdate();
		
		this.motionY += 0.06F;
		
		this.particle(EnumParticleTypes.SMOKE_LARGE, 0.1D);
		this.particle(EnumParticleTypes.PORTAL);
		this.particle(EnumParticleTypes.WATER_SPLASH);
		this.particle(EnumParticleTypes.WATER_SPLASH);
		this.particle(EnumParticleTypes.FLAME, 0.1D);
		this.particle(EnumParticleTypes.CRIT);
		this.particle(EnumParticleTypes.CRIT_MAGIC);
		this.particle(EnumParticleTypes.ENCHANTMENT_TABLE);
		this.particle(EnumParticleTypes.DRIP_LAVA, 0.0D);
		this.particle(EnumParticleTypes.DRIP_LAVA, 0.0D);
		this.particle(EnumParticleTypes.DRIP_LAVA, 0.0D);
		
		this.doRemove();
		
	}
	
	@Override
	public boolean attackEntityAsMob(final Entity par1Entity)
	{
		
		if (super.attackEntityAsMob(par1Entity))
		{
			
			this.chatMessage("Do ataku armio Herobrine!");
			
			this.playSound("mob.herobrine.attack", 1.0F, 1.0F);

			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 2));
			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 40, 0));
			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 1200, 1));
			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 0));
			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 60, 1));
			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 2));
			
			return true;
			
		}
		
		else
		{
			
			if (DataCalculations.random.nextInt(10) == 0)
				this.chatMessage("Zabije Ci\u0119! Ju\u017c mi nie uciekniesz!");
			
			return false;
			
		}
		
	}
	
	protected final void particle(final EnumParticleTypes particle)
	{
		
		this.particle(particle, this.rand.nextDouble());
		
	}
	
	protected void particle(final EnumParticleTypes particle, final double variable)
	{
		
		this.worldObj.spawnParticle(particle, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + variable * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
		
	}
	
	protected void chatMessage(final String message)
	{
		
		Util.doPrintChat("entity.Herobrine.message", "\u00A77(" + this.messagesPreface.get(DataCalculations.random.nextInt(this.messagesPreface.size())) + ")\u00A77", message);
		
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue /* setAttribute */ (50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue /* setAttribute */ (1.01D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue /* setAttribute */ (this.rand.nextInt((int) 5.0D) + 5.0D);
		
	}
	
	@Override
	public void onStruckByLightning(final EntityLightningBolt par1EntityLightningBolt)
	{
		
		super.onStruckByLightning(par1EntityLightningBolt);
		this.entityDropItem(new ItemStack(VirtualWorld.magic, 1, 0), 1);
		
	}
	
	protected void doRemove()
	{
		
		if(this.remove)
			this.worldObj.removeEntity(this);
		
	}
	
	protected class EffectWheelsLightning extends ThreadSub
	{
		
		protected final World world;
		protected final double positionX;
		protected final double positionY;
		protected final double positionZ;
		
		public EffectWheelsLightning(final World par1World, final double par2, final double par3, final double par4)
		{
			
			this.world = par1World;
			this.positionX = par2;
			this.positionY = par3;
			this.positionZ = par4;
			
		}
		
		@Override
		public void action()
		{
			
			this.doProcedureLightning(	-10,	0	);
			this.doProcedureLightning(	-5,		-5	);
			this.doProcedureLightning(	0,		-10	);
			this.doProcedureLightning(	5,		-5	);
			this.doProcedureLightning(	10,		0	);
			this.doProcedureLightning(	5,		5	);
			this.doProcedureLightning(	0,		10	);
			this.doProcedureLightning(	-5,		5	);
			
		}
		
		protected void doProcedureLightning(final double x, final double z)
		{
			
			Util.doPause(0.5F);
			this.doLightning(x, z);
			
		}
		
		protected final void doLightning(final double x, final double z)
		{
			
			this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.positionX + x, this.positionY, this.positionZ + z));
			
		}
		
	}
	
}
