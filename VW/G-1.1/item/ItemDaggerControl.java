package vw.item;

import javax.jnf.lwjgl.Color;
import com.google.common.collect.Multimap;
import vw.entity.projectile.EntityDagger;
import vw.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemDaggerControl extends Item
{
	
	public static final byte SCAN = 1;
	
	protected final boolean model;
	
	public ItemDaggerControl(final boolean par1) 
	{
		
		this.setMaxStackSize(2);
		
		this.model = par1;
		
		if(!this.model)
			this.setCreativeTab(CreativeTabs.tabCombat);
		
	}
	
	public ItemDaggerControl()
	{
		
		this(false);
		
	}
	
	public byte power()
	{
		
		return 0;
		
	}
	
	public byte damage()
	{
		
		return (byte) (this.power() / 2.5F);
		
	}
	
	public short use()
	{
		
		return (short) (this.damage() * 10);
		
	}
	
	protected byte colorItemRed()
	{
		
		return 127;
		
	}
	
	protected byte colorItemGreen()
	{
		
		return 127;
		
	}
	
	protected byte colorItemBlue()
	{
		
		return 127;
		
	}
	
	public final int colorItem()
	{
		
		return new Color(this.colorItemRed() + 128, this.colorItemGreen() + 128, this.colorItemBlue() + 128).getColor();
		
	}
	
	public String iconItem()
	{
		
		return this.model ? "daggermodel" : "dagger";
		
	}
	
	@Override
	@SuppressWarnings({"deprecation", "unchecked"})
	public Multimap<?, ?> getItemAttributeModifiers() // getDamageVsEntity
	{
		
		return Util.giveItemModifierDamage(super.getItemAttributeModifiers(), this.itemModifierUUID, this.damage());
		
	}
	
	@Override
	public boolean isFull3D()
	{
		
		return true;
		
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer)
	{
		
		if (!par3EntityPlayer.capabilities.isCreativeMode)
			--par1ItemStack.stackSize;
		
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (this.itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityDagger(par2World, par3EntityPlayer, this.power(), this));
		
		return par1ItemStack;
		
	}
	
	@Override
	public boolean onLeftClickEntity(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final Entity par3Entity)
	{
		
		return this.damage() == 0;
		
	}
	
	@Override
	public boolean isBookEnchantable(final ItemStack par1ItemStack, final ItemStack par2ItemStack)
	{
		
		return this.damage() >= 0 && this.damage() <= 3;
		
	}
	
	@Override
	public final int getColorFromItemStack(final ItemStack par1ItemStack, final int par2)
	{
		
		return this.colorItem();
		
	}
	
	@Override
	public final int getMaxDamage()
	{
		
		return this.use();
		
	}
	
	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
		
	}
	
}
