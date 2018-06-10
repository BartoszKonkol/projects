package vw.dispenser;

import vw.entity.projectile.EntityDagger;
import vw.item.ItemDaggerControl;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class DispenserBehaviorDagger extends BehaviorProjectileDispense
{
	
	public static final byte SCAN = 1;
	
	protected final byte power;
	protected final ItemDaggerControl dagger;
	
	public DispenserBehaviorDagger(final byte par1, final ItemDaggerControl par2ItemDaggerControl)
	{
		
		this.power = par1;
		this.dagger = par2ItemDaggerControl;
		
	}
	
	@Override
	protected IProjectile getProjectileEntity(final World par1World, final IPosition par2IPosition)
    {
		
        return new EntityDagger(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ(), this.power, this.dagger);
        
    }

}
