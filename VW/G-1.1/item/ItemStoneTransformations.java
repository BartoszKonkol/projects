package vw.item;

import vw.entity.projectile.EntityStoneTransformations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStoneTransformations extends Item
{
	
	public static final byte SCAN = 1;

	public ItemStoneTransformations()
	{
		
		this.setMaxStackSize(2);
		
	}
	
	@Override
	public boolean hasEffect(final ItemStack par1ItemStack)
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
			par2World.spawnEntityInWorld(new EntityStoneTransformations(par2World, par3EntityPlayer));
		
		return par1ItemStack;
		
	}
	
}
