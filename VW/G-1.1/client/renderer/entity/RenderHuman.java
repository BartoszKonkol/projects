package vw.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderHuman extends RenderLiving
{
	
	public static final byte SCAN = 1;

	public RenderHuman()
	{
		
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0.5F);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(final Entity par1Entity)
	{
		
		return new ResourceLocation("textures/entity/steve.png");
		
	}

}
