package vw.proxy;

import net.minecraftforge.fml.common.LoaderState.ModState;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy 
{
	
	protected CommonProxy()
	{
		
		;
		
	}

	public abstract void onEnable(final ModState state, final Side side, final FMLPreInitializationEvent pre);
	public abstract void onDisable(final ModState state, final Side side, final FMLPostInitializationEvent post);
	
}
