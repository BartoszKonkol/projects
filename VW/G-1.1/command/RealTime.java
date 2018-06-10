package vw.command;

import java.text.SimpleDateFormat;
import java.util.Date;
import vw.util.ThreadSub;
import vw.util.Util;
import net.minecraft.server.MinecraftServer;

public class RealTime extends ThreadSub 
{
	
	public static final byte SCAN = 1;
	
	@Override
	public void action() //6:30; 20:30; 65Y
	{
		
		while(CommandRealTime.work)
		{
		
			final long time = Util.giveTime();
			
			for(int i = 0; i < MinecraftServer.getServer().worldServers.length; ++i)
				MinecraftServer.getServer().worldServers[i].setWorldTime((long)((((Integer.valueOf(new SimpleDateFormat ("HH").format(new Date())) * 60) + Integer.valueOf(new SimpleDateFormat ("mm").format(new Date()))) * 60)+ Integer.valueOf(new SimpleDateFormat ("ss").format(new Date()))) / 72 * 20 + 16500);
			
			Util.doPause(time, 1.0F);
	  		
		}
		
	}

}
