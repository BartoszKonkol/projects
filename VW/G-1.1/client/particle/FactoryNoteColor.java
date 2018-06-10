package vw.client.particle;

import javax.jnf.lwjgl.Color;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.client.particle.EntityNoteFX.Factory;
import net.minecraft.world.World;

public class FactoryNoteColor extends Factory
{
	
	public static final byte SCAN = 1;
	
	@Override
	public EntityFX getEntityFX(int par1, World par2World, double par3, double par4, double par5, double par6, double par7, double par8, int ... args9)
	{
		
		final EntityNoteFX entity = (EntityNoteFX) super.getEntityFX(par1, par2World, par3, par4, par5, par6, par7, par8, args9.length > 1 ? args9 : new int[0]);
		
		if(args9.length > 0)
		{
			
			final Color color = new Color(args9[0]);
			entity.setRBGColorF(Float.valueOf(color.getRed()) / Color.MAX_VALUE_COLOR, Float.valueOf(color.getGreen()) / Color.MAX_VALUE_COLOR, Float.valueOf(color.getBlue()) / Color.MAX_VALUE_COLOR);
			
		}
		
		return entity;
		
	}
	
}
