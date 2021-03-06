package vw.proxy;

import javax.jnf.exception.Defect;
import net.minecraftforge.fml.common.LoaderState.ModState;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public final class ServerProxy extends CommonProxy
{
	
	@Override
	public void onEnable(ModState state, Side side, FMLPreInitializationEvent pre)
	{
		
		if(side.isServer())
			try
			{
					
				throw new Defect();
				
			}
			catch(Defect e)
			{
				
				pre.getModLog().fatal(pre.getModMetadata().name + " " + pre.getModMetadata().version + " does not support type of " + side.name() + "!", e);
				e.exit();
				
			}
		
	}
	
	@Override
	public void onDisable(ModState state, Side side, FMLPostInitializationEvent post)
	{
		
		;
		
	}
	
}
