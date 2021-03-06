package vw.dispenser;

import vw.entity.projectile.EntityStoneTransformations;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class DispenserBehaviorStoneTransformations extends BehaviorProjectileDispense
{
	
	public static final byte SCAN = 1;
	
	@Override
	protected IProjectile getProjectileEntity(final World par1World, final IPosition par2IPosition)
    {
		
		return new EntityStoneTransformations(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
		
    }
	
}
