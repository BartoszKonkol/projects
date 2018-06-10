package vw.item;

import java.util.List;
import vw.VirtualWorld;
import vw.util.DataTexts;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemEnchantedWand extends ItemSubtypesControl
{
	
	public static final byte SCAN = 1;
	
	public ItemEnchantedWand()
	{
		
		this.setMaxStackSize(16);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		
		this.addType("lightning");
		this.addType("magicstone");
		
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubItems(final Item par1Item, final CreativeTabs par2CreativeTabs, final List par3List)
	{
		
		for(int i = 0; i < this.giveQuantityTypes() + 1; i++)
			par3List.add(new ItemStack(this, 1, i));
		
	}
	
	@Override
	public boolean hasEffect(final ItemStack par1ItemStack)
	{
		
		return true;
		
	}
	
	@Override
	public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer)
	{
		
		par2World.playSoundAtEntity(par3EntityPlayer, "fireworks.blast", 20.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		for(byte i = -4; i <= 4; i++)
			for(byte j = 0; j < 3; j++)
			{
				
				double positionX = par3EntityPlayer.posX       + ((j == 0) ? (i / 2) : 0);
				double positionY = par3EntityPlayer.posY - 0.5 + ((j == 1) ? (i / 2) : 0);
				double positionZ = par3EntityPlayer.posZ       + ((j == 2) ? (i / 2) : 0);
				
				for(byte k = 0; k < 2; k++)
					par2World.spawnParticle(EnumParticleTypes.PORTAL, positionX, positionY, positionZ, 0.0D, 0.0D, 0.0D);
				
			}
		
		if(par1ItemStack.getItemDamage() > 0 && par1ItemStack.getItemDamage() <= this.giveQuantityTypes())
		{
			
			if (!par3EntityPlayer.capabilities.isCreativeMode)
				--par1ItemStack.stackSize;
			
			if(this.doCompareSubtypes(par1ItemStack, 1))
				par2World.addWeatherEffect(new EntityLightningBolt(par2World, par3EntityPlayer.getBoundingBox().minX, par3EntityPlayer.getBoundingBox().minY, par3EntityPlayer.getBoundingBox().minZ));
			else if(this.doCompareSubtypes(par1ItemStack, 2))
				return new ItemStack(VirtualWorld.enchantedWand, 1, par1ItemStack.getItemDamage());
			
			if (par1ItemStack.stackSize <= 0)
				return new ItemStack(VirtualWorld.wand);
			else if(!par3EntityPlayer.capabilities.isCreativeMode)
				par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(VirtualWorld.wand));
			
			return par1ItemStack;
			
		}
		else
			return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4)
	{
		
		if(par1ItemStack.getItemDamage() > 0)
		{
			
			par3List.add("Magiczna funkcja:");
			
			if(this.doCompareSubtypes(par1ItemStack, 1))
				par3List.add("   " + EnumChatFormatting.GOLD + DataTexts.elementNameMagicLightning);
			else if(this.doCompareSubtypes(par1ItemStack, 2))
				par3List.add("   " + EnumChatFormatting.GOLD + DataTexts.elementNameMagicMagicStone);
			
		}
		else
			super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		
	}
	
	@Override
	public boolean isFull3D()
	{
		
		return true;
		
	}
	
}
