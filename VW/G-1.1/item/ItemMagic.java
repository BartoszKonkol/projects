package vw.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemMagic extends ItemSubtypesControl
{
	
	public static final byte SCAN = 1;
	
	public ItemMagic()
	{
		
		this.setMaxStackSize(16);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		
		this.addType("lightning");
		this.addType("magicstone");
		
	}
	
	@Override
	public String getUnlocalizedName(final ItemStack par1ItemStack)
	{
		
		return super.getUnlocalizedName() + "." + this.giveType(MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, this.giveQuantityTypes() - 1) + 1);
		
	}
	
	@Override
	public boolean hasEffect(final ItemStack par1ItemStack)
	{
		
		switch(par1ItemStack.getItemDamage())
		{
			
			case 0:
				return true;
			case 1:
				return false;
			case 2:
				return true;
			case 3:
				return false;
			default:
				return super.hasEffect(par1ItemStack);
			
		}
		
	}
	
	@Override
	public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer)
	{
		
		if(this.doCompareSubtypes(par1ItemStack.getItemDamage() + 1, 1))
		{
			
			if (!par3EntityPlayer.capabilities.isCreativeMode)
				--par1ItemStack.stackSize;
		
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			
			par2World.addWeatherEffect(new EntityLightningBolt(par2World, par3EntityPlayer.getBoundingBox().minX, par3EntityPlayer.getBoundingBox().minY, par3EntityPlayer.getBoundingBox().minZ));
			
			return par1ItemStack;
			
		}
		else
			return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		
	}
	
}
