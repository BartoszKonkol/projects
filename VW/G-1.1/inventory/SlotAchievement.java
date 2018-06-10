package vw.inventory;

import vw.VirtualWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SlotAchievement
{
	
	public static final byte SCAN = 1;
	
	private SlotAchievement()
	{
		
		;
		
	}
	
	public static void onCrafting(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) 
	{
		
		if (par2ItemStack.getItem() == VirtualWorld.wateryDiamond)
			par1EntityPlayer.triggerAchievement(VirtualWorld.achievementWateryDiamond);
		
	}
	
	public static void onSmelting(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) 
	{
/*		
		if (par2ItemStack.getItem() == [ITEM])
			par1EntityPlayer.triggerAchievement([ITEM]);
*/		
	}
	
	public static void doInstant(final EntityPlayer par1EntityPlayer)
	{
		
		par1EntityPlayer.triggerAchievement(VirtualWorld.achievementVirtualWorld);
		
	}
	
}
