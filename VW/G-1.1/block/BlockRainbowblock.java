package vw.block;

import java.util.Random;
import vw.VirtualWorld;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockRainbowblock extends BlockSoftControl 
{
	
	public static final byte SCAN = 1;
	
	public BlockRainbowblock()
	{
		
		super(Material.cloth);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.rainbowBlock);
		
	}
	
	@Override
	public void onEntityCollidedWithBlock(final World par1World, final BlockPos par2BlockPos, final Entity par3Entity)
	{
		
		this.fall(par3Entity);
		
		par3Entity.motionY += 0.5F;
		
		if(par1World.getBlockState /* getBlock */ (par2BlockPos.down()).getBlock() == VirtualWorld.rainbowBlock && par1World.getBlockState /* getBlock */ (par2BlockPos.down(2)).getBlock() != Blocks.air)
			this.penetration = true;
		else
			this.penetration = false;
		
	}
	
	protected void fall(final Entity par1Entity)
	{
		
		if(par1Entity != null && par1Entity instanceof EntityLivingBase)
		{
			
			final EntityLivingBase entity = (EntityLivingBase) par1Entity;
			final float distance = entity.fallDistance;
			
			if(distance > 0)
			{
				
				final PotionEffect potioneffect = entity.getActivePotionEffect(Potion.jump);
				final float protection = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0.0F;
				final int loss = MathHelper.ceiling_float_int((distance - 3.0F - protection) / 2);
				
				if (loss > 0)
				{
					
					entity.playSound(loss > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small", 1.0F, 1.0F);
					entity.attackEntityFrom(DamageSource.fall, loss);
					
				}
				
			}
			
		}
		
	}
	
}
