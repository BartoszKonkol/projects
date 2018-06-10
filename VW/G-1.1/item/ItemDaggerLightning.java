package vw.item;

import vw.entity.projectile.EntityDaggerLightning;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDaggerLightning extends ItemDaggerControl
{
	
	public static final byte SCAN = 1;

	public ItemDaggerLightning()
	{
		
		this.setMaxStackSize(6);
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		
		if (!par3EntityPlayer.capabilities.isCreativeMode)
			--par1ItemStack.stackSize;
		 
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		 
		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityDaggerLightning(par2World, par3EntityPlayer));
		 
		return par1ItemStack;
		
	}
	
	@Override
	public String iconItem()
	{
		
		return "lightning";
		
	}
	
}
