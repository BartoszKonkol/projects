package vw.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderHerobrine extends RenderLiving implements LayerRenderer
{
	
	public static final byte SCAN = 1;

	public RenderHerobrine()
	{
		
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0.5F);
		this.addLayer(this);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(final Entity par1Entity)
	{
		
		return new ResourceLocation("textures/entity/steve.png");
		
	}

	@Override
	public boolean shouldCombineTextures()
	{
		
		return true;
		
	}

	@Override
	public void doRenderLayer(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7, float par8) // shouldRenderPass
	{
	
		this.bindTexture(new ResourceLocation("textures/entity/herobrine/herobrine_eyes.png"));
		
	}
	
}
